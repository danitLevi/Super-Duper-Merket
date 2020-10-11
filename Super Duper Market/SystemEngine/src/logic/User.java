package logic;

public class User {

    private int id;
    private static int nextId=0;
    private String name; // todo : check name unique
    private Account account;

    public User(String name) {
        this.name = name;
        nextId++;
        this.id = nextId;
        account=new Account();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static int getNextId() {
        return nextId;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public double getCurrBalance()
    {
        return getAccount().getBalance();
    }
}
