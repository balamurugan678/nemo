package com.novacroft.nemo.mock_single_sign_on.controller;

import static com.novacroft.nemo.test_support.LoginCmdTestUtil.LOGIN_VIEW_NAME;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.TEST_SESSION_ID;
import static com.novacroft.nemo.test_support.LoginCmdTestUtil.createValidTestLoginCmd;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.mock_single_sign_on.data_service.MasterCustomerDataService;
import com.novacroft.nemo.mock_single_sign_on.service.LogonService;
import com.novacroft.nemo.mock_single_sign_on.service.TokenCacheService;

public class LoginControllerTest {
    private LoginController controller;

    @Mock
    private LogonService mockLogonService;
    @Mock
    private MasterCustomerDataService mockMasterCustomerDataService;
    @Mock
    private TokenCacheService mockTokenCacheService;
    @Mock
    private HttpServletResponse mockResponse;
    @Mock
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        controller = new LoginController();

        controller.logonService = mockLogonService;
        controller.masterCustomerDataService = mockMasterCustomerDataService;
        controller.tokenCacheService = mockTokenCacheService;
    }

    @Test
    public void shouldPopulateModelAttributes() {
        Map<String, String> mockMap = new HashMap<>();
        mockMap.put("test key", "test value");
        when(mockLogonService.convert(anyString())).thenReturn(mockMap);

        Model model = new BindingAwareModelMap();
        controller.populateModelAttributes(model);

        assertTrue(model.containsAttribute("appList"));
    }

    @Test
    public void shouldShowLoginPage() {
        ModelAndView actualResult = controller.loginPage(createValidTestLoginCmd());

        assertEquals(LOGIN_VIEW_NAME, actualResult.getViewName());
    }

    @Test
    public void noTokenGeneratedIfInvalidUsername() {
        when(mockMasterCustomerDataService.isUsernameExisting(anyString())).thenReturn(false);

        controller.authenticateLoginRequestAndGenerateToken(mockRequest, mockResponse, createValidTestLoginCmd(), 
                                                            "", "", "", "", "", "");

        verify(mockResponse, never()).addCookie(any(Cookie.class));
    }

    @Test
    public void tokenGeneratedIfValidUsername() {
        when(mockMasterCustomerDataService.isUsernameExisting(anyString())).thenReturn(true);
        doNothing().when(mockResponse).addCookie(any(Cookie.class));
        doNothing().when(mockTokenCacheService).saveTokenToCache(anyString(), anyString(), anyString(), anyString());

        controller.authenticateLoginRequestAndGenerateToken(mockRequest, mockResponse, createValidTestLoginCmd(), 
                                                            "", "", "", "", "", TEST_SESSION_ID);

        verify(mockResponse).addCookie(any(Cookie.class));
        verify(mockTokenCacheService).saveTokenToCache(anyString(), anyString(), anyString(), anyString());
    }
}
