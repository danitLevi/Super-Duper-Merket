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
import java.util.Map;

public class OrderDto {

    private final LocalDate date;
    private final double itemsTotalAmount;
    private final double itemsTotalPrice;
    private final double deliveryTotalPrice;
    private final double orderTotalPrice;
    private int orderId;
    private final int amountOfItemsTypes;
    private final Map<Integer,StoreOrderDto> storeIdToStoreOrder;

    public OrderDto(LocalDate date, double itemsTotalAmount, double itemsTotalPrice, double deliveryTotalPrice, double orderTotalPrice, int orderId, int amountOfItemsTypes, Map<Integer,StoreOrderDto> storeIdToStoreOrder)
        {
            this.date = date;
            this.itemsTotalAmount = itemsTotalAmount;
            this.itemsTotalPrice = itemsTotalPrice;
            this.deliveryTotalPrice = deliveryTotalPrice;
            this.orderTotalPrice = orderTotalPrice;
            this.orderId = orderId;
            this.amountOfItemsTypes = amountOfItemsTypes;
            this.storeIdToStoreOrder=storeIdToStoreOrder;
        }

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
