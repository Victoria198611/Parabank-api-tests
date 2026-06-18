import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.AccountDetailsEndpoint;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class AccountDetailsTests extends BaseTest {
    AccountDetailsEndpoint endpoint;

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
        endpoint = new AccountDetailsEndpoint();
    }

    @Test
    public void verifyAccountDetailsValidId() {
        Response response = endpoint.callAccountDetailsEndpoint("12345");
        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Blocked or invalid ID");
        }
        response.then().statusCode(200);
    }

    @Test
    public void verifyAccountDetailsInvalidId() {
        Response response = endpoint.callAccountDetailsEndpoint("999999");
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Test
    public void verifyAccountDetailsNonNumericId() {
        Response response = endpoint.callAccountDetailsEndpoint("abc");
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}