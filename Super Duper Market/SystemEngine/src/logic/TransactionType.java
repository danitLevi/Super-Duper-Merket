package logic;

public enum TransactionType {
    CHARGE ("charge"),
    RECEIVE_PAYMENT("receive payment"),
    PAYMENT ("payment");


    // declaring private variable for getting values
    private String strValue;

    // getter method
    public String getStrValue()
    {
        return this.strValue;
    }

    // enum constructor - cannot be public or protected
    private TransactionType(String strValue)
    {
        this.strValue = strValue;
    }
}
