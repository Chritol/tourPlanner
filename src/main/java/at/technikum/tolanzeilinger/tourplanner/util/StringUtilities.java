package at.technikum.tolanzeilinger.tourplanner.util;

import java.util.regex.Pattern;

public class StringUtilities {
    public static String clearTrailingWhitespaces(String input) {
        if (input == null) {
            return null;
        }

        return input.replaceAll("^\\s+|\\s+$", "");
    }

    public static boolean isNullOrWhitespace(String input) {
        if (input == null) {
            return true;
        }

        return input.trim().isEmpty();
    }

    public static boolean isTextTooLong(String text, int maxLength) {
        return text != null && text.length() > maxLength;
    }


}
