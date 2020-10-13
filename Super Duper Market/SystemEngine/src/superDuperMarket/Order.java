package superDuperMarket;

import logic.Customer;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

public class Order implements Serializable
{
    private LocalDate date;
    private  int id;
    private Coordinate orderLocation;
    Map<Integer,StoreOrder> storeIdToStoreOrder;
    private static int nextId=0;
    boolean isDynamicOrder;
    Customer customer;
    private String regionName;

    public Order(LocalDate date, boolean isDynamicOrder, Customer customer,Coordinate orderLocation, String regionName) {
        this.date = date;
        nextId++;
        this.id = nextId;
        this.orderLocation=orderLocation;
        this.storeIdToStoreOrder = new HashMap<>();
        this.isDynamicOrder=isDynamicOrder;
        this.customer=customer;

        customer.addOrder(id, regionName);
    }
    public static void setNextId(int nextId) {
        Order.nextId = nextId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coordinate getOrderLocation() {
        return orderLocation;
    }

    public void setOrderLocation(Coordinate orderLocation) {
        this.orderLocation = orderLocation;
    }

    public Map<Integer, StoreOrder> getStoreIdToStoreOrder() {
        return storeIdToStoreOrder;
    }

    public void setStoreIdToStoreOrder(Map<Integer, StoreOrder> storeIdToStoreOrder) {
        this.storeIdToStoreOrder = storeIdToStoreOrder;
    }

    public static int getNextId() {
        return nextId;
    }
//
//    public OrderTypes getOrderType() {
//        return orderType;
//    }
//
//    public void setOrderType(OrderTypes orderType) {
//        this.orderType = orderType;
//    }

    public int getAmountOfOrderedItemsByUits(Map<Integer, Item> itemIdToItemsInSystem)
    {
        int itemsAmount=0;
        for (StoreOrder currStoreOrder : storeIdToStoreOrder.values())
        {
            itemsAmount+=currStoreOrder.getAmountOfOrderedItemsByUnits(itemIdToItemsInSystem);
        }
        return  itemsAmount;
    }

    public int getAmountOfOrderedItemsTypes()
    {
        int itemsTypesAmount=0;
        for (StoreOrder currStoreOrder : storeIdToStoreOrder.values())
        {
            itemsTypesAmount+=currStoreOrder.getItemsTypesNum();
        }
        return  itemsTypesAmount;
    }

    public int getStoresInOrderAmount()
    {
        return (storeIdToStoreOrder.size());
    }

    public double getItemsInOrderPrice()
    {
        double itemsPrice=0;
        for (StoreOrder currStoreOrder : storeIdToStoreOrder.values())
        {
            itemsPrice+=currStoreOrder.getItemsTotalPrice();
        }
        return  itemsPrice;
    }

    public double getOrderDeliveryPrice()
    {
        double deliveryPrice=0;
        for (StoreOrder currStoreOrder : storeIdToStoreOrder.values())
        {
            deliveryPrice+=currStoreOrder.getDeliveryPrice(orderLocation);
        }
        return  deliveryPrice;
    }

    public double getOrderTotalPrice()
    {
        double oderPrice=0;
        for (StoreOrder currStoreOrder : storeIdToStoreOrder.values())
        {
            oderPrice+=currStoreOrder.getTotalPrice(orderLocation);
        }
        return  oderPrice;
    }

    public Store getOneStoreInOrder()
    {

        if(storeIdToStoreOrder.size()==1)
        {
            for (StoreOrder currStoreOrder:storeIdToStoreOrder.values()) // map storeIdToStoreOrder contain only one store
            {
                return (currStoreOrder.getStore());
            }
        }
       return  null;
    }

    public void addStoreOrder(int storeId,StoreOrder storeOrder)
    {
        storeIdToStoreOrder.put(storeId,storeOrder);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public boolean isDynamicOrder() {
        return isDynamicOrder;
    }

    public void setDynamicOrder(boolean dynamicOrder) {
        isDynamicOrder = dynamicOrder;
    }
}
