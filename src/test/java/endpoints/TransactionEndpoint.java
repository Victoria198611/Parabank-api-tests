package endpoints;

import com.victoria.parabank.api.tests.base.BaseRequest;
import io.restassured.response.Response;

import java.util.HashMap;

public class TransactionEndpoint {
    private static final String ENDPOINT = "/parabank/services/bank/transactions";

    public Response callTransactionEndpoint() {
        return BaseRequest.getRequest()
                .pathParams(new HashMap<>()) // reset path params
                .get(ENDPOINT);
    }
}