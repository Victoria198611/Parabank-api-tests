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

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

@Epic("Parabank API")
@Feature("Customer Details")
public class CustomerTests extends BaseTest {

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
    }

    @Story("Missing customer ID")
    @Description("Verify that /customers endpoint without ID returns 400 or 404.")
    @Test
    public void verifyEmptyIdReturnsError() {
        Response response = BaseRequest.getRequest()
                .get("/parabank/services/bank/customers/");

        // Skip test if blocked by Cloudflare or invalid response
        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank demo environment blocked request.");
        }

        // Accept both 400 and 404 as valid responses
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}