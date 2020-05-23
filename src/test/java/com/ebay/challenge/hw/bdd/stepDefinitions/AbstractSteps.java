package com.ebay.challenge.hw.bdd.stepDefinitions;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;


/**
 * @author andrey.dodon
 */

public class AbstractSteps {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractSteps.class);


    protected <T> T executeGet(Map<String, String> queryParams, Class<T> toValueType) {
        RequestSpecification request = given().urlEncodingEnabled(false);
        setQueryParams(queryParams, request);
        Response response = request.accept(ContentType.JSON)
                .log().all().get();
        LOG.info("Response: ");
        response.then().log().all();
        response.getBody().as(toValueType);
        return response.getBody().as(toValueType);
    }


    protected ResponseBody executeGet(Map<String, String> queryParams) {
        RequestSpecification request = given().urlEncodingEnabled(false);
        setQueryParams(queryParams, request);
        Response response = request.accept(ContentType.JSON)
                .log().all().get();
        LOG.info("Response: ");
        response.then().log().all();
        return response.getBody();
    }


    private void setQueryParams(Map<String, String> queryParams, RequestSpecification request) {
        if (null != queryParams) {
            request.queryParams(queryParams);
        }
    }


}