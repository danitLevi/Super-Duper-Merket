var SALES_DATA_URL = buildUrlWithContextPath("salesInOrder");

function handleSalesWindow()
{
    $.ajax({
        url: SALES_DATA_URL,
        success: function(StoreIdToStoreSaleToAmountJson)
        {
            setSalesData(StoreIdToStoreSaleToAmountJson);
        }
    });
}


function setSalesData(StoreNameToStoreSaleToAmountJson) {

    if(StoreNameToStoreSaleToAmountJson =="No Sales")
    {
        // todo : add no sales img
        $(
            '<h1>No sales</h1>\n'
         ).appendTo($("#salesData"));
    }

    else
    {

        $.each(StoreNameToStoreSaleToAmountJson || [], function (index, SalesInOneStoreData) {

            $('<div class="container">\n' +
                '<h1>'+index+'</h1>\n' +
                '  <div class="h-divider">\n' +
                ' <div class="shadow"></div>\n' +
                '</div>\n' +
                '<div class="card-columns mb-3 text-center" id="currStoreSales">'
             +'</div>\n' +
                '</div>').appendTo($("#salesData"));

            // $.each(SalesInOneStoreData || [], function (index, oneSaleDetailsInOneStore) {
            //
            //
            //     $().appendTo($("#salesData"));
            //
            // });
        });

    }
}

function getItemDetails() {

}