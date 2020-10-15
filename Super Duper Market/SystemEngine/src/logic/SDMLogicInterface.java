package logic;

import DtoObjects.*;
import Exceptions.*;
import superDuperMarket.Region;
import superDuperMarket.StoreOrder;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

public interface SDMLogicInterface {
    void importDataFromXmlFile(InputStream inputStream , String ownerName) throws InvalidFileExtension, FileNotFoundException, JAXBException, ItemNotFoundInStoresException, ValueOutOfRangeException, StoreItemNotFoundInSystemException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException, RegionAlreadyExistException;

    boolean isUserExist(String userName);
    void addCustomer(String name);
    void addOwner(String name);
    List<UserDto> getUsersDetails();
    List<RegionBaseDataDto> getAllRegionsBaseData();
    List<String> getRegionsNames();
    void chargeCustomerBalance(String customerName, double amountToAdd, Date chargeDate);
    double getBalance(String userName);
    List<TransactionDto> getTransactionsDetails(String customerName);
    Region getRegionByName(String regionName );
    List<FeedbackDto> getOwnerFeedbackDetailsDetails(String ownerName);
    List<StoreOrderDto> getStoreOrderHistory(int storeId, String regionName);
    List<OrderDto> getCustomerOrderHistory(String customerName);
    List<StoreOrder> getOrderStores(Integer orderId, String regionName);
    void addFeedbackToOwner(String regionName, String customer, Date date, Integer rate, String review,int storeid);
    Owner getOwnerByName(String ownerName);
}
