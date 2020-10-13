package DtoObjects;

public class ItemInStoreOrderDto extends  ItemDto {

  private final double amount;
  private final double price;
  private final double totalPrice;
  private final boolean isFromSale; // todo
  private final String storeName;

    public ItemInStoreOrderDto(int id, String name, PurchaseCategory purchaseCategory, double amount,
                               double price, double totalPrice, boolean isFromSale, String storeName)
    {
        super(id, name, purchaseCategory);
        this.amount = amount;
        this.price = price;
        this.totalPrice = totalPrice;
        this.isFromSale = isFromSale;
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public double getAmount() {
        return amount;
    }

    public double getPrice() {
        return price;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isFromSale() {
        return isFromSale;
    }
}
