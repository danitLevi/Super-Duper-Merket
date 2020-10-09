package Exceptions;

public class RegionAlreadyExistException extends  Exception {

    private final String regionName;

    public RegionAlreadyExistException(String regionName) {
        this.regionName = regionName;
    }

    public String getRegionName() {
        return regionName;
    }
}
