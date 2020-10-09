var BALANCE_URL = buildUrlWithContextPath("balance");


$(function() {
    ajaxSetChargeVisibility();
    charge();
});

function ajaxSetChargeVisibility() {
    $.ajax({
        url: SESSION_URL,
        success: function (userType) {
            if(userType==="storeOwner" || userType===null)
            {
                $("#accountForm").hide();
            }
            else
            {
                showBalance();
            }
        }
    });
}

function charge() {
    $("#accountForm").submit(function () {
        $.ajax({
            url: $(this).action,
            data:$(this).serialize()
        });
        return false
    })
}

function showBalance() {
    $.ajax(
        {
            url:BALANCE_URL,
            data:{amount:$("#amountToCharge")},
            success:function (response) {
                $(".balance").text(response)
            }
        }
    )
}