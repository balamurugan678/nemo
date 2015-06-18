package com.novacroft.nemo.tfl.innovator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertAndReturnModelAttributeOfType;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.LocationSelectListService;
import com.novacroft.nemo.tfl.common.command.impl.CardPreferencesCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.constant.PageAttribute;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.EmailPreferencesValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectStationValidator;

public class CardPreferencesControllerTest {
    private static final Logger logger = LoggerFactory.getLogger(CardPreferencesControllerTest.class);
    protected CardPreferencesController controller;
    protected HttpServletRequest mockRequest;
    protected CardPreferencesService mockService;
    protected LocationSelectListService mockLocationSelectListService;
    protected SelectListService mockSelectListService;
    
    protected SelectStationValidator mockSelectStationValidator;
    protected EmailPreferencesValidator mockEmailPreferencesValidator;
    protected BindingResult mockBindingResult;
    protected CardPreferencesCmdImpl cmd;
    protected Errors mockErrors;
    protected Model mockModel;
    protected Model model;
    protected SelectListDTO selectList;
    private MessageSource mockMessageSource;

    @Before
    public void setup() {
        controller = new CardPreferencesController();
        mockService = mock(CardPreferencesService.class);
        mockSelectStationValidator = mock(SelectStationValidator.class);
        mockEmailPreferencesValidator = mock(EmailPreferencesValidator.class);
        mockBindingResult = mock(BindingResult.class);
        mockErrors = mock(Errors.class);
        mockModel = mock(Model.class);
        mockLocationSelectListService = mock(LocationSelectListService.class);
        mockSelectListService = mock(SelectListService.class);
        model = new BindingAwareModelMap();
        selectList = new SelectListDTO();
        mockMessageSource = mock(MessageSource.class);
        
        controller.cardPreferencesService = mockService;
        controller.selectStationValidator = mockSelectStationValidator;
        controller.emailPreferencesValidator = mockEmailPreferencesValidator;
        controller.locationSelectListService = mockLocationSelectListService;
        controller.selectListService = mockSelectListService;
        controller.messageSource = mockMessageSource;
        
        cmd = new CardPreferencesCmdImpl();
        when(mockService.getPreferencesByCardNumber(anyString())).thenReturn(cmd);
        when(mockService.getPreferencesByCardId(anyLong())).thenReturn(cmd);
        doNothing().when(mockSelectStationValidator).validate(any(), any(Errors.class));
        doNothing().when(mockEmailPreferencesValidator).validate(any(), any(Errors.class));
    }
    
    @Test
    public void testPopulateLocationSelectList(){
        
        when(mockLocationSelectListService.getLocationSelectList()).thenReturn(selectList);
        controller.populateLocationsSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.LOCATIONS));
    }
    
    @Test
    public void testPopulateStatementEmailFrequenciesSelectList(){
        when(mockSelectListService.getSelectList(anyString())).thenReturn(selectList);
        controller.populateStatementEmailFrequenciesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.STATEMENT_EMAIL_FREQUENCIES));
    }
    
    @Test
    public void testPopulateAttachmentTypesSelectList(){
        when(mockSelectListService.getSelectList(anyString())).thenReturn(selectList);
        controller.populateAttachmentTypesSelectList(model);
        assertTrue(model.containsAttribute(PageAttribute.STATEMENT_ATTACHMENT_TYPES));
    }
    
    @Test
    public void testViewCardPreferencesReturned() {
        ModelAndView view = controller.load("1", mockRequest);
        assertViewName(view, PageView.INV_CARD_PREFERENCES);
    }
    
    @Test
    public void testLoadReturnsCardPreferences() {
        ModelAndView modelAndView = controller.load("1", mockRequest);
        assertAndReturnModelAttributeOfType(modelAndView, PageCommand.CARD_PREFERENCES, CardPreferencesCmdImpl.class);
        assertViewName(modelAndView, PageView.INV_CARD_PREFERENCES);
    }
    
    @Test
    public void tesLoadCardPreferencesNotReturned() {
        when(mockService.getPreferencesByCardNumber(anyString())).thenReturn(null);
        ModelAndView modelAndView = controller.load("1", mockRequest);
        assertEquals(null, modelAndView.getModel().get(PageCommand.CARD_PREFERENCES));
        assertViewName(modelAndView, PageView.INV_CARD_PREFERENCES);
    }
    
    @Test
    public void testSaveValid() {
        when(mockService.updatePreferences(cmd)).thenReturn(cmd);
        when(mockMessageSource.getMessage(anyString(), any(Object[].class), anyString(), any(Locale.class))).thenReturn(ContentCode.CHANGE_CARD_PREFERENCES_UPDATE_SUCCESSFUL.textCode());
        ModelAndView modelAndView = controller.save(cmd, mockBindingResult);
        assertViewName(modelAndView, PageView.INV_CARD_PREFERENCES);
        assertAndReturnModelAttributeOfType(modelAndView, PageCommand.CARD_PREFERENCES, CardPreferencesCmdImpl.class);
        assertEquals(modelAndView.getModel().get("message"),ContentCode.CHANGE_CARD_PREFERENCES_UPDATE_SUCCESSFUL.textCode() );
    }
    
    @Test
    public void testSaveErrorsReturned() {
        when(mockBindingResult.hasErrors()).thenReturn(true);
        when(mockService.updatePreferences(cmd)).thenReturn(cmd);
        ModelAndView modelAndView = controller.save(cmd, mockBindingResult);
        assertViewName(modelAndView, PageView.INV_CARD_PREFERENCES);
        assertAndReturnModelAttributeOfType(modelAndView, PageCommand.CARD_PREFERENCES, CardPreferencesCmdImpl.class);
        
    }
    
}
