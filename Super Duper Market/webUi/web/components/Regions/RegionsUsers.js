var USER_LIST_URL = buildUrlWithContextPath("userslist");
var REMOVE_USER_URL = buildUrlWithContextPath("removeUser");


function refreshUsersList(users) {
    //clear all current users
    $("#userslist").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(users || [], function(index, userDetails) {
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $('<li>' + userDetails.name +" ("+userDetails.role+")"+'</li>').appendTo($("#userslist"));
    });
}

function ajaxUsersList() {
    $.ajax({
        url: USER_LIST_URL,
        success: function(users) {
            refreshUsersList(users);
            triggerAjaxUsers();
        }
    });
}

$(function() {
    triggerAjaxUsers();
});

// function triggerAjaxUserList() {
//     setTimeout(refreshUsersList, refreshRate);
// }

function triggerAjaxUsers() {
    setTimeout(ajaxUsersList, 1000);
}




