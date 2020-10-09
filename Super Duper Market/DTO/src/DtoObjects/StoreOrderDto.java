package DtoObjects;

import java.time.LocalDate;
import java.util.Set;

public class StoreOrderDto {

    private final LocalDate date;
    private final double itemsTotalAmount;
    private final double itemsTotalPrice;
    private final double deliveryTotalPrice;
    private final double orderTotalPrice;
    private final boolean isDynamicOrder;
    private final int orderId;
    Set<ItemInStoreOrderDto> itemsInStoreOrderDetails;
    private final double distanceFromCustomer;


    public StoreOrderDto(LocalDate date, double itemsTotalAmount, double itemsTotalPrice, double deliveryTotalPrice, double orderTotalPrice, boolean isDynamicOrder, int orderId, Set<ItemInStoreOrderDto> itemsInStoreOrderDetails, double distanceFromCustomer) {
        this.date = date;
        this.itemsTotalAmount = itemsTotalAmount;
        this.itemsTotalPrice = itemsTotalPrice;
        this.deliveryTotalPrice = deliveryTotalPrice;
        this.orderTotalPrice = orderTotalPrice;
        this.isDynamicOrder = isDynamicOrder;
        this.orderId = orderId;
        this.itemsInStoreOrderDetails = itemsInStoreOrderDetails;
        this.distanceFromCustomer = distanceFromCustomer;
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

    public boolean isDynamicOrder() {
        return isDynamicOrder;
    }

    public int getOrderId() {
        return orderId;
    }

    public Set<ItemInStoreOrderDto> getItemsInStoreOrderDetails() {
        return itemsInStoreOrderDetails;
    }

    public double getDistanceFromCustomer() {
        return distanceFromCustomer;
    }


}
