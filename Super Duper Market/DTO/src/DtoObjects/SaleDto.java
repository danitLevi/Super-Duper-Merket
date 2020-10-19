package DtoObjects;

import java.util.List;

public class SaleDto {
private  final String name;
private final double quantityToBuy;
private final int itemIdToBuy;

private final List<OfferDto> offersToGet;
private final String optionToGet;

    public SaleDto(String name, double quantityToBuy, int itemIdToBuy, List<OfferDto> offersToGet, String optionToGet) {
        this.name = name;
        this.quantityToBuy = quantityToBuy;
        this.itemIdToBuy = itemIdToBuy;

        this.offersToGet = offersToGet;
        this.optionToGet = optionToGet;
    }

    public String getName() {
        return name;
    }

    public double getQuantityToBuy() {
        return quantityToBuy;
    }

    public int getItemIdToBuy() {
        return itemIdToBuy;
    }

    public List<OfferDto> getOffersToGet() {
        return offersToGet;
    }

    public String getOptionToGet() {
        return optionToGet;
    }
}
