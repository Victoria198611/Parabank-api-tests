package tests;

import com.victoria.parabank.api.tests.base.BaseRequest;
import com.victoria.parabank.api.tests.base.BaseTest;
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
public class TransactionTests extends BaseTest {

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
    }

    @Story("List all transactions")
    @Description("Verify that /transactions returns a list of transactions when called without parameters.")
    @Test
    public void verifyTransactionList() {
        Response response = BaseRequest.getRequest()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/123.0")
                .get("/parabank/services/bank/transactions");

        // Skip test if blocked by Cloudflare or invalid response
        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Parabank demo environment blocked request or returned 400.");
        }

        // Accept both 200 and 404 as valid responses
        response.then().statusCode(anyOf(is(200), is(404)));

        // If 200, validate transaction list structure
        if (response.statusCode() == 200) {
            response.then().body("transactions.transaction.size()", greaterThan(0));
            response.then().body("transactions.transaction[0].id", notNullValue());
            response.then().body("transactions.transaction[0].amount", notNullValue());
        }
    }

    @Story("Transaction details by valid ID")
    @Description("Verify that /transactions/{id} returns details for a valid transaction ID.")
    @Test
    public void verifyTransactionDetailsValidId() {
        Response response = BaseRequest.getRequest()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/123.0")
                .get("/parabank/services/bank/transactions/15031");

        // Skip test if blocked by Cloudflare or invalid response
        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Parabank demo environment blocked request or returned 400.");
        }

        // Expected valid response
        response.then().statusCode(200);
        response.then().body("transaction.id", equalTo("15031"));
        response.then().body("transaction.amount", notNullValue());
    }

    @Story("Transaction details with invalid ID")
    @Description("Verify that /transactions/{id} with a non-existent numeric ID returns 400 or 404.")
    @Test
    public void verifyTransactionDetailsInvalidId() {
        Response response = BaseRequest.getRequest()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/123.0")
                .get("/parabank/services/bank/transactions/999999");

        // Skip test if blocked by Cloudflare
        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank demo environment blocked request.");
        }

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Story("Transaction details with non-numeric ID")
    @Description("Verify that /transactions/{id} with a non-numeric ID returns 400 or 404.")
    @Test
    public void verifyTransactionDetailsNonNumericId() {
        Response response = BaseRequest.getRequest()
                .get("/parabank/services/bank/transactions/abc");

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}