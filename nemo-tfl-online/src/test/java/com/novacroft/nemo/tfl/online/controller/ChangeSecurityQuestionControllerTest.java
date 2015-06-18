package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil.getTestSecurityQuestionCmd1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
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
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.SecurityQuestionService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.SecurityQuestionServiceImpl;
import com.novacroft.nemo.tfl.common.application_service.impl.SecurityServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.SecurityQuestionCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.SecurityQuestionValidator;

/**
 * Unit tests for ChangeSecurityQuestion Controller
 */
public class ChangeSecurityQuestionControllerTest {
    private ChangeSecurityQuestionController controller;
    private ChangeSecurityQuestionController mockController;
    private BeanPropertyBindingResult mockResult;
    private RedirectAttributes mockRedirectAttributes;
    private SecurityService mockSecurityService;
    private SecurityQuestionService mockSecurityQuestionService;
    private SecurityQuestionValidator mockSecurityQuestionValidator;
    private SelectListService mockSelectListService;
    private CardDataService mockCardDataService;
    private BindingAwareModelMap model;
    private SecurityQuestionCmdImpl mockCmd;
    private HttpSession mockSession;
    
    @Before
    public void setUp() {
        controller = new ChangeSecurityQuestionController();
        mockController = mock(ChangeSecurityQuestionController.class);
        
        mockResult = mock(BeanPropertyBindingResult.class);
        mockRedirectAttributes = mock(RedirectAttributes.class);
        mockSecurityService = mock(SecurityServiceImpl.class);
        mockSecurityQuestionService = mock(SecurityQuestionServiceImpl.class);
        mockSecurityQuestionValidator = mock(SecurityQuestionValidator.class);
        mockSelectListService = mock(SelectListService.class);
        mockCardDataService = mock(CardDataService.class);
        
        setField(controller, "securityService", mockSecurityService);
        controller.securityQuestionService = mockSecurityQuestionService;
        controller.securityQuestionValidator = mockSecurityQuestionValidator;
        controller.selectListService = mockSelectListService;
        controller.cardDataService = mockCardDataService;
        
        mockController.cardDataService = mockCardDataService;
        mockController.securityQuestionService = mockSecurityQuestionService;
        
        model = new BindingAwareModelMap();
        mockCmd = mock(SecurityQuestionCmdImpl.class);

        doNothing().when(mockSecurityQuestionValidator).validate(anyObject(), any(Errors.class));
    }
    
    @Test
    public void shouldShowChangeSecurityQuestion() {
        when(mockController.showChangeSecurityQuestion((SecurityQuestionCmdImpl)any(), (HttpSession)any())).thenCallRealMethod();
        when(mockController.getFromSession((HttpSession)any(), anyString())).thenReturn(CARD_ID);
        when(mockCardDataService.findById((Long)any())).thenReturn(getTestCardDTO1());
        when(mockSecurityQuestionService.getSecurityQuestionDetails((Long)any())).thenReturn(getTestSecurityQuestionCmd1());        
        mockController.showChangeSecurityQuestion(mockCmd, mockSession);
        verify(mockCardDataService, atLeastOnce()).findById((Long)any());
        verify(mockSecurityQuestionService).getSecurityQuestionDetails((Long)any());
    }

    @Test
    public void shouldpopulateSecurityQuestionsSelectList() {
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        controller.populateSecurityQuestionsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.SECURITY_QUESTIONS));
    }

    @Test
    public void saveChangesShouldShowSuccessUpdateConfirmationPageOnValidInput() {
        when(mockSecurityQuestionService.updateSecurityQuestionDetails((SecurityQuestionCmdImpl) anyObject()))
                .thenReturn(getTestSecurityQuestionCmd1());

        when(mockResult.hasErrors()).thenReturn(false);

        ModelAndView result = controller.saveChanges(new SecurityQuestionCmdImpl(), mockResult, mockRedirectAttributes);
        verify(mockSecurityQuestionService, atLeastOnce()).updateSecurityQuestionDetails((SecurityQuestionCmdImpl) anyObject());
        assertEquals(PageView.CARD_SECURITY_QUESTION_SUCCESS_UPDATE, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowChangeSecurityQuestionOnInvalidInput() {
        when(mockSecurityQuestionService.updateSecurityQuestionDetails((SecurityQuestionCmdImpl) anyObject()))
                .thenReturn(getTestSecurityQuestionCmd1());

        when(mockResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.saveChanges(new SecurityQuestionCmdImpl(), mockResult, mockRedirectAttributes);
        verify(mockSecurityQuestionService, never()).updateSecurityQuestionDetails((SecurityQuestionCmdImpl) anyObject());
        assertEquals(PageView.CHANGE_SECURITY_QUESTION, result.getViewName());
    }

    @Test
    public void cancelShouldRedirectToDashboard() {
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.VIEW_OYSTER_CARD, ((RedirectView) result.getView()).getUrl());
    }
}
