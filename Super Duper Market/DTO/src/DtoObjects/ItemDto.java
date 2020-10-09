

package DtoObjects;

public class ItemDto {

    private final int id;
    private final String name;
   // private PurchaseCategory purchaseCategory;
    private final PurchaseCategory purchaseCategory;

    public ItemDto(int id, String name, PurchaseCategory purchaseCategory) {
        this.id = id;
        this.name = name;
        this.purchaseCategory = purchaseCategory;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }
}
