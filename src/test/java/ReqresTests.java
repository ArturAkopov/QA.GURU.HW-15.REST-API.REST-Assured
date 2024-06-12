import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresTests {

    @BeforeAll
    public static void setUp() {
        RestAssured.baseURI = "https://reqres.in";
        RestAssured.basePath = "/api";
    }

    @Test
    void getSingleUserTest() {
        given()
                .log().all()
                .get("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .log().all()
                .get("/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }


    @Test
    void deleteSingleUserTest() {
        given()
                .log().all()
                .delete("/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    void successfulRegistrationTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\"}";
        given()
                .body(authData)
                .contentType(JSON)
                .log().all()
                .when()
                .post("/register")
                .then()
                .log().all()
                .statusCode(200)
                .body("id",is(4))
                .body("token", notNullValue());
    }

    @Test
    void updateUserTest() {
        String updateData = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .body(updateData)
                .contentType(JSON)
                .log().all()
                .when()
                .put("/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }
}