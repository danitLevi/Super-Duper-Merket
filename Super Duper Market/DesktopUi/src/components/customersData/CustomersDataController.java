package components.customersData;

import DtoObjects.CustomerDto;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Pagination;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CustomersDataController {
    @FXML
    private TableView<CustomerDto> itemsTable;
    @FXML
    private TableColumn<CustomerDto, Integer> idColumn;

    @FXML
    private TableColumn<CustomerDto, String> nameColumn;

    @FXML
    private TableColumn<CustomerDto, String> locationColumn;

    @FXML
    private TableColumn<CustomerDto, Integer> ordersAmountColumn;

    @FXML
    private TableColumn<CustomerDto, String> avgOrderPriceColumn;

    @FXML
    private TableColumn<CustomerDto, String> avgDeliveryPriceColumn;

    @FXML
    private Pagination paginationBtn;


    private List<CustomerDto> customersDataList;
    //private LogicInterface sdmLogic;
    private final int rowsPerPage=10;
    private Set<CustomerDto> customersDataSet;




    public void createData()
    {
        //Set<CustomerDto> customersDataSet=sdmLogic.getCustomersDetails();
        this.customersDataList = new ArrayList<CustomerDto>();
        this.customersDataList.addAll(this.customersDataSet);

    }

    private void createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, customersDataList.size());
        itemsTable.setItems(FXCollections.observableArrayList(customersDataList.subList(fromIndex, toIndex)));
        //return new BorderPane(itemsTable);
        //return this.itemsTable;
    }

    public void showData(Set<CustomerDto> customersDataSet)
    {
        this.customersDataSet=customersDataSet;
        // Get Items data into ArrayList
        createData();

        // Create properties

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        locationColumn.setCellValueFactory(param -> {
            return new SimpleStringProperty(String.format("(%d,%d)", param.getValue().getxCoordinate() ,param.getValue().getyCoordinate() ));
        });
        ordersAmountColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getOrdersAmount()));
        avgOrderPriceColumn.setCellValueFactory(param -> new SimpleStringProperty(String.format("%.2f ₪",param.getValue().getAvgOrderPrice())));
        avgDeliveryPriceColumn.setCellValueFactory(param -> new SimpleStringProperty(String.format("%.2f ₪",param.getValue().getAvgDeliveryPrice())));


        itemsTable.getColumns().setAll(idColumn,nameColumn,locationColumn,ordersAmountColumn,avgOrderPriceColumn,avgDeliveryPriceColumn);

        double num = customersDataList.size() / (double)rowsPerPage ;
        paginationBtn.setPageCount((int) Math.ceil(customersDataList.size() / (double)rowsPerPage ));
        //paginationBtn.setPageFactory(this::createPage);

        // Create table pages
        createPage(0);
        // Listener to notify whenever the value of the ObservableValue changes
        paginationBtn.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                createPage(newIndex.intValue()));
    }
}
