import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.*;

public class pet {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test(priority = 1)
    public void createANewPetAnValidateResponse() {
        String jsonBody = "{"
                + "\"id\": 101,"
                + "\"name\": \"Max100\","
                + "\"category\": {"
                + "\"id\": 1,"
                + "\"name\": \"Dogs\""
                + "},"
                + "\"photoUrls\": [\"url1\", \"url2\"],"
                + "\"tags\": [{"
                + "\"id\": 0,"
                + "\"name\": \"tag1\""
                + "}],"
                + "\"status\": \"available\""
                + "}";

        given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200);
    }

    @Test(priority = 2)
    public void shouldUpdateExistingPetAndValidateResponse() {
        String jsonBody = "{"
                + "\"id\": 101,"
                + "\"name\": \"Max200\","
                + "\"category\": {"
                + "\"id\": 1,"
                + "\"name\": \"Dogs\""
                + "},"
                + "\"photoUrls\": [\"url1\", \"url2\"],"
                + "\"tags\": [{"
                + "\"id\": 0,"
                + "\"name\": \"tag1\""
                + "}],"
                + "\"status\": \"available\""
                + "}";

        given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(101))
                .body("name", equalTo("Max200"))
                .body("status", equalTo("available"));
    }

    @Test(priority = 3)
    public void getPetByIdAndValidateResponse() {
        given()
                .pathParam("petId", 101)
                .when()
                .get("/pet/{petId}")  // Include the path parameter in the URL
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(101))
                .body("name", not(emptyOrNullString()))
                .body("status", not(emptyOrNullString()));
    }

    @Test(priority = 4)
    public void shouldBringAllAvailablePetsAndValidateDataTypes() {
        Response response =
                given()
                        .accept("application/json")
                        .contentType("application/json")
                        .when()
                        .get("/pet/findByStatus?status=available")
                        .then()
                        .statusCode(200)
                        .extract().response();

        List<Map<String, Object>> pets = response.jsonPath().getList("$");


        for (Map<String, Object> pet : pets) {
            // Ensure the 'id' field is not null
            assertNotNull(pet.get("id"));
            // Assert that the 'status' field is equal to "available"
            assertEquals(pet.get("status"), "available");
            // Assert that the 'name' field is of type String
            assertEquals(pet.get("name").getClass(), String.class);
            // Assert that the 'category' field is a Map (can also be LinkedHashMap)
            assertTrue(pet.get("category") instanceof Map);
            assertEquals(pet.get("category").getClass(), LinkedHashMap.class);
            // Assert that the 'photoUrls' field is of type List
            assertTrue(pet.get("photoUrls") instanceof List);
            // Assert that the 'tags' field is of type List
            assertTrue(pet.get("tags") instanceof List);
        }
    }

    @Test(priority = 5)
    public void shouldGetFirstPetAndValidateKeysValues() {
        Response response =
                given()
                        .accept("application/json")
                        .contentType("application/json")
                        .when()
                        .get("/pet/101")
                        .then()
                        .statusCode(200)
                        .extract().response();

        assertEquals(response.path("name"), "Max200");  // Assuming "name" is a String
        assertEquals(response.path("status"), "available");  // Assuming "status" is a String
        assertEquals((Integer)response.path("category.id"), Integer.valueOf(1));  // Explicit cast to Integer
        assertEquals(response.path("category.name"), "Dogs");  // Assuming "category.name" is a String
        assertEquals(response.path("photoUrls[0]"), "url1");  // Assuming "photoUrls[0]" is a String
        assertEquals((Integer)response.path("tags[0].id"), Integer.valueOf(0));  // Explicit cast to Integer
        assertEquals(response.path("tags[0].name"), "tag1");  // Assuming "tags[0].name" is a String
    }

    @Test(priority = 6)
    public void deleteAPetAndValidateResponse() {
        given()
                .pathParam("petId", 101)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200);  // Validate only the status code
    }

}
