package com.novacroft.nemo.tfl.online.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.JourneyTestUtil;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryItemCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommandAttribute;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryItemValidator;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;


public class JourneyHistoryItemControllerTest {

    private JourneyHistoryItemValidator validator;
    private JourneyHistoryCmdImpl historyCmd;
    private JourneyHistoryItemCmdImpl cmd;
    private JourneyHistoryItemController controller;
    private JourneyDTO mockJourney;
    private BeanPropertyBindingResult bindingResult;
    private JourneyHistoryService mockJourneyHistoryService;

    @Before
    public void setUp() {
        controller = new JourneyHistoryItemController();
        validator = new JourneyHistoryItemValidator();
        historyCmd = new JourneyHistoryCmdImpl();
        cmd = new JourneyHistoryItemCmdImpl();
        cmd.setCardId(CardTestUtil.CARD_ID_1);
        cmd.setJourneyDate(DateTestUtil.getApr03());
        cmd.setJourneyId(JourneyTestUtil.JOURNEY_ID_1);
        bindingResult = new BeanPropertyBindingResult(cmd, "cmd");
        mockJourneyHistoryService = mock(JourneyHistoryService.class);

    }

    @Test
    public void shouldGetHistoryItem() {

        mockJourney = mock(JourneyDTO.class);

        when(mockJourneyHistoryService.getJourneyHistoryItem(any(Long.class), any(Date.class), any(Integer.class))).thenReturn(mockJourney);

        JourneyHistoryItemValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).validate(any(), any(Errors.class));

        controller = new JourneyHistoryItemController();
        controller.journeyHistoryService = mockJourneyHistoryService;
        controller.journeyHistoryItemValidator = validatorSpy;

        ModelAndView modelAndView = controller.showPage(historyCmd, cmd, bindingResult);
        assertEquals(modelAndView.getViewName(), PageView.JOURNEY_HISTORY_ITEM);
        assertFalse(bindingResult.hasErrors());

    }

    @Test
    public void shouldReturnWithValidationDueToNullCardId() {
        cmd.setCardId(null);
        mockJourney = mock(JourneyDTO.class);

        when(mockJourneyHistoryService.getJourneyHistoryItem(any(Long.class), any(Date.class), any(Integer.class))).thenReturn(mockJourney);

        JourneyHistoryItemValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(bindingResult, PageCommandAttribute.FIELD_JOURNEY_DATE);
        doNothing().when(validatorSpy).rejectIfNotShortDate(bindingResult, PageCommandAttribute.FIELD_JOURNEY_DATE);

        controller = new JourneyHistoryItemController();
        controller.journeyHistoryService = mockJourneyHistoryService;
        controller.journeyHistoryItemValidator = validatorSpy;

        ModelAndView modelAndView = controller.showPage(historyCmd, cmd, bindingResult);
        assertEquals(modelAndView.getViewName(), PageView.JOURNEY_HISTORY_ITEM);
        assertTrue(bindingResult.hasErrors());
    }

    @Test
    public void shouldReturnWithValidationDueToNullJourneyId() {
        cmd.setJourneyId(null);
        mockJourney = mock(JourneyDTO.class);

        when(mockJourneyHistoryService.getJourneyHistoryItem(any(Long.class), any(Date.class), any(Integer.class))).thenReturn(mockJourney);

        JourneyHistoryItemValidator validatorSpy = spy(validator);
        doNothing().when(validatorSpy).rejectIfMandatoryFieldEmpty(bindingResult, PageCommandAttribute.FIELD_JOURNEY_DATE);
        doNothing().when(validatorSpy).rejectIfNotShortDate(bindingResult, PageCommandAttribute.FIELD_JOURNEY_DATE);

        controller = new JourneyHistoryItemController();
        controller.journeyHistoryService = mockJourneyHistoryService;
        controller.journeyHistoryItemValidator = validatorSpy;

        ModelAndView modelAndView = controller.showPage(historyCmd, cmd, bindingResult);
        assertEquals(modelAndView.getViewName(), PageView.JOURNEY_HISTORY_ITEM);
        assertTrue(bindingResult.hasErrors());
    }

}
