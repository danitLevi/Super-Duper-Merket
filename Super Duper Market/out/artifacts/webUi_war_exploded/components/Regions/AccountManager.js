var BALANCE_URL = buildUrlWithContextPath("balance");
var SESSION_URL = buildUrlWithContextPath("mySession");
var CHARGE_URL = buildUrlWithContextPath("charge");

$(function() {
    ajaxSetChangeVisibility();
    handleChargeSubmitting();
});

function ajaxSetChangeVisibility() {

    var parameters ;
    $.ajax({
        url: SESSION_URL,
        success: function (userType) {

            if(userType==="storeOwner" || userType===null)
            {
                $("#accountForm").hide();
            }
            showBalance();
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
                // todo : alert charged successfully (for only this user !!!)
                showBalance();

            }
        });

        return false;
    })
}

// function charge() {
//     $.ajax(
//         {
//             url:CHARGE_URL,
//             method:'POST',
//             data:{amount:$("#amountToCharge"),date:$("#date-input").val()},
//             success:function () {
//                 // todo : alert charged successfully (for only this user !!!)
//                 showBalance();
//
//             }
//         }
//     )
// }

function showBalance() {
    $.ajax(
        {
            url:BALANCE_URL,
            success:function (response) {
                $(".balance").text(response);
            }
        }
    )
}

// function triggerCharge() {
//     setTimeout(charge,300);
// }