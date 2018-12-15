package exception;

import common.ApplicationConstants;

public class NoSuchAccountException extends Exception{
    public NoSuchAccountException(long accountId){
        super(ApplicationConstants.NO_SUCH_ACCOUNT + accountId);
    }
}
