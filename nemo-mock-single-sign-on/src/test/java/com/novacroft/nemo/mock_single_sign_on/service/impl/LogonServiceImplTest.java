package com.novacroft.nemo.mock_single_sign_on.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.Response;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.test_support.UserTestUtil;

public class LogonServiceImplTest {
    private LogonServiceImpl logonService;
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() {
        logonService = new LogonServiceImpl();
        mockRequest = mock(HttpServletRequest.class);
    }

    @Test
    public void testSetCustomerAddress() {
        Customer customer = new Customer();
        logonService.setCustomerAddress(customer);

        assertNotNull(customer.getAddress());
    }

    @Test
    public void testSetCustomerDetails() {
        final Long testCustomerId = 1L;
        Customer customer = new Customer();
        customer.setCustomerId(testCustomerId);
        logonService.setCustomerDetails(customer);

        assertEquals(testCustomerId, customer.getCustomerId());
    }

    @Test
    public void testSetUserAccountDetails() {
        User user = new User();
        logonService.setUserAccount(user);

        assertNotNull(user.getUserAccount());
    }

    @Test
    public void testSecurityAnswers() {
        Customer customer = new Customer();
        logonService.setSecurityAnswers(customer);

        assertNotNull(customer.getSecurityAnswers());
    }

    @Test
    public void testSystemReferences() {
        Customer customer = new Customer();
        logonService.setSystemReferences(customer);

        assertNotNull(customer.getSecurityAnswers());
    }

    @Test
    public void processCookiesShouldReturnNull() {
        Cookie[] cookies = new Cookie[] { new Cookie("name", "value") };
        when(mockRequest.getCookies()).thenReturn(cookies);

        assertNull(logonService.processCookies(mockRequest));
    }

    @Test
    public void processCookiesShouldReturnTokenCookie() {
        Cookie tokenCookie = new Cookie("token", "token value");
        when(mockRequest.getCookies()).thenReturn(new Cookie[] { tokenCookie });

        assertEquals(tokenCookie, logonService.processCookies(mockRequest));
    }

    @Test
    public void shouldCreateInvalidResponseForNullUser() {
        Response actualResult = logonService.createResponse(null);

        assertNotNull(actualResult);
        assertFalse(actualResult.getIsValid());
    }

    @Test
    public void shouldCreateValidResponseForUser() {
        Response actualResult = logonService.createResponse(UserTestUtil.getUser());

        assertNotNull(actualResult);
        assertTrue(actualResult.getIsValid());
    }

    @Test
    public void testConvert() {
        final String testString = "key1:value1,key2:value2";
        Map<String, String> actualResult = logonService.convert(testString);

        assertNotNull(actualResult);
        assertTrue(actualResult.containsKey("key1"));
        assertTrue(actualResult.containsKey("key2"));
    }
}
