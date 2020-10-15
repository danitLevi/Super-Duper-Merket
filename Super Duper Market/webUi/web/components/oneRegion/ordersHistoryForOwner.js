var OWNER_ORDERS_DATA_URL = buildUrlWithContextPath("ownerOrders");
var OWNER_STORES_URL =  buildUrlWithContextPath("storesInOrder");

function triggerOwnerOrdersAjaxTimeInterval() {
    setInterval(ajaxOwnerOrdersData, 1000);
}

// onload...do
// Show orders data according to selected store
function initializeOrdersHistoryForOwner(){
    $("#ownerStores").on('change',function () {
        var store = $("#ownerStores").val(); //Get selected store
        var storeId= store.substr(0, store.indexOf(' ')); //Get selected store id
        ajaxOwnerOrdersData(storeId); //Show data accordingly
    })

    triggerOwnerOrdersAjaxTimeInterval();
}

function getOwnerOrderHistory() {
    ajaxStoreOptionsData();
}

//Initialize stores options
function ajaxStoreOptionsData() {
    $.ajax({
        url: OWNER_STORES_URL,
        success: function(storesJson) {
            if(storesJson.length === 0) {
                $(".noOrdersInStore").text("No orders found in this region");
            }
            else {
                //Initialize stores that have orders options
                initializeStoresOptions(storesJson);
                //Initialize order data according to curr option
                var store = $("#ownerStores").val(); //Get selected store
                var storeId= store.substr(0, store.indexOf(' ')); //Get selected store id
                ajaxOwnerOrdersData(storeId);
            }
        }
    });
}

function initializeStoresOptions(storesJson) {
    $("#ownerStores").empty();

    $.each(storesJson || [], function(index, store) {
        $("<option>"+store.id + ' ' + store.name+"</option>").appendTo($("#ownerStores"));
    });
}


function ajaxOwnerOrdersData(storeId) {
    $.ajax({
        url: OWNER_ORDERS_DATA_URL,
        data:{param:storeId},
        success: function(ordersJson) {
            if(ordersJson.length === 0)
            {
                $(".noOrdersInStore").text("No orders from this store");
            }
            else {
                //Initialize orders data
                setOwnerOrdersInStoreCards(ordersJson);
                //Insert items data to all tables
                setOwnerOrderItemsTable(ordersJson);
            }
        }
    });
}

//TODO: TEST DATA WITH ORDER

function setOwnerOrdersInStoreCards(ordersJson)
{
    $("#ordersHistoryForOwner").empty();

    $.each(ordersJson || [], function (index, orderData) {
        $("#ordersHistoryForOwner").add("OrdersHistoryForOwner.html");

        $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneOwnerOrderCard">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+
            //'<h3 class="card-title " id="storeName">'+orderData.name+'</h3>'+

            <!--store data-->
            '<div class="row">'+
            '<b class="font-weight-bold">Id: </b>'+
            '<span  class="col-1" id="id">'+orderData.orderId+'</span>'+

            '<b class="font-weight-bold">Date: </b>'+
            '<span  class="col-1" id="date">'+orderData.date+'</span>'+

            '<b class="font-weight-bold">Customer: </b>'+
            '<span  class="col-1" id="customer">'+orderData.customerName+'</span>'+

            '<b class="font-weight-bold">Coordinate: </b>'+
            '<span  class="col-1" id="coordinate">('+ orderData.xCoordinate + ',' + orderData.yCoordinate+')</span>'+

            '</div>'+
            '<br>'+

            '<div class="row">'+
            '<b class="font-weight-bold">Total amount of items: </b>'+
            '<span  class="col-1" id="itemsTotalAmount">'+orderData.itemsTotalAmount+'</span>'+

            '<b class="font-weight-bold">Total price of items: </b>'+
            '<span  class="col-1" id="itemsTotalPrice">'+orderData.itemsTotalPrice+'</span>'+

            '<b class="font-weight-bold">Delivery Price: </b>'+
            '<span  class="col-1" id="deliveryTotalPrice">'+orderData.deliveryTotalPrice+'</span>'+

            '</div>'+
            '<br>'+

            <!--items in store data-->
            '<table class="orderItemsTable table table-striped table-sm table-hover col-sm-auto" >'+
            '<thead>'+
            '<tr>'+
            '<th >Id</th>'+
            '<th>Name</th>'+
            '<th>Purchase category</th>'+
            '<th>Purchases amount</th>'+
            '<th>Price per unit</th>'+
            '<th>Total price</th>'+
            '<th>Is from sale</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody id="ownerOrderItemsTable">'+
            '</tbody>'+
            '</table>'+
            '<br>'+

            '</div>'+
            '</div>'+
            '</div>').appendTo($("#ordersHistoryForOwner"));

    });
}

function setOwnerOrderItemsTable(ordersJson) {
    // Find and create array of all Item Tables
    var tablesArray=$(".ownerOrderItemsTable");

    // Insert content to each table to BODY
    $.each(tablesArray,function (index,curritemsTable) {
        var contentElement=$(curritemsTable).find("tbody");
        var currOrder = $(ordersJson).get(index);
        var curritemsInOrder = currOrder.itemsInStoreOrderDetails;

        $.each(curritemsInOrder,function (index,item) {
            $("<tr><td>" + item.id  +"</td>" +
                "<td>"+ item.name+"</td>"+
                "<td>"+item.purchaseCategory+"</td>"+
                "<td>"+item.amount+"</td>"+
                "<td>"+item.price+"</td>"+
                "<td>"+item.totalPrice+"</td>"+
                "<td>"+item.isFromSale+"</td>"+
                "</tr>").appendTo(contentElement);
        });
    })
}