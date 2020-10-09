//import DtoObjects.*;
//import Exceptions.*;
//import superDuperMarket.Coordinate;
//import superDuperMarket.LogicInterface;
//import superDuperMarket.OrderTypes;
//
//import javax.xml.bind.JAXBException;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.*;
//
//public final class SuperDuperMarketUI
// {
//     public static class Constants
//     {
//         public final static int NO_VALUE=-1;
//         public final static String LINE_OUTER_SEPARATOR="============================================\n";
//         public final static String LINE_INNER_SEPARATOR="............................................\n";
//     }
//     }
//
//     private static Scanner myScanner = new Scanner(System.in);
//     private static LogicInterface sdm;
//
//     private SuperDuperMarketUI() { }
//
//     public static void start() {
//         System.out.println("Welcome to Super Duper Market!");
//         handleXmlMainMenu();
//     }
//
//     public static  void handleXmlMainMenu() {
//        showXmlMainMenu();
//        int choice= getMenuChoice(XmlMainMenuValues.numOfEnumsVal);
//        XmlMainMenuValues chosenVal=XmlMainMenuValues.values()[choice-1];
//        switch (chosenVal) {
//             case READ_XML:
//                 readXml(XmlRead.FIRST_FILE);
//                 break;
//             case EXIT:
//                 exit();
//                 break;
//        }
//     }
//
//     public static  void handleMenu()
//     {
//         while (true) {
//             showMenu();
//             int choice= getMenuChoice(MenuValues.numOfEnumsVal);
//             MenuValues chosenVal=MenuValues.values()[choice-1];
//             switch (chosenVal)
//             {
//                 case READ_XML:
//                     readXml(XmlRead.NEW_FILE);
//                     break;
//                 case SHOW_STORES_DETAILS:
//                     PrintStoresDetails();
//                     break;
//                 case SHOW_ITEMS:
//                     PrintItemsDetails();
//                     break;
//                 case ORDER:
//                     order();
//                     break;
//                 case SHOW_HISTORY:
//                     showHistory();
//                     break;
//                 case UPDATE_ITEMS_DATA:
//                     handleUpdateItemsDataSubMenu();
//                     break;
//                 case EXPORT_ORDER_HISTORY_TO_FILE:
//                     ExportOrdersHistoryData();
//                     break;
//                 case EXIT:
//                     exit();
//                     break;
//             }
//         }
//     }
//
//     public static void handleUpdateItemsDataSubMenu()
//     {
//         int storeId= selectStoreId();
//         showUpdateItemsDataSubMenu(sdm.getStoreName(storeId));
//         int choice= getMenuChoice(UpdateItemsDataMenuValues.numOfEnumsVal);
//         UpdateItemsDataMenuValues chosenVal=UpdateItemsDataMenuValues.values()[choice-1];
//         switch (chosenVal)
//         {
//             case DELETE_ITEM:
//                 deleteItemFromStore(storeId);
//                 break;
//             case ADD_ITEM:
//                 addItemToStore(storeId);
//                 break;
//             case UPDATE_ITEM_PRICE:
//                 updateStoreItemPrice(storeId);
//                 break;
//             case BACK:
//                 handleMenu();
//                 break;
//             case EXIT:
//                exit();
//                 break;
//         }
//     }
//
//     public static void handleImportOrderHistoryMenu()
//     {
//         showImportOrdersHistoryMenu();
//         int choice= getMenuChoice(ImportOrderHostoryMenuValues.numOfEnumsVal);
//         ImportOrderHostoryMenuValues chosenVal=ImportOrderHostoryMenuValues.values()[choice-1];
//
//         switch (chosenVal)
//         {
//             case READ_NEW_XML:
//                 readXml(XmlRead.NEW_FILE_BEFORE_IMPORT);
//                 break;
//             case READ_ORDER_HISTORY_FILE:
//                 importOrdersHistoryData();
//                 break;
//             case COUNTINUE:
//                 handleMenu();
//                 break;
//             case EXIT:
//                 exit();
//                 break;
//         }
//     }
//     public static void showMenu()
//     {
//         System.out.println("Please select an option (enter an integer number between 1 to 8) \n" +
//                 "1.Read data from new xml file \n" +
//                 "2.Show all stores in system details \n" +
//                 "3.Show all Items in system details\n" +
//                 "4.Order\n" +
//                 "5.Show orders history \n" +
//                 "6.Update item details in certain store\n"+
//                 "7.Export orders history to file\n"+
//                 "8.Exit");
//     }
//     public static void showXmlMainMenu()
//     {
//         System.out.println("Please select an option (enter an integer number between 1 to 2) \n" +
//                 "1.Read data from xml file \n" +
//                 "2.Exit");
//     }
//
//
//     public static void showImportOrdersHistoryMenu()
//     {
//         System.out.println("Please select an option (enter an integer number between 1 to 4) \n" +
//                 "1.Read data from new xml file \n" +
//                 "2.Import orders history data from file \n" +
//                 "3.Continue without import orders history data from file\n"+
//                 "4.Exit");
//     }
//
//     public static void getXmlFilePathAndLoadData(XmlRead readingType)
//     {
//         String path= myScanner.nextLine();
//         try {
//             sdm= LoadData.LoadSdmData.importDataFromXmlFile(path);
//             System.out.println("The file was loaded successfully");
//         } catch (InvalidFileExtension e) {
//             System.out.println("Invalid file");
//             System.out.println("The given file Should be an " + e.getWantedExtention() + " file (path should end with '."+e.getWantedExtention()+"' extension).");
//             handleXmlOption(readingType);
//         } catch (FileNotFoundException e) {
//             System.out.println("The given xml file was not found.");
//             handleXmlOption(readingType);;
//         } catch (ItemNotFoundInStoresException e) {
//             System.out.println("Invalid xml file");
//             System.out.println("Item with id " + e.getItemNotFoundId() + " is not sold by any store in the system.");
//             System.out.println("Each item should be sold in at least one store in the system.");
//             handleXmlOption(readingType);
//         } catch (ValueOutOfRangeException e) {
//             System.out.println("Invalid xml file");
//             System.out.println(e.getVariableName() + " of " + e.getObjectName() + "is out of range.");
//             System.out.println(e.getVariableName() + " should be between " + e.getMinValue() + " to " + e.getMaxValue());
//             handleXmlOption(readingType);
//         } catch (StoreItemNotFoundInSystemException e) {
//             System.out.println("Invalid xml file");
//             System.out.println("The store " + e.getStoreName() + " sells an item with id " + e.getItemNotFoundId() + ", this item does not exist in the system.");
//             System.out.println("A store can't sell an item that doesn't exist in the system.");
//             handleXmlOption(readingType);
//         } catch (ItemAlreadyExistInStoreException e) {
//             System.out.println("Invalid xml file");
//             System.out.println("A sell of item with id " + e.getItemId() + " is defined in the store "
//                                + e.getStoreName()+ " more than once.");
//             System.out.println("Each item sell should be defined in each store at most once.");
//             handleXmlOption(readingType);
//         } catch (JAXBException e) {
//             System.out.println("Invalid xml file");
//             System.out.println("An unexpected error occurred while loading the xml file.");
//             handleXmlOption(readingType);
//         } catch (DoubleObjectIdInSystemException e) {
//             System.out.println("Invalid xml file");
//             System.out.println("There are 2 " + e.getPluralObjectName() + " in the xml file with the same id.");
//             System.out.println("Id should be unique value.");
//             handleXmlOption(readingType);
//         }
//     }
//
//     public static void handleXmlOption(XmlRead readingType)
//     {
//         if(readingType==XmlRead.FIRST_FILE)
//         {
//             handleXmlMainMenu();
//         }
//         else
//         {
//             System.out.println("You are currently working on the old xml file");
//             if(readingType==XmlRead.NEW_FILE_BEFORE_IMPORT)
//             {
//                 handleImportOrderHistoryMenu();
//             }
//             else
//             {
//                 handleMenu();
//             }
//         }
//     }
//
//     public static void readXml(XmlRead readingType) {
//         myScanner.nextLine();      // Clear buffer
//         System.out.println("Please enter path to xml file to import data from.");
//         getXmlFilePathAndLoadData(readingType);
//         handleImportOrderHistoryMenu();
//     }
//
//     public static int selectStoreId()
//     {
//        int chosenId;
//
//        System.out.println("Please select a store id from the following options in order to update its items data");
//        printStoresInSystem();
//         chosenId = getIntInput();
//        while (!sdm.isValidStoreId(chosenId)) {
//            System.out.println("The given store id does not exist,\nPlease choose a valid id from the following options");
//            printStoresInSystem();
//            chosenId = getIntInput();
//        }
//        return  chosenId;
//     }
//
//     public static int selectStoreIdToOrderFrom()
//     {
//         int choiceId;
//
//         System.out.println("Please select a store id from the following options");
//         printStoresInSystemWithPpk();
//         choiceId = getIntInput();
//         while (!sdm.isValidStoreId(choiceId)) {
//             System.out.println("Invalid input");
//             System.out.println("The given store id does not exist in the system\nPlease enter again from the following options");
//             printStoresInSystemWithPpk();
//             choiceId = getIntInput();
//         }
//
//         return  choiceId;
//     }
//
//     public static void showUpdateItemsDataSubMenu(String storeName) {
//         System.out.println("Please select an option (an integer number between 1 to 5) \n" +
//                 "1.Delete item from the store "+storeName+"\n" +
//                 "2.Add item to the store "+storeName+"\n" +
//                 "3.Update the price of an item in the store "+storeName+"\n" +
//                 "4.Back to main menu\n"+
//                 "5.Exit");
//     }
//
//     public static void printStoresInSystem() {
//         Set<StoreDto> storeInSystemDetails= sdm.getStoresDetails();
//         for (StoreDto currStore:storeInSystemDetails) {
//             System.out.println("Store Id:" + currStore.getId() + ", Name: " + currStore.getName());
//         }
//     }
//
//     public static void printStoresInSystemWithPpk()
//     {
//         Set<StoreDto> storeInSystemDetails= sdm.getStoresDetails();
//         for (StoreDto currStore:storeInSystemDetails) {
//             System.out.println("Store Id: "+currStore.getId()+", Name: "+ currStore.getName()+", Delivery price per one Km: "+ currStore.getDeliveryPpk() +" Shekels");
//         }
//     }
//
//     public static void PrintStoresDetails()
//     {
//         Set<StoreDto> storesInSystemDetails=sdm.getStoresDetails();
//         for (StoreDto currStoreDetails :storesInSystemDetails)
//         {
//             System.out.println("Store ID: " +currStoreDetails.getId()+"\n"+
//                                 "Name: "+ currStoreDetails.getName()+"\n");
//             printItemesInStoreDetails(currStoreDetails.getItemsInStore());
//             printStoreOrdersDetails(sdm.getStoreOrderDetails(currStoreDetails.getId()));
//
//             System.out.println("Delivery price per one Km : " + currStoreDetails.getDeliveryPpk() +" Shekels\n"
//                                +"Total Profit from deliveries: "+ String.format("%.2f",currStoreDetails.getPaymentForDeliveries())+" Shekels");
//             System.out.print(Constants.LINE_OUTER_SEPARATOR);
//         }
//     }
//
//     private static void printItemesInStoreDetails(Set<ItemInStoreDto> itemsInStoreDetails)
//     {
//         String str="";
//         if(itemsInStoreDetails.isEmpty())
//             str+="No items /n";
//         else
//         {
//             str+= "Items in store details: \n";
//             for (ItemInStoreDto currItem : itemsInStoreDetails)
//             {
//                 str+="Item ID: " + currItem.getId() +"\n"
//                            +"Name: " + currItem.getName() +"\n"
//                            +"Purchase by: " + currItem.getPurchaseCategory()+"\n";
//                 str+="Price per ";
//                 if(currItem.getPurchaseCategory() == PurchaseCategory.Weight)
//                 {
//                     str+="KG: ";
//                 }
//                 else
//                 {
//                     str+="unit: ";
//                 }
//                 str+= currItem.getPrice()+" Shekels\n"
//                    +"Store sold so far: ";
//                 if(currItem.getPurchaseCategory() == PurchaseCategory.Weight)
//                 {
//                     str+=String.format("%.2f",currItem.getAmountOfpurchasesInStore())+" KG";
//                 }
//                 else
//                 {
//                     str+=(int)currItem.getAmountOfpurchasesInStore()+" units";
//                 }
//                 str+=" of this item \n";
//                 str+=Constants.LINE_INNER_SEPARATOR;
//
//             }
//         }
//         System.out.print(str + "\n");
//     }
//
//     public static void printStoreOrdersDetails(Set<StoreOrderDto> storeOrdersDetails)
//     {
//         String str="";
//         if(storeOrdersDetails.isEmpty())
//             str+="No Orders from this store \n";
//         else
//         {
//             SimpleDateFormat df = new SimpleDateFormat("dd/MM-HH:mm");
//             str+= "Orders in store details: \n";
//             for (StoreOrderDto currStoreOrderDetails: storeOrdersDetails)
//             {
//                 str+="Date: "+df.format(currStoreOrderDetails.getDate())+"\n"
//                         +"Amount of items in order: "+(int)currStoreOrderDetails.getItemsTotalAmount()+" units\n"
//                         +"Total price for items: "+String.format("%.2f",currStoreOrderDetails.getItemsTotalPrice())+" Shekels\n"
//                         +"Delivery price: "+String.format("%.2f",currStoreOrderDetails.getDeliveryTotalPrice())+"\n"
//                         +"Total price for order: "+String.format("%.2f",currStoreOrderDetails.getOrderTotalPrice())+" Shekels\n";
//                 str+=Constants.LINE_INNER_SEPARATOR;
//             }
//         }
//         System.out.print(str + "\n");
//     }
//
//     public static void PrintItemsDetails()
//     {
//         String str="";
//
//         Set<ItemInSystemDto> itemsInSystemDetails=sdm.getItemsDetails();
//         if(itemsInSystemDetails.isEmpty())
//             str+="No items /n";
//         else
//         {
//             for (ItemInSystemDto currItem : itemsInSystemDetails)
//             {
//                 str+="Item ID: " + currItem.getId() +"\n"
//                         +"Name: " + currItem.getName() +"\n"
//                         +"Purchase by: " + currItem.getPurchaseCategory();
//                 if( currItem.getPurchaseCategory() == PurchaseCategory.Weight)
//                 {
//                     str+="(KG)";
//                 }
//                 str+="\nNumber of stores selling this item: " + currItem.getNumOfSellers()+"\n"
//                         +"Average price: " +currItem.getAvgPrice() + " Shekels\n"
//                        +"Sold so far in system: ";
//                 if(currItem.getPurchaseCategory()== PurchaseCategory.Weight)
//                 {
//                     str+=currItem.getNumOfPurchases()+" KG \n";
//                 }
//                 else
//                 {
//                     str+=(int)currItem.getNumOfPurchases()+" units \n";
//                 }
//                 str+=Constants.LINE_OUTER_SEPARATOR;
//             }
//         }
//
//         System.out.println(str);
//     }
//
//     public static int getIntInput()
//     {
//         while (!myScanner.hasNextInt())
//         {
//             System.out.println("Wrong input\nPlease enter an integer number");
//             myScanner.next();
//         }
//         return myScanner.nextInt();
//     }
//
//     public static double getDoubleInput()
//     {
//         while (!myScanner.hasNextDouble())
//         {
//             System.out.println("Wrong input\nplease enter a real number");
//             myScanner.next();
//         }
//         return myScanner.nextDouble();
//     }
//
//     public static int getMenuChoice(int maxChoiceVal)
//     {
//         int choice= getIntInput();
//            while (!isValidMenuChoice(choice , maxChoiceVal))
//            {
//                System.out.println("Wrong input\nChoice should be an integer number between 1 to " + maxChoiceVal);
//                choice= getIntInput();
//            }
//            return  choice;
//     }
//
//     public static boolean isValidMenuChoice(int choice , int maxChoiceVal)
//     {
//         return (choice>=1 && choice<=maxChoiceVal);
//     }
//
//     public static void exit()
//     {
//         System.out.println("Goodbye!");
//         System.exit(0);
//     }
//
//     public static void deleteItemFromStore(int storeId)
//     {
//         System.out.println("Please choose id of item to delete from the following options");
//         int itemId= getStoreItemToUpdateIdInput(storeId);
//         if (sdm.hasOneSeller(itemId))
//         {
//             System.out.println("The chosen item being sold only in one store , therefor it can not be deleted");
//             handleUpdateItemsDataSubMenu();
//         }
//         else if(sdm.isStoreHasOneItem(storeId))
//         {
//             System.out.println("The chosen item is the only item in the store , therefor it can not be deleted");
//             handleUpdateItemsDataSubMenu();
//         }
//         else
//         {
//            sdm.deleteItem(itemId,storeId);
//             System.out.println("Item deleted successfully");
//         }
//     }
//
//
//     public static void addItemToStore(int storeId)
//     {
//         System.out.println("Please choose id of item to add from the following options");
//         int itemId= getItemToAddIdInput(storeId);
//         double itemPrice=getItemPriceInput();
//         sdm.addItemToStore(storeId,itemId,itemPrice);
//         System.out.println("Item added successfully");
//     }
//
//     public static void updateStoreItemPrice(int storeId)
//     {
//         System.out.println("Please choose id of item to update its price");
//
//         int itemId= getStoreItemToUpdateIdInput(storeId);
//         double itemPrice=getItemPriceInput();
//         sdm.updateStoreItemPrice(storeId,itemId,itemPrice);
//         System.out.println("Item updated successfully");
//     }
//
//     public static void ShowStoreItems(int storeId)
//     {
//         String str="";
//         Set<ItemInStoreDto> storeItemsDetails = sdm.getStoreItemsDetails(storeId);
//         for (ItemInStoreDto currItem :storeItemsDetails) {
//             str+="Id: "+ currItem.getId()+"\n"
//                                +"Name: "+currItem.getName()+"\n"
//                                +"Purchased by:"+currItem.getPurchaseCategory().toString();
//             if( currItem.getPurchaseCategory() == PurchaseCategory.Weight)
//             {
//                 str+="(KG)";
//             }
//              str+="\nPrice for one";
//             if(currItem.getPurchaseCategory()==PurchaseCategory.Quantity)
//             {
//                 str+=" unit:";
//             }
//             else
//             {
//                 str+=" KG:";
//             }
//             str+=currItem.getPrice()+" Shekels\n";
//             str+=Constants.LINE_INNER_SEPARATOR;
//
//         }
//         System.out.println(str);
//     }
//
//     public static void showItemsInSystem()
//     {
//         Set<ItemInSystemDto> ItemsDetails = sdm.getItemsDetails();
//         for (ItemInSystemDto currItem :ItemsDetails) {
//             System.out.println("Id: "+ currItem.getId() +", Name: "+currItem.getName());
//         }
//     }
//
//     public static int getStoreItemToUpdateIdInput(int storeId)
//     {
//         ShowStoreItems(storeId);
//         int itemId=getIntInput();
//         while (!sdm.isItemExistInStore(itemId,storeId))
//         {
//             System.out.println("Wrong input\nThe chosen item id doesn't exist in store \n"
//                                + "Please choose again from the following options");
//             ShowStoreItems(storeId);
//             itemId=getIntInput();
//         }
//         return  itemId;
//     }
//
//     public static int getItemToAddIdInput(int storeId)
//     {
//         showItemsInSystem();
//         int itemId=getIntInput();
//         boolean isExistInStore = false, isNotExistInSystem = false;
//         while ( (isExistInStore=sdm.isItemExistInStore(itemId,storeId)) || (isNotExistInSystem = (!sdm.isItemExistInSystem(itemId))))
//         {
//             System.out.println("Wrong input");
//             if(isExistInStore)
//             {
//                 System.out.println("The chosen item id is already exist in store");
//             }
//             else
//             {
//                 System.out.println("The chosen item id doesn't exist in the system");
//             }
//
//             System.out.println("Please choose again from the following options");
//             showItemsInSystem();
//             itemId=getIntInput();
//         }
//         return  itemId;
//     }
//
//     public static double getItemPriceInput()
//     {
//         System.out.println("Enter item price");
//         double itemPrice = getDoubleInput();
//         while (itemPrice <=0)
//         {
//             System.out.println("Wrong input\nThe price should be positive number \n"
//                     + "Please try again");
//             itemPrice = getDoubleInput();
//         }
//         return itemPrice;
//     }
//
//     public static void order()
//     {
//         Map< Integer,Map<Integer,Double>> StoreIdToOrderedItems=null ;
//
//         System.out.println("How do you prefer to order ?\nPlease select your choice(an integer number between 1 to 4)\n" +
//                            "1.Order items from specific store\n" +
//                            "2.Let us put together the optimize order of your items from multiple stores\n" +
//                            "3.Back to main menu\n"+
//                            "4.Exit from system");
//         int choice= getMenuChoice(OrderOptionsMenuValues.numOfEnumsVal);
//         OrderOptionsMenuValues chosenOrderOption=OrderOptionsMenuValues.values()[choice-1];
//         int storeId=Constants.NO_VALUE;
//         switch (chosenOrderOption)
//         {
//             case STATIC:
//                 storeId= selectStoreIdToOrderFrom();
//                 break;
//             case BACK:
//                 handleMenu();
//                 break;
//             case EXIT:
//                 exit();
//                 break;
//         }
//
//         Date orderDate=getOrderDate();
//         Coordinate orderLocation=getOrderLocation();
//         Map<Integer,Double> itemIdToItemAmount= getItemsToOrder(storeId,chosenOrderOption);
//
//         if(chosenOrderOption==OrderOptionsMenuValues.DYNAMIC)
//         {
//             StoreIdToOrderedItems=sdm.getMinimalItemsBag(itemIdToItemAmount);
//         }
//
//
//         summarizeOrder(itemIdToItemAmount,storeId,StoreIdToOrderedItems,orderLocation);
//         System.out.println("Press 'Y' to confirm order\n" +
//                 "Press 'N' to cancel order");
//         String confirmChoice=myScanner.next().toUpperCase();
//         while (!confirmChoice.equals("Y") && !confirmChoice.equals("N") )
//         {
//             System.out.println("Wrong input");
//             System.out.println("Press 'Y' to confirm order\n" +
//                     "Press 'N' to cancel order");
//             confirmChoice=myScanner.next().toUpperCase();
//         }
//
//         if(confirmChoice.equals("N"))
//         {
//             handleMenu();
//         }
//         else
//         {
//             if(chosenOrderOption==OrderOptionsMenuValues.DYNAMIC)
//             {
//                 sdm.saveDynamicOrder(orderDate,orderLocation, OrderTypes.DYNAMIC,StoreIdToOrderedItems);
//             }
//             else
//             {
//                 sdm.saveStaticOrder(orderDate,orderLocation, OrderTypes.STATIC,itemIdToItemAmount,storeId);
//             }
//             System.out.println("Your order was saved successfully ");
//         }
//     }
//
//     private static void summarizeOrder(Map<Integer, Double> itemIdToItemAmount ,int staticStoreId,Map<Integer,Map<Integer,Double>> dynamicStoreIdToOrderedItem,Coordinate orderLocation) {
//
//
//         summarizeItemsInOrder(itemIdToItemAmount,staticStoreId,dynamicStoreIdToOrderedItem);
//         if(dynamicStoreIdToOrderedItem!=null)
//         {
//
//             SummarizeDynamicOrderDeliveryPrice(dynamicStoreIdToOrderedItem,orderLocation);
//         }
//         else
//         {
//             SummarizeStaticOrderDeliveryPrice(staticStoreId,orderLocation);
//         }
//     }
//
//     private static void SummarizeStaticOrderDeliveryPrice(int staticStoreId,Coordinate orderLocation)
//     {
//         String str="";
//
//         double distanceFromStore1 = sdm.getDistanceFromStore(staticStoreId, orderLocation);
//         str+="Your distance from chosen store: "+String.format("%.2f", distanceFromStore1 )+" KM\n"
//                 +"Delivery price per KM: "+sdm.getStorePPK(staticStoreId)+" Shekels\n"
//                 +"Total delivery price: "+String.format("%.2f",sdm.getDeliveryPrice(staticStoreId,orderLocation)) +" Shekels";
//
//         System.out.println(str);
//     }
//
//     private static void SummarizeDynamicOrderDeliveryPrice(Map<Integer,Map<Integer,Double>> dynamicStoreIdToOrderedItem,Coordinate orderLocation)
//     {
//         String str="";
//         Set<Integer> storesInOrder = dynamicStoreIdToOrderedItem.keySet();
//         int storesInOrderCnt = storesInOrder.size();
//         str+="Total delivery price from "+ storesInOrderCnt +" store";
//               if(storesInOrderCnt >1)
//                   str+="s";
//         str+=": "+String.format("%.2f", sdm.getDeliveryPriceFromMultipleStores(storesInOrder,orderLocation)) +" Shekels\n";
//         System.out.println(str);
//     }
//
//     public static void summarizeItemsInOrder(Map<Integer, Double> itemIdToItemAmount ,int staticStoreId,Map<Integer,Map<Integer,Double>> storeIdToDynamicOrderedItem )
//     {
//         double currItemAmountByUnits,currItemPrice;
//         int currStoreId=staticStoreId;
//         ItemDto currItemDetails;
//         System.out.println("You choose to order the following items:");
//         Set<ItemInSystemDto> itemsInSystemDetails =sdm.getItemsDetails();
//         String str="";
//         if (itemIdToItemAmount.isEmpty())
//         {
//             str+="No items in order\n";
//         }
//         else {
//             for (Integer currItemId : itemIdToItemAmount.keySet()) {
//                 currItemDetails = getItemDtoByItemId(currItemId, itemsInSystemDetails);
//
//                 str += "Item Id: " + currItemId + "\n"
//                         + "Name: " + currItemDetails.getName() + "\n"
//                         + "Purchase by: " + currItemDetails.getPurchaseCategory().toString() + "\n";
//                 if (storeIdToDynamicOrderedItem != null)   // Dynamic order
//                 {
//                     currStoreId = findStoreIdOfDynamicOrderedItem(currItemId, storeIdToDynamicOrderedItem);
//                 }
//                 currItemPrice = sdm.getStoreItemPrice(currStoreId, currItemId);
//
//                 if (currItemDetails.getPurchaseCategory() == PurchaseCategory.Quantity) {
//                     currItemAmountByUnits = itemIdToItemAmount.get(currItemId);
//                 } else {
//                     currItemAmountByUnits = 1;
//                 }
//
//                 str += "Price: " + String.format("%.2f", currItemPrice) + " Shekels \n"
//                         + "Amount: " + (int) currItemAmountByUnits + " unit";
//                 if (currItemAmountByUnits > 1)
//                     str += "s";
//                 str += "\nTotal price for item in this order: " + String.format("%.2f", itemIdToItemAmount.get(currItemId) * currItemPrice) + " Shekels \n"
//                         + Constants.LINE_INNER_SEPARATOR;
//             }
//         }
//
//         System.out.print(str);
//
//     }
//
//     public static int findStoreIdOfDynamicOrderedItem(int itemId, Map<Integer, Map<Integer, Double>> storeIdToDynamicOrderedItem)
//     {
//         Map<Integer,Double> DynamicOrderedItem ;
//        for (Integer currStoreId:  storeIdToDynamicOrderedItem.keySet())
//        {
//            DynamicOrderedItem=storeIdToDynamicOrderedItem.get(currStoreId);
//            if(DynamicOrderedItem.containsKey(itemId))
//            {
//                return  currStoreId;
//            }
//        }
//
//        return -1;      // return -1 if not found
//     }
//
//     public static Date getOrderDate()
//     {
//         String dateString;
//         Date date ;
//         System.out.println("Please enter order date in format dd/mm-hh:mm");
//
//         dateString = myScanner.next();
//         while ((date=getDateInput(dateString)) == null)
//         {
//             dateString = myScanner.next();
//         }
//         return date;
//     }
//
//     public static Date getDateInput(String text) {
//
//         Date date=null;
//         if (text == null || !text.matches("\\d{2}/\\d{2}-\\d{2}:\\d{2}")) {
//             System.out.println("Wrong input\n" +
//                     "Order date should be in format dd/mm-hh:mm\n" +
//                     "Please try again");
//         }
//         else
//         {
//             SimpleDateFormat df = new SimpleDateFormat("dd/MM-HH:mm");
//             df.setLenient(false);
//             try {
//                 date=df.parse(text);
//             } catch (ParseException ex) {
//                 System.out.println("Wrong input\n" +
//                         "The date is invalid\n" +
//                         "Please try again");
//             }
//         }
//
//         return date;
//     }
//
//     public static int getCoordinate(String coordinateName)
//     {
//        int coordinateVal=getIntInput();
//        while (!sdm.isValidCoordinate(coordinateVal))
//        {
//            System.out.println("Wrong input\n"
//                                +coordinateName+" coorninate is invalid\n" +
//                                "coordinate should be an integer number between "
//                                + Coordinate.coordinateMinValue
//                                +" to "+Coordinate.coordinateMaxValue+"\n"
//                                +"Please try again");
//
//            coordinateVal=getIntInput();
//        }
//        return coordinateVal;
//     }
//
//     public static Coordinate getOrderLocation(){
//
//         Coordinate orderLocation=getOrderLocationInput();
//         while (sdm.isStoreExistHere(orderLocation)) {
//             System.out.println("Wrong input\n" +
//                     "A store is located here\n" +
//                     "You can't order to store location.\n" +
//                     "Please Choose another location");
//             orderLocation=getOrderLocationInput();
//         }
//         return  orderLocation;
//     }
//
//     public static Coordinate getOrderLocationInput()
//     {
//         System.out.println("Please enter your location as 2 coordinates: X and Y");
//         System.out.println("please enter X coordinate " +
//                 "(an integer number between "+ Coordinate.coordinateMinValue+ " to " +Coordinate.coordinateMaxValue+")");
//         int xVal= getCoordinate("X");
//         System.out.println("please enter Y coordinate " +
//                 "(an integer number between "+ Coordinate.coordinateMinValue+ " to " +Coordinate.coordinateMaxValue+")");
//         int yVal= getCoordinate("Y");
//         return new Coordinate(xVal,yVal);
//     }
//
//
//     public static void showSystemItemsToOrder(int storeId, OrderOptionsMenuValues orderOption)
//     {
//         Set<ItemInSystemDto> systemItemsDetails = sdm.getItemsDetails();
//         String str = "";
//         double itemPrice;
//         for (ItemInSystemDto currItem :systemItemsDetails) {
//             str+= "Item Id: "+ currItem.getId()
//                                +", Name: "+currItem.getName()
//                                +", Sold by "+currItem.getPurchaseCategory();
//             if(currItem.getPurchaseCategory()==PurchaseCategory.Weight)
//             {
//                 str+="(KG)";
//             }
//             if (orderOption==OrderOptionsMenuValues.STATIC)
//             {
//                 itemPrice=sdm.getStoreItemPrice(storeId,currItem.getId());
//                 if(itemPrice==Constants.NO_VALUE )
//                 {
//                     str+=" **Item not sold in wanted store**";
//                 }
//                 else
//                 {
//                     str+=", Price: " + itemPrice + " Shekels";
//                 }
//             }
//             str+="\n";
//         }
//         System.out.print(str);
//
//     }
//
//     public static double getItemAmount(int itemId)
//     {
//         double amount;
//         System.out.print("Please choose wanted amount");
//         amount=getAmountInput(itemId);
//         while (amount<=0)
//         {
//             System.out.print("Wrong input\nAmount must be a positive number\nPlease try again");
//             amount=getAmountInput(itemId);
//         }
//
//         return amount;
//     }
//
//     public static double getAmountInput(int itemId)
//     {
//         double amount;
//         PurchaseCategory purchaseCategory=sdm.getItemPurchaseCategory(itemId);
//         if(purchaseCategory==PurchaseCategory.Weight)
//         {
//             System.out.println(" of KG (insert a positive real number)");
//             amount=getDoubleInput();
//         }
//         else
//         {
//             System.out.println("(insert a positive whole number)");
//             amount=getIntInput();
//         }
//         return amount;
//     }
//
//     public static Map<Integer,Double> getItemsToOrder(int storeId, OrderOptionsMenuValues orderOption)
//     {
//         double amount,oldAmountVal;
//         int itemId;
//         Map<Integer,Double> itemDetailsToItemAmount=new HashMap<>();
//
//         itemId=getItemToOrderId(storeId,orderOption);
//         while(itemId!=Constants.NO_VALUE)
//         {
//             amount=getItemAmount(itemId);
//
//             if(itemDetailsToItemAmount.containsKey(itemId))
//             {
//                 oldAmountVal=itemDetailsToItemAmount.get(itemId);
//                 amount+=oldAmountVal;
//             }
//             itemDetailsToItemAmount.put(itemId,amount);
//
//             itemId=getItemToOrderId(storeId,orderOption);
//         }
//
//         return  itemDetailsToItemAmount;
//     }
//
//     public static int getItemToOrderId(int storeId, OrderOptionsMenuValues orderOption)
//     {
//         int itemId;
//
//         System.out.println("Please select item to order from the following options (enter the id number of the wanted item) \nTo stop enter 'Q'(or 'q')");
//         if(orderOption==OrderOptionsMenuValues.STATIC)
//         {
//             itemId= chooseItemToOrderIdFromStore(storeId);
//         }
//         else
//         {
//             itemId= chooseItemToOrderIdFromSystem();
//         }
//         return itemId;
//     }
//
//     private static int chooseItemToOrderIdFromSystem() {
//
//         showSystemItemsToOrder(Constants.NO_VALUE,OrderOptionsMenuValues.DYNAMIC);
//         int itemId=getItemToOrderIdInput();
//         while (itemId!=Constants.NO_VALUE &&!sdm.isItemExistInSystem(itemId) )
//         {
//             System.out.println("Wrong input\nThe chosen item id doesn't exist in system \n"
//                     + "Please try again");
//
//             showSystemItemsToOrder(Constants.NO_VALUE,OrderOptionsMenuValues.DYNAMIC);
//             itemId=getItemToOrderIdInput();
//         }
//
//         return itemId;
//     }
//
//     public static int chooseItemToOrderIdFromStore(int storeId)
//     {
//         showSystemItemsToOrder(storeId,OrderOptionsMenuValues.STATIC);
//         int itemId=getItemToOrderIdInput();
//         while (itemId!=Constants.NO_VALUE && !sdm.isItemExistInStore(itemId,storeId) )
//         {
//             System.out.println("Wrong input\nThe chosen item doesn't exist in store \n"
//                     + "Please try again");
//             showSystemItemsToOrder(storeId,OrderOptionsMenuValues.STATIC);
//             itemId=getItemToOrderIdInput();
//         }
//
//         return itemId;
//     }
//
//     public static ItemDto getItemDtoByItemId(int itemId,Set<ItemInSystemDto> itemsInSystemDetails)
//     {
//         for (ItemDto currItem : itemsInSystemDetails)
//         {
//             if(currItem.getId()==itemId)
//             {
//                 return currItem;
//             }
//         }
//         return  null;
//     }
//
//     public static int getItemToOrderIdInput()
//     {
//         String nextString;
//         while (!myScanner.hasNextInt())
//         {
//             nextString=myScanner.next().toLowerCase();
//             if(nextString.equals("q"))
//             {
//                 return Constants.NO_VALUE;
//             }
//             else {
//                 System.out.println("Wrong input\nplease enter an integer number or q to stop");
//             }
//         }
//         return myScanner.nextInt();
//     }
//
//     private static void showHistory()
//     {
//         SimpleDateFormat df = new SimpleDateFormat("dd/MM-HH:mm");
//         String str="";
//         DynamicOrderDto dynamicOrderDetails;
//         StaticOrderDto staticOrderDetails;
//        Set<OrderDto> ordersDetails=sdm.getOrdersDetails();
//        if(ordersDetails.isEmpty())
//        {
//            str+="No orders found in system!\n";
//        }
//        else
//        {
//            for (OrderDto currOrderDetails : ordersDetails)
//            {
//                str+="Order ID: "+currOrderDetails.getOrderId() +"\n"
//                        +"Order date: "+df.format(currOrderDetails.getDate())+"\n";
//
//                if(currOrderDetails instanceof DynamicOrderDto)
//                {
//                    dynamicOrderDetails=(DynamicOrderDto)currOrderDetails;
//                    str+="From: "+dynamicOrderDetails.getStoresInOrderAmount()+" different stores"+"\n";
//                }
//                else
//                {
//                    staticOrderDetails=(StaticOrderDto)currOrderDetails;
//                    str+="From store: "+staticOrderDetails.getStoreName()
//                                            +" (store's Id="+staticOrderDetails.getStoreId()+")\n";
//                }
//
//                int itemsTotalAmount = (int) currOrderDetails.getItemsTotalAmount();
//                str+="Number of items in order: "+ itemsTotalAmount +" unit";
//                if(itemsTotalAmount>1)
//                    str+="s";
//                str+="\nNumber of different items types in order: "+currOrderDetails.getAmountOfItemsTypes() +"\n";
//                str+="Total price for items: "+String.format("%.2f", currOrderDetails.getItemsTotalPrice() ) +" Shekels \n"
//                    +"Delivery price: "+String.format("%.2f",currOrderDetails.getDeliveryTotalPrice()) +" Shekels \n"
//                    +"Total price (include items price and delivery price): "
//                    +String.format("%.2f",currOrderDetails.getOrderTotalPrice())+" Shekels \n"
//                    +Constants.LINE_INNER_SEPARATOR;
//
//            }
//        }
//         System.out.print(str);
//     }
//
//     public static void importOrdersHistoryData()
//     {
//         String filePath="";
//         myScanner.nextLine();//clear buffer
//         System.out.println("Please enter a file path to import data from (the file should contain binary data)");
//
//         filePath=myScanner.nextLine();
//         try {
//             sdm.readOrdersHistoryFromFile(filePath);
//         } catch (FileNotFoundException e) {
//             System.out.println("The given file is invalid");
//             System.out.println("File does not exist ,or is a directory rather than a regular file, or for some other reason cannot be opened for reading");
//             handleImportOrderHistoryMenu();
//         } catch (SecurityException e) {
//             System.out.println("The given file is invalid");
//             System.out.println("Access to read from file denied");
//             handleImportOrderHistoryMenu();
//         }
//         catch (IOException e)
//         {
//             System.out.println("An error occurs while reading from given file");
//             handleImportOrderHistoryMenu();
//         }
//
//         System.out.println("Data imported successfully");
//         handleMenu();
//     }
//
//     public static void ExportOrdersHistoryData()
//     {
//         String filePath="";
//         myScanner.nextLine();//clear buffer
//         System.out.println("Please enter a file path to save orders history data to (the file will contain binary data)");
//         filePath=myScanner.nextLine();
//         try {
//             sdm.writeOrderHistoryToFile(filePath);
//         } catch (FileNotFoundException e) {
//             System.out.println("The given file is invalid");
//             System.out.println("The given file exists but is a directory rather than a regular file,or does not exist but cannot be created, or cannot be opened for any other reason");
//             ExportOrdersHistoryData();
//         } catch (SecurityException e) {
//             System.out.println("The given file is invalid");
//             System.out.println("Access to write to file denied");
//             ExportOrdersHistoryData();
//         }catch (IOException e) {
//             System.out.println("An error occurs while writing to given file");
//             e.printStackTrace();
//         }
//
//         System.out.println("Orders history exported to file successfully");
//
//
//     }
//
// }