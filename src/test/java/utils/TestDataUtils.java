package utils;

import java.util.Random;

public class TestDataUtils {

    //Generate random numeric ID
    public static String generateRandomID(){
        return String.valueOf(new Random().nextInt(999999));
    }

    //Generate random string
public static String generateRandomString(int length) {
    String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    StringBuilder sb = new StringBuilder();
    Random random =new Random();
    for (int i = 0; i <length; i++) {
        sb.append(chars.charAt(random.nextInt(chars.length())));
    }
    return sb.toString();
}
}
