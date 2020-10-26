var BALANCE_URL = buildUrlWithContextPath("balance");
var SESSION_URL = buildUrlWithContextPath("mySession");
var CHARGE_URL = buildUrlWithContextPath("charge");

var BALANCE_DATA_INTERVAL;


$(function() {
    ajaxSetChargeVisibility();
    handleChargeSubmitting();
});

function ajaxSetChargeVisibility() {

    $.ajax({
        url: SESSION_URL,
        success: function (userType) {

            if(userType==="storeOwner" || userType===null)
            {
                $("#accountForm").hide();
            }
            triggerShowBalanceInterval();
        }
    });
}

function handleChargeSubmitting() {
    $("#accountForm").submit(function () {
        $.ajax({
            url:CHARGE_URL,
            method:'POST',
            data:$(this).serialize(),
            success:function () {
                showBalance();

            }
        });

        return false;
    })
}

function triggerShowBalanceInterval() {
    BALANCE_DATA_INTERVAL=setInterval(showBalance, 1000);
}

function showBalance() {
    $.ajax(
        {
            url:BALANCE_URL,
            success:function (response) {
                $("#balance").text(response);
            }
        }
    )
}
