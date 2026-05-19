package com.fooddelivery.payment.model;
public class CashPayment extends Payment {
    private String deliveryDriverId;
    public CashPayment(String paymentId,String orderId,String customerId,double amount,String status,String createdAt,String deliveryDriverId){
        super(paymentId,orderId,customerId,amount,status,createdAt); this.deliveryDriverId=deliveryDriverId;
    }
    @Override public String getPaymentType(){return "CASH";}
    @Override public String processPayment(){return "Cash collected by driver: "+deliveryDriverId;}
    @Override public String toFileString(){return getPaymentId()+","+getOrderId()+","+getCustomerId()+","+getAmount()+","+getStatus()+","+getCreatedAt()+",CASH,"+deliveryDriverId+",N/A";}
    public String getDeliveryDriverId(){return deliveryDriverId;}
}
