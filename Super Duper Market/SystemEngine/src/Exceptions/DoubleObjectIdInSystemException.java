package Exceptions;

public class DoubleObjectIdInSystemException extends Exception {

    private String pluralObjectName;
    private int id;

    public DoubleObjectIdInSystemException(String pluralObjectName, int id) {
        this.pluralObjectName = pluralObjectName;
        this.id = id;
    }

    public String getPluralObjectName() {
        return pluralObjectName;
    }

    public void setPluralObjectName(String pluralObjectName) {
        this.pluralObjectName = pluralObjectName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
