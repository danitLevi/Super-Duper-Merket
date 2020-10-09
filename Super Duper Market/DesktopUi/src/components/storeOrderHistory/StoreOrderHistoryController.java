package components.storeOrderHistory;

import DtoObjects.StoreOrderDto;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class StoreOrderHistoryController {
        @FXML
        private TableView<StoreOrderDto> ordersTable;

        @FXML
        private TableColumn<StoreOrderDto, LocalDate> dateColumn;

        @FXML
        private TableColumn<StoreOrderDto, String> itemsAmomuntColumn;

        @FXML
        private TableColumn<StoreOrderDto, String> itemsCostColumn;

        @FXML
        private TableColumn<StoreOrderDto, String> deliveriesCostColumn;

        @FXML
        private TableColumn<StoreOrderDto, String> totalCostColumn;

        @FXML
        private TableColumn<StoreOrderDto,Boolean > isDynamicColumn;

        @FXML
        private TableColumn<StoreOrderDto, String> dynamicIdColumn;

        @FXML
        private Label ordersInStoreHeaderLbl;

        @FXML
        private Pagination paginationBtn;

    private List<StoreOrderDto> storeOrdersList;
    //private LogicInterface sdmLogic;
    private final int rowsPerPage=5;
    Set<StoreOrderDto> storeOrdersSet;



    public void createData()
    {
        //Set<ItemInSystemDto> itemsDataSet=sdmLogic.getItemsDetails();
        storeOrdersList = new ArrayList<StoreOrderDto>();
        storeOrdersList.addAll(storeOrdersSet);

    }

    private void createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, storeOrdersList.size());
        ordersTable.setItems(FXCollections.observableArrayList(storeOrdersList.subList(fromIndex, toIndex)));
        //return new BorderPane(itemsTable);
        //return this.itemsTable;
    }

    public void showData(String storeName,Set<StoreOrderDto> storeOrdersSet)
    {
        ordersInStoreHeaderLbl.setText("Orders in "+storeName);
        this.storeOrdersSet=storeOrdersSet;

        // Get Items data into ArrayList
        createData();

        // Create properties

        //todo: check if working after doing order
        dateColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getDate()));
        itemsAmomuntColumn.setCellValueFactory(param -> new SimpleStringProperty(String.format("%.2f ",param.getValue().getItemsTotalAmount())));
        itemsCostColumn.setCellValueFactory(param -> new SimpleStringProperty(String.format("%.2f ₪",param.getValue().getItemsTotalPrice())));
        deliveriesCostColumn.setCellValueFactory(param -> new SimpleStringProperty(String.format("%.2f ₪",param.getValue().getDeliveryTotalPrice())));
        totalCostColumn.setCellValueFactory(param -> new SimpleStringProperty(String.format("%.2f ₪",param.getValue().getOrderTotalPrice())));
        isDynamicColumn.setCellValueFactory(param -> new SimpleBooleanProperty(param.getValue().isDynamicOrder()));
        dynamicIdColumn.setCellValueFactory(param ->
        {
            if(param.getValue().isDynamicOrder())
            return new SimpleObjectProperty( param.getValue().getOrderId());
        else
            return new SimpleStringProperty(" ");
//            new SimpleObjectProperty<>(param.getValue().getOrderId())
        });

        ordersTable.getColumns().setAll(dateColumn,
                                        itemsAmomuntColumn,
                                        itemsCostColumn,
                                        deliveriesCostColumn,
                                        totalCostColumn,
                                        isDynamicColumn,
                                        dynamicIdColumn);

        paginationBtn.setPageCount((int) Math.ceil(storeOrdersList.size() / (double)rowsPerPage ));

        //paginationBtn.setPageFactory(this::createPage);

        // Create table pages
        createPage(0);
        // Listener to notify whenever the value of the ObservableValue changes
        paginationBtn.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                createPage(newIndex.intValue()));
    }
}
