package DtoObjects;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

public class StoreOrderDto {

//    private final String strDate;
    private final double itemsTotalAmount;//
    private final double itemsTotalPrice;//
    private final double deliveryTotalPrice;//
    private final double orderTotalPrice;//todo: check if needed
//    private final boolean isDynamicOrder;//todo: check if needed
//    private final int orderId;//
    Set<ItemInStoreOrderDto> itemsInStoreOrderDetails;
    private final double distanceFromCustomer;//todo: check if needed
//    private final String customerName;//
//    private final int xCoordinate;//todo: check if needed
//    private final int yCoordinate;//todo: check if needed

    public StoreOrderDto(
//            Date date,
            double itemsTotalAmount, double itemsTotalPrice,
                         double deliveryTotalPrice, double orderTotalPrice,
//                         boolean isDynamicOrder,
//                         int orderId,
                         Set<ItemInStoreOrderDto> itemsInStoreOrderDetails,
                         double distanceFromCustomer
//            , String customerName

//                         ,int xCoordinate,
//                         int yCoordinate
    )
    {

//        String pattern = "dd/MM/yyyy";
//        DateFormat df = new SimpleDateFormat(pattern);
//        this.strDate = df.format(date);

        this.itemsTotalAmount = itemsTotalAmount;
        this.itemsTotalPrice = itemsTotalPrice;
        this.deliveryTotalPrice = deliveryTotalPrice;
        this.orderTotalPrice = orderTotalPrice;
//        this.isDynamicOrder = isDynamicOrder;
//        this.orderId = orderId;
        this.itemsInStoreOrderDetails = itemsInStoreOrderDetails;
        this.distanceFromCustomer = distanceFromCustomer;
//        this.customerName = customerName;
//        this.xCoordinate = xCoordinate;
//        this.yCoordinate = yCoordinate;
    }

//    public String getCustomerName() {
//        return customerName;
//    }

//    public int getxCoordinate() {
//        return xCoordinate;
//    }
//
//    public int getyCoordinate() {
//        return yCoordinate;
//    }

//    public String getStrDate() {
//        return strDate;
//    }

    public double getItemsTotalAmount() {
        return itemsTotalAmount;
    }

    public double getItemsTotalPrice() {
        return itemsTotalPrice;
    }

    public double getDeliveryTotalPrice() {
        return deliveryTotalPrice;
    }

    public double getOrderTotalPrice() {
        return orderTotalPrice;
    }

//    public boolean isDynamicOrder() {
//        return isDynamicOrder;
//    }

//    public int getOrderId() {
//        return orderId;
//    }

    public Set<ItemInStoreOrderDto> getItemsInStoreOrderDetails() {
        return itemsInStoreOrderDetails;
    }

    public double getDistanceFromCustomer() {
        return distanceFromCustomer;
    }


}
