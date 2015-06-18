package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.SystemParameterTestUtil.ONLINE_BASE_URL;
import static com.novacroft.nemo.test_support.SystemParameterTestUtil.SSO_BASE_URL;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.PASSWORD_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.TokenGenerator;
import com.novacroft.nemo.common.application_service.impl.TokenGeneratorImpl;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSecurityService;
import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.DeactivatedWebAccountOnlineLoginValidator;
import com.novacroft.nemo.tfl.common.security.TflPasswordEncoder;

/**
 * Security controller test.
 */
public class SecurityControllerTest {
    private static final String TEST_EXCEPTION_MESSAGE = "Test Error";
    private static final String TEST_TOKEN_VALUE = "Test Token";
    private static final String TEST_INDEX_PAGE = "/index.jsp";
    private static final String TEST_OTHER_PAGE = "/other.htm";
    private static final String TEST_OTHER_VIEW_NAME = "other.htm";
    private static final String SSO_SERVER_ID = "mockSsoServerID";
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityControllerTest.class);
    private SecurityController controller;

    private DeactivatedWebAccountOnlineLoginValidator mockDeactivatedWebAccountOnlineLoginValidator;
    private SecurityService mockSecurityService;
    private SingleSignOnSecurityService mockSsoSecurityService;
    private SystemParameterService mockSystemParameterService;

    private LoginCmdImpl mockCmd;
    private Errors mockErrors;
    private HttpServletRequest mockRequest;
    private HttpSession mockSession;
    private SavedRequest mockSavedRequest;
    private HttpServletResponse mockResponse;

    @Before
    public void setUp() {
        controller = mock(SecurityController.class);
        when(controller.getRedirectViewWithoutExposedAttributes(anyString())).thenCallRealMethod();

        mockDeactivatedWebAccountOnlineLoginValidator = mock(DeactivatedWebAccountOnlineLoginValidator.class);
        controller.deactivatedWebAccountOnlineLoginValidator = mockDeactivatedWebAccountOnlineLoginValidator;

        mockSecurityService = mock(SecurityService.class);
        setField(controller, "securityService", mockSecurityService);
        
        mockSystemParameterService = mock(SystemParameterService.class);
        setField(controller, "systemParameterService", mockSystemParameterService);

        mockCmd = mock(LoginCmdImpl.class);
        when(mockCmd.getUsername()).thenReturn(USERNAME_1);
        when(mockCmd.getPassword()).thenReturn(PASSWORD_1);

        mockErrors = mock(Errors.class);

        mockRequest = mock(HttpServletRequest.class);

        mockSession = mock(HttpSession.class);

        mockSavedRequest = mock(SavedRequest.class);

        mockSsoSecurityService = mock(SingleSignOnSecurityService.class);
        controller.singleSignOnSecurityService = mockSsoSecurityService;
        
        mockResponse = mock(HttpServletResponse.class);
        
        when(controller.login(any(LoginCmdImpl.class), any(Errors.class), any(HttpServletRequest.class), any(HttpServletResponse.class)))
            .thenCallRealMethod();
        when(controller.autoLogin(anyString(), anyString(), any(HttpServletRequest.class)))
            .thenCallRealMethod();
        when(controller.logout(any(HttpServletRequest.class))).thenCallRealMethod();
    }

    @Test
    public void shouldLoginToDashboardWithValidCredentials() {
        when(controller.getSavedRequest(any(HttpServletRequest.class))).thenReturn(null);
        doNothing().when(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        doNothing().when(mockSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest);
        when(mockErrors.hasErrors()).thenReturn(Boolean.FALSE);

        ModelAndView result = controller.login(mockCmd, mockErrors, mockRequest, mockResponse);

        assertTrue(((RedirectView) result.getView()).getUrl().contains(PageUrl.DASHBOARD));
        verify(controller, atLeastOnce()).getSavedRequest(any(HttpServletRequest.class));
        verify(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        verify(mockSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest);
    }

    @Test
    public void shouldLoginToSavedUrlWithValidCredentials() {
        when(controller.getSavedRequest(any(HttpServletRequest.class))).thenReturn(PageUrl.CHANGE_PERSONAL_DETAILS);
        doNothing().when(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        doNothing().when(mockSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest);
        when(mockErrors.hasErrors()).thenReturn(Boolean.FALSE);

        ModelAndView result = controller.login(mockCmd, mockErrors, mockRequest, mockResponse);

        assertTrue(((RedirectView) result.getView()).getUrl().contains(PageUrl.CHANGE_PERSONAL_DETAILS));
        verify(controller, atLeastOnce()).getSavedRequest(any(HttpServletRequest.class));
        verify(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        verify(mockSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest);
    }

    @Test
    public void shouldNotLoginWithInvalidCredentials() {
        when(controller.getSavedRequest(any(HttpServletRequest.class))).thenReturn(PageUrl.CHANGE_PERSONAL_DETAILS);
        doNothing().when(controller).resetCredentials(any(LoginCmdImpl.class));
        doNothing().when(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        doThrow(new BadCredentialsException(TEST_EXCEPTION_MESSAGE)).when(mockSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest);

        when(mockErrors.hasErrors()).thenReturn(Boolean.FALSE);

        ModelAndView result = controller.login(mockCmd, mockErrors, mockRequest, mockResponse);

        assertViewName(result, PageView.OYSTER_HOME);

        verify(controller, never()).getSavedRequest(any(HttpServletRequest.class));
        verify(controller).resetCredentials(any(LoginCmdImpl.class));
        verify(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        verify(mockSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest);
    }

    @Test
    public void shouldNotLoginWithInactiveAccount() {
        when(controller.getSavedRequest(any(HttpServletRequest.class))).thenReturn(PageUrl.CHANGE_PERSONAL_DETAILS);
        doNothing().when(controller).resetCredentials(any(LoginCmdImpl.class));
        doNothing().when(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        doThrow(new BadCredentialsException(TEST_EXCEPTION_MESSAGE)).when(mockSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest);
        when(mockErrors.hasErrors()).thenReturn(Boolean.TRUE);

        ModelAndView result = controller.login(mockCmd, mockErrors, mockRequest, mockResponse);

        assertViewName(result, PageView.OYSTER_HOME);
        verify(controller, never()).getSavedRequest(any(HttpServletRequest.class));
        verify(controller, never()).resetCredentials(any(LoginCmdImpl.class));
        verify(mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        verify(mockSecurityService, never()).login(USERNAME_1, PASSWORD_1, mockRequest);
    }

    @Test
    public void shouldLogout() {
        doNothing().when(mockSecurityService).logout(mockRequest);
        
        ModelAndView result = controller.logout(mockRequest);
        
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
        verify(mockSecurityService).logout(mockRequest);
    }

    @Test
    public void shouldResetCredentials() {
        doCallRealMethod().when(controller).resetCredentials(any(LoginCmdImpl.class));
        doNothing().when(mockCmd).setUsername(anyString());
        doNothing().when(mockCmd).setPassword(anyString());

        controller.resetCredentials(mockCmd);

        verify(mockCmd).setUsername(anyString());
        verify(mockCmd).setPassword(anyString());
    }

    @Test
    public void getSavedRequestShouldReturnSavedUrl() {
        doCallRealMethod().when(controller).getSavedRequest(any(HttpServletRequest.class));

        when(mockRequest.getSession(anyBoolean())).thenReturn(mockSession);
        when(mockSession.getAttribute(eq(SecurityController.SAVED_REQUEST_ATTRIBUTE))).thenReturn(mockSavedRequest);
        when(mockSavedRequest.getRedirectUrl()).thenReturn(PageUrl.CHANGE_PERSONAL_DETAILS);

        String result = controller.getSavedRequest(mockRequest);

        assertEquals(PageUrl.CHANGE_PERSONAL_DETAILS, result);

        verify(mockRequest).getSession(anyBoolean());
        verify(mockSession).getAttribute(eq(SecurityController.SAVED_REQUEST_ATTRIBUTE));
        verify(mockSavedRequest).getRedirectUrl();
    }

    @Test
    public void getSavedRequestShouldReturnNullForNoSession() {
        doCallRealMethod().when(controller).getSavedRequest(any(HttpServletRequest.class));

        when(mockRequest.getSession(anyBoolean())).thenReturn(mockSession);
        when(mockSession.getAttribute(eq(SecurityController.SAVED_REQUEST_ATTRIBUTE))).thenReturn(null);
        when(mockSavedRequest.getRedirectUrl()).thenReturn(null);

        String result = controller.getSavedRequest(mockRequest);

        assertNull(result);

        verify(mockRequest).getSession(anyBoolean());
        verify(mockSession).getAttribute(eq(SecurityController.SAVED_REQUEST_ATTRIBUTE));
        verify(mockSavedRequest, never()).getRedirectUrl();
    }

    @Test
    public void getSavedRequestShouldReturnNullForNoSavedUrl() {
        doCallRealMethod().when(controller).getSavedRequest(any(HttpServletRequest.class));

        when(mockRequest.getSession(anyBoolean())).thenReturn(null);
        when(mockSession.getAttribute(eq(SecurityController.SAVED_REQUEST_ATTRIBUTE))).thenReturn(null);
        when(mockSavedRequest.getRedirectUrl()).thenReturn(null);

        String result = controller.getSavedRequest(mockRequest);

        assertNull(result);

        verify(mockRequest).getSession(anyBoolean());
        verify(mockSession, never()).getAttribute(eq(SecurityController.SAVED_REQUEST_ATTRIBUTE));
        verify(mockSavedRequest, never()).getRedirectUrl();
    }

    @Ignore
    public void dummyTestToGetSaltAndHashedPassword() {
        TokenGenerator saltGenerator = new TokenGeneratorImpl();
        String salt = saltGenerator.createSalt(32);
        PasswordEncoder passwordEncoder = new TflPasswordEncoder();
        String hashedPassword = passwordEncoder.encode(salt + "nemo");
        logger.info(String.format("Salt [%s]; Hashed Password [%s]", salt, hashedPassword));
    }

    @Test
    public void shouldLoginToDashboardIfSingleSignOnAuthenticationSucceed() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(true);
        when(controller.getSavedRequest(any(HttpServletRequest.class))).thenReturn(null);
        doNothing().when( mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        doNothing().when(mockSsoSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest, mockResponse);
        when(mockErrors.hasErrors()).thenReturn(Boolean.FALSE);

        ModelAndView result = controller.login(mockCmd, mockErrors, mockRequest, mockResponse);

        assertTrue(((RedirectView) result.getView()).getUrl().contains(PageUrl.DASHBOARD));
        verify(mockSsoSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest, mockResponse);
    }
    
    @Test
    public void shouldLoginToHomeIfSingleSignOnAuthenticationFail() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(true);
        when(controller.getSavedRequest(any(HttpServletRequest.class))).thenReturn(null);
        doNothing().when( mockDeactivatedWebAccountOnlineLoginValidator).validate(any(LoginCmdImpl.class), any(Errors.class));
        doThrow(new BadCredentialsException(TEST_EXCEPTION_MESSAGE)).when(mockSsoSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest, mockResponse);
        when(mockErrors.hasErrors()).thenReturn(Boolean.FALSE);

        ModelAndView result = controller.login(mockCmd, mockErrors, mockRequest, mockResponse);

        verify(mockSsoSecurityService).login(USERNAME_1, PASSWORD_1, mockRequest, mockResponse);
        verify(controller).resetCredentials(any(LoginCmdImpl.class));
        assertViewName(result, PageView.OYSTER_HOME);
    }
    
    @Test
    public void shouldAutoLoginToHomeIfNotSingleSignOn() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(false);
        
        ModelAndView result = controller.autoLogin("", "", mockRequest);
        
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldAutoLoginToHomeIfEmptyToken() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(true);
        when(mockSsoSecurityService.checkSessionActive(anyString(), anyString())).thenReturn(null);
        when(mockSsoSecurityService.autoLogin(anyString(), any(), any(HttpServletRequest.class))).thenReturn(true);
        
        ModelAndView result = controller.autoLogin(TEST_OTHER_PAGE, "", mockRequest);
        
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldAutoLoginToHomeIfIndexPage() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(true);
        when(mockSsoSecurityService.checkSessionActive(anyString(), anyString())).thenReturn(null);
        when(mockSsoSecurityService.autoLogin(anyString(), any(), any(HttpServletRequest.class))).thenReturn(true);
        
        ModelAndView result = controller.autoLogin(TEST_INDEX_PAGE, TEST_TOKEN_VALUE, mockRequest);
        
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldAutoLoginToHomeIfSingleSignOnFail() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(true);
        when(mockSsoSecurityService.checkSessionActive(anyString(), anyString())).thenReturn(null);
        when(mockSsoSecurityService.autoLogin(anyString(), any(), any(HttpServletRequest.class))).thenReturn(false);
        
        ModelAndView result = controller.autoLogin(TEST_OTHER_PAGE, TEST_TOKEN_VALUE, mockRequest);
        
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldAutoLoginAndRedirectIfSingleSignOnSuccess() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(true);
        when(mockSsoSecurityService.checkSessionActive(anyString(), anyString())).thenReturn(null);
        when(mockSsoSecurityService.autoLogin(anyString(), any(), any(HttpServletRequest.class))).thenReturn(true);
        
        ModelAndView result = controller.autoLogin(TEST_OTHER_PAGE, TEST_TOKEN_VALUE, mockRequest);
        
        assertEquals(TEST_OTHER_VIEW_NAME, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void shouldRedirectToSingleSignOutPage() {
        when(controller.isSingleSignOnAuthenticationOn()).thenReturn(true);
        when(controller.getSingleSignOutUrl(anyString())).thenReturn(TEST_OTHER_PAGE);
        
        ModelAndView result = controller.logout(mockRequest);
        
        assertEquals(TEST_OTHER_PAGE, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void testPostLogout() {
        doCallRealMethod().when(controller).postLogout(anyString(), anyString());
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(SSO_SERVER_ID);
        doNothing().when(mockSsoSecurityService).singleSignOut(anyString());
        
        controller.postLogout(SSO_SERVER_ID, TEST_TOKEN_VALUE);
        
        verify(mockSsoSecurityService).singleSignOut(TEST_TOKEN_VALUE);
    }
    
    @Test
    public void testPostLogoutIfServerIDInvalid() {
        doCallRealMethod().when(controller).postLogout(anyString(), anyString());
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(null);
        doNothing().when(mockSsoSecurityService).singleSignOut(anyString());
        
        controller.postLogout(SSO_SERVER_ID, TEST_TOKEN_VALUE);
        
        verify(mockSsoSecurityService, never()).singleSignOut(TEST_TOKEN_VALUE);
    }
    
    @Test
    public void testGetSingleSignOutUrl() {
        final String expectedResult = "ssoUrl/Logout.htm?returnURL=onlineUrl&sessionId=testSessionId";
        
        when(controller.getSingleSignOutUrl(anyString())).thenCallRealMethod();
        
        when(mockSystemParameterService.getParameterValue(SSO_BASE_URL)).thenReturn("ssoUrl");
        when(mockSystemParameterService.getParameterValue(ONLINE_BASE_URL)).thenReturn("onlineUrl");
        
        assertEquals(expectedResult, controller.getSingleSignOutUrl("testSessionId"));
    }
}
