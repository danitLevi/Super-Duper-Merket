var SALES_DATA_URL = buildUrlWithContextPath("salesInOrder");
var ITEM_DATA_URL = buildUrlWithContextPath("itemData");
var SAVE_USED_SALE_URL = buildUrlWithContextPath("saveUsedSale");
var STORE_NAME_URL = buildUrlWithContextPath("getStoreName");



function handleSalesWindow()
{
    showSales();
    handleContinueButtonInSalesWindow();
}

function showSales() {
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
        var usedSales=[];
        $.each(StoreNameToStoreSaleToAmountJson || [], function (storeId, SaleInOneStoreData) {
            var storeName=getStoreNameAjax(storeId) ;

            $('<div class="container storeContainer" >\n' +
                '        <h1 class="store" id="'+storeId+'">'+storeName+'</h1>\n' +
                '        <div class="h-divider">\n' +
                '            <div class="shadow"></div>\n' +
                '        </div>\n' +
                '    <br/>\n' +
                '    <div class="row mb-3 text-center" id="salesCards">').appendTo($("#salesData"));

            $.each(SaleInOneStoreData || [], function (index, sale) {

                var saleDetails=sale[0];
                var saleAmount=sale[1];
                var itemToBuyDetails=getItemDetails(saleDetails.itemIdToBuy);
                var p=itemToBuyDetails.purchaseCategory;
                $('        <div class="card w-25 m-3 shadow-sm rounded" id="card'+index+'">\n' +
                    '                <img class="card-img-top" src="common/resources/saleSmall.jpg" alt="sale">\n' +
                    '                <div class="card-body">\n' +
                    '<form id="saleForm">'+
                    '                <h4 class="card-title">'+saleDetails.name+'</h4>\n' +
                    '                <p class="card-text">\n' +
                    '                    <ul class="list-unstyled mt-3 mb-4">\n' +
                    '                        <li>\n' +
                    '                            <span>Buy </span>\n' +
                    '                            <span id="QuantityToBuy">'+myFormatter.format(saleDetails.quantityToBuy)+'</span>\n' +
                    '                            <span id="Unit">'+getUnitOrKg(itemToBuyDetails)+'</span>\n' +
                    '                            <span > of </span>\n' +
                    '                            <span id="ItemName">'+itemToBuyDetails.name+'</span>\n' +
                    '                        </li>\n' +

                    '                        <span>And get </span>\n' +
                                            getOffersOptions(saleDetails)+

                    '                    </li>\n' +
                    '                    </ul>\n' +
                    '                </p>\n' +
                    '                    <button type="submit" class="btn btn-lg btn-block btn-primary" id="useSaleBtn">Use this sale</button>\n' +
                    '</form>'+
                    '                </div>\n' +
                    '                <div class="card-footer">\n' +
                    '                    <span>Approval times to use: </span> <span id="approvedAmountSpan">'+saleAmount+'</span>\n' +
                    '                </div>\n' +
                    '        </div>').appendTo($("#salesCards"));

                // $(this).css("background-color","blue");
                        // let currCard = $(this).closest(".card");
                        let currCardId="#card"+index+""
                        let currStoreId=storeId;
                        let currCard =$(currCardId);
                        let currForm=$(currCard).find("#saleForm");
                        $(currForm).submit({"saleDetails":saleDetails ,"storeId":currStoreId},function handleUseSaleClick(event) {
                            let currSaleDetails = event.data.saleDetails;
                            let currStoreId=event.data.storeId
                            let currCard = $(this).closest(".card");
                            // let storeId=$(this).closest(".store").attr("id");
                            handleApprovalAmount(currCard);
                            saveUsedSales(currCard,saleDetails,storeId);

                            return false;
                        });

            });

            $('</div>' +
                '</div>').appendTo($("#salesData"));
        });

    }
}

function getItemDetails(itemId) {
// var returnVal=null;
    let returnVal=null;
    $.ajax({
        url:ITEM_DATA_URL,
        data:{"itemId":itemId},
        async: false, // pay attention async so function wait for response from servlet
        success:function (response) {
            returnVal= response;
        }
    });
    return returnVal;
}

function getUnitOrKg(itemDetails) {
if(itemDetails.purchaseCategory=='Quantity')
    return ' unit'
else
    return ' KG'
}

function getOffersOptions(saleDetails) {
   let offerOption=saleDetails.optionToGet;
    let str="";

    if(offerOption=='ONE-OF')
    {
        str='<span ><b>One</b> of the following:</span>'
        +'<div class="form-group">\n' +
            '  <select class="form-control" id="offers" required>' +
            '     <option value="" selected disabled>Please select an option to get</option>';


        $.each(saleDetails.offersToGet || [], function (index, offerDetails)
        {
            let itemInOfferDetails=getItemDetails(offerDetails.itemId);
            str+= '<option  id="'+index+'">'+getOfferStr(offerDetails,itemInOfferDetails)+'</option>\n'
        });

        str+=   '  </select>\n' +
            '</div>';

    }
    else
    {
        if (offerOption=='ALL-OR-NOTHING' ) {
            str = '<span ><b>All</b> of the following:</span>';
        }
            str+='<ul class="list-unstyled mt-3 mb-4" id="offers" value="'+saleDetails.offersToGet+'">';

            $.each(saleDetails.offersToGet || [], function (index, offerDetails)
            {
              let itemInOfferDetails=getItemDetails(offerDetails.itemId);
              str+='<li>'+getOfferStr(offerDetails,itemInOfferDetails)+'</li>';
            });

            str+='</ul>';

    }

    return str;
}

function getOfferStr(offerDetails,itemInOfferDetails) {
    return('<span>'+ offerDetails.quantity +' ' +getUnitOrKg(itemInOfferDetails) +' of '+itemInOfferDetails.name+'-'
    +offerDetails.forAdditional+ '₪ per '+getUnitOrKg(itemInOfferDetails) +'</span> '
)

}

function handleApprovalAmount(currCard) {
    let approvedAmount=$(currCard).find("span[id='approvedAmountSpan']");

    let newApprovedAmount=Number(approvedAmount.text()) -1;
    approvedAmount.text(newApprovedAmount);

    if(newApprovedAmount==0)
    {

        let cards=$(currCard).closest("#salesCards");
        if(cards.children().length == 1 )
        {
            let storeContainer=$(currCard).closest(".storeContainer")
            storeContainer.remove();

            let salesData=$(currCard).closest("#salesData");
            if(salesData.children.length==0)
            {
                showNoSales();
            }
        }
        $(currCard).remove();
    }
}

function showNoSales() {
    $(
        '<h1>No sales</h1>\n'
    ).appendTo($("#salesData"));
}

function saveUsedSales(currCard,saleDetails,storeId) {

    // var storeElm=$(this).closest(".store");
    // var storeId=storeElm.id;
    // var oneOfferToSave;
    var offerOption=saleDetails.optionToGet;
    var offers=saleDetails.offersToGet;
    if(offerOption=='ONE-OF')
    {
        // var oneOfferToSave=$(currCard).find("#offers :selected").val();
        // var oneOfferToSave=$(currCard).find("#offers :selected").value();
        let oneOfferToSaveIndex=$(currCard).find("#offers :selected").attr("id");
        let oneOfferToSave=offers[oneOfferToSaveIndex];
        // var oneOfferToSave=$('#offers').find(":selected").val(); // todo : value ?
        saveOffer(oneOfferToSave,storeId);
    }
    else
    {
        // var offersToSave=$('#offers').val();
        // var offersToSave=$(currCard).find('#offers').attr("value");
        $.each(offers || [], function (index, oneOfferToSaveObj)
        {
            saveOffer(oneOfferToSaveObj,storeId);
        })
    }


}

function saveOffer(offerToSave,storeId) {

    $.ajax({
        url:SAVE_USED_SALE_URL,
        method:'POST',
        data:{"storeId":storeId,"offerToSave":JSON.stringify(offerToSave)}
    });

}

function getStoreNameAjax(storeId) {
    var storeName;
    $.ajax({
        url:STORE_NAME_URL,
        async:false,
        data:{"storeId":storeId},
        success:function (storeNameResponse) {
            storeName=storeNameResponse;
        }
    });
    return storeName;
}



function handleContinueButtonInSalesWindow() {

    $("#continueButton").click(function () {
        // $("#content").load("orderTemplates/orderSummary.html",handleOrderSummaryWindow);
        $("#content").load("orderTemplates/orderSummary.html");

    })
}
