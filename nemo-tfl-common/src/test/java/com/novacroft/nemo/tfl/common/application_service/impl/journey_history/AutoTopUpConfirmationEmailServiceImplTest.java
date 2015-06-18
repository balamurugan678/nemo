package com.novacroft.nemo.tfl.common.application_service.impl.journey_history;

import static com.novacroft.nemo.common.constant.EmailConstant.BASE_IMAGE_URL_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.SALUTATION_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.TO_ADDRESS_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22;
import static com.novacroft.nemo.test_support.FileTestUtil.DUMMY_RESOURCE_NAME;
import static com.novacroft.nemo.test_support.UriTestUtil.BASE_URI_1;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

/**
 * JourneyHistoryStatementEmailService unit tests
 */

public class AutoTopUpConfirmationEmailServiceImplTest {
    protected static final String DUMMY_ARGUMENT = "dummy-argument";
    protected static final String DUMMY_CONTENT = "dummy-content";

 
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
        when(mockArguments.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockArguments.getRangeFrom()).thenReturn(getAug19());
        when(mockArguments.getRangeTo()).thenReturn(getAug22());
        when(mockArguments.getReferenceNumber()).thenReturn(1L);
        when(mockArguments.getReferenceNumber()).thenReturn(1L);
        
        AutoTopUpConfirmationEmailServiceImpl service =   mock(AutoTopUpConfirmationEmailServiceImpl.class);
        service.applicationContext = mockApplicationContext;
        service.systemParameterService = mockSystemParameterService;
       when(service.prepareModel(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(DUMMY_CONTENT);
        Map<String, String> result = service.prepareModel(mockArguments);
        assertTrue(result.containsKey(TO_ADDRESS_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(SALUTATION_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(BASE_IMAGE_URL_MODEL_ATTRIBUTE));
    }

    @Test
    public void shouldSendConfirmationMessage() {
        Map<String, String> mockModel = mock(Map.class);

        MimeMessage mockMimeMessage = mock(MimeMessage.class);

        EmailArgumentsDTO mockArguments = mock(EmailArgumentsDTO.class);

        EmailService mockEmailService = mock(EmailService.class);
        doNothing().when(mockEmailService).sendMessage(any(MimeMessage.class));

        AutoTopUpConfirmationEmailServiceImpl service = mock(AutoTopUpConfirmationEmailServiceImpl.class);
        doCallRealMethod().when(service).sendConfirmationMessage(any(EmailArgumentsDTO.class));
        when(service.prepareModel((any(EmailArgumentsDTO.class)))).thenReturn(mockModel);
        when(service.prepareMessage(anyMap(), anyString(), anyList())).thenReturn(mockMimeMessage);
        setField(service, "emailService", mockEmailService);
        service.sendConfirmationMessage(mockArguments);
        verify(service).prepareModel(any(EmailArgumentsDTO.class));
         verify(mockEmailService).sendMessage(any(MimeMessage.class));
    }

}
