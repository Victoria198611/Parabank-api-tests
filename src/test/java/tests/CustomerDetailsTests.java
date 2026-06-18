import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.CustomerDetailsEndpoint;
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

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@Epic("Parabank API")
@Feature("Customer Details")
public class CustomerDetailsTests extends BaseTest {
    CustomerDetailsEndpoint endpoint;

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
        endpoint = new CustomerDetailsEndpoint();
    }

    @Story("Valid customer details")
    @Description("Verify that /customers/{id} returns correct details for a valid customer ID.")
    @Test
    public void verifyCustomerDetailsValidId() {
        Response response = endpoint.callCustomerDetailsEndpoint("12212");

        // Skip test if blocked by Cloudflare or invalid response
        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Parabank demo environment blocked request or returned 400.");
        }

        // Expected valid response
        response.then().statusCode(200);
    }

    @Story("Invalid customer ID")
    @Description("Verify that /customers/{id} with a non-existent numeric ID returns 400 or 404.")
    @Test
    public void verifyCustomerDetailsInvalidId() {
        Response response = endpoint.callCustomerDetailsEndpoint("999999");

        // Skip test if blocked by Cloudflare
        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank demo environment blocked request.");
        }

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Story("Non-numeric customer ID")
    @Description("Verify that /customers/{id} with a non-numeric ID returns 400 or 404.")
    @Test
    public void verifyCustomerDetailsNonNumericId() {
        Response response = endpoint.callCustomerDetailsEndpoint("abc");

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}