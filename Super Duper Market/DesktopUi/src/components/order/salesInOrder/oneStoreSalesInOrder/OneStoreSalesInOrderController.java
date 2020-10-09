package components.order.salesInOrder.oneStoreSalesInOrder;

import DtoObjects.OfferDto;
import DtoObjects.SaleDto;
import components.order.salesInOrder.SalesInOrderController;
import components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder.oneSaleInOneStoreInOrderConstants;
import components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder.oneSaleInOneStoreInOrderController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OneStoreSalesInOrderController {


    @FXML
    private Label storeNameLbl;
    @FXML
    private FlowPane salesFlowPane;
    @FXML
    private BorderPane oneStoreInSaleBorderPane;

    private SalesInOrderController parentController;

    private Map<OfferDto,Integer> usedOffersInStoreToAmount;
    private  LogicInterface sdmLogic;
//    private Pane primaryPane;

    public void showData (int storeId,Map<SaleDto,Integer> salesInStoreDetailsToApprovalUsesAmount)
    {
        storeNameLbl.setText(this.sdmLogic.getStoreName(storeId));

        for(SaleDto currSaleDetails:salesInStoreDetailsToApprovalUsesAmount.keySet())
        {
            ShowSaleInStore(currSaleDetails ,(int)salesInStoreDetailsToApprovalUsesAmount.get(currSaleDetails) );
        }
    }

    public OneStoreSalesInOrderController() {
        this.usedOffersInStoreToAmount = new HashMap<>();
    }

    public Map<OfferDto, Integer> getUsedOffersInStoreToAmount() {
        return usedOffersInStoreToAmount;
    }

    public void setUsedOffersInStoreToAmount(Map<OfferDto, Integer> usedOffersInStoreToAmount) {
        this.usedOffersInStoreToAmount = usedOffersInStoreToAmount;
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    public void addOfferToUsedOffersInStore(OfferDto offerDto)
    {
        if(usedOffersInStoreToAmount.containsKey(offerDto))
        {
            Integer amount=usedOffersInStoreToAmount.get(offerDto)+1;
            usedOffersInStoreToAmount.put(offerDto,amount);
        }
        else
        {
            usedOffersInStoreToAmount.put(offerDto,1);
        }

    }

    public void addOffersToUsedOffersInStore(Collection<OfferDto> offersDto)
    {
        for (OfferDto currOffer:offersDto )
        {
            addOfferToUsedOffersInStore(currOffer);
        }
    }

    @FXML
    public void ShowSaleInStore(SaleDto currSaleDetails, int approvalUsesAmount)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL dataUrl = getClass().getResource(oneSaleInOneStoreInOrderConstants.ONE_SALE_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(dataUrl);
            BorderPane salePane=loader.load();
//            storesPane.getChildren().add(salePane);
            oneSaleInOneStoreInOrderController controller =loader.getController();
            controller.setSdmLogic(this.sdmLogic);
            controller.setParentController(this);
            controller.showData(currSaleDetails,approvalUsesAmount );

            salesFlowPane.getChildren().add(salePane);

            controller.approvalAmountToUsePropertyProperty().addListener((obs, oldValue, newValue) ->
            {
                if(newValue.intValue()==0)
                    salesFlowPane.getChildren().remove(salePane);
                if(salesFlowPane.getChildren().isEmpty())
                {
                    parentController.removeStoreFromSalesdetails(oneStoreInSaleBorderPane);
//                    primaryPane.getChildren().remove(oneStoreInSaleBorderPane);
//                    primaryPane.getChildren().remove(salesFlowPane);
                }
                parentController.checkLeftSales();
//                if(parentController.isStoresPaneEmpty())
//                {
//                    UtilsClass.showInformatioDialog("You have no more sales to use. please press continue button to continue your order");
//                    parentController.showNoSales();
//                }


            });


        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

//    public Pane getPrimaryPane() {
//        return primaryPane;
//    }
//
//    public void setPrimaryPane(Pane primaryPane) {
//        this.primaryPane = primaryPane;
//    }

    public SalesInOrderController getParentController() {
        return parentController;
    }

    public void setParentController(SalesInOrderController parentController) {
        this.parentController = parentController;
    }
}

