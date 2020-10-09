package Exceptions;

public class ItemInSaleNotFoundInStoreException extends Exception {

    String storeName;
    int itemId ;

    public ItemInSaleNotFoundInStoreException(String storeName, int itemId) {
        this.storeName = storeName;
        this.itemId = itemId;
    }

    public String getStoreName() {
        return storeName;
    }

    public int getItemId() {
        return itemId;
    }
}
