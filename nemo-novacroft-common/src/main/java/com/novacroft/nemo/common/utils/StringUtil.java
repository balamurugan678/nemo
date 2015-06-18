package com.novacroft.nemo.common.utils;

import java.text.DecimalFormat;

public final class StringUtil {

    private static final double ONEHUNDREDDECIMAL = 100.00;
    public static final String EMPTY_STRING = "";
    public static final char SPACE = ' ';
    public static final String HYPHEN = "-";
    public static final String COLON = ":";
    public static final String SINGLE_QUOTE = "'";
    public static final String AND = "and";
    public static final String WHITE_SPACE = " ";
    public static final String SEMI_COLON = ";";
    public static final String PATH_SEPARATOR = "/";
    public static final String QUESTION_MARK = "?";
    public static final String COMMA = ",";
    
    private StringUtil() {
    }

    /**
     * Upper case the first character in the String.
     *
     * @param text To be manipulated
     * @return String Converted String
     */
    public static String initCap(String text) {
        return text.substring(0, 1).toUpperCase() + text.substring(1, text.length()).toLowerCase();
    }

    /**
     * Pass in a String[] and it will return a separated list.
     *
     * @param array     String[] containing items to list.
     * @param separator Separator to use.
     * @return Separated list.
     */
    public static String arrayToString(String[] array, String separator) {
        String list = "";
        int count = 0;
        for (String item : array) {
            if (count + 1 == array.length) {
                list += item;
            } else {
                list += item + separator;
            }
            count++;
        }
        return list;
    }

    /**
     * Pass in a String[] and it will return a separated list. This will use a default separator of ','
     *
     * @param array String[] containing items to list.
     * @return Separated list.
     */
    public static String arrayToString(String[] array) {
        return arrayToString(array, ", ");
    }

    /**
     * Return a count of the number of times a character appears in a string
     *
     * @param value     String to check for character
     * @param character Character that is being counted.
     * @return Number of occurrences
     */
    public static int countOccurrences(String value, char character) {
        int count = 0;
        for (int i = 0; i < value.length(); i++) {
            if (value.charAt(i) == character) {
                count++;
            }
        }
        return count;
    }

    /**
     * Returns a specific delimited field from a string.
     *
     * @param string    the string to be scanned.
     * @param delimiter the delimiter used to delimit the strings.
     * @param occurance the number of the delimited field to return.
     * @return the field if found, otherwise an empty string ("").
     */
    public static String field(String string, char delimiter, int occurance) {
        int index1;
        if (occurance > 1) {
            index1 = index(string, delimiter, occurance - 1);
            if (index1 != -1) {
                index1++;
            } else {
                index1 = 0;
            }
        } else {
            index1 = 0;
        }
        String result = string.substring(index1);
        int index2 = result.indexOf(delimiter);
        if (index2 != -1) {
            result = result.substring(0, index2);
        }
        return result;
    }

    /**
     * Returns the location of a character within a string.
     *
     * @param string    the string to search.
     * @param c         the character to search for.
     * @param occurance the occurrence of the character to look for.
     * @return the location of the character if found or -1 if the search failed.
     */
    public static int index(String string, char c, int occurrence) {
        int characterLocation = occurrence;
        int index = string.indexOf(c);
        while (index != -1 && characterLocation > 1) {
            index = string.indexOf(c, index + 1);
            characterLocation--;
        }
        return index;
    }

    /**
     * Convert string into mixed case.
     *
     * @param s the string to convert.
     * @return the string in mixed case.
     */
    public static String mixCase(String s) {
        String[] words = s.split(" ");
        StringBuilder newWords = new StringBuilder();
        for (String word : words) {
            String newWord = "";
            if (!word.equals("")) {
                int n = 0;
                boolean found = false;
                while (n < word.length() && !found) {
                    char c = word.charAt(n);
                    if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                        found = true;
                    } else {
                        newWord += c;
                        n++;
                    }
                }
                boolean upper = true;
                while (n < word.length()) {
                    char c = word.charAt(n);
                    if (upper) {
                        newWord += Character.toUpperCase(c);
                    } else {
                        newWord += Character.toLowerCase(c);
                    }
                    if (c != '\'') {
                        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z')) {
                            upper = false;
                        } else {
                            upper = true;
                        }
                    }
                    n++;
                }
            }
            if (newWords.length() > 0) {
                newWords.append(' ');
            }
            newWords.append(newWord);
        }
        return newWords.toString();
    }

    /**
     * Check to see if a <code>String<code> contains a valid integer number.
     *
     * @param number the number to check.
     * @return <code>true</code> if the string is a valid integer number, <code>false</code> otherwise.
     */
    public static boolean isInteger(String number) {
        try {
            Integer.parseInt(number);
            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
    }

    /**
     * Check to see if a <code>String</code> consists entirely of alphabetic characters.
     *
     * @param string the String to check.
     * @return <code>true</code> if the character is alphabetic, <code>false</code> otherwise.
     */
    public static boolean isAlphabetic(String string) {
        for (int n = 0; n < string.length(); n++) {
            if (!Character.isLetter(string.charAt(n))) {
                return false;
            }
        }
        return true;

    }

    public static boolean isEmpty(String str) {
        return (str == null) || (str.length() == 0);
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }

    public static boolean isBlank(String str) {
        int strLen;
        if ((str == null) || ((strLen = str.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; i++) {
            if (!Character.isWhitespace(str.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    public static String trim(String str) {
        return str == null ? null : str.trim();
    }

    public static String isEmptyReturnDefault(String value, String defaultValue) {
        if (isEmpty(value)) {
            return defaultValue;
        }
        return value;
    }

    public static String formatPenceToCurrency(Integer pence) {
        DecimalFormat myFormatter = new DecimalFormat("£###,##0.00");
        return myFormatter.format(pence / ONEHUNDREDDECIMAL);
    }

    /**
     * Return the left hand part of a string.
     *
     * @param string the string to extract from.
     * @param length the length of the string to return, negative values will return the string length minus the value supplied.
     * @return the string extracted.
     */
    public static String left(String string, int length) {
        if (Math.abs(length) > string.length()) {
            return string;
        } else {
            return length < 0 ? string.substring(0, string.length() + length) : string.substring(0, length);
        }
    }

    /**
     * Return the right hand part of a string.
     *
     * @param string the string to extract from.
     * @param length the length of the string to return, negative values will return the string length minus the value supplied.
     * @return the string extracted.
     */
    public static String right(String string, int length) {
        if (Math.abs(length) > string.length()) {
            return string;
        } else {
            return length < 0 ? string.substring(-length) : string.substring(string.length() - length);
        }
    }

    /**
     * Return the mid part of a string.
     *
     * @param targetString      the string to extract from.
     * @param startingCharacter the starting character to return from (0 is first character).
     * @param returnlength      the length of the string to return.
     * @return the string extracted.
     */
    public static String mid(String targetString, int startingCharacter, int returnlength) {
        int start = startingCharacter;
        int length = returnlength;
        String string = targetString;
        if (start < 0 || start >= string.length()) {
            return "";
        } else {
            if (length < 0) {
                length = string.length() - start + length + 1;
            } else {
                if (start + length >= string.length()) {
                    length = string.length() - start;
                }
            }
            return string.substring(start, start + length);
        }
    }

    public static String replicate(char c, int x) {
        int n = x;
        StringBuffer sb = new StringBuffer();
        while (n > 0) {
            sb.append(c);
            n--;
        }
        return sb.toString();
    }

    public static String replicate(String string, int x) {
        int n = x;
        StringBuffer sb = new StringBuffer();
        while (n > 0) {
            sb.append(string);
            n--;
        }
        return sb.toString();
    }

    public static String convertNulltoEmptyString(String element) {
        if (null == element) {
            return " ";
        }
        return element;
    }

    public static String extractFirstWord(String value) {
        if (isBlank(value)) {
            return EMPTY_STRING;
        }
        if (isOneWord(value)) {
            return value.trim();
        }
        return value.trim().substring(0, value.trim().indexOf(SPACE)).trim();
    }

    public static String extractAfterFirstWord(String value) {
        if (isBlank(value) || isOneWord(value)) {
            return EMPTY_STRING;
        }
        return value.trim().substring(value.trim().indexOf(SPACE)).trim();
    }

    protected static boolean isOneWord(String value) {
        return isNotBlank(value) && value.trim().indexOf(SPACE) == -1;
    }
}
