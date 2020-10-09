package components.oneStoreData;

import DtoObjects.ItemInStoreDto;
import DtoObjects.SaleDto;
import DtoObjects.StoreDto;
import DtoObjects.StoreOrderDto;
import components.itemsInStoreData.ItemsInStoreConstants;
import components.itemsInStoreData.ItemsInStoreController;
import components.sale.SaleConstants;
import components.sale.SaleController;
import components.storeOrderHistory.StoreOrderHistoryConstants;
import components.storeOrderHistory.StoreOrderHistoryController;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Font;
import superDuperMarket.LogicInterface;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class OneStoreDataController {
    @FXML
    private Label storeNameHeaderLbl;

    @FXML
    private Label idLbl;

    @FXML
    private Label ppkLbl;

    @FXML
    private Label delivriesProfitLbl;
    //MultipleStoresDataController multipleStoresDataController;
    @FXML
    private FlowPane oneStoreDataPane;

    private SimpleIntegerProperty storeIdProperty;

    private LogicInterface sdmLogic;
//    private SimpleStringProperty storeNameProperty;

    public OneStoreDataController()
    {
        this.storeIdProperty=new SimpleIntegerProperty();
//        this.storeNameProperty=new SimpleStringProperty();
    }

    @FXML
    public void initialize()
    {
        idLbl.textProperty().bind(storeIdProperty.asString());

//        storeNameHeaderLbl.textProperty().bind(storeNameProperty);
    }
    public void setData(StoreDto storeDetails)
    {
        storeIdProperty.set(storeDetails.getId());
        storeNameHeaderLbl.setText(storeDetails.getName());

        ppkLbl.setText(String.valueOf(storeDetails.getDeliveryPpk())+" ₪");
        delivriesProfitLbl.setText(String.format("%.2f ₪",storeDetails.getPaymentForDeliveries()));
    }

    @FXML
    void showStoreItems(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL itemsBorderPaneUrl = getClass().getResource(ItemsInStoreConstants.ITEMS_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(itemsBorderPaneUrl);

        BorderPane itemsDataBorderPane=loader.load();
       oneStoreDataPane.getChildren().setAll(itemsDataBorderPane);

        ItemsInStoreController itemsInStoreController =loader.getController();

        // todo: required ??maybe send only needed data ?
        Set<ItemInStoreDto> itemsSet= sdmLogic.getStoreItemsDetails(this.storeIdProperty.get());
        itemsInStoreController.showData(storeNameHeaderLbl.getText(),itemsSet);
    }



    //todo: handle exception
    @FXML
    void showSales(ActionEvent event) throws IOException {

        oneStoreDataPane.getChildren().clear();
        StoreDto currStoreDetails=sdmLogic.getSpecificStoreDetails(this.storeIdProperty.get());
        Set<SaleDto> saleDetails=currStoreDetails.getSales();

        if(saleDetails.size()==0)
        {
            FXMLLoader loader = new FXMLLoader();
            URL saleTileUrl = getClass().getResource(SaleConstants.NO_SALE_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(saleTileUrl);
            BorderPane noSaleDataBorderPane=loader.load();
            oneStoreDataPane.getChildren().add(noSaleDataBorderPane);

        }
        else
        {
            for (SaleDto currSaleDetails:saleDetails)
            {
                FXMLLoader loader = new FXMLLoader();
                URL saleTileUrl = getClass().getResource(SaleConstants.SALE_FXML_RESOURCE_IDENTIFIER);
                loader.setLocation(saleTileUrl);
                BorderPane saleDataBorderPane=loader.load();
                oneStoreDataPane.getChildren().add(saleDataBorderPane);
                SaleController saleController =loader.getController();
                saleController.setSdmLogic(this.sdmLogic);
                saleController.showData(currSaleDetails);

            }
        }

    }
    @FXML
    void showStoreOrdersHistory(ActionEvent event) throws IOException {
        // todo: required ??maybe send only needed data ?
        Set<StoreOrderDto> storeOrdersSet= sdmLogic.getStoreOrderDetails(storeIdProperty.getValue());

        if(storeOrdersSet.size() != 0) {
            FXMLLoader loader = new FXMLLoader();
            URL itemsBorderPaneUrl = getClass().getResource(StoreOrderHistoryConstants.STORE_ORDER_HISTORY_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(itemsBorderPaneUrl);

            BorderPane StoreOrderHistoryBorderPane=loader.load();
            oneStoreDataPane.getChildren().setAll(StoreOrderHistoryBorderPane);

            StoreOrderHistoryController storeOrderHistoryController =loader.getController();
            storeOrderHistoryController.showData(storeNameHeaderLbl.getText(), storeOrdersSet);
        }
        else
        {
            Label noOrders = new Label("No Orders Found");
            noOrders.setFont(new Font(18));
            oneStoreDataPane.getChildren().setAll(noOrders);
        }
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    public FlowPane getOneStoreDataPane() {
        return oneStoreDataPane;
    }

    public void setOneStoreDataPane(FlowPane oneStoreDataPane) {
        this.oneStoreDataPane = oneStoreDataPane;
    }
    //todo: useful to update item price ?

//
//
//    /**
//     * This is a convenience method that subclasses can call upon
//     * to fire property changes back to the models. This method
//     * uses reflection to inspect each of the model classes
//     * to determine whether it is the owner of the property
//     * in question. If it isn't, a NoSuchMethodException is thrown,
//     * which the method ignores.
//     *
//     * @param propertyName = The name of the property.
//     * @param newValue = An object that represents the new value
//     * of the property.
//     */
//    protected void setModelProperty(String propertyName, Object newValue) {
//
//        for (AbstractModel model: registeredModels) {
//            try {
//
//                Method method = model.getClass().
//                        getMethod("set"+propertyName, new Class[] {
//                                        newValue.getClass()
//                                }
//
//
//                        );
//                method.invoke(model, newValue);
//
//            } catch (Exception ex) {
//                //  Handle exception.
//            }
//        }
//    }

}
