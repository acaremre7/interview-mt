package exception;

import common.ApplicationConstants;

public class DuplicateAccountIdException extends Exception{
    public DuplicateAccountIdException(long accountId){
        super(ApplicationConstants.ACCOUNT_ID_ALREADY_EXISTS + accountId);
    }
}
