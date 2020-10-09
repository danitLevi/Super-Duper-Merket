package logic;

import DtoObjects.RegionBaseDataDto;
import DtoObjects.UserDto;
import Exceptions.*;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Set;

public interface SDMLogicInterface {
    void importDataFromXmlFile(InputStream inputStream , String ownerName) throws InvalidFileExtension, FileNotFoundException, JAXBException, ItemNotFoundInStoresException, ValueOutOfRangeException, StoreItemNotFoundInSystemException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException, RegionAlreadyExistException;

    boolean isUserExist(String userName);
    void addCustomer(String name);
    void addOwner(String name);
    List<UserDto> getUsersDetails();
    Set<RegionBaseDataDto> getAllRegionsBaseData();

    void chargeCustomerBalance(String customerName, double amountToAdd);
    double getCustomerBalance(String customerName);
    }
