package logic;

import java.util.HashSet;
import java.util.Set;

public class Customer extends User{

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

    public void chargeBalance(double anountToAdd)
    {
        getAccount().addToBalance(anountToAdd);
    }

    public double getCurrBalance()
    {
        return getAccount().getBalance();
    }

}
