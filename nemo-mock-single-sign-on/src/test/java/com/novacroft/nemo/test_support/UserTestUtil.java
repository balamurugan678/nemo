package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.AddressTestUtil.COUNTY_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.HOUSE_NAME_NUMBER_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.POSTCODE_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.STREET_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.TOWN_1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_2;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EMAIL_ADDRESS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.INITIALS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.PASSWORD_3;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TITLE_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;

import com.novacroft.nemo.mock_single_sign_on.command.UserDetailsCmd;
import com.novacroft.nemo.mock_single_sign_on.domain.AccountStatus;
import com.novacroft.nemo.mock_single_sign_on.domain.Address;
import com.novacroft.nemo.mock_single_sign_on.domain.AddressType;
import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityAnswer;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityQuestion;
import com.novacroft.nemo.mock_single_sign_on.domain.SystemName;
import com.novacroft.nemo.mock_single_sign_on.domain.SystemReferences;
import com.novacroft.nemo.mock_single_sign_on.domain.Title;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.domain.UserAccount;

public class UserTestUtil {
    public static final String OYSTER = "Oyster";
    public static final String STATUS = "STATUS";
    public static final String QUESTION = "Test Question";
    public static final String REF_SYS_CUSTOMER_ID = "1";
    public static final String UPDATED_FIRST_NAME = "updated first name";
    public static final String UPDATED_LAST_NAME = "updated last name";
    
    public static final Long CUSTOMER_ID = 1L;
    public static final Long ID = 0L;
    
    public static final Integer INT_ID = 0;
    public static final Integer INT_COUNTRY_CODE_UK = 826;

    public static UserDetailsCmd getUserDetailsCmdWithStatus() {
        return new UserDetailsCmd(USERNAME_1, PASSWORD_3, CUSTOMER_ID, TITLE_1, FIRST_NAME_1, INITIALS_1, LAST_NAME_1, VALUE_1, VALUE_2, VALUE_1,
                        EMAIL_ADDRESS_1, "Question", "Answer", Boolean.TRUE, Boolean.TRUE, HOUSE_NAME_NUMBER_1, HOUSE_NAME_NUMBER_1, STREET_1,
                        TOWN_1, COUNTY_1, POSTCODE_1, getTestCountryDTO1(), "Home", STATUS);
    }

    public static User getUserWrapper() {
        User user = new User();
        user.setUser(getUser());
        return user;
    }

    public static User getUser() {
        User user = new User();
        user.setCustomer(getCustomer());
        user.setUserAccount(getUserAccount());
        return user;
    }

    public static Customer getCustomer() {
        Customer customer = new Customer(CUSTOMER_ID, getTitle(), FIRST_NAME_1, INITIALS_1, LAST_NAME_1, getAddress(), VALUE_1, VALUE_2, VALUE_1,
                        EMAIL_ADDRESS_1, true, true);
        customer.getSecurityAnswers().add(getSecurityAnswer());
        customer.getSystemReferences().add(getSystemReferences());
        return customer;
    }

    public static Title getTitle() {
        return new Title(INT_ID, "Mr");
    }

    public static Address getAddress() {
        Address address = new Address();
        address.setHouseName(HOUSE_NAME_NUMBER_1);
        address.setHouseNo(HOUSE_NAME_NUMBER_1);
        address.setStreetName(STREET_1);
        address.setCity(TOWN_1);
        address.setPostCode(POSTCODE_1);
        address.setAddressLine3(COUNTY_1);
        address.setCountry(getTestCountryDTO1().getName());
        address.setCountryCode(INT_COUNTRY_CODE_UK);
        address.setAddressType(new AddressType(ID, "Home"));
        return address;
    }

    public static UserAccount getUserAccount() {
        return new UserAccount(USERNAME_1, new AccountStatus(null), null);
    }

    private static SecurityAnswer getSecurityAnswer() {
        return new SecurityAnswer(ID, "Answer", getSecurityQuestion());
    }

    private static SecurityQuestion getSecurityQuestion() {
        return new SecurityQuestion(ID, QUESTION);
    }

    private static SystemReferences getSystemReferences() {
        return new SystemReferences(getSystemName(), REF_SYS_CUSTOMER_ID, "nemo");
    }

    private static SystemName getSystemName() {
        return new SystemName(ID, OYSTER);
    }
}
