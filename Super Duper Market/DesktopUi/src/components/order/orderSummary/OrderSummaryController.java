package components.order.orderSummary;

import DtoObjects.*;
import components.storeInOrderHistory.StoreInOrderHistoryConstants;
import components.storeInOrderHistory.StoreInOrderHistoryController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class OrderSummaryController {


    @FXML
    private FlowPane storesInOrderPane;
    @FXML
    private Label itemsCostLbl;

    @FXML
    private Label DeliveryCostLbl;

    @FXML
    private Label TotalCostLbl;
    @FXML
    private Button cancelBtn;

    @FXML
    private Button confirmBtn;
    @FXML
    private Label customerLbl;

    @FXML
    private Label dateLbl;

    private SimpleDoubleProperty wantedOrderItemsCost;
    private SimpleDoubleProperty wantedOrderDeliveryCost;
    private  SimpleDoubleProperty wantedOrderTotalCost;

    private LogicInterface sdmLogic;
    private OrderInputDto orderInputDetails;
    private Map<Integer, Map<OfferDto,Integer>> storeIdToItemsToOrderFromSales ;

    public OrderSummaryController() {
        this.wantedOrderItemsCost =new SimpleDoubleProperty(0);
        this.wantedOrderDeliveryCost = new SimpleDoubleProperty(0);
        this.wantedOrderTotalCost = new SimpleDoubleProperty(0);
    }

    @FXML
    public void initialize()
    {
        itemsCostLbl.textProperty().bind(Bindings.createStringBinding( ()->String.format("%.2f ₪",wantedOrderItemsCost.get()) ,wantedOrderItemsCost  ));
        DeliveryCostLbl.textProperty().bind(Bindings.createStringBinding( ()->String.format("%.2f ₪",wantedOrderDeliveryCost.get()) ,wantedOrderDeliveryCost  ));
        TotalCostLbl.textProperty().bind(Bindings.createStringBinding( ()->String.format("%.2f ₪",wantedOrderTotalCost.get()) ,wantedOrderTotalCost  ));

    }

    public void showData(OrderInputDto orderInputDetails, Map<Integer, Map<OfferDto,Integer>> storeIdToItemsToOrderFromSales )
    {
        this.orderInputDetails=orderInputDetails;
        this.storeIdToItemsToOrderFromSales=storeIdToItemsToOrderFromSales;
        for (Integer currStoreId: orderInputDetails.getOrderMinimalPriceBag().keySet())
        {
            Map<OfferDto,Integer> itemsFromSalesInStore;
            if(!storeIdToItemsToOrderFromSales.containsKey(currStoreId))
            {
                itemsFromSalesInStore=null;
            }
            else
            {
                itemsFromSalesInStore=storeIdToItemsToOrderFromSales.get(currStoreId);
            }
            showStoreOrderDetails(currStoreId,itemsFromSalesInStore);
        }

        this.wantedOrderTotalCost.setValue(this.wantedOrderDeliveryCost.get()+this.wantedOrderItemsCost.get());

        customerLbl.setText(this.orderInputDetails.getCustomerDetails());
        dateLbl.setText(String.valueOf(this.orderInputDetails.getDate()));

    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    public void showStoreOrderDetails(int storeId, Map<OfferDto,Integer> itemsFromSalesInStore)
    {
        try
        {
            Set<ItemInStoreOrderDto> itemsToOrderDetails=this.sdmLogic.getWantedItemsInStoreDetails(storeId,orderInputDetails.getOrderMinimalPriceBag().get(storeId),itemsFromSalesInStore);
            StoreDto storeDetails=this.sdmLogic.getStoreDetails(storeId);
            double distanceFromCustomer=this.sdmLogic.getStoreDistanceFromCustomer(storeId,orderInputDetails.getCustomerId());
            double deliveryCost=this.sdmLogic.getStoreDeliveryCostToCustomer(storeId,orderInputDetails.getCustomerId());

            //todo: border or scroll pane?
            BorderPane storeInOrderBorderPane=null;
            FXMLLoader loader = new FXMLLoader();
            URL storeInOrderUrl = getClass().getResource(StoreInOrderHistoryConstants.STORE_IN_ORDER_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(storeInOrderUrl);
            storeInOrderBorderPane = loader.load();
            StoreInOrderHistoryController storeInOrderHistoryController = loader.getController();

            storeInOrderHistoryController.showDataInOrderSummary(storeDetails,distanceFromCustomer,deliveryCost,itemsToOrderDetails);
            storesInOrderPane.getChildren().add(storeInOrderBorderPane);

            updateWantedOrderItemsCost(itemsToOrderDetails);
            this.wantedOrderDeliveryCost.setValue(this.wantedOrderDeliveryCost.getValue() + deliveryCost);

        }
        catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }


    private void updateWantedOrderItemsCost(Set<ItemInStoreOrderDto> itemsToOrderDetails)
    {
        for (ItemInStoreOrderDto currItemDetails:itemsToOrderDetails)
            this.wantedOrderItemsCost.setValue(wantedOrderItemsCost.getValue()+currItemDetails.getTotalPrice());
    }

    public double getWantedOrderItemsCost() {
        return wantedOrderItemsCost.get();
    }

    public SimpleDoubleProperty wantedOrderItemsCostProperty() {
        return wantedOrderItemsCost;
    }

    public void setWantedOrderItemsCost(double wantedOrderItemsCost) {
        this.wantedOrderItemsCost.set(wantedOrderItemsCost);
    }

    public double getWantedOrderDeliveryCost() {
        return wantedOrderDeliveryCost.get();
    }

    public SimpleDoubleProperty wantedOrderDeliveryCostProperty() {
        return wantedOrderDeliveryCost;
    }

    public void setWantedOrderDeliveryCost(double wantedOrderDeliveryCost) {
        this.wantedOrderDeliveryCost.set(wantedOrderDeliveryCost);
    }

    public double getWantedOrderTotalCost() {
        return wantedOrderTotalCost.get();
    }

    public SimpleDoubleProperty wantedOrderTotalCostProperty() {
        return wantedOrderTotalCost;
    }

    public void setWantedOrderTotalCost(double wantedOrderTotalCost) {
        this.wantedOrderTotalCost.set(wantedOrderTotalCost);
    }

    public void disableButtons()
    {
        cancelBtn.setDisable(true);
        confirmBtn.setDisable(true);
    }

    @FXML
    void cancelOrder(ActionEvent event) {

        UtilsClass.showInformatioDialog("Order canceled successfully");
        disableButtons();
    }


    //todo !!
    @FXML
    void confirmOrder(ActionEvent event) {
        boolean isDynamicOrder=this.orderInputDetails.getOrderMinimalPriceBag().size()>1;
        this.sdmLogic.saveOrder(this.orderInputDetails.getDate(),
                this.orderInputDetails.getCustomerId(),
                isDynamicOrder,
                this.orderInputDetails.getOrderMinimalPriceBag(),
                this.storeIdToItemsToOrderFromSales);
        UtilsClass.showInformatioDialog("Order saved successfully");
        disableButtons();
    }




}

