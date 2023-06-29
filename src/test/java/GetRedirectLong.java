import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class GetRedirectLong {

    @Test
    public void testGetRedirectLong()  {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
                .redirects().max(100)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);
    }
}