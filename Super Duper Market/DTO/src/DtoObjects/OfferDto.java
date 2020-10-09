package DtoObjects;

import java.util.Objects;

public class OfferDto {

    private final double quantity;
    private final int itemId;
    private final int forAdditional;

    public OfferDto(double quantity, int itemId, int forAdditional) {
        this.quantity = quantity;
        this.itemId = itemId;
        this.forAdditional = forAdditional;
    }

    public double getQuantity() {
        return quantity;
    }

    public int getItemId() {
        return itemId;
    }

    public int getForAdditional() {
        return forAdditional;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OfferDto offerDto = (OfferDto) o;
        return Double.compare(offerDto.quantity, quantity) == 0 &&
                itemId == offerDto.itemId &&
                forAdditional == offerDto.forAdditional;
    }

    @Override
    public int hashCode() {
        return Objects.hash(quantity, itemId, forAdditional);
    }
}
