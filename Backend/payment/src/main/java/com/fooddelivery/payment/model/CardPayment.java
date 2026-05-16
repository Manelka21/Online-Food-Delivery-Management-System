package com.fooddelivery.payment.model;
public class CardPayment extends Payment {
    private String cardLastFour, cardHolderName;
    public CardPayment(String paymentId,String orderId,String customerId,double amount,String status,String createdAt,String cardLastFour,String cardHolderName){
        super(paymentId,orderId,customerId,amount,status,createdAt); this.cardLastFour=cardLastFour; this.cardHolderName=cardHolderName;
    }
    @Override public String getPaymentType(){return "CARD";}
    @Override public String processPayment(){return "Card payment processed for card ending in "+cardLastFour;}
    @Override public String toFileString(){return getPaymentId()+","+getOrderId()+","+getCustomerId()+","+getAmount()+","+getStatus()+","+getCreatedAt()+",CARD,"+cardLastFour+","+cardHolderName;}
    public String getCardLastFour(){return cardLastFour;} public String getCardHolderName(){return cardHolderName;}
}
