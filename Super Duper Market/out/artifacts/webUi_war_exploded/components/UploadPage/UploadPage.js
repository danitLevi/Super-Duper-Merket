$(function() { // onload...do

    handleUploadSubmit();

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
                    window.location.assign(resp);

                    // window.$("#iChanged").text("!!!");

                    // $("#theModal").modal('show');

                } else {
                    $('#modal').modal({keyboard: true });
                    $('#modal').find('#msg').text(resp);
                    $('#modal').find('#modalTitle').text("Error");
                }
            }
        });
        return false
    })

}