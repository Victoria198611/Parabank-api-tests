package endpoints;

import com.victoria.parabank.api.tests.base.BaseRequest;
import io.restassured.response.Response;

public class AccountEndpoint {
    private static final String ENDPOINT = "/parabank/services/bank/accounts";

    public Response callAccountEndpoint() {
        return BaseRequest.getRequest()
                .get(ENDPOINT);
    }
}