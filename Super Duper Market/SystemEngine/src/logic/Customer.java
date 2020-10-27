package logic;

import java.util.*;

public class Customer extends User{

//<<<<<<< HEAD
    //todo: change to map of <Integer,Integer>  :(regionName , order id)
//    private Set<Integer> orderIds;
//=======
    //private Set<Integer> orderIds; //TODO: CHANGE TO MAP
//    private Map<Integer, String> orderIdToRegionName;
    private Map<String , List<Integer>> regionNameToOrdersIdsLst;
//>>>>>>> develop

    public Customer(String name) {
        super(name);
        //this.orderIds = new HashSet<>();
        this.regionNameToOrdersIdsLst = new HashMap<>();
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
        if(!regionNameToOrdersIdsLst.containsKey(regionName))
        {
            regionNameToOrdersIdsLst.put(regionName,new ArrayList<>());
        }
        regionNameToOrdersIdsLst.get(regionName).add(orderId);

//        regionNameToOrdersIdsLst.put(orderId,regionName);
    }


    public Map<String, List<Integer>> getRegionNameToOrdersIdsLst() {
        return regionNameToOrdersIdsLst;
    }

    public void setRegionNameToOrdersIdsLst(Map<String, List<Integer>> regionNameToOrdersIdsLst) {
        this.regionNameToOrdersIdsLst = regionNameToOrdersIdsLst;
    }

    public List<Integer> getOrdersIdInRegion(String regionName)
    {
        List<Integer> ordersIdsInRegion=new ArrayList<>();

        if(regionNameToOrdersIdsLst.containsKey(regionName))
        {
            ordersIdsInRegion=regionNameToOrdersIdsLst.get(regionName);
        }
        return ordersIdsInRegion;
    }
    public void chargeBalance(double amountToAdd, Date chargeDate)
    {
        getAccount().addToBalance(amountToAdd,chargeDate,TransactionType.CHARGE);
    }

    public void pay(double amountToPay,Date date)
    {
        getAccount().payFromBalance(amountToPay,date);
    }

}
