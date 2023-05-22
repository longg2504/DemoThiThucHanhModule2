package utils;

import java.util.SimpleTimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidateUtils {
    public static String REGEX;
    public static final String STRING_REGEX = "^([A-ZÀ-Ă-Â-ỹ][a-zÀ-ỹ]*[ ]?)+$";




    public static boolean isStringValid(String string) {
        return Pattern.compile(STRING_REGEX).matcher(string).matches();
    }

    public static String parseCommaToChar(String s) {
        String s1 = s.replaceAll(",", "!");
        return s1;
    }
    public static String parseCharToComma(String s) {
        String s1 = s.replaceAll("!", ",");
        return s1;
    }



}
