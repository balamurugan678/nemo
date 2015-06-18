package com.novacroft.nemo.test_support;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.CustomerSearchCmdImpl;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;

/**
 * Utilities for Customer tests
 */
public final class CustomerTestUtil {
    public static final Long CUSTOMER_ID_1 = 2L;
    public static final String TITLE_1 = "Mr";
    public static final String FIRST_NAME_1 = "test-first-name-1";
    public static final String INITIALS_1 = "test-initial-1";
    public static final String LAST_NAME_1 = "test-last-name-1";
    public static final String FORMATED_NAME_1 = "Mr test-first-name-1 test-initial-1 test-last-name-1";
    public static final int DECEASED_1 = 0;
    public static final int ANONYMISED_1 = 0;
    public static final int READONLY_1 = 0;

    public static final Long CUSTOMER_ID_2 = 4L;

    public static final Long ID_1 = 1L;
    public static final Long CUSTOMER_ADDRESS_ID_1 = 1L;

    public static final Long ID_2 = 2L;
    public static final String FIRST_NAME_2 = "John";
    public static final String LAST_NAME_2 = "Martin";
    public static final String INITIALS_2 = "C";
    public static final Long CUSTOMER_ADDRESS_ID_2 = 2L;

    public static final Long ID_3 = 3L;
    public static final String FIRST_NAME_3 = "Bob";
    public static final String LAST_NAME_3 = "Milk";
    public static final String INITIALS_3 = "D";
    public static final Long CUSTOMER_ADDRESS_ID_3 = 3L;

    public static final Long ID_4 = 4L;
    public static final String FIRST_NAME_4 = "Terry";
    public static final String LAST_NAME_4 = "Arm";
    public static final String INITIALS_4 = "E";
    public static final Long CUSTOMER_ADDRESS_ID_4 = 4L;

    public static final Long ID_5 = 5L;
    public static final String FIRST_NAME_5 = "David";
    public static final String LAST_NAME_5 = "Smith";
    public static final String INITIALS_5 = "F";
    public static final Long CUSTOMER_ADDRESS_ID_5 = 5L;

    public static final Long ID_6 = 6L;
    public static final String FIRST_NAME_6 = "William";
    public static final String LAST_NAME_6 = "Smith";
    public static final String INITIALS_6 = "G";
    public static final Long CUSTOMER_ADDRESS_ID_6 = 6L;

    public static final Long CS_CUSTOMERID_1 = 1L;
    public static final String CS_CARDNUMBER_1 = "0123456789012";
    public static final String CS_EMAIL_1 = "luke.smithus@novacroft.com";
    public static final String CS_FIRST_NAME_1 = "Luke";
    public static final String CS_LAST_NAME_1 = "Smithus";
    public static final String CS_POSTCODE_1 = "W1C 0JS";
    public static final Boolean CS_EXACT_1 = Boolean.FALSE;
    public static final Integer CS_RESULTLENGTH = 5;
    public static final Integer CS_STARTCOUNT = 0;
    public static final Integer CS_ENDCOUNT = 100;
    public static final Long CS_CARDID = 1L;

    public static final Long SR_ID_1 = 1L;
    public static final String SR_FIRST_NAME_1 = "Don";
    public static final String SR_LAST_NAME_1 = "Jones";
    public static final String SR_OYSTERNUMBER = "0123456789012";
    public static final String SR_STATUS = "A";
    public static final String SR_HOUSENAMENUMBER = "16";
    public static final String SR_STREET = "Test st";
    public static final String SR_TOWN = "Town";
    public static final String SR_COUNTY = "County";
    public static final String SR_COUNTRY = "Country";
    public static final String SR_POSTCODE = "W1C 0JS";
    public static final Integer SR_CALLS = 1;
    public static final String AGENT_ID = "SK11";
    public static final Long WEBACCOUNT_ID = 200L; // TODO delete
    public static final String TOKEN = "bfGdYRVYhYl1gUSrJa8M";
    public static final String TOKEN_BLANK = "";

    public static final String CUSTOMER_ID_7 = "7";
    public static final String CUSTOMER_ID_8 = "5001";

    public static final String CS_INVALID_FIRST_NAME_LENGTH = "a";
    public static final String CS_INVALID_LAST_NAME_LENGTH = "a";
    public static final String CS_INVALID_FIRST_NAME_CHARACTERS = "123";
    public static final String CS_INVALID_LAST_NAME_CHARACTERS = "123";
    public static final String CS_INVALID_CARD_NUMBER = "abc";
    public static final String CS_INVALID_EMAIL = "!";
    public static final String CS_INVALID_POSTCODE = "!";

    public static final String FIRSTNAME_LENGTH_ERROR = "First name must be 2 or more characters";
    public static final String FIRSTNAME_LENGTH_EXPECTED_ERROR = "{\"firstName\":\"First name must be 2 or more characters\"}";
    public static final String LASTNAME_LENGTH_ERROR = "Last name must be 2 or more characters";
    public static final String LASTNAME_LENGTH_EXPECTED_ERROR = "{\"lastName\":\"Last name must be 2 or more characters\"}";
    public static final String FIRSTNAME_LASTNAME_LENGTH_EXPECTED_ERROR =
            "{\"lastName\":\"Last name must be 2 or more characters\",\"firstName\":\"First name must be 2 or more " +
                    "characters\"}";
    public static final String FIRSTNAME_EMPTY_ERROR = "FirstName must be 2 or more characters";
    public static final String FIRSTNAME_LAST_NAME_EMPTY_EXPECTED_ERROR =
            "{\"FirstName\":\"First name must be 2 or more characters\",\"LastName\":\"Last Name must be 2 or more " +
                    "characters\"}";
    public static final String EMAIL_ADDRESS_1 = "test-1@nowhere.com";
    public static final String USERNAME_1 = "TestUser1";

    public static final int CUSTOMER_DEACTIVE = 1;
    public static final int CUSTOMER_ACTIVE = 0;

    public static final Long WEB_ACCOUNT_ID_3 = 1L;
    public static final String USERNAME_3 = "TestUser2";
    public static final String PASSWORD_3 = "test-password-2";
    public static final String SALT_3 = "test-salt-2";
    public static final String EMAIL_ADDRESS_3 = "test-2@nowhere.com";
    public static final String PHOTO_CARD_NUMBER_3 = "9876";
    public static final String UNFORMATTED_EMAIL_ADDRESS_3 = "test-2@nowhere.com";
    public static final int ANONYMISED_3 = 0;
    public static final int READ_ONLY_3 = 0;
    public static final int PASSWORD_CHANGE_REQUIRED_3 = 0;
    public static final String INVALID_EMAIL_ADDRESS = "test-1@nowhere";

    public static final Long EXTERNAL_USER_ID = 1L;
    public static final Long EXTERNAL_CUSTOMER_ID = 3489L;

    public static final Long CUSTOMER_ID_9 = 4998L;

    public static final Long TFL_MASTER_ID_1 = 1859115L;
    public static final String NULL_CARD_NUMBER = "";
    public static final Integer OYSTER_CARD_CHECK_SUM = 2;
    public static final Integer OYSTER_CARD_FULL_LENGTH = 1;

    public static CustomerDTO getTestCustomerDTO1() {
        return getTestCustomerDTO(CUSTOMER_ID_1, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, CUSTOMER_ADDRESS_ID_1,
                DECEASED_1, ANONYMISED_1, READONLY_1);
    }

    public static CustomerDTO getTestCustomerDTO2() {
        return getTestCustomerDTO(CUSTOMER_ID_2, TITLE_1, FIRST_NAME_2, INITIALS_2, LAST_NAME_2, CUSTOMER_ADDRESS_ID_2,
                DECEASED_1, ANONYMISED_1, READONLY_1);
    }

    public static CustomerDTO getCustomerDTO(Long customerId) {
        return getTestCustomerDTO(customerId, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, CUSTOMER_ADDRESS_ID_1, DECEASED_1,
                ANONYMISED_1, READONLY_1);
    }

    public static CustomerDTO getTestCustomerDTO3() {
        return getTestCustomerDeactiveDTO(CUSTOMER_ID_1, USERNAME_3, PASSWORD_3, SALT_3, EMAIL_ADDRESS_3, PHOTO_CARD_NUMBER_3,
                UNFORMATTED_EMAIL_ADDRESS_3, ANONYMISED_3, READ_ONLY_3, PASSWORD_CHANGE_REQUIRED_3, CUSTOMER_DEACTIVE);
    }

    public static CustomerDTO getTestCustomerDTO4() {
        return getTestCustomerDeactiveDTO(CUSTOMER_ID_1, USERNAME_3, PASSWORD_3, SALT_3, EMAIL_ADDRESS_3, PHOTO_CARD_NUMBER_3,
                UNFORMATTED_EMAIL_ADDRESS_3, ANONYMISED_3, READ_ONLY_3, PASSWORD_CHANGE_REQUIRED_3, CUSTOMER_ACTIVE);
    }

    public static CustomerDTO getTestCustomerDTO5() {
        CustomerDTO customerDTO =
                getTestCustomerDeactiveDTO(CUSTOMER_ID_1, USERNAME_3, PASSWORD_3, SALT_3, EMAIL_ADDRESS_3, PHOTO_CARD_NUMBER_3,
                        UNFORMATTED_EMAIL_ADDRESS_3, ANONYMISED_3, READ_ONLY_3, PASSWORD_CHANGE_REQUIRED_3, CUSTOMER_ACTIVE);
        customerDTO.setExternalId(EXTERNAL_CUSTOMER_ID);
        return customerDTO;
    }

    public static CustomerDTO getTestCustomerDTOWithEmail() {
        CustomerDTO customerDTO =
                getTestCustomerDTO(CUSTOMER_ID_1, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, CUSTOMER_ADDRESS_ID_1,
                        DECEASED_1, ANONYMISED_1, READONLY_1);
        customerDTO.setEmailAddress(EMAIL_ADDRESS_1);
        return customerDTO;
    }

    public static CustomerDTO getTestCustomerDTO(Long customerId, String title, String firstName, String initials,
                                                 String lastName, Long addressId, int deceased, int anonymised, int readonly) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setTitle(title);
        customerDTO.setFirstName(firstName);
        customerDTO.setInitials(initials);
        customerDTO.setLastName(lastName);
        customerDTO.setAddressId(addressId);
        customerDTO.setDeceased(deceased);
        customerDTO.setAnonymised(anonymised);
        customerDTO.setReadonly(readonly);
        return customerDTO;
    }

    public static CustomerDTO getTestCustomerDeactiveDTO(Long customerId, String username, String password, String salt,
                                                         String emailAddress, String photoCardNumber,
                                                         String unformattedEmailAddress, int anonymised, int readOnly,
                                                         int passwordChangeRequired, int deactivated) {
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setId(customerId);
        customerDTO.setUsername(username);
        customerDTO.setPassword(password);
        customerDTO.setSalt(salt);
        customerDTO.setEmailAddress(emailAddress);
        customerDTO.setPhotoCardNumber(photoCardNumber);
        customerDTO.setUnformattedEmailAddress(unformattedEmailAddress);
        customerDTO.setAnonymised(anonymised);
        customerDTO.setReadOnly(readOnly);
        customerDTO.setPasswordChangeRequired(passwordChangeRequired);
        customerDTO.setDeactivated(deactivated);
        return customerDTO;
    }

    public static List<Customer> getTestCustomerList1() {
        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(getCustomer7());
        return customerList;
    }

    public static List<Customer> getTestCustomerList2() {
        List<Customer> customerList = getTestCustomerList1();
        customerList.add(getCustomer6());
        return customerList;
    }

    public static Customer getCustomer1() {
        return createCustomer(ID_1, FIRST_NAME_1, LAST_NAME_1, INITIALS_1, CUSTOMER_ADDRESS_ID_1);
    }

    public static Customer getCustomer2() {
        return createCustomer(ID_2, FIRST_NAME_2, LAST_NAME_2, INITIALS_2, CUSTOMER_ADDRESS_ID_2);
    }

    public static Customer getCustomer3() {
        return createCustomer(ID_3, FIRST_NAME_3, LAST_NAME_3, INITIALS_3, CUSTOMER_ADDRESS_ID_3);
    }

    public static Customer getCustomer4() {
        return createCustomer(ID_4, FIRST_NAME_4, LAST_NAME_4, INITIALS_4, CUSTOMER_ADDRESS_ID_4);
    }

    public static Customer getCustomer5() {
        return createCustomer(ID_5, FIRST_NAME_5, LAST_NAME_5, INITIALS_5, CUSTOMER_ADDRESS_ID_5);
    }

    public static Customer getCustomer6() {
        return createCustomer(ID_6, FIRST_NAME_6, LAST_NAME_6, INITIALS_6, CUSTOMER_ADDRESS_ID_6);
    }

    public static Customer getCustomer7() {
        return createCustomer1(ID_6, FIRST_NAME_6, LAST_NAME_6, INITIALS_6, CUSTOMER_ADDRESS_ID_6, USERNAME_1);
    }

    public static Customer getCustomer8() {
        Customer customer = createCustomer1(ID_6, FIRST_NAME_6, LAST_NAME_6, INITIALS_6, CUSTOMER_ADDRESS_ID_6, USERNAME_1);
        customer.setExternalUserId(EXTERNAL_USER_ID);
        return customer;
    }

    public static Customer createCustomer(Long id, String firstName, String lastName, String initial, Long addressId) {
        Customer c = new Customer();
        c.setId(id);
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setInitials(initial);
        c.setAddressId(addressId);
        return c;
    }

    public static Customer createCustomer1(Long id, String firstName, String lastName, String initial, Long addressId,
                                           String userName) {
        Customer c = new Customer();
        c.setId(id);
        c.setFirstName(firstName);
        c.setLastName(lastName);
        c.setInitials(initial);
        c.setAddressId(addressId);
        c.setUsername(userName);
        return c;
    }

    public static CustomerSearchCmdImpl getSearch1() {
        return createSearch(CS_CUSTOMERID_1, CS_CARDNUMBER_1, CS_EMAIL_1, CS_FIRST_NAME_1, CS_LAST_NAME_1, CS_POSTCODE_1,
                CS_EXACT_1, CS_RESULTLENGTH, CS_STARTCOUNT, CS_ENDCOUNT, CS_CARDID);
    }

    public static CustomerSearchCmdImpl getInvalidSearchIncludingNameLength() {
        return createSearch(CS_CUSTOMERID_1, CS_INVALID_CARD_NUMBER, CS_INVALID_EMAIL, CS_INVALID_FIRST_NAME_LENGTH,
                CS_INVALID_LAST_NAME_LENGTH, CS_INVALID_POSTCODE, CS_EXACT_1, CS_RESULTLENGTH, CS_STARTCOUNT, CS_ENDCOUNT,
                CS_CARDID);
    }

    public static CustomerSearchCmdImpl getInvalidSearchIncludingNameCharacters() {
        return createSearch(CS_CUSTOMERID_1, CS_INVALID_CARD_NUMBER, CS_INVALID_EMAIL, CS_INVALID_FIRST_NAME_CHARACTERS,
                CS_INVALID_LAST_NAME_CHARACTERS, CS_INVALID_POSTCODE, CS_EXACT_1, CS_RESULTLENGTH, CS_STARTCOUNT, CS_ENDCOUNT,
                CS_CARDID);
    }

    public static CustomerSearchCmdImpl createSearch(Long customerId, String cardNumber, String email, String firstName,
                                                     String lastName, String postcode, Boolean exact, Integer resultLength,
                                                     Integer startCount, Integer endCount, Long cardId) {
        return new CustomerSearchCmdImpl(customerId, cardNumber, email, firstName, lastName, postcode, exact.toString(),
                resultLength, startCount, endCount, cardId);
    }

    public static Object[] getSearchResult() {
        return createSearchResult(SR_ID_1, SR_FIRST_NAME_1, SR_LAST_NAME_1, SR_OYSTERNUMBER, SR_STATUS, SR_HOUSENAMENUMBER,
                SR_STREET, SR_TOWN, SR_COUNTY, SR_COUNTRY, SR_POSTCODE, SR_CALLS);
    }

    public static Object[] createSearchResult(Long id, String firstName, String lastName, String oysterNumber, String status,
                                              String houseNameNumber, String street, String town, String county, String country,
                                              String postcode, Integer calls) {
        Object[] sr =
                new Object[]{BigDecimal.valueOf(id), firstName, lastName, oysterNumber, status, houseNameNumber, street, town,
                        county, country, postcode, BigDecimal.valueOf(calls)};
        return sr;
    }

    public static CustomerSearchResultDTO getSearchResultObject() {
        return createSearchResultObject(SR_ID_1, SR_FIRST_NAME_1, SR_LAST_NAME_1, SR_OYSTERNUMBER, SR_STATUS,
                SR_HOUSENAMENUMBER, SR_STREET, SR_COUNTY, SR_COUNTRY, SR_POSTCODE, SR_CALLS);
    }

    public static CustomerSearchResultDTO createSearchResultObject(Long id, String firstName, String lastName,
                                                                   String oysterNumber, String status, String houseNameNumber,
                                                                   String street, String county, String country,
                                                                   String postcode, Integer calls) {
        return new CustomerSearchResultDTO(id, firstName, lastName, oysterNumber, status, houseNameNumber, street, null, county,
                country, postcode);
    }

    public static String getSearchResultErrorResponseFirstNameLength() {
        return FIRSTNAME_LENGTH_ERROR;
    }

    public static String getSearchResultExpectedErrorResponseFirstNameLength() {
        return FIRSTNAME_LENGTH_EXPECTED_ERROR;
    }

    public static String getSearchResultErrorResponseLastNameLength() {
        return LASTNAME_LENGTH_ERROR;
    }

    public static String getSearchResultExpectedErrorResponseLastNameLength() {
        return LASTNAME_LENGTH_EXPECTED_ERROR;
    }

    public static String getSearchResultExpectedErrorResponseFirstandLastNameLength() {
        return FIRSTNAME_LASTNAME_LENGTH_EXPECTED_ERROR;
    }

    public static String getSearchResultEmptyFirstNameError() {
        return FIRSTNAME_EMPTY_ERROR;
    }

    public static String getSearchResultEmptyFirstNameExpectedError() {
        return FIRSTNAME_LAST_NAME_EMPTY_EXPECTED_ERROR;
    }
}
