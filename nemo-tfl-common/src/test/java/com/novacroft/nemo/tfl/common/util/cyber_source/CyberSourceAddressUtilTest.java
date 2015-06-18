package com.novacroft.nemo.tfl.common.util.cyber_source;

import org.junit.Test;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.AddressTestUtil.*;
import static com.novacroft.nemo.test_support.CyberSourceAddressTestUtil.*;
import static com.novacroft.nemo.test_support.CountryTestUtil.TEST_COUNTRY_UK_CODE;
import static org.junit.Assert.assertEquals;

/**
 * CyberSourceAddressUtil unit tests
 */
public class CyberSourceAddressUtilTest {

    @Test
    public void shouldExtractHouseNameNumberFromAddressWithHouseNumber() {
        assertEquals(TEST_HOUSE_NUMBER_1,
                CyberSourceAddressUtil.extractHouseNameNumber(getTestCyberSourceAddressDTOWithHouseNumber()));
    }

    @Test
    public void shouldExtractHouseNameNumberFromAddressWithHouseName() {
        assertEquals(TEST_HOUSE_NAME_1,
                CyberSourceAddressUtil.extractHouseNameNumber(getTestCyberSourceAddressDTOWithHouseName()));
    }

    @Test
    public void shouldExtractHouseNameNumberFromAddressWithoutHouseNameNumber() {
        assertEquals(EMPTY_STRING,
                CyberSourceAddressUtil.extractHouseNameNumber(getTestCyberSourceAddressDTOWithoutHouseNumberOrName()));
    }

    @Test
    public void shouldExtractStreetFromAddressWithHouseNumber() {
        assertEquals(STREET_1, CyberSourceAddressUtil.extractStreet(getTestCyberSourceAddressDTOWithHouseNumber()));
    }

    @Test
    public void shouldExtractStreetFromAddressWithHouseName() {
        assertEquals(STREET_1, CyberSourceAddressUtil.extractStreet(getTestCyberSourceAddressDTOWithHouseName()));
    }

    @Test
    public void shouldExtractStreetFromAddressWithoutHouseNameNumber() {
        assertEquals(STREET_1, CyberSourceAddressUtil.extractStreet(getTestCyberSourceAddressDTOWithoutHouseNumberOrName()));
    }

    @Test
    public void shouldExtractTown() {
        assertEquals(TOWN_1, CyberSourceAddressUtil.extractTown(getTestCyberSourceAddressDTOWithHouseNumber()));
    }

    @Test
    public void shouldExtractPostCode() {
        assertEquals(POSTCODE_1, CyberSourceAddressUtil.extractPostCode(getTestCyberSourceAddressDTOWithHouseNumber()));
    }

    @Test
    public void shouldExtractCountry() {
        assertEquals(TEST_COUNTRY_UK_CODE, CyberSourceAddressUtil.extractCountry(getTestCyberSourceAddressDTOWithHouseNumber()));
    }
}
