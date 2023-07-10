package lib;

import io.restassured.response.Response;

import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.not;
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
        public static void assertJsoneHasField(Response Response, String expectedFieldName){
            Response.then().assertThat().body("$", hasKey(expectedFieldName));

}

    public static void assertJsoneHasNotField(Response Response, String expectedFieldName){
        Response.then().assertThat().body("$", not(hasKey(expectedFieldName)));

    }

    public static void assertJsoneHasFields(Response Response, String[] expectedFieldNames){
        for (String expectedFieldName : expectedFieldNames) {
            Assertions.assertJsoneHasField(Response, expectedFieldName);
        }

    }


}
