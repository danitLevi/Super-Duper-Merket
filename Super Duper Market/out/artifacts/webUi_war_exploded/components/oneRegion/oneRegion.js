var SESSION_URL = buildUrlWithContextPath("mySession");
var REGION_NAME_URL = buildUrlWithContextPath("getRegionName");

$(function () {
    setRegionName();
    ajaxSetMenuByUserType();

    itemsClicked();
    StoresClicked();
})

function itemsClicked() {
    $("#itemsPage").click(function () {
        $("#content").load("ItemsTemplate.html");
        triggerItemsAjaxTimeInterval();
    })
}

function StoresClicked() {
    $("#storesPage").click(function () {
        $("#content").load("storesTemplate.html");
         triggerStoresAjaxTimeInterval();
    })
}

function ManagerFeedbacksClicked() {
    $("#feedbacksPage").click(function () {
        $("#content").load("feedbacksTemplate.html");
        triggerFeedbacksAjaxTimeInterval();
    })
}

function AddNewStoreClicked() {
    $("#newStorePage").click(function () {
        $("#content").load("AddNewStore.html");
        addNewStore();
    })
}

function ajaxSetMenuByUserType() {
    $.ajax({
        url: SESSION_URL,
        success: function (userType) {
            if(userType==="Customer")
            {
                $("<button type='button' class='btn1 btn1-pink btn-rounded' id='orderPage'>Order</button>" +
                    "<button type='button' class='btn1 btn1-pink btn-rounded' id='orderHistoryPage'>OrdersHistory</button>" +
                    "").appendTo($("#oneRegionMenu"));
            }
            else
            {
                $("<button type='button' class='btn1 btn1-pink btn-rounded' id='ordersFromStorePage'>Orders from my stores</button>" +
                    "<button type='button' class='btn1 btn1-pink btn-rounded' id='feedbacksPage'>Feedbacks</button>" +
                    "<button type='button' class='btn1 btn1-pink btn-rounded' id='newStorePage'>Open new store</button>"
                    ).appendTo($("#oneRegionMenu"));
                ManagerFeedbacksClicked();
                AddNewStoreClicked();
            }
        }
    });
}

function setRegionName() {
    $.ajax({
        url: REGION_NAME_URL,
        success: function (regionName) {
            $("#regionName").text(regionName);
        }
    });
}


// danit added
function orderClicked() {
    $("#orderPage").click(function () {
        $("#content").load("OrderTemplate.html");
        triggerItemsAjaxTimeInterval();
    })
}