package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.List;

import static com.novacroft.nemo.test_support.AddressTestUtil.*;
import static com.novacroft.nemo.test_support.CardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerSearchTestUtil.getTestCustomerSearchRecord;
import static com.novacroft.nemo.test_support.CustomerSearchTestUtil.getTestCustomerSearchRecords;
import static com.novacroft.nemo.test_support.CustomerTestUtil.*;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class CustomerSearchConverterImplTest {
    private CustomerSearchConverterImpl converter;

    private CustomerSearchResultDTO mockCustomerSearchResultDTO;

    @Before
    public void setUp() {
        this.converter = mock(CustomerSearchConverterImpl.class);

        this.mockCustomerSearchResultDTO = mock(CustomerSearchResultDTO.class);
    }

    @Test
    public void shouldConvertList() {
        when(this.converter.convert(anyList())).thenCallRealMethod();
        when(this.converter.convert(any(Object[].class))).thenReturn(this.mockCustomerSearchResultDTO);
        List<CustomerSearchResultDTO> result = this.converter.convert(getTestCustomerSearchRecords());
        verify(this.converter).convert(any(Object[].class));
        assertTrue(result.contains(this.mockCustomerSearchResultDTO));
    }

    @Test
    public void shouldConvertRecord() {
        when(this.converter.convert(any(Object[].class))).thenCallRealMethod();

        when(this.converter.getCustomerId(any(Object[].class))).thenReturn(CUSTOMER_ID_1);
        when(this.converter.getFirstName(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getLastName(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getOysterNumber(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getStatus(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getHouseNameNumber(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getStreet(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getTown(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getCounty(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getCountry(any(Object[].class))).thenReturn(EMPTY);
        when(this.converter.getPostcode(any(Object[].class))).thenReturn(EMPTY);

        this.converter.convert(getTestCustomerSearchRecord());

        verify(this.converter).getCustomerId(any(Object[].class));
        verify(this.converter).getFirstName(any(Object[].class));
        verify(this.converter).getLastName(any(Object[].class));
        verify(this.converter).getOysterNumber(any(Object[].class));
        verify(this.converter).getStatus(any(Object[].class));
        verify(this.converter).getHouseNameNumber(any(Object[].class));
        verify(this.converter).getStreet(any(Object[].class));
        verify(this.converter).getTown(any(Object[].class));
        verify(this.converter).getCounty(any(Object[].class));
        verify(this.converter).getCountry(any(Object[].class));
        verify(this.converter).getPostcode(any(Object[].class));
    }

    @Test
    public void shouldGetCustomerId() {
        when(this.converter.getCustomerId(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToLong(anyObject())).thenReturn(CUSTOMER_ID_1);
        assertEquals(CUSTOMER_ID_1, this.converter.getCustomerId(getTestCustomerSearchRecord()));
        verify(this.converter).convertToLong(anyObject());
    }

    @Test
    public void shouldGetFirstName() {
        when(this.converter.getFirstName(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(FIRST_NAME_1);
        assertEquals(FIRST_NAME_1, this.converter.getFirstName(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetLastName() {
        when(this.converter.getLastName(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(LAST_NAME_1);
        assertEquals(LAST_NAME_1, this.converter.getLastName(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetOysterNumber() {
        when(this.converter.getOysterNumber(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(OYSTER_NUMBER_1);
        assertEquals(OYSTER_NUMBER_1, this.converter.getOysterNumber(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetStatus() {
        when(this.converter.getStatus(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(EMPTY);
        assertEquals(EMPTY, this.converter.getStatus(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetHouseNameNumber() {
        when(this.converter.getHouseNameNumber(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(HOUSE_NAME_NUMBER_1);
        assertEquals(HOUSE_NAME_NUMBER_1, this.converter.getHouseNameNumber(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetStreet() {
        when(this.converter.getStreet(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(STREET_1);
        assertEquals(STREET_1, this.converter.getStreet(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetTown() {
        when(this.converter.getTown(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(TOWN_1);
        assertEquals(TOWN_1, this.converter.getTown(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetCounty() {
        when(this.converter.getCounty(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(EMPTY);
        assertEquals(EMPTY, this.converter.getCounty(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetCountry() {
        when(this.converter.getCountry(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(COUNTRY_1);
        assertEquals(COUNTRY_1, this.converter.getCountry(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void shouldGetPostcode() {
        when(this.converter.getPostcode(any(Object[].class))).thenCallRealMethod();
        when(this.converter.convertToString(anyObject())).thenReturn(POSTCODE_1);
        assertEquals(POSTCODE_1, this.converter.getPostcode(getTestCustomerSearchRecord()));
        verify(this.converter).convertToString(anyObject());
    }

    @Test
    public void convertToLongShouldConvertNull() {
        when(this.converter.convertToLong(anyObject())).thenCallRealMethod();
        assertNull(this.converter.convertToLong(null));
    }

    @Test
    public void convertToLongShouldConvertValue() {
        when(this.converter.convertToLong(anyObject())).thenCallRealMethod();
        assertEquals(CUSTOMER_ID_1, this.converter.convertToLong(BigDecimal.valueOf(CUSTOMER_ID_1)));
    }

    @Test
    public void convertToStringShouldConvertNull() {
        when(this.converter.convertToString(anyObject())).thenCallRealMethod();
        assertEquals(EMPTY, this.converter.convertToString(null));
    }

    @Test
    public void convertToStringShouldConvertValue() {
        when(this.converter.convertToString(anyObject())).thenCallRealMethod();
        assertEquals(FIRST_NAME_1, this.converter.convertToString(FIRST_NAME_1));
    }
}