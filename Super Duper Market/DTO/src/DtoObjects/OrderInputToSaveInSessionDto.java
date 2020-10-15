package DtoObjects;

import java.util.Date;
import java.util.Map;

public class OrderInputToSaveInSessionDto {

    public final int NO_VALUE=-1;

    private Map<Integer, Map<Integer, Double>> orderMinimalPriceBag;
//    private int customerId;
//    private String customerDetails;
    private Date date; //local date ?
    private boolean isDynamicOrder;

    private int storeId;
    private int xCoordinate;
    private int yCoordinate;



    public OrderInputToSaveInSessionDto(Map<Integer, Map<Integer, Double>> orderMinimalPriceBag, Date date, boolean isDynamicOrder, int storeId, int xCoordinate, int yCoordinate) {
        this.orderMinimalPriceBag = orderMinimalPriceBag;
        this.date = date;
        this.isDynamicOrder = isDynamicOrder;
        this.storeId = storeId;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
    }

    //store id not provided
    public OrderInputToSaveInSessionDto(Map<Integer, Map<Integer, Double>> orderMinimalPriceBag, Date date, boolean isDynamicOrder, int xCoordinate, int yCoordinate) {
        this.orderMinimalPriceBag = orderMinimalPriceBag;
        this.date = date;
        this.isDynamicOrder = isDynamicOrder;
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        storeId= NO_VALUE;
    }

    public int getNO_VALUE() {
        return NO_VALUE;
    }

    public Map<Integer, Map<Integer, Double>> getOrderMinimalPriceBag() {
        return orderMinimalPriceBag;
    }

    public Date getDate() {
        return date;
    }

    public boolean isDynamicOrder() {
        return isDynamicOrder;
    }

    public int getStoreId() {
        return storeId;
    }

    public int getxCoordinate() {
        return xCoordinate;
    }

    public int getyCoordinate() {
        return yCoordinate;
    }
}
