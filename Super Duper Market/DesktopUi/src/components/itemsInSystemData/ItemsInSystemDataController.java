package components.itemsInSystemData;

import DtoObjects.ItemInSystemDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ItemsInSystemDataController {

    @FXML
    private TableView<ItemInSystemDto> itemsTable;
    @FXML
    private TableColumn<ItemInSystemDto, Integer> idColumn;

    @FXML
    private TableColumn<ItemInSystemDto, String > nameColumn;

    @FXML
    private TableColumn<ItemInSystemDto, String> purchaseCategoryColumn;

    @FXML
    private TableColumn<ItemInSystemDto, Integer> sellerAmountColumn;

    @FXML
    private TableColumn<ItemInSystemDto, Double> avgPriceColumn;

    @FXML
    private Pagination paginationBtn;

    //todo:?
    @FXML
    private BorderPane tableBorderPane;

    @FXML
    private Label itemsDataHeader;

    private List<ItemInSystemDto> itemsDataList;
    //private LogicInterface sdmLogic;
    private final int rowsPerPage=5;
    Set<ItemInSystemDto> itemsDataSet;



    public void createData()
    {
        //Set<ItemInSystemDto> itemsDataSet=sdmLogic.getItemsDetails();
        itemsDataList = new ArrayList<ItemInSystemDto>();
        itemsDataList.addAll(this.itemsDataSet);

    }

    private void createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, itemsDataList.size());
        itemsTable.setItems(FXCollections.observableArrayList(itemsDataList.subList(fromIndex, toIndex)));
        //return new BorderPane(itemsTable);
        //return this.itemsTable;
    }

    public void showData(Set<ItemInSystemDto> itemsDataSet)
    {
        this.itemsDataSet=itemsDataSet;

        // Get Items data into ArrayList
        createData();

        // Create properties

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        purchaseCategoryColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPurchaseCategory().toString()));
        sellerAmountColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getNumOfSellers()));
        avgPriceColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAvgPrice()));

        itemsTable.getColumns().setAll(idColumn,nameColumn,purchaseCategoryColumn,sellerAmountColumn,avgPriceColumn);

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
