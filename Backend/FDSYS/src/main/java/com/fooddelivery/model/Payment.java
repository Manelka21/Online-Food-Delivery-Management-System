package com.fooddelivery.model;

public class Payment {

    private String paymentId;
    private String orderId;
    private double amount;
    private String method;          // CARD or CASH
    private String status;          // PENDING, COMPLETED, FAILED
    private String paymentDate;

    public Payment() {}

    public String getPaymentId()                { return paymentId; }
    public void   setPaymentId(String id)       { this.paymentId = id; }

    public String getOrderId()                  { return orderId; }
    public void   setOrderId(String o)          { this.orderId = o; }

    public double getAmount()                   { return amount; }
    public void   setAmount(double a)           { this.amount = a; }

    public String getMethod()                   { return method; }
    public void   setMethod(String m)           { this.method = m; }

    public String getStatus()                   { return status; }
    public void   setStatus(String s)           { this.status = s; }

    public String getPaymentDate()              { return paymentDate; }
    public void   setPaymentDate(String d)      { this.paymentDate = d; }
}
