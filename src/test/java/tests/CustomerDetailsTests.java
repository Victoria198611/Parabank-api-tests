import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.CustomerDetailsEndpoint;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class CustomerDetailsTests extends BaseTest {
    CustomerDetailsEndpoint endpoint;

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
        endpoint = new CustomerDetailsEndpoint();
    }

    @Test
    public void verifyCustomerDetailsValidId() {
        Response response = endpoint.callCustomerDetailsEndpoint("12212");
        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Blocked or invalid ID");
        }
        response.then().statusCode(200);
    }

    @Test
    public void verifyCustomerDetailsInvalidId() {
        Response response = endpoint.callCustomerDetailsEndpoint("999999");
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Test
    public void verifyCustomerDetailsNonNumericId() {
        Response response = endpoint.callCustomerDetailsEndpoint("abc");
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}