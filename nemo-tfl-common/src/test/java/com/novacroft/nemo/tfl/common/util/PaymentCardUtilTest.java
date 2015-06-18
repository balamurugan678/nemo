package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.transfer.PaymentCardDTO;
import org.joda.time.LocalDate;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_NICK_NAME_1;
import static com.novacroft.nemo.test_support.PaymentCardTestUtil.TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentCardUtilTest {
    private static final String CARD_EXPIRY_DATE_PATTERN = "MM-yyyy";
    private PaymentCardDTO mockPaymentCardDTO;

    @Before
    public void setUp() {
        this.mockPaymentCardDTO = mock(PaymentCardDTO.class);
    }

    @Test
    public void shouldCreateDisplayNameWithNickName() {
        when(this.mockPaymentCardDTO.getObfuscatedPrimaryAccountNumber()).thenReturn(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1);
        when(this.mockPaymentCardDTO.getNickName()).thenReturn(TEST_NICK_NAME_1);
        String result = PaymentCardUtil.createDisplayName(this.mockPaymentCardDTO);
        assertTrue(result.contains(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1));
        assertTrue(result.contains(TEST_NICK_NAME_1));
    }

    @Test
    public void shouldCreateDisplayNameWithoutNickName() {
        when(this.mockPaymentCardDTO.getObfuscatedPrimaryAccountNumber()).thenReturn(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1);
        when(this.mockPaymentCardDTO.getNickName()).thenReturn(EMPTY_STRING);
        String result = PaymentCardUtil.createDisplayName(this.mockPaymentCardDTO);
        assertTrue(result.contains(TEST_OBFUSCATED_PRIMARY_ACCOUNT_NUMBER_1));
        assertFalse(result.contains(TEST_NICK_NAME_1));
    }

    @Test
    public void isPaymentCardExpiredShouldReturnFalseForExpiryThisMonth() {
        LocalDate testDate = new LocalDate();
        when(this.mockPaymentCardDTO.getExpiryDate())
                .thenReturn(DateUtil.formatDate(testDate.toDate(), CARD_EXPIRY_DATE_PATTERN));
        assertFalse(PaymentCardUtil.isCardExpired(this.mockPaymentCardDTO));
    }

    @Test
    public void isPaymentCardExpiredShouldReturnTrueForExpiryLastMonth() {
        LocalDate testDate = new LocalDate().minusMonths(1);
        when(this.mockPaymentCardDTO.getExpiryDate())
                .thenReturn(DateUtil.formatDate(testDate.toDate(), CARD_EXPIRY_DATE_PATTERN));
        assertTrue(PaymentCardUtil.isCardExpired(this.mockPaymentCardDTO));
    }

    @Test
    public void isPaymentCardExpiredShouldReturnTrueForExpiryLastYear() {
        LocalDate testDate = new LocalDate().minusYears(1);
        when(this.mockPaymentCardDTO.getExpiryDate())
                .thenReturn(DateUtil.formatDate(testDate.toDate(), CARD_EXPIRY_DATE_PATTERN));
        assertTrue(PaymentCardUtil.isCardExpired(this.mockPaymentCardDTO));
    }

    @Test
    public void isPaymentCardExpiredShouldReturnFalseForExpiryNextMonth() {
        LocalDate testDate = new LocalDate().plusMonths(1);
        when(this.mockPaymentCardDTO.getExpiryDate())
                .thenReturn(DateUtil.formatDate(testDate.toDate(), CARD_EXPIRY_DATE_PATTERN));
        assertFalse(PaymentCardUtil.isCardExpired(this.mockPaymentCardDTO));
    }

    @Test
    public void isPaymentCardExpiredShouldReturnFalseForExpiryNextYear() {
        LocalDate testDate = new LocalDate().plusYears(1);
        when(this.mockPaymentCardDTO.getExpiryDate())
                .thenReturn(DateUtil.formatDate(testDate.toDate(), CARD_EXPIRY_DATE_PATTERN));
        assertFalse(PaymentCardUtil.isCardExpired(this.mockPaymentCardDTO));
    }

    @Test
    public void expiresIn3MonthsShouldDiscard() {
    	when(mockPaymentCardDTO.getExpiryDate()).thenReturn(DateTestUtil.getDateNMonthsFromNow(2));
        assertTrue(PaymentCardUtil.expiresInNMonths(3, mockPaymentCardDTO));
    }
    
    @Test
    public void expiresIn3MonthsShouldNotDiscard() {
    	when(mockPaymentCardDTO.getExpiryDate()).thenReturn(DateTestUtil.getDateNMonthsFromNow(3));
    	assertFalse(PaymentCardUtil.expiresInNMonths(3, mockPaymentCardDTO));
    }
}