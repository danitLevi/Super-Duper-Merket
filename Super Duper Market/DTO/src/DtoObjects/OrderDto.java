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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrderDto {

    private final LocalDate date;
    private final double itemsTotalAmount;
    private final double itemsTotalPrice;
    private final double deliveryTotalPrice;
    private final double orderTotalPrice;
    private int orderId;
    private final int amountOfItemsTypes; //TODO: DEL
    private final Map<Integer,StoreOrderDto> storeIdToStoreOrder;
    private final List<ItemInStoreOrderDto> itemsInOrder;
    private final int numOfStores;
    private final int xCoordinate;
    private final int yCoordinate;

    public OrderDto(LocalDate date, double itemsTotalAmount, double itemsTotalPrice, double deliveryTotalPrice,
                    double orderTotalPrice, int orderId, int amountOfItemsTypes,
                    Map<Integer, StoreOrderDto> storeIdToStoreOrder,
                    int xCoordinate, int yCoordinate) {
        this.date = date;
        this.itemsTotalAmount = itemsTotalAmount;
        this.itemsTotalPrice = itemsTotalPrice;
        this.deliveryTotalPrice = deliveryTotalPrice;
        this.orderTotalPrice = orderTotalPrice;
        this.orderId = orderId;
        this.amountOfItemsTypes = amountOfItemsTypes;
        this.storeIdToStoreOrder = storeIdToStoreOrder;
        this.itemsInOrder = getItemsInStoreOrderDetails();
        this.numOfStores = storeIdToStoreOrder.size();
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    public List<ItemInStoreOrderDto> getItemsInOrder() { return itemsInOrder; }

    public List<ItemInStoreOrderDto> getItemsInStoreOrderDetails()
    {
        List<ItemInStoreOrderDto> itemsInOrder = new ArrayList<>();

        for (StoreOrderDto currStoreOrder : storeIdToStoreOrder.values())
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

    public LocalDate getDate() {
        return date;
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

    public int getAmountOfItemsTypes() {
        return amountOfItemsTypes;
    }

    public Map<Integer, StoreOrderDto> getStoreIdToStoreOrder() {
        return storeIdToStoreOrder;
    }
}
