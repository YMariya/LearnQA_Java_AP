package lib;

import io.restassured.RestAssured;
import io.restassured.internal.common.assertion.Assertion;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import lib.Assertions;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasKey;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class Assertions extends BaseTestCase {
   public static void asserJsonByName (Response Response, String name, int expectedValue){
       Response.then().assertThat().body("$", hasKey(name));

       int value = Response.jsonPath().getInt(name);
       assertEquals(expectedValue,value, "JSON value is not equals to expected value");
   }

   public static void assertResponseTexEquals (Response Response, String expectedAnswer){
       assertEquals(
               expectedAnswer,
               Response.asString(),
               "Response text is not as expected"
       );

   }
    public static void assertResponseCodeEquals (Response Response, int expectedStatusCode){
        assertEquals(
                expectedStatusCode,
                Response.statusCode(),
                "Response status is not as expected"
        );}
        public static void assertJsoneHasKey (Response Response, String expectedFieldName){
            Response.then().assertThat().body("$", hasKey(expectedFieldName));

}}
