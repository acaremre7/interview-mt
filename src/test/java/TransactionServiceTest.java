import exception.DuplicateAccountIdException;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;
import model.Transaction;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceTest {

    private static TransactionService transactionService;
    private static AccountService accountService;
    private static Account senderAccount,receiverAccount;

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
        double initialBalance = 1000D;
        double transactionAmount = 500D;

        senderAccount = accountService.createAccount(2000L,initialBalance);
        receiverAccount = accountService.createAccount(2001L,initialBalance);
        Transaction transaction = new Transaction(senderAccount,receiverAccount,transactionAmount);

        transactionService.processTransaction(transaction);
        Assert.assertEquals((initialBalance-transactionAmount),senderAccount.getBalance(),0);
        Assert.assertEquals((initialBalance+transactionAmount),receiverAccount.getBalance(),0);
    }

    @Test
    public void testProcessTransaction_NoSuchAccountException_1() throws DuplicateAccountIdException {
        senderAccount = accountService.createAccount(2002L,1000D);
        receiverAccount = new Account(2003L,1000D);
        Transaction transaction = new Transaction(senderAccount,receiverAccount,500D);
        Assert.assertThrows(NoSuchAccountException.class, () -> transactionService.processTransaction(transaction));
    }

    @Test
    public void testProcessTransaction_NoSuchAccountException_2() throws DuplicateAccountIdException {
        senderAccount = new Account(2004L,1000D);
        receiverAccount = accountService.createAccount(2005L,1000D);
        Transaction transaction = new Transaction(senderAccount,receiverAccount,500D);
        Assert.assertThrows(NoSuchAccountException.class, () -> transactionService.processTransaction(transaction));
    }

    @Test
    public void testProcessTransaction_InsufficientBalanceException() throws DuplicateAccountIdException, NoSuchAccountException {
        double initialBalance = 1000D;
        double transactionAmount = 2000D;

        senderAccount = accountService.createAccount(2005L,initialBalance);
        receiverAccount = accountService.createAccount(2006L,initialBalance);
        Transaction transaction = new Transaction(senderAccount,receiverAccount,transactionAmount);
        Assert.assertThrows(InsufficientBalanceException.class, () -> transactionService.processTransaction(transaction));
    }
}
