package components.map;

import DtoObjects.CustomerDto;
import DtoObjects.StoreDto;
import components.map.detailsOnMap.DetailsOnMapConstants;
import components.map.detailsOnMap.DetailsOnMapController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import superDuperMarket.LogicInterface;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

//todo: add loactions on map or in tooltip
public class MapController {

    @FXML
    private ImageView mapImage;
    @FXML
    private Group imagesGroup;
    @FXML
    private BorderPane mapBorderPane;

    @FXML
    private Pane mapPane;

    private LogicInterface sdmLogic;

    private final int oneCoordinateWidth=1000/50;
    private final int oneCoordinateHeight=1000/50;

    private int maxXval;
    private int maxYval;


    public MapController() {
        this.maxXval = 0;
        this.maxYval = 0;
    }

    public void showMap()
{
    Rectangle2D croppedPortion = new Rectangle2D(0,0, oneCoordinateWidth*(this.maxXval+1), oneCoordinateHeight*(this.maxYval+1));

    mapImage.setViewport(croppedPortion);

//    mapImage.setPreserveRatio(true);
}

public void insertDataToMap()
{
    insertStoresData();
    insertCustomersData();
    showMap();
}

public void insertStoresData()
{
    Set<StoreDto> storesDetails=sdmLogic.getStoresDetails();
    for (StoreDto currStoreDetails: storesDetails)
    {
        Button storeBtn=new Button();
        storeBtn.toFront();

        Image storeLocation=new Image(MapConstants.STORE_LOCATION_IMAGE);
        ImageView storeLocationView=new ImageView(storeLocation);
//
//        //todo: delete after resize image
        storeLocationView.setFitWidth(20);
        storeLocationView.setFitHeight(20);

        storeBtn.setGraphic(storeLocationView);
        storeBtn.getStyleClass().setAll("LocationBtn");

        //storeBtn.getGraphic().setBlendMode(BlendMode.MULTIPLY);

        // sub 1 from store/ customer location (start from (1,1))
        int storeX=currStoreDetails.getxCoordinate()-1;
        int storeY=currStoreDetails.getyCoordinate()-1;

        Tooltip tooltipLoc = new Tooltip("(" + (storeX+1) + "," + (storeY+1) + ")");
        storeBtn.setTooltip(tooltipLoc);

        imagesGroup.getChildren().add(storeBtn);
        storeBtn.setLayoutX(oneCoordinateWidth*storeX -10);
        storeBtn.setLayoutY(oneCoordinateHeight*storeY -20 + 90);

        if(storeX>this.maxXval)
        {
            this.maxXval=storeX;
        }
        if(storeY>this.maxYval)
        {
            this.maxYval=storeY;
        }
        storeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                showSelectedStoreData(currStoreDetails);

            }
        });
    }
}

public void insertCustomersData()
{
    Set<CustomerDto> customersDetails=sdmLogic.getCustomersDetails();
    for (CustomerDto currCustomerDetails: customersDetails)
    {
        Button storeBtn=new Button();
        storeBtn.toFront();
        Image storeLocation=new Image(MapConstants.CUSTOMER_LOCATION_IMAGE);
        ImageView customerLocationView=new ImageView(storeLocation);

//        //todo: delete after resize image
        customerLocationView.setFitWidth(20);
        customerLocationView.setFitHeight(20);

        storeBtn.setGraphic(customerLocationView);
        storeBtn.getStyleClass().setAll("LocationBtn");
        //storeBtn.getGraphic().setBlendMode(BlendMode.MULTIPLY);

        //todo: sub 1 from store/ customer location (start from (1,1))
        int customerX=currCustomerDetails.getxCoordinate()-1;
        int customerY=currCustomerDetails.getyCoordinate()-1;

        Tooltip tooltipLoc = new Tooltip("(" + (customerX+1) + "," + (customerY+1) + ")");
        storeBtn.setTooltip(tooltipLoc);

        imagesGroup.getChildren().add(storeBtn);
        storeBtn.setLayoutX(oneCoordinateWidth*customerX -10);
        storeBtn.setLayoutY(oneCoordinateHeight*customerY -20 + 90);

        if(customerX>this.maxXval)
        {
            this.maxXval=customerX;
        }
        if(customerY>this.maxYval)
        {
            this.maxYval=customerY;
        }
        storeBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                showSelectedCustomerData(currCustomerDetails);

            }
        });
    }
}

    public void showSelectedStoreData(StoreDto storeDetails)
    {
        DetailsOnMapController detailsOnMapController=showDetails();
        detailsOnMapController.setStoreData(storeDetails);

    }

    public void showSelectedCustomerData(CustomerDto customerDetails)
    {
        DetailsOnMapController detailsOnMapController=showDetails();
        detailsOnMapController.setCustomerData(customerDetails);

    }

    public DetailsOnMapController showDetails()
    {
        FXMLLoader loader = new FXMLLoader();
        URL storesDataUrl = getClass().getResource(DetailsOnMapConstants.DETAILS_ON_MAP_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(storesDataUrl);

        //BorderPane detailsOnMapBorderPane= null;
        GridPane detailsOnMapBorderPane= null;
        try {
            detailsOnMapBorderPane = loader.load();
        } catch (IOException ioException) {

            //todo!!
            ioException.printStackTrace();
        }
        mapBorderPane.setCenter(detailsOnMapBorderPane);

        DetailsOnMapController detailsOnMapController=loader.getController();

        return detailsOnMapController;
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }
}

