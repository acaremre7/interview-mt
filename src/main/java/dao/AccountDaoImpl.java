package dao;

import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;

public class AccountDaoImpl implements AccountDao {
    public Account createAccount(long accountId) {
        return null;
    }

    public Account createAccount(long accountId, double initialBalance) {
        return null;
    }

    public void withdraw(long accountId, double amount) throws NoSuchAccountException, InsufficientBalanceException {

    }

    public void deposit(long accountId, double amount) throws NoSuchAccountException {

    }
}
