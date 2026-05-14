package com.fooddelivery.model;

public class Admin extends Person {

    public Admin() { super(); }

    public Admin(String personId, String name, String email, String password, String phone) {
        super(personId, name, email, password, phone);
    }

    @Override
    public String getRole() { return "ADMIN"; }
}
