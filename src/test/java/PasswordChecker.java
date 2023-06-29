import io.restassured.RestAssured;
import io.restassured.http.Cookie;
import io.restassured.response.Response;

import java.util.ArrayList;

public class PasswordChecker {

    public static void main(String[] args) {
        String login = "super_admin";
        ArrayList<String> myArrayList = new ArrayList<String>();

        myArrayList.add("123456");
        myArrayList.add("12345678");
        myArrayList.add("qwerty");
        myArrayList.add("abc123");
        myArrayList.add("password");
        myArrayList.add("monkey");
        myArrayList.add("princess");
        myArrayList.add("123456789");
        myArrayList.add("111111");
        myArrayList.add("letmein");
        myArrayList.add("1234");
        myArrayList.add("1234567890");
        myArrayList.add("1234567");
        myArrayList.add("dragon");
        myArrayList.add("baseball");
        myArrayList.add("sunshine");
        myArrayList.add("iloveyou");
        myArrayList.add("trustno1");
        myArrayList.add("654321");
        myArrayList.add("starwars");
        myArrayList.add("121212");
        myArrayList.add("mustang");
        myArrayList.add("ashley");
        myArrayList.add("qwertyuiop");
        myArrayList.add("1qaz2wsx");
        myArrayList.add("photoshop[a]");
        myArrayList.add("666666");
        myArrayList.add("master");
        myArrayList.add("1q2w3e4r");
        myArrayList.add("solo");
        myArrayList.add("qwerty123");
        myArrayList.add("admin");
        myArrayList.add("login");
        myArrayList.add("welcome");
        myArrayList.add("123123");
        myArrayList.add("adobe123[a]");
        myArrayList.add("bailey");
        myArrayList.add("access");
        myArrayList.add("flower");
        myArrayList.add("555555");
        myArrayList.add("passw0rd");
        myArrayList.add("shadow");
        myArrayList.add("lovely");
        myArrayList.add("7777777");
        myArrayList.add("12345");
        myArrayList.add("michael");
        myArrayList.add("!@#$%^&*");
        myArrayList.add("jesus");
        myArrayList.add("superman");
        myArrayList.add("hello");
        myArrayList.add("charlie");
        myArrayList.add("888888");
        myArrayList.add("696969");
        myArrayList.add("hottie");
        myArrayList.add("freedom");
        myArrayList.add("aa123456");
        myArrayList.add("qazwsx");
        myArrayList.add("ninja");
        myArrayList.add("azerty");
        myArrayList.add("loveme");
        myArrayList.add("whatever");
        myArrayList.add("donald");
        myArrayList.add("batman");
        myArrayList.add("zaq1zaq1");
        myArrayList.add("password1");
        myArrayList.add("Football");
        myArrayList.add("000000");
        myArrayList.add("123qwe");

        for (String password : myArrayList) {
            String authCookieValue = getAuthorizationCookie(login, password);
            if (authCookieValue != null) {
                String authResult = checkAuthCookie(authCookieValue);
                if (!authResult.equals("You are NOT authorized")) {
                    System.out.println("Correct password found:");
                    System.out.println("Login: " + login);
                    System.out.println("Password: " + password);
                    System.out.println("Auth Result: " + authResult);
                    break;
                }
            }
        }
    }

    public static String getAuthorizationCookie(String login, String password) {
        String url = "https://playground.learnqa.ru/ajax/api/get_secret_password_homework";
        Response response = RestAssured.given()
                .param("login", login)
                .param("password", password)
                .post(url);
        int statusCode = response.getStatusCode();
        if (statusCode == 200) {
            Cookie authCookie = response.getDetailedCookie("auth_cookie");
            if (authCookie != null) {
                return authCookie.getValue();
            }
        }
        return null;
    }

    public static String checkAuthCookie(String authCookieValue) {
        String url = "https://playground.learnqa.ru/ajax/api/check_auth_cookie";
        Response response = RestAssured.given()
                .cookie("auth_cookie", authCookieValue)
                .get(url);
        return response.getBody().asString();
    }
}
