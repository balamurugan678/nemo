package com.novacroft.nemo.mock_single_sign_on.controller;

import static com.novacroft.nemo.test_support.CookieTestUtil.TEST_TOKEN;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_APP;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_APP_ID;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_RETURN_URL;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_SESSION_ID;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_USER_NAME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.mock_single_sign_on.command.LogoutCmd;
import com.novacroft.nemo.mock_single_sign_on.service.LogonService;
import com.novacroft.nemo.mock_single_sign_on.service.TokenCacheService;
public class LogoutControllerTest {
    private static final String LOGOUT_VIEW_NAME = "LogoutView";
    
    private LogoutController controller;
    
    @Mock
    private LogonService mockLogonService;
    @Mock
    private TokenCacheService mockTokenCacheService;
    @Mock
    private HttpServletRequest mockRequest;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        controller = new LogoutController();
        
        controller.logonService = mockLogonService;
        controller.tokenCacheService = mockTokenCacheService;
    }
    
    @Test
    public void initShouldInvokeLogonService() {
        when(mockLogonService.convert(anyString())).thenReturn(new HashMap<String, String>());
        
        controller.init();
        
        verify(mockLogonService).convert(anyString());
    }
    
    @Test
    public void shouldDisplayLoggedUserIfNoTokenFoundForSession() {
        when(mockTokenCacheService.getCachedToken(TEST_SESSION_ID)).thenReturn(null);
        
        ModelAndView actualResult = controller.displayLoggedUser(new LogoutCmd(), TEST_RETURN_URL, TEST_SESSION_ID, mockRequest);
        
        assertEquals(LOGOUT_VIEW_NAME, actualResult.getViewName());
    }
    
    @Test
    public void displayLoggedUserShouldUpdateCmdIfTokenFound() {
        Map<String, String> testMap = new HashMap<>();
        testMap.put(TEST_APP_ID, TEST_APP);
        controller.appIdNameMap = testMap;
        
        when(mockTokenCacheService.getCachedToken(TEST_SESSION_ID)).thenReturn(TEST_TOKEN);
        when(mockTokenCacheService.getCachedUsername(anyString())).thenReturn(TEST_USER_NAME);
        when(mockTokenCacheService.getCachedApps(anyString())).thenReturn(Arrays.asList(TEST_APP_ID));
        
        LogoutCmd testCmd = new LogoutCmd();
        ModelAndView actualResult = controller.displayLoggedUser(testCmd, TEST_RETURN_URL, TEST_SESSION_ID, mockRequest);
        
        assertEquals(LOGOUT_VIEW_NAME, actualResult.getViewName());
        assertEquals(TEST_USER_NAME, testCmd.getUsername());
        assertEquals(TEST_RETURN_URL, testCmd.getReturnURL());
    }
}
