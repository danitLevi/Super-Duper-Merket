package DtoObjects;

public class ItemInSystemDto extends  ItemDto {

    private final int numOfSellers;
    private final double avgPrice;
    private final double numOfPurchases;

    public ItemInSystemDto(int id, String name, PurchaseCategory purchaseCategory, int numOfSellers, double avgPrice, double numOfPurchases) {
        super(id, name, purchaseCategory);
        this.numOfSellers = numOfSellers;
        this.avgPrice = avgPrice;
        this.numOfPurchases = numOfPurchases;
    }

    public int getNumOfSellers() {
        return numOfSellers;
    }

    public double getAvgPrice() {
        return avgPrice;
    }

    public double getNumOfPurchases() {
        return numOfPurchases;
    }
}
