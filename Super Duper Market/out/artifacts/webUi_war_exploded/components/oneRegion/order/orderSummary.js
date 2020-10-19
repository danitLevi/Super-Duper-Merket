var ORDER_DATA_URL = buildUrlWithContextPath("orderSummaryData"); //TODO SERVLET
var SAVE_ORDER_URL = buildUrlWithContextPath("saveNewOrder"); //TODO SERVLET

function saveOrder()
{
    handleOrderSubmit();
    handleOrderCancel();
    ajaxOrderData();
}

function ajaxOrderData() {
    $.ajax({
        url: ORDER_DATA_URL,
        success: function(orderDataJson) {
            //Initialize order data
            setOrderDataCards(orderDataJson)
            //Insert items data to all tables
            setOrderDataTable(orderDataJson);
        }
    });
}

//TODO: CHECK NAMES
function setOrderDataCards(orderDataJson)
{
    $("#orderSummary").empty();

    $.each(orderDataJson || [], function (index, orderData) {
        $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneOrderSummaryCard">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+

            <!--store order data-->
            '<div class="row">'+
            '<b class="font-weight-bold">Store Id: </b>'+
            '<span  class="col-1" id="id">'+orderData.id+'</span>'+

            '<b class="font-weight-bold">Store: </b>'+
            '<span  class="col-1" id="storeName">'+orderData.name+'</span>'+
            '</div>'+
            '<br>'+

            '<div class="row">'+
            '<b class="font-weight-bold">Price per km: </b>'+
            '<span  class="col-1" id="ppk">'+orderData.ppk+'</span>'+

            '<b class="font-weight-bold">Distance: </b>'+
            '<span  class="col-1" id="distance">'+orderData.distance+'</span>'+

            '<b class="font-weight-bold">Delivery price: </b>'+
            '<span  class="col-1" id="deliveryPrive">'+orderData.deliveryPrive+'</span>'+

            '</div>'+
            '<br>'+
            <!--items in store data-->
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
            '<tbody id="itemInStoreTableData">'+
            '</tbody>'+
            '</table>'+
            '<br>').appendTo($("#orderSummary"));
    });
}
//TODO: CHECK NAMES
function setOrderDataTable(orderDataJson) {
    // Find and create array of all Item Tables
    var tablesArray=$(".orderedItemsTable");

    // Insert content to each table to BODY
    $.each(tablesArray,function (index,curritemsTable) {
        var contentElement=$(curritemsTable).find("tbody");
        var currOrder = $(orderDataJson).get(index);
        var curritemsInOrder = currStore.itemsInOrder;

        $.each(curritemsInOrder,function (index,item) {
            $("<tr><td>" + item.id  +"</td>" +
                "<td>"+ item.name+"</td>"+
                "<td>"+item.purchaseCategory+"</td>"+
                "<td>"+item.amountOfpurchasesInStore+"</td>"+
                "<td>"+item.price+"</td>"+
                "<td>"+item.totalItemPrice+"</td>"+
                "<td>"+item.isFromSalr+"</td>"+
                "</tr>").appendTo(contentElement);
        });
    })
}

//Save order
function handleOrderSubmit() {
    $("#orderSummaryForm").submit(function () {
        var store = $("#storeFromOrder").val(); //Get selected store
        var storeId= store.substr(0, store.indexOf(' ')); //Get selected store id
        var rate = $("#count-existing").text(); //Get rate
        var review = $("#review").val(); //Get text review

        $.ajax({
            url:SAVE_ORDER_URL,
            method:'POST',
            data:{},
            success:function () {
                $("#SaveOrderMsg").text(" Order saved successfully!");
            }
        });
        return false;
    })
}

//Empty page if customer doesnt want to save order
function handleOrderCancel() {
    $("#cancelOrder").click(function(){
        $("OrderSummaryContent").empty();
    });
}