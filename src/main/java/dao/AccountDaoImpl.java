package dao;

import exception.DuplicateAccountIdException;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;

import java.util.concurrent.ConcurrentHashMap;

public class AccountDaoImpl implements AccountDao {

    private static AccountDaoImpl instance;
    private ConcurrentHashMap<Long,Account> accountMap = new ConcurrentHashMap<>();
    private static final Object createAccountLock = new Object();

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
    public Account createAccount(long accountId, double initialBalance) throws DuplicateAccountIdException{
        if(accountMap.containsKey(accountId)){
            throw new DuplicateAccountIdException(accountId);
        }else {
            synchronized (createAccountLock) {
                if(accountMap.containsKey(accountId)){
                    throw new DuplicateAccountIdException(accountId);
                }else{
                    Account account = new Account(accountId,initialBalance);
                    accountMap.put(accountId,new Account(accountId,initialBalance));
                    return account;
                }
            }
        }
    }

    @Override
    public void withdraw(long accountId, double amount) throws NoSuchAccountException, InsufficientBalanceException {
        if(!accountMap.containsKey(accountId)){
            throw new NoSuchAccountException(accountId);
        }else if(amount > accountMap.get(accountId).getBalance()){
            throw new InsufficientBalanceException(accountId);
        }else{
            synchronized (accountMap.get(accountId)){
                if(amount > accountMap.get(accountId).getBalance()){
                    throw new InsufficientBalanceException(accountId);
                }else{
                    Account account = accountMap.get(accountId);
                    account.setBalance(account.getBalance() - amount);
                    accountMap.put(accountId,account);
                }
            }
        }
    }

    @Override
    public void deposit(long accountId, double amount) throws NoSuchAccountException {
        if(!accountMap.containsKey(accountId)){
            throw new NoSuchAccountException(accountId);
        }else {
            synchronized (accountMap.get(accountId)){
                Account account = accountMap.get(accountId);
                account.setBalance(account.getBalance() + amount);
                accountMap.put(accountId,account);
            }
        }
    }

    @Override
    public double getCurrentBalance(long accountId) throws NoSuchAccountException {
        if(!accountMap.containsKey(accountId)){
            throw new NoSuchAccountException(accountId);
        }else {
            return accountMap.get(accountId).getBalance();
        }
    }
}
