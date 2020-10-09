package logic;

import DtoObjects.RegionBaseDataDto;
import DtoObjects.UserDto;
import Exceptions.*;
import generatedClassesJaxb.SuperDuperMarketDescriptor;
import superDuperMarket.Region;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class SDMLogic implements SDMLogicInterface {

    private Map<String, User> userIdToUser; // todo: to separate to customers and store owners ??
    Set<Region> regions;

    public SDMLogic()
    {
        this.userIdToUser=new HashMap<>();
        this.regions=new HashSet<>();
    }
    public SDMLogic(Map<Integer, User> userIdToUser, Set<Region> regions) {
        this.userIdToUser = new HashMap<>();
        this.regions = new HashSet<>();
    }



    public Map<String, User> getUserIdToUser() {
        return userIdToUser;
    }

    public void setUserIdToUser(Map<String, User> userIdToUser) {
        this.userIdToUser = userIdToUser;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

    public void saveOrder(String regionName , int customerId)
    {
        Region SDMRegionObj=findRegionByName(regionName);
        Customer customer= (Customer) userIdToUser.get(customerId);
        //todo save order (and add customer )
        //SDMRegionObj.saveOrder(customer)
    }

    public Region findRegionByName(String regionName )
    {

        for (Region currRegion:regions)
        {
            if(currRegion.getRegionName().equals(regionName))
                return currRegion;
        }
        return null;
    }


    public boolean isUserExist(String userName)
    {
        return userIdToUser.containsKey(userName.toLowerCase());
    }

    public void addCustomer(String name)
    {
        if(!isUserExist(name))
        {
            Customer newCustomer=new Customer(name);
            userIdToUser.put(newCustomer.getName().toLowerCase(),newCustomer);
        }
        else
            throw new UserAlreadyExsistException(name);

    }
    public void addOwner(String name)
    {
        if(!isUserExist(name))
        {
            Owner newOwner =new Owner(name);
            userIdToUser.put(newOwner.getName().toLowerCase(), newOwner);
        }
        else
            throw new UserAlreadyExsistException(name);

    }


    public List<UserDto> getUsersDetails() {
        List<UserDto> usersDetails=new LinkedList<>();
        UserDto currUserDetails;
        String roleStr="";
        for (User currUser : userIdToUser.values())
        {
            if (currUser instanceof Customer )
            {
                roleStr="Customer";
            }
            else
            {
                roleStr="Store owner";
            }
            currUserDetails=new UserDto(roleStr,currUser.getName());
            usersDetails.add(currUserDetails);
        }
        return  usersDetails;
    }

    public void importDataFromXmlFile(InputStream inputStream ,String ownerName) throws InvalidFileExtension, FileNotFoundException, JAXBException, ItemNotFoundInStoresException, ValueOutOfRangeException, StoreItemNotFoundInSystemException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException, RegionAlreadyExistException {


        Region superDuperMarketObj = null;
        Owner owner=getOwner(ownerName);
//        if (!getFileExtension(filePath).equals("xml"))
//        {
//            throw new InvalidFileExtension("xml");
//        }
//
//        File xmlFile = new File(filePath);
//        if(!xmlFile.exists())
//        {
//            throw new FileNotFoundException();
//        }
//        InputStream inputStream = new FileInputStream(xmlFile);
        SuperDuperMarketDescriptor superDuperMarketJaxbObj = deserializeFrom(inputStream);
        String regionName=superDuperMarketJaxbObj.getSDMZone().getName();
        if(isRegionExist(regionName))
        {
            throw new RegionAlreadyExistException(regionName);
        }

        superDuperMarketObj=new Region(superDuperMarketJaxbObj, owner);

        regions.add(superDuperMarketObj);
    }

    private static SuperDuperMarketDescriptor deserializeFrom(InputStream in) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance("generatedClassesJaxb");
        Unmarshaller u = jc.createUnmarshaller();
        return (SuperDuperMarketDescriptor) u.unmarshal(in);
    }

    public boolean isRegionExist(String regionName)
    {
        for (Region currRegion:regions)
        {
            if(currRegion.getRegionOwner().getName().toLowerCase().equals(regionName.toLowerCase()))
                return true;
        }
        return false;
    }

//    private static String getFileExtension(String filePath)
//    {
//
//        int lastIndexOf = filePath.lastIndexOf('.');
//        if (lastIndexOf == -1) {
//            return "";  // Empty extension
//        }
//        return filePath.substring(lastIndexOf+1).toLowerCase();
//    }

    public Owner getOwner(String ownerName)
    {
        return (Owner) userIdToUser.get(ownerName);
    }

    public Set<RegionBaseDataDto> getAllRegionsBaseData()
    {
        Set<RegionBaseDataDto> regionBaseDataDetails=new HashSet<>();
        RegionBaseDataDto currRegionBaseData;
        for (Region currRegion:regions)
        {
            currRegionBaseData =new RegionBaseDataDto(currRegion.getRegionOwner().getName(),
                    currRegion.getRegionName(),
                    currRegion.getItemsTypesAmount(),
                    currRegion.getStoresAmount(),
                    currRegion.getOrdersAmount(),
                    currRegion.getAvgOrderPrice());

            regionBaseDataDetails.add(currRegionBaseData);
        }
        return regionBaseDataDetails;
    }

    public void chargeCustomerBalance(String customerName, double amountToAdd)
    {
        Customer customer= (Customer) userIdToUser.get(customerName);
        customer.chargeBalance(amountToAdd);
    }

    public double getCustomerBalance(String customerName)
    {
        Customer currCustomer= (Customer) userIdToUser.get(customerName);
        return currCustomer.getCurrBalance();
    }

}
