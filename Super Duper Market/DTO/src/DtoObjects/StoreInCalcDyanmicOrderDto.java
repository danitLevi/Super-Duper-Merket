package DtoObjects;

public class StoreInCalcDyanmicOrderDto {

    private final int id;
    private final String name;
    private final int xCoordinate;
    private final int yCoordinate;
    private final double distanceFromOrder;
    private final int deliveryPpk;
    private final double deliveryPrice;
    private  final int amountOfItemsTypes;
    private final double itemsTotalPrice;

    public StoreInCalcDyanmicOrderDto(int id, String name, int xCoordinate, int yCoordinate, double distanceFromOrder, int deliveryPpk, double deliveryPrice, int amountOfItemsTypes, double itemsTotalPrice) {
        this.id = id;
        this.name = name;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.distanceFromOrder = distanceFromOrder;
        this.deliveryPpk = deliveryPpk;
        this.deliveryPrice = deliveryPrice;
        this.amountOfItemsTypes = amountOfItemsTypes;
        this.itemsTotalPrice = itemsTotalPrice;
    }



    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }

    public double getDistanceFromOrder() {
        return distanceFromOrder;
    }

    public int getDeliveryPpk() {
        return deliveryPpk;
    }

    public double getDeliveryPrice() {
        return deliveryPrice;
    }

    public int getAmountOfItemsTypes() {
        return amountOfItemsTypes;
    }

    public double getItemsTotalPrice() {
        return itemsTotalPrice;
    }
}
