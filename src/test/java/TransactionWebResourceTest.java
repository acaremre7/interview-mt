import common.ApplicationConstants;
import exception.DuplicateAccountIdException;
import exception.NoSuchAccountException;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import service.AccountService;
import service.AccountServiceImpl;

import java.io.IOException;

public class TransactionWebResourceTest {

    private static AccountService accountService;

    @BeforeClass
    public static void setUp() throws Exception {
        Main.initServer();
        Main.server.start();
        accountService = AccountServiceImpl.getInstance();
    }
    @AfterClass
    public static void tearDown() throws Exception {
        if(Main.server.isRunning()) {
            Main.server.stop();
            Main.server.destroy();
        }
        accountService = null;
    }

    @Test
    public void testTransaction() throws IOException, DuplicateAccountIdException, NoSuchAccountException {
        Long accountId1 = 19L;
        Double initialAmount1 = 1000D;
        Long accountId2 = 20L;
        Double initialAmount2 = 1000D;
        Double transferAmount = 500D;

        accountService.createAccount(accountId1,initialAmount1);
        accountService.createAccount(accountId2,initialAmount2);

        HttpUriRequest request = new HttpPost(ApplicationConstants.TRANSACTION_OPERATIONS_PATH +
                "?from=" + accountId1.toString() +
                "&to=" + accountId2.toString() +
                "&amount=" + transferAmount.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusLine().getStatusCode());

        Assert.assertEquals((initialAmount1 - transferAmount),accountService.getCurrentBalance(accountId1),0);
        Assert.assertEquals((initialAmount2 + transferAmount),accountService.getCurrentBalance(accountId2),0);
    }

    @Test
    public void testTransaction_InsufficientBalance() throws DuplicateAccountIdException, IOException {
        Long accountId1 = 21L;
        Double initialAmount1 = 1000D;
        Long accountId2 = 22L;
        Double initialAmount2 = 1000D;
        Double transferAmount = 2000D;

        accountService.createAccount(accountId1,initialAmount1);
        accountService.createAccount(accountId2,initialAmount2);

        HttpUriRequest request = new HttpPost(ApplicationConstants.TRANSACTION_OPERATIONS_PATH +
                "?from=" + accountId1.toString() +
                "&to=" + accountId2.toString() +
                "&amount=" + transferAmount.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.INSUFFICIENT_BALANCE + accountId1.toString(),EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testTransaction_InvalidTransferAmount() throws DuplicateAccountIdException, IOException {
        Long accountId1 = 23L;
        Double initialAmount1 = 1000D;
        Long accountId2 = 24L;
        Double initialAmount2 = 1000D;
        Double transferAmount = -100D;

        accountService.createAccount(accountId1,initialAmount1);
        accountService.createAccount(accountId2,initialAmount2);

        HttpUriRequest request = new HttpPost(ApplicationConstants.TRANSACTION_OPERATIONS_PATH +
                "?from=" + accountId1.toString() +
                "&to=" + accountId2.toString() +
                "&amount=" + transferAmount.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.NEGATIVE_TRANSFER_AMOUNT,EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testTransaction_NoSuchAccount() throws DuplicateAccountIdException, IOException {
        Long accountId1 = 25L;
        Double initialAmount1 = 1000D;
        Long accountId2 = 26L;
        Double transferAmount = -100D;

        accountService.createAccount(accountId1,initialAmount1);

        HttpUriRequest request = new HttpPost(ApplicationConstants.TRANSACTION_OPERATIONS_PATH +
                "?from=" + accountId1.toString() +
                "&to=" + accountId2.toString() +
                "&amount=" + transferAmount.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.NEGATIVE_TRANSFER_AMOUNT,EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testTransaction_InvalidID() throws DuplicateAccountIdException, IOException {
        Long accountId1 = 27L;
        Double initialAmount1 = 1000D;
        Long accountId2 = -10L;
        Double transferAmount = -100D;

        accountService.createAccount(accountId1,initialAmount1);

        HttpUriRequest request = new HttpPost(ApplicationConstants.TRANSACTION_OPERATIONS_PATH +
                "?from=" + accountId1.toString() +
                "&to=" + accountId2.toString() +
                "&amount=" + transferAmount.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.INVALID_ID + accountId2,EntityUtils.toString(response.getEntity()));
    }
}
