package endpoints;

import com.victoria.parabank.api.tests.base.BaseRequest;
import io.restassured.response.Response;

public class CustomerDetailsEndpoint {
    private static final String ENDPOINT = "/parabank/services/bank/customers/{id}";

    public Response callCustomerDetailsEndpoint(String customerId) {
        return BaseRequest.getRequest()
                .pathParam("id", customerId)
                .get(ENDPOINT);
    }
}