package com.novacroft.nemo.common.validator;

import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_DATE_PATTERN;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_EMAIL;
import static com.novacroft.nemo.common.constant.CommonContentCode.INVALID_PATTERN;
import static com.novacroft.nemo.common.constant.CommonContentCode.MANDATORY_FIELD_EMPTY;
import static com.novacroft.nemo.common.constant.CommonContentCode.MANDATORY_FIELD_EMPTY_USING_AN;
import static com.novacroft.nemo.common.constant.CommonContentCode.MANDATORY_SELECT_FIELD_EMPTY;
import static com.novacroft.nemo.common.constant.CommonContentCode.VALUE_ABOVE_MAXIMUM;
import static com.novacroft.nemo.common.constant.CommonContentCode.VALUE_BELOW_MINIMUM;
import static com.novacroft.nemo.common.constant.CommonContentCode.WEB_CREDIT_NOT_AVAILABLE;
import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithHtmlCurrencySymbol;
import static org.springframework.validation.ValidationUtils.rejectIfEmptyOrWhitespace;

import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.novacroft.nemo.common.utils.DateUtil;

/**
 * Base implementation for a validator
 */
public abstract class BaseValidator implements Validator {
    protected static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    protected static final String PHONE_PATTERN = "^\\+?[0-9]{11,}+$";
    protected static final String USERNAME_PATTERN = "^[a-zA-Z0-9]+$|" + EMAIL_PATTERN;
    protected static final String NUMERIC_PATTERN = "^[0-9]+$";
    protected static final String ALPHABETIC_PATTERN = "^[a-zA-Z]+$";
    protected static final String ALPHABETIC_WHITE_SPACE_PATTERN = "^[a-zA-Z\\s]+$";
    protected static final String ALPHABETIC_WITH_WHITE_SPACE_PATTERN = "^(?!\\s*$)[a-zA-Z\\s]+$";
    protected static final String ALPHABETIC_WITH_HYPHEN_WHITE_SPACE_PATTERN = "^(?!\\s*$)[a-zA-Z-\\s]+$";
    protected static final String ALPHANUMERIC_PATTERN = "^[a-zA-Z0-9]+$";
    protected static final String ALPHANUMERIC_WITH_WHITE_SPACE_PATTERN = "^(?!\\s*$)[a-zA-Z0-9\\s,]+$";
    protected static final String ALPHANUMERIC_WITH_CURENCY_WHITE_SPACE_PATTERN = "^(?!\\s*$)[a-zA-Z0-9\\s£,.]+$";
    protected static final String SHORT_DATE_PATTERN_PARTIAL_YEAR = "^(0?[1-9]|[12][0-9]|3[01])[/](0?[1-9]|1[012])[/]\\d{2}$";
    protected static final String SHORT_DATE_PATTERN = "^(0?[1-9]|[12][0-9]|3[01])[/](0?[1-9]|1[012])[/]\\d{4}$";
    protected static final String DECIMAL_PATTERN = "^[0-9]+(\\.[0-9]{1,2}?)?$";
    protected static final Integer ZERO = 0;
    public static final String ISO_UK_CODE = "GB";
    public static final Integer MINIMUM_FIELD_LENGTH = 2;

    protected boolean hasNoFieldError(Errors errors, String field) {
        return !errors.hasFieldErrors(field);
    }

    public void rejectIfMandatoryFieldEmpty(Errors errors, String field) {
        rejectIfEmptyOrWhitespace(errors, field, MANDATORY_FIELD_EMPTY.errorCode(), new Object[] { new DefaultMessageSourceResolvable(field
                        + ".label") });
    }

    public void rejectIfMandatoryFieldEmptyUsingAn(Errors errors, String field) {
        rejectIfEmptyOrWhitespace(errors, field, MANDATORY_FIELD_EMPTY_USING_AN.errorCode(), new Object[] { new DefaultMessageSourceResolvable(field
                        + ".label") });
    }

    public void rejectIfMandatorySelectFieldEmpty(Errors errors, String field) {
        rejectIfEmptyOrWhitespace(errors, field, MANDATORY_SELECT_FIELD_EMPTY.errorCode(), new Object[] { new DefaultMessageSourceResolvable(field
                        + ".label") });
    }

    public void rejectIfPatternNotMatched(Errors errors, String field, String errorCode, String pattern) {
        rejectIfPatternNotMatched(errors, field, errorCode, pattern,(String) errors.getFieldValue(field));
    }

    public void rejectIfPatternNotMatched(Errors errors, String field, String errorCode, String pattern, String value) {
        if (!isPatternMatched(value, pattern)) {
            errors.rejectValue(field, errorCode);
        }
    }

    public boolean isPatternMatched(String value, String pattern) {
        if (value == null || pattern == null) {
            return false;
        }
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(value);
        return m.matches();
    }

    public void rejectIfNotValidEmail(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_EMAIL.errorCode(), EMAIL_PATTERN);
    }

    public void rejectIfNotValidPhone(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), PHONE_PATTERN);
    }

    public void rejectIfNotValidUsername(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), USERNAME_PATTERN);
    }

    public void rejectIfNotNumeric(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), NUMERIC_PATTERN);
    }

    public void rejectIfNotAlphabetic(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), ALPHABETIC_PATTERN);
    }

    public void rejectIfNotAlphabeticOrWhiteSpace(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), ALPHABETIC_WHITE_SPACE_PATTERN);
    }

    public void rejectIfNotAlphabeticWithWhiteSpaceBetweenWordsOnly(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), ALPHABETIC_WITH_WHITE_SPACE_PATTERN);
    }

    public void rejectIfNotAlphabeticWithHyphenWhiteSpaceBetweenWordsOnly(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), ALPHABETIC_WITH_HYPHEN_WHITE_SPACE_PATTERN);
    }

    public void rejectIfNotAlphaNumeric(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), ALPHANUMERIC_PATTERN);
    }

    public void rejectIfNotAlphaNumericWithWhiteSpaceBetweenWordsOnly(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), ALPHANUMERIC_WITH_WHITE_SPACE_PATTERN);
    }

    public void rejectIfNotAlphaNumericWithCurrencyWhiteSpaceBetweenWordsOnly(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), ALPHANUMERIC_WITH_CURENCY_WHITE_SPACE_PATTERN);
    }

    public void rejectIfNotPartialYearShortDate(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_DATE_PATTERN.errorCode(), SHORT_DATE_PATTERN_PARTIAL_YEAR);
    }

    public void rejectIfNotShortDate(Errors errors, String field) {
        String value = null;
        if (errors.getFieldValue(field) instanceof Date) {
            value = DateUtil.formatDate((Date) errors.getFieldValue(field));
        }
        else {
            value = (String) errors.getFieldValue(field);
        }
        rejectIfPatternNotMatched(errors, field, INVALID_DATE_PATTERN.errorCode(), SHORT_DATE_PATTERN, value);
    }

    public void rejectIfNotDecimal(Errors errors, String field) {
        rejectIfPatternNotMatched(errors, field, INVALID_PATTERN.errorCode(), DECIMAL_PATTERN);
    }

    public void rejectIfPenceAmountBelowMinimumValue(Errors errors, String fieldName, Integer penceAmount, Integer minimumPenceAmount) {
        if (penceAmount < minimumPenceAmount) {
            errors.rejectValue(fieldName, VALUE_BELOW_MINIMUM.errorCode(), new String[] { formatPenceWithHtmlCurrencySymbol(minimumPenceAmount) },
                            null);
        }
    }

    public void rejectIfPenceAmountAboveMaximumValue(Errors errors, String fieldName, Integer penceAmount, Integer maximumPenceAmount) {
        if (penceAmount > maximumPenceAmount) {
            errors.rejectValue(fieldName, VALUE_ABOVE_MAXIMUM.errorCode(), new String[] { formatPenceWithHtmlCurrencySymbol(maximumPenceAmount) },
                            null);
        }
    }

    public void rejectIfInvalidDate(Errors errors, String field, String input) {
        Date date = DateUtil.parse(input, "", TimeZone.getDefault());
        if (date == null) {
            errors.rejectValue(field, INVALID_DATE_PATTERN.errorCode());
        }
    }

    public void rejectIfPenceAmountIsZero(Errors errors, String fieldName, Integer wcApplyPenceAmount, Integer wcAvaialblePenceAmount) {
        if (ZERO.equals(wcAvaialblePenceAmount) && wcApplyPenceAmount > ZERO) {
            errors.rejectValue(fieldName, WEB_CREDIT_NOT_AVAILABLE.errorCode(), null, null);
        }
    }
}
