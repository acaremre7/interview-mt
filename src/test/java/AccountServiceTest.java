import exception.DuplicateAccountIdException;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import model.Account;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.AccountService;
import service.AccountServiceImpl;

public class AccountServiceTest {

    private static AccountService accountService;

    @BeforeClass
    public static void setUp(){
        accountService  = AccountServiceImpl.getInstance();
    }

    @AfterClass
    public static void tearDown(){
        accountService = null;
    }

    @Test
    public void testCreateAndGetAccount() throws DuplicateAccountIdException, NoSuchAccountException {
        long accountId = 5L;
        double initialBalance = 1000D;
        Account account = accountService.createAccount(accountId,initialBalance);
        Assert.assertEquals(account,accountService.getAccount(accountId));
    }

    @Test
    public void testCreateAccount_DuplicateAccountIdException() throws DuplicateAccountIdException{
        long accountId = 6L;
        double initialBalance = 1000D;
        accountService.createAccount(accountId,initialBalance);
        Assert.assertThrows(DuplicateAccountIdException.class, () -> accountService.createAccount(accountId,initialBalance));
    }

    @Test
    public void testGetAccount_NoSuchAccountException(){
        long accountId = 14L;
        Assert.assertThrows(NoSuchAccountException.class, () -> accountService.getAccount(accountId));
    }

    @Test
    public void testGetCurrentBalance() throws NoSuchAccountException, DuplicateAccountIdException {
        long accountId = 1L;
        double balance = 1000D;
        accountService.createAccount(accountId,balance);
        Assert.assertEquals(balance,accountService.getCurrentBalance(accountId),0);
    }

    @Test
    public void testGetCurrentBalance_NoSuchAccountException(){
        Assert.assertThrows(NoSuchAccountException.class, () -> accountService.getCurrentBalance(1000L));
    }

    @Test
    public void testWithdraw() throws DuplicateAccountIdException, InsufficientBalanceException, NoSuchAccountException {
        long accountId = 2L;
        double balance = 1000D;
        double withdrawnAmount = 200D;

        accountService.createAccount(accountId,balance);
        accountService.withdraw(accountId,withdrawnAmount);
        Assert.assertEquals((balance-withdrawnAmount),accountService.getCurrentBalance(accountId),0);
    }

    @Test
    public void testWithdraw_NoSuchAccountException(){
        Assert.assertThrows(NoSuchAccountException.class, () -> accountService.withdraw(1001L,1000D));
    }

    @Test
    public void testWithdraw_InsufficientBalanceException() throws DuplicateAccountIdException {
        long accountId = 3L;
        double balance = 1000D;
        double withdrawnAmount = 2000D;
        accountService.createAccount(accountId,balance);
        Assert.assertThrows(InsufficientBalanceException.class, () -> accountService.withdraw(accountId,withdrawnAmount));
    }

    @Test
    public void testDeposit() throws DuplicateAccountIdException, NoSuchAccountException {
        long accountId = 4L;
        double balance = 1000D;
        double depositedAmount = 200D;

        accountService.createAccount(accountId,balance);
        accountService.deposit(accountId,depositedAmount);
        Assert.assertEquals(balance+depositedAmount,accountService.getCurrentBalance(accountId),0);
    }

    @Test
    public void testDeposit_NoSuchAccountException() {
        Assert.assertThrows(NoSuchAccountException.class, () -> accountService.deposit(1002L,1000D));
    }

}
