package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.COUNTRY_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TEST_HOUSE_NUMBER_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TOWN_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EMAIL_ADDRESS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;

public final class CustomerSearchTestUtil {

    public static Object[] getTestCustomerSearchRecord() {
        return new Object[]{CUSTOMER_ID_1, FIRST_NAME_1, LAST_NAME_1, OYSTER_NUMBER_1, EMPTY, TEST_HOUSE_NUMBER_1, STREET_1,
                TOWN_1, EMPTY, COUNTRY_1, POSTCODE_1};
    }

    public static List<?> getTestCustomerSearchRecords() {
        List customerSearchRecords = new ArrayList<>();
        customerSearchRecords.add(getTestCustomerSearchRecord());
        return customerSearchRecords;
    }

    public static CustomerSearchArgumentsDTO getTestCustomerSearchArgumentsDTO() {
        return new CustomerSearchArgumentsDTO(true, CUSTOMER_ID_1, OYSTER_NUMBER_1, FIRST_NAME_1, LAST_NAME_1, POSTCODE_1,
                EMAIL_ADDRESS_1, USERNAME_1, 1, 8);
    }

    public static CustomerSearchResultDTO getTestCustomerSearchResultDTO() {
        return new CustomerSearchResultDTO(CUSTOMER_ID_1, FIRST_NAME_1, LAST_NAME_1, OYSTER_NUMBER_1, EMPTY,
                TEST_HOUSE_NUMBER_1, STREET_1, TOWN_1, EMPTY, COUNTRY_1, POSTCODE_1);
    }

    public static List<CustomerSearchResultDTO> getTestCustomerSearchResultDTOs() {
        List<CustomerSearchResultDTO> customerSearchResultDTOs = new ArrayList<CustomerSearchResultDTO>();
        customerSearchResultDTOs.add(getTestCustomerSearchResultDTO());
        return customerSearchResultDTOs;
    }

    private CustomerSearchTestUtil() {
    }
}
