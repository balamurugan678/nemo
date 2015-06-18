package com.novacroft.nemo.test_support;

import java.util.HashMap;
import java.util.Map;

public class UserChangeSetTestUtil {
    private static final String PROPERTY_PATH_HOME_NUMBER = "customer.homePhoneNumber";
    private static final String PROPERTY_VALUE_HOME_NUMBER = "12345678901";
    
    private static final String PROPERTY_PATH_TFL_OPT_IN = "customer.tfLMarketingOptIn";
    private static final Boolean PROPERTY_VALUE_TFL_OPT_IN = Boolean.FALSE;
    
    public static Map<String, Object> createValidTestChangeSet() {
        Map<String, Object> testMap = new HashMap<>();
        testMap.put(PROPERTY_PATH_HOME_NUMBER, PROPERTY_VALUE_HOME_NUMBER);
        testMap.put(PROPERTY_PATH_TFL_OPT_IN, PROPERTY_VALUE_TFL_OPT_IN);
        return testMap;
    }
     
}
