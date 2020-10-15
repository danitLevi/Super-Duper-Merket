var ORDER_STORES_URL =  buildUrlWithContextPath("storesInOrder");
var SAVE_FEEDBACK_URL =  buildUrlWithContextPath("saveFeedback");


function initializeFeedbackPage() {
    ajaxOrderStoreOptionsData();
    handleSubmit();
    handleSkip();
    handleRatingSelection();
}

//Initialize stores options
function ajaxOrderStoreOptionsData() {
    $.ajax({
        url: ORDER_STORES_URL,
        success: function(storesJson) {
            if(storesJson.length === 0) {
                $(".noOrdersInStore").text("No orders found");
            }
            else {
                initializeOrderStoresOptions(storesJson);
            }
        }
    });
}

function initializeOrderStoresOptions(storesJson) {
    $("#ownerStores").empty();

    $.each(storesJson || [], function(index, store) {
        $("<option>"+store.id + ' ' + store.name+"</option>").appendTo($("#ownerStores"));
    });
}

//Save feedback
function handleSubmit() {
    $("#feedbackForm").submit(function () {
        var store = $("#storeFromOrder").val(); //Get selected store
        var storeId= store.substr(0, store.indexOf(' ')); //Get selected store id
        var rate = $("#count-existing").text(); //Get rate
        var review = $("#review").val(); //Get text review

        $.ajax({
            url:SAVE_FEEDBACK_URL,
            method:'POST',
            data:{store: storeId, rate: rate, review: review},
            success:function () {
                //Remove reviewed store from list
                var itemSelectorOption = $('#storeFromOrder option:selected');
                itemSelectorOption.remove();
                $("#FeedbackMsg").text(" Feedback added successfully!");

            }
        });
        return false;
    })
}

//Empty page if customer doesnt want to give feedback
function handleSkip() {
    $("#skipFeedback").click(function(){
        $("FeedbackContent").empty();
    });
}

function handleRatingSelection()
{
    $("input[type=radio]").on("change", function(){
        if ($("#1").is(":checked")) {
            $("#count-existing").text("1");
        }
        else if($("#2").is(":checked")) {
            $("#count-existing").text("2");
        }
        else if($("#3").is(":checked")) {
            $("#count-existing").text("3");
        }
        else if($("#4").is(":checked")) {
            $("#count-existing").text("4");
        }
        else if($("#5").is(":checked")) {
            $("#count-existing").text("5");
        }
    })
}