package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_USER_NAME;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.createMockResponseDTO;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.novacroft.nemo.common.application_service.TokenGenerator;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSessionService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnTokenService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.SingleSignOnDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

@RunWith(MockitoJUnitRunner.class)
public class SingleSignOnSecurityServiceImplTest {
    private static final String TEST_PASSWORD = "test password";
    private static final String TEST_TOKEN_VALUE = "mocktoken";
    private static final String TEST_NEW_SALT = "new salt";
    private static final String TEST_NEW_PASSWORD = "test password";
    private static final String TEST_SESSION_ID = "test session id";
   
    @Mock
    private SingleSignOnSecurityServiceImpl service;
    @Mock 
    private SingleSignOnTokenService mockSsoTokenService;
    @Mock 
    private SingleSignOnDataService mockSsoDataService;
    @Mock 
    private TokenGenerator mockTokenGenerator;
    @Mock
    private PasswordEncoder mockPasswordEncoder;
    @Mock
    private NemoUserContext mockContext;
    @Mock
    private CustomerDataService mockCustomerDataService;
    @Mock
    private Cookie mockCookie;
    @Mock
    private SingleSignOnSessionService mockSsoSessionService;
    
    private MockHttpServletRequest mockRequest;
    private MockHttpServletResponse mockResponse;

    @Before
    public void setUp() {
        service.singleSignOnTokenService = mockSsoTokenService;
        service.singleSignOnDataService = mockSsoDataService;
        service.tokenGenerator = mockTokenGenerator;
        service.passwordEncoder = mockPasswordEncoder;
        service.nemoUserContext = mockContext;
        service.customerDataService = mockCustomerDataService;
        service.singleSignOnSessionService = mockSsoSessionService;
        
        mockRequest = new MockHttpServletRequest();
        mockResponse = new MockHttpServletResponse();
        
        when(mockCookie.getValue()).thenReturn(TEST_TOKEN_VALUE);
        when(mockSsoDataService.checkAndUpdateLocalData(any(SingleSignOnResponseDTO.class))).thenReturn(true);
        doNothing().when(mockContext).setUserName(anyString());
        doCallRealMethod().when(service).createSingleSignOnResponseDTO(any(Object.class));
    }
    
    @Test(expected=BadCredentialsException.class)
    public void loginShouldThrowExceptionIfTokenIsNull() {
        doCallRealMethod().when(service).login(anyString(), anyString(), any(HttpServletRequest.class), any(HttpServletResponse.class));
        
        doReturn("").when(service).getContent(anyString());
        when(mockSsoTokenService.requestToken(anyString(), anyString(), anyString(), anyString())).thenReturn(null);
        service.login("", "", mockRequest, mockResponse);
    }
    
    @Test(expected=BadCredentialsException.class)
    public void loginShouldThrowExceptionIfResponseIsNotValid() {       
        doCallRealMethod().when(service).login(anyString(), anyString(), any(HttpServletRequest.class), any(HttpServletResponse.class));
        
        SingleSignOnResponseDTO invalidResponse = createMockResponseDTO();
        invalidResponse.setIsValid(false);
        when(mockSsoTokenService.requestToken(anyString(), anyString(), anyString(), anyString())).thenReturn(mockCookie);
        when(mockSsoTokenService.validateTokenAndRetrieveUserDetails(anyString())).thenReturn(invalidResponse);
        service.login("", "", mockRequest, mockResponse);
    }
    
    @Test
    public void shouldLoginSuccessful() {
        doCallRealMethod().when(service).login(anyString(), anyString(), any(HttpServletRequest.class), any(HttpServletResponse.class));
        
        SingleSignOnResponseDTO validResponse = createMockResponseDTO();
        validResponse.setIsValid(true);
        when(mockSsoTokenService.requestToken(anyString(), anyString(), anyString(), anyString())).thenReturn(mockCookie);
        when(mockSsoTokenService.validateTokenAndRetrieveUserDetails(anyString())).thenReturn(validResponse);
        when(service.autoLogin(anyString(), any(), any(HttpServletRequest.class))).thenReturn(true);
        
        service.login(SSO_USER_NAME, TEST_PASSWORD, mockRequest, mockResponse);
        
        verify(mockSsoTokenService).requestToken(anyString(), anyString(), anyString(), anyString());
        verify(mockSsoTokenService).validateTokenAndRetrieveUserDetails(TEST_TOKEN_VALUE);
        verify(service).autoLogin(anyString(), any(), any(HttpServletRequest.class));
    }
    
    @Test
    public void shouldSaveTokenAsPassword() {
        doCallRealMethod().when(service).saveTokenAsPassword(anyString(), anyString());
        
        CustomerDTO mockCustomer = mock(CustomerDTO.class);
        doNothing().when(mockCustomer).setSalt(anyString());
        doNothing().when(mockCustomer).setPassword(anyString());
        
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(mockCustomer);
        when(mockTokenGenerator.createSalt(anyInt())).thenReturn(TEST_NEW_SALT);
        when(mockPasswordEncoder.encode(anyString())).thenReturn(TEST_NEW_PASSWORD);
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).thenReturn(null);
        
        service.saveTokenAsPassword(SSO_USER_NAME, "");
        
        verify(mockCustomerDataService).findByUsernameOrEmail(SSO_USER_NAME);
        verify(mockTokenGenerator).createSalt(anyInt());
        verify(mockPasswordEncoder).encode(anyString());
        verify(mockCustomer).setSalt(TEST_NEW_SALT);
        verify(mockCustomer).setPassword(TEST_NEW_PASSWORD);
        verify(mockCustomerDataService).createOrUpdate(mockCustomer);
    }
    
    @Test
    public void checkSessionActiveShouldInvokeTokenService() {
        doCallRealMethod().when(service).checkSessionActive(anyString(), anyString());
        when(mockSsoTokenService.checkIfSessionActive(anyString(), anyString(), anyString())).thenReturn(null);
        
        service.checkSessionActive(TEST_TOKEN_VALUE, TEST_SESSION_ID);
        
        verify(mockSsoTokenService).checkIfSessionActive(anyString(), anyString(), anyString());
    }
    
    @Test
    public void autoLoginShouldReturnFalse() {
        doCallRealMethod().when(service).autoLogin(anyString(), any(), any(HttpServletRequest.class));
        
        assertFalse(service.autoLogin(TEST_TOKEN_VALUE, null, mockRequest));
    }
    
    @Test
    public void autoLoginShouldReturnTrue() {
        doCallRealMethod().when(service).autoLogin(anyString(), any(), any(HttpServletRequest.class));
        
        SingleSignOnResponseDTO validResponse = createMockResponseDTO();
        validResponse.setIsValid(true);
        assertTrue(service.autoLogin(TEST_TOKEN_VALUE, validResponse, mockRequest));
    }
    
    @Test
    public void shouldUpdateUserDetailAndLogin() {
        doCallRealMethod().when(service)
            .updateUserDetailAndLogin(anyString(), any(SingleSignOnResponseDTO.class), any(HttpServletRequest.class));
        
        when(mockSsoDataService.checkAndUpdateLocalData(any(SingleSignOnResponseDTO.class))).thenReturn(true);
        doNothing().when(service).saveTokenAsPassword(anyString(), anyString());
        doNothing().when(mockSsoSessionService).addSessionByToken(anyString(), any(HttpSession.class));
                     
        service.updateUserDetailAndLogin(TEST_TOKEN_VALUE, createMockResponseDTO(), mockRequest);
        
        verify(mockSsoDataService).checkAndUpdateLocalData(any(SingleSignOnResponseDTO.class));
        verify(service).saveTokenAsPassword(SSO_USER_NAME, TEST_TOKEN_VALUE);
        verify(mockContext).setUserName(SSO_USER_NAME);
        verify(mockSsoSessionService).addSessionByToken(anyString(), any(HttpSession.class));
    }
    
    @Test
    public void singleSignOutShouldInvalidateSession() {
        doCallRealMethod().when(service).singleSignOut(anyString());
        
        HttpSession mockSession = mock(HttpSession.class);
        when(mockSsoSessionService.removeSessionByToken(anyString())).thenReturn(mockSession);
        doNothing().when(mockSession).invalidate();
        
        service.singleSignOut(TEST_TOKEN_VALUE);
        
        verify(mockSsoSessionService).removeSessionByToken(TEST_TOKEN_VALUE);
        verify(mockSession).invalidate();
    }
}
