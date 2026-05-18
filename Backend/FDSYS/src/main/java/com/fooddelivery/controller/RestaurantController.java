package com.fooddelivery.controller;

import com.fooddelivery.model.Restaurant;
import com.fooddelivery.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class RestaurantController {

    @Autowired
    private RestaurantService service;

    @GetMapping("/restaurants")
    public List<Restaurant> getAll() { return service.getAll(); }

    @GetMapping("/restaurants/open")
    public List<Restaurant> getOpen() { return service.getOpenRestaurants(); }

    @GetMapping("/restaurants/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return service.getById(id)
            .map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/restaurants")
    public ResponseEntity<Restaurant> add(@RequestBody Restaurant r) {
        return ResponseEntity.status(201).body(service.save(r));
    }

    @PutMapping("/restaurants/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Restaurant r) {
        try { return ResponseEntity.ok(service.update(id, r)); }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/restaurants/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Restaurant deleted"));
    }
}
