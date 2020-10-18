$(function() { // onload...do

    handleUploadSubmit();
    //displaySuccessMessage();
    modalOnClick();
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
                if (resp === "Regions.html") {
                    displaySuccessMessage();
                    //window.location.assign(resp);

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
}

function displaySuccessMessage() {
    var message = "New reagion added to the system"
    $(".success-modal-body").text(message);
    $("#success-modal").modal("show");
}

function modalOnClick(){
        $(".close").click(function() {
        window.location.assign("Regions.html");
    })
}