var TRANSACTIONS_TABLE_URL = buildUrlWithContextPath("transactionsData");

var TRANSACTIONS_DATA_INTERVAL;


$(function() {
    ajaxTransactionsData();
    triggerTransactionsAjaxTimeInterval();
});


function triggerTransactionsAjaxTimeInterval() {
    TRANSACTIONS_DATA_INTERVAL=setInterval(ajaxTransactionsData, 1000);
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
            "<td>"+myFormatter.format(transactionData.transactionAmount)+"₪"+"</td>"+
            "<td>"+myFormatter.format(transactionData.balanceBefore)+"₪"+"</td>"+
            "<td>"+myFormatter.format(transactionData.balanceAfter)+"₪"+"</td>"+
            "</tr>").appendTo($("#transactionsTableData"));
    });
}



