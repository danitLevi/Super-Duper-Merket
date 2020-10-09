package components.order;

import DtoObjects.*;
import components.homePage.HomePageController;
import components.order.itemsToOrder.ItemsToOrderConstants;
import components.order.itemsToOrder.ItemsToOrderController;
import components.order.loadDynamicOrder.LoadDynamicOrderConstants;
import components.order.loadDynamicOrder.LoadDynamicOrderController;
import components.order.salesInOrder.SalesInOrderConstants;
import components.order.salesInOrder.SalesInOrderController;
import components.order.storesInDynamicOrder.StoresInDynamicOrderConstants;
import components.order.storesInDynamicOrder.StoresInDynamicOrderController;
import javafx.animation.TranslateTransition;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class OrderController {

    @FXML
    private ComboBox<String> customerComboBox;

    @FXML
    private ToggleGroup orderTypeGroup;

    @FXML
    private Label deliveryPriceValLbl;

    @FXML
    private Label deliveryPriceHeaderLbl;

    @FXML
    private Label storeHeaderLbl;
    @FXML
    private ComboBox<String> storeComboBox;

    //    @FXML
//    private Pane itemsToOrderPane;
    @FXML
    private DatePicker datePicker;

    @FXML
    private RadioButton dynamicBtn;

    @FXML
    private RadioButton staticBtn;

    @FXML
    private GridPane orderGridPane;
    @FXML
    private ImageView arrow; //ALONA

    //    ToggleGroup orderTypeGroup;
    private SimpleBooleanProperty isStaticOrderSelectedProperty;
    private SimpleIntegerProperty customerIdProperty;
    private SimpleIntegerProperty storeIdProperty;

    private LogicInterface sdmLogic;
    private BorderPane primaryBorderPane;
    ItemsToOrderController itemsToOrderController;

    private Map<Integer, Map<Integer, Double>> orderMinimalPriceBag;
    private SimpleBooleanProperty isMinimalPriceBagReturned;


    @FXML
    public void initialize() {
        FXMLLoader loader = new FXMLLoader(); // todo: insert to separate table
        URL itemsToOrderDataUrl = getClass().getResource(ItemsToOrderConstants.ITEMS_TO_ORDER_FXML_RESOURCE_IDENTIFIER);
        loader.setLocation(itemsToOrderDataUrl);
        try {
            BorderPane itemsToOrderBorderPane=loader.load();
            orderGridPane.add(itemsToOrderBorderPane,0,4,4,1);
            this.itemsToOrderController=loader.getController();
            this.itemsToOrderController.setOrderController(this);
            arrow.visibleProperty().bind(HomePageController.isAnimatedProperty());//ALONA

            isStaticOrderSelectedProperty =new SimpleBooleanProperty(false);
            customerIdProperty=new SimpleIntegerProperty(UtilsClass.NO_VALUE);
            storeIdProperty=new SimpleIntegerProperty(UtilsClass.NO_VALUE);

            deliveryPriceValLbl.textProperty().bind(Bindings.createStringBinding(()->getDeliveryPrice() ,customerIdProperty,storeIdProperty));

            deliveryPriceValLbl.visibleProperty().bind(isStaticOrderSelectedProperty);
            deliveryPriceHeaderLbl.visibleProperty().bind(isStaticOrderSelectedProperty);
            storeHeaderLbl.visibleProperty().bind(isStaticOrderSelectedProperty);
            storeComboBox.visibleProperty().bind(isStaticOrderSelectedProperty);

            storeComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                        if(storeComboBox.getSelectionModel().isEmpty())
                        {
                            this.storeIdProperty.setValue(UtilsClass.NO_VALUE);
                        }
                        else
                        {
                            this.storeIdProperty.setValue( findStoreIdInStr(newValue));
                            fillItemsTable(isStaticOrderSelectedProperty.get());

//                            UtilsClass.showInformatioDialog("you");
                        }

                    }
            );
            //isStaticOrderSelectedProperty.addListener((o, oldVal, newVal) -> fillItemsTable(newVal));

        }
        catch (IOException e) {
            UtilsClass.showErrorAlert();
        }


    }


    public OrderController()
    {
        orderTypeGroup=new ToggleGroup();
        orderMinimalPriceBag=new HashMap<>();
        isMinimalPriceBagReturned=new SimpleBooleanProperty(false);
    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
        this.itemsToOrderController.setSdmLogic(this.sdmLogic);
    }

    public void setOrderPage( )
    {
        fillCustomerComboBox();
        initializeRadioButtons();
        fillStoresComboBox();
        fillItemsTable(isStaticOrderSelectedProperty.get()); // todo: check if needed ?

    }

    public void fillCustomerComboBox()
    {
        Set<CustomerDto> customersDetails=this.sdmLogic.getCustomersDetails();
        List<String> customersDetailsInComboBox=new ArrayList<>();
        for (CustomerDto currStoreDetails: customersDetails)
        {
            customersDetailsInComboBox.add(currStoreDetails.getName()+" (id="+currStoreDetails.getId()+")");
        }
        customerComboBox.getItems().addAll( customersDetailsInComboBox);
        customerComboBox.getSelectionModel().selectedItemProperty().addListener( (options, oldValue, newValue) -> {
                    customerIdProperty.setValue(findCustomerIdInStr(newValue));
                    // deliveryPriceValLbl.textProperty().bind(Bindings.createDoubleBinding(this.sdmLogic.getDeliveryPriceToCustomer(sto)));

                }
        );
    }

    public void fillStoresComboBox()
    {
        Set<StoreDto> storesDetails=this.sdmLogic.getStoresDetails();
        List<String> storesDetailsInComboBox=new ArrayList<>();
        for (StoreDto currStoreDetails: storesDetails)
        {
            storesDetailsInComboBox.add(currStoreDetails.getName()+
                    ": id="+currStoreDetails.getId()
                    +", location=("+currStoreDetails.getxCoordinate()+","+currStoreDetails.getyCoordinate()+")");
        }
        storeComboBox.getItems().addAll(storesDetailsInComboBox);
        fillItemsTable(isStaticOrderSelectedProperty.get());


    }

    // todo: handle case lastIndexOf not found
    //  and cant parse ??
    private int findStoreIdInStr(String str)
    {
        int idStrIndex=str.lastIndexOf("id=");
        int locationIndex=str.lastIndexOf(", location=(");
        return Integer.parseInt( str.substring(idStrIndex+3,locationIndex));
    }
    private int findCustomerIdInStr(String str)
    {
        int equalsIndex=str.lastIndexOf("=");
        int bracketIndex=str.lastIndexOf(")");
        return Integer.parseInt( str.substring(equalsIndex+1,bracketIndex));
    }

    public void initializeRadioButtons()
    {
        orderTypeGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
            public void changed(ObservableValue<? extends Toggle> ov,
                                Toggle old_toggle, Toggle new_toggle) {

                if ((RadioButton) orderTypeGroup.getSelectedToggle() ==dynamicBtn ) {
                    storeIdProperty.setValue(UtilsClass.NO_VALUE);
                    isStaticOrderSelectedProperty.setValue(false);
                    fillItemsTable(isStaticOrderSelectedProperty.get());
                    storeComboBox.getSelectionModel().clearSelection();

                }
                else
                {
                    isStaticOrderSelectedProperty.setValue(true);
                    clearItemsToOrderTable();
                }
//                fillItemsTable(isStaticOrderSelectedProperty.get());
            }
        });
    }

    public String getDeliveryPrice()
    {
        if(storeIdProperty.getValue()==-1 ||customerIdProperty.getValue()==-1)
            return "NO VALUE";
        return String.format("%.2f ₪", this.sdmLogic.getDeliveryPriceToCustomer(storeIdProperty.getValue(),customerIdProperty.getValue()));
    }


    public void fillItemsTable(Boolean isStaticOrderSelected)
    {
        Set<ItemInSystemDto> itemsDataSet=sdmLogic.getItemsDetails();
        this.itemsToOrderController.showData(storeIdProperty.getValue(),itemsDataSet,isStaticOrderSelected);
    }


    private void clearItemsToOrderTable()
    {
        this.itemsToOrderController.clearItemsTable();
    }


    @FXML
    public  void finishShopping(ActionEvent event) throws IOException, InterruptedException {


        Map<Integer, Double> itemsToOrder = itemsToOrderController.getItemToOrderIdToItemAmount();
        if(isAllFieldsSupplied(itemsToOrder))
        {
//            Map<Integer, Map<Integer, Double>> orderMinimalPriceBag= new HashMap<>();


            if(!isStaticOrderSelectedProperty.getValue())
            {
                 findMinimalPriceBag(itemsToOrder); //todo:uncomment after fixing


                //todo: delete after fixing threads
//                Map<Integer, Double> insideMap1=new HashMap<>();
//                insideMap1.put(5,2.0);
//                insideMap1.put(10,2.0);
//                orderMinimalPriceBag.put(3,insideMap1);
//
//                Map<Integer, Double> insideMap2=new HashMap<>();
//                insideMap2.put(1,2.0);
//                orderMinimalPriceBag.put(1,insideMap2);
/////////////////////////////////////////////////////////////////////////////////
                isMinimalPriceBagReturned.addListener((observable, oldValue, newValue) ->
                {
                   final OrderInputDto orderInputDetails=new OrderInputDto(orderMinimalPriceBag,
                            customerIdProperty.get(),
                            customerComboBox.getSelectionModel().getSelectedItem(),
                            datePicker.getValue(),
                            false);

                    showStoresInDynamicOrder(orderInputDetails);
                });


                //todo: add continue to sales after show stores
            }
            else
            {
                orderMinimalPriceBag.put(storeIdProperty.getValue(),itemsToOrder);

                OrderInputDto orderInputDetails=new OrderInputDto(orderMinimalPriceBag,
                        customerIdProperty.get(),
                        customerComboBox.getSelectionModel().getSelectedItem(),
                        datePicker.getValue(),
                        true);

                showSalesInOrder(orderInputDetails);
            }
        }
    }

    @FXML
    public boolean isAllFieldsSupplied(Map<Integer, Double> itemsToOrder)
    {
        boolean isAllFieldsSupplied=true;
        String msg="Invalid order. Not all data supplied\n";
        if(customerIdProperty.getValue()==UtilsClass.NO_VALUE)
        {
            isAllFieldsSupplied=false;
            msg+="Please choose customer\n";
        }

        if(datePicker.getValue()==null)
        {
            isAllFieldsSupplied=false;
            msg+="Please choose date\n";
        }

        if(storeComboBox.isVisible() && storeIdProperty.getValue()==UtilsClass.NO_VALUE)
        {
            isAllFieldsSupplied=false;
            msg+="Please choose store to order from\n";
        }

        if(itemsToOrder.size()==0)
        {
            isAllFieldsSupplied=false;
            msg+="You didn't order any item . please choose at least one item to order\n";
        }

        if(!isAllFieldsSupplied)
        {
            UtilsClass.showErrorAlert(msg);
        }
        return isAllFieldsSupplied;
    }

    //todo: return void
    public void findMinimalPriceBag(Map<Integer, Double> itemsToOrder) throws InterruptedException {
        try {
            FXMLLoader loader = new FXMLLoader();

            // load main fxml
            URL windowFXML = getClass().getResource(LoadDynamicOrderConstants.LOAD_DYNAMIC_ORDER_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(windowFXML);
            Parent root = null;
            root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setTitle("Load dynamic order");
            stage.setScene(scene);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initStyle(StageStyle.UNDECORATED);

            LoadDynamicOrderController Controller=loader.getController();
            Controller.setPrimaryStage(stage);
             Controller.findMinimalBag(itemsToOrder,this.sdmLogic,this::setOrderMinimalPriceBag,this::setIsMinimalPriceBagReturned);


            stage.show();

//             Controller.getItemsMinimalBag();
        } catch (IOException e) {
            UtilsClass.showErrorAlert();

        }

    }

    public void showStoresInDynamicOrder( OrderInputDto orderInputDetails)
    {
        try {
            Map<Integer, Map<Integer, Double>> orderMinimalPriceBag  =orderInputDetails.getOrderMinimalPriceBag();
            FXMLLoader loader = new FXMLLoader();
            URL orderDataUrl = getClass().getResource(StoresInDynamicOrderConstants.STORES_IN_DYNAMIC_ORDER_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(orderDataUrl);
            ScrollPane storesInDynamicOrderScrollPane= loader.load();
            StoresInDynamicOrderController controller =loader.getController();

            Set<StoreInCalcDyanmicOrderDto> storesInDynamicOrderDetails=this.sdmLogic.getStoresInDynamicOrderDetails(orderMinimalPriceBag,customerIdProperty.get());
            controller.setParentController(this);
            primaryBorderPane.setCenter(storesInDynamicOrderScrollPane);
            controller.showData(storesInDynamicOrderDetails,orderInputDetails);
        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

    public BorderPane getPrimaryBorderPane() {
        return primaryBorderPane;
    }

    public void setPrimaryBorderPane(BorderPane primaryBorderPane) {
        this.primaryBorderPane = primaryBorderPane;
    }

    public void showSalesInOrder(OrderInputDto orderInputDetails)
    {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL dataUrl = getClass().getResource(SalesInOrderConstants.SALES_IN_ORDER_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(dataUrl);
            ScrollPane salesPane=loader.load();
//            storesPane.getChildren().add(storePane);

            SalesInOrderController controller =loader.getController();
            controller.setSdmLogic(this.sdmLogic);
            controller.setPrimaryPane(this.primaryBorderPane);
            controller.showData(orderInputDetails);

            primaryBorderPane.setCenter(salesPane);

        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

    public Map<Integer, Map<Integer, Double>> getOrderMinimalPriceBag() {
        return orderMinimalPriceBag;
    }

    public void setOrderMinimalPriceBag(Map<Integer, Map<Integer, Double>> orderMinimalPriceBag) {
        this.orderMinimalPriceBag = orderMinimalPriceBag;
    }

    public boolean isIsMinimalPriceBagReturned() {
        return isMinimalPriceBagReturned.get();
    }

    public SimpleBooleanProperty isMinimalPriceBagReturnedProperty() {
        return isMinimalPriceBagReturned;
    }

    public void setIsMinimalPriceBagReturned(boolean isMinimalPriceBagReturned) {
        this.isMinimalPriceBagReturned.set(isMinimalPriceBagReturned);
    }

    public int getStoreIdProperty() {
        return storeIdProperty.get();
    }

    public SimpleIntegerProperty storeIdPropertyProperty() {
        return storeIdProperty;
    }

    public void setStoreIdProperty(int storeIdProperty) {
        this.storeIdProperty.set(storeIdProperty);
    }

    public void startAddItemToCartAnimation() { //ALONA

        //arrow.setVisible(true);
        Duration duration = Duration.millis(1400);
        TranslateTransition transition = new TranslateTransition(duration, arrow);
        transition.setByX(0);
        transition.setByY(15);
        transition.setAutoReverse(true);
        transition.setCycleCount(2);
        transition.play();

    }
}


