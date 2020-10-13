var CUSTOMER_ORDERS_DATA_URL = buildUrlWithContextPath("customerOrders");

function triggerCustomerOrdersAjaxTimeInterval() {
    setInterval(ajaxCustomerOrdersData, 500);
}

function ajaxCustomerOrdersData() {
    $.ajax({
        url: CUSTOMER_ORDERS_DATA_URL,
        success: function(ordersJson) {
            if(ordersJson.length === 0)
            {
                $(".noCustomerOrders").text("No orders found");
            }
            else {
                //Initialize orders data
                setCustomerOrdersCards(ordersJson);
                //Insert items data to all tables
                setCustomerOrderItemsTable(ordersJson);
            }
        }
    });
}

function setCustomerOrdersCards(ordersJson)
{
    $("#ordersHistoryForCustomer").empty();

    $.each(ordersJson || [], function (index, orderData) {
        $("#ordersHistoryForCustomer").add("OrdersHistoryForCustomer.html");

        $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneCustomerOrderCard">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+
            //'<h3 class="card-title " id="storeName">'+orderData.name+'</h3>'+

            <!--store data-->
            '<div class="row">'+
            '<b class="font-weight-bold">Id: </b>'+
            '<span  class="col-1" id="id">'+orderData.orderId+'</span>'+

            '<b class="font-weight-bold">Date: </b>'+
            '<span  class="col-1" id="date">'+orderData.date+'</span>'+

            '<b class="font-weight-bold">Coordinate: </b>'+
            '<span  class="col-1" id="coordinate">('+ orderData.xCoordinate + ',' + orderData.yCoordinate+')</span>'+

            '</div>'+
            '<br>'+

            '<div class="row">'+
            '<b class="font-weight-bold">Num of different stores in order: </b>'+
            '<span  class="col-1" id="numOfStores">'+orderData.numOfStores+'</span>'+

            '<b class="font-weight-bold">Total amount of items: </b>'+
            '<span  class="col-1" id="itemsTotalAmount">'+orderData.itemsTotalAmount+'</span>'+

            '</div>'+
            '<br>'+

            '<div class="row">'+
            '<b class="font-weight-bold">Total items price: </b>'+
            '<span  class="col-1" id="itemsTotalPrice">'+orderData.itemsTotalPrice+'</span>'+

            '<b class="font-weight-bold">Total delivery price: </b>'+
            '<span  class="col-1" id="deliveryTotalPrice">'+orderData.deliveryTotalPrice+'</span>'+

            '<b class="font-weight-bold">Total price: </b>'+
            '<span  class="col-1" id="orderTotalPrice">'+orderData.orderTotalPrice+'</span>'+

            '</div>'+
            '<br>'+

            <!--items in store data-->
            '<table class="orderItemsTable table table-striped table-sm table-hover col-sm-auto" >'+
            '<thead>'+
            '<tr>'+
            '<th >Id</th>'+
            '<th>Name</th>'+
            '<th>Purchase category</th>'+
            '<th>Store</th>'+
            '<th>Purchases amount</th>'+
            '<th>Price per unit</th>'+
            '<th>Total price</th>'+
            '<th>Is from sale</th>'+
            '</tr>'+
            '</thead>'+
            '<tbody id="customerOrderItemsTable">'+
            '</tbody>'+
            '</table>'+
            '<br>'+

            '</div>'+
            '</div>'+
            '</div>').appendTo($("#ordersHistoryForCustomer"));

    });
}

function setOwnerOrderItemsTable(ordersJson) {
    // Find and create array of all Item Tables
    var tablesArray=$(".customerOrderItemsTable");

    // Insert content to each table to BODY
    $.each(tablesArray,function (index,curritemsTable) {
        var contentElement=$(curritemsTable).find("tbody");
        var currOrder = $(ordersJson).get(index);
        var curritemsInOrder = currOrder.itemsInOrder;

        $.each(curritemsInOrder,function (index,item) {
            $("<tr><td>"+item.id+"</td>" +
                "<td>"+ item.name+"</td>"+
                "<td>"+item.purchaseCategory+"</td>"+
                "<td>"+item.storeName+"</td>"+
                "<td>"+item.amount+"</td>"+
                "<td>"+item.price+"</td>"+
                "<td>"+item.totalPrice+"</td>"+
                "<td>"+item.isFromSale+"</td>"+
                "</tr>").appendTo(contentElement);
        });
    })
}