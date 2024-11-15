import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.emptyOrNullString;
import static org.testng.AssertJUnit.*;

public class PetTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test(priority = 1)
    public void createANewPetAndValidateResponse() {
        String jsonBody = """
                {
                    "id": 101,
                    "name": "Max100",
                    "category": {
                        "id": 1,
                        "name": "Dogs"
                    },
                    "photoUrls": ["url1", "url2"],
                    "tags": [{
                        "id": 0,
                        "name": "tag1"
                    }],
                    "status": "available"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .extract().response();

        // Log response for debugging
        System.out.println("Create Pet Response: " + response.asString());
    }

    @Test(priority = 2)
    public void shouldUpdateExistingPetAndValidateResponse() {
        String jsonBody = """
                {
                    "id": 101,
                    "name": "Max200",
                    "category": {
                        "id": 1,
                        "name": "Dogs"
                    },
                    "photoUrls": ["url1", "url2"],
                    "tags": [{
                        "id": 0,
                        "name": "tag1"
                    }],
                    "status": "available"
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(jsonBody)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .contentType("application/json")
                .body("id", equalTo(101))
                .body("name", equalTo("Max200"))
                .body("status", equalTo("available"))
                .extract().response();

        // Log response for debugging
        System.out.println("Update Pet Response: " + response.asString());
    }

    @Test(priority = 3)
    public void getPetByIdAndValidateResponse() {
        Response response = given()
                .pathParam("petId", 101)
                .when()
                .get("/pet/{petId}")
                .then()
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(101))
                .body("name", not(emptyOrNullString()))
                .body("status", not(emptyOrNullString()))
                .extract().response();

        // Log response for debugging
        System.out.println("Get Pet By ID Response: " + response.asString());
    }

    @Test(priority = 4)
    public void shouldBringAllAvailablePetsAndValidateDataTypes() {
        Response response = given()
                .accept("application/json")
                .contentType("application/json")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> pets = response.jsonPath().getList("$");

        for (Map<String, Object> pet : pets) {
            assertNotNull(pet.get("id"));
            assertEquals(pet.get("status"), "available");
            assertEquals(pet.get("name").getClass(), String.class);
            assertTrue(pet.get("category") instanceof Map);
            assertEquals(pet.get("category").getClass(), LinkedHashMap.class);
            assertTrue(pet.get("photoUrls") instanceof List);
            assertTrue(pet.get("tags") instanceof List);
        }

        // Log response for debugging
        System.out.println("Get All Available Pets Response: " + response.asString());
    }

    @Test(priority = 5)
    public void shouldGetFirstPetAndValidateKeysValues() {
        Response response = given()
                .accept("application/json")
                .contentType("application/json")
                .when()
                .get("/pet/101")
                .then()
                .statusCode(200)
                .extract().response();

        assertEquals(response.path("name"), "Max200");
        assertEquals(response.path("status"), "available");
        assertEquals((Integer)response.path("category.id"), Integer.valueOf(1));
        assertEquals(response.path("category.name"), "Dogs");
        assertEquals(response.path("photoUrls[0]"), "url1");
        assertEquals((Integer)response.path("tags[0].id"), Integer.valueOf(0));
        assertEquals(response.path("tags[0].name"), "tag1");

        // Log response for debugging
        System.out.println("Get First Pet By ID Response: " + response.asString());
    }

    @Test(priority = 6)
    public void deleteAPetAndValidateResponse() {
        Response response = given()
                .pathParam("petId", 101)
                .when()
                .delete("/pet/{petId}")
                .then()
                .statusCode(200)
                .extract().response();

        // Log response for debugging
        System.out.println("Delete Pet Response: " + response.asString());
    }
}
