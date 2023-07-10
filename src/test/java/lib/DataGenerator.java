package lib;

import java.text.SimpleDateFormat;

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
    }



