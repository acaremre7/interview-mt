package exception;

public class DuplicateAccountIdException extends Exception{
    private static final String ACCOUNT_ID_ALREADY_EXISTS = "Account id already exists: ";

    public DuplicateAccountIdException(long accountId){
        super(ACCOUNT_ID_ALREADY_EXISTS + accountId);
    }
}
