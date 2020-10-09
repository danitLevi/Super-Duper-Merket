package superDuperMarket;

import DtoObjects.OfferDto;
import DtoObjects.SaleDto;
import generatedClassesJaxb.SDMDiscount;

import java.util.ArrayList;
import java.util.List;

public class Sale {
    private String name;
    private SalesIfYouBuy ifYouBuy;
    private SalesThenYouGet thenYouGet;

    public Sale(SDMDiscount sdmDiscount) {
        this.name = sdmDiscount.getName();
        this.ifYouBuy = new SalesIfYouBuy(sdmDiscount.getIfYouBuy()) ;
        this.thenYouGet =new SalesThenYouGet( sdmDiscount.getThenYouGet());
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public SalesIfYouBuy getIfYouBuy() {
        return ifYouBuy;
    }

    public void setIfYouBuy(SalesIfYouBuy ifYouBuy) {
        this.ifYouBuy = ifYouBuy;
    }

    public SalesThenYouGet getThenYouGet() {
        return thenYouGet;
    }

    public void setThenYouGet(SalesThenYouGet thenYouGet) {
        this.thenYouGet = thenYouGet;
    }

    public boolean isItemInSale(int ItemId) {
        return ( (this.ifYouBuy.getItemId()==ItemId)|| isItemInSaleOffers(ItemId));
    }

    private boolean isItemInSaleOffers(int ItemId)
    {
        List<SalesOffer> offers=this.thenYouGet.getOffers();
        for (SalesOffer currOffer : offers)
        {
            if(currOffer.getItemId()==ItemId)
            {
                return true;
            }
        }
        return false;
    }

    public SaleDto getSaleDetails()
    {
        List<SalesOffer> offers=this.thenYouGet.getOffers();
        List<OfferDto> currSaleOffersDetail=new ArrayList<>();

        for (SalesOffer currOffer : offers)
        {
            OfferDto currOferDetails=currOffer.getOfferDetails();
            currSaleOffersDetail.add(currOferDetails);
        }
        return new SaleDto(name,this.ifYouBuy.getQuantity(),this.ifYouBuy.getItemId(),currSaleOffersDetail,this.thenYouGet.getOperator());

    }
}
