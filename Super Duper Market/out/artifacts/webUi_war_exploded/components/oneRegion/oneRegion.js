var SESSION_URL = buildUrlWithContextPath("mySession");
var REGION_NAME_URL = buildUrlWithContextPath("getRegionName");

$(function () {
    setRegionName();
    ajaxSetMenuByUserType();

// <<<<<<< HEAD
  itemsClicked();
  StoresClicked();
  backClicked();

// =======
//     itemsClicked();
//     StoresClicked();
// >>>>>>> develop
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
        $("#content").load("OrdersHistoryForOwner.html",initializeOrdersHistoryForOwner);

    })
}

function CustomerOrdersHistoryClicked() {
    $("#orderHistoryPage").click(function () {
        $("#content").load("OrdersHistoryForCustomer.html",handleCustomerOrdersHistoryWindow);

    })
}

function ajaxSetMenuByUserType() {
    $.ajax({
        url: SESSION_URL,
        success: function (userType) {
            if(userType==="Customer")
            {
                $("<button type='button' class='btn btn-info btn-rounded' id='orderPage'>Order</button>" +
                    "<button type='button' class='btn btn-info btn-rounded' id='orderHistoryPage'>Orders History</button>" +
                    "").appendTo($("#oneRegionMenu"));
                orderClicked();

                CustomerOrdersHistoryClicked();

            }
            else
            {
                $("<button type='button' class='btn btn-info btn-rounded' id='ordersFromStorePage'>Orders from my stores</button>" +
                    "<button type='button' class='btn btn-info btn-rounded' id='feedbacksPage'>Feedbacks</button>" +
                    "<button type='button' class='btn btn-info btn-rounded' id='newStorePage'>Open new store</button>"
            ).appendTo($("#oneRegionMenu"));
                ManagerFeedbacksClicked();
                AddNewStoreClicked();
                OwnerOrdersHistoryClicked();
                FeedbackClicked();
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
        $("#content").load("OrderTemplate.html",initializeOrderPage);

        // initializeOrderPage();
    })
}

function backClicked() {
    $("#back").click(function () {
        window.location.assign("Regions.html");
    })
}

//TODO: DELETE ALONA
function FeedbackClicked() {
    $("#FillFeedbackPage").click(function () {
        $("#content").load("FillFeedback.html", initializeFeedbackPage);

    })
}