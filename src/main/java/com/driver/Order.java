package com.driver;

public class Order {

    private String id;
    private int deliveryTime;

    private DeliveryPartner deliveryPartner;

    public Order(String id, String deliveryTime)
    {

        // The deliveryTime has to converted from string to int and then stored in the attribute
        //deliveryTime  = HH*60 + MM
        String arr[]=deliveryTime.split(":");
        this.deliveryTime=60*Integer.parseInt(arr[0])+Integer.parseInt(arr[1]);
        this.id=id;
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