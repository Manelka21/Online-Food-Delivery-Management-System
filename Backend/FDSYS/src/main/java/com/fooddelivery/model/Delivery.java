package com.fooddelivery.model;

public class Delivery {

    private String deliveryId;
    private String orderId;
    private String driverId;
    private String assignedTime;
    private String deliveredTime;
    private String status;           // ASSIGNED, PICKED_UP, DELIVERED

    public Delivery() {}

    public String getDeliveryId()                  { return deliveryId; }
    public void   setDeliveryId(String id)         { this.deliveryId = id; }

    public String getOrderId()                     { return orderId; }
    public void   setOrderId(String o)             { this.orderId = o; }

    public String getDriverId()                    { return driverId; }
    public void   setDriverId(String d)            { this.driverId = d; }

    public String getAssignedTime()                { return assignedTime; }
    public void   setAssignedTime(String t)        { this.assignedTime = t; }

    public String getDeliveredTime()               { return deliveredTime; }
    public void   setDeliveredTime(String t)       { this.deliveredTime = t; }

    public String getStatus()                      { return status; }
    public void   setStatus(String s)              { this.status = s; }
}
