package model;

import java.util.Objects;

public class Account {
    private long accountId;
    private double balance;
    private final Object lock = new Object();

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

    public Object getLock() {
        return lock;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return accountId == account.accountId &&
                Double.compare(account.balance, balance) == 0;
    }

    @Override
    public int hashCode() {

        return Objects.hash(accountId, balance);
    }
}
