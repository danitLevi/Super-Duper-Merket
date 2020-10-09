package Exceptions;

public class ItemNotFoundInStoresException extends Exception{
    private  int itemNotFoundId;

    public ItemNotFoundInStoresException(int itemNotFoundId) {
        this.itemNotFoundId = itemNotFoundId;
    }

    public int getItemNotFoundId() {
        return itemNotFoundId;
    }

    public void setItemNotFoundId(int itemNotFoundId) {
        this.itemNotFoundId = itemNotFoundId;
    }
}
