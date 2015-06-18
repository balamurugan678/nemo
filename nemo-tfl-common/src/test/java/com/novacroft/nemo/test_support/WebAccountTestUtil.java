package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_2;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.novacroft.nemo.tfl.common.domain.WebAccount;
import com.novacroft.nemo.tfl.common.transfer.WebAccountDTO;

/**
 * Utilities for Web Account tests
 */
public final class WebAccountTestUtil {
    public static final Long WEB_ACCOUNT_ID_1 = 2L;
    public static final String USERNAME_1 = "TestUser1";
    public static final String PASSWORD_1 = "test-password-1";
    public static final String SALT_1 = "test-salt-1";
    public static final String EMAIL_ADDRESS_1 = "test-1@nowhere.com";
    public static final String PHOTO_CARD_NUMBER_1 = "1234";
    public static final String UNFORMATTED_EMAIL_ADDRESS_1 = "test-1@nowhere.com";
    public static final int ANONYMISED_1 = 0;
    public static final int READ_ONLY_1 = 0;
    public static final int PASSWORD_CHANGE_REQUIRED_1 = 0;

    public static final String USERNAME_2 = "test-user-2";
    public static final String EMAIL_ADDRESS_2 = "test-2@nowhere.com";
    public static final Long WEB_ACCOUNT_ID_2 = 4L;
    public static final String PASSWORD_2 = "test-password-2";
    public static final String SALT_2 = "test-salt-2";
    public static final String PHOTO_CARD_NUMBER_2 = "5678";
    public static final String UNFORMATTED_EMAIL_ADDRESS_2 = "test-2@nowhere.com";
    public static final int ANONYMISED_2 = 0;
    public static final int READ_ONLY_2 = 0;
    public static final int PASSWORD_CHANGE_REQUIRED_2 = 0;
    
    public static final int WEB_ACCOUNT_DEACTIVE = 1; 
    public static final int WEB_ACCOUNT_ACTIVE = 0;

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
    
    public static final String VALID_PASSWORD="TestPassword123";
    public static final String SHORT_PASSWORD ="123";
    public static final String UPPERCASE_PASSWORD = "TEST123";
    public static final String NO_DIGIT_PASSWORD = "TestingPassword";
    public static final String SPECIAL_CHARACTER_PASSWORD = "Test@1234";

    public static WebAccountDTO getTestWebAccountDTO1() {
        return getTestWebAccountDTO(WEB_ACCOUNT_ID_1, USERNAME_1, PASSWORD_1, SALT_1, EMAIL_ADDRESS_1, CUSTOMER_ID_1, PHOTO_CARD_NUMBER_1, UNFORMATTED_EMAIL_ADDRESS_1, ANONYMISED_1, READ_ONLY_1,
                PASSWORD_CHANGE_REQUIRED_1, WEB_ACCOUNT_ACTIVE);
    }

    public static WebAccountDTO getTestWebAccountDTO2() {
        return getTestWebAccountDTO(WEB_ACCOUNT_ID_2, USERNAME_2, PASSWORD_2, SALT_2, EMAIL_ADDRESS_2, CUSTOMER_ID_2, PHOTO_CARD_NUMBER_2, UNFORMATTED_EMAIL_ADDRESS_2, ANONYMISED_2, READ_ONLY_2,
                PASSWORD_CHANGE_REQUIRED_2, WEB_ACCOUNT_ACTIVE);
    }
    
    public static WebAccountDTO getTestWebAccountDTO3() {
        return getTestWebAccountDTO(WEB_ACCOUNT_ID_3, USERNAME_2, PASSWORD_2, SALT_2, EMAIL_ADDRESS_2, CUSTOMER_ID_2, PHOTO_CARD_NUMBER_2, UNFORMATTED_EMAIL_ADDRESS_2, ANONYMISED_2, READ_ONLY_2,
                PASSWORD_CHANGE_REQUIRED_2, WEB_ACCOUNT_DEACTIVE);
    }

    public static List<WebAccountDTO> getTestWebAccountDTOList1() {
        List<WebAccountDTO> webAccountDTOs = new ArrayList<WebAccountDTO>();
        webAccountDTOs.add(getTestWebAccountDTO1());
        return webAccountDTOs;
    }

    public static List<WebAccountDTO> getTestWebAccountDTOList2() {
        List<WebAccountDTO> webAccountDTOs = getTestWebAccountDTOList1();
        webAccountDTOs.add(getTestWebAccountDTO2());
        return webAccountDTOs;
    }

    public static WebAccountDTO getTestWebAccountDTO(Long webAccountId, String username, String password, String salt, String emailAddress, Long customerId, String photoCardNumber,
            String unformattedEmailAddress, int anonymised, int readOnly, int passwordChangeRequired, int deactivated) {
        WebAccountDTO webAccountDTO = new WebAccountDTO();
        webAccountDTO.setId(webAccountId);
        webAccountDTO.setUsername(username);
        webAccountDTO.setPassword(password);
        webAccountDTO.setSalt(salt);
        webAccountDTO.setEmailAddress(emailAddress);
        webAccountDTO.setCustomerId(customerId);
        webAccountDTO.setPhotoCardNumber(photoCardNumber);
        webAccountDTO.setUnformattedEmailAddress(unformattedEmailAddress);
        webAccountDTO.setAnonymised(anonymised);
        webAccountDTO.setReadOnly(readOnly);
        webAccountDTO.setPasswordChangeRequired(passwordChangeRequired);
        webAccountDTO.setDeactivated(deactivated);
        return webAccountDTO;
    }

    public static WebAccount getTestWebAccount1() {
        return getTestWebAccount(WEB_ACCOUNT_ID_1, USERNAME_1, new Date(), null, null, USERNAME_1, PASSWORD_1, SALT_1, EMAIL_ADDRESS_1, CUSTOMER_ID_1, PHOTO_CARD_NUMBER_1, EMAIL_ADDRESS_1,
                ANONYMISED_1, READ_ONLY_1, PASSWORD_CHANGE_REQUIRED_1);
    }

    public static WebAccount getTestWebAccount2() {
        return getTestWebAccount(WEB_ACCOUNT_ID_2, USERNAME_2, new Date(), null, null, USERNAME_2, PASSWORD_2, SALT_2, EMAIL_ADDRESS_2, CUSTOMER_ID_2, PHOTO_CARD_NUMBER_2, EMAIL_ADDRESS_2,
                ANONYMISED_2, READ_ONLY_2, PASSWORD_CHANGE_REQUIRED_2);
    }

    public static List<WebAccount> getTestWebAccountList1() {
        List<WebAccount> webAccounts = new ArrayList<WebAccount>();
        webAccounts.add(getTestWebAccount1());
        return webAccounts;
    }

    public static List<WebAccount> getTestWebAccountList2() {
        List<WebAccount> webAccounts = getTestWebAccountList1();
        webAccounts.add(getTestWebAccount2());
        return webAccounts;
    }

    public static WebAccount getTestWebAccount(Long webAccountId, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime, String username, String password,
            String salt, String emailAddress, Long customerId, String photoCardNumber, String unformattedEmailAddress, int anonymised, int readOnly, int passwordChangeRequired) {
        WebAccount webAccount = new WebAccount();
        webAccount.setId(webAccountId);
        webAccount.setCreatedUserId(createdUserId);
        webAccount.setCreatedDateTime(createdDateTime);
        webAccount.setModifiedUserId(modifiedUserId);
        webAccount.setModifiedDateTime(modifiedDateTime);
        webAccount.setUsername(username);
        webAccount.setPassword(password);
        webAccount.setSalt(salt);
        webAccount.setEmailAddress(emailAddress);
        webAccount.setCustomerId(customerId);
        webAccount.setPhotoCardNumber(photoCardNumber);
        webAccount.setUnformattedEmailAddress(unformattedEmailAddress);
        webAccount.setAnonymised(anonymised);
        webAccount.setReadOnly(readOnly);
        webAccount.setPasswordChangeRequired(passwordChangeRequired);
        return webAccount;
    }
}
