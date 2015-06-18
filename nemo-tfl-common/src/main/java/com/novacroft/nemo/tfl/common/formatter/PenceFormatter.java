package com.novacroft.nemo.tfl.common.formatter;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.Formatter;

public class PenceFormatter implements Formatter<Integer> {
    private static final Logger logger = LoggerFactory.getLogger(PenceFormatter.class);
    protected static final int PENCE_IN_POUND = 100;
    protected static final String ZERO_IN_STRING = "0.00";
    protected static final String BLANK_STRING = "";
    protected static final int MAX_LENGTH_OF_AMOUNT_IN_POUNDS=3;
    protected static final int PENCE_IN_POUND_FOR_TRI_DECIMAL = 1000;
    protected static final int PENCE_OFFSET = 0;    
    protected static final String PENCE_FORMATTER_GENERIC_ERROR_MESSAGE =" Enter a numeric monetary value with 2 decimals" ;


    @Override
    public String print(Integer amountInPence, Locale locale) {
        // Ignoring the locale as the method is not intended to display currency symbol
        if (amountInPence != null) {
            if (amountInPence != 0) {
                return convertPenceToPounds(amountInPence);
            } else {
                return ZERO_IN_STRING;
            }
        } else {
            return BLANK_STRING;
        }
    }

    public static final String convertPenceToPounds(Integer amountInPence) {
        return (amountInPence != null) ? String.format("%.2f", ((float) amountInPence / PENCE_IN_POUND)) : "0.00";
    }

    @Override
    public Integer parse(String amountInPounds, Locale locale) throws ParseException {
        // Ignoring the locale as the method is not intended to convert currency symbol
        if (amountInPounds.length() == 0) {
            return null;
        }
        BigDecimal amountInPoundsForDecimalCheck = new BigDecimal(amountInPounds);
        if(amountInPoundsForDecimalCheck.scale()>=MAX_LENGTH_OF_AMOUNT_IN_POUNDS){
        	throw new ParseException(PENCE_FORMATTER_GENERIC_ERROR_MESSAGE, PENCE_OFFSET);
        }
        return convertPoundsToPence(amountInPounds);
    }

    public static final Integer convertPoundsToPence(String amountInPounds) {
        try {
        		Double amountInPence =  (double) (PENCE_IN_POUND * Double.valueOf(amountInPounds));
        		return (int) Math.round(amountInPence);
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }
}
