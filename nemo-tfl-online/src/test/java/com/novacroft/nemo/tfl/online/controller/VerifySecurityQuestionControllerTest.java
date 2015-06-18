package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.command.SecurityQuestionCmd;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpSession;

import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * Unit tests for cart Controller
 */
public class VerifySecurityQuestionControllerTest {

    private VerifySecurityQuestionController controller;
    private SecurityQuestionCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private SecurityQuestionValidator mockSecurityQuestionValidator;
    private SecurityQuestionService mockSecurityQuestionService;
    private SecurityService mockSecurityService;
    private SelectListService mockSelectListService;
    private HttpSession mockSession;
    private BindingAwareModelMap model;

    public static final String ANONYMOUS_USER = "anonymousUser";
    public static final String USER = "user";

    @Before
    public void setUp() {
        controller = new VerifySecurityQuestionController();
        mockCmd = mock(SecurityQuestionCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockSecurityQuestionValidator = mock(SecurityQuestionValidator.class);
        mockSecurityQuestionService = mock(SecurityQuestionService.class);
        mockSecurityService = mock(SecurityService.class);
        mockSelectListService = mock(SelectListService.class);
        mockSession = mock(HttpSession.class);
        model = new BindingAwareModelMap();

        controller.securityQuestionValidator = mockSecurityQuestionValidator;
        controller.securityQuestionService = mockSecurityQuestionService;
        setField(controller, "securityService", mockSecurityService);
        controller.selectListService = mockSelectListService;

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
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockSecurityQuestionService.verifySecurityQuestionDetails(any(SecurityQuestionCmd.class), anyLong()))
                .thenReturn(true);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = controller.verifySecurityQuestion(mockSession, mockCmd, mockResult, redirectAttributes);

        verify(mockSecurityService, atLeastOnce()).getLoggedInCustomer();
        verify(mockSecurityQuestionService, atLeastOnce())
                .verifySecurityQuestionDetails((SecurityQuestionCmd) anyObject(), anyLong());
        assertEquals(PageUrl.CONFIRMATION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void verifySecurityQuestionShouldShowVerifySecurityQuestionWhenVerificationFailed() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockSecurityQuestionService.verifySecurityQuestionDetails(any(SecurityQuestionCmd.class), anyLong()))
                .thenReturn(false);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = controller.verifySecurityQuestion(mockSession, mockCmd, mockResult, redirectAttributes);

        verify(mockSecurityService, atLeastOnce()).getLoggedInCustomer();
        verify(mockSecurityQuestionService, atLeastOnce())
                .verifySecurityQuestionDetails((SecurityQuestionCmd) anyObject(), anyLong());
        assertEquals(PageView.VERIFY_SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void verifySecurityQuestionShouldShowVerifySecurityQuestionOnInValidInput() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result = controller.verifySecurityQuestion(mockSession, mockCmd, mockResult, redirectAttributes);
        assertEquals(PageView.VERIFY_SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToDashBoard() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }

}
