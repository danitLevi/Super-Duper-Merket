var STORES_DATA_URL = buildUrlWithContextPath("storesInRegion");

function triggerStoresAjaxTimeInterval() {
    setInterval(ajaxStoresData, 500);
}

function ajaxStoresData() {
    $.ajax({
        url: STORES_DATA_URL,
        success: function(storesJson) {

            //Initialize stores data
            setStoresInRegionCards(storesJson)
            //Insert items data to all tables
            setStoreItemsTable(storesJson);
        }
    });
}


function setStoresInRegionCards(storesJson)
{
    $("#storesData").empty();

    $.each(storesJson || [], function (index, storeData) {
        // $("#storesData").add("StoreCardTemplate.html");

        $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneStoreCard">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+
            '<h3 class="card-title " id="storeName">'+storeData.name+'</h3>'+

            <!--store data-->
            '<div class="row">'+
            '<b class="font-weight-bold">Id: </b>'+
            '<span  class="col-1" id="id">'+storeData.id+'</span>'+

            '<b class="font-weight-bold">Owner: </b>'+
            '<span  class="col-1" id="Owner">'+storeData.owner+'</span>'+

            '<b class="font-weight-bold">Coordinate: </b>'+
            '<span  class="col-1" id="coordinate">('+ storeData.xCoordinate + ',' + storeData.yCoordinate+')</span>'+

            '</div>'+
            '<br>'+
            <!--items in store data-->
            '<table class="storeItemsTable table table-striped table-sm table-hover col-sm-auto" >'+
            '<thead>'+
            '<tr>'+
            '<th >Id</th>'+
            '<th>Name</th>'+
            '<th>Purchase category</th>'+
            '<th>Item price</th>'+
            '<th>Purchases amount</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody id="itemInStoreTableData">'+
            '</tbody>'+
            '</table>'+
            '<br>'+

            '<div class="row">'+
            '<b class="font-weight-bold">Order amount: </b>'+
            '<span  class="col-1" id="orderAmount">'+storeData.ordersAmount+'</span>'+
            '<b class="font-weight-bold">Purchased items cost: </b>'+
            '<span  class="col-1" id="itemsProfit">'+storeData.paymentForItems+'</span>'+

            '<b class="font-weight-bold">Price per km: </b>'+
            '<span  class="col-1" id="ppk">'+storeData.deliveryPpk+'</span>'+

            '<b class=" font-weight-bold">Profit from deliveries: </b>'+
            '<span  class="col-1" id="deliveriesProfit">'+storeData.paymentForDeliveries+'</span>'+

            '</div>'+
            '</div>'+
            '</div>').appendTo($("#storesData"));

    });
}

function setStoreItemsTable(storesJson) {
    // Find and create array of all Item Tables
    var tablesArray=$(".storeItemsTable");

    // Insert content to each table to BODY
    $.each(tablesArray,function (index,curritemsTable) {
        var contentElement=$(curritemsTable).find("tbody");
        var currStore = $(storesJson).get(index);
        var curritemsInStore = currStore.itemsInStore;

        $.each(curritemsInStore,function (index,item) {
            $("<tr><td>" + item.id  +"</td>" +
                "<td>"+ item.name+"</td>"+
                "<td>"+item.purchaseCategory+"</td>"+
                "<td>"+item.price+"</td>"+
                "<td>"+item.amountOfpurchasesInStore+"</td>"+
                "</tr>").appendTo(contentElement);
        });
    })
}