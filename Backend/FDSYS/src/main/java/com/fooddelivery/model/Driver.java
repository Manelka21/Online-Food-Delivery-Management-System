package com.fooddelivery.model;

public class Driver extends Person {

    private String vehicleNumber;
    private String licenseNumber;
    private boolean available;

    public Driver() { super(); }

    public Driver(String personId, String name, String email, String password,
                  String phone, String vehicleNumber, String licenseNumber) {
        super(personId, name, email, password, phone);
        this.vehicleNumber = vehicleNumber;
        this.licenseNumber = licenseNumber;
        this.available     = true;
    }

    @Override
    public String getRole() { return "DRIVER"; }

    public String  getVehicleNumber()              { return vehicleNumber; }
    public void    setVehicleNumber(String v)      { this.vehicleNumber = v; }

    public String  getLicenseNumber()              { return licenseNumber; }
    public void    setLicenseNumber(String l)      { this.licenseNumber = l; }

    public boolean isAvailable()                   { return available; }
    public void    setAvailable(boolean a)         { this.available = a; }
}
