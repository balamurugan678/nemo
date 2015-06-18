package com.novacroft.nemo.mock_single_sign_on.controller;

import static com.novacroft.nemo.test_support.CookieTestUtil.TEST_TOKEN;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_APP;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_APP_ID;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_RETURN_URL;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_SESSION_ID;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_USER_NAME;
import static com.novacroft.nemo.test_support.UserTestUtil.CUSTOMER_ID;
import static com.novacroft.nemo.test_support.UserTestUtil.UPDATED_FIRST_NAME;
import static com.novacroft.nemo.test_support.UserTestUtil.getUser;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mock.web.MockHttpServletResponse;

import com.novacroft.nemo.mock_single_sign_on.data_service.MasterCustomerDataService;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.service.LogoutService;
import com.novacroft.nemo.mock_single_sign_on.service.TokenCacheService;
import com.novacroft.nemo.mock_single_sign_on.service.impl.LogonServiceImpl;

public class SSOControllerTest {

    private static final String appIdMap = "a3ac81d4-80e8-4427-b348-a3d028dfdbe7:CASC,8ead5cf4-4624-4389-b90c-b1fd1937bf1f:Oyster,6687e912-d120-461e-9da9-3c0288629f4f:CAS Support Site";
    private static final String appLogoutMap = "CASC:test.htm";

    private SSOController controller;
    private LogonServiceImpl logonService;
    private TokenCacheService mockTokenCacheService;
    private MasterCustomerDataService mockCustomerDataService;
    private HttpServletResponse httpResponse;
    private HttpServletRequest httpRequest;
    private MockHttpServletResponse mockHttpResponse;
    private LogoutService mockLogoutService;
    private User mockUser;

    @Before
    public void setUp() {
        controller = mock(SSOController.class, CALLS_REAL_METHODS);

        logonService = mock(LogonServiceImpl.class);
        mockTokenCacheService = mock(TokenCacheService.class);
        mockCustomerDataService = mock(MasterCustomerDataService.class);
        mockLogoutService = mock(LogoutService.class);

        controller.logonService = logonService;
        controller.tokenCacheService = mockTokenCacheService;
        controller.masterCustomerDataService = mockCustomerDataService;
        controller.logoutService = mockLogoutService;

        httpResponse = mock(HttpServletResponse.class);
        httpRequest = mock(HttpServletRequest.class);

        mockHttpResponse = new MockHttpServletResponse();
        mockUser = mock(User.class);
    }

    @Test
    public void validateSecurityTokenShouldReturnResponseWithNullUser() {
        when(logonService.processCookies(httpRequest)).thenReturn(null);

        controller.validateSecurityToken(httpResponse, httpRequest);

        verify(logonService).createResponse(null);
    }

    @Test
    public void validateSecurityTokenShouldReturnCustomerData() {
        Cookie cookie = new Cookie("token", "test");
        when(logonService.processCookies(httpRequest)).thenReturn(cookie);
        when(mockTokenCacheService.getCachedUsername(anyString())).thenReturn(TEST_USER_NAME);
        when(mockCustomerDataService.findMasterCustomerByUsername(TEST_USER_NAME)).thenReturn(mockUser);

        controller.validateSecurityToken(httpResponse, httpRequest);

        verify(logonService).createResponse(mockUser);
    }

    @Test
    public void checkActiveSSOSessionShouldReturnResponseWithNullUser() {
        when(controller.isAppValid(anyString())).thenReturn(false);

        controller.checkActiveSSOSession(TEST_TOKEN, TEST_APP, "", "");

        verify(logonService).createResponse(null);
    }

    @Test
    public void checkActiveSSOSessionShouldReturnCustomerData() {
        when(controller.isAppValid(anyString())).thenReturn(true);
        when(mockTokenCacheService.isTokenValid(anyString())).thenReturn(true);
        when(mockTokenCacheService.getCachedUsername(anyString())).thenReturn(TEST_USER_NAME);
        when(mockCustomerDataService.findMasterCustomerByUsername(TEST_USER_NAME)).thenReturn(mockUser);

        controller.checkActiveSSOSession(TEST_TOKEN, TEST_APP, "", "");

        verify(logonService).createResponse(mockUser);
    }

    @Test
    public void testLogoutForInvalidToken() {
        when(mockTokenCacheService.isTokenValid(anyString())).thenReturn(false);

        controller.centralSignOut(TEST_TOKEN, TEST_RETURN_URL, mockHttpResponse);

        verify(mockTokenCacheService, never()).clearTokenCache(anyString());
        verify(mockLogoutService, never()).singleSignOut(anyListOf(String.class), anyString());
    }

    @Test
    public void testLogoutShouldClearCache() {
        when(mockTokenCacheService.getCachedToken(TEST_SESSION_ID)).thenReturn(TEST_TOKEN);
        when(mockTokenCacheService.isTokenValid(anyString())).thenReturn(true);
        doNothing().when(mockTokenCacheService).clearTokenCache(anyString());

        controller.centralSignOut(TEST_SESSION_ID, TEST_RETURN_URL, mockHttpResponse);
        verify(mockTokenCacheService).clearTokenCache(TEST_TOKEN);
    }

    @Test
    public void testInitShouldInvokeLogonService() {
        controller.setAppIdMap(appIdMap);
        controller.setAppLogoutMap(appLogoutMap);
        when(logonService.convert(anyString())).thenReturn(new HashMap<String, String>());
        when(logonService.convert(anyString())).thenReturn(new HashMap<String, String>());

        controller.init();

        verify(controller, atLeastOnce()).init();
        verify(logonService).convert(appIdMap);
        verify(logonService).convert(appLogoutMap);
    }

    @Test
    public void shouldNotUpdateUserDetailIfUserNotFound() {
        when(mockCustomerDataService.findMasterCustomerById(anyLong())).thenReturn(null);

        controller.updateUserDetail(CUSTOMER_ID.toString(), FIRST_NAME_1, LAST_NAME_1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null, null, null, null, null, null);

        verify(mockCustomerDataService, never()).updateMasterCustomer(any(User.class));
    }

    
     @Test
     public void shouldUpdateUserDetail() {
        when(mockCustomerDataService.findMasterCustomerById(anyLong())).thenReturn(getUser());

        controller.updateUserDetail(CUSTOMER_ID.toString(), FIRST_NAME_1, LAST_NAME_1, null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null);

        verify(mockCustomerDataService).updateMasterCustomer(any(User.class));
     }

    @Test
    public void getLogoutUrlsShouldReturnEmptyList() {
        when(mockTokenCacheService.getCachedApps(TEST_TOKEN)).thenReturn(new ArrayList<String>());

        List<String> actualResult = controller.getLogoutUrls(TEST_TOKEN);

        assertTrue(actualResult.isEmpty());
    }

    @Test
    public void shouldGetLogoutUrlsIfTokenValid() {
        Map<String, String> testIdMap = new HashMap<>();
        testIdMap.put(TEST_APP_ID, TEST_APP);
        controller.applicationIdMap = testIdMap;

        Map<String, String> testLogoutMap = new HashMap<>();
        testLogoutMap.put(TEST_APP, TEST_RETURN_URL);
        controller.applicationLogoutMap = testLogoutMap;

        when(mockTokenCacheService.getCachedApps(anyString())).thenReturn(Arrays.asList(TEST_APP_ID));

        List<String> actualResult = controller.getLogoutUrls(TEST_TOKEN);

        assertTrue(actualResult.size() == 1);
        assertEquals(TEST_RETURN_URL, actualResult.get(0));
    }
    
    @Test
    public void shouldNotUpdateBeanPropertyValueIfNullValue() throws IllegalAccessException, InvocationTargetException {
        User testUser = getUser();
        String expectedResult = testUser.getCustomer().getFirstName();
        
        controller.updateBeanPropertyValue(testUser.getCustomer(), "firstName", null);
        
        assertEquals(expectedResult, testUser.getCustomer().getFirstName());
    }
    
    @Test
    public void shouldUpdateBeanPropertyValueIfNotNull() throws IllegalAccessException, InvocationTargetException {
        User testUser = getUser();
        
        controller.updateBeanPropertyValue(testUser.getCustomer(), "firstName", UPDATED_FIRST_NAME);
        
        assertEquals(UPDATED_FIRST_NAME, testUser.getCustomer().getFirstName());
    }
    
    @Test
    public void shouldNotUpdateUserDetailIfException() {
        User mockUser = mock(User.class);
        when(mockUser.getCustomer()).thenThrow(InvocationTargetException.class);
        when(mockCustomerDataService.findMasterCustomerById(anyLong())).thenReturn(mockUser);
        
        controller.updateUserDetail(CUSTOMER_ID.toString(), FIRST_NAME_1, LAST_NAME_1, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                        null, null, null, null, null, null, null, null, null, null, null, null);
       
        verify(mockCustomerDataService, never()).updateMasterCustomer(any(User.class));
        verify(logonService).createResponse(null);
    }
    
    @Test
    public void centralSignOutRedirectToLogoutPage() {
        when(mockTokenCacheService.getCachedToken(TEST_SESSION_ID)).thenReturn(TEST_TOKEN);
        when(mockTokenCacheService.isTokenValid(anyString())).thenReturn(false);

        controller.centralSignOut(TEST_SESSION_ID, null, mockHttpResponse);
        
        assertEquals("Logout.htm", mockHttpResponse.getRedirectedUrl());
    }
}
