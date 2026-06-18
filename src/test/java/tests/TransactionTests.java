package tests;
import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.TransactionEndpoint;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class TransactionTests extends BaseTest {
    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
    }

    @Test
    public void verifyTransactionDetailsMissingIdReturnsError() {
        Response response = new TransactionEndpoint()
                .callTransactionEndpoint(); // apel fără parametru

        if (response.contentType().contains("text/html")) {
            throw new SkipException("Parabank returned HTML instead of XML. Cloudflare blocking automated request.");
        }

        response.then().statusCode(anyOf(is(400), is(404)));
    }
}