var OWNER_STORE_ORDERS_DATA_URL = buildUrlWithContextPath("ownerStoreOrders");
var OWNER_STORES_URL =  buildUrlWithContextPath("getOwnerStoresInRegion");

var OWNER_STORES_ORDERS_HISTORY_DATA_INTERVAL;

// function on page load
// Show orders data according to selected store
function initializeOrdersHistoryForOwner(){
    triggerOwnerStoresAjaxTimeInterval();
    handleOwnerStoreChange();

    // todo: check if ok and pick in other js (and remove duplicate in customer orders history)
    handleCollapsingBtnClick2();
}

function handleCollapsingBtnClick2() {
    $('.expand-button').on("click",function () {
        console.log("ok");
        if ($(this).text() == 'Show more information') {
            $(this).text('Hide more information');
        } else {
            $(this).text('Show more information');
        }
    });


}

function triggerOwnerStoresAjaxTimeInterval() {
    OWNER_STORES_ORDERS_HISTORY_DATA_INTERVAL=setInterval(ajaxOwnerStoresOptionsData, 1000);
}

//Initialize stores options
function ajaxOwnerStoresOptionsData() {
    $.ajax({
        url: OWNER_STORES_URL,
        success: function(storesJson) {
            if(storesJson.length==0)
            {
                $("#ownerOrdersHistoryContent").empty();
                $("<h4>You don't own any store</h4>").appendTo($("#ownerOrdersHistoryContent"));
            }
            else
            {
                initializeOwnerStoresOptions(storesJson);

            }
        }
    });
}

function initializeOwnerStoresOptions(storesJson) {

    if( $("#ownerStores")[0].children.length>1)
    {
        var currSelectedStore=$("#ownerStores").val();
        $("#ownerStores").empty();
    }


    $('<option value="" disabled>Select store</option>').appendTo($("#ownerStores"));

    $.each(storesJson || [], function(index, store) {
        $("<option value='"+store.id+"'>"+store.name + "(id=" + store.id+")</option>").appendTo($("#ownerStores"));
    });
    $("#ownerStores").val(currSelectedStore);

}

function handleOwnerStoreChange() {
$("#ownerStores").change(function () {
    var storeId = $("#ownerStores").val(); //Get selected store id
    // triggerOwnerStoresAjaxTimeInterval(storeId);
        ajaxOwnerStoreOrdersData(storeId);
    })
}

function ajaxOwnerStoreOrdersData(storeId) {
    $.ajax({
        url: OWNER_STORE_ORDERS_DATA_URL,
        data:{"storeId":storeId},
        success: function(ordersJson) {
            if(ordersJson.length === 0)
            {
                $("#ordersHistoryForOwnerPage").empty();
                $("<h5 id='noOrders'>No orders in current store</h5>").appendTo($("#ordersHistoryForOwnerPage"));
            }
            else {
                replaceNoOrders();
                setOwnerStoreOrdersData(ordersJson);
            }
        }
    });
}

function replaceNoOrders() {

    // check if element noOrders exist
    if($("#noOrders").length)
    {
        $("#noOrders").remove();
        $('            <div class="table-responsive">\n' +
            '                <table class="table table-striped table-sm table-hover col-sm-auto ">\n' +
            '                    <thead>\n' +
            '                    <tr>\n' +
            '                        <th scope="col">Id</th>\n' +
            '                        <th scope="col">Order date</th>\n' +
            '                        <th scope="col">Customer name</th>\n' +
            '                        <th scope="col">Location</th>\n' +
            '                        <th scope="col">Items amount</th>\n' +
            '                        <th scope="col">Items total cost</th>\n' +
            '                        <th scope="col">Delivery cost</th>\n' +
            '                        <th scope="col"></th>\n' +
            '                    </tr>\n' +
            '                    </thead>\n' +
            '\n' +
            '                    <tbody id="OwnerStoreOrdersData">\n' +
            '\n' +
            '                    </tbody>\n' +
            '                </table>\n' +
            '            </div> ').appendTo($("#ordersHistoryForOwnerPage"));
    }

}

function setOwnerStoreOrdersData(ordersJson) {

    $("#OwnerStoreOrdersData").empty();

    $.each(ordersJson || [], function(index,oneOrderDetails) {
        $('                <tr>\n' +
            '                    <td id="Id">'+oneOrderDetails.orderId+'</td>\n' +
            '                    <td id="OrderDate">'+oneOrderDetails.strDate+'</td>\n' +
            '                    <td id="customerName">'+oneOrderDetails.customerName+'</td>\n' +
            '                    <td id="Location">('+oneOrderDetails.xCoordinate+','+oneOrderDetails.yCoordinate+')</td>\n' +
            '                    <td id="itemsAmount">'+myFormatter.format(oneOrderDetails.itemsTotalAmount)+'</td>\n' +
            '                    <td id="itemsCost">'+myFormatter.format(oneOrderDetails.itemsTotalPrice)+' ₪</td>\n' +
            '                    <td id="deliveryCost">'+myFormatter.format(oneOrderDetails.deliveryTotalPrice)+' ₪</td>\n' +
            '                    <td id="moreDetails"><button class="expand-button btn btn-outline-dark btn-rounded" type="button" data-toggle="collapse" data-target="#collapse'+index+'" >Show more information</button>\n' +
            '                    </td>\n' +
            '                </tr>\n' +
            '                \n' +
            '                <tr class="hide-table-padding">\n' +
            '                    <td  colspan="9" class="p-0">\n' +
            '                        <div id="collapse'+index+'" class="collapse in p-3">\n' +
            '                                <h3>Items in order</h3>' +
            '                            <div class="row" >'
            +getItemsCardsHtmlInStoreOrder(oneOrderDetails.itemsInStoreOrderDetails)+
            '                           </div>\n' +
            '                        </div>\n' +
            '                    </td>\n' +
            '                </tr>\n'
        ).appendTo($("#OwnerStoreOrdersData"));
    });

}

function getItemsCardsHtmlInStoreOrder(itemsInOrderData) {
    let str="";
    $.each(itemsInOrderData || [], function(index,oneItemInStoreOrderDetails) {
        str+='                                <div class="card shadow w-25 m-3 bg-white rounded" >\n' +
            '                                    <div class="card-body">\n' +
            '                                        <h3 class="card-title cen"  id="name">'+oneItemInStoreOrderDetails.name+'</h3>\n' +
            '                                    <!--item data-->' +
            '                                        <p class="card-text">\n' +
            '                                            <ul class="list-unstyled mt-3 mb-4">\n' +
            '                                                <li>\n' +
            '                                                    <b class="font-weight-bold">Id:</b>\n' +
            '                                                    <span >'+ oneItemInStoreOrderDetails.id+'</span>\n' +
            '                                                </li>\n' +
            '\n' +
            '                                                <li>\n' +
            '                                                    <b class="font-weight-bold">Purchase category:</b>\n' +
            '                                                    <span >'+ oneItemInStoreOrderDetails.purchaseCategory+'</span>\n' +
            '                                                </li>\n' +
            '\n' +
            '                                                <li>\n' +
            '                                                    <b class="font-weight-bold">Amount:</b>\n' +
            '                                                    <span >'+ myFormatter.format(oneItemInStoreOrderDetails.amount)+getUnitOrKgStr(oneItemInStoreOrderDetails.purchaseCategory)+'</span>\n' +
            '                                                </li>\n' +
            '\n' +
            '                                                <li>\n' +
            '                                                    <b class="font-weight-bold">Item Price:</b>\n' +
            '                                                    <span >'+ myFormatter.format(oneItemInStoreOrderDetails.unitPrice)+' ₪ per'+getUnitOrKgStr(oneItemInStoreOrderDetails.purchaseCategory)+'</span>\n' +
            '                                                </li>\n' +
            '\n' +
            '                                                <li>\n' +
            '                                                    <b class="font-weight-bold">Total price:</b>\n' +
            '                                                    <span >'+ myFormatter.format(oneItemInStoreOrderDetails.totalPrice)+' ₪</span>\n' +
            '                                                </li>\n' +
            '\n' +
            '                                                <li>\n' +
            '                                                    <b class="font-weight-bold">Is from sale?</b>\n' +
            '                                                    <span >'+ getIsFromSaleStr(oneItemInStoreOrderDetails.isFromSale)+'</span>\n' +
            '                                                </li>\n' +
            '                                            </ul>\n' +
            '                                        </p>\n' +
            '\n' +
            '\n' +
            '                                </div>\n' +
            '\n' +
            '                                </div>';

    });
    return str;
}




//
// function ajaxOwnerStoreOrdersData(storeId) {
//     $.ajax({
//         url: OWNER_STORE_ORDERS_DATA_URL,
//         data:{storeId:storeId},
//         success: function(ordersJson) {
//             if(ordersJson.length === 0)
//             {
//                 $(".noOrdersInStore").text("No orders from this store");
//             }
//             else {
//
//                 // todo !!!!!!!!!!!!!!!!!!!!!!!!!(uncomment )
//
//                 // //Initialize orders data
//                 // setOwnerOrdersInStoreCards(ordersJson);
//                 // //Insert items data to all tables
//                 // setOwnerOrderItemsTable(ordersJson);
//             }
//         }
//     });
// }
//
// //TODO: TEST DATA WITH ORDER
//
// function setOwnerOrdersInStoreCards(ordersJson)
// {
//     $("#ordersHistoryForOwner").empty();
//
//     $.each(ordersJson || [], function (index, orderData) {
//         $("#ordersHistoryForOwner").add("OrdersHistoryForOwner.html");
//
//         $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneOwnerOrderCard">'+
//             <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
//             '<div class="card-body">'+
//             //'<h3 class="card-title " id="storeName">'+orderData.name+'</h3>'+
//
//             <!--store data-->
//             '<div class="row">'+
//             '<b class="font-weight-bold">Id: </b>'+
//             '<span  class="col-1" id="id">'+orderData.orderId+'</span>'+
//
//             '<b class="font-weight-bold">Date: </b>'+
//             '<span  class="col-1" id="date">'+orderData.date+'</span>'+
//
//             '<b class="font-weight-bold">Customer: </b>'+
//             '<span  class="col-1" id="customer">'+orderData.customerName+'</span>'+
//
//             '<b class="font-weight-bold">Coordinate: </b>'+
//             '<span  class="col-1" id="coordinate">('+ orderData.xCoordinate + ',' + orderData.yCoordinate+')</span>'+
//
//             '</div>'+
//             '<br>'+
//
//             '<div class="row">'+
//             '<b class="font-weight-bold">Total amount of items: </b>'+
//             '<span  class="col-1" id="itemsTotalAmount">'+orderData.itemsTotalAmount+'</span>'+
//
//             '<b class="font-weight-bold">Total price of items: </b>'+
//             '<span  class="col-1" id="itemsTotalPrice">'+orderData.itemsTotalPrice+'</span>'+
//
//             '<b class="font-weight-bold">Delivery Price: </b>'+
//             '<span  class="col-1" id="deliveryTotalPrice">'+orderData.deliveryTotalPrice+'</span>'+
//
//             '</div>'+
//             '<br>'+
//
//             <!--items in store data-->
//             '<table class="orderItemsTable table table-striped table-sm table-hover col-sm-auto" >'+
//             '<thead>'+
//             '<tr>'+
//             '<th >Id</th>'+
//             '<th>Name</th>'+
//             '<th>Purchase category</th>'+
//             '<th>Purchases amount</th>'+
//             '<th>Price per unit</th>'+
//             '<th>Total price</th>'+
//             '<th>Is from sale</th>'+
//             '</tr>'+
//             '</thead>'+
//             '<tbody id="ownerOrderItemsTable">'+
//             '</tbody>'+
//             '</table>'+
//             '<br>'+
//
//             '</div>'+
//             '</div>'+
//             '</div>').appendTo($("#ordersHistoryForOwner"));
//
//     });
// }
//
// function setOwnerOrderItemsTable(ordersJson) {
//     // Find and create array of all Item Tables
//     var tablesArray=$(".ownerOrderItemsTable");
//
//     // Insert content to each table to BODY
//     $.each(tablesArray,function (index,curritemsTable) {
//         var contentElement=$(curritemsTable).find("tbody");
//         var currOrder = $(ordersJson).get(index);
//         var curritemsInOrder = currOrder.itemsInStoreOrderDetails;
//
//         $.each(curritemsInOrder,function (index,item) {
//             $("<tr><td>" + item.id  +"</td>" +
//                 "<td>"+ item.name+"</td>"+
//                 "<td>"+item.purchaseCategory+"</td>"+
//                 "<td>"+item.amount+"</td>"+
//                 "<td>"+item.price+"</td>"+
//                 "<td>"+item.totalPrice+"</td>"+
//                 "<td>"+item.isFromSale+"</td>"+
//                 "</tr>").appendTo(contentElement);
//         });
//     })
// }