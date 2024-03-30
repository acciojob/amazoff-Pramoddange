package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class OrderRepository {

    private HashMap<String, Order> orderMap;
    private HashMap<String, DeliveryPartner> partnerMap;
    private HashMap<String, HashSet<String>> partnerToOrderMap;
    private HashMap<String, String> orderToPartnerMap;

    public OrderRepository(){
        this.orderMap = new HashMap<String, Order>();
        this.partnerMap = new HashMap<String, DeliveryPartner>();
        this.partnerToOrderMap = new HashMap<String, HashSet<String>>();
        this.orderToPartnerMap = new HashMap<String, String>();
    }

    public void saveOrder(Order order){
        // your code here
        String id=order.getId();
        orderMap.put(id,order);
    }

    public void savePartner(String partnerId){
        // your code here
        // create a new partner with given partnerId and save it
        DeliveryPartner deliveryPartner=new DeliveryPartner();
          deliveryPartner.setId(partnerId);
          partnerMap.put(partnerId,deliveryPartner);
    }

    public void saveOrderPartnerMap(String orderId, String partnerId){
        if(orderMap.containsKey(orderId) && partnerMap.containsKey(partnerId)){
            // your code here
            //add order to given partner's order list
            //increase order count of partner
            //assign partner to this order

            HashSet<String> partnerOrders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());

            // Update the set of orders assigned to the given partner
            partnerOrders.add(orderId);
            partnerToOrderMap.put(partnerId, partnerOrders);
            // Update the order-to-partner mapping
            orderToPartnerMap.put(orderId, partnerId);
        }
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
        HashSet<String> partnerOrders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());
        return partnerOrders.size();
    }

    public List<String> findOrdersByPartnerId(String partnerId){
        // your code here
        HashSet<String> partnerOrders = partnerToOrderMap.getOrDefault(partnerId, new HashSet<>());
        return new ArrayList<>(partnerOrders);
    }

    public List<String> findAllOrders(){
        // your code here
        // return list of all orders
        return new ArrayList<>(orderMap.keySet());
    }

    public void deletePartner(String partnerId){
        // your code here
        // delete partner by ID
        partnerMap.remove(partnerId);
        // Remove partner from order-to-partner mapping
        orderToPartnerMap.values().removeIf(partner -> partner.equals(partnerId));
        // Remove partner's orders
        partnerToOrderMap.remove(partnerId);
    }

    public void deleteOrder(String orderId){
        // your code here
        // delete order by ID
        orderMap.remove(orderId);
        // Remove order from order-to-partner mapping
        orderToPartnerMap.remove(orderId);
        // Remove order from partner's order list
        partnerToOrderMap.values().forEach(orders -> orders.remove(orderId));
    }

    public Integer findCountOfUnassignedOrders(){
        // your code here
        int unassignedCount = 0;
        for (String orderId : orderMap.keySet()) {
            if (!orderToPartnerMap.containsKey(orderId)) {
                unassignedCount++;
            }
        }
        return unassignedCount;
    }

    public Integer findOrdersLeftAfterGivenTimeByPartnerId(String timeString, String partnerId){
        // your code here
        int count = 0;
        // Assuming the format of timeString is "HH:MM"
        String[] timeParts = timeString.split(":");
        int givenHour = Integer.parseInt(timeParts[0]);
        int givenMinute = Integer.parseInt(timeParts[1]);

        for (String orderId : partnerToOrderMap.getOrDefault(partnerId, new HashSet<>())) {
            Order order = orderMap.get(orderId);
            int deliveryTime = order.getDeliveryTime();
            int deliveryHour = deliveryTime / 60; // Extracting hour
            int deliveryMinute = deliveryTime % 60; // Extracting minute

            if (deliveryHour > givenHour || (deliveryHour == givenHour && deliveryMinute > givenMinute)) {
                count++;
            }
        }
        return count;
    }

    public String findLastDeliveryTimeByPartnerId(String partnerId){
        // your code here
        // code should return string in format HH:MM
        String lastDeliveryTime = "00:00";
        for (String orderId : partnerToOrderMap.getOrDefault(partnerId, new HashSet<>())) {
            Order order = orderMap.get(orderId);
            int deliveryTime = order.getDeliveryTime();
            int deliveryHour = deliveryTime / 60; // Extracting hour
            int deliveryMinute = deliveryTime % 60; // Extracting minute

            String[] lastDeliveryTimeParts = lastDeliveryTime.split(":");
            int lastHour = Integer.parseInt(lastDeliveryTimeParts[0]);
            int lastMinute = Integer.parseInt(lastDeliveryTimeParts[1]);

            if (deliveryHour > lastHour || (deliveryHour == lastHour && deliveryMinute > lastMinute)) {
                lastDeliveryTime = String.valueOf(order.getDeliveryTime());
            }
        }
        return lastDeliveryTime;
    }
    }
