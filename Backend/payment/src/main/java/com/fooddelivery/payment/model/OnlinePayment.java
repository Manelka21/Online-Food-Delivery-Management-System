package com.fooddelivery.payment.model;
public class OnlinePayment extends Payment {
    private String walletProvider;
    public OnlinePayment(String paymentId,String orderId,String customerId,double amount,String status,String createdAt,String walletProvider){
        super(paymentId,orderId,customerId,amount,status,createdAt); this.walletProvider=walletProvider;
    }
    @Override public String getPaymentType(){return "ONLINE";}
    @Override public String processPayment(){return "Online payment via "+walletProvider;}
    @Override public String toFileString(){return getPaymentId()+","+getOrderId()+","+getCustomerId()+","+getAmount()+","+getStatus()+","+getCreatedAt()+",ONLINE,"+walletProvider+",N/A";}
    public String getWalletProvider(){return walletProvider;}
}
