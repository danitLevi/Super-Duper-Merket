var CUSTOMER_ORDERS_DATA_URL = buildUrlWithContextPath("customerOrders");


function handleCustomerOrdersHistoryWindow() {
    ajaxCustomerOrdersData() ;
    //handleCollapsingBtnClick();
}

// function handleCollapsingBtnClick() {
//     $('.expand-button').on("click",function () {
//             if ($(this).text() == 'Show more information') {
//                 $(this).text('Hide more information');
//             } else {
//                 $(this).text('Show more information');
//             }
//         });
// }


function ajaxCustomerOrdersData() {
    $.ajax({
        url: CUSTOMER_ORDERS_DATA_URL,
        //async:false,
        success: function(ordersJson) {
            if(ordersJson.length === 0)
            {
                $("#ordersHistoryForCustomerPage").empty();
                $("<h4>No orders</h4>").appendTo($("#ordersHistoryForCustomerPage"));
            }
            else {
                setCustomerOrdersData(ordersJson);
            }
        }
    });

}

function setCustomerOrdersData(ordersJson) {
    $.each(ordersJson || [], function(index,oneOrderDetails) {
        $('                <tr>\n' +
            '                    <td id="Id">'+oneOrderDetails.orderId+'</td>\n' +
            '                    <td id="OrderDate">'+oneOrderDetails.strDate+'</td>\n' +
            '                    <td id="Location">('+oneOrderDetails.xCoordinate+','+oneOrderDetails.yCoordinate+')</td>\n' +
            '                    <td id="storesAmount">'+oneOrderDetails.numOfStores+'</td>\n' +
            '                    <td id="itemsAmount">'+myFormatter.format(oneOrderDetails.itemsTotalAmount)+'</td>\n' +
            '                    <td id="itemsCost">'+myFormatter.format(oneOrderDetails.itemsTotalPrice)+' ₪</td>\n' +
            '                    <td id="deliveryCost">'+myFormatter.format(oneOrderDetails.deliveryTotalPrice)+' ₪</td>\n' +
            '                    <td id="orderTotalPrice">'+myFormatter.format(oneOrderDetails.orderTotalPrice)+' ₪</td>\n' +
            '                    <td id="moreDetails"><button class="expand-button btn btn-outline-dark btn-rounded" type="button" data-toggle="collapse" data-target="#collapse'+index+'" >More information</button>\n' +
            '                    </td>\n' +
            '                </tr>\n' +
            '                \n' +
            '                <tr class="hide-table-padding">\n' +
            '                    <td  colspan="9" class="p-0">\n' +
            '                        <div id="collapse'+index+'" class="collapse in p-3">\n' +
            '                                <h3>Items in order</h3>' +
            '                            <div class="row" >'
                                            +getItemsCardsHtmlInCustomerOrder(oneOrderDetails.itemsInOrder)+
            '                           </div>\n' +
            '                        </div>\n' +
            '                    </td>\n' +
            '                </tr>\n'
        ).appendTo($("#customerOrdersData"));
        });

    }

    function getItemsCardsHtmlInCustomerOrder(itemsInOrderData) {
        let str="";
        $.each(itemsInOrderData || [], function(index,oneItemInOrderDetails) {
            str+='                                <div class="card shadow w-25 m-3 bg-white rounded" >\n' +
                '                                    <div class="card-body">\n' +
                '                                        <h3 class="card-title cen"  id="name">'+oneItemInOrderDetails.name+'</h3>\n' +
                '                                    <!--item data-->' +
                '                                        <p class="card-text">\n' +
                '                                            <ul class="list-unstyled mt-3 mb-4">\n' +
                '                                                <li>\n' +
                '                                                    <b class="font-weight-bold">Id:</b>\n' +
                '                                                    <span >'+ oneItemInOrderDetails.id+'</span>\n' +
                '                                                </li>\n' +
                '\n' +
                '                                                <li>\n' +
                '                                                    <b class="font-weight-bold">Purchase category:</b>\n' +
                '                                                    <span >'+ oneItemInOrderDetails.purchaseCategory+'</span>\n' +
                '                                                </li>\n' +
                '\n' +
                '                                                <li>\n' +
                '                                                    <b class="font-weight-bold">Ordered From:</b>\n' +
                '                                                    <span >'+ oneItemInOrderDetails.storeName+'(id='+oneItemInOrderDetails.storeId+')</span>\n' +
                '                                                </li>\n' +
                '\n' +
                '                                                <li>\n' +
                '                                                    <b class="font-weight-bold">Amount:</b>\n' +
                '                                                    <span >'+ myFormatter.format(oneItemInOrderDetails.amount)+getUnitOrKgStr(oneItemInOrderDetails.purchaseCategory)+'</span>\n' +
                '                                                </li>\n' +
                '\n' +
                '                                                <li>\n' +
                '                                                    <b class="font-weight-bold">Item Price:</b>\n' +
                '                                                    <span >'+ myFormatter.format(oneItemInOrderDetails.unitPrice)+' ₪ per'+getUnitOrKgStr(oneItemInOrderDetails.purchaseCategory)+'</span>\n' +
                '                                                </li>\n' +
                '\n' +
                '                                                <li>\n' +
                '                                                    <b class="font-weight-bold">Total price:</b>\n' +
                '                                                    <span >'+ myFormatter.format(oneItemInOrderDetails.totalPrice)+' ₪</span>\n' +
                '                                                </li>\n' +
                '\n' +
                '                                                <li>\n' +
                '                                                    <b class="font-weight-bold">Is from sale?</b>\n' +
                '                                                    <span >'+ getIsFromSaleStr(oneItemInOrderDetails.isFromSale)+'</span>\n' +
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




// function setCustomerOrdersCards(ordersJson)
// {
//     $("#ordersHistoryForCustomer").empty();
//
//     $.each(ordersJson || [], function (index, orderData) {
//         $("#ordersHistoryForCustomer").add("OrdersHistoryForCustomer.html");
//
//         $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneCustomerOrderCard">'+
//             <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
//             '<div class="card-body">'+
//             //'<h3 class="card-title " id="storeName">'+orderData.name+'</h3>'+
//
//             <!--store data-->
//             '<div class="row">'+
//             '<b class="font-weight-bold">Id: </b>'+
//             '<span  id="id">'+orderData.orderId+'</span>'+
//
//             '<b class="font-weight-bold">Date: </b>'+
//             '<span  id="date">'+orderData.strDate+'</span>'+
//
//             '<b class="font-weight-bold">Coordinate: </b>'+
//             '<span id="coordinate">('+ orderData.xCoordinate + ',' + orderData.yCoordinate+')</span>'+
//
//             '</div>'+
//             '<br>'+
//
//             '<div class="row">'+
//             '<b class="font-weight-bold">Num of different stores in order: </b>'+
//             '<span  id="numOfStores">'+orderData.numOfStores+'</span>'+
//
//             '<b class="font-weight-bold">Total amount of items: </b>'+
//             '<span  id="itemsTotalAmount">'+orderData.itemsTotalAmount+'</span>'+
//
//             '</div>'+
//             '<br>'+
//
//             '<div class="row">'+
//             '<b class="font-weight-bold">Total items price: </b>'+
//             '<span  id="itemsTotalPrice">'+orderData.itemsTotalPrice+'</span>'+
//
//             '<b class="font-weight-bold">Total delivery price: </b>'+
//             '<span  id="deliveryTotalPrice">'+orderData.deliveryTotalPrice+'</span>'+
//
//             '<b class="font-weight-bold">Total price: </b>'+
//             '<span  id="orderTotalPrice">'+orderData.orderTotalPrice+'</span>'+
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
//             '<th>Store</th>'+
//             '<th>Purchases amount</th>'+
//             '<th>Price per unit</th>'+
//             '<th>Total price</th>'+
//             '<th>Is from sale</th>'+
//             '</tr>'+
//             '</thead>'+
//             '<tbody id="customerOrderItemsTable">'+
//             '</tbody>'+
//             '</table>'+
//             '<br>'+
//
//             '</div>'+
//             '</div>'+
//             '</div>').appendTo($("#ordersHistoryForCustomer"));
//
//     });
// }
//
// function setCustomerOrderItemsTable(ordersJson) {
//     // Find and create array of all Item Tables
//     var tablesArray=$(".customerOrderItemsTable");
//
//     // Insert content to each table to BODY
//     $.each(tablesArray,function (index,curritemsTable) {
//         var contentElement=$(curritemsTable).find("tbody");
//         var currOrder = $(ordersJson).get(index);
//         var curritemsInOrder = currOrder.itemsInOrder;
//
//         $.each(curritemsInOrder,function (index,item) {
//             $("<tr><td>"+item.id+"</td>" +
//                 "<td>"+ item.name+"</td>"+
//                 "<td>"+item.purchaseCategory+"</td>"+
//                 "<td>"+item.storeName+"</td>"+
//                 "<td>"+item.amount+"</td>"+
//                 "<td>"+item.price+"</td>"+
//                 "<td>"+item.totalPrice+"</td>"+
//                 "<td>"+item.isFromSale+"</td>"+
//                 "</tr>").appendTo(contentElement);
//         });
//     })
// }