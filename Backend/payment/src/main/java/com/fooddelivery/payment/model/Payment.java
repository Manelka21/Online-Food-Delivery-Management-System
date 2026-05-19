package com.fooddelivery.payment.model;

public abstract class Payment {
    private String paymentId, orderId, customerId, status, createdAt;
    private double amount;

    public Payment(String paymentId, String orderId, String customerId,
                   double amount, String status, String createdAt) {
        this.paymentId=paymentId; this.orderId=orderId; this.customerId=customerId;
        this.amount=amount; this.status=status; this.createdAt=createdAt;
    }

    public abstract String getPaymentType();
    public abstract String processPayment();
    public abstract String toFileString();

    public String getPaymentId(){return paymentId;} public void setPaymentId(String v){paymentId=v;}
    public String getOrderId(){return orderId;}
    public String getCustomerId(){return customerId;}
    public double getAmount(){return amount;}
    public String getStatus(){return status;} public void setStatus(String v){status=v;}
    public String getCreatedAt(){return createdAt;}
}
