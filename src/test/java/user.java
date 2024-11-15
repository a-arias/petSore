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

public class user {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test(priority = 1)
    public void createUserAndValidateResponse() {
        String userJson = "{\n" +
                "  \"id\": 101,\n" +
                "  \"username\": \"theUser\",\n" +
                "  \"firstName\": \"John\",\n" +
                "  \"lastName\": \"James\",\n" +
                "  \"email\": \"john@email.com\",\n" +
                "  \"password\": \"12345\",\n" +
                "  \"phone\": \"12345\",\n" +
                "  \"userStatus\": 1\n" +
                "}";

        given()
                .contentType("application/json")
                .body(userJson)
                .when()
                .post("/user")
                .then()
                .log().all()  // Log the full response
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(101))
                .body("username",  equalTo("theUser"))
                .body("firstName",  equalTo("John"))
                .body("lastName",  equalTo("James"))
                .body("email",  equalTo("john@email.com"))
                .body("password",  equalTo("12345"))
                .body("phone",  equalTo("12345"))
                .body("userStatus",  equalTo(1));
    }

    @Test(priority = 2)
    public void deleteUserAndValidateResponse() {
        given()
                .pathParam("userId", 101)
                .when()
                .delete("/user/{userId}")
                .then()
                .statusCode(200);  // Validate only the status code
    }
}