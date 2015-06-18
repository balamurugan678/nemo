package com.novacroft.nemo.tfl.common.converter.impl.financial_services_centre;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.AddressExportDTO;
import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.common.utils.StringUtil.isBlank;
import static com.novacroft.nemo.test_support.AddressTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class AddressExportConverterImplTest {

    private AddressExportConverterImpl converter;
    private AddressDTO mockAddressDTO;

    @Before
    public void setUp() {
        this.converter = mock(AddressExportConverterImpl.class, CALLS_REAL_METHODS);

        this.mockAddressDTO = mock(AddressDTO.class);
        when(this.mockAddressDTO.getPostcode()).thenReturn(POSTCODE_1);
        when(this.mockAddressDTO.getTown()).thenReturn(TOWN_1);
        when(this.mockAddressDTO.getStreet()).thenReturn(STREET_1);

    }

    @Test
    public void shouldConvertWithBlankLine2() {
        when(this.mockAddressDTO.getHouseNameNumber()).thenReturn(TEST_HOUSE_NUMBER_1);

        AddressExportDTO result = this.converter.convert(this.mockAddressDTO);

        assertEquals(POSTCODE_1, result.getCustomerAddressPostCode());
        assertEquals(TOWN_1, result.getCustomerAddressTown());
        assertTrue(result.getCustomerAddressStreet().contains(STREET_1));
        assertTrue(result.getCustomerAddressStreet().contains(TEST_HOUSE_NUMBER_1));
        assertTrue(isBlank(result.getCustomerAddressLine2()));
        assertTrue(isBlank(result.getCustomerAddressLine1()));
    }

    @Test
    public void shouldConvertWithNonBlankLine2() {
        when(this.mockAddressDTO.getHouseNameNumber()).thenReturn(HOUSE_NAME_NUMBER_1);

        AddressExportDTO result = this.converter.convert(this.mockAddressDTO);

        assertEquals(POSTCODE_1, result.getCustomerAddressPostCode());
        assertEquals(TOWN_1, result.getCustomerAddressTown());
        assertEquals(STREET_1, result.getCustomerAddressStreet());
        assertEquals(HOUSE_NAME_NUMBER_1, result.getCustomerAddressLine2());
        assertTrue(isBlank(result.getCustomerAddressLine1()));
    }
}