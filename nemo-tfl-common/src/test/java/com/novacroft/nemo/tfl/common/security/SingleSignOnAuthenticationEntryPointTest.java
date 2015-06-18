package com.novacroft.nemo.tfl.common.security;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSecurityService;

public class SingleSignOnAuthenticationEntryPointTest {
    private SingleSignOnAuthenticationEntryPoint ssoEntryPoint;
    private SingleSignOnSecurityService mockSsoSecurityService;
    private SystemParameterService mockSystemParameterService;
    private HttpServletResponse mockResponse;
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() {
        ssoEntryPoint = mock(SingleSignOnAuthenticationEntryPoint.class, CALLS_REAL_METHODS);

        mockSsoSecurityService = mock(SingleSignOnSecurityService.class);
        mockSystemParameterService = mock(SystemParameterService.class);

        ssoEntryPoint.singleSignOnSecurityService = mockSsoSecurityService;
        ssoEntryPoint.systemParameterService = mockSystemParameterService;
        
        mockRequest = mock(HttpServletRequest.class);
        mockResponse = mock(HttpServletResponse.class);
    }

    @Test
    public void shouldCommenceIfNotSingleSignOn() {
        SingleSignOnAuthenticationEntryPoint spyEntryPoint = spy(new SingleSignOnAuthenticationEntryPoint());
        when(mockSystemParameterService.getBooleanParameterValue(anyString())).thenReturn(false);
        String exceptionMessage = "";
        try {
            doNothing().when((LoginUrlAuthenticationEntryPoint) spyEntryPoint)
                       .commence(any(HttpServletRequest.class), any(HttpServletResponse.class), any(AuthenticationException.class));
            
            spyEntryPoint.commence(mockRequest, mockResponse, null);
            
            verify((LoginUrlAuthenticationEntryPoint) spyEntryPoint).commence(mockRequest, mockResponse, null);
        } catch (IOException | ServletException e) {
            exceptionMessage = e.getMessage();
        }
        
        assertEquals("", exceptionMessage);
    }
    
    @Test
    public void shouldRedirectIfSingleSignOn() {
        when(mockSystemParameterService.getBooleanParameterValue(anyString())).thenReturn(true);
        doReturn("testRedirectUrl").when(ssoEntryPoint).buildRedirectUrl(mockRequest);
        String exceptionMessage = "";
        try {
            doNothing().when(mockResponse).sendRedirect(anyString());
            ssoEntryPoint.commence(mockRequest, mockResponse, null);
            verify(mockResponse).sendRedirect(anyString());
        } catch (IOException | ServletException e) {
            exceptionMessage = e.getMessage();
        }
        assertEquals("", exceptionMessage);
    }
    
    @Test
    public void testBuildRedirectUrl() {
        final String mockReturnedUrl = "testReturnUrl";
        Cookie[] cookies = new Cookie[] {new Cookie("token", "testToken")};
        when(mockRequest.getCookies()).thenReturn(cookies);
        when(mockRequest.getServletPath()).thenReturn("/testPath.htm");
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(mockReturnedUrl);
        
        final String expectedUrl = mockReturnedUrl + "?redirectUrl=/testPath.htm";
        assertEquals(expectedUrl, ssoEntryPoint.buildRedirectUrl(mockRequest));
    }
    
    @Test
    public void buildRedirectUrlShouldReturnNull() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(null);
        assertNull(ssoEntryPoint.buildRedirectUrl(mockRequest));
    }
}
