import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.testng.AssertJUnit.*;

public class inventory {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test(priority = 1)
    public void getInventoryInformation() {
        given()
                .when()
                .get("/store/inventory")  // Include the path parameter in the URL
                .then()
                .statusCode(200)
                .assertThat()
                .body("approved", equalTo(50))
                .body("delivered", equalTo(50));
    }

}
