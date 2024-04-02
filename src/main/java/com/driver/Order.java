package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    private DeliveryPartner deliveryPartner;
    public Order(String id, String deliveryTime) {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        this.id=id;
        String arr[]=deliveryTime.split(":");
        int hourMinute=Integer.getInteger(arr[0])*60;
        int minute=Integer.getInteger(arr[1]);
        this.deliveryTime=hourMinute+minute;
    }

    public String getId() {
        return id;
    }

    public int getDeliveryTime() {return deliveryTime;}

    public DeliveryPartner getDeliveryPartner() {
        return deliveryPartner;
    }

    public void setDeliveryPartner(DeliveryPartner deliveryPartner) {
        this.deliveryPartner = deliveryPartner;
    }
}
