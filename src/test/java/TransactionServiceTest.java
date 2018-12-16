import exception.DuplicateAccountIdException;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;
import model.Transaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.AccountService;
import service.AccountServiceImpl;
import service.TransactionService;
import service.TransactionServiceImpl;

public class TransactionServiceTest {

    private static TransactionService transactionService;
    private static AccountService accountService;
    private static Account senderAccount, recipientAccount;

    @BeforeClass
    public static void setUp(){
        transactionService  = TransactionServiceImpl.getInstance();
        accountService  = AccountServiceImpl.getInstance();
    }

    @AfterClass
    public static void tearDown(){
        transactionService = null;
        accountService = null;
    }

    @Test
    public void testProcessTransaction() throws InsufficientBalanceException, NoSuchAccountException, DuplicateAccountIdException {
        long senderAccountId = 2000L;
        long recipientAccountId = 2001L;
        double initialBalance = 1000D;
        double transactionAmount = 500D;

        accountService.createAccount(senderAccountId,initialBalance);
        accountService.createAccount(recipientAccountId,initialBalance);
        Transaction transaction = new Transaction(senderAccountId, recipientAccountId,transactionAmount);

        transactionService.processTransaction(transaction);
        Assert.assertEquals((initialBalance-transactionAmount),accountService.getCurrentBalance(senderAccountId),0);
        Assert.assertEquals((initialBalance+transactionAmount),accountService.getCurrentBalance(recipientAccountId),0);
    }

    @Test
    public void testProcessTransaction_NoSuchAccountException_1() throws DuplicateAccountIdException {
        long senderAccountId = 2002L;
        long recipientAccountId = 2003L;

        accountService.createAccount(senderAccountId,1000D);

        Transaction transaction = new Transaction(senderAccountId, recipientAccountId,500D);
        Assert.assertThrows(NoSuchAccountException.class, () -> transactionService.processTransaction(transaction));
    }

    @Test
    public void testProcessTransaction_NoSuchAccountException_2() throws DuplicateAccountIdException {
        long senderAccountId = 2004L;
        long recipientAccountId = 2005L;

        accountService.createAccount(recipientAccountId,1000D);

        Transaction transaction = new Transaction(senderAccountId, recipientAccountId,500D);
        Assert.assertThrows(NoSuchAccountException.class, () -> transactionService.processTransaction(transaction));
    }

    @Test
    public void testProcessTransaction_InsufficientBalanceException() throws DuplicateAccountIdException, NoSuchAccountException {
        long senderAccountId = 2007L;
        long recipientAccountId = 2006L;
        double initialBalance = 1000D;
        double transactionAmount = 2000D;

        senderAccount = accountService.createAccount(senderAccountId,initialBalance);
        recipientAccount = accountService.createAccount(recipientAccountId,initialBalance);
        Transaction transaction = new Transaction(senderAccountId, recipientAccountId,transactionAmount);
        Assert.assertThrows(InsufficientBalanceException.class, () -> transactionService.processTransaction(transaction));
    }
}
