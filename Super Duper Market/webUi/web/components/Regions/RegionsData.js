var REGIONS_BASE_DATA_URL = buildUrlWithContextPath("regionsBaseData");
var REGION_PAGE_URL = buildUrlWithContextPath("regionPage");

var REGIONS_DATA_INTERVAL;

$(function() {
    initializeRegionsTableData();
    onBtnClick();
});

function initializeRegionsTableData() {
    ajaxRegionsBaseData();
    triggerAjaxRegionsDataTimeInterval();
}

function triggerAjaxRegionsDataTimeInterval() {

    REGIONS_DATA_INTERVAL=setInterval(ajaxRegionsBaseData, 2000);
}

function ajaxRegionsBaseData() {
    $.ajax({
        url: REGIONS_BASE_DATA_URL,
        success: function(regionsJson) {
            refreshRegionsTable(regionsJson);
        }
    });
}


// function refreshRegionsTable(regionsJson) {
//     //clear all current users
//     $("#regionsTableData").empty();
//
//     // rebuild the list of users: scan all users and add them to the list of users
//     $.each(regionsJson || [], function(index, regionData) {
//         //create a new <option> tag with a value in it and
//         //appeand it to the #userslist (div with id=userslist) element
//         $("<tr><td>" +
//             regionData.ownerName  +"</td>" +
//             "<td>"+regionData.regionName+"</td>"+
//             "<td>"+regionData.itemsTypesAmount+"</td>"+
//             "<td>"+regionData.storesAmount+"</td>"+
//             "<td>"+regionData.ordersAmount+"</td>"+
//             "<td>"+regionData.orderAvgCost+"</td>"+
//             "<td><button class='btnSelect'>More details</button></td>" +
//             "</tr>").appendTo($("#regionsTableData"));
//     });
// }


function refreshRegionsTable(regionsJson) {
    //clear all current users
    $("#regionsTableData").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(regionsJson || [], function(index, regionData) {
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $("<tr><td>" +
            regionData.ownerName  +"</td>" +
            "<td>"+regionData.regionName+"</td>"+
            "<td>"+regionData.itemsTypesAmount+"</td>"+
            "<td>"+regionData.storesAmount+"</td>"+
            "<td>"+regionData.ordersAmount+"</td>"+
            "<td>"+myFormatter.format(regionData.orderAvgCost)+"₪"+"</td>"+
            "<td><button type='button' class='btnSelect btn-outline-dark btn-rounded'>Select region</button></td>" +
            "</tr>").appendTo($("#regionsTableData"));
    });
}




function onBtnClick() {
$("#regionsTableData").on('click','.btnSelect',function(){
    var currentRow=$(this).closest("tr");
    var regionName=currentRow.find("td:eq(1)").text(); // get current row 2nd TD
    // triggerAjaxRegionsDataTimeOut(regionName);
    stopIntervalsInRegionsPage();
    triggerAjaxRegionPage(regionName);
})
}

// function triggerAjaxRegionsDataTimeOut(regionName) {
//     setTimeout(triggerAjexRegionPage(regionName), 200);
// }

function triggerAjaxRegionPage(regionName) {
    $.ajax({
        url: REGION_PAGE_URL,
        // method:'POST',
        data:{name:regionName},
        success:function (resp) {
            window.location.assign(resp);
            },
        error:function () {
            console.log("error")
        }
    });
}

function stopIntervalsInRegionsPage() {
    clearInterval(REGIONS_DATA_INTERVAL);
    clearInterval(TRANSACTIONS_DATA_INTERVAL);
    clearInterval(USERS_DATA_INTERVAL);
    clearInterval(BALANCE_DATA_INTERVAL);
}