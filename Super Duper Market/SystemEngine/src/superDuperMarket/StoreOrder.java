package superDuperMarket;

import DtoObjects.ItemInStoreOrderDto;
import DtoObjects.OfferDto;
import DtoObjects.PurchaseCategory;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class StoreOrder implements Serializable
{
    private Map<Sell,Double> itemSellToItemAmount;
    private Map<Sell,Double> itemsFromSaleSellToItemAmount;
    private Store store;

    StoreOrder(Store store,Map<Integer,Double> itemIdToItemAmountInput,Map<OfferDto,Integer> ItemsToOrderFromSalesToSalesAmountDetails)
    {
        setStore(store);
        setItemSellToItemAmountFromInput(itemIdToItemAmountInput);
        InitializeItemsToOrderFromSalesToSalesAmount(ItemsToOrderFromSalesToSalesAmountDetails);

    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Map<Sell, Double> getItemSellToItemAmount() {
        return itemSellToItemAmount;
    }

    public void setItemSellToItemAmount(Map<Sell, Double> itemSellToItemAmount) {
        this.itemSellToItemAmount = itemSellToItemAmount;
    }

    public void setItemSellToItemAmountFromInput(Map<Integer,Double> itemIdToItemAmountInput) {

      //size of itemIdToItemAmountInput is bigger than 0
       this.itemSellToItemAmount  = new HashMap<>();

        Sell currSell;
        Sell newSell;
        for (Integer currItemId:itemIdToItemAmountInput.keySet())
        {
            currSell=store.getItemIdToItemSell().get(currItemId);
            newSell=new Sell(currSell.getPrice(),currSell.getItemId());
            itemSellToItemAmount.put(newSell,itemIdToItemAmountInput.get(currItemId));
        }

    }
    public  void InitializeItemsToOrderFromSalesToSalesAmount(Map<OfferDto,Integer> ItemsToOrderFromSalesToSalesAmountDetails)
    {
        this.itemsFromSaleSellToItemAmount=new HashMap<>();
        if(ItemsToOrderFromSalesToSalesAmountDetails!=null)
        {
            for (OfferDto offerDetails:ItemsToOrderFromSalesToSalesAmountDetails.keySet() )
            {
                Sell currSell=new Sell(offerDetails.getForAdditional(),offerDetails.getItemId());
                int amountOfUses=ItemsToOrderFromSalesToSalesAmountDetails.get(offerDetails);
                double itemQuantityInOffer=offerDetails.getQuantity();
                double totalItemAmount=amountOfUses* itemQuantityInOffer;
                this.itemsFromSaleSellToItemAmount.put(currSell,totalItemAmount);

            }

        }
    }


    public double getTotalAmountOfOrderedItems()
    {
        double itemsAmount=0;
        //check items ordered regulary
        for (Sell currSell : itemSellToItemAmount.keySet())
        {
            itemsAmount+= itemSellToItemAmount.get(currSell);
        }

        //check items ordered from sale
        for (Sell currSell : itemsFromSaleSellToItemAmount.keySet())
        {
            itemsAmount+= itemsFromSaleSellToItemAmount.get(currSell);
        }

        return  itemsAmount;
    }

    public int getAmountOfOrderedItemsByUnits(Map<Integer, Item> itemIdToItemsInSystem)
    {
        int itemsAmountByUnits=0;
        for (Sell currSell : itemSellToItemAmount.keySet())
        {
            if(currSell.getPurchaseCategory()== PurchaseCategory.Weight)
            {
                itemsAmountByUnits+=1;
            }
            else
            {
                itemsAmountByUnits+= itemSellToItemAmount.get(currSell);
            }
        }

        //items from sales
        for (Sell currSell : itemsFromSaleSellToItemAmount.keySet())
        {
            Item currItem=itemIdToItemsInSystem.get(currSell.getItemId());
            if(currItem.getPurchaseCategory()== PurchaseCategory.Weight)
            {
                itemsAmountByUnits+=1;
            }
            else
            {
                itemsAmountByUnits+= itemsFromSaleSellToItemAmount.get(currSell);
            }
        }
        return  itemsAmountByUnits;
    }

    public double getItemsTotalPrice()
    {
        double totalPrice=0;
        double itemPrice;
        //check items ordered regularly
        for (Sell currSell : itemSellToItemAmount.keySet())
        {
            itemPrice=currSell.getPrice();
            totalPrice+=itemPrice * itemSellToItemAmount.get(currSell);
        }
        //check items ordered from sale
        for (Sell currSell : itemsFromSaleSellToItemAmount.keySet())
        {
            itemPrice=currSell.getPrice();
            totalPrice+=itemPrice * itemsFromSaleSellToItemAmount.get(currSell);
        }

        return totalPrice;
    }

    public double getDeliveryPrice(Coordinate customerLocation)
    {
       /* double distance= getDistanceFromStore(customerLocation);
        return (distance * store.getDeliveryPpk());*/
        return store.getDeliveryPrice(customerLocation);
    }

    public double getDistanceFromCustomer(Coordinate customerLocation)
    {
        return store.getDistanceFromGivenLocation(customerLocation);
    }


    public double getTotalPrice(Coordinate customerLocation)
    {
        double deliveryPrice= getDeliveryPrice(customerLocation);
        double itemsPrice= getItemsTotalPrice();

        return  (deliveryPrice+itemsPrice);
    }

    public int getItemsTypesNum()
    {
        return itemSellToItemAmount.size() + itemsFromSaleSellToItemAmount.size();
    }

    // if not found return 0
    public double getOrderedItemPurchases(int itemId)
    {
        double itemPurhcasesAmount=0;
        Set<Sell> sells=itemSellToItemAmount.keySet();

        //check items ordered regularly
        for (Sell currSell: sells)
        {
            if(currSell.getItemId()==itemId)
            {
                itemPurhcasesAmount+= itemSellToItemAmount.get(currSell);
            }
        }

        //check items ordered from sale
        for (Sell currSell:itemsFromSaleSellToItemAmount.keySet())
        {
            if(currSell.getItemId()==itemId)
            {
                itemPurhcasesAmount+= itemsFromSaleSellToItemAmount.get(currSell);
            }
        }

        return itemPurhcasesAmount;
    }

    public Set<ItemInStoreOrderDto> getItemsInStoreOrderDetails(final Map<Integer,Item> itemIDToItemInSystem)
    {
        Set<ItemInStoreOrderDto> itemsInStoreOrderDetails=new HashSet<>();
        ItemInStoreOrderDto currItemDetails;
        int currItemId;
        String currItemName;

        //check items ordered regularly
        for (Sell currSell:itemSellToItemAmount.keySet())
        {
            currItemId=currSell.getItemId();
            Item currItem=itemIDToItemInSystem.get(currItemId);
            currItemName=currItem.getName();
            currItemDetails=new ItemInStoreOrderDto(currItemId,
                    currItemName,
                    currItem.getPurchaseCategory(),
                    itemSellToItemAmount.get(currSell),
                    currSell.getPrice(),
                    itemSellToItemAmount.get(currSell) * currSell.getPrice(),
                    false);

            itemsInStoreOrderDetails.add(currItemDetails);
        }

        // items ordered from sale

        for (Sell currSell:itemsFromSaleSellToItemAmount.keySet())
        {
            currItemId=currSell.getItemId();
            Item currItem=itemIDToItemInSystem.get(currItemId);
            currItemName=currItem.getName();
            currItemDetails=new ItemInStoreOrderDto(currItemId,
                    currItemName,
                    currItem.getPurchaseCategory(),
                    itemsFromSaleSellToItemAmount.get(currSell),
                    currSell.getPrice(),
                    itemsFromSaleSellToItemAmount.get(currSell) * currSell.getPrice(),
                    true);

            itemsInStoreOrderDetails.add(currItemDetails);
        }

        return itemsInStoreOrderDetails;
    }

    public int getAmountOfOrderedItemsTypes()
    {
        return itemSellToItemAmount.size();
    }

    public Map<Sell, Double> getItemsFromSaleSellToItemAmount() {
        return itemsFromSaleSellToItemAmount;
    }

    public void setItemsFromSaleSellToItemAmount(Map<Sell, Double> itemsFromSaleSellToItemAmount) {
        this.itemsFromSaleSellToItemAmount = itemsFromSaleSellToItemAmount;
    }
}
