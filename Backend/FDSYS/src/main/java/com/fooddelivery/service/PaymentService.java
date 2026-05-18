package com.fooddelivery.service;

import com.fooddelivery.model.Payment;
import com.fooddelivery.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repo;

    public List<Payment> getAll()                          { return repo.findAll(); }
    public Optional<Payment> getById(String id)            { return repo.findById(id); }
    public Optional<Payment> getByOrderId(String orderId)  { return repo.findByOrderId(orderId); }

    public Payment createPayment(String orderId, double amount, String method) {
        Payment p = new Payment();
        p.setOrderId(orderId);
        p.setAmount(amount);
        p.setMethod(method.toUpperCase());
        if ("CARD".equals(method.toUpperCase())) {
            p.setStatus("COMPLETED");
            p.setPaymentDate(LocalDateTime.now().toString());
        } else {
            p.setStatus("PENDING");
            p.setPaymentDate(null);
        }
        return repo.save(p);
    }

    public List<Payment> getByBatch(List<String> orderIds) {
        List<Payment> result = new ArrayList<>();
        for (String oid : orderIds) repo.findByOrderId(oid).ifPresent(result::add);
        return result;
    }

    public Payment updateStatus(String id, String status) {
        Payment p = repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Payment not found"));
        p.setStatus(status);
        if ("COMPLETED".equals(status)) p.setPaymentDate(LocalDateTime.now().toString());
        return repo.save(p);
    }
}
