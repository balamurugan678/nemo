package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.test_support.CookieTestUtil.PATH_ATTRIBUTE_VALUE;
import static com.novacroft.nemo.test_support.CookieTestUtil.TOKEN_ATTRIBUTE_NAME;
import static com.novacroft.nemo.test_support.CookieTestUtil.TOKEN_ATTRIBUTE_VALUE;
import static com.novacroft.nemo.test_support.CookieTestUtil.createTestTokenCookie;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import javax.servlet.http.Cookie;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpHeaders;

public class CookieUtilTest {
    private HttpHeaders mockHttpHeaders; 
    
    @Before
    public void setUp() {
        mockHttpHeaders = mock(HttpHeaders.class);
    }
    
    @Test
    public void shouldReturnNullIfNoSetcookieField() {
        when(mockHttpHeaders.get(anyString())).thenReturn(null);
        assertNull(CookieUtil.extractCookieFromHeader(mockHttpHeaders, ""));
    }
    
    @Test
    public void shouldReturnNullIfNameNotExist() {
        when(mockHttpHeaders.get(anyString())).thenReturn(Arrays.asList(createTestTokenCookie()));
        assertNull(CookieUtil.extractCookieFromHeader(mockHttpHeaders, "NonExistName"));
    }
    
    @Test
    public void shouldReturnCookie() {
        when(mockHttpHeaders.get(anyString())).thenReturn(Arrays.asList(createTestTokenCookie()));
        
        Cookie actualResult = CookieUtil.extractCookieFromHeader(mockHttpHeaders, TOKEN_ATTRIBUTE_NAME);
        
        assertNotNull(actualResult);
        assertEquals(TOKEN_ATTRIBUTE_VALUE, actualResult.getValue());
        assertEquals(PATH_ATTRIBUTE_VALUE, actualResult.getPath());
    }
}
