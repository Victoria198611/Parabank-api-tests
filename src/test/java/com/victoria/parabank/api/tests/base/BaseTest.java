package com.victoria.parabank.api.tests.base;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import org.testng.annotations.BeforeClass;

public class BaseTest {

    @BeforeClass
    public void setup(){
        RestAssured.filters(new AllureRestAssured());

    }
}
