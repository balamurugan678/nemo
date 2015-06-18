package com.novacroft.nemo.test_support;

import javax.servlet.http.Cookie;

public final class CookieTestUtil {
    public static final String TEST_TOKEN = "Test Token";
    
    public static Cookie createTestTokenCookie() {
        return new Cookie("token", TEST_TOKEN);
    }
    
    private CookieTestUtil() {}
}
