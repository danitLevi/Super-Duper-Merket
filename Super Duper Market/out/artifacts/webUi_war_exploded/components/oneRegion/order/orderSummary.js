var ORDER_DATA_URL = buildUrlWithContextPath("getOrderSummaryData");
var SAVE_ORDER_URL = buildUrlWithContextPath("saveOrder");

function handleOrderSummaryWindow()
{
    handleOrderConfirm();
    handleOrderCancel();
    ajaxOrderSummaryData();
}

function ajaxOrderSummaryData() {
    $.ajax({
        url: ORDER_DATA_URL,
        success: function(orderSummaryDataJson) {
            setOrderSummaryData(orderSummaryDataJson);
        }
    });
}

function setOrderSummaryData(orderSummaryDataJson) {
    setGeneralData(orderSummaryDataJson);
    setStoresInOrderData(orderSummaryDataJson.storesInOrderSummary)
}

function setGeneralData(orderSummaryDataJson) {
    $("#orderDate").text(orderSummaryDataJson.orderDate);
    $("#orderLocation").text("("+orderSummaryDataJson.orderXCoordinate+","+orderSummaryDataJson.orderYCoordinate+")");

    $("#totalItemsPrice").text(myFormatter.format(orderSummaryDataJson.itemsTotalPrice) +" ₪");
    $("#totalDeliveryPrice").text(myFormatter.format(orderSummaryDataJson.deliveryTotalPrice) +" ₪");
    $("#totalOrderPrice").text(myFormatter.format(orderSummaryDataJson.orderTotalPrice) +" ₪");


}

//TODO: CHECK NAMES
function setStoresInOrderData(storesInOrderSummaryDataJson)
{
    // $("#storesInOrderCards").empty();

    $.each(storesInOrderSummaryDataJson || [], function (index, storeInOrderData) {
        $('<div class="card shadow p-3 m-4 bg-white rounded" id="oneStoreCard'+index+'">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+
            '<h3 class="card-title border-bottom border-gray ">Order from '+storeInOrderData.storeName+':</h3>'+
            <!--store order data-->
            '<div class="flex-row">'+
            '<b class="font-weight-bold">Store Id: </b>'+
            '<span  class="col-1" id="id">'+storeInOrderData.storeId+'</span>'+
            //
            // '<b class="font-weight-bold">Store: </b>'+
            // '<span  class="col-1" id="storeName">'+orderData.name+'</span>'+
            '</div>'+
            '<br>'+

            '<div class="flex-row">'+
                '<b class="font-weight-bold">Price per km: </b>'+
                '<span  class="col-2" id="ppk">'+storeInOrderData.ppk+' ₪</span>'+

                '<b class="font-weight-bold">Distance: </b>'+
                '<span  class="col-2" id="distance">'+myFormatter.format(storeInOrderData.distance)+' KM</span>'+

                '<b class="font-weight-bold">Delivery price: </b>'+
                '<span  class="col-2" id="deliveryPrive">'+ myFormatter.format(storeInOrderData.deliveryPrice)+'₪</span>'+

            '</div>'+
            '<br/>'+
            '<div>'
                +'<h5 class="m-2">Ordered items:</h5>'+

                <!--items in store data-->
            '<div class="table-responsive" >'+
                '<table class="orderedItemsTable table table-striped table-sm table-hover col-sm-auto" >'+
                '<thead>'+
                '<tr>'+
                '<th >Id</th>'+
                '<th>Name</th>'+
                '<th>Purchase category</th>'+
                '<th>Purchases amount</th>'+
                '<th>Item price</th>'+
                '<th>Total price</th>'+
                '<th>Is from sale</th>'+
                '</tr>'+
                '</thead>'+
                '<tbody id="itemInStoreTableData">'

                +setItemsData(storeInOrderData.itemsInStoreOrderDetails)+

                '</tbody>'+
                '</table>'+
            '</div>'+
            '</div>'+
            '<br>').appendTo($("#storesInOrderCards"));
    });
}
//TODO: CHECK NAMES
function setItemsData(itemsInCurrStore) {
    str="";
        $.each(itemsInCurrStore || [],function (index,currItemInStore) {

            str+="<tr><td>" + currItemInStore.id  +"</td>" +
            "<td>"+ currItemInStore.name+"</td>"+
            "<td>"+currItemInStore.purchaseCategory+"</td>"+
            "<td>"+myFormatter.format(currItemInStore.amount)+"</td>"+
            "<td>"+myFormatter.format(currItemInStore.unitPrice)+" ₪ " +getUnit(currItemInStore)+ "</td>"+
            "<td>"+myFormatter.format(currItemInStore.totalPrice)+" ₪</td>"+
            "<td>"+getIsFromSale(currItemInStore)+"</td>"+
            "</tr>"
        });

    return str;
}

function getIsFromSale(currItemInStore) {
if(currItemInStore.isFromSale)
    return "Yes";
return "No";
}

function getUnit(currItemInStore) {
if(currItemInStore.purchaseCategory=="Quantity")
{
    return"per unit";
}
return  "per Kg"
}

//Save order
function handleOrderConfirm() {
    $("#confirmOrder").click(function () {
        $.ajax({
            url:SAVE_ORDER_URL,
            method:'POST',
            success:function (newOrderId) {
                // $("#SaveOrderMsg").text(" Order saved successfully!");
                $("#content").load("FillFeedback.html",initializeFeedbackPage);
                triggerSaveOrderAlertMsgToShow(newOrderId);
            }
        });
        return false;
    })
}

//Empty page if customer doesnt want to save order
function handleOrderCancel() {
    $("#cancelOrder").click(function(){
        $("#OrderSummaryContent").empty();
    });
}

function triggerSaveOrderAlertMsgToShow(newOrderId) {

    $.ajax({
        url:SAVE_ALERT_TO_SHOW_URL,
        method:'POST',
        data:{alertType:"order",newOrderId:newOrderId}
    });
}