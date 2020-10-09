package tasks;

import DtoObjects.ItemDto;
import javafx.concurrent.Task;
import superDuperMarket.LogicInterface;
import utils.UtilsClass;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CalculateMinimalBagTask extends Task<Map<Integer, Map<Integer, Double>>> {

    private  Map<Integer,Double> itemIdToAmount;
    private LogicInterface sdmLogic;
    private Consumer<Map<Integer, Map<Integer, Double>>> setMinimalPriceBag;
    private Consumer<Boolean> isMinimalPriceBagReturned;

    public CalculateMinimalBagTask(Map<Integer, Double> itemIdToAmount, LogicInterface sdmLogic) {
        this.itemIdToAmount = itemIdToAmount;
        this.sdmLogic = sdmLogic;
        this.setMinimalPriceBag=setMinimalPriceBag;
        this.isMinimalPriceBagReturned=isMinimalPriceBagReturned;
    }

    @Override
    protected Map<Integer, Map<Integer, Double>> call() throws Exception {
        Map<Integer, Map<Integer, Double>>  itemsBag=new HashMap<>();

        double minPrice;
        int currMinStoreId =-1;
        Map<Integer, Double> itemIdToAmountInStore;
        int itemsAmount=this.itemIdToAmount.size();
        int totalWork=itemsAmount+1;
        int checkedItemsAmount=0;
        for (Integer currItemId:this.itemIdToAmount.keySet())
        {
            checkedItemsAmount++;
            ItemDto currItemDetails=sdmLogic.getItemDetails(currItemId);
            updateMessage("Search best seller for "+currItemDetails.getName()+" (id="+currItemDetails.getId()+")");
            updateProgress(checkedItemsAmount,totalWork);
            UtilsClass.sleepForAWhile(1000);

            currMinStoreId=sdmLogic.getItemCheapestSellerId(currItemId);
            if (!itemsBag.containsKey(currMinStoreId))
            {
                itemIdToAmountInStore=new HashMap<>();
            }
            else
            {
                itemIdToAmountInStore=itemsBag.get(currMinStoreId);
            }
            itemIdToAmountInStore.put(currItemId,itemIdToAmount.get(currItemId));
            itemsBag.put(currMinStoreId,itemIdToAmountInStore);
        }
        updateMessage("Finishing...");
        updateProgress(1,1);
        UtilsClass.sleepForAWhile(1000);
//        setMinimalPriceBag.accept(itemsBag);
//        isMinimalPriceBagReturned.accept(true);
//          itemsBag;
        return itemsBag;
    }

}
