package com.victoria.parabank.api.tests.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;

public class BaseRequest {

    private static final String BASE_URL;

    static {
        // Citește baseURL din environment.properties
        BASE_URL = ConfigManager.getProperty("base.url");
        RestAssured.baseURI = BASE_URL;
        RestAssured.useRelaxedHTTPSValidation();
    }

    public static RequestSpecification getRequest() {
        // Creează un RequestSpecification nou la fiecare apel
        return RestAssured
                .given()
                .filter(new AllureRestAssured())
                .pathParams(new HashMap<>()) // reset path params global
                .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/120.0.0.0 Safari/537.36 Edg/120.0.0.0")
                .header("Accept", "application/xml")
                .header("Accept-Language", "en-US,en;q=0.9")
                .header("Connection", "keep-alive")
                .contentType(ContentType.XML)
                .accept(ContentType.XML)
                .baseUri(BASE_URL);
    }
}