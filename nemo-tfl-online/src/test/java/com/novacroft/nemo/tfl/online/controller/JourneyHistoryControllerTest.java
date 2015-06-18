package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CardPreferencesCmdTestUtil.getTestCardPreferencesCmd1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards1And2;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards1List;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCards2List;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_3;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.FileTestUtil.TEST_FILE_NAME_1;
import static com.novacroft.nemo.test_support.FileTestUtil.TEST_FILE_SUFFIX_1;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyHistoryDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.time.DateUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.exception.ServiceNotAvailableException;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardPreferencesService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.journey_history.JourneyHistoryServiceImpl;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.command.impl.JourneyHistoryCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.form_validator.JourneyHistoryValidator;
import com.novacroft.nemo.tfl.common.form_validator.SelectCardValidator;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.util.JourneyStatementDateUtil;

/**
 * Unit tests for journey history Controller
 */
public class JourneyHistoryControllerTest {

    private Model model;
    private JourneyHistoryController mockController;
    private BeanPropertyBindingResult mockResult;
    private SelectCardValidator mockSelectCardValidator;
    private JourneyHistoryValidator mockJourneyHistoryValidator;
    private JourneyHistoryService mockJourneyHistoryService;
    private JourneyHistoryCmdImpl cmd;
    private CardPreferencesService mockCardPreferenceService;
    private ApplicationContext applicationContext;
    private SecurityService mockSecurityService;
    private CardDataService mockCardDataService;
    
    @Before
    public void setUp() {
        model = new ExtendedModelMap();
        mockController = mock(JourneyHistoryController.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockSelectCardValidator = mock(SelectCardValidator.class);
        mockJourneyHistoryValidator = mock(JourneyHistoryValidator.class);
        mockJourneyHistoryService = mock(JourneyHistoryService.class);
        mockCardPreferenceService = mock(CardPreferencesService.class);
        applicationContext = mock(ApplicationContext.class);
        mockSecurityService = mock(SecurityService.class);
        mockCardDataService = mock(CardDataService.class);
        cmd = new JourneyHistoryCmdImpl();

        mockController.setApplicationContext(applicationContext);
        mockController.cardPreferencesService = mockCardPreferenceService;
        mockController.cardDataService = mockCardDataService;
        setField(mockController, "securityService", mockSecurityService);
        when(mockController.getStartDate(any(Date.class), anyInt())).thenCallRealMethod();
        when(mockController.computeAndSetStartDateAndEndDate(any(JourneyHistoryCmdImpl.class), anyInt())).thenCallRealMethod();
    }

    @Test
    public void shouldGetFileName() {
        CardDataService mockCardDataService = mock(CardDataService.class);
        when(mockCardDataService.findById(anyLong())).thenReturn(getTestCardDTO1());
        JourneyHistoryController controller = new JourneyHistoryController();
        controller.cardDataService = mockCardDataService;
        assertTrue(controller.getFileName(CARD_ID_1, TEST_FILE_SUFFIX_1).matches(
                        OYSTER_NUMBER_1 + "-[0-9]{4}_[0-9]{2}_[0-9]{2}-[0-9]{2}_[0-9]{2}_[0-9]{2}." + TEST_FILE_SUFFIX_1));
    }

    @Test
    public void shouldGetPdfFileName() {
        when(mockController.getPdfFileName(anyLong())).thenCallRealMethod();
        when(mockController.getFileName(anyLong(), anyString())).thenReturn(TEST_FILE_NAME_1);
        assertEquals(TEST_FILE_NAME_1, mockController.getPdfFileName(CARD_ID_1));
    }

    @Test
    public void shouldGetCsvFileName() {
        when(mockController.getCsvFileName(anyLong())).thenCallRealMethod();
        when(mockController.getFileName(anyLong(), anyString())).thenReturn(TEST_FILE_NAME_1);
        assertEquals(TEST_FILE_NAME_1, mockController.getCsvFileName(CARD_ID_1));
    }

    @Test
    public void shouldReloadJourneyHistory() {
        when(mockController.reloadJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class))).thenCallRealMethod();
        mockController.reloadJourneyHistory(cmd, mockResult, model);
        verify(mockController).showJourneyHistory(cmd, mockResult, model);
    }

    @Test
    public void shouldDownloadAsCSV() throws IOException {

        when(mockJourneyHistoryService.getJourneyHistoryAsCsv(any(JourneyHistoryDTO.class))).thenReturn(new byte[] {});

        MockHttpServletResponse mockResponse = mock(MockHttpServletResponse.class);

        OutputStream mockOutputStream = mock(OutputStream.class);

        doCallRealMethod().when(mockController).downloadAsCSV(any(JourneyHistoryCmdImpl.class), any(HttpServletResponse.class),
                        any(OutputStream.class));
        when(mockController.getCsvFileName(anyLong())).thenReturn(TEST_FILE_NAME_1);
        doNothing().when(mockController).streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(),
                        any(byte[].class));
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);
        when(mockController.getJourneyHistoryData(cmd)).thenReturn(getTestJourneyHistoryDTO1());

        mockController.journeyHistoryService = mockJourneyHistoryService;

        mockController.downloadAsCSV(cmd, mockResponse, mockOutputStream);

        verify(mockController).downloadAsCSV(any(JourneyHistoryCmdImpl.class), any(HttpServletResponse.class), any(OutputStream.class));
        verify(mockController).getCsvFileName(anyLong());
        verify(mockJourneyHistoryService).getJourneyHistoryAsCsv(any(JourneyHistoryDTO.class));
        verify(mockController).streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(), any(byte[].class));
    }

    @Test
    public void shouldDownloadAsPDF() throws IOException {

        when(mockJourneyHistoryService.getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class))).thenReturn(new byte[] {});

        MockHttpServletResponse mockResponse = mock(MockHttpServletResponse.class);

        OutputStream mockOutputStream = mock(OutputStream.class);

        doCallRealMethod().when(mockController).downloadAsPDF(any(JourneyHistoryCmdImpl.class), any(HttpServletResponse.class),
                        any(OutputStream.class));
        doCallRealMethod().when(mockController).setAttachedFileNameOnResponseHeader(any(HttpServletResponse.class), anyString());
        when(mockController.getPdfFileName(anyLong())).thenReturn(TEST_FILE_NAME_1);
        doNothing().when(mockController).streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(),
                        any(byte[].class));
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);
        when(mockController.getJourneyHistoryData(cmd)).thenReturn(getTestJourneyHistoryDTO1());

        mockController.journeyHistoryService = mockJourneyHistoryService;

        mockController.downloadAsPDF(cmd, mockResponse, mockOutputStream);

        verify(mockController).downloadAsPDF(any(JourneyHistoryCmdImpl.class), any(HttpServletResponse.class), any(OutputStream.class));
        verify(mockController).getPdfFileName(anyLong());
        verify(mockJourneyHistoryService).getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class));
        verify(mockController).streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(), any(byte[].class));
    }

    @Test
    public void shouldShowJourneyHistoryWithValidationErrors() {

        when(mockResult.hasErrors()).thenReturn(Boolean.TRUE);

        doNothing().when(mockSelectCardValidator).validate(any(), any(Errors.class));

        doNothing().when(mockJourneyHistoryValidator).validate(any(), any(Errors.class));

        when(mockJourneyHistoryService.getJourneyHistory(anyLong(), any(Date.class), any(Date.class))).thenReturn(getTestJourneyHistoryDTO1());

        doCallRealMethod().when(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.journeyHistoryValidator = mockJourneyHistoryValidator;
        mockController.journeyHistoryService = mockJourneyHistoryService;

        ModelAndView result = mockController.showJourneyHistory(cmd, mockResult, model);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        verify(mockSelectCardValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryService, never()).getJourneyHistory(anyLong(), any(Date.class), any(Date.class));
    }

    @Test
    public void shouldShowJourneyHistoryWithoutValidationErrors() {

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);
        doNothing().when(mockResult).reject(anyString());

        doNothing().when(mockSelectCardValidator).validate(any(), any(Errors.class));

        doNothing().when(mockJourneyHistoryValidator).validate(any(), any(Errors.class));

        ServiceNotAvailableException serviceNotAvailableException = new ServiceNotAvailableException("Test Error");
        when(mockJourneyHistoryService.getJourneyHistory(anyLong(), any(Date.class), any(Date.class))).thenThrow(serviceNotAvailableException);

        doCallRealMethod().when(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController).getJourneyHistoryData(cmd);
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.journeyHistoryValidator = mockJourneyHistoryValidator;
        mockController.journeyHistoryService = mockJourneyHistoryService;

        ModelAndView result = mockController.showJourneyHistory(cmd, mockResult, model);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        verify(mockSelectCardValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryService).getJourneyHistory(anyLong(), any(Date.class), any(Date.class));
        verify(mockResult).reject(anyString());
    }

    @Test
    public void shouldShowJourneyHistoryWithServiceNotAvailable() {

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doNothing().when(mockSelectCardValidator).validate(any(), any(Errors.class));

        doNothing().when(mockJourneyHistoryValidator).validate(any(), any(Errors.class));

        when(mockJourneyHistoryService.getJourneyHistory(anyLong(), any(Date.class), any(Date.class))).thenReturn(getTestJourneyHistoryDTO1());

        doCallRealMethod().when(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController).getJourneyHistoryData(cmd);
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.journeyHistoryValidator = mockJourneyHistoryValidator;
        mockController.journeyHistoryService = mockJourneyHistoryService;

        ModelAndView result = mockController.showJourneyHistory(cmd, mockResult, model);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        verify(mockSelectCardValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryService).getJourneyHistory(anyLong(), any(Date.class), any(Date.class));
    }

    @Test
    public void shouldShowPageNoCardIdPassedViaSession() {
        JourneyHistoryService historyService = new JourneyHistoryServiceImpl();

        ApplicationContext applicationContext = mock(ApplicationContext.class);
        ReflectionTestUtils.setField(historyService, "applicationContext", applicationContext);
        when(applicationContext.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn("Mocked Content");

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doCallRealMethod().when(mockController).showPage(any(Long.class), any(SelectListDTO.class), any(SelectListDTO.class),
                        any(SelectListDTO.class), any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController)
                        .setDefaultCardIdOnCommand(any(Long.class), any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));
        doCallRealMethod().when(mockController).setDefaultWeekRangeOnCommand(any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));

        ModelAndView result = mockController.showPage(null, getTestCards2List(), historyService.getWeekRangeSelectListDTO(), getTestCards2List(), cmd, mockResult, model);
        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        cmd = (JourneyHistoryCmdImpl) result.getModel().get(PageCommand.JOURNEY_HISTORY);
        assertNull(cmd.getCardId());
        verify(mockController, never()).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
    }

    @Test
    public void shouldShowPageNoCardIdPassedViaSessionAndCardListHasOneItemOnly() {
        JourneyHistoryService historyService = new JourneyHistoryServiceImpl();

        ApplicationContext applicationContext = mock(ApplicationContext.class);
        ReflectionTestUtils.setField(historyService, "applicationContext", applicationContext);
        when(applicationContext.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn("Mocked Content");

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doCallRealMethod().when(mockController).showPage(any(Long.class), any(SelectListDTO.class), any(SelectListDTO.class),
                        any(SelectListDTO.class), any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController)
                        .setDefaultCardIdOnCommand(any(Long.class), any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));
        doCallRealMethod().when(mockController).setDefaultWeekRangeOnCommand(any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));

        when(mockController.showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class))).thenReturn(null);

        ModelAndView result = mockController.showPage(null, getTestCards1List(), historyService.getWeekRangeSelectListDTO(), getTestCards2List(), cmd, mockResult, model);
        verify(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
    }

    @Test
    public void shouldShowPageWithCardIdPassedViaSession() {
        JourneyHistoryService historyService = new JourneyHistoryServiceImpl();

        ApplicationContext applicationContext = mock(ApplicationContext.class);
        ReflectionTestUtils.setField(historyService, "applicationContext", applicationContext);
        when(applicationContext.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn("Mocked Content");

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doCallRealMethod().when(mockController)
                        .setDefaultCardIdOnCommand(any(Long.class), any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));
        doCallRealMethod().when(mockController).showPage(any(Long.class), any(SelectListDTO.class), any(SelectListDTO.class),
                        any(SelectListDTO.class), any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        when(mockController.showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class))).thenReturn(null);
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        ModelAndView result = mockController.showPage(CardTestUtil.CARD_ID_1, getTestCards1List(), historyService.getWeekRangeSelectListDTO(), getTestCards2List(), cmd,
                        mockResult, model);
        verify(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
    }

    @Test
    public void shouldShowJourneyHistoryForThisWeek() {
        // week range selected as 'this week'
        cmd.setWeekNumberFromToday(new Integer(0));
        cmd.setCardId(CardTestUtil.CARD_ID_1);

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doNothing().when(mockSelectCardValidator).validate(any(), any(Errors.class));

        doNothing().when(mockJourneyHistoryValidator).validate(any(), any(Errors.class));

        Date startDate = JourneyStatementDateUtil.getStartOfWeek(new Date());
        Date endDate = JourneyStatementDateUtil.getEndOfWeek(startDate);
        when(mockJourneyHistoryService.getJourneyHistory(any(Long.class), any(Date.class), any(Date.class))).thenReturn(getTestJourneyHistoryDTO1());

        doCallRealMethod().when(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController).getJourneyHistoryData(cmd);
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.journeyHistoryValidator = mockJourneyHistoryValidator;
        mockController.journeyHistoryService = mockJourneyHistoryService;

        ModelAndView result = mockController.showJourneyHistory(cmd, mockResult, model);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        verify(mockSelectCardValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryService).getJourneyHistory(any(Long.class), any(Date.class), any(Date.class));
    }

    @Test
    public void shouldShowJourneyHistoryForOneWeekAgo() {
        // week range selected as '1' - previous week
        cmd.setWeekNumberFromToday(new Integer(1));
        cmd.setCardId(CardTestUtil.CARD_ID_1);

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doNothing().when(mockSelectCardValidator).validate(any(), any(Errors.class));

        doNothing().when(mockJourneyHistoryValidator).validate(any(), any(Errors.class));

        Date yesterday = DateUtil.addDaysToDate(new Date(), -1);
        Date startDate = JourneyStatementDateUtil.getStartOfWeek(yesterday);
        startDate = DateUtils.addDays(startDate, -7);
        Date endDate = JourneyStatementDateUtil.getEndOfWeek(startDate);
        when(mockJourneyHistoryService.getJourneyHistory(cmd.getCardId(), startDate, endDate)).thenReturn(getTestJourneyHistoryDTO1());

        doCallRealMethod().when(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController).getJourneyHistoryData(cmd);
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.journeyHistoryValidator = mockJourneyHistoryValidator;
        mockController.journeyHistoryService = mockJourneyHistoryService;

        ModelAndView result = mockController.showJourneyHistory(cmd, mockResult, model);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        verify(mockSelectCardValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryService).getJourneyHistory(cmd.getCardId(), startDate, endDate);
    }

    @Test
    public void shouldShowJourneyHistoryForSixWeeksAgo() {
        // week range selected as '1' - previous week
        cmd.setWeekNumberFromToday(new Integer(6));
        cmd.setCardId(CardTestUtil.CARD_ID_1);

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doNothing().when(mockSelectCardValidator).validate(any(), any(Errors.class));

        doNothing().when(mockJourneyHistoryValidator).validate(any(), any(Errors.class));

        Date yesterday = DateUtil.addDaysToDate(new Date(), -1);
        Date startDate = JourneyStatementDateUtil.getStartOfWeek(yesterday);
        startDate = DateUtil.addDaysToDate(startDate, -42);
        Date endDate = JourneyStatementDateUtil.getEndOfWeek(startDate);
        when(mockJourneyHistoryService.getJourneyHistory(cmd.getCardId(), startDate, endDate)).thenReturn(getTestJourneyHistoryDTO1());

        doCallRealMethod().when(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController).getJourneyHistoryData(cmd);
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.journeyHistoryValidator = mockJourneyHistoryValidator;
        mockController.journeyHistoryService = mockJourneyHistoryService;

        ModelAndView result = mockController.showJourneyHistory(cmd, mockResult, model);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        verify(mockSelectCardValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryService).getJourneyHistory(cmd.getCardId(), startDate, endDate);
    }

    @Test
    public void shouldShowJourneyHistoryForCustomDate() {
        // week range selected as '1' - previous week
        cmd.setWeekNumberFromToday(new Integer(10));
        cmd.setCardId(CardTestUtil.CARD_ID_1);
        cmd.setStartDate(DateTestUtil.getMon10Feb());
        cmd.setEndDate(DateTestUtil.getSun23Feb());

        when(mockResult.hasErrors()).thenReturn(Boolean.FALSE);

        doNothing().when(mockSelectCardValidator).validate(any(), any(Errors.class));

        doNothing().when(mockJourneyHistoryValidator).validate(any(), any(Errors.class));

        when(mockJourneyHistoryService.getJourneyHistory(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate())).thenReturn(
                        getTestJourneyHistoryDTO1());

        doCallRealMethod().when(mockController).showJourneyHistory(any(JourneyHistoryCmdImpl.class), any(BindingResult.class), any(Model.class));
        doCallRealMethod().when(mockController).getJourneyHistoryData(cmd);
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(true);

        mockController.selectCardValidator = mockSelectCardValidator;
        mockController.journeyHistoryValidator = mockJourneyHistoryValidator;
        mockController.journeyHistoryService = mockJourneyHistoryService;

        ModelAndView result = mockController.showJourneyHistory(cmd, mockResult, model);

        assertViewName(result, PageView.JOURNEY_HISTORY);
        assertModelAttributeAvailable(result, PageCommand.JOURNEY_HISTORY);
        verify(mockSelectCardValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryValidator).validate(any(), any(Errors.class));
        verify(mockJourneyHistoryService).getJourneyHistory(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate());
    }

    @Test
    public void getJourneyHistoryDataShouldInvokeJourneyHistoryServiceWhenThisWeekIsChoosenTest() {
        mockController.journeyHistoryService = mockJourneyHistoryService;
        doCallRealMethod().when(mockController).getJourneyHistoryData(any(JourneyHistoryCmdImpl.class));
        when(mockJourneyHistoryService.getJourneyHistory(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate())).thenReturn(
                        getTestJourneyHistoryDTO1());

        cmd.setWeekNumberFromToday(0);
        mockController.getJourneyHistoryData(cmd);
        verify(mockJourneyHistoryService).getJourneyHistory(any(Long.class), any(Date.class), any(Date.class));
    }

    @Test
    public void getJourneyHistoryDataShouldInvokeJourneyHistoryServiceWhenOtherOptionsAreChoosenTest() {
        mockController.journeyHistoryService = mockJourneyHistoryService;
        doCallRealMethod().when(mockController).getJourneyHistoryData(any(JourneyHistoryCmdImpl.class));
        when(mockJourneyHistoryService.getJourneyHistory(cmd.getCardId(), cmd.getStartDate(), cmd.getEndDate())).thenReturn(
                        getTestJourneyHistoryDTO1());

        cmd.setWeekNumberFromToday(1);
        mockController.getJourneyHistoryData(cmd);
        verify(mockJourneyHistoryService).getJourneyHistory(any(Long.class), any(Date.class), any(Date.class));
    }

    @Test
    public void setEmailStatementPreferenceOnCommandShouldInvokeServicesTest() {
        doCallRealMethod().when(mockController).setEmailStatementPreferenceOnCommand(any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));
        when(mockCardPreferenceService.getPreferences(any(Long.class), anyString())).thenReturn(getTestCardPreferencesCmd1());
        mockController.setEmailStatementPreferenceOnCommand(cmd, getTestCards1List());
        verify(mockCardPreferenceService).getPreferences(any(Long.class), anyString());
    }

    @Test
    public void setEmailStatementPreferenceOnCommandShouldNotInvokeServicesTest() {
        doCallRealMethod().when(mockController).setEmailStatementPreferenceOnCommand(any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));
        when(mockCardPreferenceService.getPreferences(any(Long.class), anyString())).thenReturn(getTestCardPreferencesCmd1());
        SelectListDTO selectList = new SelectListDTO();
        mockController.setEmailStatementPreferenceOnCommand(cmd, selectList);
        verify(mockCardPreferenceService, never()).getPreferences(any(Long.class), anyString());
    }

    @Test
    public void setDefaultCardIdOnCommandShouldSetCardIdTest() {
        doCallRealMethod().when(mockController)
                        .setDefaultCardIdOnCommand(any(Long.class), any(JourneyHistoryCmdImpl.class), any(SelectListDTO.class));
        when(mockCardPreferenceService.getPreferences(any(Long.class), anyString())).thenReturn(getTestCardPreferencesCmd1());
        SelectListDTO selectList = new SelectListDTO();
        mockController.setDefaultCardIdOnCommand(CARD_ID_1, cmd, selectList);
        assertEquals(CARD_ID_1, cmd.getCardId());
    }

    @Test
    public void doesCardIdBelongToUserShouldReturnTrueTest() {
        when(mockCardDataService.findByCustomerId(any(Long.class))).thenReturn(getTestCards1And2());
        when(mockController.getCustomer()).thenReturn(getTestCustomerDTO1());
        doCallRealMethod().when(mockController).doesCardIdBelongToUser(any(Long.class));
        assertTrue(mockController.doesCardIdBelongToUser(CARD_ID_1));
    }

    @Test
    public void doesCardIdBelongToUserShouldReturnFalseTest() {
        when(mockCardDataService.findByCustomerId(any(Long.class))).thenReturn(getTestCards1And2());
        when(mockController.getCustomer()).thenReturn(getTestCustomerDTO1());
        doCallRealMethod().when(mockController).doesCardIdBelongToUser(any(Long.class));
        assertFalse(mockController.doesCardIdBelongToUser(CARD_ID_3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldDownloadAsPDFThrowIllegalArgumentExceptionTest() {
        when(mockJourneyHistoryService.getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class))).thenReturn(new byte[] {});
        MockHttpServletResponse mockResponse = mock(MockHttpServletResponse.class);
        OutputStream mockOutputStream = mock(OutputStream.class);
        doCallRealMethod().when(mockController).downloadAsPDF(any(JourneyHistoryCmdImpl.class), any(HttpServletResponse.class),
                        any(OutputStream.class));
        doCallRealMethod().when(mockController).setAttachedFileNameOnResponseHeader(any(HttpServletResponse.class), anyString());
        when(mockController.getPdfFileName(anyLong())).thenReturn(TEST_FILE_NAME_1);
        doNothing().when(mockController).streamFile(any(HttpServletResponse.class), any(OutputStream.class), anyString(), anyString(),
                        any(byte[].class));
        when(mockController.doesCardIdBelongToUser(anyLong())).thenReturn(false);
        when(mockController.getJourneyHistoryData(cmd)).thenReturn(getTestJourneyHistoryDTO1());

        mockController.journeyHistoryService = mockJourneyHistoryService;
        cmd.setCardId(CARD_ID_3);
        mockController.downloadAsPDF(cmd, mockResponse, mockOutputStream);

        verify(mockController).downloadAsPDF(any(JourneyHistoryCmdImpl.class), any(HttpServletResponse.class), any(OutputStream.class));
        verify(mockJourneyHistoryService, never()).getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class));
    }

    @Test
    public void shouldComputeAndSetStartDateAndEndDateForLastWeek() {
        assertNull(cmd.getStartDate());
        assertNull(cmd.getEndDate());
        mockController.computeAndSetStartDateAndEndDate(cmd, 9);
        assertNotNull(cmd.getStartDate());
        assertNotNull(cmd.getEndDate());
    }

    @Test
    public void shouldComputeAndSetStartDateAndEndDateForLastSevenDays() {
        assertNull(cmd.getStartDate());
        assertNull(cmd.getEndDate());
        mockController.computeAndSetStartDateAndEndDate(cmd, 0);
        assertNotNull(cmd.getStartDate());
        assertNotNull(cmd.getEndDate());
    }

    @Test
    public void shouldComputeAndSetStartDateAndEndDateForPreviousWeek() {
        assertNull(cmd.getStartDate());
        assertNull(cmd.getEndDate());
        mockController.computeAndSetStartDateAndEndDate(cmd, 1);
        assertNotNull(cmd.getStartDate());
        assertNotNull(cmd.getEndDate());
    }
}