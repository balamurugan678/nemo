package com.novacroft.nemo.test_support;


public final class CookieTestUtil {
    
    public static final String TOKEN_ATTRIBUTE_NAME = "token";
    public static final String TOKEN_ATTRIBUTE_VALUE = "test token";
    
    public static final String EXPIRES_ATTRIBUTE_NAME = "Expires";
    public static final String EXPIRES_ATTRIBUTE_VALUE = "Tue, 14-Oct-2014 09:32:51 GMT";
    
    public static final String PATH_ATTRIBUTE_NAME = "Path";
    public static final String PATH_ATTRIBUTE_VALUE = "/TestPath/";
    
    public static final String createTestTokenCookie() {
        return createNameValuePair(TOKEN_ATTRIBUTE_NAME, TOKEN_ATTRIBUTE_VALUE) + " " +
                        createNameValuePair(EXPIRES_ATTRIBUTE_NAME, EXPIRES_ATTRIBUTE_VALUE) + " " +
                        createNameValuePair(PATH_ATTRIBUTE_NAME, PATH_ATTRIBUTE_VALUE);
    }
    
    private static final String createNameValuePair(String name, String value) {
        return name + "=" + value + ";"; 
    }
    private CookieTestUtil() {}
}
