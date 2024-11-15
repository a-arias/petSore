import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class UserTest {

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = "http://localhost:8080/api/v3";
    }

    @Test(priority = 1)
    public void createUserAndValidateResponse() {
        String userJson = """
                {
                    "id": 101,
                    "username": "theUser",
                    "firstName": "John",
                    "lastName": "James",
                    "email": "john@email.com",
                    "password": "12345",
                    "phone": "12345",
                    "userStatus": 1
                }
                """;

        Response response = given()
                .contentType("application/json")
                .body(userJson)
                .when()
                .post("/user")
                .then()
                .log().all()  // Log the full response
                .statusCode(200)
                .assertThat()
                .body("id", equalTo(101))
                .body("username", equalTo("theUser"))
                .body("firstName", equalTo("John"))
                .body("lastName", equalTo("James"))
                .body("email", equalTo("john@email.com"))
                .body("password", equalTo("12345"))
                .body("phone", equalTo("12345"))
                .body("userStatus", equalTo(1))
                .extract().response();

        // Log response for debugging
        System.out.println("Create User Response: " + response.asString());
    }

    @Test(priority = 2)
    public void deleteUserAndValidateResponse() {
        Response response = given()
                .pathParam("userId", 101)
                .when()
                .delete("/user/{userId}")
                .then()
                .statusCode(200)
                .extract().response();

        // Log response for debugging
        System.out.println("Delete User Response: " + response.asString());
    }
}
