package com.fooddelivery.controller;

import com.fooddelivery.model.*;
import com.fooddelivery.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class OrderController {

    @Autowired
    private OrderService service;

    @GetMapping("/orders")
    public List<Order> getAll() { return service.getAll(); }

    @GetMapping("/orders/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return service.getById(id).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/orders/customer/{customerId}")
    public List<Order> getByCustomer(@PathVariable String customerId) {
        return service.getByCustomer(customerId);
    }

    @GetMapping("/orders/batch/{batchId}")
    public List<Order> getByBatch(@PathVariable String batchId) {
        return service.getByBatch(batchId);
    }

    @GetMapping("/order-batches")
    public List<OrderBatch> getAllBatches() { return service.getAllBatches(); }

    @PostMapping("/orders/batch")
    public ResponseEntity<?> placeBatchOrder(@RequestBody Map<String, Object> req) {
        try {
            OrderBatch batch = service.placeBatchOrder(req);
            return ResponseEntity.status(201).body(batch);
        } catch (Exception e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/orders/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id,
                                          @RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.ok(service.updateStatus(id, body.get("status")));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
