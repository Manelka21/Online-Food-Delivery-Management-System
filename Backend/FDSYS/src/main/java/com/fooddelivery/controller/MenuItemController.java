package com.fooddelivery.controller;

import com.fooddelivery.model.MenuItem;
import com.fooddelivery.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@CrossOrigin(origins = "*")
public class MenuItemController {

    @Autowired
    private MenuItemService service;

    @GetMapping("/menu-items")
    public List<MenuItem> getAll() { return service.getAll(); }

    @GetMapping("/menu-items/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        return service.getById(id).map(ResponseEntity::ok)
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/menu-items/restaurant/{restaurantId}")
    public List<MenuItem> getByRestaurant(@PathVariable String restaurantId) {
        return service.getByRestaurant(restaurantId);
    }

    @PostMapping("/menu-items")
    public ResponseEntity<MenuItem> add(@RequestBody MenuItem m) {
        return ResponseEntity.status(201).body(service.save(m));
    }

    @PutMapping("/menu-items/{id}")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody MenuItem m) {
        try { return ResponseEntity.ok(service.update(id, m)); }
        catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/menu-items/{id}")
    public ResponseEntity<?> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.ok(Map.of("message", "Menu item deleted"));
    }
}
