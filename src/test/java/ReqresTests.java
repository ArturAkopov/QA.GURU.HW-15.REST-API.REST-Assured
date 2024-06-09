import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @Test
    void getSingleUserTest() {
        given()
                .log().all()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("data.id", is(2));
    }

    @Test
    void getSingleUserNotFoundTest() {
        given()
                .log().all()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().all()
                .statusCode(404);
    }


    @Test
    void deleteSingleUserTest() {
        given()
                .log().all()
                .delete("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(204);
    }

    @Test
    void successfulLoginTest() {
        String authData = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"cityslicka\"}";
        given()
                .body(authData)
                .contentType(JSON)
                .log().all()
                .when()
                .post("https://reqres.in/api/login")
                .then()
                .log().all()
                .statusCode(200)
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void updateUserTest() {
        String updateData = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";
        given()
                .body(updateData)
                .contentType(JSON)
                .log().all()
                .when()
                .put("https://reqres.in/api/users/2")
                .then()
                .log().all()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }
}