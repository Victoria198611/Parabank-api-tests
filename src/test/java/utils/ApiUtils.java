package utils;

import io.restassured.response.Response;

public class ApiUtils {

    //Extract status code
    public static int getStatusCode(Response response) {
        return response.getStatusCode();
    }

    //Extract response time
    public static long getResponseTime(Response response) {
        return response.getTime();
    }

    //Extract header value
    public static String getHeader(Response response, String headerName) {
        return response.getHeader(headerName);
    }
}

