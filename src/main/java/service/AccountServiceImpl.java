package service;

import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;

public class AccountServiceImpl implements AccountService {

    private static AccountServiceImpl instance;

    private AccountServiceImpl(){}

    public static AccountServiceImpl getInstance(){
        if(instance == null){
            synchronized (AccountServiceImpl.class) {
                if(instance == null) {
                    instance = new AccountServiceImpl();
                }
            }
        }
        return instance;
    }

    public Account createAccount(long accountId) {
        return null;
    }

    public Account createAccount(long accountId, double initialBalance) {
        return null;
    }

    public long getCurrentBalance(long accountId) throws NoSuchAccountException {
        return 0;
    }

    public void withdraw(long accountId, double amount) throws NoSuchAccountException, InsufficientBalanceException {

    }

    public void deposit(long accountId, double amount) throws NoSuchAccountException {

    }
}
