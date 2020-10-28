package logic;

import DtoObjects.*;
import Exceptions.*;
import generatedClassesJaxb.SuperDuperMarketDescriptor;
//<<<<<<< HEAD
//import superDuperMarket.Order;
//import superDuperMarket.Region;
//=======
import superDuperMarketRegion.*;
//>>>>>>> bd26cd2d1ef95b2547d381cb4a4664ea48d6d806

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.*;

public class SDMLogic implements SDMLogicInterface {

    private Map<String, User> userNameToUser; // todo: to separate to customers and store owners ??
    Set<Region> regions;

    public SDMLogic()
    {
        this.userNameToUser =new HashMap<>();
        this.regions=new HashSet<>();
    }
    public SDMLogic(Map<Integer, User> userNameToUser, Set<Region> regions) {
        this.userNameToUser = new HashMap<>();
        this.regions = new HashSet<>();
    }



    public Map<String, User> getUserNameToUser() {
        return userNameToUser;
    }

    public void setUserNameToUser(Map<String, User> userNameToUser) {
        this.userNameToUser = userNameToUser;
    }

    public Set<Region> getRegions() {
        return regions;
    }

    public void setRegions(Set<Region> regions) {
        this.regions = regions;
    }

//    public void saveOrder(String regionName , int customerId)
//    {
//        Region SDMRegionObj= getRegionByName(regionName);
//        Customer customer= (Customer) userNameToUser.get(customerId);
//        //todo save order (and add customer )
//        //SDMRegionObj.saveOrder(customer)
//    }

    public Region getRegionByName(String regionName )
    {

        for (Region currRegion:regions)
        {
            if(currRegion.getRegionName().equals(regionName))
                return currRegion;
        }
        return null;
    }

    public List<String> getRegionsNames()
    {
        List<String> regionsNames = new ArrayList<>();

        for (Region currRegion : regions)
        {
            regionsNames.add(currRegion.getRegionName());
        }

        return regionsNames;
    }

    public  Customer getCustomer(String customerName)
    {
        return (Customer) userNameToUser.get(customerName);
    }

    public List<FeedbackDto> getOwnerFeedbackDetailsDetails(String ownerName)
    {
        Owner owner= (Owner) userNameToUser.get(ownerName);
        return owner.getFeedbacksDetails();
    }

    public List<StoreOrderDto> getStoreOrderHistory(int storeId, String regionName)
    {
        List<StoreOrderDto> storeOrderList = new ArrayList<>();
        Region region = getRegionByName(regionName);
        storeOrderList = region.getStoreOrderDetails(storeId);

        return storeOrderList;
    }


    public List<OrderDto> getCustomerOrderHistoryInRegion(String customerName, String regionName)
    {
        List<OrderDto> customerOrdersDetails=new ArrayList<>();

        Customer customer = (Customer) userNameToUser.get(customerName);
        List<Integer> customerOrdersIdsInRegion=customer.getOrdersIdInRegion(regionName);

        if(customerOrdersIdsInRegion.size()!=0)
        {
            Region currRegion=getRegionByName(regionName);

            customerOrdersDetails=currRegion.getWantedOrdersDetails(customerOrdersIdsInRegion);
        }

        return customerOrdersDetails;
    }

    public boolean isUserExist(String userName)
    {
        Map<String,User> lowerCaseUserNameToUser=new HashMap<>();
        for(String currUserName:userNameToUser.keySet())
        {
            lowerCaseUserNameToUser.put(currUserName.toLowerCase(), userNameToUser.get(currUserName));
        }

        return (lowerCaseUserNameToUser.containsKey(userName.toLowerCase()));
    }

    public void addCustomer(String name)
    {
        if(!isUserExist(name))
        {
            Customer newCustomer=new Customer(name);
            userNameToUser.put(newCustomer.getName(),newCustomer);
        }
        else
            throw new UserAlreadyExsistException(name);

    }

    public void addOwner(String name)
    {
        if(!isUserExist(name))
        {
            Owner newOwner =new Owner(name);
            userNameToUser.put(newOwner.getName(), newOwner);
        }
        else
            throw new UserAlreadyExsistException(name);

    }


    public List<UserDto> getUsersDetails() {
        List<UserDto> usersDetails=new LinkedList<>();
        UserDto currUserDetails;
        String roleStr="";
        for (User currUser : userNameToUser.values())
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

    public String importDataFromXmlFile(InputStream inputStream , String ownerName, String fileName) throws InvalidFileExtension, FileNotFoundException, JAXBException, ItemNotFoundInStoresException, ValueOutOfRangeException, StoreItemNotFoundInSystemException, ItemAlreadyExistInStoreException, DoubleObjectIdInSystemException, DoubleObjectInCoordinateException, ItemInSaleNotFoundInStoreException, RegionAlreadyExistException {


        Region newRegion = null;
        Owner owner=getOwner(ownerName);
        if (!getFileExtension(fileName).equals("xml"))
        {
            throw new InvalidFileExtension("xml");
        }
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

        newRegion=new Region(superDuperMarketJaxbObj, owner);

        regions.add(newRegion);

        return newRegion.getRegionName();
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
            if(currRegion.getRegionName().toLowerCase().equals(regionName.toLowerCase()))
                return true;
        }
        return false;
    }

    private static String getFileExtension(String filePath)
    {

        int lastIndexOf = filePath.lastIndexOf('.');
        if (lastIndexOf == -1) {
            return "";  // Empty extension
        }
        return filePath.substring(lastIndexOf+1).toLowerCase();
    }

    public Owner getOwner(String ownerName)
    {
        return (Owner) userNameToUser.get(ownerName);
    }

    public List<RegionBaseDataDto> getAllRegionsBaseData()
    {
        List<RegionBaseDataDto> regionBaseDataDetails=new ArrayList<>();
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

    public void chargeCustomerBalance(String customerName, double amountToAdd, Date chargeDate)
    {
        Customer customer= (Customer) userNameToUser.get(customerName);
        customer.chargeBalance(amountToAdd,chargeDate);
    }

    public double getBalance(String userName)
    {
        User currUser=  userNameToUser.get(userName);
        return currUser.getCurrBalance();
    }

    public List<TransactionDto> getTransactionsDetails(String userName)
    {
//        Set<TransactionDto> CustomerTransactionsDetails=new HashSet<>();
        User currUser= userNameToUser.get(userName);
        return currUser.getTransactionsDetails();

    }

    public List<StoreOrder> getOrderStores(Integer orderId, String regionName){
        Region region = getRegionByName(regionName);
        return region.getStoresInOrder(orderId);
    }

    public void addFeedbackToOwner(String regionName, String customer, Date date, Integer rate, String review,int storeid)
    {
        Region region = getRegionByName(regionName);
        Store store = region.getStoreIdToStore().get(storeid);
        Owner currUser= store.getStoreOwner();
        currUser.addFeedback(customer, date, rate, review, store.getName());
    }

    public Owner getOwnerByName(String ownerName)
    {
        for (User curruser:userNameToUser.values())
        {
            if(curruser.getName().equals(ownerName))
                return (Owner)curruser;
        }
        return null;
    }
}
