package com.fooddelivery.model;

public class Order {

    private String orderId;
    private String batchId;
    private String customerId;
    private String restaurantId;
    private String status;           // PENDING, CONFIRMED, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    private String orderDate;
    private double totalAmount;
    private String deliveryAddress;

    public Order() {}

    public String getOrderId()                    { return orderId; }
    public void   setOrderId(String id)           { this.orderId = id; }

    public String getBatchId()                    { return batchId; }
    public void   setBatchId(String b)            { this.batchId = b; }

    public String getCustomerId()                 { return customerId; }
    public void   setCustomerId(String c)         { this.customerId = c; }

    public String getRestaurantId()               { return restaurantId; }
    public void   setRestaurantId(String r)       { this.restaurantId = r; }

    public String getStatus()                     { return status; }
    public void   setStatus(String s)             { this.status = s; }

    public String getOrderDate()                  { return orderDate; }
    public void   setOrderDate(String d)          { this.orderDate = d; }

    public double getTotalAmount()                { return totalAmount; }
    public void   setTotalAmount(double t)        { this.totalAmount = t; }

    public String getDeliveryAddress()            { return deliveryAddress; }
    public void   setDeliveryAddress(String a)    { this.deliveryAddress = a; }
}
