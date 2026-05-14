package com.fooddelivery.repository;

import com.fooddelivery.model.Customer;

import java.util.Optional;

public class CustomerRepository extends BaseFileRepository<Customer> {

    @Override
    protected String getFilename() { return "users.txt"; }

    @Override
    protected String getPrefix() { return "USR"; }

    @Override
    protected int getIdIndex() { return 0; }

    @Override
    protected String getEntityRole() { return "Customer"; }

    @Override
    protected int getRoleIndex() { return 1; }

    @Override
    protected Customer parse(String[] f) {
        if (f.length < 10) return null;
        Customer c = new Customer();
        c.setPersonId(f[0]);
        c.setName(f[2]);
        c.setEmail(f[3]);
        c.setPassword(f[4]);
        c.setPhone(f[5]);
        c.setAddress(f[6]);
        return c;
    }

    @Override
    protected String[] toFields(Customer c) {
        return new String[]{
            c.getPersonId(),
            "Customer",
            c.getName(),
            c.getEmail(),
            c.getPassword(),
            c.getPhone(),
            c.getAddress() != null ? c.getAddress() : "",
            "", "", "true"
        };
    }

    @Override
    protected String getId(Customer c) {
        return c.getPersonId();
    }

    @Override
    protected void setId(Customer c, String id) {
        c.setPersonId(id);
    }

    public Optional<Customer> findByEmailAndPassword(String email, String password) {
        return findBy(c -> c.getEmail().equals(email) && c.getPassword().equals(password));
    }

    public boolean existsByEmail(String email) {
        return findBy(c -> c.getEmail().equals(email)).isPresent();
    }
}