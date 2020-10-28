var ADD_STORE_DATA_URL = buildUrlWithContextPath("addStore");
var ITEMS_IN_REGION_URL = buildUrlWithContextPath("itemByRegion");
var VALIDATE_ORDER_LOCATION_URL = buildUrlWithContextPath("validateOrderLocation");

// onload...do
function initializeItemsPerRegion(){
    handleAddStoreSubmitting();
    handleNewStoreLocationValidation();
}

function addNewStore() {
    //Initialize items to sell table
    ajaxItemsPerRegionTableData();
}

function ajaxItemsPerRegionTableData() {
    $.ajax({
        url: ITEMS_IN_REGION_URL,
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

    notifyIfNewStoreItemsToOrderEmpty();
}

function handleAddStoreSubmitting() {
    $("#newStoreForm").submit(function () {

        var store = $("#storeName").val();
        var ppk = $("#storePPK").val();
        var xCoordinate = $("#x").val();
        var yCoordinate = $("#y").val();
        var itemsJson =getItemsToAddToStoreJson();

        $.ajax({
            url:ADD_STORE_DATA_URL,
            method:'POST',
            data:{itemsToAdd:JSON.stringify(itemsJson.items), storeName:store, ppk:ppk, x: xCoordinate, y:yCoordinate},
            success:function (response) {
                $("#newStoreMsg").text(response);
            },
            error: function(response) {
                $("#newStoreMsg").text(response);
            }
        });

        return false;
    })
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

function handleNewStoreLocationValidation() {
    $("#x").change(notifyIfNewStoreLocationInvalid);
    $("#y").change(notifyIfNewStoreLocationInvalid);
}

function notifyIfNewStoreLocationInvalid() {

    let xVal=$("#x").val();
    let yVal=$("#y").val();

    if(xVal!="" && yVal!="" )
    {
        $.ajax({
            url:VALIDATE_ORDER_LOCATION_URL,
            data:{xCoordinate:xVal, yCoordinate:yVal},
            success:function (response) {
                if(response=="false")
                {
                    $("#x")[0].setCustomValidity("A store located in this location. " +
                        "Please select other location");
                }
                else
                {
                    $("#x")[0].setCustomValidity("");
                }
            }
        });
    }
}

function notifyIfNewStoreItemsToOrderEmptyAfterChange() {
    $("#itemsToSellTableData input").change(notifyIfNewStoreItemsToOrderEmpty);
}

function notifyIfNewStoreItemsToOrderEmpty() {
    let itemsJson =getItemsToAddToStoreJson();
    if(itemsJson.items.length==0)
    {
        $("#itemsToSellTableData input")[0].setCustomValidity("Select at least one item to sell");
    }
    else
    {
        $("#itemsToSellTableData input")[0].setCustomValidity("");
    }

    notifyIfNewStoreItemsToOrderEmptyAfterChange()
}