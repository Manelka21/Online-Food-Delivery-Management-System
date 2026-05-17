package com.fooddelivery.model;

// MenuItem model - represents a food item in the delivery system

// This class represents a single food item in the menu
// OOP Concept: Encapsulation - all fields are private
public class MenuItem {

    // Private fields - cannot be accessed from outside this class
    private String id;
    private String name;
    private String category;
    private String description;
    private double price;
    private boolean available;
    private String imageUrl;

    // Default constructor - creates an empty MenuItem object
    public MenuItem() {
    }

    // Parameterized constructor - creates a MenuItem with all details
    public MenuItem(String id, String name, String category, String description, double price, boolean available, String imageUrl) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.available = available;
        this.imageUrl = imageUrl;
    }

    // Getters - used to read private fields from outside the class
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public boolean isAvailable() {
        return available;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    // Setters - used to change private fields from outside the class
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    // This method converts a MenuItem object into a text line
    // Converts MenuItem object to pipe delimited string for file storage
    // Example output: MI001|Chicken Burger|Main Course|Juicy burger|850.0|true|
    public String toFileString() {
        return id + "|" + name + "|" + category + "|" + description + "|" + price + "|" + available + "|" + imageUrl;
    }

    // This method reads a text line and creates a MenuItem object
    // It splits the line by | symbol to get each field
    public static MenuItem fromFileString(String line) {
        String[] parts = line.split("\\|", -1);

        // Check if the line has all 7 fields
        if (parts.length < 7) {
            return null;
        }

        String id          = parts[0];
        String name        = parts[1];
        String category    = parts[2];
        String description = parts[3];
        double price       = Double.parseDouble(parts[4]);
        boolean available  = Boolean.parseBoolean(parts[5]);
        String imageUrl    = parts[6];

        return new MenuItem(id, name, category, description, price, available, imageUrl);
    }
}