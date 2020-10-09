package components.ordersHistory;

import DtoObjects.OrderDto;
import DtoObjects.StoreOrderDto;
import components.storeInOrderHistory.StoreInOrderHistoryConstants;
import components.storeInOrderHistory.StoreInOrderHistoryController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Accordion;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.Map;
import java.util.Set;

public class OrdersHistoryController {

    private LogicInterface sdmLogic;

    @FXML
    private BorderPane ordersHisstoryBorderPane;

    public void showData()
    {
        Set<OrderDto> ordersDetails=sdmLogic.getOrdersDetails();
        if(!ordersDetails.isEmpty())
        {
            Accordion ordersAccordion=new Accordion();
            ordersHisstoryBorderPane.setCenter(ordersAccordion);

            for (OrderDto currOrder : ordersDetails)
            {
                TitledPane currOrderTiteledPane=new TitledPane();
                currOrderTiteledPane.setText("Order " + currOrder.getOrderId());

                FlowPane storesInOrderFlowPane=new FlowPane();
                //todo: fix (to be 2 tiles in same row)
                storesInOrderFlowPane.setHgap(20);
                storesInOrderFlowPane.setVgap(20);

                ScrollPane scrollPane=new ScrollPane();
                scrollPane.setContent(storesInOrderFlowPane);
                currOrderTiteledPane.setContent(scrollPane);

                Map<Integer, StoreOrderDto> storeIdToStoreOrder=currOrder.getStoreIdToStoreOrder();
                for (Integer storeId:storeIdToStoreOrder.keySet())
                {

                    BorderPane borderPane=showStoreInOrderTile(storeId ,storeIdToStoreOrder.get(storeId));
                    storesInOrderFlowPane.getChildren().add(borderPane);
                }

                ordersAccordion.getPanes().add(currOrderTiteledPane);
            }
        }

    }


    public BorderPane showStoreInOrderTile(Integer storeId,StoreOrderDto storeOrderDetails){

        BorderPane storeInOrderBorderPane=null;
        try {
            FXMLLoader loader = new FXMLLoader();
            URL storeInOrderUrl = getClass().getResource(StoreInOrderHistoryConstants.STORE_IN_ORDER_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(storeInOrderUrl);
            storeInOrderBorderPane = loader.load();
            StoreInOrderHistoryController storeInOrderHistoryController = loader.getController();
            storeInOrderHistoryController.showData(sdmLogic.getStoreDetails(storeId),storeOrderDetails);

        }
        catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
//
//            storeInOrderBorderPane=loader.load();

        return  storeInOrderBorderPane;
    }
    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }


}
