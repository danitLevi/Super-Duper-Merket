package DtoObjects;

//public class OrderDto extends StoreOrderDto{
//
////    private final int orderId;
//    private final int amountOfItemsTypes;
//
//
//    public OrderDto(Date date, double itemsTotalAmount, double itemsTotalPrice, double deliveryTotalPrice, double storeOrderTotalPrice, int orderId, int amountOfItemsTypes,boolean isDynamicOrder) {
//        super(date, itemsTotalAmount, itemsTotalPrice, deliveryTotalPrice, storeOrderTotalPrice,isDynamicOrder,orderId,null);
////        this.orderId = orderId;
//        this.amountOfItemsTypes = amountOfItemsTypes;
//    }
//
////    public int getOrderId() {
////        return orderId;
////    }
//
//    public int getAmountOfItemsTypes() {
//        return amountOfItemsTypes;
//    }

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class OrderDto {

    private final String strDate;
    private final double itemsTotalAmount;//
    private final double itemsTotalPrice;//
    private final double deliveryTotalPrice;//
    private final double orderTotalPrice;
    private int orderId; //
//    private final int amountOfItemsTypes; //TODO: DEL
    private final Map<Integer,StoreOrderDto> storeIdToStoreOrderDetails;
    private final List<ItemInStoreOrderDto> itemsInOrder;
    private final int numOfStores;
    private final int xCoordinate;
    private final int yCoordinate;
    private final String customerName; // for alert

    public OrderDto(Date date,
                    double itemsTotalAmount,
                    double itemsTotalPrice,
                    double deliveryTotalPrice,
                    double orderTotalPrice,
                    int orderId,
                    int amountOfItemsTypes,
                    Map<Integer, StoreOrderDto> storeIdToStoreOrderDetails,
                    int xCoordinate,
                    int yCoordinate,
                    String customerName) {

        String pattern = "dd/MM/yyyy";
        DateFormat df = new SimpleDateFormat(pattern);
        this.strDate = df.format(date);

        this.itemsTotalAmount = itemsTotalAmount;
        this.itemsTotalPrice = itemsTotalPrice;
        this.deliveryTotalPrice = deliveryTotalPrice;
        this.orderTotalPrice = orderTotalPrice;
        this.orderId = orderId;
//        this.amountOfItemsTypes = amountOfItemsTypes;
        this.storeIdToStoreOrderDetails = storeIdToStoreOrderDetails;
        this.itemsInOrder = getItemsInStoreOrderDetails();
        this.numOfStores = storeIdToStoreOrderDetails.size();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;

        this.customerName=customerName;
    }

    public List<ItemInStoreOrderDto> getItemsInOrder() { return itemsInOrder; }

    public List<ItemInStoreOrderDto> getItemsInStoreOrderDetails()
    {
        List<ItemInStoreOrderDto> itemsInOrder = new ArrayList<>();

        for (StoreOrderDto currStoreOrder : storeIdToStoreOrderDetails.values())
        {
            Set<ItemInStoreOrderDto> ItemInStoreOrderDtoSet = currStoreOrder.itemsInStoreOrderDetails;
            for (ItemInStoreOrderDto currItemInStoreOrderDto : ItemInStoreOrderDtoSet)
            {
                itemsInOrder.add(currItemInStoreOrderDto);
            }
        }
        return itemsInOrder;
    }

    public int getNumOfStores() { return numOfStores; }

    public int getxCoordinate() { return xCoordinate; }

    public int getyCoordinate() { return yCoordinate; }

    public String getStrDate() {
        return strDate;
    }

    public double getItemsTotalAmount() {
        return itemsTotalAmount;
    }

    public double getItemsTotalPrice() {
        return itemsTotalPrice;
    }

    public double getDeliveryTotalPrice() {
        return deliveryTotalPrice;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public int getOrderId() {
        return orderId;
    }

//    public int getAmountOfItemsTypes() {
//        return amountOfItemsTypes;
//    }

    public Map<Integer, StoreOrderDto> getStoreIdToStoreOrderDetails() {
        return storeIdToStoreOrderDetails;
    }

    public String getCustomerName() {
        return customerName;
    }
}
