package model;

public class Account {
    private long accountId;
    private double balance;

    public Account(long accountId) {
        this.accountId = accountId;
        this.balance = 0;
    }

    public Account(long accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
    }

    public long getAccountId() {
        return accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
