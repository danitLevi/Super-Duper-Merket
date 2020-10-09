package DtoObjects;

public class ItemInStoreDto extends  ItemDto {
    private final double price;
    private final double amountOfpurchasesInStore;

    public ItemInStoreDto(int id, String name, PurchaseCategory purchaseCategory, double price, double amountOfpurchasesInStore) {
        super(id, name, purchaseCategory);
        this.price = price;
        this.amountOfpurchasesInStore = amountOfpurchasesInStore;
    }

    public double getPrice() {
        return price;
    }

    public double getAmountOfpurchasesInStore() {
        return amountOfpurchasesInStore;
    }
}
