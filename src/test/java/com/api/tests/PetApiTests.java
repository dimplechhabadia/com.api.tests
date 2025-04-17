package com.api.tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import models.Pet;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.JsonUtils;
import utils.RestAssuredUtils;

import java.io.IOException;

public class PetApiTests {

    private static final String API_VERSION = "v2";
    private static final String PET_ENDPOINT = "/pet";
    private static final String JSON_FILE_PATH = "src/test/resources/pet_data.json";

    // 1. Add a new Pet
    @Test
    public void testAddPetUsingPOJO() throws IOException {
        Pet pet = JsonUtils.readJsonAsObject(JSON_FILE_PATH, "addPet", Pet.class);
        pet.name = "HybridDog_" + System.currentTimeMillis();
        String requestBody = JsonUtils.convertObjectToJson(pet);

        Response response = RestAssuredUtils.sendPostRequest(API_VERSION, PET_ENDPOINT, null, requestBody);
        Assert.assertEquals(response.statusCode(), 200, "Status code mismatch");

        Pet createdPet = new ObjectMapper().readValue(response.asString(), Pet.class);
        Assert.assertEquals(createdPet.name, pet.name, "Name mismatch");
        Assert.assertEquals(createdPet.status, pet.status, "Status mismatch");
    }

    // 2. Get Pet by ID
    @Test
    public void testGetPetById() {
        String endpoint = PET_ENDPOINT + "/1";
        Response response = RestAssuredUtils.sendGetRequest(API_VERSION, endpoint, null);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch on getPetById");
        Assert.assertNotNull(response.jsonPath().getString("category.name"), "Category name is null in response");
    }

    // 3. Update Pet
    @Test
    public void testUpdatePet() throws IOException {
        Pet pet = JsonUtils.readJsonAsObject(JSON_FILE_PATH, "updatePet", Pet.class);
        String requestBody = JsonUtils.convertObjectToJson(pet);

        Response response = RestAssuredUtils.sendPutRequest(API_VERSION, PET_ENDPOINT, null, requestBody);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch on updatePet");
        Assert.assertEquals(response.jsonPath().getString("photoUrls[0]"),
                "https://example.com/dog-updated.jpg", "Photo URL mismatch");
    }

    // 4. Delete Pet
    @Test
    public void testDeletePet() {
        String petId = "123";
        String endpoint = PET_ENDPOINT + "/" + petId;

        Response response = RestAssuredUtils.sendDeleteRequest(API_VERSION, endpoint, null);

        Assert.assertEquals(response.getStatusCode(), 200, "Status code mismatch on deletePet");
    }

    // 5. Data-driven test for multiple pets
    @DataProvider(name = "petDataProvider")
    public Object[][] petDataProvider() throws IOException {
        String json = JsonUtils.readJsonFile("src/test/resources/multiple_pets.json");
        Pet[] pets = new ObjectMapper().readValue(json, Pet[].class);
        Object[][] data = new Object[pets.length][1];
        for (int i = 0; i < pets.length; i++) {
            data[i][0] = pets[i];
        }
        return data;
    }

    @Test(dataProvider = "petDataProvider")
    public void testAddMultiplePets(Pet pet) throws IOException {
        pet.name += "_" + System.currentTimeMillis();
        String body = new ObjectMapper().writeValueAsString(pet);
        Response response = RestAssuredUtils.sendPostRequest(API_VERSION, PET_ENDPOINT, null, body);

        Assert.assertEquals(response.statusCode(), 200, "Status code mismatch");

        Pet createdPet = new ObjectMapper().readValue(response.asString(), Pet.class);
        Assert.assertEquals(createdPet.name, pet.name, "Name mismatch");
    }
}
