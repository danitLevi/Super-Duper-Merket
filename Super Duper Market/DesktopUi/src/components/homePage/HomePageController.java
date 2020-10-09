package components.homePage;

import DtoObjects.CustomerDto;
import DtoObjects.ItemInSystemDto;
import components.customersData.CustomersDataConstants;
import components.customersData.CustomersDataController;
import components.itemsInSystemData.ItemsInSystemConstants;
import components.itemsInSystemData.ItemsInSystemDataController;
import components.map.MapConstants;
import components.map.MapController;
import components.multipleStoresData.MultipleStoresDataConstants;
import components.multipleStoresData.MultipleStoresDataController;
import components.order.OrderConstants;
import components.order.OrderController;
import components.order.salesInOrder.oneStoreSalesInOrder.oneSaleInOneStoreInOrder.oneSaleInOneStoreInOrderController;
import components.ordersHistory.OrdersHistoryConstants;
import components.ordersHistory.OrdersHistoryController;
import components.updateItem.UpdateItemConstants;
import components.updateItem.UpdateItemController;
import components.uploadFile.UploadFileConstants;
import components.uploadFile.UploadFileController;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import superDuperMarket.LogicInterface;
import superDuperMarket.SuperDuperMarket;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.Set;

//import components.order.OrderConstants;
//import components.order.OrderController;


public class HomePageController {


    private LogicInterface sdmLogic;
    private Stage primaryStage;
    //ItemsInSystemDataController  itemsInSystemDataController;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private ImageView logoId;

    @FXML
    private Label fileNameLbl;

    @FXML
    private MenuButton headerShowDataBtn;

    @FXML
    private Button headerMapBtn;

    @FXML
    private Button headerOrderBtn;

    @FXML
    private Button headerUpdateItemBtn;

    @FXML
    private Button headerUploadBtn;

    @FXML
    private BorderPane homePageBorderPane;

    @FXML
    private ComboBox<String> skinBox;

    @FXML
    private CheckBox animation; //ALONA

    @FXML
    void onAnimation(ActionEvent event) { //ALONA
        if(animation.isSelected()) {
            isAnimated.setValue(true);
        }
        else
        {
            isAnimated.setValue(false);
        }
    }

    private SimpleBooleanProperty isFileSelectedProperty;
    private static SimpleBooleanProperty isAnimated;//ALONA

    public static SimpleBooleanProperty isAnimatedProperty() { //ALONA
        return isAnimated;
    }

    public void setSDMLogic(SuperDuperMarket sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    public HomePageController() {
        isFileSelectedProperty = new SimpleBooleanProperty(false);
        isAnimated = new SimpleBooleanProperty(false);
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    @FXML
    private void initialize() {
        skinBox.getItems().addAll("Light", "Dark", "Colorful");
        // Default skin is: Light
        skinBox.getSelectionModel().selectFirst();
        UtilsClass.setSkin(skinBox.getValue());
        oneSaleInOneStoreInOrderController.setSkin(skinBox.getValue());
        UpdateItemController.setSkin(skinBox.getValue());
        headerShowDataBtn.disableProperty().bind(isFileSelectedProperty.not());
        headerUpdateItemBtn.disableProperty().bind(isFileSelectedProperty.not());
        headerOrderBtn.disableProperty().bind(isFileSelectedProperty.not());
        headerMapBtn.disableProperty().bind(isFileSelectedProperty.not());

    }

    public void fileSelected()
    {
        isFileSelectedProperty.setValue(true);
    }

    @FXML
    void changeSkin(ActionEvent event) {
        String skinSelection = skinBox.getValue();
        UtilsClass.setSkin(skinSelection);
        UpdateItemController.setSkin(skinSelection);
        oneSaleInOneStoreInOrderController.setSkin(skinSelection);
        homePageBorderPane.getStylesheets().clear();

        switch (skinSelection) {
            case "Light":
                String lightSkin = getClass().getResource(HomePageConstants.CSS_LIGHT_RESOURCE_IDENTIFIER).toExternalForm();
                scrollPane.getStylesheets().setAll(lightSkin);
                logoId.setImage(new Image("/components/homePage/icons/Logo.png"));
                changeToLightSkin();
                break;
            case "Dark":
                String darkSkin = getClass().getResource(HomePageConstants.CSS_DARK_RESOURCE_IDENTIFIER).toExternalForm();
                scrollPane.getStylesheets().setAll(darkSkin);
                logoId.setImage(new Image("/components/homePage/icons/LogoDark.png"));
                changeToDarkSkin();
                break;
            case "Colorful":
                String colorSkin = getClass().getResource(HomePageConstants.CSS_COLORFUL_RESOURCE_IDENTIFIER).toExternalForm();
                scrollPane.getStylesheets().setAll(colorSkin);
                logoId.setImage(new Image("/components/homePage/icons/Logo.png"));
                changeToDarkSkin();
                break;

        }
    }

    void changeToDarkSkin()
    {
        ImageView headerShowDataImage = new ImageView("/components/homePage/icons/ShowDataDark.png");
        fitImage(headerShowDataImage);
        headerShowDataBtn.setGraphic(headerShowDataImage);

        ImageView headerMapImage = new ImageView("/components/homePage/icons/MapDark.png");
        fitImage(headerMapImage);
        headerMapBtn.setGraphic(headerMapImage);

        ImageView headerOrderImage = new ImageView("/components/homePage/icons/OrderDark.png");
        fitImage(headerOrderImage);
        headerOrderBtn.setGraphic(headerOrderImage);

        ImageView headerUpdateItemImage = new ImageView("/components/homePage/icons/UpdateItemDark.png");
        fitImage(headerUpdateItemImage);
        headerUpdateItemBtn.setGraphic(headerUpdateItemImage);

        ImageView headerUploadImage = new ImageView("/components/homePage/icons/UploadDark.png");
        fitImage(headerUploadImage);
        headerUploadBtn.setGraphic(headerUploadImage);
    }

    void changeToLightSkin()
    {
        ImageView headerShowDataImage = new ImageView("/components/homePage/icons/ShowData.png");
        fitImage(headerShowDataImage);
        headerShowDataBtn.setGraphic(headerShowDataImage);

        ImageView headerMapImage = new ImageView("/components/homePage/icons/Map.png");
        fitImage(headerMapImage);
        headerMapBtn.setGraphic(headerMapImage);

        ImageView headerOrderImage = new ImageView("/components/homePage/icons/Order.png");
        fitImage(headerOrderImage);
        headerOrderBtn.setGraphic(headerOrderImage);

        ImageView headerUpdateItemImage = new ImageView("/components/homePage/icons/UpdateItem.png");
        fitImage(headerUpdateItemImage);
        headerUpdateItemBtn.setGraphic(headerUpdateItemImage);

        ImageView headerUploadImage = new ImageView("/components/homePage/icons/Upload.png");
        fitImage(headerUploadImage);
        headerUploadBtn.setGraphic(headerUploadImage);
    }

    void fitImage(ImageView image)
    {
        image.fitHeightProperty().setValue(50);
        image.fitWidthProperty().setValue(50);
    }

    @FXML
    void openLoadFileWindow(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        // load main fxml
        URL uploadFileWindowFXML = getClass().getResource(UploadFileConstants.UPLOAD_FILE_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(uploadFileWindowFXML);
        Parent root = loader.load();
        Scene scene = new Scene(root);

        String skinSelection = skinBox.getValue();
        switch (skinSelection) {
            case "Light":
                String lightSkin = getClass().getResource(UploadFileConstants.CSS_LIGHT_RESOURCE_IDENTIFIER).toExternalForm();
                scene.getStylesheets().clear();
                scene.getStylesheets().setAll(lightSkin);
                break;
            case "Dark":
                String darkSkin = getClass().getResource(UploadFileConstants.CSS_DARK_RESOURCE_IDENTIFIER).toExternalForm();
                scene.getStylesheets().clear();
                scene.getStylesheets().setAll(darkSkin);
                break;
            case "Colorful":
                String colorSkin = getClass().getResource(UploadFileConstants.CSS_COLORFUL_RESOURCE_IDENTIFIER).toExternalForm();
                scene.getStylesheets().clear();
                scene.getStylesheets().setAll(colorSkin);
                break;
        }

        Stage uploadFileWindow = new Stage();
        uploadFileWindow.setTitle("Upload file");
        uploadFileWindow.setScene(scene);
        uploadFileWindow.initModality(Modality.APPLICATION_MODAL);

        uploadFileWindow.initOwner(primaryStage);

        UploadFileController uploadFileController=loader.getController();
        uploadFileController.setHomePageController(this);
        uploadFileController.setPrimaryStage(uploadFileWindow);
        uploadFileWindow.show();
    }

    //todo : needded property ?
    public void setFileNameLbl(Label fileNameLbl) {
        this.fileNameLbl = fileNameLbl;
    }

    public void updateFileNameLblText(String fileName)
    {
        this.fileNameLbl.setText(fileName);
    }

    @FXML
    void showItemsData(ActionEvent event)  throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL itemsBorderPaneUrl = getClass().getResource(ItemsInSystemConstants.ITEMS_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(itemsBorderPaneUrl);

        BorderPane itemsDataBorderPane=loader.load();
        homePageBorderPane.setCenter(itemsDataBorderPane);

        ItemsInSystemDataController itemsInSystemDataController =loader.getController();
        Set<ItemInSystemDto> itemsInSystemDetails=sdmLogic.getItemsDetails();
        //itemsInSystemDataController.setSdmLogic(this.sdmLogic);
        itemsInSystemDataController.showData(itemsInSystemDetails);
    }

    //TODO: CHECK WHAT TO DO WITH THROW EXCEPTION
    @FXML
    void showStoresData(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        URL storesDataUrl = getClass().getResource(MultipleStoresDataConstants.STORES_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(storesDataUrl);

        ScrollPane storesDataPane=loader.load();

        homePageBorderPane.setCenter(storesDataPane);
        MultipleStoresDataController storesInSystemDataController=loader.getController();
        storesInSystemDataController.setSdmLogic(this.sdmLogic);
        storesInSystemDataController.showData();

    }

    @FXML
    void showOrderWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL orderDataUrl = getClass().getResource(OrderConstants.ORDER_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(orderDataUrl);

        ScrollPane orderScrollPane=loader.load();

        homePageBorderPane.setCenter(orderScrollPane);

        OrderController orderController =loader.getController();
        orderController.setSdmLogic(this.sdmLogic);
        orderController.setPrimaryBorderPane(this.homePageBorderPane);
        orderController.setOrderPage();

    }


    @FXML
    void showCustomersDataWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL storesDataUrl = getClass().getResource(CustomersDataConstants.CUSTOMERS_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(storesDataUrl);

        BorderPane customersBorderPane=loader.load();
        homePageBorderPane.setCenter(customersBorderPane);

        CustomersDataController customersDataController =loader.getController();
        //customersDataController.setSdmLogic(this.sdmLogic);
        Set<CustomerDto> customersDetails=sdmLogic.getCustomersDetails();
        customersDataController.showData(customersDetails);
    }

    @FXML
    void showMap(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL storesDataUrl = getClass().getResource(MapConstants.MAP_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(storesDataUrl);

        ScrollPane mapBorderPane=loader.load();

        homePageBorderPane.setCenter(mapBorderPane);
        MapController mapController =loader.getController();
        //customersDataController.setSdmLogic(this.sdmLogic);
        mapController.setSdmLogic(this.sdmLogic);
        mapController.insertDataToMap();


    }

    @FXML
    void showUpdateItemWindow(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL storesDataUrl = getClass().getResource(UpdateItemConstants.UPDATE_ITEM_DATA_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(storesDataUrl);

        BorderPane upadteItemBorderPane=loader.load();

        homePageBorderPane.setCenter(upadteItemBorderPane);
        UpdateItemController updateItemController =loader.getController();
        updateItemController.setSdmLogic(this.sdmLogic);
        // updateItemController.setHomePageController(this); // TODO: keep?
        updateItemController.setSkin(skinBox.getValue());
//        oneSaleInOneStoreInOrderController.setSkin(skinBox.getValue());
        updateItemController.showData();

    }

    @FXML
    void showOrdersHistory(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        URL storesDataUrl = getClass().getResource(OrdersHistoryConstants.ORDERS_HISTORY_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(storesDataUrl);

        BorderPane ordersHistoryDataBorderPane=loader.load();

        homePageBorderPane.setCenter(ordersHistoryDataBorderPane);
        OrdersHistoryController ordersHistoryController=loader.getController();
        ordersHistoryController.setSdmLogic(this.sdmLogic);

        ordersHistoryController.showData();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getHomePageBorderPane() {
        return homePageBorderPane;
    }

    public void setHomePageBorderPane(BorderPane homePageBorderPane) {
        this.homePageBorderPane = homePageBorderPane;
    }


}
