package logic;

import DtoObjects.*;
import Exceptions.*;
import generatedClassesJaxb.SuperDuperMarketDescriptor;
import superDuperMarket.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.time.LocalDate;
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
        Region SDMRegionObj= getRegionByName(regionName);
        Customer customer= (Customer) userIdToUser.get(customerId);
        //todo save order (and add customer )
        //SDMRegionObj.saveOrder(customer)
    }

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

    public List<FeedbackDto> getOwnerFeedbackDetailsDetails(String ownerName)
    {
        Owner owner= (Owner) userIdToUser.get(ownerName);
        return owner.getFeedbacksDetails();
    }

    public List<StoreOrderDto> getStoreOrderHistory(int storeId, String regionName)
    {
        List<StoreOrderDto> storeOrderList = new ArrayList<>();
        Region region = getRegionByName(regionName);
        storeOrderList = region.getStoreOrderDetails(storeId);

        return storeOrderList;
    }

    public List<OrderDto> getCustomerOrderHistory(String customerName)
    {
        List<OrderDto> customerOrdersList = new ArrayList<>();
        Customer customer = (Customer) userIdToUser.get(customerName);

        Map<Integer, String> orders = customer.getOrderIdToRegionName();

        for (Integer currOrderId : orders.keySet())
        {
            Region orderRegion = getRegionByName(orders.get(currOrderId));
            Order currOrder=orderRegion.getOrderIdToOrder().get(currOrderId);
            Map<Integer,StoreOrderDto> StoreIdToStoreOrderDto = new HashMap<>();
            for (Integer currStoreId:currOrder.getStoreIdToStoreOrder().keySet())
            {
                StoreOrderDto currStoreOrderDetails=orderRegion.getStoreOrderDetails(currOrder,currStoreId);
                StoreIdToStoreOrderDto.put(currStoreId,currStoreOrderDetails);
            }

            OrderDto currOrderDetails=new OrderDto(currOrder.getDate(),
                                            currOrder.getAmountOfOrderedItemsByUits(orderRegion.getItemIdToItems()),
                                            currOrder.getItemsInOrderPrice(),
                                            currOrder.getOrderDeliveryPrice(),
                                            currOrder.getOrderTotalPrice(),
                                            currOrder.getId(),
                                            currOrder.getAmountOfOrderedItemsTypes(),
                                            StoreIdToStoreOrderDto,
                                            currOrder.getOrderLocation().getX(),
                                            currOrder.getOrderLocation().getY());
//            currStoreOrderDto=getStoreOrderDetails(currOrder,storeId);
//            storeOrdersDetails.add(currStoreOrderDto);
        }

        return customerOrdersList;
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
        Customer customer= (Customer) userIdToUser.get(customerName);
        customer.chargeBalance(amountToAdd,chargeDate);
    }

    public double getBalance(String userName)
    {
        User currUser=  userIdToUser.get(userName);
        return currUser.getCurrBalance();
    }

    public List<TransactionDto> getTransactionsDetails(String userName)
    {
        Set<TransactionDto> CustomerTransactionsDetails=new HashSet<>();
        User currUser= userIdToUser.get(userName);
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
        for (User curruser:userIdToUser.values())
        {
            if(curruser.getName().equals(ownerName))
                return (Owner)curruser;
        }
        return null;
    }
}
