package DtoObjects;

import java.util.Date;
import java.util.Map;
import java.util.Set;

public class OrderSummaryDto {

    private int storeId;
    private String storeName;
    private int ppk;
    private double distance;
    private double totalOrderPrice;
    Set<ItemInStoreOrderDto> ItemInStoreOrder;

    public OrderSummaryDto(int storeId, String storeName, int ppk, double distance, double totalOrderPrice, Set<ItemInStoreOrderDto> itemInStoreOrder) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.ppk = ppk;
        this.distance = distance;
        this.totalOrderPrice = totalOrderPrice;
        ItemInStoreOrder = itemInStoreOrder;
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

    public double getTotalOrderPrice() {
        return totalOrderPrice;
    }

    public Set<ItemInStoreOrderDto> getItemInStoreOrder() {
        return ItemInStoreOrder;
    }
}
