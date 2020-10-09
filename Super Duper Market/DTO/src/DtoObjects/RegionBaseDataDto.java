package DtoObjects;

public class RegionBaseDataDto {

   private final String ownerName;
    private final String regionName;
    private final int itemsTypesAmount;

    private final int storesAmount;
    private final int ordersAmount;
    private final double orderAvgCost;

    public RegionBaseDataDto(String ownerName, String regionName, int itemsTypesAmount, int storesAmount, int ordersAmount, double orderAvgCost) {
        this.ownerName = ownerName;
        this.regionName = regionName;
        this.itemsTypesAmount = itemsTypesAmount;
        this.storesAmount = storesAmount;
        this.ordersAmount = ordersAmount;
        this.orderAvgCost = orderAvgCost;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public String getRegionName() {
        return regionName;
    }

    public int getItemsTypesAmount() {
        return itemsTypesAmount;
    }

    public int getStoresAmount() {
        return storesAmount;
    }

    public int getOrdersAmount() {
        return ordersAmount;
    }

    public double getOrderAvgCost() {
        return orderAvgCost;
    }
}
