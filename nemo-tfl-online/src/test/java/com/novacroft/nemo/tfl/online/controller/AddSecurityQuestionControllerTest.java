package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO1;
import static com.novacroft.nemo.test_support.CartSessionDataTestUtil.getTestCartSessionDataDTO4;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.command.SecurityQuestionCmd;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;
import com.novacroft.nemo.tfl.common.transfer.CartSessionData;
import com.novacroft.nemo.tfl.common.util.CartUtil;

/**
 * Unit tests for Add Security Question Controller
 */
public class AddSecurityQuestionControllerTest {

    private AddSecurityQuestionController controller;
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
        controller = new AddSecurityQuestionController();
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
    public void shouldShowSecurityQuestion() {
        ModelAndView result = controller.viewSecurityQuestion(mockCmd);
        assertEquals(PageView.SECURITY_QUESTION, result.getViewName());
        assertTrue(result.getModel().containsKey(PageCommand.SECURITY_QUESTION));
        assertTrue(result.getModel().get(PageCommand.SECURITY_QUESTION) instanceof SecurityQuestionCmdImpl);
    }

    @Test
    public void addSecurityQuestionShouldShowCheckoutOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        CartSessionData cartSessionData = getTestCartSessionDataDTO1();
        cartSessionData.setPageName(Page.CART);
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(cartSessionData);
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = controller.addSecurityQuestion(mockSession, mockCmd, mockResult, redirectAttributes);

        verify(mockSecurityService, atLeastOnce()).getLoggedInCustomer();
        verify(mockSecurityQuestionService, atLeastOnce()).addSecurityQuestionDetails((SecurityQuestionCmd) anyObject(), anyLong(),
                        any(CartSessionData.class));
        assertEquals(PageUrl.CHECKOUT, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addSecurityQuestionShouldShowConfirmationOnValidInput() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = controller.addSecurityQuestion(mockSession, mockCmd, mockResult, redirectAttributes);

        verify(mockSecurityService, atLeastOnce()).getLoggedInCustomer();
        verify(mockSecurityQuestionService, atLeastOnce()).addSecurityQuestionDetails((SecurityQuestionCmd) anyObject(), anyLong(),
                        any(CartSessionData.class));
        assertEquals(PageUrl.CONFIRMATION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addSecurityQuestionShouldShowConfirmationOnValidInputWithCartSessionData() {
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(CartUtil.getCartSessionDataDTOFromSession(mockSession)).thenReturn(getTestCartSessionDataDTO1());
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

        ModelAndView result = controller.addSecurityQuestion(mockSession, mockCmd, mockResult, redirectAttributes);

        verify(mockSecurityService, atLeastOnce()).getLoggedInCustomer();
        verify(mockSecurityQuestionService, atLeastOnce()).addSecurityQuestionDetails((SecurityQuestionCmd) anyObject(), anyLong(),
                        any(CartSessionData.class));
        assertEquals(PageUrl.CONFIRMATION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addSecurityQuestionShouldShowSecurityQuestionOnInValidInput() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result = controller.addSecurityQuestion(mockSession, mockCmd, mockResult, redirectAttributes);
        assertEquals(PageView.SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToDashBoard() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void addSecurityQuestionShouldRedirectModelAndView() {
        RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();
        when(mockResult.hasErrors()).thenReturn(true);
        ModelAndView result =
                controller.redirectModelAndView(mockSession, redirectAttributes, getTestCartSessionDataDTO4(), mockCmd);
        assertEquals(PageUrl.COLLECT_PURCHASE, ((RedirectView) result.getView()).getUrl());
    }

}
