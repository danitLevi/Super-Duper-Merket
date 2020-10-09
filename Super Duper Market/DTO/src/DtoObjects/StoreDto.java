package DtoObjects;

import java.util.Set;

public class StoreDto {

    private final int id;
    private final String name;
    private final int deliveryPpk;
    private final double paymentForDeliveries;
    private final int xCoordinate;
    private final int yCoordinate;
    Set<ItemInStoreDto> itemsInStore;
    private  final  int ordersAmount;
    private  final Set<SaleDto> sales;


    public StoreDto(int id, String name, int deliveryPpk, double paymentForDeliveries, int xCoordinate, int yCoordinate, Set<ItemInStoreDto> itemsInStore, int ordersAmount,Set<SaleDto> sales) {
        this.id = id;
        this.name = name;
        this.deliveryPpk = deliveryPpk;
        this.paymentForDeliveries = paymentForDeliveries;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.itemsInStore = itemsInStore;
        this.ordersAmount = ordersAmount;
        this.sales=sales;

    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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

    public Set<ItemInStoreDto> getItemsInStore() {
        return itemsInStore;
    }

    public int getOrdersAmount() {
        return ordersAmount;
    }

    public Set<SaleDto> getSales() {
        return sales;
    }
}
