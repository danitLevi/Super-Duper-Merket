package Exceptions;

public class DeleteStoreOnlyItemException extends  Exception {
    private final int itemId;

    public DeleteStoreOnlyItemException(int itemId) {
        this.itemId = itemId;
    }

    public int getItemId() {
        return itemId;
    }
}
