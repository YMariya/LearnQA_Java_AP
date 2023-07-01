import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;


public class GetRedirectLong {

    @Test
    public void testGetRedirectLong() {

        Response response = RestAssured
                .given()
                .redirects()
                .follow(true)
              //  .redirects().max(100)
                .when()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();

        int statusCode = response.getStatusCode();
        System.out.println("Status code: " + statusCode);
    }

    @Test
    public void testGetRedirectLong2() {
        String locationHeader = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode = 0;
        int count = 0;
        while (statusCode != 200) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(true)
                    .when()
                    .get(locationHeader)
                    .andReturn();
            count++;
            System.out.println("Количество редиректов: " + count);
            statusCode = response.getStatusCode();
            System.out.println("Status code: " + statusCode);
            locationHeader = response.getHeader("Location");
        }
    }}



