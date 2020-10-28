var FEEDBACKS_DATA_URL = buildUrlWithContextPath("feedbacks");

function triggerFeedbacksAjaxTimeInterval() {
    setInterval(ajaxFeedbacksData, 500);
}

function ajaxFeedbacksData() {
    $.ajax({
        url: FEEDBACKS_DATA_URL,
        success: function(feedbacksJson) {
            if(feedbacksJson.length === 0)
            {
                $(".noFeedbacks").text("No feedbacks found");
            }
            else {
                //Initialize feedbacks data
                setManagerFeedbackCards(feedbacksJson);
            }
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
            '<h4 class="card-title font-weight-bold" id="storeName">Store: '+feedback.storeName+'</h4>'+
            '<h4 class="card-title" style="text-decoration: underline" id="feedbacker">Customer: '+feedback.name+'</h4>'+

            <!--feedback data-->
            '<b class="font-weight-bold">Date: </b>'+
            '<span  class="col-1" id="id">'+feedback.strDate+'</span>'+
            '<br/>'+

            '<b class="font-weight-bold">Rate: </b>'+
            '<span  class="col-1" id="rate">'+feedback.rate+'</span>'+
            '<br/>'+

            '<b class="font-weight-bold">Feedback description: </b>'+
            '<p id="feedbackText">'+feedback.feedbackText+'</p>'+

            '</div>'+
            '</div>').appendTo($("#managerFeedbacks"));

    });
}
