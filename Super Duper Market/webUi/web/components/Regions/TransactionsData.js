var TRANSACTIONS_TABLE_URL = buildUrlWithContextPath("transactionsData");

var TRANSACTIONS_DATA_INTERVAL;


$(function() {
    triggerTransactionsAjaxTimeInterval();
});


function triggerTransactionsAjaxTimeInterval() {

    TRANSACTIONS_DATA_INTERVAL=setInterval(ajaxTransactionsData, 500);
}

function ajaxTransactionsData() {
    $.ajax({
        url: TRANSACTIONS_TABLE_URL,
        success: function(TransactionsJson) {
            refreshTransactionsTable(TransactionsJson);
        }
    });
}

function refreshTransactionsTable(TransactionsJson) {
    //clear all current users
    $("#transactionsTableData").empty();

    // rebuild the list of users: scan all users and add them to the list of users
    $.each(TransactionsJson || [], function(index, transactionData) {
        //create a new <option> tag with a value in it and
        //appeand it to the #userslist (div with id=userslist) element
        $("<tr><td>" +
            transactionData.type  +"</td>" +
            "<td>"+transactionData.date+"</td>"+
            "<td>"+transactionData.transactionAmount+"</td>"+
            "<td>"+transactionData.balanceBefore+"</td>"+
            "<td>"+transactionData.balanceAfter+"</td>"+
            "</tr>").appendTo($("#transactionsTableData"));
    });
}



