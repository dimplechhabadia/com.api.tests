package com.api.tests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;
import reqres.RestAssuredUtils;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.testng.Assert.assertEquals;

public class PetApiTests {

    private final String BASE_URL = "https://petstore.swagger.io/v2";

    // Read the JSON from a file
    private String readJsonFile(String filePath) throws IOException {
        return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
    }

    // 1. Add a new Pet (POST /pet)
    @Test
    public void addPet() throws IOException {
        //arrange
        String endPoint = BASE_URL + "/pet";
        String petJson = readJsonFile("src/test/resources/add_pet.json");

        //act
        Response response = RestAssuredUtils.sendPostRequest(BASE_URL, endPoint, null, petJson);

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

        String endpoint = "/pet/34567";
        // Send GET request using utility method
        Response response = RestAssuredUtils.sendGetRequest(BASE_URL, endpoint, null);

        // Validate the response status code
        assertEquals(response.getStatusCode(), HttpStatus.SC_OK, "Expected status code 200");

        // Validate the response body (example: checking username)
        assertEquals(response.jsonPath().getString("name"), "Buddy", "Name mismatch in response");
    }

    // 3. Update Pet (PUT /pet)
    @Test
    public void updatePet() throws IOException {
        String updatedPetJson = readJsonFile("src/test/resources/update_pet.json");
        String endpoint = BASE_URL + "/pet";

        // Send PUT request using utility method
        Response response = RestAssuredUtils.sendPutRequest(BASE_URL, endpoint, null, updatedPetJson);

        // Validate the response status code
        Assert.assertEquals(response.getStatusCode(), 200, "Expected status code 200");

        // Validate the response body (example: checking name update)
        Assert.assertEquals(response.jsonPath().getString("photoUrls[0]"), "https://example.com/updated-photo.jpg", "Photo mismatch in response");
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
