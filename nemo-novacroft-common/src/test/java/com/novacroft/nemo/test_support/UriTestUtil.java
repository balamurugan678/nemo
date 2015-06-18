package com.novacroft.nemo.test_support;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Utilities for URI tests
 */
public class UriTestUtil {
    public final static String SCHEME_1 = "HTTP";
    public final static String HOST_1 = "test-server";
    public final static String HOST_2 = "test\\&server";
    public final static int PORT_1 = 8181;
    public final static String PATH_1 = "/test-application";
    public final static String PATH_2 = "test-path";
    public final static String PATH_3 = "test\\:path";
    public final static String URI_1 = "http://test-server:8282/test-application";
    public final static String PARAMETER_1 = "test-param";
    public final static String VALUE_1 = "test-value";

    public static String BASE_URI_STRING_1 = "http://test-host/test-application";
    public static URI BASE_URI_1 = null;
    public static String PAGE_URI_STRING_1 = "http://test-host/test-application/TestPage.htm";
    public static URI PAGE_URI_1 = null;

    static {
        try {
            BASE_URI_1 = new URI(BASE_URI_STRING_1);
            PAGE_URI_1 = new URI(PAGE_URI_STRING_1);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
