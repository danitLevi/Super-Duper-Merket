$(function () {
    $("form.loginForm").submit(function () {
        var parameters = $(this).serialize();

        $.ajax({

            data: parameters,
            url: this.action,
            timeout: 2000,
            error: function () {
                console.log("Unexpected Error");
            },
            success: function (resp) {
                // todo constants ?
                if (resp === "Login.html" || resp === "Regions.html" ) {
                    window.location.assign(resp);
                } else {
                    $(".error").text(resp);
                }
            }
        });
        return false
    })
});