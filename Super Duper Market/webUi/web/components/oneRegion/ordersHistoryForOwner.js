var ORDERS_DATA_URL = buildUrlWithContextPath("orders");

function triggerOrdersAjaxTimeInterval() {
    setInterval(ajaxOrdersData, 500);
}

function ajaxOrdersData() {
    $.ajax({
        url: STORES_DATA_URL,
        success: function(ordersJson) {

            //Initialize orders data
            setOrdersInRegionCards(ordersJson);
            //Insert items data to all tables
            setOrderItemsTable(ordersJson);
        }
    });
}

//TODO: CHECK NAMES
function setOrdersInRegionCards(ordersJson)
{
    $("#ordersHistoryForOwner").empty();

    $.each(ordersJson || [], function (index, orderData) {
        $("#ordersHistoryForOwner").add("StoreCardTemplate.html");

        $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneStoreCard">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+
            //'<h3 class="card-title " id="storeName">'+orderData.name+'</h3>'+

            <!--store data-->
            '<div class="row">'+
            '<b class="font-weight-bold">Id: </b>'+
            '<span  class="col-1" id="id">'+orderData.id+'</span>'+

            '<b class="font-weight-bold">Date: </b>'+
            '<span  class="col-1" id="Date">'+orderData.date+'</span>'+

            '<b class="font-weight-bold">Customer: </b>'+
            '<span  class="col-1" id="Customer">'+orderData.customer+'</span>'+

            '<b class="font-weight-bold">Coordinate: </b>'+
            '<span  class="col-1" id="coordinate">('+ orderData.xCoordinate + ',' + orderData.yCoordinate+')</span>'+

            '</div>'+
            '<br>'+

            '<div class="row">'+
            '<b class="font-weight-bold">Total amount of items: </b>'+
            '<span  class="col-1" id="Customer">'+orderData.totalAmountOfItems+'</span>'+

            '<b class="font-weight-bold">Total price of items: </b>'+
            '<span  class="col-1" id="Customer">'+orderData.totalPriceOfItems+'</span>'+

            '<b class="font-weight-bold">Delivery Price: </b>'+
            '<span  class="col-1" id="Customer">'+orderData.deliveryPrice+'</span>'+

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
            '<tbody id="itemInStoreTableData">'+
            '</tbody>'+
            '</table>'+
            '<br>'+

            '</div>'+
            '</div>'+
            '</div>').appendTo($("#ordersHistoryForOwner"));

    });
}


//TODO: CHECK NAMES

function setOrderItemsTable(ordersJson) {
    // Find and create array of all Item Tables
    var tablesArray=$(".orderItemsTable");

    // Insert content to each table to BODY
    $.each(tablesArray,function (index,curritemsTable) {
        var contentElement=$(curritemsTable).find("tbody");
        var currStore = $(ordersJson).get(index);
        var curritemsInStore = currStore.itemsInStore;

        $.each(curritemsInStore,function (index,item) {
            $("<tr><td>" + item.id  +"</td>" +
                "<td>"+ item.name+"</td>"+
                "<td>"+item.purchaseCategory+"</td>"+
                "<td>"+item.amount+"</td>"+
                "<td>"+item.pricePerUnit+"</td>"+
                "<td>"+item.totalPrice+"</td>"+
                "<td>"+item.isFromSale+"</td>"+
                "</tr>").appendTo(contentElement);
        });
    })
}