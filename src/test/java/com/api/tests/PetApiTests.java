package com.api.tests;

import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqres.JsonUtils;
import reqres.RestAssuredUtils;

import java.io.IOException;

import static org.testng.Assert.assertEquals;

public class PetApiTests {


    private static final String JSON_FILE_PATH = "src/test/resources/pet_data.json";

    // 1. Add a new Pet (POST /pet)
    @Test
    public void addPet() throws IOException {
        //arrange
        String endPoint = "/pet";
        // Read "addPet" payload from JSON file
        String requestBody = JsonUtils.readJsonFile(JSON_FILE_PATH, "addPet");

        //act
        Response response = RestAssuredUtils.sendPostRequest(endPoint, null, requestBody);

        // assert
        Assert.assertEquals(200, response.getStatusCode());

        // ✅ Convert String to JSON Object
        String responseBodyString = response.getBody().asString();

        try {
            JSONObject responseBody = new JSONObject(responseBodyString);
            System.out.println("Parsed JSON Response: " + responseBody.toString(2)); // Pretty print

            // ✅ Validate Response Fields
            assertEquals(responseBody.getString("name"), "Buddy", "Name mismatch");
            assertEquals(responseBody.getString("status"), "available", "Job mismatch");

        } catch (JSONException e) {
            Assert.fail("Failed to parse response as JSON. Response: " + responseBodyString);
        }
    }

    // 2. Get Pet by ID (GET /pet/{petId})
    @Test
    public void getPetById() {

        // Define the endpoint for GET request
        String endpoint = "/pet/123";  // Example endpoint

        // Send GET request using utility method
        Response response = RestAssuredUtils.sendGetRequest(endpoint, null);

        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expected status code 200");

        // Validate the response body (example: checking username)
        Assert.assertEquals(response.jsonPath().getString("name"), "Buddy Updated", "Name mismatch in response");
    }

    // 3. Update Pet (PUT /pet)
    @Test
    public void updatePet() throws IOException {
        // Read "addPet" payload from JSON file
        String requestBody = JsonUtils.readJsonFile(JSON_FILE_PATH, "updatePet");
        String endpoint = "/pet";

        // Send PUT request using utility method
        Response response = RestAssuredUtils.sendPutRequest(endpoint, null, requestBody);

        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate the response body (example: checking name update)
        Assert.assertEquals(response.jsonPath().getString("photoUrls[0]"), "https://example.com/dog-updated.jpg", "Photo mismatch in response");
    }

    // 4. Delete Pet (DELETE /pet/{petId})
//    @Test
//    public void deletePet() {
//        given()
//                .pathParam("petId", 34567)
//                .when()
//                .delete(BASE_URL + "/pet/{petId}")
//                .then()
//                .statusCode(200)
//                .body("message", equalTo("Pet 34567 deleted"));
//    }
}
