package tests;

import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.AccountTransactionsEndpoint;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utils.TestDataUtils;

import static org.hamcrest.Matchers.*;

@Epic("Parabank API")
@Feature("Account Transactions")
public class AccountTransactionsTests extends BaseTest {
    AccountTransactionsEndpoint endpoint;

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
        endpoint = new AccountTransactionsEndpoint();
    }

    @Story("Valid account transactions")
    @Description("Verify that /transactions/{accountId} returns correct data for a valid account ID.")
    @Test
    public void verifyAccountTransactionsReturnsValidData() {
        Response response = endpoint.callAccountTransactionsEndpoint("13344");

        // Skip test if blocked by Cloudflare or invalid response
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

    @Story("Invalid account ID - random")
    @Description("Verify that /transactions/{accountId} with a random invalid ID returns 400 or 404.")
    @Test
    public void verifyInvalidAccountIdRandom() {
        String invalidId = TestDataUtils.generateRandomID();
        Response response = endpoint.callAccountTransactionsEndpoint(invalidId);

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Story("Invalid account ID - static")
    @Description("Verify that /transactions/{accountId} with static ID 999999 returns 400 or 404 and proper error message.")
    @Test
    public void verifyInvalidAccountIdStatic() {
        Response response = endpoint.callAccountTransactionsEndpoint("999999");

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(anyOf(is(400), is(404)));
        response.then().body(equalTo("Could not find transactions for account #999999"));
    }

    @Story("Missing account ID")
    @Description("Verify that /transactions endpoint without accountId returns 400 or 404.")
    @Test
    public void verifyMissingAccountId() {
        Response response = endpoint.callAccountTransactionsEndpoint("");

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().log().all();
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Story("Non-numeric account ID")
    @Description("Verify that /transactions/{accountId} with a non-numeric ID returns 400 or 404.")
    @Test
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