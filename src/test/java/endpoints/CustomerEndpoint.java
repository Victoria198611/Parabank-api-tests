package endpoints;

import com.victoria.parabank.api.tests.base.BaseRequest;
import io.restassured.response.Response;

public class CustomerEndpoint {
    private static final String ENDPOINT = "/parabank/services/bank/customers";

    public Response callCustomerEndpoint() {
        return BaseRequest.getRequest()
                .get(ENDPOINT);
    }
}