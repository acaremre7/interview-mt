package service;

import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Transaction;

public class TransactionServiceImpl implements TransactionService {

    private static TransactionServiceImpl instance;

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

    }
}
