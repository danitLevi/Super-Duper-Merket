var SESSION_URL = buildUrlWithContextPath("mySession");
var REGION_NAME_URL = buildUrlWithContextPath("getRegionName");

$(function () {
    setRegionName();
    ajaxSetMenuByUserType();

  itemsClicked();
  StoresClicked();
  backClicked();

})

function itemsClicked() {
    $("#itemsPage").click(function () {
        stopIntervalsInOneRegionPage();
        $("#content").load("ItemsTemplate.html");
        triggerItemsAjaxTimeInterval();
    })
}

function StoresClicked() {
    $("#storesPage").click(function () {
        stopIntervalsInOneRegionPage();
        $("#content").load("storesTemplate.html");
         triggerStoresAjaxTimeInterval();
    })
}

function ManagerFeedbacksClicked() {
    $("#feedbacksPage").click(function () {
        stopIntervalsInOneRegionPage();
        $("#content").load("feedbacksTemplate.html");
        triggerFeedbacksAjaxTimeInterval();
    })
}

function AddNewStoreClicked() {
    $("#newStorePage").click(function () {
        stopIntervalsInOneRegionPage();
        $("#content").load("AddNewStore.html", initializeAddNewStore);
    })
}

function initializeAddNewStore(){
    initializeItemsPerRegion();
    // handleRegionChange();
    // handleAddStoreSubmitting();
    addNewStore();
}

function OwnerOrdersHistoryClicked() {
    $("#ordersFromStorePage").click(function () {
        stopIntervalsInOneRegionPage();
        $("#content").load("OrdersHistoryForOwner.html",initializeOrdersHistoryForOwner);

    })
}

function CustomerOrdersHistoryClicked() {
    $("#orderHistoryPage").click(function () {
        stopIntervalsInOneRegionPage();
        // $("#content").load("OrdersHistoryForCustomer.html",handleCustomerOrdersHistoryWindow);
        $("#content").load("OrdersHistoryForCustomer.html",handleCustomerOrdersHistoryWindow);

    })
}

function ajaxSetMenuByUserType() {
    $.ajax({
        url: SESSION_URL,
        success: function (userType) {
            if(userType==="Customer")
            {
                $("<button type='button' class='btn btn-info btn-rounded ' id='orderPage'>Order</button>" +
                    "<button type='button' class='btn btn-info btn-rounded ' id='orderHistoryPage'>Orders History</button>" +
                    "").appendTo($("#oneRegionMenu"));
                orderClicked();

                CustomerOrdersHistoryClicked();

            }
            else
            {
                $("<button type='button' class='btn btn-info btn-rounded ' id='ordersFromStorePage'>Orders from my stores</button>" +
                    "<button type='button' class='btn btn-info btn-rounded ' id='feedbacksPage'>Feedbacks</button>" +
                    "<button type='button' class='btn btn-info btn-rounded ' id='newStorePage'>Open new store</button>"
            ).appendTo($("#oneRegionMenu"));
                ManagerFeedbacksClicked();
                AddNewStoreClicked();
                OwnerOrdersHistoryClicked();
                // FeedbackClicked();
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
        stopIntervalsInOneRegionPage();
        $("#content").load("OrderTemplate.html",initializeOrderPage);

        // initializeOrderPage();
    })
}

function FeedbackClicked() {
    $("#FillFeedbackPage").click(function () {
        stopIntervalsInOneRegionPage();
        $("#content").load("FillFeedback.html", initializeFeedbackPage);

    })
}

function backClicked() {
    $("#back").click(function () {
        stopIntervalsInOneRegionPage();
        window.location.assign("Regions.html");
    })
}

function stopIntervalsInOneRegionPage() {
    clearInterval(ITEMS_IN_REGION_INTERVAL);
    clearInterval(STORES_IN_REGION_INTERVAL);

    clearInterval(OWNER_FEEDBACKS_DATA_INTERVAL);
    // clearInterval(OWNER_STORES_ORDERS_HISTORY_DATA_INTERVAL);
}