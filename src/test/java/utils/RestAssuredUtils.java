package utils;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class RestAssuredUtils {

    private static final String BASE_URL = "https://petstore.swagger.io";

    // Builds request with given headers/body
    private static RequestSpecification buildRequest(Map<String, String> headers, String body) {
        RequestSpecification request = RestAssured.given()
                .contentType(ContentType.JSON);

        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        if (body != null) {
            request.body(body);
        }

        return request;
    }

    // Common method to create full endpoint with version
    private static String buildFullUrl(String apiVersion, String endpoint) {
        return BASE_URL + "/" + apiVersion + endpoint;
    }

    public static Response sendPostRequest(String apiVersion, String endpoint, Map<String, String> headers, String body) {
        return buildRequest(headers, body)
                .post(buildFullUrl(apiVersion, endpoint))
                .then()
                .extract()
                .response();
    }

    public static Response sendGetRequest(String apiVersion, String endpoint, Map<String, String> headers) {
        return buildRequest(headers, null)
                .get(buildFullUrl(apiVersion, endpoint))
                .then()
                .extract()
                .response();
    }

    public static Response sendPutRequest(String apiVersion, String endpoint, Map<String, String> headers, String body) {
        return buildRequest(headers, body)
                .put(buildFullUrl(apiVersion, endpoint))
                .then()
                .extract()
                .response();
    }

    public static Response sendDeleteRequest(String apiVersion, String endpoint, Map<String, String> headers) {
        return buildRequest(headers, null)
                .delete(buildFullUrl(apiVersion, endpoint))
                .then()
                .extract()
                .response();
    }
}
