package Exceptions;

public class ValueOutOfRangeException extends Exception{

    private final int minValue;
    private final int maxValue;
    private final String variableName;
    private String objectName ="";

    public ValueOutOfRangeException(int minValue, int maxValue, String variableName, String objectName) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.variableName = variableName;
        this.objectName = objectName;
    }

    public ValueOutOfRangeException(int minValue, int maxValue, String variableName) {
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.variableName = variableName;
        this.objectName = "";
    }

    public int getMinValue() {
        return minValue;
    }

    public int getMaxValue() {
        return maxValue;
    }

    public String getVariableName() {
        return variableName;
    }

    public String getObjectName() {
        return objectName;
    }
}
