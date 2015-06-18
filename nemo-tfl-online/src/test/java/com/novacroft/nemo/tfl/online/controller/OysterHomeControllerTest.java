package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.tfl.common.constant.CartAttribute.CARD_ID;
import static com.novacroft.nemo.tfl.common.constant.CartAttribute.SESSION_ATTRIBUTE_SHOPPING_CART_DATA;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.ONLINE_SYSTEM_BASE_URI;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.SingleSignOnSecurityService;
import com.novacroft.nemo.tfl.common.command.impl.LoginCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

/**
 * OysterHomeController unit tests
 */
public class OysterHomeControllerTest {
    private static final String TEST_TOKEN = "TestToken";
    private static final String TEST_SESSION_ID = "TestSessionId";
    
    @Mock
    private OysterHomeController mockController;
    @Mock
	private HttpSession mockSession;
    @Mock
    private SystemParameterService mockSystemParameterService;
    @Mock
    private SingleSignOnSecurityService mockSingleSignOnSecurityService;
    @Mock
    private HttpServletRequest mockRequest;
	
	@Before
	public void setUp() {
	    MockitoAnnotations.initMocks(this);
	    
	    mockController.systemParameterService = mockSystemParameterService;
	    mockController.singleSignOnSecurityService = mockSingleSignOnSecurityService;                  
	    
	    doCallRealMethod().when(mockController).populateModelAttributes(any(Model.class));
	    when(mockController.showOysterHome(any(HttpSession.class), any(HttpServletRequest.class), anyString(), anyBoolean())).thenCallRealMethod();
	}
	
    @Test
    public void shouldPopulateModelAttributes() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(ONLINE_SYSTEM_BASE_URI.code());
        Model model = new BindingAwareModelMap();
        mockController.populateModelAttributes(model);
        assertTrue(model.containsAttribute(PageAttribute.RETURN_URL));
    }

    @Test
    public void shouldShowOysterHome() {
        when(mockController.isSingleSignOnAuthenticationOn()).thenReturn(false);
        
        ModelAndView result = mockController.showOysterHome(mockSession, mockRequest, "", false);
        
        assertEquals(PageView.OYSTER_HOME, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.LOGIN));
        assertTrue(result.getModel().get(PageCommand.LOGIN) instanceof LoginCmdImpl);
    }
    
    @Test
    public void shouldDelteSessionAttributeCardId() {
        when(mockController.getFromSession(mockSession, CARD_ID)).thenReturn("");
        doNothing().when(mockController).deleteAttributeFromSession(any(HttpSession.class), anyString());
        
        mockController.showOysterHome(mockSession, mockRequest, "", true);
        
        verify(mockController).deleteAttributeFromSession(mockSession, CARD_ID);
    }
    
    @Test
    public void shouldDelteSessionAttributeShoppingCartData() {
        when(mockController.getFromSession(mockSession, SESSION_ATTRIBUTE_SHOPPING_CART_DATA)).thenReturn("");
        doNothing().when(mockController).deleteAttributeFromSession(any(HttpSession.class), anyString());
        
        mockController.showOysterHome(mockSession, mockRequest, "", true);
        
        verify(mockController).deleteAttributeFromSession(mockSession, SESSION_ATTRIBUTE_SHOPPING_CART_DATA);
    }
    
    @Test
    public void testShowOysterHomeIfSessionIsNull() {
        mockController.showOysterHome(null, mockRequest, "", true);
        
        verify(mockController, never()).deleteAttributeFromSession(any(HttpSession.class), anyString());
    }
    
    @Test
    public void shouldRedirectToOysterHomeAfterAutoLogin() {
        when(mockController.isAutoLoginEligible(anyString())).thenReturn(true);
        when(mockRequest.getRequestedSessionId()).thenReturn(TEST_SESSION_ID);
        when(mockSingleSignOnSecurityService.checkSessionActive(anyString(), anyString())).thenReturn(new Object());
        when(mockSingleSignOnSecurityService.autoLogin(anyString(), any(Object.class), any(HttpServletRequest.class))).thenReturn(true);
        
        ModelAndView actualResult = mockController.showOysterHome(mockSession, mockRequest, TEST_TOKEN, false);
        
        verify(mockSingleSignOnSecurityService).autoLogin(anyString(), any(Object.class), any(HttpServletRequest.class));
        assertEquals(PageUrl.OYSTER_HOME, ((RedirectView) actualResult.getView()).getUrl());
    }
    
    @Test
    public void shouldShowOysterHomeIfAutoLoginNotEligible() {
        when(mockController.isAutoLoginEligible(anyString())).thenReturn(false);
        
        ModelAndView actualResult = mockController.showOysterHome(mockSession, mockRequest, null, false);
        
        verify(mockSingleSignOnSecurityService, never()).autoLogin(anyString(), any(Object.class), any(HttpServletRequest.class));
        assertEquals(PageView.OYSTER_HOME, actualResult.getViewName());
    }
    
    @Test
    public void shouldShowOysterHomeIfAutoLoginFailed() {
        when(mockController.isAutoLoginEligible(anyString())).thenReturn(true);
        when(mockRequest.getRequestedSessionId()).thenReturn(TEST_SESSION_ID);
        when(mockSingleSignOnSecurityService.checkSessionActive(anyString(), anyString())).thenReturn(new Object());
        when(mockSingleSignOnSecurityService.autoLogin(anyString(), any(Object.class), any(HttpServletRequest.class))).thenReturn(false);
        
        ModelAndView actualResult = mockController.showOysterHome(mockSession, mockRequest, TEST_TOKEN, false);
        
        verify(mockSingleSignOnSecurityService).autoLogin(anyString(), any(Object.class), any(HttpServletRequest.class));
        assertEquals(PageView.OYSTER_HOME, actualResult.getViewName());
    }
    
    @Test
    public void isAutoLoginEligibleReturnFalseIfNotSignleSignOn() {
        when(mockController.isAutoLoginEligible(anyString())).thenCallRealMethod();
        when(mockController.isSingleSignOnAuthenticationOn()).thenReturn(false);
        
        assertFalse(mockController.isAutoLoginEligible(TEST_TOKEN));
    }
    
    @Test
    public void isAutoLoginEligibleReturnFalseIfTokenIsNull() {
        when(mockController.isAutoLoginEligible(anyString())).thenCallRealMethod();
        when(mockController.isSingleSignOnAuthenticationOn()).thenReturn(true);
        
        assertFalse(mockController.isAutoLoginEligible(null));
    }
    
    @Test
    public void isAutoLoginEligibleReturnTrue() {
        when(mockController.isAutoLoginEligible(anyString())).thenCallRealMethod();
        when(mockController.isSingleSignOnAuthenticationOn()).thenReturn(true);
        
        assertTrue(mockController.isAutoLoginEligible(TEST_TOKEN));
    }
}
