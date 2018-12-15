package exception;

public class NoSuchAccountException extends Exception{
    private static final String NO_SUCH_ACCOUNT = "No such account matching with the account id: ";


    public NoSuchAccountException(long accountId){
        super(NO_SUCH_ACCOUNT + accountId);
    }
}
