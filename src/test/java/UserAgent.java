import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserAgent {

    @ParameterizedTest
    @ValueSource(strings = {"Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
            "Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
            "Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
            "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
            "Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1"})

    public void userAgent1(String userAgent) {
        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent", userAgent);

        JsonPath response = RestAssured
                .given()
                .headers(headers)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();
        response.prettyPeek();
        String usersAgent = response.getString("user_agent");
        String platformReq = response.getString("platform");
        String browserReq = response.getString("browser");
        String deviceReq = response.getString("device");


        if (usersAgent.equals("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30")) {
            assertEquals("Mobile", platformReq);
            assertEquals("No", browserReq);
            assertEquals("Android", deviceReq);
        }


        if (usersAgent.equals("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1")) {
            assertEquals("Mobile", platformReq);
            assertEquals("No", browserReq);
            assertEquals("iOS", deviceReq);
        }

        if (usersAgent.equals("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)")) {
            assertEquals("Googlebot", platformReq);
            assertEquals("Unknown", browserReq);
            assertEquals("Unknown", deviceReq);
        }

        if (usersAgent.equals("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0")) {
            assertEquals("Web", platformReq);
            assertEquals("Chrome", browserReq);
            assertEquals("No", deviceReq);
        }

        if (usersAgent.equals("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1")) {
            assertEquals("Mobile", platformReq);
            assertEquals("No", browserReq);
            assertEquals("iPhone", deviceReq);
        }
    }
}


