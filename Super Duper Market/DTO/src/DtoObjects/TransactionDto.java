package DtoObjects;

public class TransactionDto {

    private final String type;
    private final String date;
    private final double transactionAmount;
    private final double balanceBefore;
    private final double balanceAfter;

    public TransactionDto(String type, String date, double transactionAmount, double balanceBefore, double balanceAfter) {
        this.type = type;
        this.date = date;
        this.transactionAmount = transactionAmount;
        this.balanceBefore = balanceBefore;
        this.balanceAfter = balanceAfter;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public double getTransactionAmount() {
        return transactionAmount;
    }

    public double getBalanceBefore() {
        return balanceBefore;
    }

    public double getBalanceAfter() {
        return balanceAfter;
    }
}
