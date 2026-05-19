package com.Fooddelivery.Food_delivery.model;
public class CartItem extends MenuItem {

    private int quantity;

    // Constructor using their MenuItem fields
    public CartItem(String name,double price,int quantity,String catagory){
        super(null,name,catagory,"",price,true,"'");
    this.quantity=quantity;
    }

    public CartItem() {}

    // Encapsulation
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { this.quantity = quantity; }

    // Polymorphism - overrides MenuItem's getTotal
    public double getTotal() {
        return getPrice() * quantity;
    }

    // Polymorphism - overrides MenuItem's toFileString
    @Override
    public String toFileString() {
        return getName() + "|" + getPrice() + "|" + quantity + "|" + getCategory();
    }

    // Read one line from cart.txt
    public static CartItem fromFileString(String line) {
        String[] parts = line.split("\\|");
        CartItem item = new CartItem();
        item.setName(parts[0]);
        item.setPrice(Double.parseDouble(parts[1]));
        item.setQuantity(Integer.parseInt(parts[2]));
        item.setCategory(parts.length > 3 ? parts[3] : "");
        return item;
    }

    @Override
    public String toString() {
        return "CartItem{name='" + getName() +
                "', price=" + getPrice() +
                ", quantity=" + quantity +
                ", total=" + getTotal() + "}";
    }
}