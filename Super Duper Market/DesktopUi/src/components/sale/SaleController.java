package components.sale;

import DtoObjects.ItemDto;
import DtoObjects.OfferDto;
import DtoObjects.PurchaseCategory;
import DtoObjects.SaleDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.util.List;

public class SaleController {

    private LogicInterface sdmLogic;

    @FXML
    private Text buyHeaderLbl;

    @FXML
        private Text buyLbl;

    @FXML
    private Text getHeaderLbl;
    @FXML
    private TextFlow itemToGetTextFlow;

    @FXML
    private Label saleNameLbl;



    public void showData(SaleDto currSaleDetails)
    {
        saleNameLbl.setText(currSaleDetails.getName());
        ItemDto itemToBuyDetails=sdmLogic.getItemDetails(currSaleDetails.getItemIdToBuy());

        double itemToBuyQuantity=currSaleDetails.getQuantityToBuy();
        printItemInSale(itemToBuyDetails,itemToBuyQuantity,buyLbl, UtilsClass.NO_VALUE);
        Text textToGet;
        Text textToGetOperator=new Text("");
        Text details=new Text("");

        List<OfferDto> offersDeails=currSaleDetails.getOffersToGet();
        String operatorDetails=currSaleDetails.getOptionToGet();

        if(operatorDetails.equals("ONE-OF"))
        {
            textToGetOperator.setText("ONE ");
            textToGetOperator.getStyleClass().setAll("BoldText");
            details.setText("of the following: \n");
            itemToGetTextFlow.getChildren().addAll(textToGetOperator,details);
        }
        else if(operatorDetails.equals("ALL-OR-NOTHING"))
        {
            textToGetOperator.setText("ALL ");
            textToGetOperator.getStyleClass().setAll("BoldText");
            details.setText("the following: \n");
            itemToGetTextFlow.getChildren().addAll(textToGetOperator,details);
        }


        for (OfferDto currOffer :offersDeails )
        {
            textToGet=new Text("");
            ItemDto itemToGetDetails=sdmLogic.getItemDetails(currOffer.getItemId());
            double itemToGetQuantity=currOffer.getQuantity();
            int additionalPrice=currOffer.getForAdditional();
            printItemInSale(itemToGetDetails,itemToGetQuantity,textToGet,additionalPrice);
            itemToGetTextFlow.getChildren().add(textToGet);
        }

    }

    public void printItemInSale(ItemDto itemDetails,double itemQuantity,Text textArea,int additionalPrice )
    {
        String str="";
        String itemName=itemDetails.getName() ;
        PurchaseCategory purchaseCategory= itemDetails.getPurchaseCategory();
        if(purchaseCategory==PurchaseCategory.Weight)
        {
            str+=String.format("• %.2f KG of %s",itemQuantity,itemName);
            if(additionalPrice!=UtilsClass.NO_VALUE)
            {
                str+=" - "+additionalPrice +"₪ per KG";
            }
        }
        else
        {
            str+=String.format("• %d units of %s",(int)itemQuantity,itemName);
            if(additionalPrice!=UtilsClass.NO_VALUE)
            {
                str+=" - "+additionalPrice +"₪ per unit";
            }
        }
        str+=" •\n";
        textArea.setText(str);
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }
}
