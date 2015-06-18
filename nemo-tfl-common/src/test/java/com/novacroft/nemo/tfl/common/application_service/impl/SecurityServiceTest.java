package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.SecurityServiceTestUtil.BASIC_AUTHORIZATION_HEADER;
import static com.novacroft.nemo.test_support.SecurityServiceTestUtil.NON_BASIC_AUTHORIZATION_HEADER;
import static com.novacroft.nemo.test_support.SecurityServiceTestUtil.USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.novacroft.nemo.common.application_service.TokenGenerator;
import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.support.NemoUserContextImpl;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/**
 * Security service unit tests
 * <p/>
 */
public class SecurityServiceTest {
    private static final String TEST_CREATED_SALT = "created salt";
    private static final String TEST_SALT = "salt";
    private static final String TEST_CLEAR_PASSWORD = "clear";
    private static final String TEST_ENCODED_PASSWORD = "encoded password";
    private static final String TEST_USER_NAME = "nemo";
    private static final String TEST_PASSWORD = "nemo";
    
    private SecurityServiceImpl securityService;
    private HttpServletRequest mockRequest;
    private AgentAuthenticationProviderServiceImpl mockAgentAuthenticationProvider;
    private AuthenticationProvider mockAuthenticationProvider;
    private HttpSession mockSession;
    private NemoUserContext mockNemoUserContext;
    private CustomerDataService mockCustomerDataService;
    private TokenGenerator mockTokenGenerator;
    private PasswordEncoder mockPasswordEncoder;
    private Authentication mockAuthentication; 

    @Before
    public void setup() {
        securityService = new SecurityServiceImpl();
        
        mockRequest = mock(HttpServletRequest.class);
        mockAgentAuthenticationProvider = mock(AgentAuthenticationProviderServiceImpl.class);
        mockAuthenticationProvider = mock(AuthenticationProvider.class);
        mockSession = mock(HttpSession.class);
        mockNemoUserContext = mock(NemoUserContext.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockTokenGenerator = mock(TokenGenerator.class);
        mockPasswordEncoder = mock(PasswordEncoder.class);
        mockAuthentication = mock(Authentication.class);

        securityService.agentAuthenticationProvider = mockAgentAuthenticationProvider;
        securityService.authenticationProvider = mockAuthenticationProvider;
        securityService.nemoUserContext = mockNemoUserContext;
        securityService.customerDataService = mockCustomerDataService;
        securityService.tokenGenerator = mockTokenGenerator;
        securityService.passwordEncoder = mockPasswordEncoder;
        
        when(mockAgentAuthenticationProvider.authenticateAgent(any(Authentication.class), any(HttpServletRequest.class)))
            .thenReturn(null);
        when(mockAuthenticationProvider.authenticate(any(Authentication.class))).thenReturn(null);
        when(mockRequest.getSession(true)).thenReturn(mockSession);
        when(mockTokenGenerator.createSalt(SecurityServiceImpl.SALT_LENGTH)).thenReturn(TEST_CREATED_SALT);
        when(mockPasswordEncoder.encode(anyString())).thenReturn(TEST_ENCODED_PASSWORD);
    }

    @Test
    public void checkLoginAsAgent() {
        Authentication presentedAuthentication = new UsernamePasswordAuthenticationToken(TEST_USER_NAME, TEST_PASSWORD);
        when(mockRequest.getParameter(PageParameter.AGENT_ID)).thenReturn(StringUtil.EMPTY_STRING);
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(StringUtil.EMPTY_STRING);
        
        securityService.login(TEST_USER_NAME, TEST_PASSWORD, mockRequest);
        verify(mockNemoUserContext).setUserName(TEST_USER_NAME);
        verify(mockAgentAuthenticationProvider).authenticateAgent(presentedAuthentication, mockRequest);
    }

    @Test
    public void checkLoginAsNonAgentWhenAgentIdIsNull() {
        Authentication presentedAuthentication = new UsernamePasswordAuthenticationToken(TEST_USER_NAME, TEST_PASSWORD);
        when(mockRequest.getParameter(PageParameter.AGENT_ID)).thenReturn(null);
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(StringUtil.EMPTY_STRING);
        
        securityService.login(TEST_USER_NAME, TEST_PASSWORD, mockRequest);
        verify(mockNemoUserContext).setUserName(TEST_USER_NAME);
        verify(mockAuthenticationProvider).authenticate(presentedAuthentication);
    }

    @Test
    public void checkLoginAsNonAgentWhenCustomerIdIsNull() {
        Authentication presentedAuthentication = new UsernamePasswordAuthenticationToken(TEST_USER_NAME, TEST_PASSWORD);
        when(mockRequest.getParameter(PageParameter.AGENT_ID)).thenReturn(StringUtil.EMPTY_STRING);
        when(mockRequest.getParameter(PageParameter.CUSTOMER_ID)).thenReturn(null);
        
        securityService.login(TEST_USER_NAME, TEST_PASSWORD, mockRequest);
        verify(mockNemoUserContext).setUserName(TEST_USER_NAME);
        verify(mockAuthenticationProvider).authenticate(presentedAuthentication);
    }
    
    @Test
    public void shouldLogout() {
        securityService.logout(mockRequest);
        verify(mockSession).setAttribute(SecurityServiceImpl.SPRING_SECURITY_CONTEXT_ATTRIBUTE, null);
        verify(mockSession).setAttribute(SecurityServiceImpl.SPRING_SECURITY_SAVED_REQUEST, null);
        verify(mockNemoUserContext).setUserName(NemoUserContextImpl.DEFAULT_USER_NAME);
    }
    
    @Test
    public void shouldGetLoggedInCustomer() {
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
        securityService.getLoggedInCustomer();
        verify(mockCustomerDataService).findByUsernameOrEmail(StringUtil.EMPTY_STRING);
    }

    @Test
    public void shouldGenerateSalt() {
        assertEquals(TEST_CREATED_SALT, securityService.generateSalt());
        verify(mockTokenGenerator).createSalt(SecurityServiceImpl.SALT_LENGTH);
    }
    
    @Test
    public void shouldHashPassword() {
        assertEquals(TEST_ENCODED_PASSWORD, 
                        securityService.hashPassword(TEST_SALT, TEST_CLEAR_PASSWORD));
        verify(mockPasswordEncoder).encode(TEST_SALT + TEST_CLEAR_PASSWORD);
    }
    
    @Test
    public void shouldUpdatePassword() {
        CustomerDTO mockCustomerDTO = mock(CustomerDTO.class);
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).thenReturn(mockCustomerDTO);
        CustomerDTO customerDTO = getTestCustomerDTO1();
        assertEquals(mockCustomerDTO, 
                        securityService.updatePassword(customerDTO, StringUtil.EMPTY_STRING));
        assertEquals(TEST_CREATED_SALT, customerDTO.getSalt());
        assertEquals(TEST_ENCODED_PASSWORD, customerDTO.getPassword());
    }
    
    @Test
    public void isLoggedInShouldReturnTrue() {
        SecurityServiceImpl mockService = mock(SecurityServiceImpl.class);
        when(mockService.isLoggedIn()).thenCallRealMethod();
        when(mockService.getLoggedInUsername()).thenReturn(TEST_USER_NAME);
        assertTrue(mockService.isLoggedIn());
    }
    
    @Test
    public void isLoggedInShouldReturnFalse() {
        SecurityServiceImpl mockService = mock(SecurityServiceImpl.class);
        when(mockService.isLoggedIn()).thenCallRealMethod();
        when(mockService.getLoggedInUsername()).thenReturn(SecurityServiceImpl.ANONYMOUS_USER.toLowerCase());
        assertFalse(mockService.isLoggedIn());
    }
    
    @Test
    public void shouldGetUsernameFromAuthorizationHeader() {
        when(mockRequest.getHeader(anyString())).thenReturn(BASIC_AUTHORIZATION_HEADER);
        String username = securityService.getUsernameFromAuthorizationHeader(mockRequest);
        assertNotNull(username);
        assertEquals(USERNAME, username);
    }

    @Test
    public void shouldNotGetUsernameFromAuthorizationHeaderIfAuthorizationIsNull() {
        when(mockRequest.getHeader(anyString())).thenReturn(null);
        String username = securityService.getUsernameFromAuthorizationHeader(mockRequest);
        assertNull(username);
    }

    @Test
    public void shouldNotGetUsernameFromAuthorizationHeaderIfAuthorizationIsNotBasic() {
        when(mockRequest.getHeader(anyString())).thenReturn(NON_BASIC_AUTHORIZATION_HEADER);
        String username = securityService.getUsernameFromAuthorizationHeader(mockRequest);
        assertNull(username);
    }
}
