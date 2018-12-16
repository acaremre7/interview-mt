package dao;

import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;

public class AccountDaoImpl implements AccountDao {

    private static AccountDaoImpl instance;

    private AccountDaoImpl(){}

    public static AccountDaoImpl getInstance(){
        if(instance == null){
            synchronized (AccountDaoImpl.class) {
                if(instance == null) {
                    instance = new AccountDaoImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public Account createAccount(long accountId, double initialBalance) {
        return null;
    }

    @Override
    public void withdraw(long accountId, double amount) throws NoSuchAccountException, InsufficientBalanceException {

    }

    @Override
    public void deposit(long accountId, double amount) throws NoSuchAccountException {

    }

    @Override
    public double getCurrentBalance(long accountId) throws NoSuchAccountException {
        return 0;
    }
}
