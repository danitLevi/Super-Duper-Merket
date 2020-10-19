package superDuperMarket;

import DtoObjects.*;
import Exceptions.DeleteItemFromItsOnlySellerException;
import Exceptions.DeleteStoreOnlyItemException;
import Exceptions.DoubleObjectInCoordinateException;
import logic.Customer;
import logic.Owner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface RegionInterface {
    List<StoreDto> getStoresDetails();
    StoreDto getStoreDetails(int storeId);
    StoreDto getSpecificStoreDetails(int storeId);
    List<ItemInSystemDto> getItemsDetails() ;

    boolean isValidStoreId(int id);

    String getStoreName(int storeId);

    List<ItemInStoreDto> getStoreItemsDetails(int storeId);

//    Set<CustomerDto> getCustomersDetails();

//    CustomerDto getCustomerDetails(int customerId);

    boolean isItemExistInStore(int itemId, int storeId);

    boolean isItemExistInSystem(int itemId);

    //boolean hasOneSeller(int itemId);

    void deleteItemFromStore(int itemId, int storeId) throws DeleteItemFromItsOnlySellerException, DeleteStoreOnlyItemException;

    void addItemToStore(int storeId, int itemId, double itemPrice);

    void updateStoreItemPrice(int storeId, int itemId, double itemPrice);

    boolean isValidCoordinate(int coordinateVal);

    boolean isStoreExistHere(Coordinate location);

    double getStoreItemPrice(int storeId, int itemId);

    double getDistanceFromStore(int storeId, Coordinate orderLocation);

    int getStorePPK(int storeId);

    double getDeliveryPrice(int storeId, Coordinate orderLocation);
    double getDeliveryPriceFromCoordinates(int storeId, int xCorrdinate,int yCoordinate);

//    double getDeliveryPriceToCustomer(int storeId,int customerId);

    double getDeliveryPriceFromMultipleStores(Set<Integer> storesIds, Coordinate orderLocation);

//    Map<Integer, Map<Integer, Double>> getMinimalItemsBag(Map<Integer, Double> itemIdToAmount);

    PurchaseCategory getItemPurchaseCategory(int itemId);

    List<StoreOrderDto> getStoreOrderDetails(int storeId);

//    Set<OrderDto> getOrdersInSystemDetails();
//
//    void saveStaticOrder(Date orderDate,
//                         OrderTypes orderType,
//                         Map<Integer, Double> itemIdToItemAmount,
//                         int storeId,
//                         Customer customer);
//
//    void saveDynamicOrder(Date orderDate,
//                          OrderTypes orderType,
//                          Map<Integer, Map<Integer, Double>> dynamicStoreIdToOrderedItem,
//                          Customer customer);

    Set<OrderDto> getRegionOrdersDetails();

    void readOrdersHistoryFromFile(String filePath) throws IOException;
    void writeOrderHistoryToFile(String filePath) throws IOException;
    //boolean isStoreHasOneItem(int storeId);

    ItemDto getItemDetails(int itemId);

    Set<ItemInSystemDto> getItemsInSystemNotInStore(int storeId);

    boolean isItemInStoreInSale(int itemId,int storeId);

//    Map<SaleDto,Integer> getStoreSalesInOrder(int storeId , Map<Integer,Double> itemIdToItemAmount); //todo: continue !!!!

    Map<Integer, Map<SaleDto, Integer>> getSalesInOrder(Map<Integer, Map<Integer, Double>> orderMinimalPriceBag);


    int getItemCheapestSellerId(int itemId);

    Set<StoreInCalcDyanmicOrderDto>  getStoresInDynamicOrderDetails(Map<Integer, Map<Integer, Double>> itemsBag ,int customerId);
    Set<ItemInStoreOrderDto> getWantedItemsInStoreDetails(int storeId,Map<Integer, Double> itemsToOrderRegular,Map<OfferDto,Integer> itemsToOrderFromSale);
    double getStoreDistanceFromCustomer(int storeId,int customerId);
    double getStoreDeliveryCostToCustomer(int storeId , int customerId);
    void saveOrder(LocalDate orderDate,
                          Customer currCustomer,
                          boolean isDynamicOrder,
                          Map<Integer,Map<Integer,Double>> dynamicStoreIdToOrderedItem,
                          Map<Integer, Map<OfferDto,Integer>> storeIdToItemsToOrderFromSales);

    String getRegionName();
    Owner getRegionOwner();

    Map<Integer, Map<Integer, Double>> getMinimalItemsBag(Map<Integer,Double> itemIdToAmount);
    List<StoreInCalcDyanmicOrderDto>  getStoresInDynamicOrderDetails(Map<Integer, Map<Integer, Double>> itemsBag ,
                                                                     int orderXCoordinate,
                                                                     int orderYCoordinate);


//    void importDataFromXmlFile(InputStream inpStream) throws JAXBException, ValueOutOfRangeException, StoreItemNotFoundInSystemException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException, ItemNotFoundInStoresException;
    List<StoreOrder> getStoresInOrder(Integer orderId);
    void addNewStoreToRegion(Owner owner, String storeName, int ppk, int xCoordinate, int yCoordinate, List<Sell> itemsList) throws DoubleObjectInCoordinateException;
    Map<Integer, Sell> createItemIdToItemSellMap(List<Sell> itemsList);
    int getNewStoreId();
    String getStoreOwnerName(int storeId);

}