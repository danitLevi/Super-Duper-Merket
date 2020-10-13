// var ORDER_URL = buildUrlWithContextPath("order");
var ITEMS_IN_SYSTEM_URL = buildUrlWithContextPath("items");
var ITEMS_IN_STORE_URL = buildUrlWithContextPath("itemsFromStoreToOrder");
var STORES_IN_SYSTEM_URL = buildUrlWithContextPath("storesInRegion");
var DELIVERY_COST_URL = buildUrlWithContextPath("getDeliveryCost");
var SAVE_ORDER_IN_SESSION_URL = buildUrlWithContextPath("saveOrderInputInSession");



function initializeOrderPage()
{
    // at first computed is selected by default
    onDynamicOrder();

    handleOrderTypeSelection();
    handleDeliveryCostOnChange();
    fillStoreComboBox();
    listenTosubmitOrder();
}



function setItemsToOrderTable() {

}

function handleOrderTypeSelection()
{
    $("input[type=radio]").on("change", function(){
        if ($("#computed").is(":checked")) {
            onDynamicOrder();
        } else {
            onStaticOrder();
        }
    })
}

function onDynamicOrder() {
    setItemsFromSystemToOrder();
    $("#staticOrderAddition").hide();
    // $("#deliveryCostVal").text("NO VALUE");
}

function onStaticOrder() {
    $("#staticOrderAddition").show();
    setItemsFromStoreToOrder();
}

function fillStoreComboBox() {
    $.ajax({
        url: STORES_IN_SYSTEM_URL,
        success: function(StoresJson) {
            $.each(StoresJson || [], function(index,storeData)
            {
                var storeRow="<option value='"+storeData.id+"'>"+storeData.name+": id= "+storeData.id+" Location= ("+
                    storeData.xCoordinate+","+storeData.yCoordinate+")</option>";

                $(storeRow).appendTo("#storesOptions");
            })
        }
//                currStoreDetails.getName()+
//                        ": id="+currStoreDetails.getId()
//                        +", location=("+currStoreDetails.getxCoordinate()+","+currStoreDetails.getyCoordinate()+")"
    });
}



function setItemsFromSystemToOrder() {
    $.ajax({
        url: ITEMS_IN_SYSTEM_URL,
        success: function(itemsJson) {
            setItemsTable(itemsJson,false);
        }
    });
}

function setItemsFromStoreToOrder() {
    var selectedStoreId=$("#storesOptions").val();
    $.ajax({
        url: ITEMS_IN_STORE_URL,
        data:{storeId:selectedStoreId},
        success: function(itemsJson) {
            setItemsTable(itemsJson,true);
        }
    });
}


function setItemsTable(itemsJson,isStatic) {
    //clear all current items
    $("#itemsToOrderTableData").empty();

    $.each(itemsJson || [], function (index, itemData) {

        var currRowData = "<tr><td>" +
            itemData.id + "</td>" +
            "<td>" + itemData.name + "</td>" +
            "<td>" + itemData.purchaseCategory + "</td>";
        if (isStatic)
        {
            currRowData += '<td>' + itemData.price + '</td>';
            $("#priceColumn").css("display", "block");
        }
        else {
            $("#priceColumn").css("display", "none");
        }

        if (itemData.purchaseCategory.toString() == 'Quantity') {
            currRowData += '<td> <input type="number" id="'+itemData.id+' " min="0" step="1" placeholder="Insert an integer number" class="form-control itemToOrderAmount"> </td>';
        } else {
            currRowData += '<td> <input type="number" id="'+itemData.id+'" min="0" step="0.01" placeholder="Insert a float number" class="form-control itemToOrderAmount"> </td>';
        }
        currRowData += "</tr>";

        $(currRowData).appendTo($("#itemsToOrderTableData"));
    });
}

    function handleDeliveryCostOnChange() {
        $("#xCoordinate").change(validateAndSetDeliveryCost);
        $("#yCoordinate").change(validateAndSetDeliveryCost);
        $("#storesOptions").change(handleSelectedStoreChanged);
    }

    function handleSelectedStoreChanged() {
        validateAndSetDeliveryCost();
        setItemsFromStoreToOrder();
    }




    //todo: add validations (not store location and 1-50) !!!!!!!!
    // todo:check if location is invalid (on change and not only in saving ) add alert and set delivery to No Value
    function validateAndSetDeliveryCost() {

    //todo : fix !always undifined
        if($("#xCoordinate").val()!="" && $("#yCoordinate").val()!="" )
        {
            setDeliveryCostFromValidCoordinates();
        }
        else
        {
            $("#deliveryCostVal").text("NO VALUE");
        }
    }
    
    function setDeliveryCostFromValidCoordinates() {
        var selectedStoreId=$("#storesOptions").val();
        var x=$("#xCoordinate").textContent;
        var y=$("#yCoordinate").textContent;

        $.ajax({
            url: DELIVERY_COST_URL,
            data:{"storeId":selectedStoreId,"xCoordinate":x,"yCoordinate":y},
            success: function (response) {
                $("#deliveryCostVal").text(response);
            },
            error:function () {
                $("#deliveryCostVal").text("NO VALUE");
            }
        });
    }

    function getItemsToOrderJson()
    {
        var quantityToOrderInputs=$(".itemToOrderAmount");
        var itemsToOrderJson={items:[]};
        var counter=0;
        $.each(quantityToOrderInputs || [], function (index, currInput)
        {
            var itemId=$(currInput).attr('id');
            var quantity=$(currInput).val();
            if(quantity!="0" && quantity!=undefined && quantity!="")
            {
                itemsToOrderJson.items[counter]={"itemId":itemId ,"quantity":quantity};

                counter++;
                // itemsToOrderJson.items.push({itemId:quantity});
                // $.extend(itemsToOrderJson.items,{"itemId":itemId ,"quantity":quantity});
            }

        });

        return itemsToOrderJson;
    }


// todo
function listenTosubmitOrder() {
    $(".orderForm").submit(function ()
        {
            console.log("ok");
            var itemsJson =getItemsToOrderJson();
            // var itemsJson_to_send = $.serialize(itemsJson);

            $.ajax({
                url:SAVE_ORDER_IN_SESSION_URL,
                method:'POST',
                // contentType: 'application/json',
                // dataType: 'json',
            // data:{"itemsToOrder":itemsJson},
                data:JSON.stringify({items:itemsJson.items}),
                // contentType: false,
                // processData: false,
                success:function () {
                    console.log("ok");
                },
                error:
                function () {
                    console.log("not ok");
                }

                });
            return false;
        }

    )


}


