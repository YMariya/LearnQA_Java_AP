import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
@Epic("User Management")
@Feature("Delete User")

public class UserDeleteTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Severity(SeverityLevel.NORMAL)
    @Story("Deleting a user who was just created")
    @Test
    public void testDeletedJustCreationTest() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        responseCreateAuth.prettyPeek();
        String userId = responseCreateAuth.getString("id");

//LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", userData.get("email"));
        authData.put("password", userData.get("password"));

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/ajax/api/user/login", authData);

// DELETED
        Response responseEditUser = apiCoreRequests
                .makeDeletedRequest("https://playground.learnqa.ru/api/user/2", this.getCookie(responseGetAuth, "auth_sid"),
                        this.getHeader(responseGetAuth, "x-csrf-token"));
        responseEditUser.prettyPeek();

        //GET
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"));
        System.out.println(responseUserData.asString().equals("User not found"));
    }

    @Severity(SeverityLevel.NORMAL)
    @Test
    @Story("Attempting to delete a user that should not be deleted")
    public void testNotDeletedUserTest() {

//LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/ajax/api/user/login", authData);
        responseGetAuth.prettyPeek();

//DELETED
        Response responseEditUser = apiCoreRequests
                .makeDeletedRequest("https://playground.learnqa.ru/api/user/2", this.getCookie(responseGetAuth, "auth_sid"),
                        this.getHeader(responseGetAuth, "x-csrf-token"));
        responseEditUser.prettyPeek();

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTexEquals(responseEditUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
    }

    @Severity(SeverityLevel.NORMAL)
    @DisplayName("Test Deleted Other User Test")
    @Test
    public void testDeletedOtherUserTest() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        responseCreateAuth.prettyPeek();
        String userId = responseCreateAuth.getString("id");

//LOGIN
        Map<String, String> authData = new HashMap<>();
        authData.put("email", "vinkotov@example.com");
        authData.put("password", "1234");

        Response responseGetAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/ajax/api/user/login", authData);
        responseGetAuth.prettyPeek();
// DELETED
        Response responseEditUser = apiCoreRequests
                .makeDeletedRequest("https://playground.learnqa.ru/api/user/" + userId, this.getCookie(responseGetAuth, "auth_sid"),
                        this.getHeader(responseGetAuth, "x-csrf-token"));
        responseEditUser.prettyPeek();

        //GET
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"));
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTexEquals(responseEditUser, "Please, do not delete test users with ID 1, 2, 3, 4 or 5.");
    }


}
