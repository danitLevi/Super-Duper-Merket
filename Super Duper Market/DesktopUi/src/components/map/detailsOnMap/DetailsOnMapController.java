package components.map.detailsOnMap;

import DtoObjects.CustomerDto;
import DtoObjects.StoreDto;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailsOnMapController {

    @FXML
    private Label idLbl;

    @FXML
    private Label nameLbl;

    @FXML
    private Label ordersAmountLbl;

    @FXML
    private Label ppkLbl;
    @FXML
    private Label ppkHeaderLbl;
    @FXML
    private Label headerLbl;
    @FXML
    private Label locationLbl;

    private SimpleBooleanProperty isStoreSelected;

    public DetailsOnMapController() {

        isStoreSelected = new SimpleBooleanProperty(false);
    }

    @FXML
    public void initialize()
    {

        ppkHeaderLbl.visibleProperty().bind(isStoreSelected);
        ppkLbl.visibleProperty().bind(isStoreSelected);

    }

    public void setStoreData(StoreDto storeDetails)
    {
        isStoreSelected.set(true);
        headerLbl.setText("Store details:");
        idLbl.setText(String.valueOf(storeDetails.getId()));
        nameLbl.setText(storeDetails.getName());
        ordersAmountLbl.setText(String.valueOf(storeDetails.getOrdersAmount()));
        ppkLbl.setText(String.valueOf(storeDetails.getDeliveryPpk()));
        locationLbl.setText(String.format("(%d,%d)",storeDetails.getxCoordinate(),storeDetails.getyCoordinate()));
    }

    public void setCustomerData(CustomerDto customerDetails)
    {
        isStoreSelected.set(false);
        headerLbl.setText("Customer details:");
        idLbl.setText(String.valueOf(customerDetails.getId()));
        nameLbl.setText(customerDetails.getName());
        ordersAmountLbl.setText(String.valueOf(customerDetails.getOrdersAmount()));
        locationLbl.setText(String.format("(%d,%d)",customerDetails.getxCoordinate(),customerDetails.getyCoordinate()));
    }
}
