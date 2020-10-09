package components.updateItem;

import DtoObjects.ItemInStoreDto;
import DtoObjects.StoreDto;
import Exceptions.DeleteItemFromItsOnlySellerException;
import Exceptions.DeleteStoreOnlyItemException;
import components.updateItem.addNewItem.AddItemConstants;
import components.updateItem.addNewItem.AddItemController;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.effect.BlendMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import propertiesClasses.ItemPropertiesClass;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static utils.UtilsClass.showConfirmationDialof;

public class UpdateItemController {

    @FXML
    private ComboBox<String> storeComboBox;

    @FXML
    private Button addBtn;

    @FXML
    private TableView<ItemPropertiesClass> itemsTable;
    @FXML
    private TableColumn<ItemPropertiesClass, SimpleIntegerProperty> idColumn;

    @FXML
    private TableColumn<ItemPropertiesClass, SimpleStringProperty> nameColumn;

    @FXML
    private TableColumn<ItemPropertiesClass, SimpleStringProperty> purchaseCategoryColumn;

    @FXML
    private TableColumn<ItemPropertiesClass, SimpleStringProperty> priceColumn;

    @FXML
    private TableColumn<ItemPropertiesClass, ItemPropertiesClass> removeColumn;
    @FXML
    private TableColumn<ItemPropertiesClass, ItemPropertiesClass> updatePriceColumn;

    @FXML
    private Pagination paginationBtn;

    private final int rowsPerPage=3;

    private LogicInterface sdmLogic;
    private SimpleIntegerProperty selectedStoreIdProperty;
    private ObservableList<ItemPropertiesClass> data;
    private static String skin;
    //private HomePageController homePageController;

    @FXML
    public void initialize()
    {
        selectedStoreIdProperty=new SimpleIntegerProperty();
        itemsTable.disableProperty().bind(storeComboBox.getSelectionModel().selectedItemProperty().isNull());
        addBtn.disableProperty().bind(storeComboBox.getSelectionModel().selectedItemProperty().isNull());
        data=FXCollections.observableArrayList();
        paginationBtn.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                createPage(newIndex.intValue() ,data));

    }

//    public void setHomePageController (HomePageController homePageController)
//    {
//        this.homePageController = homePageController;
//    }
    public static void setSkin(String skinType)
    {
        skin = skinType;
    }

    public void showData()
    {
        Set<StoreDto> storesDetails=this.sdmLogic.getStoresDetails();
        List<String> storesDetailsInComboBox=new ArrayList<>();
        for (StoreDto currStoreDetails: storesDetails)
        {
            storesDetailsInComboBox.add(currStoreDetails.getName()+" (id="+currStoreDetails.getId()+")");
        }
        storeComboBox.getItems().addAll( storesDetailsInComboBox);
    }
    @FXML
    void showItemsInStoreSelection(ActionEvent event) {

        selectedStoreIdProperty.set( findIdInStr(storeComboBox.getSelectionModel().getSelectedItem()));
        initializeItemsInStoreTable();
    }


    public void initializeItemsInStoreTable()
    {
        Set<ItemInStoreDto> itemsDetails=this.sdmLogic.getStoreItemsDetails(selectedStoreIdProperty.getValue());
        Set<ItemPropertiesClass> itemsPropertiesDetails=new HashSet<>();
        ItemPropertiesClass currItemProperties;
        for (ItemInStoreDto currItem:itemsDetails)
        {
            currItemProperties=new ItemPropertiesClass(currItem.getId()
                                                                ,currItem.getName()
                                                                ,currItem.getPurchaseCategory().toString()
                                                                ,String.valueOf(currItem.getPrice()));
            itemsPropertiesDetails.add(currItemProperties);
        }

        idColumn.setCellValueFactory(new PropertyValueFactory<ItemPropertiesClass,SimpleIntegerProperty>("itemId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<ItemPropertiesClass,SimpleStringProperty>("name"));
        purchaseCategoryColumn.setCellValueFactory(new PropertyValueFactory<ItemPropertiesClass,SimpleStringProperty>("purchaseCategory"));

       //todo : add ₪ in print ?

        //priceColumn.setCellValueFactory((data) -> new SimpleStringProperty(data.getValue().getPrice());
        priceColumn.setCellValueFactory(new PropertyValueFactory<ItemPropertiesClass, SimpleStringProperty>("price"));

//        priceColumn.setCellValueFactory(cellData -> Bindings.createStringBinding(() ->
//                        cellData.getValue().priceProperty()+ " ₪ " ,
//                cellData.getValue().priceProperty()));

//        createDeleteColumn();
//        createUpdatePriceColumn();

//         Button deleteButton = new Button("Delete item"); // todo: add image (and toolip )?
//        Consumer<ItemToUpdatePropertiesClass> myConsumer = item -> deleteRow(item);
//        createButtonColumn(removeColumn,deleteButton,myConsumer);


        itemsTable.getColumns().setAll(idColumn, nameColumn, purchaseCategoryColumn,priceColumn,updatePriceColumn,removeColumn);

        this.data = FXCollections.observableArrayList(itemsPropertiesDetails);
        paginationBtn.pageCountProperty().bind( Bindings.createIntegerBinding(()->(int)Math.ceil(this.data.size() / (double)rowsPerPage ),data));

        createPage(0,data );
        //paginationBtn.setPageCount((int) Math.ceil(data.size() / (double)rowsPerPage ));
        //paginationBtn.pageCountProperty().bind((int) Math.ceil(data.size() / (double)rowsPerPage ));

        this.data.addListener(new ListChangeListener() {

            @Override
            public void onChanged(ListChangeListener.Change change) {
                createPage(paginationBtn.getCurrentPageIndex(),data );
            }
        });



    }

    private void createPage(int pageIndex, ObservableList<ItemPropertiesClass> data ) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, data.size());
        itemsTable.setItems(FXCollections.observableArrayList( data.subList(fromIndex, toIndex)));
        createDeleteColumn();
        createUpdatePriceColumn();

    }

    public LogicInterface getSdmLogic() {
        return sdmLogic;
    }

    public void setSdmLogic(LogicInterface sdmLogic) {
        this.sdmLogic = sdmLogic;
    }

    private int findIdInStr(String str)
    {
        int equalsIndex=str.lastIndexOf("=");
        int bracketIndex=str.lastIndexOf(")");
        return Integer.parseInt( str.substring(equalsIndex+1,bracketIndex));
    }

    public void createDeleteColumn()
    {
//        TableColumn<Person, Person> unfriendCol = new TableColumn<>("Anti-social");
        removeColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        removeColumn.setCellFactory(param -> new TableCell<ItemPropertiesClass, ItemPropertiesClass>() {
            private Button deleteButton = new Button();

            private void setButton()
            {
                deleteButton.setStyle("-fx-background-color: #f8cfd4");
                Image deleteIcon=new Image(UpdateItemConstants.DELETE_ITEM_ICON_FXML_RESOURCE_IDENTIFIER);
                deleteButton.setOnMouseEntered(e -> deleteButton.setStyle("-fx-background-color:#dcb1b6"));
                deleteButton.setOnMouseExited(e -> deleteButton.setStyle("-fx-background-color:#f8cfd4"));

                ImageView deleteIconView=new ImageView(deleteIcon);
                deleteIconView.setFitWidth(20);
                deleteIconView.setFitHeight(20);
                deleteIconView.setFitHeight(20);
                deleteButton.setGraphic(deleteIconView);
                deleteButton.getGraphic().setBlendMode(BlendMode.MULTIPLY);

                Tooltip tooltipLoc = new Tooltip("Delete item");
                deleteButton.setTooltip(tooltipLoc);
            }

            @Override
            protected void updateItem(ItemPropertiesClass itemToDelete, boolean empty) {
                super.updateItem(itemToDelete, empty);

                if (itemToDelete == null) {
                    setGraphic(null);
                    return;
                }
                setButton();
                setGraphic(deleteButton);//?

               // deleteButton.setOnAction(event -> getTableView().getItems().remove(itemToDelete));
               // deleteButton.setOnAction(event -> itemsTable.getItems().remove(itemToDelete));
                deleteButton.setOnAction(event ->deleteRow (itemToDelete));


            }
        });
    }

    public void createUpdatePriceColumn()
    {
//        TableColumn<Person, Person> unfriendCol = new TableColumn<>("Anti-social");
        updatePriceColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        updatePriceColumn.setCellFactory(param -> new TableCell<ItemPropertiesClass, ItemPropertiesClass>() {
            private Button updateButton = new Button();


            private void setButton()
            {
                updateButton.setStyle("-fx-background-color: #c9fad9");
                updateButton.setOnMouseEntered(e -> updateButton.setStyle("-fx-background-color:#a8d9b8"));
                updateButton.setOnMouseExited(e -> updateButton.setStyle("-fx-background-color:#c9fad9"));

                Image updateIcon=new Image(UpdateItemConstants.UPDATE_ITEM_ICON_FXML_RESOURCE_IDENTIFIER);
                ImageView updateIconView=new ImageView(updateIcon);
                updateIconView.setFitWidth(20);
                updateIconView.setFitHeight(20);
                updateButton.setGraphic(updateIconView);
                updateButton.getGraphic().setBlendMode(BlendMode.MULTIPLY);

                Tooltip tooltipLoc = new Tooltip("Update price");
                updateButton.setTooltip(tooltipLoc);
            }
            //todo : add style while hover and click ?

            @Override
            protected void updateItem(ItemPropertiesClass itemToUpdate, boolean empty) {
                super.updateItem(itemToUpdate, empty);

                if (itemToUpdate == null) {
                    setGraphic(null);
                    return;
                }
                setButton();
                setGraphic(updateButton);

                //todo: add alert -are you sure ?


                updateButton.setOnAction(event ->updatePrice(itemToUpdate));
                //todo: add alert -item deleted successfully ?

            }
        });
    }

    // todo : checking to price outside input dialog
    // and check after turn price into string
    private void updatePrice(ItemPropertiesClass itemToUpdate) {
        String inputStringPrice=UtilsClass.showInputDialog("Enter item's price (a positive number)");

        while ( !inputStringPrice.equals("") && !isValidPrice(inputStringPrice))
        {
            inputStringPrice=UtilsClass.showInputDialog("Enter item's price (a positive number)");
        }
//        double price= showPriceInputDialog("Enter item's price (a positive number)" ,itemToUpdate);

        if(!inputStringPrice.equals(""))
        {

            itemToUpdate.setPrice(inputStringPrice);
            double price=Double.parseDouble(inputStringPrice);
            this.sdmLogic.updateStoreItemPrice(selectedStoreIdProperty.getValue(),itemToUpdate.getItemId(),price);
            UtilsClass.showInformatioDialog("The price of "+itemToUpdate.getName()+"(id="+itemToUpdate.getItemId()+") updated successfully");

        }

    }

    public boolean isValidPrice(String inputStringPrice)
    {
        double price;
        try
        {

            price = Double.parseDouble(inputStringPrice);
            if (price < 0) {
                UtilsClass.showErrorAlert("Invalid data\n" +
                        "price should be positive number");
                return false;
            }

        }
        catch (NumberFormatException e){
                UtilsClass.showErrorAlert("Invalid data\n" +
                        "Price should be a number");
                return  false;
        }
        return true;
    }

//
//
//
//    public static double showPriceInputDialog(String msg , ItemPropertiesClass itemToUpdate) {
//        TextInputDialog dialog = new TextInputDialog();
//        dialog.setTitle("Input Dialog");
//        dialog.setHeaderText(null);
//        dialog.setContentText(msg);
//        double price = itemToUpdate.getPrice();
//
//        //Optional<String> result = dialog.showAndWait();
//        Optional<String> result = dialog.showAndWait();
//        if (result.isPresent()) {
//            try {
//                price = Double.parseDouble(result.get());
//                if (price < 0) {
//                    UtilsClass.showErrorAlert("Invalid data\n" +
//                            "price should be positive number");
//                    price=showPriceInputDialog(msg,itemToUpdate);
//                }
//                return price;
//            } catch (NumberFormatException nfe) {
//                UtilsClass.showErrorAlert("Invalid data\n" +
//                        "Price should be a number");
//                price=showPriceInputDialog(msg,itemToUpdate);
//
//            }
//        }
//
//        return price;
//    }






    //    public void createButtonColumn(TableColumn<ItemToUpdatePropertiesClass, ItemToUpdatePropertiesClass> column ,Button button , Consumer<ItemToUpdatePropertiesClass> myConsumer )
//    {
//        column.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
//
//        column.setCellFactory(param -> new TableCell<ItemToUpdatePropertiesClass, ItemToUpdatePropertiesClass>() {
//            //todo : add style while hover and click ?
//
//            @Override
//            protected void updateItem(ItemToUpdatePropertiesClass itemToDelete, boolean empty) {
//                super.updateItem(itemToDelete, empty);
//
//                if (itemToDelete == null) {
//                    setGraphic(null);
//                    return;
//                }
//
//                setGraphic(button);//?
//
//                button.setOnAction(event ->myConsumer.accept(itemToDelete));
//                //todo: add alert -item deleted successfully ?
//
//            }
//        });
//    }
    public void deleteRow(ItemPropertiesClass itemToDelete)
    {
        String msgStr="";
        boolean isInSale=false;
        try {
            msgStr="Are you sure you want to delete the item "+itemToDelete.getName()+" (id= "+itemToDelete.getItemId()+") ?";
            isInSale=this.sdmLogic.isItemInStoreInSale(itemToDelete.getItemId(),selectedStoreIdProperty.getValue());
            if(isInSale)
            {
                msgStr+="\nThis item is in sale.If you delete it the corresponding sales will be deleted either ";
            }
            ButtonType choice=showConfirmationDialof(msgStr);
            if(choice== ButtonType.OK)
            {
                this.sdmLogic.deleteItemFromStore(itemToDelete.getItemId(),selectedStoreIdProperty.getValue());
                //itemsTable.getItems().remove(itemToDelete);
                data.remove(itemToDelete);
                msgStr="The item "+itemToDelete.getName()+" (id="+itemToDelete.getItemId()+")";
                if(isInSale)
                {
                    msgStr+=" and the sales that contains it";
                }
                msgStr+=" has deleted successfully";

                UtilsClass.showInformatioDialog(msgStr);

            }

        } catch (DeleteItemFromItsOnlySellerException e) {
            UtilsClass.showErrorAlert("The chosen item ( "+itemToDelete.getName()+",id="+itemToDelete.getItemId()+
                    " ) is being sold only in this store , therefor it can not be deleted.\n" +
                    "Each item should be sold in at least one store");

        } catch (DeleteStoreOnlyItemException e) {
           UtilsClass.showErrorAlert("The chosen item ( "+itemToDelete.getName()+",id="+itemToDelete.getItemId()+
                   " ) is the only item in the store , therefor it can not be deleted.\n" +
                   "Each store should sell at least one item");
        }
        // todo - add exception if delete item part of sale !!!!!

    }
    @FXML
    void openAddNewItemWindow(ActionEvent event) {

        try {
            FXMLLoader loader = new FXMLLoader();
            // load main fxml
            URL addItemWindowFXML = getClass().getResource(AddItemConstants.ADD_ITEM_DATA_FXML_RESOURCE_IDENTIFIER);
            loader.setLocation(addItemWindowFXML);
            Parent root = loader.load();
            Scene scene = new Scene(root);


            switch (skin) {
                case "Light":
                    String lightSkin = getClass().getResource(AddItemConstants.CSS_LIGHT_RESOURCE_IDENTIFIER).toExternalForm();
                    scene.getStylesheets().clear();
                    scene.getStylesheets().setAll(lightSkin);
                    break;
                case "Dark":
                    String darkSkin = getClass().getResource(AddItemConstants.CSS_DARK_RESOURCE_IDENTIFIER).toExternalForm();
                    scene.getStylesheets().clear();
                    scene.getStylesheets().setAll(darkSkin);
                    break;
                case "Colorful":
                    String colorSkin = getClass().getResource(AddItemConstants.CSS_COLORFUL_RESOURCE_IDENTIFIER).toExternalForm();
                    scene.getStylesheets().clear();
                    scene.getStylesheets().setAll(colorSkin);
                    break;
            }

            Stage addItemWindow = new Stage();
            addItemWindow.setTitle("Add item");
            addItemWindow.setScene(scene);
            addItemWindow.initModality(Modality.APPLICATION_MODAL);

            AddItemController addItemController=loader.getController();
            addItemController.setUpdateItemController(this);
            addItemController.setPrimaryStage(addItemWindow);
            addItemWindow.show();
            addItemController.showData(this.sdmLogic.getItemsInSystemNotInStore(selectedStoreIdProperty.getValue()));

        } catch (IOException e) {
            UtilsClass.showErrorAlert();
        }
    }

    public void addItem(ItemPropertiesClass itemToAdd)
    {
        data.add(itemToAdd);
        this.sdmLogic.addItemToStore(selectedStoreIdProperty.getValue(),itemToAdd.getItemId(),Double.parseDouble(itemToAdd.getPrice()));
    }

}
