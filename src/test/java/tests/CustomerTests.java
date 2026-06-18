import com.victoria.parabank.api.tests.base.BaseRequest;
import com.victoria.parabank.api.tests.base.BaseTest;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class CustomerTests extends BaseTest {
    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
    }

    @Test
    public void verifyEmptyIdReturnsError() {
        Response response = BaseRequest.getRequest()
                .get("/parabank/services/bank/customers/");
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}