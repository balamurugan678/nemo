package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.novacroft.nemo.common.domain.SystemParameter;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;

/**
 * Utilities for System Parameter tests
 */
public class SystemParameterTestUtil {

    public static final String CODE_1 = "test-param-1";
    public static final String VALUE_1 = "test-value-1";
    public static final String PURPOSE_1 = "test-purpose-1";

    public static final String CODE_2 = "test-param-2";
    public static final String VALUE_2 = "test-value-2";
    public static final String PURPOSE_2 = "test-purpose-2";

    public static final String CODE_INTEGER = "test-param-3";
    public static final String VALUE_INTEGER = "99";
    public static final String PURPOSE_INTEGER = "integer-test-param";

    public static final String CODE_FLOAT = "test-param-4";
    public static final String VALUE_FLOAT = "12.34";
    public static final String PURPOSE_FLOAT = "float-test-param";
    
    public static final String SSO_BASE_URL = "singleSignOn.baseUrl";
    public static final String ONLINE_BASE_URL = "onlineSystemBaseURI";
    public static final String BOOLEAN_CODE = "boolean-code";
    public static final String BOOLEAN_FALSE_VALUE = "false";
    public static final String BOOLEAN_TRUE_VALUE = "true";

    public static List<SystemParameterDTO> getTestSystemParameterDTOList2() {
        List<SystemParameterDTO> list = new ArrayList<>();
        list.add(getTestSystemParameterDTO1());
        list.add(getTestSystemParameterDTO2());
        return list;
    }

    public static SystemParameterDTO getTestSystemParameterDTO1() {
        return getTestSystemParameterDTO(CODE_1, VALUE_1, PURPOSE_1);
    }

    public static SystemParameterDTO getTestSystemParameterDTO2() {
        return getTestSystemParameterDTO(CODE_2, VALUE_2, PURPOSE_2);
    }

    public static SystemParameter getTestSystemParameter1() {
        return getTestSystemParameter(CODE_1, VALUE_1, PURPOSE_1);
    }

    public static SystemParameterDTO getTestSystemParameterDTO(String code, String value, String purpose) {
        SystemParameterDTO systemParameterDTO = new SystemParameterDTO();
        systemParameterDTO.setCode(code);
        systemParameterDTO.setValue(value);
        systemParameterDTO.setPurpose(purpose);
        return systemParameterDTO;

    }

    public static SystemParameter getTestSystemParameter(String code, String value, String purpose) {
        SystemParameter systemParameter = new SystemParameter();
        systemParameter.setCode(code);
        systemParameter.setValue(value);
        systemParameter.setPurpose(purpose);
        return systemParameter;
    }

    public static Map<String, String> getTestCache1() {
        Map<String, String> cache = new HashMap<String, String>();
        cache.put(CODE_1, VALUE_1);
        return cache;
    }

    public static Map<String, String> getTestCache2() {
        Map<String, String> cache = getTestCache1();
        cache.put(CODE_2, VALUE_2);
        return cache;
    }
}
