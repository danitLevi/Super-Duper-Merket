const GET_ALERTS_URL = buildUrlWithContextPath("handleAlerts");

const SAVE_ALERT_TO_SHOW_URL =  buildUrlWithContextPath("saveAlertToShowLater");

$(function() { // onload...do

    triggerAlertsAjaxTimeInterval();

});

function triggerAlertsAjaxTimeInterval() {

    setInterval(ajaxAlerts, 1000);
}

function ajaxAlerts() {
    $.ajax({
        url: GET_ALERTS_URL,
        success: function(response) {
            if(response!="No alerts")
            {
                $('#modal').modal({keyboard: true });
                $('#modal').find('#msg').text(response);
                $('#modal').find('#modalTitle').text("Super Duper Market update");
            }
        }
    });
}
