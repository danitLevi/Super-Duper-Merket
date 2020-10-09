package logic;

import java.util.HashSet;
import java.util.Set;

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

    public void addToBalance(double amountToAdd)
    {
        this.balance+=amountToAdd;
    }

}
