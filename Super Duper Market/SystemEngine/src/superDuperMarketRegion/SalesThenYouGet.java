package superDuperMarketRegion;

import generatedClassesJaxb.SDMOffer;
import generatedClassesJaxb.ThenYouGet;

import java.util.ArrayList;
import java.util.List;

public class SalesThenYouGet {


    private List<SalesOffer> offers;

    private String operator;

    public SalesThenYouGet(ThenYouGet thenYouGet) {
        offers =new ArrayList<>();
        this.operator = thenYouGet.getOperator();
        List<SDMOffer> sdmOffers= thenYouGet.getSDMOffer();
        SalesOffer currOffer;
        for (SDMOffer currSdmOffer:sdmOffers)
        {
            currOffer=new SalesOffer(currSdmOffer);
            offers.add(currOffer);
        }
    }

    public List<SalesOffer> getOffers() {
        return offers;
    }

    public void setOffers(List<SalesOffer> offers) {
        this.offers = offers;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
