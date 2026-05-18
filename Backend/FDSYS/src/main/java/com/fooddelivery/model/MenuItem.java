package com.fooddelivery.model;

public class MenuItem {

    private String itemId;
    private String restaurantId;
    private String name;
    private String description;
    private double price;
    private String category;
    private boolean available;

    public MenuItem() {}

    public MenuItem(String itemId, String restaurantId, String name,
                    String description, double price, String category, boolean available) {
        this.itemId       = itemId;
        this.restaurantId = restaurantId;
        this.name         = name;
        this.description  = description;
        this.price        = price;
        this.category     = category;
        this.available    = available;
    }

    public String  getItemId()                     { return itemId; }
    public void    setItemId(String id)            { this.itemId = id; }

    public String  getRestaurantId()               { return restaurantId; }
    public void    setRestaurantId(String rid)     { this.restaurantId = rid; }

    public String  getName()                       { return name; }
    public void    setName(String n)               { this.name = n; }

    public String  getDescription()                { return description; }
    public void    setDescription(String d)        { this.description = d; }

    public double  getPrice()                      { return price; }
    public void    setPrice(double p)              { this.price = p; }

    public String  getCategory()                   { return category; }
    public void    setCategory(String c)           { this.category = c; }

    public boolean isAvailable()                   { return available; }
    public void    setAvailable(boolean a)         { this.available = a; }
}
