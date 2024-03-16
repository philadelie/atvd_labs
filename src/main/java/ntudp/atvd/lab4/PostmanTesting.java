package ntudp.atvd.lab4;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.parsing.Parser;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.testng.annotations.*;


public class PostmanTesting {
    private final String BASE_URL = "https://1a84744e-def6-4586-b999-3018a191c54c.mock.pstmn.io";
    private final String API_KEY = "PMAK-65f555117cbc030001cdadb2-83bd8679bd2eb253e61fdda4c0edc7b5d2";

    @BeforeClass(alwaysRun = true)
    public void setUp() {
        RestAssured.baseURI = BASE_URL;
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.requestSpecification = new RequestSpecBuilder().setContentType(ContentType.JSON).build();
        RestAssured.responseSpecification = new ResponseSpecBuilder().build();
    }

    @Test
    public void testGet200() {
        Response response = RestAssured.given()
                .header("x-api-key", API_KEY)
                .body("{\"access\": true}")
                .get();
        response.then().statusCode(HttpStatus.SC_OK);
        System.out.printf("\n%s\n", response.jsonPath().get().toString());
    }

    @Test
    public void testGet403() {
        Response response = RestAssured.given()
                .header("x-api-key", API_KEY)
                .body("{\"access\": false}")
                .get();
        response.then().statusCode(HttpStatus.SC_FORBIDDEN);
        System.out.printf("\n%s\n", response.jsonPath().get().toString());
    }

    @Test
    public void testPost200() {
        Response response = RestAssured.given()
                .header("x-api-key", API_KEY)
                .body("{\"name\": \"John\"}")
                .pathParam("user", 1)
                .post("?user={user}");
        response.then().statusCode(HttpStatus.SC_OK);
        System.out.printf("\n%s\n", response.jsonPath().get().toString());
    }

    @Test
    public void testPost400() {
        Response response = RestAssured.given()
                .header("x-api-key", API_KEY)
                .body("{\"name\": \"John\"}")
                .post();
        response.then().statusCode(HttpStatus.SC_BAD_REQUEST);
        System.out.printf("\n%s\n", response.jsonPath().get().toString());
    }

    @Test
    public void testPut500() {
        Response response = RestAssured.given()
                .header("x-api-key", API_KEY)
                .body("{\"name\": \"John\"}")
                .put();
        response.then().statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR);
        System.out.printf("\n%s\n", response.jsonPath().get().toString());
    }

    @Test(priority = 1)
    public void testDelete() {
        Response response = RestAssured.given()
                .header("x-api-key", API_KEY)
                .delete();
        response.then().statusCode(HttpStatus.SC_GONE);
        System.out.printf("\n%s\n", response.jsonPath().get().toString());
    }
}
