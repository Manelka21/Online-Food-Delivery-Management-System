package com.fooddelivery.model;

public class Customer extends Person {

    private String address;

    public Customer() { super(); }

    public Customer(String personId, String name, String email, String password,
                    String phone, String address) {
        super(personId, name, email, password, phone);
        this.address = address;
    }

    @Override
    public String getRole() { return "CUSTOMER"; }

    public String getAddress()           { return address; }
    public void   setAddress(String a)   { this.address = a; }
}