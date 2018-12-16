package service;

import dao.AccountDao;
import dao.AccountDaoImpl;
import exception.DuplicateAccountIdException;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;

public class AccountServiceImpl implements AccountService {

    private static AccountServiceImpl instance;
    private AccountDao accountDao = AccountDaoImpl.getInstance();

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
    public Account createAccount(long accountId, double initialBalance) throws DuplicateAccountIdException {
        return accountDao.createAccount(accountId,initialBalance);
    }

    @Override
    public Account getAccount(long accountId) throws NoSuchAccountException {
        return accountDao.getAccount(accountId);
    }

    @Override
    public double getCurrentBalance(long accountId) throws NoSuchAccountException {
        return accountDao.getCurrentBalance(accountId);
    }

    @Override
    public void withdraw(long accountId, double amount) throws NoSuchAccountException, InsufficientBalanceException {
        accountDao.withdraw(accountId,amount);
    }

    @Override
    public void deposit(long accountId, double amount) throws NoSuchAccountException {
        accountDao.deposit(accountId,amount);
    }
}
