package superDuperMarket;

import generatedClassesJaxb.IfYouBuy;

public class SalesIfYouBuy {
    private double quantity;
    private int itemId;

    public SalesIfYouBuy(IfYouBuy ifYouBuy) {
        this.quantity = ifYouBuy.getQuantity();
        this.itemId = ifYouBuy.getItemId();
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
}
