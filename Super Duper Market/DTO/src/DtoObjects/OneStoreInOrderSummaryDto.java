package DtoObjects;

import java.util.Set;

public class OneStoreInOrderSummaryDto {

    private int storeId;
    private String storeName;
    private int ppk;
    private double distance;
    private double deliveryPrice;

    Set<ItemInStoreOrderDto> itemsInStoreOrderDetails;

    public OneStoreInOrderSummaryDto(int storeId, String storeName, int ppk, double distance, double deliveryPrice, Set<ItemInStoreOrderDto> itemsInStoreOrderDetails) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.ppk = ppk;
        this.distance = distance;
        this.deliveryPrice = deliveryPrice;
        this.itemsInStoreOrderDetails = itemsInStoreOrderDetails;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getPpk() {
        return ppk;
    }

    public double getDistance() {
        return distance;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public Set<ItemInStoreOrderDto> getItemsInStoreOrderDetails() {
        return itemsInStoreOrderDetails;
    }
}
