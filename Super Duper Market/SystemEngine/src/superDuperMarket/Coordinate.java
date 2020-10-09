//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2020.08.09 at 11:30:54 AM IDT 
//


package superDuperMarket;

import Exceptions.ValueOutOfRangeException;
import generatedClassesJaxb.Location;

import java.io.Serializable;
import java.util.Objects;

//import jaxb.generated.Location;

public class Coordinate implements Serializable
{
    private int y;
    private int x;
    public static final int coordinateMinValue=1;
    public static final int coordinateMaxValue=50;

    public Coordinate(Location sdmLocation) throws ValueOutOfRangeException {
       setX(sdmLocation.getX());
       setY(sdmLocation.getY());
    }

    public Coordinate(int x, int y) {
        this.y = y;
        this.x = x;
    }

    /**
     * Gets the value of the y property.
     * 
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the value of the y property.
     * 
     */
    public void setY(int value) throws ValueOutOfRangeException {

        NotifyInValidCoordinateVal(value,"Y coordinate");
        this.y = value;
    }

    /**
     * Gets the value of the x property.
     * 
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the value of the x property.
     * 
     */
    public void setX(int value) throws ValueOutOfRangeException {
        NotifyInValidCoordinateVal(value,"X coordinate");
        this.x = value;
    }

    public  void NotifyInValidCoordinateVal(int value, String coordinateName) throws ValueOutOfRangeException {
        if(value<coordinateMinValue || value>coordinateMaxValue)
        {
            throw new ValueOutOfRangeException(coordinateMinValue,coordinateMaxValue,coordinateName);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate that = (Coordinate) o;
        return y == that.y &&
                x == that.x;
    }

    @Override
    public int hashCode() {
        return Objects.hash(y, x);
    }

    @Override
    public String toString() {
        return "("+x+","+y+")";

    }
}
