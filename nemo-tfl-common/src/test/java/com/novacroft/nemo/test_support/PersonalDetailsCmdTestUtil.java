package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;

import static com.novacroft.nemo.test_support.AddressTestUtil.*;
import static com.novacroft.nemo.test_support.ContactTestUtil.*;
import static com.novacroft.nemo.test_support.CustomerPreferencesTestUtil.CUSTOMER_PREFERENCES_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.*;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.EMAIL_ADDRESS_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.WEB_ACCOUNT_ID_1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO;

/**
 * Utilities for tests that use PersonalDetailsCmd
 */
public final class PersonalDetailsCmdTestUtil {

    public static PersonalDetailsCmdImpl getTestPersonalDetailsCmd1() {
        return getTestPersonalDetailsCmd(WEB_ACCOUNT_ID_1, CUSTOMER_ID_1, ADDRESS_ID_1, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1,
                        HOUSE_NAME_NUMBER_1, STREET_1, TOWN_1, "", POSTCODE_1, "", VALUE_1, VALUE_2, EMAIL_ADDRESS_1, CONTACT_ID_1, CONTACT_ID_2,
                        CUSTOMER_PREFERENCES_ID_1, null, null, null, null, null, null);
    }
    
    public static PersonalDetailsCmdImpl getTestPersonalDetailsCmdWithCardNumber() {
        return getTestPersonalDetailsCmd(WEB_ACCOUNT_ID_1, CUSTOMER_ID_1, ADDRESS_ID_1, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1,
                        HOUSE_NAME_NUMBER_1, STREET_1, TOWN_1, "", POSTCODE_1, "", VALUE_1, VALUE_2, EMAIL_ADDRESS_1, CONTACT_ID_1, CONTACT_ID_2,
                        CUSTOMER_PREFERENCES_ID_1, null, null, null, null, null, null,CommonCardTestUtil.OYSTER_NUMBER_1);
    }

    public static PersonalDetailsCmdImpl getTestPersonalDetailsCmd(Long webAccountId, Long customerId, Long addressId, String title,
                    String firstName, String initials, String lastName, String houseNameNumber, String street, String town, String county,
                    String postcode, String country, String homePhone, String mobilePhone, String emailAddress, Long homePhoneContactId,
                    Long mobilePhoneContactId, Long customerPreferencesId, String securityOption, String securityPassword, String dateOfBirth,
                    String addressForPostcode, Boolean canTflContact, Boolean canThirdPartyContact) {
        return getTestPersonalDetailsCmd(webAccountId, customerId, addressId, title, firstName, initials, lastName, houseNameNumber, street, town,
                        county, postcode, country, homePhone, mobilePhone, emailAddress, homePhoneContactId, mobilePhoneContactId,
                        customerPreferencesId, securityOption, securityPassword, dateOfBirth, addressForPostcode, canTflContact,
                        canThirdPartyContact, null);
    }

    public static PersonalDetailsCmdImpl getTestPersonalDetailsCmd(Long webAccountId, Long customerId, Long addressId, String title,
                    String firstName, String initials, String lastName, String houseNameNumber, String street, String town, String county,
                    String postcode, String countryCode, String homePhone, String mobilePhone, String emailAddress, Long homePhoneContactId,
                    Long mobilePhoneContactId, Long customerPreferencesId, String securityOption, String securityPassword, String dateOfBirth,
                    String addressForPostcode, Boolean canTflContact, Boolean canThirdPartyContact, String cardNumber) {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setWebAccountId(webAccountId);
        cmd.setCustomerId(customerId);
        cmd.setAddressId(addressId);
        cmd.setTitle(title);
        cmd.setFirstName(firstName);
        cmd.setInitials(initials);
        cmd.setLastName(lastName);
        cmd.setHouseNameNumber(houseNameNumber);
        cmd.setStreet(street);
        cmd.setTown(town);
        cmd.setCounty(county);
        cmd.setPostcode(postcode);
        cmd.setCountry(getTestCountryDTO(countryCode));
        cmd.setHomePhone(homePhone);
        cmd.setMobilePhone(mobilePhone);
        cmd.setEmailAddress(emailAddress);
        cmd.setHomePhoneContactId(homePhoneContactId);
        cmd.setMobilePhoneContactId(mobilePhoneContactId);
        cmd.setCustomerPreferencesId(customerPreferencesId);
        cmd.setSecurityOption(securityOption);
        cmd.setSecurityPassword(securityPassword);
        cmd.setDateOfBirth(dateOfBirth);
        cmd.setAddressForPostcode(addressForPostcode);
        cmd.setCanTflContact(canTflContact);
        cmd.setCanThirdPartyContact(canThirdPartyContact);
        cmd.setCardNumber(cardNumber);
        return cmd;
    }
    public static PersonalDetailsCmdImpl getTestPersonalDetailsCmdForNonDeactivatedAccountCmd() {
    	  PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
    	  cmd.setCustomerDeactivated(Boolean.FALSE);
    	  return cmd;
    }
    public static PersonalDetailsCmdImpl getTestPersonalDetailsCmdForDeactivatedAccountCmd() {
	  	  PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
	  	  cmd.setCustomerDeactivated(Boolean.TRUE);
    	  return cmd;
    }
    
    private PersonalDetailsCmdTestUtil() {
    }
}
