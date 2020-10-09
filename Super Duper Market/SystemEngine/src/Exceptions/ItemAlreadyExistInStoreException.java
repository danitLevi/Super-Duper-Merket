package Exceptions;

public class ItemAlreadyExistInStoreException extends  Exception{

    private final int itemId;
    private final String storeName;

    public ItemAlreadyExistInStoreException(int itemId, String storeName) {
        this.itemId = itemId;
        this.storeName = storeName;
    }

    public int getItemId() {
        return itemId;
    }

    public String getStoreName() {
        return storeName;
    }


}
