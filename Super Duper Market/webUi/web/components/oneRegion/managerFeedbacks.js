var FEEDBACKS_DATA_URL = buildUrlWithContextPath("feedbacks");

function triggerFeedbacksAjaxTimeInterval() {
    setInterval(ajaxFeedbacksData, 500);
}

function ajaxFeedbacksData() {
    $.ajax({
        url: FEEDBACKS_DATA_URL,
        success: function(feedbacksJson) {
            //Initialize feedbacks data
            setManagerFeedbackCards(feedbacksJson);
        }
    });
}

function setManagerFeedbackCards(feedbacksJson)
{
    $("#managerFeedbacks").empty();

    $.each(feedbacksJson || [], function (index, feedback) {
        $("#managerFeedbacks").add("feedbacksTemplate.html");

        $('<div class="card shadow p-3 m-2 bg-white rounded" id="oneFeedbackCard" style="width: 23rem">'+
            <!--        <img class="card-img-top user-image" alt="User image" src="common/images/cards/request_icon.svg">-->
            '<div class="card-body">'+
            '<h3 class="card-title " id="feedbacker">'+feedback.name+'</h3>'+

            <!--feedback data-->
            '<b class="font-weight-bold">Date: </b>'+
            '<span  class="col-1" id="id">'+feedback.date+'</span>'+
            '<br/>'+

            '<b class="font-weight-bold">Rate: </b>'+
            '<span  class="col-1" id="rate">'+feedback.rate+'</span>'+
            '<br/>'+

            '<b class="font-weight-bold">Feedback: </b>'+
            '<p id="feedbackText">'+feedback.feedbackText+'</p>'+

            '</div>'+
            '</div>').appendTo($("#managerFeedbacks"));

    });
}
