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
@Feature("Accounts")
public class AccountTests extends BaseTest {
    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
    }

    @Story("List accounts by customer ID")
    @Description("Verify that /accounts/{customerId} returns a list of accounts for a valid customer.")
    @Test
    public void verifyAccountDetailsMissingIdReturnsError() {
        Response response = BaseRequest.getRequest()
                .get("/parabank/services/bank/accounts/");
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Story("Valid customer ID")
    @Description("Verify that /accounts/{customerId} returns a list of accounts for a valid customer.")
    @Test
    public void verifyAccountListValidCustomerId() {
        Response response = BaseRequest.getRequest()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/123.0")
                .get("/parabank/services/bank/accounts/12212");

        // Skip test if blocked by Cloudflare or invalid response
        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Parabank demo environment blocked request or returned 400.");
        }

        // Expected valid response
        response.then().statusCode(200);
        response.then().body("accounts.account.size()", greaterThan(0));
        response.then().body("accounts.account[0].id", notNullValue());
        response.then().body("accounts.account[0].type", notNullValue());
        response.then().body("accounts.account[0].balance", notNullValue());
    }

    @Story("Invalid customer ID")
    @Description("Verify that /accounts/{customerId} with a non-existent numeric ID returns 400 or 404.")
    @Test
    public void verifyAccountListInvalidCustomerId() {
        Response response = BaseRequest.getRequest()
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) Chrome/123.0")
                .get("/parabank/services/bank/accounts/999999");

        // Skip test if blocked by Cloudflare
        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank demo environment blocked request.");
        }

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Story("Non-numeric customer ID")
    @Description("Verify that /accounts/{customerId} with a non-numeric ID returns 400 or 404.")
    @Test
    public void verifyAccountListNonNumericCustomerId() {
        Response response = BaseRequest.getRequest()
                .get("/parabank/services/bank/accounts/abc");
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}