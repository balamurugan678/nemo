package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.CardTestUtil.getTestListOfValidationErrors;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeValue;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageParameter;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

/**
 * JourneyHistoryController unit tests
 */
public class JourneyHistoryControllerTest {
    private static final Long DEFAULT_CARD_ID = 0L;
    private JourneyHistoryController controller;
    private JourneyHistoryValidator mockJourneyHistoryValidator;
    private JourneyHistoryService mockJourneyHistoryService;

    private JourneyHistoryDTO mockJourneyHistoryDTO;
    private JourneyHistoryCmdImpl mockJourneyHistoryCmd;
    private BindingResult mockBindingResult;
    private ServletRequestDataBinder mockServletRequestDataBinder;

    @Before
    public void setUp() {
        this.controller = mock(JourneyHistoryController.class);
        this.mockJourneyHistoryValidator = mock(JourneyHistoryValidator.class);
        this.controller.journeyHistoryValidator = this.mockJourneyHistoryValidator;
        this.mockJourneyHistoryService = mock(JourneyHistoryService.class);
        this.controller.journeyHistoryService = this.mockJourneyHistoryService;
        this.mockServletRequestDataBinder = mock(ServletRequestDataBinder.class);

        this.mockJourneyHistoryDTO = mock(JourneyHistoryDTO.class);
        this.mockJourneyHistoryCmd = mock(JourneyHistoryCmdImpl.class);
        this.mockBindingResult = mock(BindingResult.class);
    }

    @Test
    public void shouldShowPageWithId() {
        when(this.controller.showPage(anyLong(), anyString())).thenCallRealMethod();

        ModelAndView result = this.controller.showPage(CARD_ID_1, OYSTER_NUMBER_1);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        assertEquals(CARD_ID_1, ((JourneyHistoryCmdImpl) result.getModel().get(PageCommand.JOURNEY_HISTORY)).getCardId());
    }

    @Test
    public void shouldShowPageWithoutId() {
        when(this.controller.showPage(anyLong(), anyString())).thenCallRealMethod();

        ModelAndView result = this.controller.showPage(null, null);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        assertEquals(DEFAULT_CARD_ID, ((JourneyHistoryCmdImpl) result.getModel().get(PageCommand.JOURNEY_HISTORY)).getCardId());
    }

    @Test
    public void shouldShowPageAndAddIdToModelWithId() {
        when(this.controller.showPageAndAddIdToModel(anyLong())).thenCallRealMethod();

        ModelAndView result = this.controller.showPageAndAddIdToModel(CARD_ID_1);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageParameter.ID);
        assertEquals(CARD_ID_1, ((JourneyHistoryCmdImpl) result.getModel().get(PageCommand.JOURNEY_HISTORY)).getCardId());
        assertModelAttributeValue(result, PageParameter.ID, CARD_ID_1);
    }

    @Test
    public void shouldShowPageAndAddIdToModelWithoutId() {
        when(this.controller.showPageAndAddIdToModel(anyLong())).thenCallRealMethod();

        ModelAndView result = this.controller.showPageAndAddIdToModel(null);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageParameter.ID);
        assertEquals(DEFAULT_CARD_ID, ((JourneyHistoryCmdImpl) result.getModel().get(PageCommand.JOURNEY_HISTORY)).getCardId());
        assertNull((result.getModel().get(PageParameter.ID)));
    }

    @Test
    public void shouldShowJourneyHistory() {
        when(this.controller.showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class)))
                .thenCallRealMethod();
        doNothing().when(this.mockJourneyHistoryValidator).validate(any(JourneyHistoryCmdImpl.class), any(BindingResult.class));
        when(this.mockBindingResult.hasErrors()).thenReturn(false);
        when(this.mockJourneyHistoryService.getJourneyHistory(anyLong(), any(Date.class), any(Date.class)))
                .thenReturn(this.mockJourneyHistoryDTO);

        ModelAndView result = this.controller.showJourneyHistory(this.mockJourneyHistoryCmd, this.mockBindingResult);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);

        verify(this.mockJourneyHistoryValidator).validate(any(JourneyHistoryCmdImpl.class), any(BindingResult.class));
        verify(this.mockJourneyHistoryService).getJourneyHistory(anyLong(), any(Date.class), any(Date.class));
    }

    @Test
    public void shouldShowJourneyHistoryWithValidationErrors() {
        when(this.controller.showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class)))
                .thenCallRealMethod();
        doNothing().when(this.mockJourneyHistoryValidator).validate(any(JourneyHistoryCmdImpl.class), any(BindingResult.class));
        when(this.mockBindingResult.hasErrors()).thenReturn(true);
        when(this.mockJourneyHistoryService.getJourneyHistory(anyLong(), any(Date.class), any(Date.class)))
                .thenReturn(this.mockJourneyHistoryDTO);

        ModelAndView result = this.controller.showJourneyHistory(this.mockJourneyHistoryCmd, this.mockBindingResult);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);

        verify(this.mockJourneyHistoryValidator).validate(any(JourneyHistoryCmdImpl.class), any(BindingResult.class));
        verify(this.mockJourneyHistoryService, never()).getJourneyHistory(anyLong(), any(Date.class), any(Date.class));
    }

    @Test
    public void shouldShowJourneyHistoryWithServiceBusy() {
    	
        when(this.controller.showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class)))
                .thenCallRealMethod();
        doNothing().when(this.mockJourneyHistoryValidator).validate(any(JourneyHistoryCmdImpl.class), any(BindingResult.class));
        when(this.mockBindingResult.hasErrors()).thenReturn(false);
        doNothing().when(this.mockBindingResult).reject(anyString());

        when(this.mockJourneyHistoryService.getJourneyHistory(anyLong(), any(Date.class), any(Date.class)))
                .thenThrow(new ServiceNotAvailableException("test-error"));

        ModelAndView result = this.controller.showJourneyHistory(this.mockJourneyHistoryCmd, this.mockBindingResult);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);

        verify(this.mockJourneyHistoryValidator).validate(any(JourneyHistoryCmdImpl.class), any(BindingResult.class));
        verify(this.mockJourneyHistoryService).getJourneyHistory(anyLong(), any(Date.class), any(Date.class));
        verify(this.mockBindingResult).reject(anyString());
    }
    
    @Test
    public void initBinderShouldRegisterCustomEditor() {
    	controller = new JourneyHistoryController();
        doNothing().when(mockServletRequestDataBinder).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
        controller.initBinder(mockServletRequestDataBinder);
        verify(mockServletRequestDataBinder, atLeastOnce()).registerCustomEditor(eq(Date.class), any(CustomDateEditor.class));
    }

    @Test
    public void shouldShowJourneyHistoryWithBindError(){
    	controller = new JourneyHistoryController();
    	controller.journeyHistoryValidator = this.mockJourneyHistoryValidator;
    	mockJourneyHistoryCmd = new JourneyHistoryCmdImpl();
        when(mockBindingResult.getAllErrors()).thenReturn(getTestListOfValidationErrors());
        when(mockBindingResult.hasErrors()).thenReturn(Boolean.TRUE);
        ModelAndView result = controller.showJourneyHistory(mockJourneyHistoryCmd, mockBindingResult);
        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertTrue(mockBindingResult.hasErrors());
    }
}
