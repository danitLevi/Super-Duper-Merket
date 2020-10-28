var ORDER_STORES_URL =  buildUrlWithContextPath("getStoresInOrder");
var SAVE_FEEDBACK_URL =  buildUrlWithContextPath("saveFeedback");


function initializeFeedbackPage() {
    ajaxOrderStoreOptionsData();
    handleSubmit();
     handleFinish();
    handleRatingSelection();
}

//Initialize stores options
function ajaxOrderStoreOptionsData() {
    $.ajax({
        url: ORDER_STORES_URL,
        success: function(storesInOrderJson) {
                initializeOrderStoresOptions(storesInOrderJson);
        }
    });
}

function initializeOrderStoresOptions(storesJson) {
    $.each(storesJson || [], function(index, store) {
        $("<option value='"+store.id+"'>"+store.name + " (id=" + store.id+")</option>").appendTo($("#storesFromOrder"));
    });
}

//Save feedback
function handleSubmit() {
    $("#feedbackForm").submit(function () {
        let currStoreId = $("#storesFromOrder").val(); //Get selected store id
        let rate = $("#count-existing").text(); //Get rate
        let review = $("#review").val(); //Get text review
        let feedbackData={store: currStoreId, rate: rate, review: review};
        $.ajax({
            url:SAVE_FEEDBACK_URL,
            method:'POST',
            data:feedbackData,
            success:function () {
                resetPageData();
                removeStoreFromSelection();
                $("#FeedbackMsg").text(" Feedback for store added successfully!");
                triggerFeedbackAlertMsgToShow(feedbackData);
            }
        });
        return false;
    });
}




//Remove reviewed store from list
function removeStoreFromSelection() {
    var selectedStore = $('#storesFromOrder :selected');
    selectedStore.remove();
    if($('#storesFromOrder option').length==1)
    {
        $("#FeedbackContent").empty();
        $('<h2>You gave feedback to all possible stores</h2>' +
            '<h4>No More Stores to give feedback for. </h4>').appendTo($("#FeedbackContent"));

    }
}

function  resetPageData() {
    $("#count-existing").text("No rate");
    $("#feedbackForm")[0].reset();

}

function triggerFeedbackAlertMsgToShow(feedbackData) {

    $.extend(feedbackData,{alertType:"feedback"});
    $.ajax({
        url:SAVE_ALERT_TO_SHOW_URL,
        method:'POST',
        data:feedbackData,
        // success:function () {
        //     //Remove reviewed store from list
        //     // var itemSelectorOption = $('#storesFromOrder :selected');
        //     // itemSelectorOption.remove();
        //     // $("#FeedbackMsg").text(" Feedback added successfully!");
        //
        // }
    });
}

//Empty page if customer doesnt want to give feedback
function handleFinish() {
    $("#exitWindow").click(function(){
        $("#content").empty();
    });
}

function handleRatingSelection()
{
    $("input[type=radio]").on("change", function(){
        if ($("#star1").is(":checked")) {
            $("#count-existing").text("1");
        }
        else if($("#star2").is(":checked")) {
            $("#count-existing").text("2");
        }
        else if($("#star3").is(":checked")) {
            $("#count-existing").text("3");
        }
        else if($("#star4").is(":checked")) {
            $("#count-existing").text("4");
        }
        else if($("#star5").is(":checked")) {
            $("#count-existing").text("5");
        }
    })
}