package Exceptions;

public class StoreItemNotFoundInSystemException extends  Exception{

    private final int itemNotFoundId;
    private final String storeName;
    private boolean isInSale;

    public StoreItemNotFoundInSystemException(int itemNotFoundId, String storeName) {
        this.itemNotFoundId = itemNotFoundId;
        this.storeName = storeName;
        this.isInSale=false;
    }

    public StoreItemNotFoundInSystemException(int itemNotFoundId, String storeName, boolean isInSale) {
        this.itemNotFoundId = itemNotFoundId;
        this.storeName = storeName;
        this.isInSale=isInSale;
    }

    public int getItemNotFoundId() {
        return itemNotFoundId;
    }

    public String getStoreName() {
        return storeName;
    }

    public boolean isInSale() {
        return isInSale;
    }
}
