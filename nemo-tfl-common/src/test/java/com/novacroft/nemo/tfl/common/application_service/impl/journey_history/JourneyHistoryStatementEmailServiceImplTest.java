package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.transfer.EmailAttachmentDTO;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import javax.mail.internet.MimeMessage;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.novacroft.nemo.common.constant.EmailConstant.*;
import static com.novacroft.nemo.test_support.CardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22;
import static com.novacroft.nemo.test_support.FileTestUtil.DUMMY_RESOURCE_NAME;
import static com.novacroft.nemo.test_support.JourneyTestUtil.DUMMY_CSV;
import static com.novacroft.nemo.test_support.JourneyTestUtil.DUMMY_PDF;
import static com.novacroft.nemo.test_support.UriTestUtil.BASE_URI_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.ReflectionTestUtils.setField;

/**
 * JourneyHistoryStatementEmailService unit tests
 */

public class JourneyHistoryStatementEmailServiceImplTest {
    protected static final String DUMMY_ARGUMENT = "dummy-argument";
    protected static final String DUMMY_CONTENT = "dummy-content";

    @Test
    public void shouldGetAttachments() {
        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class);
        when(mockArguments.getJourneyHistoryCsv()).thenReturn(DUMMY_CSV);
        when(mockArguments.getJourneyHistoryPdf()).thenReturn(DUMMY_PDF);

        JourneyHistoryStatementEmailServiceImpl service = mock(JourneyHistoryStatementEmailServiceImpl.class);
        when(service.getAttachments(any(EmailArgumentsDTO.class))).thenCallRealMethod();

        List<EmailAttachmentDTO> result = service.getAttachments(mockArguments);
        assertEquals(2, result.size());
        verify(mockArguments, times(2)).getJourneyHistoryCsv();
        verify(mockArguments, times(2)).getJourneyHistoryPdf();
    }

    @Test
    public void shouldNotAddNullAttachmentsToMessage() {
        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class);
        when(mockArguments.getJourneyHistoryCsv()).thenReturn(null);
        when(mockArguments.getJourneyHistoryPdf()).thenReturn(null);

        JourneyHistoryStatementEmailServiceImpl service = mock(JourneyHistoryStatementEmailServiceImpl.class);
        when(service.getAttachments(any(EmailArgumentsDTO.class))).thenCallRealMethod();

        List<EmailAttachmentDTO> result = service.getAttachments(mockArguments);
        assertTrue(result.isEmpty());
        verify(mockArguments, times(1)).getJourneyHistoryCsv();
        verify(mockArguments, times(1)).getJourneyHistoryPdf();
    }

    @Test
    public void shouldPrepareModel() {
        SystemParameterService mockSystemParameterService = mock(SystemParameterService.class);
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn("");

        ApplicationContext mockApplicationContext = mock(ApplicationContext.class);
        when(mockApplicationContext.getResource(anyString())).thenReturn(new ClassPathResource(DUMMY_RESOURCE_NAME));

        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class, CALLS_REAL_METHODS);
        when(mockArguments.getToAddress()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getSalutation()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getBaseUri()).thenReturn(BASE_URI_1);

        JourneyHistoryStatementEmailServiceImpl service =
                mock(JourneyHistoryStatementEmailServiceImpl.class, CALLS_REAL_METHODS);
        service.applicationContext = mockApplicationContext;
        service.systemParameterService = mockSystemParameterService;

        Map<String, String> result = service.prepareModel(mockArguments);

        assertTrue(result.containsKey(TO_ADDRESS_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(SALUTATION_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(BASE_IMAGE_URL_MODEL_ATTRIBUTE));
    }

    @Test
    public void shouldPrepareModelForMonthlyStatement() {
        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class, CALLS_REAL_METHODS);
        when(mockArguments.getToAddress()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getSalutation()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getBaseUri()).thenReturn(BASE_URI_1);
        when(mockArguments.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockArguments.getRangeFrom()).thenReturn(getAug19());
        when(mockArguments.getRangeTo()).thenReturn(getAug22());

        JourneyHistoryStatementEmailServiceImpl service = mock(JourneyHistoryStatementEmailServiceImpl.class);
        when(service.prepareModelForMonthlyStatement(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(DUMMY_CONTENT);
        Map<String, String> result = service.prepareModelForMonthlyStatement(mockArguments);

        assertTrue(result.containsKey(SUBJECT_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(BODY_TEXT_MODEL_ATTRIBUTE));
    }

    @Test
    public void shouldPrepareModelForWeeklyStatement() {
        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class, CALLS_REAL_METHODS);
        when(mockArguments.getToAddress()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getSalutation()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getBaseUri()).thenReturn(BASE_URI_1);
        when(mockArguments.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockArguments.getRangeFrom()).thenReturn(getAug19());
        when(mockArguments.getRangeTo()).thenReturn(getAug22());

        JourneyHistoryStatementEmailServiceImpl service = mock(JourneyHistoryStatementEmailServiceImpl.class);
        when(service.prepareModelForWeeklyStatement(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(DUMMY_CONTENT);
        Map<String, String> result = service.prepareModelForWeeklyStatement(mockArguments);

        assertTrue(result.containsKey(SUBJECT_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(BODY_TEXT_MODEL_ATTRIBUTE));
    }

    @Test
    public void shouldSendMonthlyMessage() {
        Map<String, String> mockModel = mock(Map.class);

        MimeMessage mockMimeMessage = mock(MimeMessage.class);

        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class);

        EmailService mockEmailService = mock(EmailService.class);
        doNothing().when(mockEmailService).sendMessage(any(MimeMessage.class));

        JourneyHistoryStatementEmailServiceImpl service = mock(JourneyHistoryStatementEmailServiceImpl.class);
        doCallRealMethod().when(service).sendMonthlyMessage(any(EmailArgumentsDTO.class));
        when(service.prepareModelForMonthlyStatement(any(EmailArgumentsDTO.class))).thenReturn(mockModel);
        when(service.prepareMessage(anyMap(), anyString(), anyList())).thenReturn(mockMimeMessage);
        when(service.getAttachments(any(EmailArgumentsDTO.class))).thenReturn(Collections.EMPTY_LIST);
        setField(service, "emailService", mockEmailService);

        service.sendMonthlyMessage(mockArguments);

        verify(service).prepareModelForMonthlyStatement(any(EmailArgumentsDTO.class));
        verify(service).prepareMessage(anyMap(), anyString(), anyList());
        verify(service).getAttachments(any(EmailArgumentsDTO.class));
        verify(mockEmailService).sendMessage(any(MimeMessage.class));
    }

    @Test
    public void shouldSendWeeklyMessage() {
        Map<String, String> mockModel = mock(Map.class);

        MimeMessage mockMimeMessage = mock(MimeMessage.class);

        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class);

        EmailService mockEmailService = mock(EmailService.class);
        doNothing().when(mockEmailService).sendMessage(any(MimeMessage.class));

        JourneyHistoryStatementEmailServiceImpl service = mock(JourneyHistoryStatementEmailServiceImpl.class);
        doCallRealMethod().when(service).sendWeeklyMessage(any(EmailArgumentsDTO.class));
        when(service.prepareModelForWeeklyStatement(any(EmailArgumentsDTO.class))).thenReturn(mockModel);
        when(service.prepareMessage(anyMap(), anyString(), anyList())).thenReturn(mockMimeMessage);
        when(service.getAttachments(any(EmailArgumentsDTO.class))).thenReturn(Collections.EMPTY_LIST);
        setField(service, "emailService", mockEmailService);

        service.sendWeeklyMessage(mockArguments);

        verify(service).prepareModelForWeeklyStatement(any(EmailArgumentsDTO.class));
        verify(service).prepareMessage(anyMap(), anyString(), anyList());
        verify(service).getAttachments(any(EmailArgumentsDTO.class));
        verify(mockEmailService).sendMessage(any(MimeMessage.class));
    }
}
