package com.fooddelivery.model;

// FeaturedMenuItem - extends MenuItem with discount functionality

public class FeaturedMenuItem extends MenuItem {

    private double discountPercent;
    private String featuredTag;

    public FeaturedMenuItem() { super(); }

    public FeaturedMenuItem(String id, String name, String category,
                            String description, double price,
                            boolean available, String imageUrl,
                            double discountPercent, String featuredTag) {
        super(id, name, category, description, price, available, imageUrl);
        this.discountPercent = discountPercent;
        this.featuredTag     = featuredTag;
    }

    @Override
    public double getPrice() {
        return super.getPrice() * (1 - discountPercent / 100.0);
    }

    public double getOriginalPrice()                       { return super.getPrice(); }

    public double getDiscountPercent()                     { return discountPercent; }
    public void   setDiscountPercent(double d)             { this.discountPercent = d; }

    public String getFeaturedTag()                         { return featuredTag; }
    public void   setFeaturedTag(String featuredTag)       { this.featuredTag = featuredTag; }
}
