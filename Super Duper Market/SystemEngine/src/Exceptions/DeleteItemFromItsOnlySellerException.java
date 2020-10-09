package Exceptions;

public class DeleteItemFromItsOnlySellerException extends Exception {
    private final int itemId;

    public DeleteItemFromItsOnlySellerException(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
