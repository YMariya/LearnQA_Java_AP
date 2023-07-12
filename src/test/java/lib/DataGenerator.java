package lib;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

public class DataGenerator {
    public static String getRandomEmail() {
        String timestemp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
    return "learnga" + timestemp + "@example.com";
    }
    public static String getBadEmail() {
        String timestemp = new SimpleDateFormat("yyyyMMddHHmmss").format(new java.util.Date());
        return "learnga" + timestemp + "example.com";
    }

    public static String getLongName() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 251; i++) {
            sb.append('a');
        }
        return sb.toString();
    }


    public static Map<String, String>  getRegistrationData() {
        Map<String, String> data = new HashMap<>();
        data.put("email", DataGenerator.getRandomEmail());
        data.put("password", "123");
        data.put("username", "learnga");
        data.put("firstName", "learnga");
        data.put("lastName", "learnga");
        return data;
    }

    public static Map<String, String>  getRegistrationData(Map<String, String>
                                                           nonDefaultValues) {

        Map<String, String> defaultValues = DataGenerator.getRegistrationData();
        Map<String, String> userData = new HashMap<>();
        String[] keys = {"email", "password", "username", "firstName", "lastName"};
        for (String key : keys) {
            if (nonDefaultValues.containsKey(key)) {
                userData.put(key, nonDefaultValues.get(key));
            }else {
                userData.put(key, defaultValues.get(key));
            }

        }
return userData;
    }
    }



