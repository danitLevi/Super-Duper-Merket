package propertiesClasses;

import javafx.beans.property.SimpleDoubleProperty;

public class ItemToOrderPropertiesClass extends ItemPropertiesClass {

    private SimpleDoubleProperty amount;

    public ItemToOrderPropertiesClass(int itemId, String name, String purchaseCategory) {
        super(itemId, name, purchaseCategory,"NO VALUE");
        this.amount = new SimpleDoubleProperty(0);
    }

    public ItemToOrderPropertiesClass(int itemId, String name, String purchaseCategory,String price) {
        super(itemId, name, purchaseCategory,price);
        this.amount = new SimpleDoubleProperty(0);
    }

    public double getAmount() {
        return amount.get();
    }

    public SimpleDoubleProperty amountProperty() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount.set(amount);
    }
}
