package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountry;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO;

import com.google.gson.Gson;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.domain.Country;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.phoenix.service.paf.bean.PAFFullAddress;

/**
 * Utilities for Address tests
 */
public final class AddressTestUtil {
    public static final String TEST_HOUSE_NUMBER_1 = "1";
    public static final String TEST_HOUSE_NUMBER_RANGE_1 = "1-2";
    public static final String TEST_HOUSE_NUMBER_WITH_LETTER_1 = "42A";
    public static final String TEST_HOUSE_NAME_1 = "Novacroft Centre";

    public static final Long ADDRESS_ID_1 = 4L;
    public static final String HOUSE_NAME_NUMBER_1 = "Cirrus Park";
    public static final String STREET_1 = "Lower Farm Road";
    public static final String TOWN_1 = "Moulton Park";
    public static final String COUNTY_1 = "Northhamptonshire";
    public static final String COUNTRY_1 = "United Kingdom";
    public static final String POSTCODE_1 = "NN3 6UR";

    public static final Long ADDRESS_ID_2 = 8L;
    public static final String HOUSE_NAME_NUMBER_2 = "1";
    public static final String STREET_2 = "Abington Square";
    public static final String TOWN_2 = "Northampton";
    public static final String COUNTRY_2 = "United Kingdom";
    public static final String POSTCODE_2 = "NN1 4AE";
    
    public static final String UPRN = "3477562503085768S";
    public static final String ORGANISATION_NAME = "Charles Novacroft Direct Ltd";
    public static final String BUILDING_NAME = "Cirrus Park";
    public static final String SUB_BUILDING_NAME = "Novacroft House";
    public static final String BUILDING_NUMBER = "Northampton";
    public static final String THOROUGH_FARE_NAME = "Lower Farm";
    public static final String THOROUGH_FARE_DESCRIPTOR = "Road";
    public static final String POST_TOWN = "Northampton";
    public static final String POST_CODE = "NN3 6UR";
    public static final String POST_CODE_TYPE = "S";

    public static final String POSTCODE_WITHOUT_SPACE = "NN14AE";
    
    public static AddressDTO getTestAddressDTO1() {
        return getTestAddressDTO(ADDRESS_ID_1, HOUSE_NAME_NUMBER_1, STREET_1, TOWN_1, COUNTRY_1, POSTCODE_1);
    }

    public static AddressDTO getTestAddressDTO2() {
        return getTestAddressDTO(ADDRESS_ID_2, HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, COUNTRY_2, POSTCODE_2);
    }
    
    public static AddressDTO getTestAddressDTONullPostCode() {
        return getTestAddressDTO(ADDRESS_ID_2, HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, COUNTRY_2, null);
    }

    public static String getTestAddressDTO2AsJson() {
        Gson gson = new Gson();
        return gson.toJson(getTestAddressDTO2());
    }

    public static Address getTestAddress1() {
        return getTestAddress(ADDRESS_ID_1, HOUSE_NAME_NUMBER_1, STREET_1, TOWN_1, COUNTRY_1, POSTCODE_1);
    }
    
    public static PAFFullAddress getPAFFullTestAddress1() {
        return getTestPAFFullAddress();
    }
    
    public static PAFFullAddress getPAFFullTestAddressWithBuildingNumber() {
        return getTestPAFFullAddress(10);
    }

    public static Address getTestAddress2() {
        return getTestAddress(ADDRESS_ID_2, HOUSE_NAME_NUMBER_2, STREET_2, TOWN_2, COUNTRY_2, POSTCODE_2);
    }

    private static AddressDTO getTestAddressDTO(Long addressId, String houseNameNumber, String street, String town,
                                               String countryCode, String postcode) {
        CountryDTO countryDTO = getTestCountryDTO(countryCode);
        return new AddressDTO(addressId, houseNameNumber, street, town, countryDTO, postcode);
    }

    public static Address getTestAddress(Long addressId, String houseNameNumber, String street, String town, String countryCode,
                                         String postcode) {
    	Country country = getTestCountry(countryCode);
        return new Address(addressId, houseNameNumber, street, town, country, postcode);
    }
    
    public static PAFFullAddress getTestPAFFullAddress() {
        return new PAFFullAddress(UPRN,BUILDING_NAME,0,"","",THOROUGH_FARE_DESCRIPTOR,"",THOROUGH_FARE_NAME,"","","",1, "",ORGANISATION_NAME, POST_CODE_TYPE, "", "", POST_CODE, POST_CODE_TYPE, POST_TOWN, "Y", SUB_BUILDING_NAME, THOROUGH_FARE_DESCRIPTOR, "", THOROUGH_FARE_NAME);
    }
    
    public static PAFFullAddress getTestPAFFullAddress(int buildingNumber) {
        return new PAFFullAddress(UPRN,BUILDING_NAME,buildingNumber,"","",THOROUGH_FARE_DESCRIPTOR,"",THOROUGH_FARE_NAME,"","","",1, "",ORGANISATION_NAME, POST_CODE_TYPE, "", "", POST_CODE, POST_CODE_TYPE, POST_TOWN, "Y", SUB_BUILDING_NAME, THOROUGH_FARE_DESCRIPTOR, "", THOROUGH_FARE_NAME);
    }
}
