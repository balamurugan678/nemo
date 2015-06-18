package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.domain.Country;
import com.novacroft.nemo.common.transfer.CountryDTO;

public class CountryTestUtil {
    private static final Long TEST_COUNTRY_ID1 = 1L;
    public static final String TEST_COUNTRY_UK_NAME = "United Kingdom";
    public static final String TEST_COUNTRY_UK_CODE = "GB";
    public static final String TEST_UK_NUMERIC_CODE = "826";
    
    private static final Long TEST_COUNTRY_ID2 = 2L;
    private static final String TEST_COUNTRY_EG_NAME = "Egypt";
    private static final String TEST_COUNTRY_EG_CODE = "EG";
    private static final String TEST_EG_NUMERIC_CODE = "818";
    
    private static final String TEST_NUMERIC_CODE = "000";
    
    public static Country getTestCountryUK() {
        return createTestCountry(TEST_COUNTRY_ID1, TEST_COUNTRY_UK_CODE, TEST_COUNTRY_UK_NAME, TEST_UK_NUMERIC_CODE);
    }
    
    public static Country getTestCountry(String countryCode) {
        return createTestCountry(null, countryCode, countryCode, TEST_NUMERIC_CODE);
    }
    
    private static Country createTestCountry(Long id, String countryCode, String countryName, String numericCode) {
        Country country = new Country();
        country.setId(id);
        country.setCode(countryCode);
        country.setName(countryName);
        country.setIsoNumericCode(numericCode);
        return country;
    }
    
    public static CountryDTO getTestCountryDTO1() {
        return createTestCountryDTO(TEST_COUNTRY_ID1, TEST_COUNTRY_UK_CODE, TEST_COUNTRY_UK_NAME, TEST_UK_NUMERIC_CODE);
    }
    
    public static CountryDTO getTestCountryDTO2() {
        return createTestCountryDTO(TEST_COUNTRY_ID2, TEST_COUNTRY_EG_CODE, TEST_COUNTRY_EG_NAME, TEST_EG_NUMERIC_CODE);
    }
    
    public static CountryDTO getTestCountryDTO(String countryCode) {
        return createTestCountryDTO(null, countryCode, countryCode, TEST_NUMERIC_CODE);
    }
    
    private static CountryDTO createTestCountryDTO(Long id, String countryCode, String countryName, String numericCode) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setId(id);
        countryDTO.setCode(countryCode);
        countryDTO.setName(countryName);
        countryDTO.setIsoNumericCode(numericCode);
        return countryDTO;
    }
}
