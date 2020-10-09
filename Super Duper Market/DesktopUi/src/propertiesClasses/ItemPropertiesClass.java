package propertiesClasses;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class ItemPropertiesClass {

    private SimpleIntegerProperty itemId;
    private SimpleStringProperty name;
    private SimpleStringProperty purchaseCategory;
    private SimpleStringProperty price;

    public ItemPropertiesClass(int itemId, String name, String purchaseCategory, String price) {
        this.itemId =new SimpleIntegerProperty(itemId);
        this.name = new SimpleStringProperty( name);
        this.purchaseCategory =new SimpleStringProperty( purchaseCategory);
        this.price = new SimpleStringProperty(price);

    }

    public int getItemId() {
        return itemId.get();
    }

    public SimpleIntegerProperty itemIdProperty() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId.set(itemId);
    }

    public String getName() {
        return name.get();
    }

    public SimpleStringProperty nameProperty() {
        return name;
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public String getPurchaseCategory() {
        return purchaseCategory.get();
    }

    public SimpleStringProperty purchaseCategoryProperty() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(String purchaseCategory) {
        this.purchaseCategory.set(purchaseCategory);
    }

    public String getPrice() {
        return price.get();

    }

    public SimpleStringProperty priceProperty() {
        return price;
    }

    public void setPrice(String price) {
        this.price.set(price);
    }
}
