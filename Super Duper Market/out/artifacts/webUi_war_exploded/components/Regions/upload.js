var SESSION_URL = buildUrlWithContextPath("mySession");
var UPLOAD_URL = buildUrlWithContextPath("upload");

$(function() {
    ajaxUserType();

});

function ajaxUserType() {
    $.ajax({
        url: SESSION_URL,
        success: function (userType) {
            if(userType==="Customer" || userType===null)
            {
                $("#uploadbtn").hide();
            }
        }
    });
}

$(function() { // onload...do
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
                if (resp === "Regions.html") {
                    window.location.assign(resp);
                    // window.$("#iChanged").text("!!!");

                    // $("#theModal").modal('show');

                } else {
                    //todo alert
                    // $(".error").text(resp);
                }
            }
        });
        return false
    })
});

//
// function ajaxPostFile(formData) {
//     $.ajax({
//         method:'POST',
//         data: formData,
//         url: this.action,
//         processData: false, // Don't process the files
//         contentType: false, // Set content type to false as jQuery will tell the server its a query string request
//         timeout: 4000,
//         success: function (resp) {
//             if (resp === "Regions.html") {
//                 window.location.assign(resp);
//             } else {
//                 //todo alert
//                // $(".error").text(resp);
//             }
//         }
//     });
// }

