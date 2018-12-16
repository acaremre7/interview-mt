import dao.AccountDao;
import dao.AccountDaoImpl;
import exception.DuplicateAccountIdException;
import exception.InsufficientBalanceException;
import exception.NoSuchAccountException;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class AccountDaoTest {

    private static AccountDao accountDao;

    @BeforeClass
    public static void setUp(){
        accountDao  = AccountDaoImpl.getInstance();
    }

    @AfterClass
    public static void tearDown(){
        accountDao = null;
    }

    @Test
    public void testCreateAccount() throws DuplicateAccountIdException, NoSuchAccountException {
        long accountId = 7L;
        double balance = 1000D;
        accountDao.createAccount(accountId,balance);
        Assert.assertEquals(balance,accountDao.getCurrentBalance(accountId),0);
    }

    @Test
    public void testCreateAccount_DuplicateAccountIdException() throws DuplicateAccountIdException {
        long accountId = 8L;
        accountDao.createAccount(accountId,0D);
        Assert.assertThrows(DuplicateAccountIdException.class, () -> accountDao.createAccount(accountId,0D));
    }

    @Test
    public void testWithdraw() throws DuplicateAccountIdException, InsufficientBalanceException, NoSuchAccountException {
        long accountId = 9L;
        double balance = 1000D;
        double withdrawnAmount = 200D;
        accountDao.createAccount(accountId,balance);
        accountDao.withdraw(accountId,withdrawnAmount);
        Assert.assertEquals((balance-withdrawnAmount),accountDao.getCurrentBalance(accountId),0);
    }

    @Test
    public void testWithdraw_NoSuchAccountException() {
        long accountId = 10L;
        double withdrawnAmount = 200D;
        Assert.assertThrows(NoSuchAccountException.class, () -> accountDao.withdraw(accountId,withdrawnAmount));
    }

    @Test
    public void testWithdraw_InsufficientBalanceException() throws DuplicateAccountIdException {
        long accountId = 11L;
        double balance = 1000D;
        double withdrawnAmount = 2000D;
        accountDao.createAccount(accountId,balance);
        Assert.assertThrows(InsufficientBalanceException.class, () -> accountDao.withdraw(accountId,withdrawnAmount));
    }

    @Test
    public void testDeposit() throws DuplicateAccountIdException, NoSuchAccountException {
        long accountId = 12L;
        double balance = 1000D;
        double depositedAmount = 200D;
        accountDao.createAccount(accountId,balance);
        accountDao.deposit(accountId,depositedAmount);
        Assert.assertEquals((balance+depositedAmount),accountDao.getCurrentBalance(accountId),0);
    }

    @Test
    public void testDeposit_NoSuchAccountException(){
        long accountId = 13L;
        double depositedAmount = 200D;
        Assert.assertThrows(NoSuchAccountException.class, () -> accountDao.deposit(accountId,depositedAmount));
    }
}
