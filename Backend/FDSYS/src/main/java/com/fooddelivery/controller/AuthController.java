package com.fooddelivery.controller;

import com.fooddelivery.service.AdminService;
import com.fooddelivery.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class AuthController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AdminService adminService;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String password = body.get("password");


        Optional<Customer> customer = customerService.login(email, password);
        if (customer.isPresent()) {
            Customer c = customer.get();
            Map<String, Object> res = new HashMap<>();
            res.put("personId", c.getPersonId());
            res.put("name", c.getName());
            res.put("role", c.getRole());
            res.put("email", c.getEmail());
            return ResponseEntity.ok(res);
        }

        Optional<Admin> admin = adminService.login(email, password);
        if (admin.isPresent()) {
            Admin a = admin.get();
            Map<String, Object> res = new HashMap<>();
            res.put("personId", a.getPersonId());
            res.put("name", a.getName());
            res.put("role", a.getRole());
            res.put("email", a.getEmail());
            return ResponseEntity.ok(res);
        }

        return ResponseEntity.status(401).body(Map.of("error", "Invalid email or password"));
    }

    @PostMapping("/auth/admin/signup")
    public ResponseEntity<?> adminSignup(@RequestBody Admin admin) {
        try {
            Admin saved = adminService.addAdmin(admin);
            Map<String, Object> res = new HashMap<>();
            res.put("personId", saved.getPersonId());
            res.put("name", saved.getName());
            res.put("role", saved.getRole());
            return ResponseEntity.status(201).body(res);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<?> signup(@RequestBody Customer customer) {
        try {
            Customer saved = customerService.addCustomer(customer);
            Map<String, Object> res = new HashMap<>();
            res.put("personId", saved.getPersonId());
            res.put("name", saved.getName());
            res.put("role", saved.getRole());
            return ResponseEntity.status(201).body(res);
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("error", e.getMessage()));
        }
    }
}