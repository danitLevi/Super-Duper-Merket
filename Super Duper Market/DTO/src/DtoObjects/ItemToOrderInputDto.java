package DtoObjects;

public class ItemToOrderInputDto {

    final private int itemId;
    final private double quantity;

    public ItemToOrderInputDto(int itemId, double quantity) {
        this.itemId = itemId;
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public double getQuantity() {
        return quantity;
    }
}
