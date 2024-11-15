import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class InventoryTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test(priority = 1)
    public void getInventoryInformation() {
        Response response = given()
                .when()
                .get("/store/inventory")
                .then()
                .statusCode(200)
                .assertThat()
                .body("approved", equalTo(50))
                .body("delivered", equalTo(50))
                .extract()
                .response();

        // Log response for debugging
        System.out.println("Response: " + response.asString());
    }
}
