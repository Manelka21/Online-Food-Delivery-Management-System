package com.fooddelivery.model;

// This class represents a special menu item with a discount
// OOP Concept: Inheritance - FeaturedMenuItem extends MenuItem
// This means FeaturedMenuItem gets all fields and methods from MenuItem

public class FeaturedMenuItem extends MenuItem {

    // Extra fields specific to featured items
    private double discountPercent; // example: 15.0 means 15% discount
    private String featuredTag;     // example: "Chef's Pick" or "Today's Special"

    // Default constructor
    public FeaturedMenuItem() {
        super(); // calls the MenuItem default constructor
    }

    // Parameterized constructor
    public FeaturedMenuItem(String id, String name, String category, String description, double price, boolean available, String imageUrl, double discountPercent, String featuredTag) {

        // Call parent class constructor to set the basic fields
        super(id, name, category, description, price, available, imageUrl);

        // Set the extra fields
        this.discountPercent = discountPercent;
        this.featuredTag = featuredTag;
    }

    // OOP Concept: Polymorphism - overriding the getPrice() method
    // In MenuItem, getPrice() returns the original price
    // Here we override it to return the discounted price instead
    // Same method name, different behaviour - this is polymorphism
    @Override
    public double getPrice() {
        // Calculate discounted price
        // Example: price=850, discount=15%
        // discounted price = 850 * (1 - 15/100) = 850 * 0.85 = 722.50
        double discountAmount = super.getPrice() * discountPercent / 100;
        return super.getPrice() - discountAmount;
    }

    // This method returns the original price before discount
    // We use super.getPrice() to get the price from MenuItem
    public double getOriginalPrice() {
        return super.getPrice();
    }

    // Getters and setters for the extra fields
    public double getDiscountPercent() {
        return discountPercent;
    }

    public void setDiscountPercent(double discountPercent) {
        this.discountPercent = discountPercent;
    }

    public String getFeaturedTag() {
        return featuredTag;
    }

    public void setFeaturedTag(String featuredTag) {
        this.featuredTag = featuredTag;
    }
}
