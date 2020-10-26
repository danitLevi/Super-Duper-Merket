package DtoObjects;

import java.util.Set;

public class OrderSummaryDto {
    private final String orderDate;
    private final int orderXCoordinate;
    private final int orderYCoordinate;

    private final Set<OneStoreInOrderSummaryDto> storesInOrderSummary;

    private final double itemsTotalPrice;
    private final double deliveryTotalPrice;
    private final double orderTotalPrice;

    public OrderSummaryDto(String orderDate, int orderXCoordinate, int orderYCoordinate, Set<OneStoreInOrderSummaryDto> storesInOrderSummary, double itemsTotalPrice, double deliveryTotalPrice, double orderTotalPrice) {
        this.orderDate = orderDate;
        this.orderXCoordinate = orderXCoordinate;
        this.orderYCoordinate = orderYCoordinate;
        this.storesInOrderSummary = storesInOrderSummary;
        this.itemsTotalPrice = itemsTotalPrice;
        this.deliveryTotalPrice = deliveryTotalPrice;
        this.orderTotalPrice = orderTotalPrice;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public int getOrderXCoordinate() {
        return orderXCoordinate;
    }

    public int getOrderYCoordinate() {
        return orderYCoordinate;
    }

    public Set<OneStoreInOrderSummaryDto> getStoresInOrderSummary() {
        return storesInOrderSummary;
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
}
