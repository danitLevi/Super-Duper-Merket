package DtoObjects;

import java.time.LocalDate;
import java.util.Map;

public class OrderInputDto {

    public final int NO_VALUE=-1;

    private Map<Integer, Map<Integer, Double>> orderMinimalPriceBag;
    private int customerId;
    private String customerDetails;
    private LocalDate date;
    private boolean isStaticOrder;

    private int storeId;

    //ctor overloading

    public OrderInputDto(Map<Integer, Map<Integer, Double>> orderMinimalPriceBag, int customerId, String customerDetails, LocalDate date, boolean isStaticOrder)
    {this.orderMinimalPriceBag = orderMinimalPriceBag;
        this.customerId = customerId;
        this.customerDetails = customerDetails;
        this.date = date;
        this.isStaticOrder = isStaticOrder;
        storeId= NO_VALUE;
    }

    public String getCustomerDetails() {
        return customerDetails;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCustomerDetails(String customerDetails) {
        this.customerDetails = customerDetails;
    }

    public OrderInputDto(Map<Integer, Map<Integer, Double>> orderMinimalPriceBag, int customerId, String customerDetails, LocalDate date, boolean isStaticOrder, int storeId) {
        this.orderMinimalPriceBag = orderMinimalPriceBag;
        this.customerId = customerId;
        this.customerDetails = customerDetails;
        this.date = date;
        this.isStaticOrder = isStaticOrder;
        this.storeId = storeId;
    }

    public int getNO_VALUE() {
        return NO_VALUE;
    }

    public Map<Integer, Map<Integer, Double>> getOrderMinimalPriceBag() {
        return orderMinimalPriceBag;
    }

    public int getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }

    public boolean isStaticOrder() {
        return isStaticOrder;
    }

    public int getStoreId() {
        return storeId;
    }
}
