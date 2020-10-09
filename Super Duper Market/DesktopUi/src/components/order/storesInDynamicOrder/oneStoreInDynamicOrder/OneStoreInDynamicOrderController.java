package components.order.storesInDynamicOrder.oneStoreInDynamicOrder;


import DtoObjects.StoreInCalcDyanmicOrderDto;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OneStoreInDynamicOrderController {

    @FXML
    private Label nameLbl;

    @FXML
    private Label IDLbl;

    @FXML
    private Label locationLbl;

    @FXML
    private Label ppkLbl;

    @FXML
    private Label distanceLbl;

    @FXML
    private Label typesLbl;

    @FXML
    private Label deliveryPriceLbl;

    @FXML
    private Label itemsPriceLbl;

public void showData(StoreInCalcDyanmicOrderDto storeDetails)
{
    nameLbl.setText(storeDetails.getName());
    IDLbl.setText(String.valueOf(storeDetails.getId()));
    locationLbl.setText("("+storeDetails.getxCoordinate()+","+storeDetails.getyCoordinate()+")");
    ppkLbl.setText(String.format("%d ₪",storeDetails.getDeliveryPpk()))    ;
    distanceLbl.setText(String.format("%.2f km" ,storeDetails.getDistanceFromCustomer()));
    deliveryPriceLbl.setText(String.format("%.2f ₪" ,storeDetails.getDeliveryPrice()));
    typesLbl.setText(String.valueOf(storeDetails.getAmountOfItemsTypes()));
    itemsPriceLbl.setText(String.format("%.2f ₪" , storeDetails.getItemsTotalPrice()));


}


}

