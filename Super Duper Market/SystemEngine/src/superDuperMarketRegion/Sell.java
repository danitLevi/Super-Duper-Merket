//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a>
// Any modifications to this file will be lost upon recompilation of the source schema.
// Generated on: 2020.08.09 at 11:30:54 AM IDT
//


package superDuperMarketRegion;


import DtoObjects.PurchaseCategory;
import generatedClassesJaxb.SDMSell;

import java.io.Serializable;

public class Sell implements Serializable
{
    private double price;
    private int itemId;
    private PurchaseCategory purchaseCategory;

    public Sell(SDMSell sdmSell , PurchaseCategory purchaseCategory )
    {
        setItemId(sdmSell.getItemId());
        setPrice(sdmSell.getPrice());
        setPurchaseCategory(purchaseCategory);
    }

    public Sell(double price, int itemId) {
        this.price = price;
        this.itemId = itemId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public PurchaseCategory getPurchaseCategory() {
        return purchaseCategory;
    }

    public void setPurchaseCategory(PurchaseCategory purchaseCategory) {
        this.purchaseCategory = purchaseCategory;
    }
}


