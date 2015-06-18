package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardPreferencesCmdTestUtil.getTestCardPreferencesCmd1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.CardPreferencesServiceImpl;
import com.novacroft.nemo.tfl.common.application_service.impl.SecurityServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.EmailPreferencesValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;

/**
 * Unit tests for ChangeCardPreferencesController
 */
public class ChangeCardPreferencesControllerTest {

    private ChangeCardPreferencesController controller;
    private ChangeCardPreferencesController mockController;
    private SecurityService mockSecurityService;
    private HttpSession mockSession;
    private CardPreferencesService mockCardPreferencesService;
    private SelectStationValidator mockSelectStationValidator;
    private EmailPreferencesValidator mockEmailPreferencesValidator;
    private RedirectAttributes mockRedirectAttributes;

    @Before
    public void setUp() {
        controller = new ChangeCardPreferencesController();
        mockSecurityService = mock(SecurityServiceImpl.class);
        mockSession = mock(HttpSession.class);
        mockController = mock(ChangeCardPreferencesController.class);
        mockCardPreferencesService = mock(CardPreferencesServiceImpl.class);
        mockSelectStationValidator = mock(SelectStationValidator.class);
        mockEmailPreferencesValidator = mock(EmailPreferencesValidator.class);
        mockRedirectAttributes = mock(RedirectAttributes.class);

        controller.cardPreferencesService = mockCardPreferencesService;
        controller.selectStationValidator = mockSelectStationValidator;
        controller.emailPreferencesValidator = mockEmailPreferencesValidator;

        setField(controller, "securityService", mockSecurityService);
    }

    @Test
    public void showChangeCardPreferencesShouldShowCardList() {
        ModelAndView result = controller.showChangeCardPreferences(mockSession);
        assertEquals(PageView.CHANGE_CARD_PREFERENCES, result.getViewName());
        assertNull(((CardPreferencesCmdImpl) result.getModel().get(PageCommand.CARD_PREFERENCES)).getCardId());
    }

    @Test
    public void showChangeCardPreferencesShouldAutoSelectSingleCard() {
        when(mockCardPreferencesService.getPreferences(anyLong(), anyString())).thenReturn(getTestCardPreferencesCmd1());

        ModelAndView result = controller.showChangeCardPreferences(mockSession);

        assertEquals(PageView.CHANGE_CARD_PREFERENCES, result.getViewName());
    }

    @Test
    public void showChangeCardPreferencesShouldReturnCmdFromCardPreferencesService() {

        when(mockController.getFromSession(any(HttpSession.class), anyString())).thenReturn(CardTestUtil.CARD_ID);
        when(mockSession.getAttribute(anyString())).thenReturn(CardTestUtil.CARD_ID);
        when(mockCardPreferencesService.getPreferences(anyLong(), anyString())).thenReturn(getTestCardPreferencesCmd1());

        ModelAndView result = controller.showChangeCardPreferences(mockSession);

        verify(mockCardPreferencesService, atLeastOnce()).getPreferences(anyLong(), anyString());
        assertEquals(PageView.CHANGE_CARD_PREFERENCES, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowChangeCardPreferencesOnInvalidInput() {
        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(true);

        ModelAndView result = controller.saveChanges(new CardPreferencesCmdImpl(), mockResult, mockRedirectAttributes);
        assertEquals(PageView.CHANGE_CARD_PREFERENCES, result.getViewName());
    }

    @Test
    public void saveChangesShouldShowConfirmationPageWithSuccessMessageOnvalidInput() {
        BeanPropertyBindingResult mockResult = mock(BeanPropertyBindingResult.class);
        when(mockResult.hasErrors()).thenReturn(false);
        when(mockCardPreferencesService.updatePreferences(any(CardPreferencesCmdImpl.class))).thenReturn(getTestCardPreferencesCmd1());

        ModelAndView result = controller.saveChanges(getTestCardPreferencesCmd1(), mockResult, mockRedirectAttributes);

        verify(mockCardPreferencesService, atLeastOnce()).updatePreferences(any(CardPreferencesCmdImpl.class));
        assertEquals(PageUrl.CONFIRMATION, ((RedirectView) result.getView()).getUrl());
    }

    @Test
    public void cancelShouldRedirectToDashboard() {
        ChangeCardPreferencesController controller = new ChangeCardPreferencesController();
        ModelAndView result = controller.cancel();
        assertEquals(PageUrl.VIEW_OYSTER_CARD, ((RedirectView) result.getView()).getUrl());
    }
    

    @Test
    public void shouldPopulateStatementEmailFrequenciesSelectList() {
        SelectListService mockSelectListService = mock(SelectListService.class);
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        ChangeCardPreferencesController controller = new ChangeCardPreferencesController();
        controller.selectListService = mockSelectListService;
        BindingAwareModelMap model = new BindingAwareModelMap();
        controller.populateStatementEmailFrequenciesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.STATEMENT_EMAIL_FREQUENCIES));
    }
    
    @Test
    public void shouldPopulateAttachmentTypesSelectList() {
        SelectListService mockSelectListService = mock(SelectListService.class);
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        ChangeCardPreferencesController controller = new ChangeCardPreferencesController();
        controller.selectListService = mockSelectListService;
        BindingAwareModelMap model = new BindingAwareModelMap();
        controller.populateAttachmentTypesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.STATEMENT_ATTACHMENT_TYPES));
    }
    
    @Test
    public void shouldPopulateLocationsSelectList() {
        LocationSelectListService mockLocationSelectListService = mock(LocationSelectListService.class);
        when(mockLocationSelectListService.getLocationSelectList()).thenReturn(new SelectListDTO());
        ChangeCardPreferencesController controller = new ChangeCardPreferencesController();
        controller.locationSelectListService = mockLocationSelectListService;
        BindingAwareModelMap model = new BindingAwareModelMap();
        controller.populateLocationsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.LOCATIONS));
    }
}
