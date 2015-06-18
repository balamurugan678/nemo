package com.novacroft.nemo.tfl.services.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.COUNTRY_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.COUNTY_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TEST_HOUSE_NUMBER_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TOWN_1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_2;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CS_EMAIL_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EXTERNAL_USER_ID;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.INITIALS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TITLE_1;

import com.novacroft.nemo.tfl.services.transfer.Customer;

public final class CustomerServiceTestUtil {
    public static Customer getCustomer(Long id, String title, String firstName, String initials, String lastName, String houseNameNumber, String street,
                    String town, String county, String countryCode, String postcode, String homePhone, String mobilePhone, String emailAddress,
                    String username) {
        return new Customer(id, title, firstName, initials, lastName, houseNameNumber, street, town, county, countryCode, postcode, homePhone,
                        mobilePhone, emailAddress, username);
    }
    
    public static Customer getTestCustomer1(){
        return getCustomer(EXTERNAL_USER_ID, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, TEST_HOUSE_NUMBER_1, STREET_1, TOWN_1, COUNTY_1, COUNTRY_1, POSTCODE_1, VALUE_1, VALUE_2, CS_EMAIL_1, null);
    }
    
   public static Customer getTestCustomerNullId(){
       return getCustomer(null, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, TEST_HOUSE_NUMBER_1, STREET_1, TOWN_1, COUNTY_1, COUNTRY_1, POSTCODE_1, VALUE_1, VALUE_2, CS_EMAIL_1, null);
   }
   
   public static Customer getTestCustomerEmailIsEmpty(){
       return getCustomer(EXTERNAL_USER_ID, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, TEST_HOUSE_NUMBER_1, STREET_1, TOWN_1, COUNTY_1, COUNTRY_1, POSTCODE_1, VALUE_1, VALUE_2, "", null);
   }
   
   public static Customer getTestCustomerWithError(){
       Customer customer = new Customer();
       customer.setErrors(ErrorResultTestUtil.getTestErrorResult1());
       return customer;
   }
}
