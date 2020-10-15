package DtoObjects;

import java.util.List;

public class SaleDto {
private  final String name;
private final double QuantityToBuy;
private final int itemIdToBuy;

private final List<OfferDto> offersToGet;
private final String OptionToGet;

    public SaleDto(String name, double quantityToBuy, int itemIdToBuy, List<OfferDto> offersToGet, String optionToGet) {
        this.name = name;
        QuantityToBuy = quantityToBuy;
        this.itemIdToBuy = itemIdToBuy;

        this.offersToGet = offersToGet;
        OptionToGet = optionToGet;
    }

    public String getName() {
        return name;
    }

    public double getQuantityToBuy() {
        return QuantityToBuy;
    }

    public int getItemIdToBuy() {
        return itemIdToBuy;
    }

    public List<OfferDto> getOffersToGet() {
        return offersToGet;
    }

    public String getOptionToGet() {
        return OptionToGet;
    }
}
