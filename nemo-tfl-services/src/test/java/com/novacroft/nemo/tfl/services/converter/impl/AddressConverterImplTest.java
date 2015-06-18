package com.novacroft.nemo.tfl.services.converter.impl;

import static com.novacroft.nemo.test_support.CommonAddressTestUtil.COUNTRY_UK;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.HOUSE_NAME_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.CommonAddressTestUtil.TOWN_1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.services.transfer.Address;

public class AddressConverterImplTest {
    
    private AddressConverterImpl addressConverter;
    AddressDTO address;
    @Before
    public void init(){
        addressConverter = new AddressConverterImpl();
        address = new AddressDTO(HOUSE_NAME_NUMBER_1, STREET_1, TOWN_1, POSTCODE_1, getTestCountryDTO(COUNTRY_UK));
    }
    
    
    @Test
    public void testConvert(){
        
        Address convertedAddress = addressConverter.convert(address);
        assertEquals(convertedAddress.getHouseNameNumber(),HOUSE_NAME_NUMBER_1);
        assertEquals(convertedAddress.getStreet(),STREET_1);
        assertEquals(convertedAddress.getTown(),TOWN_1);
        assertEquals(convertedAddress.getPostcode(),POSTCODE_1);
        assertEquals(convertedAddress.getCountry(),COUNTRY_UK);
        
    }
    
    @Test
    public void testNullConvertisNull(){
        
        Address convertedAddress = addressConverter.convert(null);
        assertNull(convertedAddress);

        
    }
    

    
}
