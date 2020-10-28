// const SAVE_ALERT_TO_SHOW_URL =  buildUrlWithContextPath("saveAlertToShowLater");

$(function() { // onload...do

    handleUploadSubmit();
    //displaySuccessMessage();
    // modalCloseBtnOnClick();
    backBtnOnClick();
});

function handleUploadSubmit() {

    $("#uploadForm").submit(function () {
        var file = this[0].files[0];
        var formData = new FormData();
        formData.append("dataFile", file);
        //ajaxPostFile(formData);

        $.ajax({
            method:'POST',
            data: formData,
            url: this.action,
            processData: false, // Don't process the files
            contentType: false, // Set content type to false as jQuery will tell the server its a query string request
            timeout: 4000,
            success: function (resp) {

                if(resp.nextPage)
                {

                    window.location.assign(resp.nextPage);

                    triggerUploadSuccessAlertMsgToShow(resp.newRegionName);
                } else {
                    $('#modal').modal({keyboard: true });
                    $('#modal').find('#msg').text(resp.errorMsg);
                    $('#modal').find('#modalTitle').text("Error");
                }
            },
            error:function () {
              console.log("not ok");
            }

        });
        return false
    })
}

function triggerUploadSuccessAlertMsgToShow(newRegionName) {

    $.ajax({
        url:SAVE_ALERT_TO_SHOW_URL,
        method:'POST',
        data:{alertType:"upload",newRegionNameData:newRegionName}
    });
}

// function displaySuccessMessage() {
//     var message = "New reagion added to the system"
//     $(".success-modal-body").text(message);
//     $("#success-modal").modal("show");
// }

// function modalCloseBtnOnClick(){
//         $(".closePage").click(function() {
//         window.location.assign("Regions.html");
//     })
// }

function backBtnOnClick(){
    $("#backBtn").click(function() {
        window.location.assign("Regions.html");
    })
}