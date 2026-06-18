package endpoints;

import com.victoria.parabank.api.tests.base.BaseRequest;
import io.restassured.response.Response;

public class TransactionDetailsEndpoint {
    private static final String ENDPOINT = "/parabank/services/bank/transactions/{id}";

    public Response callTransactionDetailsEndpoint(String transactionId) {
        return BaseRequest.getRequest()
                .pathParam("id", transactionId)
                .get(ENDPOINT);
    }
}