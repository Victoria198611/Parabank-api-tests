package endpoints;

import com.victoria.parabank.api.tests.base.BaseRequest;
import io.restassured.response.Response;

public class AccountDetailsEndpoint {
    private static final String ENDPOINT = "/parabank/services/bank/accounts/{id}";

    public Response callAccountDetailsEndpoint(String accountId) {
        return BaseRequest.getRequest()
                .pathParam("id", accountId)
                .get(ENDPOINT);
    }
}