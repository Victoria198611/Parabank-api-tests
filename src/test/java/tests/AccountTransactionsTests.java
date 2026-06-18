package tests;

import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.AccountTransactionsEndpoint;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.TestDataUtils;

import static org.hamcrest.Matchers.*;

public class AccountTransactionsTests extends BaseTest {
    AccountTransactionsEndpoint endpoint;

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
        endpoint = new AccountTransactionsEndpoint();
    }

    @Test
    // Positive test
    public void verifyAccountTransactionsReturnsValidData() {
        Response response = endpoint.callAccountTransactionsEndpoint("13344");

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(200);
        response.then().body("transactions.transaction[0].id", notNullValue());
        response.then().body("transactions.transaction[0].type", notNullValue());
        response.then().body("transactions.transaction.size()", greaterThan(0));
        response.then().body("transactions.transaction[0].date", notNullValue());
    }

    @Test
    // Negative test (dynamic invalid ID)
    public void verifyInvalidAccountIdRandom() {
        String invalidId = TestDataUtils.generateRandomID();
        Response response = endpoint.callAccountTransactionsEndpoint(invalidId);

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Test
    // Negative test (static check for message)
    public void verifyInvalidAccountIdStatic() {
        Response response = endpoint.callAccountTransactionsEndpoint("999999");

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(anyOf(is(400), is(404)));
        response.then().body(equalTo("Could not find transactions for account #999999"));
    }

    @Test
    // Negative test
    public void verifyMissingAccountId() {
        Response response = endpoint.callAccountTransactionsEndpoint("");

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Test
    // Negative test
    public void verifyNonnumericAccountId() {
        String randomString = TestDataUtils.generateRandomString(5);
        Response response = endpoint.callAccountTransactionsEndpoint(randomString);

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}