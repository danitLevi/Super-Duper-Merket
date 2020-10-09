package components.order.storesInDynamicOrder;


import DtoObjects.OrderInputDto;
import DtoObjects.StoreInCalcDyanmicOrderDto;
import components.order.OrderController;
import components.order.storesInDynamicOrder.oneStoreInDynamicOrder.OneStoreInDynamicOrderConstants;
import components.order.storesInDynamicOrder.oneStoreInDynamicOrder.OneStoreInDynamicOrderController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class StoresInDynamicOrderController {

    @FXML
    private FlowPane storesPane;

    private OrderController parentController;
    private  OrderInputDto orderInputDetails;

    public void showData(Set<StoreInCalcDyanmicOrderDto> storesInDynamicOrderDetails , OrderInputDto orderInputDetails)
    {
        this.orderInputDetails=orderInputDetails;
        for (StoreInCalcDyanmicOrderDto storeDetails:storesInDynamicOrderDetails )
        {
            showStoreTile(storeDetails);
        }
    }

    private void showStoreTile(StoreInCalcDyanmicOrderDto storeDetails)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL saleTileUrl = getClass().getResource(OneStoreInDynamicOrderConstants.one_STORE_IN_DYNAMIC_ORDER_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(saleTileUrl);
            BorderPane borderPane= loader.load();
            storesPane.getChildren().add(borderPane);

            OneStoreInDynamicOrderController controller =loader.getController();
//            saleController.setSdmLogic(this.sdmLogic);
            controller.showData(storeDetails);
        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }


    @FXML
    void continueToSales(ActionEvent event) {
        parentController.showSalesInOrder(this.orderInputDetails);
    }

    public OrderController getParentController() {
        return parentController;
    }

    public void setParentController(OrderController parentController) {
        this.parentController = parentController;
    }
}

