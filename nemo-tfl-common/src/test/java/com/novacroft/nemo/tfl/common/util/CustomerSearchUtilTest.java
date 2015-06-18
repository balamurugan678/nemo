package com.novacroft.nemo.tfl.common.util;

import com.novacroft.nemo.test_support.AddressTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomerSearchUtilTest {
    private CustomerSearchArgumentsDTO mockCustomerSearchArgumentsDTO;

    @Before
    public void setUp() {
        this.mockCustomerSearchArgumentsDTO = mock(CustomerSearchArgumentsDTO.class);
    }

    @Test
    public void isExactMatchShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        assertTrue(CustomerSearchUtil.isExactMatch(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isExactMatchShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        assertFalse(CustomerSearchUtil.isExactMatch(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        assertTrue(CustomerSearchUtil.isNotExactMatch(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        assertFalse(CustomerSearchUtil.isNotExactMatch(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isExactMatchAndFirstNameNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertTrue(CustomerSearchUtil.isExactMatchAndFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isExactMatchAndFirstNameNotNullShouldReturnFalseWithBlankFirstName() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(StringUtils.EMPTY);
        assertFalse(CustomerSearchUtil.isExactMatchAndFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isExactMatchAndFirstNameNotNullShouldReturnFalseWithExactOff() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertFalse(CustomerSearchUtil.isExactMatchAndFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchAndFirstNameNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertTrue(CustomerSearchUtil.isNotExactMatchAndFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchAndFirstNameNotNullShouldReturnFalseWithBlankFirstName() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(StringUtils.EMPTY);
        assertFalse(CustomerSearchUtil.isNotExactMatchAndFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchAndFirstNameNotNullShouldReturnFalseWithExactOff() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertFalse(CustomerSearchUtil.isNotExactMatchAndFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isExactMatchAndLastNameNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertTrue(CustomerSearchUtil.isExactMatchAndLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isExactMatchAndLastNameNotNullShouldReturnFalseWithBlankLastName() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(StringUtils.EMPTY);
        assertFalse(CustomerSearchUtil.isExactMatchAndLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isExactMatchAndLastNameNotNullShouldReturnFalseWithExactOff() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertFalse(CustomerSearchUtil.isExactMatchAndLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchAndLastNameNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertTrue(CustomerSearchUtil.isNotExactMatchAndLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchAndLastNameNotNullShouldReturnFalseWithBlankLastName() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.FALSE);
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(StringUtils.EMPTY);
        assertFalse(CustomerSearchUtil.isNotExactMatchAndLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isNotExactMatchAndLastNameNotNullShouldReturnFalseWithExactOff() {
        when(this.mockCustomerSearchArgumentsDTO.getUseExactMatch()).thenReturn(Boolean.TRUE);
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertFalse(CustomerSearchUtil.isNotExactMatchAndLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isCustomerIdNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getCustomerId()).thenReturn(CustomerTestUtil.CUSTOMER_ID_1);
        assertTrue(CustomerSearchUtil.isCustomerIdNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isCustomerIdNotNullShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getCustomerId()).thenReturn(null);
        assertFalse(CustomerSearchUtil.isCustomerIdNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isFirstNameNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(CustomerTestUtil.FIRST_NAME_1);
        assertTrue(CustomerSearchUtil.isFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isFirstNameNotNullShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getFirstName()).thenReturn(null);
        assertFalse(CustomerSearchUtil.isFirstNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isLastNameNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(CustomerTestUtil.LAST_NAME_1);
        assertTrue(CustomerSearchUtil.isLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isLastNameNotNullShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getLastName()).thenReturn(null);
        assertFalse(CustomerSearchUtil.isLastNameNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isCardNumberNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getCardNumber()).thenReturn(CardTestUtil.OYSTER_NUMBER_1);
        assertTrue(CustomerSearchUtil.isCardNumberNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isCardNumberNotNullShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getCardNumber()).thenReturn(null);
        assertFalse(CustomerSearchUtil.isCardNumberNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isPostcodeNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getPostcode()).thenReturn(AddressTestUtil.POSTCODE_1);
        assertTrue(CustomerSearchUtil.isPostcodeNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isPostcodeNotNullShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getPostcode()).thenReturn(null);
        assertFalse(CustomerSearchUtil.isPostcodeNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isEmailNotNullShouldReturnTrue() {
        when(this.mockCustomerSearchArgumentsDTO.getEmail()).thenReturn(CustomerTestUtil.EMAIL_ADDRESS_1);
        assertTrue(CustomerSearchUtil.isEmailNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void isEmailNotNullShouldReturnFalse() {
        when(this.mockCustomerSearchArgumentsDTO.getEmail()).thenReturn(null);
        assertFalse(CustomerSearchUtil.isEmailNotNull(this.mockCustomerSearchArgumentsDTO));
    }

    @Test
    public void stripSpacesShouldStrip() {
        assertEquals("NN36UR", CustomerSearchUtil.stripSpaces(AddressTestUtil.POSTCODE_1));
    }

    @Test
    public void stripSpacesShouldNotStrip() {
        assertEquals(CustomerTestUtil.FIRST_NAME_1, CustomerSearchUtil.stripSpaces(CustomerTestUtil.FIRST_NAME_1));
    }
}