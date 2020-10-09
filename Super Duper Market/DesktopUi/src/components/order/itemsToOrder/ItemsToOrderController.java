package components.order.itemsToOrder;

import DtoObjects.ItemInSystemDto;
import DtoObjects.PurchaseCategory;
import components.order.OrderController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import propertiesClasses.ItemToOrderPropertiesClass;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class ItemsToOrderController {

    @FXML
    private TableView<ItemToOrderPropertiesClass> itemsTable;
    @FXML
    private TableColumn<ItemToOrderPropertiesClass, SimpleIntegerProperty> idColumn;

    @FXML
    private TableColumn<ItemToOrderPropertiesClass, SimpleStringProperty> nameColumn;

    @FXML
    private TableColumn<ItemToOrderPropertiesClass, SimpleStringProperty> purchaseCategoryColumn;

    @FXML
    private TableColumn<ItemToOrderPropertiesClass, SimpleStringProperty> priceColumn;


    @FXML
    private TableColumn<ItemToOrderPropertiesClass, SimpleDoubleProperty> amountColumn;

    @FXML
    private TableColumn<ItemToOrderPropertiesClass, ItemToOrderPropertiesClass> addItemToOrderColumn;


    @FXML
    private Pagination paginationBtn;


    //    private List<ItemInSystemDto> itemsDataList;
    private OrderController orderController;
    private LogicInterface sdmLogic;
    private Map<Integer,Double> itemToOrderIdToItemAmount;
    private ObservableList<ItemToOrderPropertiesClass> data;
    private final int rowsPerPage=5;


    private SimpleBooleanProperty isStaticOrderProperty;

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    public void initialize()
    {
        isStaticOrderProperty=new SimpleBooleanProperty(false);
        this.itemToOrderIdToItemAmount=new HashMap<>();


//        priceColumn.visibleProperty().bind(isStaticOrderProperty);

    }

    public void createData(int storeId,Set<ItemInSystemDto> itemsDataSet)
    {
//        itemsDataList = new ArrayList<ItemInSystemDto>();
//        itemsDataList.addAll(itemsDataSet);
        Set<ItemToOrderPropertiesClass> itemsPropertiesDetails=new HashSet<>();
        ItemToOrderPropertiesClass currItemProperties;
        String price;
        for (ItemInSystemDto currItem:itemsDataSet)
        {
            price=getItemPrice(storeId,currItem.getId());
            currItemProperties=new ItemToOrderPropertiesClass(currItem.getId()
                    ,currItem.getName()
                    ,currItem.getPurchaseCategory().toString()
                    ,price);

            itemsPropertiesDetails.add(currItemProperties);
        }
        this.data = FXCollections.observableArrayList(itemsPropertiesDetails);

        orderController.storeIdPropertyProperty().addListener((observable, oldValue, newValue) ->
        {

            if(newValue.intValue()!=UtilsClass.NO_VALUE && itemToOrderIdToItemAmount.size()!=0)
            {
                UtilsClass.showInformatioDialog("You choose to order from different store\n" +
                        "therefor items you ordered from previous store will be deleted and your cart is empty now");
                this.itemToOrderIdToItemAmount=new HashMap<>();
            }
        });
    }

    public String getItemPrice(int storeId,int itemId)
    {
        String value;
        if(storeId== UtilsClass.NO_VALUE)
        {
            value="NO VALUE";
        }
        else
        {
            value="Not available in store";
            double itemPrice = this.sdmLogic.getStoreItemPrice(storeId, itemId);
            if (itemPrice != -1) {
                value = String.valueOf(itemPrice) + " ₪"; // todo : add it in creating the column like in update
            }
        }

        return value;
    }

    private void createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, this.data.size());
        itemsTable.setItems(FXCollections.observableArrayList(this.data.subList(fromIndex, toIndex)));
        createAddItemToOrderColumn();
        //return new BorderPane(itemsTable);
        //return this.itemsTable;
    }

    public void showData(int storeId ,Set<ItemInSystemDto> itemsDataSet ,boolean isStaticOrderSelected)
    {
        this.isStaticOrderProperty.setValue(isStaticOrderSelected);
        priceColumn.setVisible(isStaticOrderSelected);
        // Get Items data into ArrayList
        createData(storeId,itemsDataSet);

        // Create properties

        idColumn.setCellValueFactory(new PropertyValueFactory<ItemToOrderPropertiesClass,SimpleIntegerProperty>("itemId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ItemToOrderPropertiesClass,SimpleStringProperty>("name"));
        purchaseCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemToOrderPropertiesClass,SimpleStringProperty>("purchaseCategory"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<ItemToOrderPropertiesClass,SimpleDoubleProperty>("amount"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<ItemToOrderPropertiesClass,SimpleStringProperty>("price"));

//        createAddItemToOrderColumn();
        itemsTable.getColumns().setAll(idColumn,nameColumn,purchaseCategoryColumn,priceColumn,amountColumn,addItemToOrderColumn);

        paginationBtn.setPageCount((int) Math.ceil(this.data.size() / (double)rowsPerPage ));
        // Create table pages
        createPage(0);

        // Listener to notify whenever the value of the ObservableValue changes
        paginationBtn.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                createPage(newIndex.intValue()));
    }

    public boolean isIsStaticOrderProperty() {
        return isStaticOrderProperty.get();
    }

    public SimpleBooleanProperty isStaticOrderPropertyProperty() {
        return isStaticOrderProperty;
    }

    public void setIsStaticOrderProperty(boolean isStaticOrderProperty) {
        this.isStaticOrderProperty.set(isStaticOrderProperty);
    }


    public void createAddItemToOrderColumn()
    {
//        TableColumn<Person, Person> unfriendCol = new TableColumn<>("Anti-social");
        addItemToOrderColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        addItemToOrderColumn.setCellFactory(param -> new TableCell<ItemToOrderPropertiesClass, ItemToOrderPropertiesClass>() {
            private Button addBtn = new Button("add to order");
            private void setButton()
            {
//                Image addItemIcon=new Image(ADD_ITEM_TO_ORDER_ICON);
//                ImageView iconView=new ImageView(addItemIcon);
//                iconView.setFitWidth(20);
//                iconView.setFitHeight(20);
//                addBtn.setGraphic(iconView);
//                addBtn.getGraphic().setBlendMode(BlendMode.MULTIPLY);

                Tooltip tooltipLoc = new Tooltip("Add item to order");
                addBtn.setTooltip(tooltipLoc);

            }
            //todo : add style while hover and click ?

            @Override
            protected void updateItem(ItemToOrderPropertiesClass itemToAdd, boolean empty) {
                super.updateItem(itemToAdd, empty);

                if (itemToAdd == null) {
                    setGraphic(null);
                    return;
                }

                setButton();
                setGraphic(addBtn);
                //addBtn.setOnAction(event ->addItemToOrder(itemToAdd));
                addBtn.addEventHandler(ActionEvent.ACTION, event ->addItemToOrder(itemToAdd));//ALONA
                addBtn.addEventHandler(ActionEvent.ACTION, event -> orderController.startAddItemToCartAnimation());//ALONA
                addBtn.disableProperty().bind(Bindings.createBooleanBinding(()->
                        itemToAdd.getPrice().equals("Not available in store"),itemToAdd.priceProperty()));
            }
        });
    }

    public void setOrderController(OrderController orderController) {
        this.orderController = orderController;
    }

    private void addItemToOrder(ItemToOrderPropertiesClass itemToAdd) {
        String msg="Enter amount to order\n";


        if(itemToAdd.getPurchaseCategory()==String.valueOf(PurchaseCategory.Quantity))
        {
            msg+="(a positive whole number) ";
        }
        else {
            msg+="(a positive real number which represent amount of kg ) ";;
        }
        if(itemToAdd.getAmount()!=0)
        {
            msg+="\nYou have already ordered this item therefor this amount will be added to current ordered amount ";
        }
        String inputAmount="";
        inputAmount= UtilsClass.showInputDialog(msg);


        while ( !inputAmount.equals("") && (!isValidAmountToOrder(itemToAdd,inputAmount)))
        {
            inputAmount= UtilsClass.showInputDialog(msg);

        }
        if(inputAmount.equals(""))
            return;
        else
        {
            double amount = Double.parseDouble(inputAmount);
            double newAmount=itemToAdd.getAmount()+amount;
            itemToAdd.setAmount(newAmount);
            this.itemToOrderIdToItemAmount.put(itemToAdd.getItemId(),newAmount);
        }


        // todo: add animation

    }

    public void clearItemsTable()
    {
        itemsTable.getItems().clear();
    }

    public boolean isValidAmountToOrder(ItemToOrderPropertiesClass itemToAdd , String amountToOrder)
    {
        if(itemToAdd.getPurchaseCategory().equals(String.valueOf(PurchaseCategory.Quantity)) )
        {
            if(!isInteger(amountToOrder))
            {
                UtilsClass.showErrorAlert("Amount of item purchased by quantity should be an whole number");
                return false;
            }
        }
        else {
            if (!isDouble(amountToOrder) && !isInteger(amountToOrder)) {
                UtilsClass.showErrorAlert("Amount of item purchased by weight should be a real number");
                return false;
            }
        }
        double amount= Double.parseDouble(amountToOrder);
        if(amount<=0)
        {
            UtilsClass.showErrorAlert("Amount of item should be a positive number(greater than 0)");
            return false;
        }

        return true;
    }

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
    private static boolean isDouble(String s) {
        try {
            Double.parseDouble(s);
        } catch(NumberFormatException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    public Map<Integer, Double> getItemToOrderIdToItemAmount() {
        return itemToOrderIdToItemAmount;
    }

    public void setItemToOrderIdToItemAmount(Map<Integer, Double> itemToOrderIdToItemAmount) {
        this.itemToOrderIdToItemAmount = itemToOrderIdToItemAmount;
    }

    public OrderController getOrderController() {
        return orderController;
    }
}
