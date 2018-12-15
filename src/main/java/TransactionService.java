import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Transaction;

public interface TransactionService {

    /**
     * Processes given transaction.
     * @param transaction Transaction to be processed
     * @throws NoSuchAccountException Thrown when either sender or receiver doesn't exist
     * @throws InsufficientBalanceException Thrown when sender doesn't have sufficient amount of money in the account
     */
    void processTransaction(Transaction transaction) throws NoSuchAccountException, InsufficientBalanceException;
}
