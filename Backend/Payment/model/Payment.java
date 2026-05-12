package com.fooddelivery.payment.model;

public abstract class Payment {

    private String paymentId;
    private String orderId;
    private String customerId;   // NEW — which customer made this payment
    private double amount;
    private String status;
    private String createdAt;

    public Payment(String paymentId, String orderId, String customerId,
                   double amount, String status, String createdAt) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.customerId = customerId;
        this.amount = amount;
        this.status = status;
        this.createdAt = createdAt;
    }

    public abstract String getPaymentType();
    public abstract String processPayment();
    public abstract String toFileString();

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
    public String getOrderId() { return orderId; }
    public void setOrderId(String orderId) { this.orderId = orderId; }
    public String getCustomerId() { return customerId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
