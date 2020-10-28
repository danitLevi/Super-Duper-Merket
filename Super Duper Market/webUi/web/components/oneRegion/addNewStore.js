var ADD_STORE_DATA_URL = buildUrlWithContextPath("addStore");
var ITEMS_IN_REGION_URL = buildUrlWithContextPath("itemByRegion");
var REGIONS_IN_SYSTEM_URL = buildUrlWithContextPath("getAllRegionsNames");

// onload...do
// Show items data according to selected region
function initializeItemsPerRegion(){
    handleRegionChange();
    handleAddStoreSubmitting();
}

function addNewStore() {
    //Initialize Region & items to sell table
    ajaxRegionsOptionsData();
    //initItemRegionstable();
}

function ajaxRegionsOptionsData() {
    $.ajax({
        url: REGIONS_IN_SYSTEM_URL,
        success: function(regionsJson) {
            //Initialize region options
            initializeRegionsOptions(regionsJson);
            //Initialize table data according to current option
            var region = $("#storeRegion").val(); //Get selected region
            ajaxItemsPerRegionTableData(region); //Show data accordingly
        }
    });
}

function initializeRegionsOptions(regionsJson) {
    $("#storeRegion").empty();

    $.each(regionsJson || [], function(index, region) {
        $("<option>"+region+"</option>").appendTo($("#storeRegion"));
    });
}

function handleRegionChange(){
    $("#storeRegion").on('change',function () {
        var region = $("#storeRegion").val(); //Get selected region
        ajaxItemsPerRegionTableData(region); //Show data accordingly
    })
}

// function initItemRegionstable() {
//     var region = $("#storeRegion").val(); //Get selected region
//     ajaxItemsPerRegionTableData(region);
// }

function ajaxItemsPerRegionTableData(region) {
    $.ajax({
        url: ITEMS_IN_REGION_URL,
        data: {region: region},
        success: function(itemsJson) {
            //Initialize items table
            initializeItemsTable(itemsJson);
        }
    });
}

function initializeItemsTable(itemsJson) {
    //clear all current items
    $("#itemsToSellTableData").empty();

    // Build the list of items in system: scan all items and add them to the list
    $.each(itemsJson || [], function(index, itemData) {
        $('<tr><td>' +
            itemData.id  +'</td>' +
            '<td>'+itemData.name+'</td>'+
            '<td>'+itemData.purchaseCategory+'</td>'+
            '<td> <input type="number" id="'+itemData.id+' " min="0" step="0.01" placeholder="Set Price" class="form-control itemToAddPrice col-4"> </td>'+
        '</tr>').appendTo($("#itemsToSellTableData"));
    });
}

function handleAddStoreSubmitting() {
    $("#newStoreForm").submit(function () {

        var regionName = $("#storeRegion").val();
        var storeName = $("#storeName").val();
        var ppk = $("#storePPK").val();
        var xCoordinate = $("#x").val();
        var yCoordinate = $("#y").val();
        var itemsJson =getItemsToAddToStoreJson();

        $.ajax({
            url:ADD_STORE_DATA_URL,
            method:'POST',
            data:{itemsToAdd:JSON.stringify(itemsJson.items), region:regionName, storeName:storeName, ppk:ppk, x: xCoordinate, y:yCoordinate},
            success:function (response) {
                $("#newStoreMsg").text(response);
                triggerAddStoreAlertMsgToShow(storeName,xCoordinate,yCoordinate,itemsJson.size);
            }
        });
        return false;
    })
}

function triggerAddStoreAlertMsgToShow(storeName,xCoordinate,yCoordinate,numOfItemsToSell) {

    $.ajax({
        url:SAVE_ALERT_TO_SHOW_URL,
        method:'POST',
        data:{alertType:"newStore",storeName:storeName,xCoordinate:xCoordinate,yCoordinate:yCoordinate,numOfItemsToSell:numOfItemsToSell}
    });
}

function getItemsToAddToStoreJson()
{
    var newItemPriceInputs=$(".itemToAddPrice");
    var itemsToAddToStoreJson={items:[]};
    var counter=0;
    $.each(newItemPriceInputs || [], function (index, currInput)
    {
        var itemId=$(currInput).attr('id');
        var price=$(currInput).val();
        var currentRow=$(this).closest("tr");
        var purchaseCategory=currentRow.find("td:eq(2)").text(); // get current row 2nd TD
        if(price!="0" && price!=undefined && price!="")
        {
            itemsToAddToStoreJson.items[counter]={"itemId":itemId ,"price":price, "purchaseCategory":purchaseCategory};

            counter++;
            // itemsToOrderJson.items.push({itemId:quantity});
            // $.extend(itemsToOrderJson.items,{"itemId":itemId ,"quantity":quantity});
        }

    });

    return itemsToAddToStoreJson;
}