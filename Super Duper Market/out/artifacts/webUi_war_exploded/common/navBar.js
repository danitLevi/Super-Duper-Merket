$(function() {
    handleChatClick();
}
);

function handleChatClick() {
$("#chatLink").click(stopIntervalsInChatPage);
}

function stopIntervalsInChatPage() {
    // intervals in regions page
    clearInterval(REGIONS_DATA_INTERVAL);
    clearInterval(TRANSACTIONS_DATA_INTERVAL);
    clearInterval(USERS_DATA_INTERVAL);
    clearInterval(BALANCE_DATA_INTERVAL);

    // intervals in one region page
    clearInterval(ITEMS_IN_REGION_INTERVAL);
    clearInterval(STORES_IN_REGION_INTERVAL);

    clearInterval(OWNER_FEEDBACKS_DATA_INTERVAL);

}