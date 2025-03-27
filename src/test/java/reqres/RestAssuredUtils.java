package reqres;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.Map;

public class RestAssuredUtils {

    /**
     * Generic method to send a POST request using Rest Assured
     *
     * @param baseURI      - Base URI of the API
     * @param endpoint     - API endpoint
     * @param headers      - Map of request headers
     * @param requestBody  - Request payload in JSON format
     * @return Response    - Rest Assured response object
     */
    public static Response sendPostRequest(String baseURI, String endpoint, Map<String, String> headers, String requestBody) {
        RestAssured.baseURI = baseURI;
        RequestSpecification request = RestAssured.given();

        // Add headers if provided
        if (headers != null && !headers.isEmpty()) {
            request.headers(headers);
        }

        // Add request body
        request.body(requestBody);

        // Send the POST request and return response
        return request
                .contentType("application/json")
                .when()
                .post(endpoint)
                .then()
                .extract()
                .response();
    }

    // Reusable GET Request Method
    public static Response sendGetRequest(String baseUrl, String endpoint, Map<String, String> headers) {
        // Request specification
        RequestSpecification request = RestAssured.given();

        // Add headers if any
        if (headers != null) {
            for (Map.Entry<String, String> entry : headers.entrySet()) {
                request.header(entry.getKey(), entry.getValue());
            }
        }

        // Send GET request and return response
        return request
                .baseUri(baseUrl)
                .when()
                .get(endpoint)
                .then()
                .extract().response();
    }

    // Reusable PUT Request Method
    public static Response sendPutRequest(String baseUrl, String endpoint, Map<String, String> headers, String body) {
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
                .baseUri(baseUrl)
                .contentType(ContentType.JSON)
                .body(body) // Add request body for PUT
                .when()
                .put(endpoint)
                .then()
                .extract().response();
    }
}
