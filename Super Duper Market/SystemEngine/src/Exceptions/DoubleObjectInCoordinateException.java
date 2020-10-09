package Exceptions;

import superDuperMarket.Coordinate;

public class DoubleObjectInCoordinateException extends Exception{

    String objectString1;
    String objectString2;
    Coordinate location;

    public DoubleObjectInCoordinateException(String objectString1, String objectString2, Coordinate location) {
        this.objectString1 = objectString1;
        this.objectString2 = objectString2;
        this.location = location;
    }

    public String getObjectString1() {
        return objectString1;
    }

    public String getObjectString2() {
        return objectString2;
    }

    public Coordinate getLocation() {
        return location;
    }
}
