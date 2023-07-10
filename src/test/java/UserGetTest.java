import io.restassured.http.Headers;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class UserGetTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void testGetUserDataNotAuth() {
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/1");
        responseUserData.prettyPeek();
        Assertions.assertJsoneHasField(responseUserData, "username");
        Assertions.assertJsoneHasNotField(responseUserData, "firstname");
        Assertions.assertJsoneHasNotField(responseUserData, "lastname");
        Assertions.assertJsoneHasNotField(responseUserData, "email");
    }

    @Test
    public void testGetUserDetailsAuthAsSameUser() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/ajax/api/user/login", authData);
        responseGetAuth.prettyPeek();
        String cookie = this.getCookie(responseGetAuth, "auth_sid");
        String header = this.getHeader(responseGetAuth, "x-csrf-token");

        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/2", cookie, header);
        responseGetAuth.prettyPeek();

        String[] expectedFields = {"username", "firstname", "lastname", "email"};
        Assertions.assertJsoneHasFields(responseUserData, expectedFields);

//        Assertions.assertJsoneHasField(responseUserData, "username");
//        Assertions.assertJsoneHasField(responseUserData, "firstname");
//        Assertions.assertJsoneHasField(responseUserData, "lastname");
//        Assertions.assertJsoneHasField(responseUserData, "email");

    }


    @Test
    public void testGetUserDetailsAuthAsSameUser1() {
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/ajax/api/user/login", authData);
        responseGetAuth.prettyPeek();
        String cookie = getCookie(responseGetAuth, "auth_sid");
        String header = getHeader(responseGetAuth, "x-csrf-token");

        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/1", cookie, header);
        responseGetAuth.prettyPeek();

        String[] expectedFields = {"username", "firstname", "lastname", "email"};
        Assertions.assertJsoneHasFields(responseUserData, expectedFields);
    }
}
