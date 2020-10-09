
package components.itemsInStoreData;

import DtoObjects.ItemInStoreDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemsInStoreController {

    @FXML
    private TableView<ItemInStoreDto> itemsTable;
    @FXML
    private TableColumn<ItemInStoreDto, Integer> idColumn;

    @FXML
    private TableColumn<ItemInStoreDto, String > nameColumn;

    @FXML
    private TableColumn<ItemInStoreDto, String> purchaseCategoryColumn;

    @FXML
    private TableColumn<ItemInStoreDto, Double> priceColumn;

    @FXML
    private TableColumn<ItemInStoreDto, Integer> purchasesAmountColumn;

    @FXML
    private Label itemsDataHeader;

    @FXML
    private Pagination paginationBtn;

    private List<ItemInStoreDto> itemsDataList;
    //private LogicInterface sdmLogic; //todo: needed?
    private final int rowsPerPage=4;
    private Set<ItemInStoreDto> itemsDataSet;
    //todo: ok ??
    public void createData()
    {
        //Set<ItemInStoreDto> itemsDataSet=sdmLogic.getStoreItemsDetails(storeId);
        itemsDataList = new ArrayList<ItemInStoreDto>();
        itemsDataList.addAll(this.itemsDataSet);

    }

    private void createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, itemsDataList.size());
        itemsTable.setItems(FXCollections.observableArrayList(itemsDataList.subList(fromIndex, toIndex)));
        //return new BorderPane(itemsTable);
        //return this.itemsTable;
    }

    public void showData(String storeName,Set<ItemInStoreDto> itemsDataSet)
    {
        itemsDataHeader.setText("Items in "+storeName);
        this.itemsDataSet=itemsDataSet;
        // Get Items data into ArrayList
        createData();

        // Create properties

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        purchaseCategoryColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPurchaseCategory().toString()));
        priceColumn.setCellValueFactory(param -> new SimpleObjectProperty(String.format("%.2f ₪",param.getValue().getPrice())) ) ;
        purchasesAmountColumn.setCellValueFactory(param -> new SimpleObjectProperty(param.getValue().getAmountOfpurchasesInStore()));


        itemsTable.getColumns().setAll(idColumn,nameColumn,purchaseCategoryColumn,priceColumn,purchasesAmountColumn);

        //paginationBtn.setPageCount(itemsDataList.size() / rowsPerPage + 1); //TODO: handle even/odd
        paginationBtn.setPageCount((int) Math.ceil(itemsDataList.size() / (double)rowsPerPage ));


        //paginationBtn.setPageFactory(this::createPage);

        // Create table pages
        createPage(0);
        // Listener to notify whenever the value of the ObservableValue changes
        paginationBtn.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                createPage(newIndex.intValue()));
    }

}





