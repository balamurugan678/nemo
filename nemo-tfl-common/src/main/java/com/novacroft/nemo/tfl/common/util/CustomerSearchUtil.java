package com.novacroft.nemo.tfl.common.util;

import org.apache.commons.lang3.StringUtils;

import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;

public final class CustomerSearchUtil {
    public static final String CUSTOMER_ID_PARAMETER_NAME = "customerId";
    public static final String CARD_NUMBER_PARAMETER_NAME = "cardNumber";
    public static final String FIRST_NAME_PARAMETER_NAME = "firstName";
    public static final String FIRST_NAME_METAPHONE_PARAMETER_NAME = "firstNameMetaphone";
    public static final String LAST_NAME_PARAMETER_NAME = "lastName";
    public static final String LAST_NAME_METAPHONE_PARAMETER_NAME = "lastNameMetaphone";
    public static final String POSTCODE_PARAMETER_NAME = "postcode";
    public static final String EMAIL_PARAMETER_NAME = "email";
    public static final String USERNAME_PARAMETER_NAME = "userName";

    public static Boolean isExactMatch(CustomerSearchArgumentsDTO arguments) {
        return arguments.getUseExactMatch();
    }

    public static Boolean isNotExactMatch(CustomerSearchArgumentsDTO arguments) {
        return !isExactMatch(arguments);
    }

    public static Boolean isExactMatchAndFirstNameNotNull(CustomerSearchArgumentsDTO arguments) {
        return arguments.getUseExactMatch() && StringUtils.isNotBlank(arguments.getFirstName());
    }

    public static Boolean isNotExactMatchAndFirstNameNotNull(CustomerSearchArgumentsDTO arguments) {
        return !arguments.getUseExactMatch() && StringUtils.isNotBlank(arguments.getFirstName());
    }

    public static Boolean isExactMatchAndLastNameNotNull(CustomerSearchArgumentsDTO arguments) {
        return arguments.getUseExactMatch() && StringUtils.isNotBlank(arguments.getLastName());
    }

    public static Boolean isNotExactMatchAndLastNameNotNull(CustomerSearchArgumentsDTO arguments) {
        return !arguments.getUseExactMatch() && StringUtils.isNotBlank(arguments.getLastName());
    }

    public static Boolean isCustomerIdNotNull(CustomerSearchArgumentsDTO arguments) {
        return (null != arguments.getCustomerId());
    }

    public static Boolean isFirstNameNotNull(CustomerSearchArgumentsDTO arguments) {
        return StringUtils.isNotBlank(arguments.getFirstName());
    }

    public static Boolean isLastNameNotNull(CustomerSearchArgumentsDTO arguments) {
        return StringUtils.isNotBlank(arguments.getLastName());
    }

    public static Boolean isCardNumberNotNull(CustomerSearchArgumentsDTO arguments) {
        return StringUtils.isNotBlank(arguments.getCardNumber());
    }

    public static Boolean isPostcodeNotNull(CustomerSearchArgumentsDTO arguments) {
        return StringUtils.isNotBlank(arguments.getPostcode());
    }

    public static Boolean isEmailNotNull(CustomerSearchArgumentsDTO arguments) {
        return StringUtils.isNotBlank(arguments.getEmail());
    }

    public static Boolean isUserNameNotNull(CustomerSearchArgumentsDTO arguments) {
        return StringUtils.isNotBlank(arguments.getUserName());
    }

    public static String stripSpaces(String value) {
        return value.replaceAll("\\s", StringUtils.EMPTY);
    }

    private CustomerSearchUtil() {
    }
}
