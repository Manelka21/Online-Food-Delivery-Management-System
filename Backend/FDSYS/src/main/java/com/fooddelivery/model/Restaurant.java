package com.fooddelivery.model;

public class Restaurant {

    private String restaurantId;
    private String name;
    private String address;
    private String phone;
    private String cuisineType;
    private String status;       // "Open" or "Closed"

    public Restaurant() {}

    public Restaurant(String restaurantId, String name, String address,
                      String phone, String cuisineType, String status) {
        this.restaurantId = restaurantId;
        this.name         = name;
        this.address      = address;
        this.phone        = phone;
        this.cuisineType  = cuisineType;
        this.status       = status;
    }

    public String getRestaurantId()                { return restaurantId; }
    public void   setRestaurantId(String id)       { this.restaurantId = id; }

    public String getName()                        { return name; }
    public void   setName(String n)                { this.name = n; }

    public String getAddress()                     { return address; }
    public void   setAddress(String a)             { this.address = a; }

    public String getPhone()                       { return phone; }
    public void   setPhone(String p)               { this.phone = p; }

    public String getCuisineType()                 { return cuisineType; }
    public void   setCuisineType(String ct)        { this.cuisineType = ct; }

    public String getStatus()                      { return status; }
    public void   setStatus(String s)              { this.status = s; }
}
