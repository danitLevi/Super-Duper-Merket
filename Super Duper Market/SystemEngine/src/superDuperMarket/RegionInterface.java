package superDuperMarket;

import DtoObjects.*;
import Exceptions.DeleteItemFromItsOnlySellerException;
import Exceptions.DeleteStoreOnlyItemException;
import logic.Customer;
import logic.Owner;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Set;

public interface RegionInterface {
    Set<StoreDto> getStoresDetails();
    StoreDto getStoreDetails(int storeId);
    StoreDto getSpecificStoreDetails(int storeId);
    Set<ItemInSystemDto> getItemsDetails();

    boolean isValidStoreId(int id);

    String getStoreName(int storeId);

    Set<ItemInStoreDto> getStoreItemsDetails(int storeId);

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

    double getDeliveryPriceToCustomer(int storeId,int customerId);

    double getDeliveryPriceFromMultipleStores(Set<Integer> storesIds, Coordinate orderLocation);

//    Map<Integer, Map<Integer, Double>> getMinimalItemsBag(Map<Integer, Double> itemIdToAmount);

    PurchaseCategory getItemPurchaseCategory(int itemId);

    Set<StoreOrderDto> getStoreOrderDetails(int storeId);

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

    Set<OrderDto> getOrdersDetails();

    void readOrdersHistoryFromFile(String filePath) throws IOException;
    void writeOrderHistoryToFile(String filePath) throws IOException;
    //boolean isStoreHasOneItem(int storeId);

    ItemDto getItemDetails(int itemId);

    Set<ItemInSystemDto> getItemsInSystemNotInStore(int storeId);

    boolean isItemInStoreInSale(int itemId,int storeId);

    Map<SaleDto,Integer> getStoreSalesInOrder(int storeId , Map<Integer,Double> itemIdToItemAmount); //todo: continue !!!!

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

//    boolean isUserExist(String userName);
//    void addCustomer(String name);
//    void addStoreOwner(String name);
//    List<UserDto> getUsersDetails();
    String getRegionName();
    Owner getRegionOwner();
//    void importDataFromXmlFile(InputStream inpStream) throws JAXBException, ValueOutOfRangeException, StoreItemNotFoundInSystemException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException, ItemNotFoundInStoresException;
}