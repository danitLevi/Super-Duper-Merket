package logic;

import DtoObjects.OrderDto;
import DtoObjects.TransactionDto;
import superDuperMarket.Order;
import superDuperMarket.Region;

import java.util.*;

public class Customer extends User{

    //private Set<Integer> orderIds; //TODO: CHANGE TO MAP
    private Map<Integer, String> orderIdToRegionName;

    public Customer(String name) {
        super(name);
        //this.orderIds = new HashSet<>();
        this.orderIdToRegionName = new HashMap<>();
    }

//    public Set<Integer> getOrderIds() { return orderIds; }
//
//    public void setOrderIds(Set<Integer> orderIds) {
//        this.orderIds = orderIds;
//    }
//
    public void addOrder(int orderId, String regionName)
    {
       // orderIds.add(orderId);
        orderIdToRegionName.put(orderId,regionName);
    }


    public Map<Integer, String> getOrderIdToRegionName() {
        return orderIdToRegionName;
    }

    public void setOrderIdToRegionName(Map<Integer, String> orderIdToRegionName) {
        this.orderIdToRegionName = orderIdToRegionName;
    }

    public void chargeBalance(double amountToAdd, Date chargeDate)
    {
        getAccount().addToBalance(amountToAdd,chargeDate);
    }

    public List<TransactionDto> getTransactionsDetails()
    {
        return this.getAccount().getTransactionsDetails();
    }
}
