package components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder.selectOffer;

import DtoObjects.ItemDto;
import DtoObjects.OfferDto;
import DtoObjects.PurchaseCategory;
import components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder.oneSaleInOneStoreInOrderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.util.ArrayList;
import java.util.List;

public class SelectOfferController {


    @FXML
    private ComboBox<String > offersComboBox;

    private oneSaleInOneStoreInOrderController parentController;
    private LogicInterface sdmLogic;
    private List<OfferDto> offersToGet;
    private Stage primaryStage;

    public void showData(List<OfferDto> offersToGet)
    {
        this.offersToGet=offersToGet;
        List<String> offersDetailsInComboBox=new ArrayList<>();
        for (OfferDto currOfferDetails: offersToGet)
        {
            String str="";
            ItemDto itemToGetDetails=sdmLogic.getItemDetails(currOfferDetails.getItemId());
            String itemName=itemToGetDetails.getName() ;
            PurchaseCategory purchaseCategory= itemToGetDetails.getPurchaseCategory();
            if(purchaseCategory==PurchaseCategory.Weight)
            {
                str+=String.format("Get %.2f KG of %s",currOfferDetails.getQuantity(),itemName)
                   +" - "+currOfferDetails.getForAdditional() +"₪ per KG";

            }
            else
            {
                str+=String.format("%d units of %s",(int)currOfferDetails.getQuantity(),itemName)
                    +" - "+currOfferDetails.getForAdditional() +"₪ per unit";

            }
            offersDetailsInComboBox.add(str);
        }
        offersComboBox.getItems().addAll( offersDetailsInComboBox);

    }

    public oneSaleInOneStoreInOrderController getParentController() {
        return parentController;
    }

    public void setParentController(oneSaleInOneStoreInOrderController parentController) {
        this.parentController = parentController;
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    @FXML
    void selectOption(ActionEvent event) {

        if(offersComboBox.getSelectionModel().isEmpty())
        {
            UtilsClass.showErrorAlert("Invalid input!\nPlease choose an option from the combo box ");
        }
        else
        {
            OfferDto selectedOffer= offersToGet.get(offersComboBox.getSelectionModel().getSelectedIndex());
            primaryStage.close();
            parentController.saveUsedSaleOffer(selectedOffer);

        }

    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}

