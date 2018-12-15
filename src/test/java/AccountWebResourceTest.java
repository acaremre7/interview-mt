import com.google.gson.Gson;
import common.ApplicationConstants;
import model.Account;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;

public class AccountWebResourceTest {
    private static Gson gson;

    @BeforeClass
    public static void setUp() throws Exception {
        Main.initServer();
        Main.server.start();
        gson  = new Gson();
    }
    @AfterClass
    public static void tearDown() throws Exception {
        if(Main.server.isRunning()) {
            Main.server.stop();
            Main.server.destroy();
        }
        gson = null;
    }

    @Test
    public void testCreateAccount() throws IOException {
        HttpUriRequest request = new HttpPost(ApplicationConstants.ACCOUNT_OPERATIONS_PATH + "create?id=14");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusLine().getStatusCode());
    }

    @Test
    public void testCreateAccount_DuplicateAccountId() throws IOException {
        Long accountId = 15L;
        HttpUriRequest request = new HttpPost(ApplicationConstants.ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId.toString());
        HttpClientBuilder.create().build().execute(request);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.ACCOUNT_ID_ALREADY_EXISTS + accountId.toString(),EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testCreateAccount_NegativeInitialAmount() throws IOException {
        HttpUriRequest request = new HttpPost(ApplicationConstants.ACCOUNT_OPERATIONS_PATH + "create?id=16&amount=-5");
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.NEGATIVE_INITIAL_AMOUNT,EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testCreateAccount_InvalidID() throws IOException {
        Long defectiveId = -1L;
        HttpUriRequest request = new HttpPost(ApplicationConstants.ACCOUNT_OPERATIONS_PATH + "create?id=" + defectiveId.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.INVALID_ID + defectiveId.toString(),EntityUtils.toString(response.getEntity()));
    }

    @Test
    public void testGetAccount() throws IOException {
        Long accountId = 17L;
        Double initialAmount = 1000D;
        Account account = new Account(accountId,initialAmount);

        HttpUriRequest request1 = new HttpPost(ApplicationConstants.ACCOUNT_OPERATIONS_PATH + "create?id=" + accountId.toString() + "&amount=" + initialAmount.toString());
        HttpClientBuilder.create().build().execute(request1);
        HttpUriRequest request2 = new HttpGet(ApplicationConstants.ACCOUNT_OPERATIONS_PATH + accountId.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request2);

        Assert.assertEquals(HttpStatus.SC_OK,response.getStatusLine().getStatusCode());

        Account receivedAccount = gson.fromJson(EntityUtils.toString(response.getEntity()),Account.class);
        Assert.assertEquals(account,receivedAccount);
    }

    @Test
    public void testGetAccount_NoSuchAccount() throws IOException {
        Long accountId = 18L;
        HttpUriRequest request = new HttpGet(ApplicationConstants.ACCOUNT_OPERATIONS_PATH + accountId.toString());
        HttpResponse response = HttpClientBuilder.create().build().execute(request);

        Assert.assertEquals(HttpStatus.SC_BAD_REQUEST,response.getStatusLine().getStatusCode());
        Assert.assertEquals(ApplicationConstants.NEGATIVE_INITIAL_AMOUNT + accountId.toString(),EntityUtils.toString(response.getEntity()));
    }


}
