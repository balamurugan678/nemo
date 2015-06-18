package com.novacroft.nemo.common.converter.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddress1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryUK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.data_access.CountryDAO;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.domain.Country;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;

public class AddressConverterImplTest {
    private AddressConverterImpl converter;
    private CountryConverterImpl mockCountryConverter;
    private CountryDAO mockDAO;

    @Before
    public void setUp() {
        converter = new AddressConverterImpl();
        mockCountryConverter = mock(CountryConverterImpl.class);
        mockDAO = mock(CountryDAO.class);
        converter.countryConverter = mockCountryConverter;
        converter.countryDAO = mockDAO;

        when(mockCountryConverter.convertDtoToEntity(any(CountryDTO.class), any(Country.class))).thenReturn(getTestCountryUK());
        when(mockCountryConverter.convertEntityToDto(any(Country.class))).thenReturn(getTestCountryDTO1());
    }

    @Test
    public void shouldConvertEntityToDto() {
        AddressDTO actualResult = converter.convertEntityToDto(getTestAddress1());

        verify(mockCountryConverter).convertEntityToDto(any(Country.class));
        assertNotNull(actualResult);
        assertNotNull(actualResult.getCountry());
    }

    @Test
    public void shouldConvertEntityToDtoWithNoAddress() {
        Address address = getTestAddress1();
        address.setCountry(null);
        AddressDTO actualResult = converter.convertEntityToDto(address);

        assertNotNull(actualResult);
        assertNull(actualResult.getCountry());
    }

    @Test
    public void shouldConvertDtoToEntity() {
        Country countryUK = getTestCountryUK();
        when(mockDAO.findByExampleUniqueResult(any(Country.class))).thenReturn(countryUK);
        Address actualResult = converter.convertDtoToEntity(getTestAddressDTO1(), new Address());

        verify(mockDAO).findByExampleUniqueResult(any(Country.class));
        assertNotNull(actualResult);
        assertEquals(countryUK, actualResult.getCountry());
    }
}
