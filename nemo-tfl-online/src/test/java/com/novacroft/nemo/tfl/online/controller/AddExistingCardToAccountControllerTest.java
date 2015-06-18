package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.CardServiceImpl;
import com.novacroft.nemo.tfl.common.application_service.impl.CustomerServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.CardCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;

/**
 * Unit tests for AddExistingCardToAccountController
 */
public class AddExistingCardToAccountControllerTest {

    private AddExistingCardToAccountController controller;
    private CardCmdImpl mockCmd;
    private BeanPropertyBindingResult mockResult;
    private OysterCardValidator mockOysterCardValidator;
    private SecurityQuestionService mockSecurityQuestionService;
    private SecurityService mockSecurityService;
    private SelectListService mockSelectListService;
    private BindingAwareModelMap model;
    private CustomerService mockCustomerService;
    private RedirectAttributes mockRedirectAttributes;
    private CardServiceImpl mockCardService;
    
    @Before
    public void setUp() {
        controller = new AddExistingCardToAccountController();
        mockCmd = mock(CardCmdImpl.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockOysterCardValidator = mock(OysterCardValidator.class);
        mockSecurityQuestionService = mock(SecurityQuestionService.class);
        mockSecurityService = mock(SecurityService.class);
        mockSelectListService = mock(SelectListService.class);
        mockCustomerService = mock(CustomerServiceImpl.class);
        mockCardService = mock(CardServiceImpl.class);
        mockRedirectAttributes = mock(RedirectAttributes.class);
        model = new BindingAwareModelMap();

        controller.oysterCardValidator = mockOysterCardValidator;
        controller.securityQuestionService = mockSecurityQuestionService;
        controller.customerService = mockCustomerService;
        controller.cardService = mockCardService;
        controller.selectListService = mockSelectListService;

        doNothing().when(mockOysterCardValidator).validate(anyObject(), any(Errors.class));
    }

    @Test
    public void shouldpopulateSecurityQuestionsSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateSecurityQuestionsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.SECURITY_QUESTIONS));
    }

    @Test
    public void shouldShowPage() {
        ModelAndView result = controller.showPage();
        assertEquals(PageView.ADD_EXISTING_CARD_TO_ACCOUNT, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowSecurityQuestionOnValidInput() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(null);
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1().getId());
        when(mockResult.hasErrors()).thenReturn(false);
        setField(controller, "securityService", mockSecurityService);

        ModelAndView result = controller.saveChanges(mockCmd, mockResult, mockRedirectAttributes);
        assertEquals(PageView.SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowVerifySecurityQuestionOnValidInput() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService. createCard((Long)any(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityQuestionService.getSecurityQuestionDetails(anyLong())).thenReturn(SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmd1());
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1().getId());
        setField(controller, "securityService", mockSecurityService);

        ModelAndView result = controller.saveChanges(mockCmd, mockResult, mockRedirectAttributes);
        assertEquals(PageView.VERIFY_SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowSecurityQuestionWhenSecurityQuestionAnswerAreNull() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityQuestionService.getSecurityQuestionDetails(anyLong())).thenReturn(new SecurityQuestionCmdImpl());
        setField(controller, "securityService", mockSecurityService);

        ModelAndView result = controller.saveChanges(mockCmd, mockResult, mockRedirectAttributes);
        assertEquals(PageView.SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowSecurityQuestionWhenSecurityQuestionIsNotNullAndAnswerIsNull() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(CardTestUtil.getTestCardDTO1());
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1().getId());
        when(mockResult.hasErrors()).thenReturn(false);
        SecurityQuestionCmdImpl secQuesCmd = SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmd1();
        secQuesCmd.setSecurityAnswer(null);
        when(mockSecurityQuestionService.getSecurityQuestionDetails(anyLong())).thenReturn(secQuesCmd);
        setField(controller, "securityService", mockSecurityService);

        ModelAndView result = controller.saveChanges(mockCmd, mockResult, mockRedirectAttributes);
        assertEquals(PageView.SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowSecurityQuestionWhenCardDTOIsEmpty() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard((Long)any(), anyString())).thenReturn(new CardDTO());
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1().getId());
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockSecurityQuestionService.getSecurityQuestionDetails(anyLong())).thenReturn(new SecurityQuestionCmdImpl());
        setField(controller, "securityService", mockSecurityService);

        ModelAndView result = controller.saveChanges(mockCmd, mockResult, mockRedirectAttributes);
        assertEquals(PageView.SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowAddCardOnInvalidInput() {
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockCustomerService.createCard((Long)any(),anyString())).thenReturn(null);
        when(mockResult.hasErrors()).thenReturn(true);
        when(mockCardService.getCardIdFromCardNumber(anyString())).thenReturn(CardTestUtil.getTestCardDTO1().getId());
        setField(controller, "securityService", mockSecurityService);

        ModelAndView result = controller.saveChanges(mockCmd, mockResult, mockRedirectAttributes);
        assertEquals(PageView.ADD_EXISTING_CARD_TO_ACCOUNT, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToDashboard() {
        setField(controller, "securityService", mockSecurityService);
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) result.getView()).getUrl());
    }
}
