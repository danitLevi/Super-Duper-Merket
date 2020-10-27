package DtoObjects;

public class ItemInStoreOrderDto extends  ItemDto {

  private final double amount;
  private final double unitPrice;
  private final double totalPrice;
  private final boolean isFromSale;
  private final String storeName;
  private final int storeId;

    public ItemInStoreOrderDto(int id, String name, PurchaseCategory purchaseCategory, double amount,
                               double unitPrice, double totalPrice, boolean isFromSale, String storeName,int storeId)
    {
        super(id, name, purchaseCategory);
        this.amount = amount;
        this.unitPrice = unitPrice;
        this.totalPrice = totalPrice;
        this.isFromSale = isFromSale;
        this.storeName = storeName;
        this.storeId=storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public double getAmount() {
        return amount;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public boolean isFromSale() {
        return isFromSale;
    }

    public int getStoreId() {
        return storeId;
    }
}
