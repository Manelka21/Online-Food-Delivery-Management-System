package com.fooddelivery.payment.service;

import com.fooddelivery.payment.model.*;
import com.fooddelivery.payment.util.FileHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class PaymentService {

    @Autowired
    private FileHandler fileHandler;

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // CREATE
    public Payment createPayment(String paymentType, String orderId, String customerId,
                                  double amount, String extraField1, String extraField2) {
        String paymentId = "PAY-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        String createdAt = LocalDateTime.now().format(FORMATTER);
        String status = "PENDING";

        Payment newPayment;
        switch (paymentType.toUpperCase()) {
            case "CARD":   newPayment = new CardPayment(paymentId, orderId, customerId, amount, status, createdAt, extraField1, extraField2); break;
            case "CASH":   newPayment = new CashPayment(paymentId, orderId, customerId, amount, status, createdAt, extraField1); break;
            case "ONLINE": newPayment = new OnlinePayment(paymentId, orderId, customerId, amount, status, createdAt, extraField1); break;
            default: throw new IllegalArgumentException("Unknown payment type: " + paymentType);
        }

        fileHandler.appendPayment(newPayment);
        return newPayment;
    }


    public List<Payment> getAllPayments() {
        return fileHandler.loadAllPayments();
    }


    public Optional<Payment> getPaymentById(String paymentId) {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getPaymentId().equals(paymentId))
                .findFirst();
    }


    public List<Payment> getPaymentsByCustomer(String customerId) {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }


    public List<Payment> getPaymentsByStatus(String status) {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getStatus().equalsIgnoreCase(status))
                .collect(Collectors.toList());
    }


    public boolean updatePaymentStatus(String paymentId, String newStatus) {
        List<Payment> payments = fileHandler.loadAllPayments();
        boolean found = false;
        for (Payment p : payments) {
            if (p.getPaymentId().equals(paymentId)) {
                p.setStatus(newStatus);
                found = true;
                break;
            }
        }
        if (found) fileHandler.saveAllPayments(payments);
        return found;
    }


    public boolean deletePayment(String paymentId) {
        List<Payment> payments = fileHandler.loadAllPayments();
        int original = payments.size();
        payments.removeIf(p -> p.getPaymentId().equals(paymentId));
        if (payments.size() < original) {
            fileHandler.saveAllPayments(payments);
            return true;
        }
        return false;
    }


    public double getTotalRevenue() {
        return fileHandler.loadAllPayments().stream()
                .filter(p -> p.getStatus().equals("COMPLETED"))
                .mapToDouble(Payment::getAmount).sum();
    }
}
