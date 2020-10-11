var SESSION_URL = buildUrlWithContextPath("mySession");
var REGION_NAME_URL = buildUrlWithContextPath("getRegionName");

$(function () {
    setRegionName();
  ajaxSetMenuByUserType();

  itemsClicked();
    StoresClicked()
})

function itemsClicked() {
    $("#itemsPage").click(function () {
        $("#content").load("ItemsTemplate.html");
        triggerItemsAjaxTimeInterval();
    })
}

function ajaxSetMenuByUserType() {
    $.ajax({
        url: SESSION_URL,
        success: function (userType) {
            if(userType==="Customer")
            {
                $("<button type='button' class='btn1 btn1-pink btn-rounded' id='itemsPage'>Order</button>" +
                    "<button type='button' class='btn1 btn1-pink btn-rounded' id='itemsPage'>OrdersHistory</button>" +
                    "").appendTo($("#oneRegionMenu"));
            }
            else
            {
                $("<button type='button' class='btn1 btn1-pink btn-rounded' id='itemsPage'>Orders from my stores</button>" +
                    "<button type='button' class='btn1 btn1-pink btn-rounded' id='itemsPage'>Feedbacks</button>" +
                    "<button type='button' class='btn1 btn1-pink btn-rounded' id='itemsPage'>Open new store</button>"
                    ).appendTo($("#oneRegionMenu"));
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



function StoresClicked() {
    $("#itemsPage").click(function () {
        $("#storesPage").load("storesTemplate.html");
        // triggerStoresAjaxTimeInterval(); //todo
    })
}

