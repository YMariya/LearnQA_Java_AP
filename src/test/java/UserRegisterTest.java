import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.restassured.response.Response;
import lib.ApiCoreRequests;
import lib.Assertions;
import lib.BaseTestCase;
import lib.DataGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
@Epic("Register cases")
@Feature("Register")
public class UserRegisterTest extends BaseTestCase {

    private final ApiCoreRequests apiCoreRequests = new ApiCoreRequests();

    @Test
    public void testCreateUserWithExistingEmail() {
        String email = "vinkotov@example.com";

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData = DataGenerator.getRegistrationData(userData);


        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTexEquals(responseCreateAuth, "Users with email '" + email + "' already exists");


    }

    @Test
    public void testCreateUserSuccessfully() {
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = DataGenerator.getRegistrationData();


        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);

        Assertions.assertResponseCodeEquals(responseCreateAuth, 200);
        Assertions.assertJsoneHasField(responseCreateAuth, "id");
    }


    @Test
    public void testCreateUserBadEmail() {
        String email = DataGenerator.getBadEmail();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "learnga");
        userData.put("username", "learnga");
        userData.put("firstName", "learnga");
        userData.put("lastName", "learnga");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        responseCreateAuth.prettyPeek();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTexEquals(responseCreateAuth, "Invalid email format");
    }


    @Test
    public void testCreateUserNameShort() {
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "learnga");
        userData.put("username", "l");
        userData.put("firstName", "learnga");
        userData.put("lastName", "learnga");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        responseCreateAuth.prettyPeek();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTexEquals(responseCreateAuth, "The value of 'username' field is too short");
    }


    @Test
    public void testCreateUserNameLong() {
        String email = DataGenerator.getRandomEmail();
String name = DataGenerator.getLongName();
        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", "learnga");
        userData.put("username", name);
        userData.put("firstName", "learnga");
        userData.put("lastName", "learnga");

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        responseCreateAuth.prettyPeek();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
        Assertions.assertResponseTexEquals(responseCreateAuth, "The value of 'username' field is too long");
    }

    @ParameterizedTest
    @CsvSource({
            ", learnga, learnga, learnga",
            "123, , learnga, learnga",
            "123, learnga, , learnga",
            "123, learnga, learnga, ",
    })
    public void testCreateUserParamEmpty(String password, String username, String firstName, String lastName) {
        String email = DataGenerator.getRandomEmail();

        Map<String, String> userData = new HashMap<>();
        userData.put("email", email);
        userData.put("password", password);
        userData.put("username", username);
        userData.put("firstName", firstName);
        userData.put("lastName", lastName);

        Response responseCreateAuth = apiCoreRequests
                .makePostRequest("https://playground.learnqa.ru/api/user/", userData);
        responseCreateAuth.prettyPeek();
        Assertions.assertResponseCodeEquals(responseCreateAuth, 400);
    }
}
