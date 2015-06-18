package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Utilities for formatting addresses.
 */
public final class AddressFormatUtil {
    /* Single house number could be a number or a number followed by a letter */
    protected static final String SINGLE_HOUSE_NUMBER_PATTERN = "[0-9]+[a-zA-Z]?";
    /* House number range could be two single numbers separated with a dash */
    protected static final String HOUSE_NUMBER_RANGE_PATTERN =
            SINGLE_HOUSE_NUMBER_PATTERN + " *- *" + SINGLE_HOUSE_NUMBER_PATTERN;
    protected static final String SPACE = " ";
    protected static final String COMMA = ",";

    public static List<String> formatNameAndAddress(String title, String firstName, String initials, String lastName,
                                                    String houseNameNumber, String street, String town, String postcode,
                                                    CountryDTO country) {
        List<String> nameAndAddressLines = new ArrayList<String>();
        nameAndAddressLines.add(formatName(title, firstName, initials, lastName));
        nameAndAddressLines.addAll(formatAddress(houseNameNumber, street, town, postcode, country));
        return nameAndAddressLines;
    }

    public static String formatName(CustomerDTO customer) {
        StringBuilder stringBuilder = new StringBuilder();
        appendPrefixAndValue(stringBuilder, customer.getTitle(), SPACE);
        appendPrefixAndValue(stringBuilder, customer.getFirstName(), SPACE);
        appendPrefixAndValue(stringBuilder, customer.getInitials(), SPACE);
        appendPrefixAndValue(stringBuilder, customer.getLastName(), SPACE);
        return stringBuilder.toString();
    }

    public static String formatName(String title, String firstName, String initials, String lastName) {
        StringBuilder stringBuilder = new StringBuilder();
        appendPrefixAndValue(stringBuilder, title, SPACE);
        appendPrefixAndValue(stringBuilder, firstName, SPACE);
        appendPrefixAndValue(stringBuilder, initials, SPACE);
        appendPrefixAndValue(stringBuilder, lastName, SPACE);
        return stringBuilder.toString();
    }

    public static String formatName(String firstName, String initials, String lastName) {
        StringBuilder stringBuilder = new StringBuilder();
        appendPrefixAndValue(stringBuilder, firstName, SPACE);
        appendPrefixAndValue(stringBuilder, initials, SPACE);
        appendPrefixAndValue(stringBuilder, lastName, SPACE);
        return stringBuilder.toString();
    }

    public static String formatName(String firstName, String lastName) {
        StringBuilder stringBuilder = new StringBuilder();
        appendPrefixAndValue(stringBuilder, firstName, SPACE);
        appendPrefixAndValue(stringBuilder, lastName, SPACE);
        return stringBuilder.toString();
    }
    
    public static List<String> formatAddress(String houseNameNumber, String street, String town, String postcode,
                    CountryDTO country) {
        return formatAddress(houseNameNumber, street, town, postcode, (country == null ? null : country.getName()));
    }
    
    public static List<String> formatAddress(String houseNameNumber, String street, String town, String postcode,
                    String country) {
        List<String> addressLines = new ArrayList<String>();
        addLine1ToAddress(addressLines, houseNameNumber, street);
        addLine2ToAddress(addressLines, houseNameNumber, street);
        addLine3ToAddress(addressLines, town, postcode);
        addLine4ToAddress(addressLines, country);
        return addressLines;
    }

    protected static void addLine1ToAddress(List<String> formattedAddress, String houseNameNumber, String street) {
        String line = formatLine1(houseNameNumber, street);
        if (isNotBlank(line)) {
            formattedAddress.add(line);
        }
    }

    protected static void addLine2ToAddress(List<String> formattedAddress, String houseNameNumber, String street) {
        String line = formatLine2(houseNameNumber, street);
        if (isNotBlank(line)) {
            formattedAddress.add(line);
        }
    }

    protected static void addLine3ToAddress(List<String> formattedAddress, String town, String postcode) {
        StringBuilder stringBuilder = new StringBuilder();
        if (isNotBlank(town)) {
            stringBuilder.append(town);
        }
        if (isNotBlank(postcode)) {
            stringBuilder.append(isNotBlank(town) ? COMMA + SPACE + postcode : postcode);
        }
        formattedAddress.add(stringBuilder.toString());
    }

    protected static void addLine4ToAddress(List<String> formattedAddress, String country) {
        if (isNotBlank(country)) {
            formattedAddress.add(country);
        }
    }

    /**
     * Append a prefix and value to a StringBuilder.  The prefix is only appended if the StringBuilder is not empty.
     */
    public static void appendPrefixAndValue(StringBuilder stringBuilder, String value, String prefix) {
        if (isNotBlank(stringBuilder.toString()) && isNotBlank(value)) {
            stringBuilder.append(prefix);
        }
        if (isNotBlank(value)) {
            stringBuilder.append(value);
        }
    }

    /**
     * If <code>houseNameNumber</code> is a number then output house number concatenated with street.
     * If <code>houseNameNumber</code> is a name then output house name.
     */
    public static String formatLine1(String houseNameNumber, String street) {
        StringBuilder formattedLine1 = new StringBuilder();
        if (isNotBlank(houseNameNumber)) {
            formattedLine1.append(houseNameNumber);
            if (isHouseNumber(houseNameNumber) && isNotBlank(street)) {
                formattedLine1.append(SPACE);
                formattedLine1.append(street);
            }
        } else if (isNotBlank(street)) {
            formattedLine1.append(street);
        }
        return formattedLine1.toString();
    }

    /**
     * If <code>houseNameNumber</code> is a name then output street, else output nothing.
     */
    public static String formatLine2(String houseNameNumber, String street) {
        if (isNotBlank(houseNameNumber) && isNotHouseNumber(houseNameNumber) && isNotBlank(street)) {
            return street;
        }
        return StringUtil.EMPTY_STRING;
    }

    /**
     * If <code>houseNameNumber</code> is a number then output it.
     */
    public static String formatHouseNumber(String houseNameNumber) {
        if (isHouseNumber(houseNameNumber)) {
            return houseNameNumber;
        } else {
            return StringUtil.EMPTY_STRING;
        }
    }

    /**
     * If <code>houseNameNumber</code> is a name then output it, otherwise output the street.
     */
    public static String formatLine1WithSeparateHouseNumber(String houseNameNumber, String street) {
        return isNotHouseNumber(houseNameNumber) ? houseNameNumber : street;
    }

    /**
     * If <code>houseNameNumber</code> is a name then output the street.
     */
    public static String formatLine2WithSeparateHouseNumber(String houseNameNumber, String street) {
        return isNotHouseNumber(houseNameNumber) ? street : StringUtil.EMPTY_STRING;
    }

    public static boolean isHouseNumber(String value) {
        return isNotBlank(value) && (value.matches(SINGLE_HOUSE_NUMBER_PATTERN) || value.matches(HOUSE_NUMBER_RANGE_PATTERN));
    }

    public static boolean isNotHouseNumber(String value) {
        return !isHouseNumber(value);
    }
    
    public static String formatHouseNameNumber(String houseNumber, String houseName) {
        boolean isNumberBlank = StringUtil.isBlank(houseNumber);
        boolean isNameBlank = StringUtil.isBlank(houseName);
        
        if (isNumberBlank && isNameBlank) {
            return null;
        }
        else if (!isNumberBlank && !isNameBlank) {
            return houseNumber + SPACE + houseName;
        }
        else {
            return isNumberBlank ? houseName : houseNumber;
        }
    }
    
    public static String[] extractHouseNameNumber(String houseNameNumber) {
        String houseNumber = "";
        String houseName = "";
        
        String[] parts = StringUtils.split(houseNameNumber);
        if (isHouseNumber(parts[0])) {
            houseNumber = parts[0];
            if (parts.length > 1) {
                houseName = StringUtils.join(parts, SPACE, 1, parts.length);
            }
        }
        else {
            houseName = houseNameNumber;
        }
        
        return new String[]{houseNumber, houseName};
    }
    
    private AddressFormatUtil() {}
}
