package com.novacroft.nemo.common.utils;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

/**
 * Currency utilities
 */
public final class CurrencyUtil {
    public static final int PENCE_IN_POUND = 100;
    protected static final String MONEY_FORMAT_PATTERN = "###,###,###,##0.00;-###,###,###,##0.00";
    protected static final String POUND_HTML_SYMBOL = "&pound;";
    protected static final String COMMA = ",";
    protected static final int MINUS_ONE = -1;
    protected static final String POUNDS_AND_TWO_PENCE_DECIMAL_PATTERN = "(\\d)*(\\.\\d{2})";
    protected static final String POUNDS_AND_ONE_PENCE_DECIMAL_PATTERN = "(\\d)*(\\.\\d{1})";
    protected static final String ZERO = "0";
    protected static final String DOUBLE_ZERO = "00";
    protected static final String DOT = ".";

    public static Float convertPenceToPounds(Integer amountInPence) {
        return (amountInPence != null) ? convertPenceToPounds((float) amountInPence) : null;
    }

    public static Float convertPenceToPounds(Float amountInPence) {
        return (amountInPence != null) ? (amountInPence / PENCE_IN_POUND) : null;
    }

    public static Integer convertPoundsToPence(Float amountInPoundsAndPence) {
        return (amountInPoundsAndPence != null) ? (int) (amountInPoundsAndPence * PENCE_IN_POUND) : null;
    }

    public static String formatPoundsAndPenceWithoutCurrencySymbolOrCommas(Float amountInPoundsAndPence) {
        return StringUtils
                .remove(formatPoundsAndPenceWithPattern(amountInPoundsAndPence, getMoneyFormatPatternWithoutCommas()), COMMA);
    }

    public static String formatPoundsAndPenceWithoutCurrencySymbol(Float amountInPoundsAndPence) {
        return formatPoundsAndPenceWithPatternWithoutCommas(amountInPoundsAndPence, MONEY_FORMAT_PATTERN);
    }

    public static String formatPenceWithoutCurrencySymbol(Integer amountInPence) {
        return formatPoundsAndPenceWithoutCurrencySymbol(convertPenceToPounds(amountInPence));
    }

    public static String formatPenceWithoutCurrencySymbolOrCommas(Integer amountInPence) {
        return formatPoundsAndPenceWithoutCurrencySymbolOrCommas(convertPenceToPounds(amountInPence));
    }

    public static String formatPenceWithHtmlCurrencySymbol(Integer amountInPence) {
        return POUND_HTML_SYMBOL + formatPoundsAndPenceWithoutCurrencySymbol(convertPenceToPounds(amountInPence));
    }

    public static Integer convertPoundsAndPenceAsStringToPenceAsInteger(String value) {
        try {
            DecimalFormat decimalFormat =
                    (DecimalFormat) DecimalFormat.getInstance(LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE);
            return (int) (decimalFormat.parse(value).floatValue() * PENCE_IN_POUND);
        } catch (ParseException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }
    
    public static Integer convertPoundsAndPenceToPenceAsInteger(String value) {
        boolean matchesFormat = Pattern.matches(POUNDS_AND_TWO_PENCE_DECIMAL_PATTERN, value);
        if(matchesFormat){
            return Integer.valueOf(value.replace(DOT, ""));
        } else if( !matchesFormat && !value.contains(DOT)){
            return Integer.valueOf(value.concat(DOUBLE_ZERO));
        } else if (!matchesFormat && Pattern.matches(POUNDS_AND_ONE_PENCE_DECIMAL_PATTERN, value)){
            return Integer.valueOf(value.replace(DOT, "").concat(ZERO));
        } else {
            return convertPoundsAndPenceAsStringToPenceAsInteger(value);
        }
    }
    
    public static Long convertPoundsAndPenceAsStringToPenceAsLong(String value) {
        try {
            DecimalFormat decimalFormat =
                    (DecimalFormat) DecimalFormat.getInstance(LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE);
            return (long) (decimalFormat.parse(value).floatValue() * PENCE_IN_POUND);
        } catch (ParseException e) {
            throw new ApplicationServiceException(e.getMessage(), e);
        }
    }

    public static Float reverseSign(Float value) {
        return value * MINUS_ONE;
    }

    public static Integer reverseSign(Integer value) {
        return value * MINUS_ONE;
    }

    protected static String formatPoundsAndPenceWithPattern(Float amountInPoundsAndPence, String formatPattern) {
        if (amountInPoundsAndPence == null) {
            return EMPTY_STRING;
        }
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getInstance(LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE);
        decimalFormat.applyPattern(formatPattern);
        return decimalFormat.format(amountInPoundsAndPence);
    }

    protected static String formatPoundsAndPenceWithPatternWithoutCommas(Float amountInPoundsAndPence, String formatPattern) {
        return StringUtils.remove(formatPoundsAndPenceWithPattern(amountInPoundsAndPence, formatPattern), COMMA);
    }

    protected static String getMoneyFormatPatternWithoutCommas() {
        return MONEY_FORMAT_PATTERN.replaceAll(COMMA, EMPTY_STRING);
    }

    private CurrencyUtil() {
    }
}
