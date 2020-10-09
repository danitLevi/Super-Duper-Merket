package superDuperMarket;

import DtoObjects.OfferDto;
import generatedClassesJaxb.SDMOffer;

public class SalesOffer {

    private double quantity;

    private int itemId;

    private int forAdditional;

    public SalesOffer(SDMOffer sdmOffer) {
        this.quantity = sdmOffer.getQuantity();
        this.itemId = sdmOffer.getItemId();
        this.forAdditional = sdmOffer.getForAdditional();
    }

    public SalesOffer(double quantity, int itemId, int forAdditional) {
        this.quantity = quantity;
        this.itemId = itemId;
        this.forAdditional = forAdditional;
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

    public int getForAdditional() {
        return forAdditional;
    }

    public void setForAdditional(int forAdditional) {
        this.forAdditional = forAdditional;
    }

    public OfferDto getOfferDetails()
    {
       return new OfferDto(this.quantity,this.itemId,this.forAdditional);

    }
}
