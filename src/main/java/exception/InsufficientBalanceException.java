package exception;

import common.ApplicationConstants;

public class InsufficientBalanceException extends Exception{
    public InsufficientBalanceException(long accountId){
        super(ApplicationConstants.INSUFFICIENT_BALANCE + accountId);
    }
}
