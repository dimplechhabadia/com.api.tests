package com.api.tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.IOException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;

public class PetStoreAPI{

    private final String BASE_URL = "https://petstore.swagger.io/v2";

    // Read the JSON from a file
    private String readJsonFile(String filePath) throws IOException {
        return new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(filePath)));
    }

    // POST request for creating or updating a pet
    private void sendPostOrPutRequest(String jsonPayload, String method) {
        given()
                .contentType(ContentType.JSON)
                .body(jsonPayload)
                .when()
                .request(method, "/pet")
                .then()
                .statusCode(200);
    }

    // 1. Add a new Pet (POST /pet)
    @Test
    public void addPet() throws IOException {
        String petJson = readJsonFile("src/test/resources/add_pet.json");

        given()
                .contentType(ContentType.JSON)
                .body(petJson)
                .when()
                .post(BASE_URL + "/pet")
                .then()
                .statusCode(200)
                .body("name", equalTo("Buddy"))
                .body("status", equalTo("available"));
    }

    // 2. Get Pet by ID (GET /pet/{petId})
    @Test
    public void getPetById() {
        given()
                .pathParam("petId", 34567)
                .when()
                .get(BASE_URL + "/pet/{petId}")
                .then()
                .statusCode(200)
                .body("id", equalTo(34567))
                .body("name", equalTo("Buddy"))
                .body("status", equalTo("available"));
    }

    // 3. Update Pet (PUT /pet)
    @Test
    public void updatePet() throws IOException {
        String updatedPetJson = readJsonFile("src/test/resources/update_pet.json");

        given()
                .contentType(ContentType.JSON)
                .body(updatedPetJson)
                .when()
                .put(BASE_URL + "/pet")
                .then()
                .statusCode(200)
                .body("status", equalTo("sold"))
                .body("photoUrls[0]", equalTo("https://example.com/updated-photo.jpg"));
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
