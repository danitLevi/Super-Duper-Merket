var STORES_DATA_URL = buildUrlWithContextPath("stores");

function triggerStoresAjaxTimeInterval() {
    setInterval(ajaxStoresData, 500);
}

function ajaxStoresData() {
    $.ajax({
        url: STORES_DATA_URL,
        success: function(storesJson) {

            $("#storesData").empty();

            $.each(storesJson || [], function (index, storeData) {
                $("#storesData").add("StoreCardTemplate.html");
               // refreshStoreCard(storeData);

            $('<div class="card shadow p-3  m-4 bg-white rounded" id="oneStoreCard">'+
                    <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
                    '<div class="card-body">'+
                   '<h3 class="card-title " id="storeName">'+storeData.name+'</h3>'+

                    <!--            store data-->
                    '<div class="row">'+
                    '<b class="font-weight-bold">Id: </b>'+
                '<span  class="col-1" id="id">'+storeData.id+'</span>'+

                    '<b class="font-weight-bold">Owner: </b>'+
                '<span  class="col-1" id="Owner"></span>'+ //todo

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
                    <!--                <div >-->
                    '<b class="font-weight-bold">Order amount: </b>'+
                '<span  class="col-1" id="orderAmount">'+storeData.ordersAmount+'</span>'+
                    //<!--</div>-->
                    '<b class="font-weight-bold">Purchased items cost: </b>'+
                '<span  class="col-1" id="itemsProfit"></span>'+ //todo

                    '<b class="font-weight-bold">Price per km: </b>'+
                '<span  class="col-1" id="ppk">'+storeData.deliveryPpk+'</span>'+

                    '<b class=" font-weight-bold">Profit from deliveries: </b>'+
                '<span  class="col-1" id="deliveriesProfit">'+storeData.paymentForDeliveries+'</span>'+

                    '</div>'+
                    '</div>'+
                    '</div>').appendTo($("#storesData"));

            });

            // //added
            setItems();
        }
    });
}


function setItems() {

    var tablesArray=$(".storeItemsTable");
    $.each(tablesArray,function (index,curritemsTable) {
        var contentElement=$(curritemsTable).find("tbody");
        $("<tr><td>added</td>").appendTo(contentElement);

    })
}



//
// function refreshStoreCard(storeData) {
//     //clear all current users
//    // $("#itemInStoreTableData").empty();
//
//     // rebuild the list of users: scan all users and add them to the list of users
//     // $.each(storesJson || [], function(index, storeData) {
//         //create a new <option> tag with a value in it and
//         //appeand it to the #userslist (div with id=userslist) element
//
//         // $("<tr><td>" +
//         //     storeData.id  +"</td>" +
//         //     "<td>"+storeData.name+"</td>"+
//         //     "<td>"+storeData.purchaseCategory+"</td>"+
//         //     "<td>"+storeData.numOfSellers+"</td>"+
//         //     "<td>"+storeData.avgPrice+"</td>"+
//         //     "<td>"+storeData.numOfPurchases+"</td>"+
//         //     "</tr>").appendTo($("#itemInStoreTableData"));
//
//         $("#storeName").text(storeData.name);
//         $("#id").text(storeData.id);
//         //$("#Owner").text(storeData.); //todo
//         $("#coordinate").text(storeData.xCoordinate + storeData.yCoordinate);
//         $("#orderAmount").text(storeData.ordersAmount);
//        // $("#itemsProfit").text(storeData.);
//         $("#ppk").text(storeData.deliveryPpk); //todo
//         $("#deliveriesProfit").text(storeData.paymentForDeliveries);
//
//     //});
// }

// function refreshStorsData(storesJson) {
//    // $("#storesData").add("storesTemplate.html");
//     $.each(storesJson || [], function(index, storeData) {
//        // refreshStoreCard(storesJson);
//         $("#storesData").add(refreshStoreCard(storesJson));
//     });
// }