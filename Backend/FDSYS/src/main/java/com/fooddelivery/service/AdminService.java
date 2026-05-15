package com.fooddelivery.service;

import com.fooddelivery.model.Admin;
import com.fooddelivery.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class AdminService {

    @Autowired
    private AdminRepository repo;

    public Admin addAdmin(Admin a) {
        if (repo.existsByEmail(a.getEmail()))
            throw new RuntimeException("Email already registered");

        return repo.save(a);
    }

    public Optional<Admin> login(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }
}
