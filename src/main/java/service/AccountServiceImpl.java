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

    @Override
    public Account createAccount(long accountId, double initialBalance) {
        return null;
    }

    @Override
    public long getCurrentBalance(long accountId) throws NoSuchAccountException {
        return 0;
    }

    @Override
    public void withdraw(long accountId, double amount) throws NoSuchAccountException, InsufficientBalanceException {

    }

    @Override
    public void deposit(long accountId, double amount) throws NoSuchAccountException {

    }
}
