package com.novacroft.nemo.tfl.common.util;

import static org.apache.commons.lang3.StringUtils.isNotBlank;
import org.joda.time.LocalDate;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;

/**
 * Payment card utilities
 */
public final class PaymentCardUtil {
	
	private static final int MONTH_LOWER_LIMIT = 0;
	private static final int MONTH_UPPER_LIMIT = 2;
	private static final int YEAR_LOWER_LIMIT = 3;
	private static final int YEAR_UPPER_LIMIT = 7;

    private PaymentCardUtil() {
    }

    public static String createDisplayName(PaymentCardDTO paymentCardDTO) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(paymentCardDTO.getObfuscatedPrimaryAccountNumber());
        if (isNotBlank(paymentCardDTO.getNickName())) {
            stringBuilder.append(" (");
            stringBuilder.append(paymentCardDTO.getNickName());
            stringBuilder.append(")");
        }
        return stringBuilder.toString();
    }

    public static Boolean isCardExpired(PaymentCardDTO paymentCardDTO) {
        LocalDate now = new LocalDate().now();
        Integer expiryMonth = Integer.valueOf(paymentCardDTO.getExpiryDate().substring(MONTH_LOWER_LIMIT, MONTH_UPPER_LIMIT));
        Integer expiryYear = Integer.valueOf(paymentCardDTO.getExpiryDate().substring(YEAR_LOWER_LIMIT, YEAR_UPPER_LIMIT));
        LocalDate expiresOn = new LocalDate(expiryYear, expiryMonth, 1).dayOfMonth().withMaximumValue();
        return expiresOn.isBefore(now);
    }

    public static boolean expiresInNMonths(int months, PaymentCardDTO paymentCardDTO) {
        LocalDate now = new LocalDate().now();
        Integer expiryMonth = Integer.valueOf(paymentCardDTO.getExpiryDate().substring(MONTH_LOWER_LIMIT, MONTH_UPPER_LIMIT));
        Integer expiryYear = Integer.valueOf(paymentCardDTO.getExpiryDate().substring(YEAR_LOWER_LIMIT, YEAR_UPPER_LIMIT));
        LocalDate expiresOn = new LocalDate(expiryYear, expiryMonth, 1).dayOfMonth().withMaximumValue();
        return expiresOn.isBefore(now.plusMonths(months));
    }
}
