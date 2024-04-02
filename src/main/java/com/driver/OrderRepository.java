package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, List<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    private List<String >orderAssign=new ArrayList<>();
    private List<String>notAssignOrder=new ArrayList<>();

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, List<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        String id=order.getId();
        orderMap.put(id,order);
        orderAssign.add(id);
        notAssignOrder.add(id);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner deliveryPartner=new DeliveryPartner(partnerId);
         // deliveryPartner.setId(partnerId);
          partnerMap.put(partnerId,deliveryPartner);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        List<String>order;
        if(!partnerToOrderMap.containsKey(partnerId)) {
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order
            order = new ArrayList<>();
        }else {
            order = partnerToOrderMap.get(partnerId);
        }
           order.add(orderId);

            // Update the set of orders assigned to the given partne
            partnerToOrderMap.put(partnerId, order);
            DeliveryPartner deliveryPartner=partnerMap.get(partnerId);
            deliveryPartner.setNumberOfOrders(deliveryPartner.getNumberOfOrders()+1);
            partnerMap.put(partnerId,deliveryPartner);
            notAssignOrder.remove(orderId);
            // Update the order-to-partner mapping


    }


    public Order findOrderById(String orderId){
        // your code here
        return orderMap.get(orderId);

    }

    public DeliveryPartner findPartnerById(String partnerId){
        // your code here
        return partnerMap.get(partnerId);
    }

    public Integer findOrderCountByPartnerId(String partnerId){
        // your code here
        if(!partnerToOrderMap.containsKey(partnerId)){
            return 0;
        }
        return partnerToOrderMap.get(partnerId).size();
    }

    public List<String> findOrdersByPartnerId(String partnerId) {
        // your code here

        return partnerToOrderMap.get(partnerId);
    }
    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
       List<String>ans=new ArrayList<>();
       for(String key:orderMap.keySet()){
           Order order=orderMap.get(key);
           ans.add(order.getId());
       }
       return ans;
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID

        // Remove partner from order-to-partner mapping

        // Remove partner's orders
        if(!partnerMap.containsKey(partnerId)){
            return;
        }
        List<String>orderList=partnerToOrderMap.get(partnerId);
        partnerMap.remove(partnerId);
         partnerToOrderMap.remove(partnerId);
         if(orderList==null||orderList.size()==0){
             return;
         }
         for(String key:orderList){
             orderAssign.remove(key);
             notAssignOrder.add(key);
         }
    }

    public void deleteOrder(String orderId) {
        // your code here
        // delete order by ID

        // Remove order from order-to-partner mapping
        for (List<String> arr : partnerToOrderMap.values()) {
            if (arr.contains(orderId)) {
                arr.remove(orderId);
                break;
            }
        }
        orderMap.remove(orderId);
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here

        return notAssignOrder.size();
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId){
        // your code here
        List<String>orders=partnerToOrderMap.get(partnerId);
        // Assuming the format of timeString is "HH:MM"
           if(orders==null)return 0;
           String arr[]=time.split(":");
        int givenHour = 60*Integer.parseInt(arr[0]);
        int givenMinute = Integer.parseInt(arr[1]);
      int totalTime=givenMinute+givenHour;
       int count=0;
        for (String id : orders) {
            Order order = orderMap.get(id);

            if (totalTime<order.getDeliveryTime()) {
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        List<String>orders=partnerToOrderMap.get(partnerId);
        if(orders==null){
            return  null;
        }
        int time=0;

        for (String key:orders) {
            Order order = orderMap.get(key);

            if (time < order.getDeliveryTime()) {
                time = order.getDeliveryTime();
            }
        }
        int mm=time%60;
        String mM=(mm>=0&&mm<=9)?("0"+mm):(mm+"");
        int hh=(time-mm)/60;
        String hH=(hh>=0&&hh<=9)?("0"+hh):(hh+"");


        return hH+":"+mM;
    }
    }
