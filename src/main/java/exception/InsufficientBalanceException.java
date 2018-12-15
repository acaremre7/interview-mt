package exception;

public class InsufficientBalanceException extends Exception{
    private static final String INSUFFICIENT_BALANCE = "Cannot process transaction: Insufficient balance in account id: ";

    public InsufficientBalanceException(long accountId){
        super(INSUFFICIENT_BALANCE + accountId);
    }
}
