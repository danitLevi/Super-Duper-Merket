var ITEMS_DATA_URL = buildUrlWithContextPath("items");
var ITEMS_IN_REGION_INTERVAL;

function triggerItemsAjaxTimeInterval() {

    ITEMS_IN_REGION_INTERVAL=setInterval(ajaxItemsData, 500);
}

function ajaxItemsData() {
    $.ajax({
        url: ITEMS_DATA_URL,
        success: function(itemsJson) {

            refreshItemsTable(itemsJson);
        }
    });
}


function refreshItemsTable(itemsJson,isStatic) {
    //clear all current users
    $("#itemsTableData").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(itemsJson || [], function(index, itemData) {
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $("<tr><td>" +
            itemData.id  +"</td>" +
            "<td>"+itemData.name+"</td>"+
            "<td>"+itemData.purchaseCategory+"</td>"+
            "<td>"+itemData.numOfSellers+"</td>"+
            "<td>"+myFormatter.format(itemData.avgPrice)+"₪"+"</td>"+
            "<td>"+itemData.numOfPurchases+"</td>"+
            "</tr>").appendTo($("#itemsTableData"));
    });
}
