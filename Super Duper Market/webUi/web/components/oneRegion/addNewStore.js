var ADD_STORE_DATA_URL = buildUrlWithContextPath("addStore");
var ITEMS_IN_SYSTEM_URL = buildUrlWithContextPath("items");
var REGIONS_IN_SYSTEM_URL = buildUrlWithContextPath("getAllRegionsNames");

function addNewStore() {
    //Initialize Region & items to sell table
    ajaxRegionsOptionsData();
    ajaxItemsTableData();
    //Handle "Add Store" submission
    //handleAddStoreSubmitting();
}

function ajaxRegionsOptionsData() {
    $.ajax({
        url: REGIONS_IN_SYSTEM_URL,
        success: function(regionsJson) {
            //Initialize region options
            initializeRegionsOptions(regionsJson);
        }
    });
}
function initializeRegionsOptions(regionsJson) {
    $("#storeRegion").empty();

    $.each(regionsJson || [], function(index, region) {
        $("<option>"+region+"</option>").appendTo($("#storeRegion"));
    });
}

function ajaxItemsTableData() {
    $.ajax({
        url: ITEMS_IN_SYSTEM_URL,
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
        $("<tr><td>" +
            itemData.id  +"</td>" +
            "<td>"+itemData.name+"</td>"+
            "<td>"+itemData.purchaseCategory+"</td>"+
            "<td><input type='number' id='itemPrice' min='0' step='0.01' placeholder='Set Price'></td>" +
            "</tr>").appendTo($("#itemsToSellTableData"));
    });
}

function handleAddStoreSubmitting() {
    $("#newStoreForm").submit(function () {
        $.ajax({
            url:ADD_STORE_DATA_URL,
            method:'POST',
            data:$(this).serialize(),
            success:function () {
                //TODO
            }
        });

        return false;
    })
}