package com.fooddelivery.service;

import com.fooddelivery.model.Customer;
import com.fooddelivery.repository.CustomerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {


    private final CustomerRepository repo = new CustomerRepository();

    public List<Customer> getAllCustomers() {
        return repo.findAll();
    }

    public Optional<Customer> getById(String id) {
        return repo.findById(id);
    }

    public Customer addCustomer(Customer c) {
        if (repo.existsByEmail(c.getEmail()))
            throw new RuntimeException("Email already registered");
        
        return repo.save(c);
    }

    public Customer updateCustomer(String id, Customer updated) {
        Customer existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        existing.setName(updated.getName());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        
        return repo.save(existing);
    }

    public void deleteCustomer(String id) {
        repo.deleteById(id);
    }

    public Optional<Customer> login(String email, String password) {
        return repo.findByEmailAndPassword(email, password);
    }
}