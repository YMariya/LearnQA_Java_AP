import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;

public class GetTextJson {

    @Test

    public void testGetJson() {
        JsonPath response = RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();

        String messages = response.getString("messages[1]");
        System.out.println(messages);
    }
}
