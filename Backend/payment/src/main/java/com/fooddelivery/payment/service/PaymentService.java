package com.fooddelivery.payment.service;

import com.fooddelivery.payment.model.*;
import com.fooddelivery.payment.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired private FileHandler fileHandler;
    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // CREATE
    public Payment createPayment(String type, String orderId, String customerId,
                                  double amount, String f1, String f2) {
        String id = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String at = LocalDateTime.now().format(FMT);
        Payment p;
        switch (type.toUpperCase()) {
            case "CARD":   p = new CardPayment(id, orderId, customerId, amount, "PENDING", at, f1, f2); break;
            case "CASH":   p = new CashPayment(id, orderId, customerId, amount, "PENDING", at, f1); break;
            case "ONLINE": p = new OnlinePayment(id, orderId, customerId, amount, "PENDING", at, f1); break;
            default: throw new IllegalArgumentException("Unknown type: " + type);
        }
        fileHandler.appendPayment(p);
        return p;
    }

    // READ
    public List<Payment> getAllPayments() { return fileHandler.loadAllPayments(); }

    public Optional<Payment> getPaymentById(String id) {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getPaymentId().equals(id)).findFirst();
    }

    public List<Payment> getPaymentsByCustomer(String cid) {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getCustomerId().equals(cid)).collect(Collectors.toList());
    }

    public List<Payment> getPaymentsByStatus(String status) {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getStatus().equalsIgnoreCase(status)).collect(Collectors.toList());
    }

    // UPDATE
    public boolean updatePaymentStatus(String id, String status) {
        List<Payment> list = fileHandler.loadAllPayments();
        boolean found = false;
        for (Payment p : list) {
            if (p.getPaymentId().equals(id)) { p.setStatus(status); found = true; break; }
        }
        if (found) fileHandler.saveAllPayments(list);
        return found;
    }

    // DELETE
    public boolean deletePayment(String id) {
        List<Payment> list = fileHandler.loadAllPayments();
        int s = list.size();
        list.removeIf(p -> p.getPaymentId().equals(id));
        if (list.size() < s) { fileHandler.saveAllPayments(list); return true; }
        return false;
    }

    public double getTotalRevenue() {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getStatus().equals("COMPLETED"))
                .mapToDouble(Payment::getAmount).sum();
    }
}
