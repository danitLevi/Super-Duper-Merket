package logic;

import DtoObjects.RegionBaseDataDto;
import DtoObjects.TransactionDto;
import DtoObjects.UserDto;
import Exceptions.*;
import superDuperMarket.Region;

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

    void chargeCustomerBalance(String customerName, double amountToAdd, Date chargeDate);
    double getBalance(String userName);
    List<TransactionDto> getTransactionsDetails(String customerName);
    Region getRegionByName(String regionName );
    }
