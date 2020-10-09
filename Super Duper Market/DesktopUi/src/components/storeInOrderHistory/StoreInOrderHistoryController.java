package components.storeInOrderHistory;

import DtoObjects.ItemInStoreOrderDto;
import DtoObjects.PurchaseCategory;
import DtoObjects.StoreDto;
import DtoObjects.StoreOrderDto;
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

public class StoreInOrderHistoryController {
    @FXML
    private Label storeIdLbl;

    @FXML
    private Label ppkLbl;

    @FXML
    private Label distanceFromCustomerLbl;

    @FXML
    private Label deliveryCostLbl;

    @FXML
    private TableView<ItemInStoreOrderDto> itemsInOrderTable;

    @FXML
    private TableColumn<ItemInStoreOrderDto, Integer> idColumn;

    @FXML
    private TableColumn<ItemInStoreOrderDto, String> nameColumn;

    @FXML
    private TableColumn<ItemInStoreOrderDto, String> purchaseCategoryColumn;

    @FXML
    private TableColumn<ItemInStoreOrderDto, Double> amountColumn;

    @FXML
    private TableColumn<ItemInStoreOrderDto, String> priceColumn;

    @FXML
    private TableColumn<ItemInStoreOrderDto, String> totalCostColumn;

    @FXML
    private TableColumn<ItemInStoreOrderDto, String> isFromSaleColumn;

    @FXML
    private Pagination paginationBtn;
    @FXML
    private Label storeNameLbl;

    private List<ItemInStoreOrderDto> itemsInStoreOrderDataList;
//    private LogicInterface sdmLogic;
    private final int rowsPerPage=5;
    Set<ItemInStoreOrderDto> itemsInOrderDataSet;

    public void showData(StoreDto storeDetails, StoreOrderDto storeOrderDetails)
    {
        storeNameLbl.setText(storeDetails.getName());
        storeIdLbl.setText(String.valueOf(storeDetails.getId()));
        ppkLbl.setText(String.valueOf(storeDetails.getDeliveryPpk())+" ₪");
        distanceFromCustomerLbl.setText(String.format("%.2f KM",storeOrderDetails.getDistanceFromCustomer()));
        deliveryCostLbl.setText(String.format("%.2f ₪",storeOrderDetails.getDeliveryTotalPrice()));

        showItemsData(storeOrderDetails.getItemsInStoreOrderDetails());
    }



    public void createData()
    {
        //Set<ItemInSystemDto> itemsDataSet=sdmLogic.getItemsDetails();
        itemsInStoreOrderDataList = new ArrayList<ItemInStoreOrderDto>();
        itemsInStoreOrderDataList.addAll(this.itemsInOrderDataSet);

    }

    private void createPage(int pageIndex) {

        int fromIndex = pageIndex * rowsPerPage;
        int toIndex = Math.min(fromIndex + rowsPerPage, itemsInStoreOrderDataList.size());
        itemsInOrderTable.setItems(FXCollections.observableArrayList(itemsInStoreOrderDataList.subList(fromIndex, toIndex)));
        //return new BorderPane(itemsTable);
        //return this.itemsTable;
    }

    public void showItemsData(Set<ItemInStoreOrderDto> itemsInOrderDataSet)
    {
        this.itemsInOrderDataSet = itemsInOrderDataSet;

        // Get Items data into ArrayList
        createData();

        // Create properties

        idColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getId()));
        nameColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getName()));
        purchaseCategoryColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getPurchaseCategory().toString()));
        amountColumn.setCellValueFactory(param -> new SimpleObjectProperty<>(param.getValue().getAmount()));
        priceColumn.setCellValueFactory(param ->
        {
            String str="(Per ";
            if(param.getValue().getPurchaseCategory()== PurchaseCategory.Weight)
                str+="KG)";
            else
                str+="unit)";
            return new SimpleStringProperty(String.format("%.2f ₪ %s", param.getValue().getPrice(), str));
        });
        totalCostColumn.setCellValueFactory(param -> new SimpleStringProperty(String.format("%.2f ₪",param.getValue().getTotalPrice())));

        //todo: change yes/no
        isFromSaleColumn.setCellValueFactory(param ->
        {
            String val="";
           if(param.getValue().isFromSale())
               val="Yes";
           else
               val="No";

           return new SimpleStringProperty(val);
        });


        itemsInOrderTable.getColumns().setAll(idColumn,nameColumn,purchaseCategoryColumn,amountColumn,priceColumn,totalCostColumn,isFromSaleColumn );

        //paginationBtn.setPageCount(itemsDataList.size() / rowsPerPage + 1); //TODO: handle even/odd
        paginationBtn.setPageCount((int) Math.ceil(itemsInStoreOrderDataList.size() / (double)rowsPerPage ));

        //paginationBtn.setPageFactory(this::createPage);

        // Create table pages
        createPage(0);
        // Listener to notify whenever the value of the ObservableValue changes
        paginationBtn.currentPageIndexProperty().addListener((obs, oldIndex, newIndex) ->
                createPage(newIndex.intValue()));
    }


    public void showDataInOrderSummary(StoreDto storeDetails,
                                       double distanceFromCustomer,
                                       double deliveryCost,
                                       Set<ItemInStoreOrderDto> itemsToOrderIncludeSalesSet)
    {
        storeNameLbl.setText(storeDetails.getName());
        storeIdLbl.setText(String.valueOf(storeDetails.getId()));
        ppkLbl.setText(String.valueOf(storeDetails.getDeliveryPpk())+" ₪");
        distanceFromCustomerLbl.setText(String.format("%.2f km",distanceFromCustomer));
        deliveryCostLbl.setText(String.format("%.2f ₪",deliveryCost));

        showItemsData(itemsToOrderIncludeSalesSet);
    }


}
