package com.novacroft.nemo.test_support;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.configuration.PostProfile;

import java.util.ArrayList;
import java.util.List;

/**
 * PostProfile test fixtures and utilities
 */
public final class ProfileTestUtil {
    public static final String TEST_NAME_1 = "test-profile-1";
    public static final String TEST_ID_1 = "01";
    public static final String TEST_COMPANY_NAME_1 = "test-company-name-1";
    public static final Boolean TEST_ACTIVE_1 = Boolean.TRUE;
    public static final String TEST_CUSTOM_RESPONSE_URL_1 = "http://test-1/";

    public static final String TEST_NAME_2 = "test-profile-2";
    public static final String TEST_ID_2 = "02";
    public static final String TEST_COMPANY_NAME_2 = "test-company-name-2";
    public static final Boolean TEST_ACTIVE_2 = Boolean.TRUE;
    public static final String TEST_CUSTOM_RESPONSE_URL_2 = "http://test-2/";

    public static PostProfile getTestProfile1() {
        return getTestProfile(TEST_NAME_1, TEST_ID_1, TEST_COMPANY_NAME_1, TEST_ACTIVE_1, TEST_CUSTOM_RESPONSE_URL_1);
    }

    public static PostProfile getTestProfile2() {
        return getTestProfile(TEST_NAME_2, TEST_ID_2, TEST_COMPANY_NAME_2, TEST_ACTIVE_2, TEST_CUSTOM_RESPONSE_URL_2);
    }

    public static List<PostProfile> getTestProfileList1() {
        List<PostProfile> postProfiles = new ArrayList<PostProfile>();
        postProfiles.add(getTestProfile1());
        return postProfiles;
    }

    public static List<PostProfile> getTestProfileList2() {
        List<PostProfile> postProfiles = getTestProfileList1();
        postProfiles.add(getTestProfile2());
        return postProfiles;
    }

    public static PostProfile getTestProfile(String name, String id, String companyName, Boolean active,
                                             String customResponseUrl) {
        return new PostProfile(name, id, companyName, active, customResponseUrl);
    }

    private ProfileTestUtil() {
    }
}

