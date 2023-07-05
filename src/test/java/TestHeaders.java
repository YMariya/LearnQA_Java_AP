import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHeaders {
    @Test
    public void testHeaders() {
        Response response = given()
                .when()
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        response.prettyPeek();
        Headers headers = response.getHeaders();
        System.out.println(headers);

        Date currentDate = new Date();

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss zzz", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String formattedDate = dateFormat.format(currentDate);
        assertEquals(formattedDate, headers.getValue("Date"));
        assertEquals("application/json", headers.getValue("Content-Type"));
        assertEquals("15", headers.getValue("Content-Length"));
        assertEquals("keep-alive", headers.getValue("Connection"));
        assertEquals("timeout=10", headers.getValue("Keep-Alive"));
        assertEquals("Apache", headers.getValue("Server"));
        assertEquals("Some secret value", headers.getValue("x-secret-homework-header"));
        assertEquals("max-age=0", headers.getValue("Cache-Control"));
        assertEquals(formattedDate, headers.getValue("Expires"));
    }
    }
