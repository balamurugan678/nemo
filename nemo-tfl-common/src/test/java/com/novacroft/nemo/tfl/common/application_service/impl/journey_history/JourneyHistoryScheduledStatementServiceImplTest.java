package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesDTO1;
import static com.novacroft.nemo.test_support.CardPreferencesTestUtil.getTestCardPreferencesDTOList1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22;
import static com.novacroft.nemo.test_support.EmailTestUtil.TEST_SALUTATION;
import static com.novacroft.nemo.test_support.JourneyTestUtil.DUMMY_CSV;
import static com.novacroft.nemo.test_support.JourneyTestUtil.DUMMY_PDF;
import static com.novacroft.nemo.test_support.JourneyTestUtil.getTestJourneyHistoryDTO1;
import static com.novacroft.nemo.test_support.UriTestUtil.BASE_URI_1;
import static com.novacroft.nemo.test_support.UriTestUtil.BASE_URI_STRING_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.getTestWebAccountDTO1;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Date;

import org.junit.Ignore;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.ApplicationEventService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryStatementEmailService;
import com.novacroft.nemo.tfl.common.constant.JourneyHistoryFrequency;
import com.novacroft.nemo.tfl.common.constant.JourneyHistoryOutput;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardPreferencesDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.CustomerDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

/**
 * JourneyHistoryScheduledStatementService unit tests
 */
public class JourneyHistoryScheduledStatementServiceImplTest {

    @Test
    public void shouldBuildAdditionalInformation() {
        EmailArgumentsDTO mockEmailArgumentsDTO = mock(EmailArgumentsDTO.class);
        when(mockEmailArgumentsDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockEmailArgumentsDTO.getRangeFrom()).thenReturn(getAug19());
        when(mockEmailArgumentsDTO.getRangeTo()).thenReturn(getAug22());

        JourneyHistoryScheduledStatementServiceImpl service =
                mock(JourneyHistoryScheduledStatementServiceImpl.class, CALLS_REAL_METHODS);
        assertEquals("Journey History Statement; Oyster card number [100000000001]; period [19/08/2013 - 22/08/2013]",
                service.buildAdditionalInformation(mockEmailArgumentsDTO));
    }

    @Test
    public void shouldCreateEvent() {
        ApplicationEventService mockApplicationEventService = mock(ApplicationEventService.class);
        doNothing().when(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());

        EmailArgumentsDTO mockEmailArgumentsDTO = mock(EmailArgumentsDTO.class);
        when(mockEmailArgumentsDTO.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockEmailArgumentsDTO.getRangeFrom()).thenReturn(getAug19());
        when(mockEmailArgumentsDTO.getRangeTo()).thenReturn(getAug22());
        when(mockEmailArgumentsDTO.getCustomerId()).thenReturn(CUSTOMER_ID_1);

        JourneyHistoryScheduledStatementServiceImpl service =
                mock(JourneyHistoryScheduledStatementServiceImpl.class, CALLS_REAL_METHODS);
        service.applicationEventService = mockApplicationEventService;

        service.createEvent(mockEmailArgumentsDTO);

        verify(mockApplicationEventService).create(anyLong(), any(EventName.class), anyString());
        verify(mockEmailArgumentsDTO).getCardNumber();
        verify(mockEmailArgumentsDTO).getRangeFrom();
        verify(mockEmailArgumentsDTO).getRangeTo();
        verify(mockEmailArgumentsDTO).getCustomerId();
    }

    @Test
    public void isPdfPreferredShouldReturnFalseForCsv() {
        JourneyHistoryScheduledStatementServiceImpl service = new JourneyHistoryScheduledStatementServiceImpl();
        assertFalse(service.isPdfPreferred(JourneyHistoryOutput.CSV.code()));
    }

    @Test
    public void isPdfPreferredShouldReturnTrueForPdf() {
        JourneyHistoryScheduledStatementServiceImpl service = new JourneyHistoryScheduledStatementServiceImpl();
        assertTrue(service.isPdfPreferred(JourneyHistoryOutput.PDF.code()));
    }

    @Test
    public void isPdfPreferredShouldReturnTrueForPdfAndCsv() {
        JourneyHistoryScheduledStatementServiceImpl service = new JourneyHistoryScheduledStatementServiceImpl();
        assertTrue(service.isPdfPreferred(JourneyHistoryOutput.CSV_AND_PDF.code()));
    }

    @Test
    public void isCsvPreferredShouldReturnTrueForCsv() {
        JourneyHistoryScheduledStatementServiceImpl service = new JourneyHistoryScheduledStatementServiceImpl();
        assertTrue(service.isCsvPreferred(JourneyHistoryOutput.CSV.code()));
    }

    @Test
    public void isCsvPreferredShouldReturnFalseForPdf() {
        JourneyHistoryScheduledStatementServiceImpl service = new JourneyHistoryScheduledStatementServiceImpl();
        assertFalse(service.isCsvPreferred(JourneyHistoryOutput.PDF.code()));
    }

    @Test
    public void isCsvPreferredShouldReturnTrueForPdfAndCsv() {
        JourneyHistoryScheduledStatementServiceImpl service = new JourneyHistoryScheduledStatementServiceImpl();
        assertTrue(service.isCsvPreferred(JourneyHistoryOutput.CSV_AND_PDF.code()));
    }

    @Test
    public void getJourneyHistoryPdfIfPreferredShouldReturnPdf() {
        JourneyHistoryService mockJourneyHistoryService = mock(JourneyHistoryService.class);
        when(mockJourneyHistoryService.getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class))).thenReturn("dummy-pdf".getBytes());

        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        when(service.getJourneyHistoryPdfIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class)))
                .thenCallRealMethod();
        when(service.isPdfPreferred(anyString())).thenReturn(true);
        service.journeyHistoryService = mockJourneyHistoryService;

        byte[] result = service.getJourneyHistoryPdfIfPreferred(getTestCardPreferencesDTO1(), getTestJourneyHistoryDTO1());

        assertNotNull(result);
        verify(mockJourneyHistoryService).getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class));
        verify(service).isPdfPreferred(anyString());
    }

    @Test
    public void getJourneyHistoryPdfIfPreferredShouldReturnNull() {
        JourneyHistoryService mockJourneyHistoryService = mock(JourneyHistoryService.class);
        when(mockJourneyHistoryService.getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class))).thenReturn("dummy-pdf".getBytes());

        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        when(service.getJourneyHistoryPdfIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class)))
                .thenCallRealMethod();
        when(service.isPdfPreferred(anyString())).thenReturn(false);
        service.journeyHistoryService = mockJourneyHistoryService;

        byte[] result = service.getJourneyHistoryPdfIfPreferred(getTestCardPreferencesDTO1(), getTestJourneyHistoryDTO1());

        assertNull(result);
        verify(mockJourneyHistoryService, never()).getJourneyHistoryAsPdf(any(JourneyHistoryDTO.class));
        verify(service).isPdfPreferred(anyString());
    }

    @Test
    public void getJourneyHistoryCsvIfPreferredShouldReturnCsv() {
        JourneyHistoryService mockJourneyHistoryService = mock(JourneyHistoryService.class);
        when(mockJourneyHistoryService.getJourneyHistoryAsCsv(any(JourneyHistoryDTO.class))).thenReturn(DUMMY_PDF);

        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        when(service.getJourneyHistoryCsvIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class)))
                .thenCallRealMethod();
        when(service.isCsvPreferred(anyString())).thenReturn(true);
        service.journeyHistoryService = mockJourneyHistoryService;

        byte[] result = service.getJourneyHistoryCsvIfPreferred(getTestCardPreferencesDTO1(), getTestJourneyHistoryDTO1());

        assertNotNull(result);
        verify(mockJourneyHistoryService).getJourneyHistoryAsCsv(any(JourneyHistoryDTO.class));
        verify(service).isCsvPreferred(anyString());
    }

    @Test
    public void getJourneyHistoryCsvIfPreferredShouldReturnNull() {
        JourneyHistoryService mockJourneyHistoryService = mock(JourneyHistoryService.class);
        when(mockJourneyHistoryService.getJourneyHistoryAsCsv(any(JourneyHistoryDTO.class))).thenReturn(DUMMY_CSV);

        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        when(service.getJourneyHistoryCsvIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class)))
                .thenCallRealMethod();
        when(service.isCsvPreferred(anyString())).thenReturn(false);
        service.journeyHistoryService = mockJourneyHistoryService;

        byte[] result = service.getJourneyHistoryCsvIfPreferred(getTestCardPreferencesDTO1(), getTestJourneyHistoryDTO1());

        assertNull(result);
        verify(mockJourneyHistoryService, never()).getJourneyHistoryAsCsv(any(JourneyHistoryDTO.class));
        verify(service).isCsvPreferred(anyString());
    }

    @Ignore
    @Test
    public void shouldGetEmailArguments() {
        CardPreferencesDataService mockCardPreferencesDataService = mock(CardPreferencesDataService.class);
        JourneyHistoryService mockJourneyHistoryService = mock(JourneyHistoryService.class);
        CardDataService mockCardDataService = mock(CardDataService.class);
        CustomerDataServiceImpl mockCustomerDataService = mock(CustomerDataServiceImpl.class);
        SystemParameterService mockSystemParameterService = mock(SystemParameterService.class);
        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        CardDTO mockCardDTO = mock(CardDTO.class);
        CustomerDTO mockTestCustomer= mock(CustomerDTO.class);
        
        service.cardPreferencesDataService = mockCardPreferencesDataService;
        service.journeyHistoryService = mockJourneyHistoryService;
        service.cardDataService = mockCardDataService;
        service.systemParameterService = mockSystemParameterService;
        setField(service, "customerDataService", mockCustomerDataService);
        
        when(mockCardDataService.findById(CARD_ID_1)).thenReturn(mockCardDTO);
        doReturn(getTestCustomerDTO1()).when(mockCustomerDataService).findById(anyLong());
        doReturn(5L).when(mockCardDTO).getWebaccountId();
        doReturn(5L).when(mockCardDTO).getCustomerId();
        
        when(mockCustomerDataService.findById(5L)).thenReturn(mockTestCustomer);
        
        when(mockCardPreferencesDataService.findByCardId(anyLong())).thenReturn(getTestCardPreferencesDTO1());

        when(mockJourneyHistoryService.getJourneyHistory(anyLong(), any(Date.class), any(Date.class)))
                .thenReturn(getTestJourneyHistoryDTO1());



        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(BASE_URI_STRING_1);

        when(service.getEmailArguments(anyLong(), any(Date.class), any(Date.class))).thenCallRealMethod();
        when(service.getJourneyHistoryCsvIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class)))
                .thenReturn(DUMMY_CSV);
        when(service.getJourneyHistoryPdfIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class)))
                .thenReturn(DUMMY_PDF);
        when(service.getSalutation(any(CustomerDTO.class))).thenReturn(TEST_SALUTATION);

        EmailArgumentsDTO result = service.getEmailArguments(CARD_ID_1, getAug19(), getAug22());

        assertEquals(getTestWebAccountDTO1().getEmailAddress(), result.getToAddress());
        assertEquals(TEST_SALUTATION, result.getSalutation());
        assertEquals(BASE_URI_1, result.getBaseUri());
        assertEquals(getTestCardDTO1().getCardNumber(), result.getCardNumber());
        assertEquals(getAug19(), result.getRangeFrom());
        assertEquals(getAug22(), result.getRangeTo());
        assertArrayEquals(DUMMY_CSV, result.getJourneyHistoryCsv());
        assertArrayEquals(DUMMY_PDF, result.getJourneyHistoryPdf());

        verify(mockCardPreferencesDataService).findByCardId(anyLong());
        verify(mockJourneyHistoryService).getJourneyHistory(anyLong(), any(Date.class), any(Date.class));
        verify(mockCardDataService).findById(anyLong());
        verify(mockCustomerDataService).findById(anyLong());
        verify(mockSystemParameterService).getParameterValue(anyString());
        verify(service).getJourneyHistoryCsvIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class));
        verify(service).getJourneyHistoryPdfIfPreferred(any(CardPreferencesDTO.class), any(JourneyHistoryDTO.class));
        verify(service).getSalutation(any(CustomerDTO.class));
    }

    @Test
    public void shouldGetCardsForStatementFrequency() {
        CardPreferencesDataService mockCardPreferencesDataService = mock(CardPreferencesDataService.class);
        when(mockCardPreferencesDataService.findByEmailPreference(anyString())).thenReturn(getTestCardPreferencesDTOList1());

        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        when(service.getCardsForStatementFrequency(anyString())).thenCallRealMethod();
        service.cardPreferencesDataService = mockCardPreferencesDataService;

        service.getCardsForStatementFrequency(JourneyHistoryFrequency.WEEKLY.code());

        verify(mockCardPreferencesDataService).findByEmailPreference(anyString());
    }

    @Test
    public void shouldSendMonthlyStatement() {
        JourneyHistoryStatementEmailService mockJourneyHistoryStatementEmailService =
                mock(JourneyHistoryStatementEmailService.class);
        doNothing().when(mockJourneyHistoryStatementEmailService).sendMonthlyMessage(any(EmailArgumentsDTO.class));

        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        doCallRealMethod().when(service).sendMonthlyStatement(anyLong(), any(Date.class), any(Date.class));
        when(service.getEmailArguments(anyLong(), any(Date.class), any(Date.class))).thenReturn(new EmailArgumentsDTO());
        service.journeyHistoryStatementEmailService = mockJourneyHistoryStatementEmailService;

        service.sendMonthlyStatement(CARD_ID_1, getAug19(), getAug22());

        verify(mockJourneyHistoryStatementEmailService).sendMonthlyMessage(any(EmailArgumentsDTO.class));
        verify(service).getEmailArguments(anyLong(), any(Date.class), any(Date.class));
    }

    @Test
    public void shouldSendWeeklyStatement() {
        JourneyHistoryStatementEmailService mockJourneyHistoryStatementEmailService =
                mock(JourneyHistoryStatementEmailService.class);
        doNothing().when(mockJourneyHistoryStatementEmailService).sendWeeklyMessage(any(EmailArgumentsDTO.class));

        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        doCallRealMethod().when(service).sendWeeklyStatement(anyLong(), any(Date.class), any(Date.class));
        when(service.getEmailArguments(anyLong(), any(Date.class), any(Date.class))).thenReturn(new EmailArgumentsDTO());
        service.journeyHistoryStatementEmailService = mockJourneyHistoryStatementEmailService;

        service.sendWeeklyStatement(CARD_ID_1, getAug19(), getAug22());

        verify(mockJourneyHistoryStatementEmailService).sendWeeklyMessage(any(EmailArgumentsDTO.class));
        verify(service).getEmailArguments(anyLong(), any(Date.class), any(Date.class));
    }

    @Test
    public void shouldGetCardsForMonthlyStatement() {
        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        when(service.getCardsForMonthlyStatement()).thenCallRealMethod();
        when(service.getCardsForStatementFrequency(anyString())).thenReturn(getTestCardPreferencesDTOList1());

        service.getCardsForMonthlyStatement();

        verify(service).getCardsForStatementFrequency(anyString());
    }

    @Test
    public void shouldGetCardsForWeeklyStatement() {
        JourneyHistoryScheduledStatementServiceImpl service = mock(JourneyHistoryScheduledStatementServiceImpl.class);
        when(service.getCardsForWeeklyStatement()).thenCallRealMethod();
        when(service.getCardsForStatementFrequency(anyString())).thenReturn(getTestCardPreferencesDTOList1());

        service.getCardsForWeeklyStatement();

        verify(service).getCardsForStatementFrequency(anyString());
    }
}
