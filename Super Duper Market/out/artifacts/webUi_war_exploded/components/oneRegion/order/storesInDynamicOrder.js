var STORES_IN_DYNAMIC_ORDER_DATA_URL = buildUrlWithContextPath("storesInDynamicOrder");

function handleStoresInDynamicOrderWindow()
{
    ajaxStoresInDynamicOrderData();
    handleContinueButtonInDynamicStoresWindow();
}

function ajaxStoresInDynamicOrderData() {
    $.ajax({
        url: STORES_IN_DYNAMIC_ORDER_DATA_URL,
        success: function(storesJson) {

            //Initialize stores data
            setStoresInDynamicOrderCards(storesJson)
        }
    });
}


function setStoresInDynamicOrderCards(storesInDynamicOrderJson)
{

    $.each(storesInDynamicOrderJson || [], function (index, storeData) {
        $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneStoreCard">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+
            '<h3 class="card-title " id="storeName">'+storeData.name+'</h3>'+

            <!--store data-->
            '<div class="row">'+
            '<b class="font-weight-bold">Id: </b>'+
            '<span  class="col-1" id="id">'+storeData.id+'</span>'+

            '<b class="font-weight-bold">Location: </b>'+
            '<span  class="col-1" id="location">('+ storeData.xCoordinate + ',' + storeData.yCoordinate+')</span>'+

            '</div>'+

            '<div class="row">'+
            '<b class="font-weight-bold">Distance from order location: </b>'+
            '<span  class="col-1" id="distanceFromOrder">'+myFormatter.format(storeData.distanceFromOrder)+' KM</span>'+
            '<b class="font-weight-bold">Price per KM: </b>'+
            '<span  class="col-1" id="deliveryPpk">'+myFormatter.format(storeData.deliveryPpk)+' ₪</span>'+

            '<b class="font-weight-bold">Delivery cost: </b>'+
            '<span  class="col-1" id="deliveryPrice">'+myFormatter.format(storeData.deliveryPrice)+' ₪</span>'+

            '</div>'+

            '<div class="row">'+
            '<b class="font-weight-bold">ordered items types amount: </b>'+
            '<span  class="col-1" id="amountOfItemsTypes">'+storeData.amountOfItemsTypes+'</span>'+

            '<b class="font-weight-bold">orderd items total price </b>'+
            '<span  class="col-1" id="itemsTotalPrice">'+myFormatter.format(storeData.itemsTotalPrice)+' ₪</span>'+

            '</div>'+

            '</div>'+
            '</div>').appendTo($("#storesInDynamicOrderData"));

    });
}


function handleContinueButtonInDynamicStoresWindow() {

    $("#continueButton").click(function () {
        openSalesWindow();
        // $("#content").load("orderTemplates/SalesInOrder.html",handleSalesWindow);
    })
}
