package com.fooddelivery.model;

public class OrderBatch {

    private String batchId;
    private String customerId;
    private String batchDate;
    private double totalAmount;
    private int    orderCount;

    public OrderBatch() {}

    public OrderBatch(String batchId, String customerId, String batchDate,
                      double totalAmount, int orderCount) {
        this.batchId     = batchId;
        this.customerId  = customerId;
        this.batchDate   = batchDate;
        this.totalAmount = totalAmount;
        this.orderCount  = orderCount;
    }

    public String getBatchId()                { return batchId; }
    public void   setBatchId(String id)       { this.batchId = id; }

    public String getCustomerId()             { return customerId; }
    public void   setCustomerId(String c)     { this.customerId = c; }

    public String getBatchDate()              { return batchDate; }
    public void   setBatchDate(String d)      { this.batchDate = d; }

    public double getTotalAmount()            { return totalAmount; }
    public void   setTotalAmount(double t)    { this.totalAmount = t; }

    public int    getOrderCount()             { return orderCount; }
    public void   setOrderCount(int n)        { this.orderCount = n; }
}
