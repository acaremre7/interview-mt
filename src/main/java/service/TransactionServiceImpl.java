package service;

import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;
import model.Transaction;

public class TransactionServiceImpl implements TransactionService {

    private static TransactionServiceImpl instance;
    private AccountService accountService = AccountServiceImpl.getInstance();

    private TransactionServiceImpl(){}

    public static TransactionServiceImpl getInstance(){
        if(instance == null){
            synchronized (TransactionServiceImpl.class) {
                if(instance == null) {
                    instance = new TransactionServiceImpl();
                }
            }
        }
        return instance;
    }

    @Override
    public void processTransaction(Transaction transaction) throws NoSuchAccountException, InsufficientBalanceException {
        Account senderAccount =  accountService.getAccount(transaction.getSenderId());
        Account recipientAccount = accountService.getAccount(transaction.getRecipientId());

        Object innerLock,outerLock;
        if(senderAccount.getAccountId() > recipientAccount.getAccountId()){
            innerLock = senderAccount.getLock();
            outerLock = recipientAccount.getLock();
        }else{
            innerLock = recipientAccount.getLock();
            outerLock = senderAccount.getLock();
        }

        /*
         * These accounts should always be locked and unlocked in the same order to avoid deadlock.
         * That's why locks are sorted according to account's id's
         */

        synchronized (outerLock){
            synchronized (innerLock){
                accountService.withdraw(transaction.getSenderId(), transaction.getAmount());
                try {
                    accountService.deposit(transaction.getRecipientId(), transaction.getAmount());
                }catch (Exception e){
                    accountService.deposit(transaction.getSenderId(),transaction.getAmount());
                    throw e;
                }
            }
        }
    }
}
