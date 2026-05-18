package com.fooddelivery.model;

public class OrderItem {

    private String orderItemId;
    private String orderId;
    private String itemId;
    private int    quantity;
    private double unitPrice;

    public OrderItem() {}

    public String getOrderItemId()                { return orderItemId; }
    public void   setOrderItemId(String id)       { this.orderItemId = id; }

    public String getOrderId()                    { return orderId; }
    public void   setOrderId(String o)            { this.orderId = o; }

    public String getItemId()                     { return itemId; }
    public void   setItemId(String i)             { this.itemId = i; }

    public int    getQuantity()                   { return quantity; }
    public void   setQuantity(int q)              { this.quantity = q; }

    public double getUnitPrice()                  { return unitPrice; }
    public void   setUnitPrice(double p)          { this.unitPrice = p; }
}
