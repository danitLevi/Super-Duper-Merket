package logic;

import DtoObjects.TransactionDto;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Customer extends User{

    //todo: change to map of <Integer,Integer>  :(regionName , order id)
    private Set<Integer> orderIds;

    public Customer(String name) {
        super(name);
        this.orderIds = new HashSet<>();
    }

    public Set<Integer> getOrderIds() {
        return orderIds;
    }

    public void setOrderIds(Set<Integer> orderIds) {
        this.orderIds = orderIds;
    }

    public void addOrder(int orderId)
    {
        orderIds.add(orderId);
    }

    public void chargeBalance(double amountToAdd, Date chargeDate)
    {
        getAccount().addToBalance(amountToAdd,chargeDate);
    }



}
