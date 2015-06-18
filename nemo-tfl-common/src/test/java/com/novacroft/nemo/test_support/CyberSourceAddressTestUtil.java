package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.transfer.cyber_source.CyberSourceAddressDTO;

import static com.novacroft.nemo.common.utils.StringUtil.EMPTY_STRING;
import static com.novacroft.nemo.test_support.AddressTestUtil.*;
import static com.novacroft.nemo.test_support.CountryTestUtil.TEST_COUNTRY_UK_CODE;

/**
 * Fixtures and utilities for testing CyberSource addresses
 */
public final class CyberSourceAddressTestUtil {

    public static CyberSourceAddressDTO getTestCyberSourceAddressDTOWithHouseNumber() {
        return new CyberSourceAddressDTO(String.format("%s %s", TEST_HOUSE_NUMBER_1, STREET_1), EMPTY_STRING, TOWN_1,
                POSTCODE_1, TEST_COUNTRY_UK_CODE);
    }

    public static CyberSourceAddressDTO getTestCyberSourceAddressDTOWithHouseName() {
        return new CyberSourceAddressDTO(TEST_HOUSE_NAME_1, STREET_1, TOWN_1, POSTCODE_1, TEST_COUNTRY_UK_CODE);
    }

    public static CyberSourceAddressDTO getTestCyberSourceAddressDTOWithoutHouseNumberOrName() {
        return new CyberSourceAddressDTO(STREET_1, EMPTY_STRING, TOWN_1, POSTCODE_1, TEST_COUNTRY_UK_CODE);
    }

    private CyberSourceAddressTestUtil() {
    }
}
