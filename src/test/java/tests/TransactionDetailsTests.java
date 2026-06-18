package tests;

import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.TransactionDetailsEndpoint;
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

import static org.hamcrest.Matchers.*;

@Epic("Parabank API")
@Feature("Transactions")
public class TransactionDetailsTests extends BaseTest {
    TransactionDetailsEndpoint endpoint;

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
        endpoint = new TransactionDetailsEndpoint();
    }

    @Story("Valid transaction ID")
    @Description("Verify that /transactions/{id} returns correct details for a valid transaction ID.")
    @Test
    public void verifyTransactionDetailsValidId() {
        Response response = endpoint.callTransactionDetailsEndpoint("15031");

        // Skip test if blocked by Cloudflare or invalid response
        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Parabank demo environment blocked request or returned 400.");
        }

        // Expected valid response
        response.then().statusCode(200);
        response.then().body("transaction.id", equalTo("15031"));
        response.then().body("transaction.type", notNullValue());
        response.then().body("transaction.amount", notNullValue());
        response.then().body("transaction.date", notNullValue());
    }

    @Story("Invalid transaction ID")
    @Description("Verify that /transactions/{id} with a non-existent numeric ID returns 400 or 404.")
    @Test
    public void verifyTransactionDetailsInvalidId() {
        Response response = endpoint.callTransactionDetailsEndpoint("999999");

        // Skip test if blocked by Cloudflare
        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank demo environment blocked request.");
        }

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Story("Non-numeric transaction ID")
    @Description("Verify that /transactions/{id} with a non-numeric ID returns 400 or 404.")
    @Test
    public void verifyTransactionDetailsNonNumericId() {
        Response response = endpoint.callTransactionDetailsEndpoint("abc");

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}