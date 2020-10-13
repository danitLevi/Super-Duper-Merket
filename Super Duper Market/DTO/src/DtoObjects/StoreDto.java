package DtoObjects;

import java.util.List;
import java.util.Set;

public class StoreDto {

    private final int id;
    private final String name;
    private final String owner;
    private final int deliveryPpk;
    private final double paymentForDeliveries;
    private final double paymentForItems;
    private final int xCoordinate;
    private final int yCoordinate;
    List<ItemInStoreDto> itemsInStore;
    private  final  int ordersAmount;
    private  final Set<SaleDto> sales;


    public StoreDto(int id, String name, String owner, int deliveryPpk,
                    double paymentForDeliveries, double paymentForItems,
                    int xCoordinate, int yCoordinate, List<ItemInStoreDto> itemsInStore,
                    int ordersAmount, Set<SaleDto> sales) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.deliveryPpk = deliveryPpk;
        this.paymentForDeliveries = paymentForDeliveries;
        this.paymentForItems = paymentForItems;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.itemsInStore = itemsInStore;
        this.ordersAmount = ordersAmount;
        this.sales = sales;
    }

    public double getPaymentForItems() { return paymentForItems; }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getOwner() { return owner; }

    public int getDeliveryPpk() {
        return deliveryPpk;
    }

    public double getPaymentForDeliveries() {
        return paymentForDeliveries;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public List<ItemInStoreDto> getItemsInStore() {
        return itemsInStore;
    }

    public int getOrdersAmount() {
        return ordersAmount;
    }

    public Set<SaleDto> getSales() {
        return sales;
    }
}
