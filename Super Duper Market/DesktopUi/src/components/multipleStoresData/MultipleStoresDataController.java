
package components.multipleStoresData;

import DtoObjects.StoreDto;
import components.oneStoreData.OneStoreDataConstants;
import components.oneStoreData.OneStoreDataController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import superDuperMarket.LogicInterface;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

public class MultipleStoresDataController {

    @FXML
    private VBox storesVbox;


    @FXML
    private FlowPane oneStoreDataPane;

    private LogicInterface sdmLogic;

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }


    //TODO: CHECK WHAT TO DO WITH EXCEPTION !!
    public void showData() {

        Set<StoreDto> storesInSystemData= sdmLogic.getStoresDetails();
        for (StoreDto currStore :storesInSystemData) {

            try {
                createStoreTile(currStore);

            } catch (IOException e) {
                //todo
                e.printStackTrace();

            }
        }
    }

    public void createStoreTile(StoreDto storeData) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL oneStoreDataUrl = getClass().getResource(OneStoreDataConstants.ONE_STORE_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(oneStoreDataUrl);

        BorderPane oneStoreDataBorderPane=loader.load();
        //oneStoreDataBorderPane.getStyleClass().setAll();

        OneStoreDataController oneStoreDataController=loader.getController();
        //oneStoreDataController.setMultipleStoresDataController(this);

        oneStoreDataController.setSdmLogic(this.sdmLogic);
        oneStoreDataController.setOneStoreDataPane(this.oneStoreDataPane);

        //todo: add data to tile

        storesVbox.getChildren().add(oneStoreDataBorderPane);
        oneStoreDataController.setData(storeData);


    }

    public FlowPane getOneStoreDataPane() {
        return oneStoreDataPane;
    }

    public void setOneStoreDataPane(FlowPane oneStoreDataPane) {
        this.oneStoreDataPane = oneStoreDataPane;
    }
}



