import com.fasterxml.jackson.annotation.JsonAlias;
import io.qameta.allure.*;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
@Epic("User Management")
@Feature("Edit User")

public class UserEditTest extends BaseTestCase {
    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();
    @Severity(SeverityLevel.NORMAL)
    @Story("Editing a user who was just created")
    @Test
    public void testEditJustCreationTest() {
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

// EDIT
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                        editData, this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"));
        responseEditUser.prettyPeek();

       //GET
        Response responseUserData = apiCoreRequests
                .makeGetRequest("https://playground.learnqa.ru/api/user/" + userId, this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"));
        System.out.println(responseUserData.asString());
    }
    @Severity(SeverityLevel.NORMAL)
    @Story("Editing a user who was not auth")
    @Test
    public void testEditNotAuthTest() {
        //GENERATE USER
        Map<String, String> userData = DataGenerator.getRegistrationData();

        JsonPath responseCreateAuth = RestAssured
                .given()
                .body(userData)
                .post("https://playground.learnqa.ru/api/user/")
                .jsonPath();
        responseCreateAuth.prettyPeek();
        String userId = responseCreateAuth.getString("id");


// EDIT
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                        editData);
        responseEditUser.prettyPeek();

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTexEquals(responseEditUser, "Auth token not supplied");

    }
    @Severity(SeverityLevel.NORMAL)
    @Story("Editing a user who was auth other user")
    @Test
    public void testEditAuthOtherUserTest() {
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

//
//        // EDIT
        String newName = "Changed Name";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                        editData, this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"));
        responseEditUser.prettyPeek();

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTexEquals(responseEditUser, "Please, do not edit test users with ID 1, 2, 3, 4 or 5.");
    }
    @Severity(SeverityLevel.NORMAL)
    @Story("Editing a user email")
    @Test
    public void testEditEmailTest() {
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

//
//        // EDIT
        String email = "vinkotovexample.com";
        Map<String, String> editData = new HashMap<>();
        editData.put("email", email);

        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                        editData, this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"));
        responseEditUser.prettyPeek();
        System.out.println(editData.get("firstName"));

        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTexEquals(responseEditUser, "Invalid email format");
    }
    @Severity(SeverityLevel.NORMAL)
    @Story("Editing on short name")
    @Test
    public void testEditNameOnShortTest() {
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

//
//        // EDIT
        String newName = "C";
        Map<String, String> editData = new HashMap<>();
        editData.put("firstName", newName);

        Response responseEditUser = apiCoreRequests
                .makePutRequest("https://playground.learnqa.ru/api/user/" + userId,
                        editData, this.getHeader(responseGetAuth, "x-csrf-token"),
                        this.getCookie(responseGetAuth, "auth_sid"));
        responseEditUser.prettyPeek();
        Assertions.assertResponseCodeEquals(responseEditUser, 400);
        Assertions.assertResponseTexEquals(responseEditUser, "{\"error\":\"Too short value for field firstName\"}");

    }

}


