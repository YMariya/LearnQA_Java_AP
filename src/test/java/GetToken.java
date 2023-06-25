import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

import static java.util.Optional.empty;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


public class GetToken {

    @Test

    public void testGetJson() throws InterruptedException {

        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String token = response.getString("token");
        String seconds = response.getString("seconds");

        JsonPath response2 = RestAssured.given()
                .queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status = response2.get("status");
        assertThat((status), equalTo("Job is NOT ready"));

        Thread.sleep(Long.parseLong(seconds) * 1000);

        JsonPath response3 = RestAssured
                .given().queryParam("token", token)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .then()
                .assertThat().body("result", not(empty()))
                .assertThat().body("status", equalTo("Job is ready"))
                .extract()
                .jsonPath();
    }
}
