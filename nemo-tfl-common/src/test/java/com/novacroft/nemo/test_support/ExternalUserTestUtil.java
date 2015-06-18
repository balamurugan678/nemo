package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import com.novacroft.nemo.tfl.common.domain.ExternalUser;
import com.novacroft.nemo.tfl.common.transfer.ExternalUserDTO;

/**
 * Utilities for ExternalUser tests
 */
public final class ExternalUserTestUtil {
    public static final Long ID_1 = 1L;
    public static final Long ID_2 = 2L;

    public static final String EMAIL_ADDRESS_1 = "test-1@nowhere.com";
    public static final String USERNAME_1 = "TestUser1";

    public static final String USERNAME_2 = "TestUser2";
    public static final String PASSWORD_2 = "test-password-2";
    public static final String PASSWORD_1 = "test-password-1";
    public static final String ROLE_1 = "ROLE_USER";
    public static final Long EXTERNAL_ID_1 = 21l;
    public static final String ROLE_2 = "ROLE_USER";
    public static final Long EXTERNAL_ID_2 = 22l;

    public static List<ExternalUser> getTestExternalUserList1() {
        List<ExternalUser> customerList = new ArrayList<ExternalUser>();
        customerList.add(getExternalUser1());
        return customerList;
    }
    
    public static List<ExternalUser> getTestExternalUserList2() {
        List<ExternalUser> customerList = getTestExternalUserList1();
        customerList.add(getExternalUser2());
        return customerList;
    }
    
    public static ExternalUser getExternalUser1() {
        return createExternalUser(ID_1, USERNAME_1, PASSWORD_1, ROLE_1, EXTERNAL_ID_1);
    }

    public static ExternalUser getExternalUser2() {
        return createExternalUser(ID_2, USERNAME_2, PASSWORD_2, ROLE_2, EXTERNAL_ID_2);
    }

    public static ExternalUser createExternalUser(Long id, String userName, String password, String role, Long externalId) {
        ExternalUser c = new ExternalUser();
        c.setId(id);
        c.setUsername(userName);
        c.setPassword(password);
        c.setRole(role);
        c.setExternalId(externalId);
        return c;
    }
    
    
    public static ExternalUserDTO createExternalUserDTO(Long id, String userName, String password, String role, Long externalId) {
        ExternalUserDTO c = new ExternalUserDTO();
        c.setId(id);
        c.setUsername(userName);
        c.setPassword(password);
        c.setRole(role);
        c.setExternalId(externalId);
        return c;
    }
    
    public static ExternalUserDTO getExternalUserDTO1() {
        return createExternalUserDTO(ID_1, USERNAME_1, PASSWORD_1, ROLE_1, EXTERNAL_ID_1);
    }

}
