package reqres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class RestAssuredUtils {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    static {
        System.out.println(BASE_URL);
    }
    public static Response sendPostRequest(String endpoint, Map<String, String> headers, String requestBody) {
        RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);

        // Add headers if provided
        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        // Add request body
        request.body(requestBody);

        // Send the POST request and return response
        return request
                .baseUri(BASE_URL)
                .body(requestBody)
                .when()
                .post(endpoint)
                .then()
                .extract().response();
    }

    // Reusable GET Request Method
    public static Response sendGetRequest(String endpoint, Map<String, String> headers) {
        // Request specification
        RequestSpecification request = RestAssured.given().contentType(ContentType.JSON);

        // Add headers if any
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }

        // Send GET request and return response
        return request
                .baseUri(BASE_URL)
                .when()
                .get(endpoint)
                .then()
                .extract().response();
    }

    // Reusable PUT Request Method
    public static Response sendPutRequest(String endpoint, Map<String, String> headers, String body) {
        // Request specification
        RequestSpecification request = RestAssured.given();

        // Add headers if any
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }

        // Send PUT request and return response
        return request
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .body(body) // Add request body for PUT
                .when()
                .put(endpoint)
                .then()
                .extract().response();
    }

    public static Response sendDeleteRequest(String endpoint, Map<String, String> headers) {

        // Start building the request
        var requestSpecification = RestAssured.given();

        // Add headers if provided
        if (headers != null && !headers.isEmpty()) {
            requestSpecification.headers(headers);
        }

        // Send DELETE request and return the response
        return requestSpecification
                .baseUri(BASE_URL)
                .when()
                .delete(endpoint);
    }
}
