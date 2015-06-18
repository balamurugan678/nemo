package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO;

import com.novacroft.nemo.common.transfer.CommonAddressDTO;

/**
 * Utilities for Common Address tests
 */
public final class CommonAddressTestUtil {

    public static final String HOUSE_NAME_NUMBER_1 = "1";
    public static final String STREET_1 = "Lower Farm Road";
    public static final String TOWN_1 = "Moulton Park";
    public static final String COUNTY_1 = "Northamptonshire";
    public static final String POSTCODE_1 = "NN3 6UR";
    public static final String POSTCODE_WITHOUT_SPACE = "NN36UR";

    public static final String HOUSE_NAME_NUMBER_2 = "2";
    public static final String STREET_2 = "Abington Square";
    public static final String TOWN_2 = "Northampton";
    public static final String COUNTY_2 = "Northamptonshire";
    public static final String POSTCODE_2 = "NN1 4AE";

    public static final String HOUSE_NAME_NUMBER_1A = "1A";
    public static final String STREET_1A = "High Street";
    public static final String TOWN_1A = "Crawley";
    public static final String COUNTY_1A = "Sussex";
    public static final String POSTCODE_1A = "RH10 1BN";
    
    public static final String HOUSE_NAME_NUMBER_ABC= "ABC";
    public static final String STREET_ABC = "Robinson Street";
    public static final String TOWN_ABC = "Gatwick";
    public static final String COUNTY_ABC = "Hampshire";
    public static final String POSTCODE_ABC = "EA3 5RS";
    
    public static final String HOUSE_NAME_NUMBER_BCD = "BCD";
    public static final String STREET_BCD = "Kerrington Street";
    public static final String TOWN_BCD = "Tooting";
    public static final String COUNTY_BCD = "Sussex";
    public static final String POSTCODE_BCD = "SW1 3YT";
    
    public static final String COUNTRY_UK = "United Kingdom";

    public static CommonAddressDTO getTestCommonAddressDTO1() {
        return getTestCommonAddressDTO( HOUSE_NAME_NUMBER_1, STREET_1, TOWN_1, COUNTY_1, COUNTRY_UK, POSTCODE_1);
    }

    public static CommonAddressDTO getTestCommonAddressDTO2() {
        return getTestCommonAddressDTO(HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, COUNTY_2, COUNTRY_UK, POSTCODE_2);
    }

    public static CommonAddressDTO getTestCommonAddressDTO1A() {
        return getTestCommonAddressDTO(HOUSE_NAME_NUMBER_1A, STREET_1A, TOWN_1A, COUNTY_1A, COUNTRY_UK, POSTCODE_1A );
    }
    
    public static CommonAddressDTO getTestCommonAddressDTOABC() {
        return getTestCommonAddressDTO( HOUSE_NAME_NUMBER_ABC, STREET_ABC, TOWN_ABC, COUNTY_ABC, COUNTRY_UK, POSTCODE_ABC);
    }
    
    public static CommonAddressDTO getTestCommonAddressDTOBCD() {
        return getTestCommonAddressDTO( HOUSE_NAME_NUMBER_BCD, STREET_BCD, TOWN_BCD, COUNTY_BCD, COUNTRY_UK, POSTCODE_BCD );
    }

    
    protected static CommonAddressDTO getTestCommonAddressDTO(String houseNameNumber, String street, String town,String county, String countryCode, String postcode) {
    	CommonAddressDTO commonAddressDTO = new CommonAddressDTO();
    	commonAddressDTO.setHouseNameNumber(houseNameNumber);
    	commonAddressDTO.setStreet(street);
    	commonAddressDTO.setTown(town);
    	commonAddressDTO.setCounty(county);
    	commonAddressDTO.setCountry(getTestCountryDTO(countryCode));
    	commonAddressDTO.setPostcode(postcode);
        return commonAddressDTO;
    }
    
}