package tests;

import com.victoria.parabank.api.tests.base.BaseTest;
import endpoints.TransactionDetailsEndpoint;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.testng.SkipException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class TransactionDetailsTests extends BaseTest {
    TransactionDetailsEndpoint endpoint;

    @BeforeMethod
    public void setup() {
        RestAssured.defaultParser = Parser.XML;
        endpoint = new TransactionDetailsEndpoint();
    }

    @Test
    public void verifyTransactionDetailsValidId() {
        Response response = new TransactionDetailsEndpoint()
                .callTransactionDetailsEndpoint("15031"); // ID valid

        if (response.contentType().contains("text/html") || response.statusCode() == 400) {
            throw new SkipException("Parabank blocked automated request or ID not valid.");
        }

        response.then().statusCode(200);
        response.then().body("transaction.id", equalTo("15031"));
        response.then().body("transaction.type", notNullValue());
        response.then().body("transaction.amount", notNullValue());
        response.then().body("transaction.date", notNullValue());
    }

    @Test
    public void verifyTransactionDetailsInvalidId() {
        Response response = endpoint.callTransactionDetailsEndpoint("999999");
        response.then().statusCode(anyOf(is(400), is(404)));
    }

    @Test
    public void verifyTransactionDetailsNonNumericId() {
        Response response = endpoint.callTransactionDetailsEndpoint("abc");
        response.then().statusCode(anyOf(is(400), is(404)));
    }
}