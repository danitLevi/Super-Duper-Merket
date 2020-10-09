package components.updateItem.addNewItem;

import DtoObjects.ItemInSystemDto;
import propertiesClasses.ItemPropertiesClass;
import components.updateItem.UpdateItemController;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.UtilsClass;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AddItemController {
    @FXML
    private TableView<ItemInSystemDto> itemsTable;

    @FXML
    private TableColumn<ItemInSystemDto,Integer> idColumn;

    @FXML
    private TableColumn<ItemInSystemDto, String> nameColumn;

    @FXML
    private TableColumn<ItemInSystemDto, String> purchaseCategoryColumn;

    @FXML
    private Pagination paginationBtn;

    @FXML
    private TextField priceTextField;

    private List<ItemInSystemDto> itemsInSystemNotInStoreSetList;
    private final int rowsPerPage=5;
    Set<ItemInSystemDto> itemsInSystemNotInStoreSetSet;
    private UpdateItemController updateItemController; // parent controller
    private Stage primaryStage;


    public void showData(Set<ItemInSystemDto> itemsInSystemNotInStoreSet)
    {
        this.itemsInSystemNotInStoreSetSet =itemsInSystemNotInStoreSet;

        // Get Items data into ArrayList
        createData();

        // Create properties

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        purchaseCategoryColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPurchaseCategory().toString()));


        itemsTable.getColumns().setAll(idColumn,nameColumn,purchaseCategoryColumn);
        itemsTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);// can choose only one row

        //paginationBtn.setPageCount(itemsDataList.size() / rowsPerPage + 1); //TODO: handle even/odd
        paginationBtn.setPageCount((int) Math.ceil(itemsInSystemNotInStoreSetList.size() / (double)rowsPerPage ));

        //paginationBtn.setPageFactory(this::createPage);

        // Create table pages
        createPage(0);
        // Listener to notify whenever the value of the ObservableValue changes
        paginationBtn.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                createPage(newIndex.intValue()));


    }

    public void createData()
    {
        itemsInSystemNotInStoreSetList = new ArrayList<ItemInSystemDto>();
        itemsInSystemNotInStoreSetList.addAll(this.itemsInSystemNotInStoreSetSet);

    }

    private void createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, itemsInSystemNotInStoreSetList.size());
        itemsTable.setItems(FXCollections.observableArrayList(itemsInSystemNotInStoreSetList.subList(fromIndex, toIndex)));
        //return new BorderPane(itemsTable);
        //return this.itemsTable;
    }


    @FXML
    void AddItem(ActionEvent event) {
        if(itemsTable.getSelectionModel().getSelectedItems().isEmpty())
        {
            UtilsClass.showErrorAlert("Invalid data\n" +
                    "You have to choose item to add from items table");
            return;
        }
        else
        {
            try
            {
                String inputPrice=priceTextField.getText();
                if(inputPrice.isEmpty() )
                {
                    UtilsClass.showErrorAlert("Invalid data\n" +
                            "You have to enter price");
                    priceTextField.getStyleClass().setAll("errorLbl");
                    return;
                }
                else
                {

                    double price = Double.parseDouble(inputPrice);
                    if(price<0)
                    {
                        UtilsClass.showErrorAlert("Invalid data\n" +
                                "price should be positive number");
                        priceTextField.getStyleClass().setAll("errorLbl");
                        return;
                    }
                    ItemInSystemDto selectedItem=itemsTable.getSelectionModel().getSelectedItem();
                     ItemPropertiesClass itemToAdd= new ItemPropertiesClass(selectedItem.getId(),
                                                                            selectedItem.getName(),
                                                                            selectedItem.getPurchaseCategory().toString(),
                                                                            inputPrice);
                     updateItemController.addItem(itemToAdd);
                    primaryStage.close();
                    UtilsClass.showInformatioDialog("Item added successfully!");
                }
            }
            catch (NumberFormatException nfe)
            {
                UtilsClass.showErrorAlert("Invalid data\n" +
                        "Price should be a number");
                priceTextField.getStyleClass().setAll("errorLbl");
                return;
            }

        }
    }

    public UpdateItemController getUpdateItemController() {
        return updateItemController;
    }

    public void setUpdateItemController(UpdateItemController updateItemController) {
        this.updateItemController = updateItemController;
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }
}
