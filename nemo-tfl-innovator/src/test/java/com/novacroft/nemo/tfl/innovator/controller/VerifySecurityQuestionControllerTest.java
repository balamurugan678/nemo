package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.command.SecurityQuestionCmd;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;
import com.novacroft.nemo.tfl.common.util.CartUtil;


public class VerifySecurityQuestionControllerTest {
    
    private VerifySecurityQuestionController controller;
    
    @Mock
    private SecurityQuestionCmdImpl mockCmd;
    @Mock
    private BeanPropertyBindingResult mockResult;
    @Mock
    private SecurityQuestionValidator mockSecurityQuestionValidator;
    @Mock
    private SecurityQuestionService mockSecurityQuestionService;
    @Mock
    private SecurityService mockSecurityService;
    @Mock
    private SelectListService mockSelectListService;
    @Mock
    private HttpSession mockSession;
    @Mock
    private CartService mockCartService;
    
    private BindingAwareModelMap model;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        controller = new VerifySecurityQuestionController();
        controller.cartService = mockCartService;
        controller.securityQuestionService = mockSecurityQuestionService;
        controller.selectListService = mockSelectListService;
        controller.securityQuestionService = mockSecurityQuestionService;
        controller.securityQuestionValidator = mockSecurityQuestionValidator;
        model = new BindingAwareModelMap();
        doNothing().when(mockSecurityQuestionValidator).validate(anyObject(), any(Errors.class));
        
    }
    
    @Test
    public void shouldpopulateSecurityQuestionsSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateSecurityQuestionsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.SECURITY_QUESTIONS));
    }
    
    @Test
    public void shouldShowVerifySecurityQuestion() {
        ModelAndView result = controller.viewSecurityQuestion(mockCmd);
        assertEquals(PageView.VERIFY_SECURITY_QUESTION, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.SECURITY_QUESTION));
        assertTrue(result.getModel().get(PageCommand.SECURITY_QUESTION) instanceof SecurityQuestionCmdImpl);
    }
    
    @Test
    public void verifySecurityQuestionShouldShowConfirmationOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityQuestionService.verifySecurityQuestionDetails(any(SecurityQuestionCmd.class), anyLong()))
                .thenReturn(true);
        when(mockCartService.findById(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithItem1());
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        ModelAndView result = controller.verifySecurityQuestion(mockSession, mockCmd, mockResult);
        assertEquals(PageUrl.CHECKOUT, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void verifySecurityQuestionShouldShowVerifySecurityQuestionOnInValidInput() {
        
        when(mockResult.hasErrors()).thenReturn(true);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        ModelAndView result = controller.verifySecurityQuestion(mockSession, mockCmd, mockResult);
        assertEquals(PageView.VERIFY_SECURITY_QUESTION, result.getViewName());
    }
}
