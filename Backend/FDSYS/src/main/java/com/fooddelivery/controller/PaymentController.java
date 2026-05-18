package com.fooddelivery.controller;

import com.fooddelivery.model.Payment;
import com.fooddelivery.service.OrderService;
import com.fooddelivery.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "*")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/payments")
    public List<Payment> getAll() { return paymentService.getAll(); }

    @GetMapping("/payments/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return paymentService.getById(id).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/payments/order/{orderId}")
    public ResponseEntity<?> getByOrder(@PathVariable String orderId) {
        return paymentService.getByOrderId(orderId).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/payments/batch/{batchId}")
    public List<Payment> getByBatch(@PathVariable String batchId) {
        List<String> orderIds = orderService.getByBatch(batchId).stream()
            .map(o -> o.getOrderId())
            .collect(Collectors.toList());
        return paymentService.getByBatch(orderIds);
    }

    @PostMapping("/payments")
    public ResponseEntity<?> createPayment(@RequestBody Map<String, Object> body) {
        try {
            String orderId = (String) body.get("orderId");
            double amount  = ((Number) body.get("amount")).doubleValue();
            String method  = (String) body.get("method");
            return ResponseEntity.status(201).body(
                paymentService.createPayment(orderId, amount, method));
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/payments/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id,
                                          @RequestBody Map<String, String> body) {
        try { return ResponseEntity.ok(paymentService.updateStatus(id, body.get("status"))); }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
