package eao0418.demo.rest.tools;

import java.lang.IllegalArgumentException;

public class RestUtil {

    public static boolean isUserValidPattern(String userString) throws IllegalArgumentException {

        boolean isValid = false;

        if (isNotNullEmptyOrWhitespaceString(userString)) {

            // For the demo, assume a userId that contains any combination of numbers or
            // letters and has a length greater than 4
            if (userString.length() > 4 && userString.matches("(?i)[a-z0-9]+")) {

                isValid = true;
            }
        }

        return isValid;
    }

    /**
     * Checks a String to determine if it is null, empty, or whitespace
     * 
     * @param s The String to check.
     * @return True if the String is not null, empty, or whitespace. False if any
     *         conditions are met.
     */
    public static boolean isNotNullEmptyOrWhitespaceString(String s) {

        boolean isValid = false;

        if (null != s) {
            if (!s.equals("")) {
                if (s.replaceAll("\\s", "").length() > 0) {
                    isValid = true;
                }
            }
        }

        return isValid;
    }

    /**
     * Checks a String to determine if it is null, empty, or whitespace.
     * 
     * @param s The String to check.
     * @return True if it matches the condition. False if it does not.
     */
    public static boolean isNullEmptyOrWhitespaceString(String s) {

        boolean isNullEmptyOrWhitespaceString = false;

        if (null == s) {

            isNullEmptyOrWhitespaceString = true;
        } else {
            if (s.equals("")) {
                isNullEmptyOrWhitespaceString = true;
            } else {
                if (!(s.replaceAll("\\s", "").length() > 0)) {
                    isNullEmptyOrWhitespaceString = true;
                }
            }
        }

        return isNullEmptyOrWhitespaceString;
    }
}