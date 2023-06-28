
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCookie {
    @Test
    public void testCookie() {
        Response response = given()
                .when()
                .get(" https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        response.prettyPeek();
        String cookie = response.getCookie("HomeWork");
        System.out.println(cookie);
        String cookies = String.valueOf(response.getCookies());
        assertNotNull(cookies.length());
        assertEquals(cookie, "hw_value");
    }
    }
