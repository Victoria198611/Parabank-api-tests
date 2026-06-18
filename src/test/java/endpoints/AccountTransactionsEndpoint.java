package endpoints;

import com.victoria.parabank.api.tests.base.BaseRequest;
import io.restassured.response.Response;

public class AccountTransactionsEndpoint {
    private static final String ENDPOINT = "/services/bank/accounts/{id}/transactions";

    public Response callAccountTransactionsEndpoint(String accountId) {

        return BaseRequest.getRequest()
                .pathParam("id", accountId)
                .get(ENDPOINT);
    }
}
