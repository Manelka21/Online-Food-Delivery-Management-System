package com.fooddelivery.controller;

import com.fooddelivery.model.*;
import com.fooddelivery.service.DeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class DeliveryController {

    @Autowired
    private DeliveryService service;

    @GetMapping("/drivers")
    public List<Driver> getDrivers() { return service.getAllDrivers(); }

    @GetMapping("/drivers/{id}")
    public ResponseEntity<?> getDriverById(@PathVariable String id) {
        return service.getDriverById(id).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/drivers")
    public ResponseEntity<Driver> addDriver(@RequestBody Driver d) {
        return ResponseEntity.status(201).body(service.saveDriver(d));
    }

    @PutMapping("/drivers/{id}")
    public ResponseEntity<?> updateDriver(@PathVariable String id, @RequestBody Driver d) {
        try { return ResponseEntity.ok(service.updateDriver(id, d)); }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/drivers/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable String id) {
        service.deleteDriver(id);
        return ResponseEntity.ok(Map.of("message", "Driver deleted"));
    }

    @GetMapping("/deliveries")
    public List<Delivery> getAll() { return service.getAllDeliveries(); }

    @GetMapping("/deliveries/order/{orderId}")
    public ResponseEntity<?> getByOrder(@PathVariable String orderId) {
        return service.getByOrderId(orderId).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/deliveries")
    public ResponseEntity<?> assign(@RequestBody Map<String, String> body) {
        try {
            return ResponseEntity.status(201).body(
                service.assignDelivery(body.get("orderId"), body.get("driverId")));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/deliveries/{id}/status")
    public ResponseEntity<?> updateStatus(@PathVariable String id,
                                          @RequestBody Map<String, String> body) {
        try { return ResponseEntity.ok(service.updateStatus(id, body.get("status"))); }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }
}
