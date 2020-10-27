function getUnitOrKgStr(purchaseCategory) {
    if(purchaseCategory=='Quantity')
        return ' unit'
    else
        return ' KG'
}

function getIsFromSaleStr(isFromSale) {
    if(isFromSale)
        return "Yes";
    return "No";
}