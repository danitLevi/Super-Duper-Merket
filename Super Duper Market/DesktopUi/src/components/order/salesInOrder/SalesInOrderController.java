package components.order.salesInOrder;

import DtoObjects.OfferDto;
import DtoObjects.OrderInputDto;
import DtoObjects.SaleDto;
import components.order.orderSummary.OrderSummaryConstants;
import components.order.orderSummary.OrderSummaryController;
import components.order.salesInOrder.oneStoreSalesInOrder.OneStoreSalesInOrderConstants;
import components.order.salesInOrder.oneStoreSalesInOrder.OneStoreSalesInOrderController;
import components.sale.SaleConstants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SalesInOrderController {
    @FXML
    private FlowPane storesPane;

    @FXML
    private Label headerLbl;
    @FXML
    private BorderPane salesInOrderBorderPane;

    private LogicInterface sdmLogic;
    private Map<Integer, Map<OfferDto,Integer>> storeIdToStoreUsedSalesOffers;
    private BorderPane primaryPane;
    private OrderInputDto orderInputDetails;

    public SalesInOrderController() {
        this.storeIdToStoreUsedSalesOffers = new HashMap<>();
    }

    public void showData(OrderInputDto orderInputDetails)
    {
        this.orderInputDetails=orderInputDetails;
        Map<Integer, Map<Integer, Double>> orderMinimalPriceBag=orderInputDetails.getOrderMinimalPriceBag();
        boolean isSaleExsist=false;
        Map<SaleDto,Integer> saleDetailsToSaleApplrovalAmountInStore=new HashMap<>();
        for (Integer currStoreId:orderMinimalPriceBag.keySet() )        {

            saleDetailsToSaleApplrovalAmountInStore = sdmLogic.getStoreSalesInOrder(currStoreId,orderMinimalPriceBag.get(currStoreId));
            if(saleDetailsToSaleApplrovalAmountInStore.size()>0)
            {
                showSalesInOneStore(currStoreId,saleDetailsToSaleApplrovalAmountInStore );
                isSaleExsist=true;
            }
        }

        if(!isSaleExsist)
        {
            showNoSales();
        }
    }

    public void showNoSales() {
        try {
            headerLbl.setVisible(false);
            FXMLLoader loader = new FXMLLoader();
            URL saleTileUrl = getClass().getResource(SaleConstants.NO_SALE_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(saleTileUrl);
            BorderPane noSaleDataBorderPane= loader.load();

            salesInOrderBorderPane.setCenter(noSaleDataBorderPane);
        }
        catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

    public void showSalesInOneStore(int storeId,Map<SaleDto,Integer> salesInStoreDetailsToApprovalAmountToUse)
    {
        try {
        FXMLLoader loader = new FXMLLoader();
        URL dataUrl = getClass().getResource(OneStoreSalesInOrderConstants.ONE_STORE_SALE_IN_ORDER_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(dataUrl);
        BorderPane storePane=loader.load();
        storesPane.getChildren().add(storePane);

        OneStoreSalesInOrderController controller =loader.getController();
        controller.setSdmLogic(this.sdmLogic);
        controller.setParentController(this);
//        controller.setPrimaryPane(storesPane);
        controller.showData(storeId,salesInStoreDetailsToApprovalAmountToUse);

        storeIdToStoreUsedSalesOffers.put(storeId,controller.getUsedOffersInStoreToAmount());

        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    public Map<Integer, Map<OfferDto, Integer>> getStoreIdToStoreUsedSalesOffers() {
        return storeIdToStoreUsedSalesOffers;
    }

    public void setStoreIdToStoreUsedSalesOffers(Map<Integer, Map<OfferDto, Integer>> storeIdToStoreUsedSalesOffers) {
        this.storeIdToStoreUsedSalesOffers = storeIdToStoreUsedSalesOffers;
    }

    public void removeStoreFromSalesdetails(Pane storeToRemovePane)
    {
        storesPane.getChildren().remove(storeToRemovePane);
    }

    public void checkLeftSales()
    {
        boolean isEmpty=storesPane.getChildren().isEmpty();
        if(isEmpty)
        {
            showNoSales();
            UtilsClass.showInformatioDialog("You have no more sales to use. please press continue button to continue your order");

        }
    }
    @FXML
    void continueToOrderSummarize(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL orderDataUrl = getClass().getResource(OrderSummaryConstants.ORDER_SUMMARY_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(orderDataUrl);

            ScrollPane orderSummaryScrollPane= null;

            orderSummaryScrollPane = loader.load();

            primaryPane.setCenter(orderSummaryScrollPane);

            OrderSummaryController controller =loader.getController();
            controller.setSdmLogic(this.sdmLogic);
            controller.showData(this.orderInputDetails,storeIdToStoreUsedSalesOffers);

        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

    public BorderPane getPrimaryPane() {
        return primaryPane;
    }

    public void setPrimaryPane(BorderPane primaryPane) {
        this.primaryPane = primaryPane;
    }
}

