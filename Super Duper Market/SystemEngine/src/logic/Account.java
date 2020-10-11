package logic;

import DtoObjects.TransactionDto;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Account {

    double balance;
    Set<Transaction> transactions;

    public Account() {
        this.balance = 0;
        this.transactions = new HashSet<>();
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Set<Transaction> getTransactions() {
        return transactions;
    }

    public void addToBalance(double amountToAdd, Date chargeDate)
    {
        double previousBalance=this.balance;
        this.balance+=amountToAdd;

        Transaction newTransaction=new Transaction(TransactionType.CHARGE,chargeDate,amountToAdd,previousBalance,this.balance);
        transactions.add(newTransaction);
    }

    public List<TransactionDto> getTransactionsDetails()
    {
        List<TransactionDto> CustomerTransactionsDetails=new ArrayList<>();

        TransactionDto currTransactionDetails;
        for (Transaction currTransaction:transactions)
        {
            String pattern = "dd/mm/yyyy";
            DateFormat df = new SimpleDateFormat(pattern);
            String dateStr = df.format(currTransaction.getDate());

            currTransactionDetails=new TransactionDto(currTransaction.getTransactionType().getStrValue()
            ,dateStr
             ,currTransaction.getValue()
            ,currTransaction.getBalanceBeforeTransaction()
            ,currTransaction.getBalanceAfterTransaction());

            CustomerTransactionsDetails.add(currTransactionDetails);
        }

        return CustomerTransactionsDetails;
    }
}
