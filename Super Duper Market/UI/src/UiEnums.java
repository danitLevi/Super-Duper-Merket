enum MenuValues
{
    READ_XML,
    SHOW_STORES_DETAILS,
    SHOW_ITEMS,
    ORDER,
    SHOW_HISTORY,
    UPDATE_ITEMS_DATA,
    EXPORT_ORDER_HISTORY_TO_FILE,
    EXIT;

    public static int numOfEnumsVal= MenuValues.values().length;
}

enum UpdateItemsDataMenuValues
{
    DELETE_ITEM,
    ADD_ITEM,
    UPDATE_ITEM_PRICE,
    BACK,
    EXIT;
    public static int numOfEnumsVal= UpdateItemsDataMenuValues.values().length;
}

enum XmlMainMenuValues
{
    READ_XML,
    EXIT;
    public static int numOfEnumsVal= XmlMainMenuValues.values().length;
}

enum OrderOptionsMenuValues
{
    STATIC,
    DYNAMIC,
    BACK,
    EXIT;
    public static int numOfEnumsVal= OrderOptionsMenuValues.values().length;
}

enum ImportOrderHostoryMenuValues
{
    READ_NEW_XML,
    READ_ORDER_HISTORY_FILE,
    COUNTINUE,
    EXIT;
    public static int numOfEnumsVal= ImportOrderHostoryMenuValues.values().length;
}

enum XmlRead
{
    FIRST_FILE,
    NEW_FILE_BEFORE_IMPORT,
    NEW_FILE
}