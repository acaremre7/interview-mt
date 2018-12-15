package model;

import java.util.Objects;

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
