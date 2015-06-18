package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.EmailConstant.BASE_IMAGE_URL_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.SALUTATION_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.TO_ADDRESS_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;
import static com.novacroft.nemo.test_support.DateTestUtil.getAug22;
import static com.novacroft.nemo.test_support.UriTestUtil.BASE_URI_1;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

public class TransferProductsConfirmationEmailServiceImplTest {

    protected static final String DUMMY_ARGUMENT = "dummy-argument";
    protected static final String DUMMY_CONTENT = "dummy-content";
    private TransferProductsConfirmationEmailServiceImpl service;
    private SystemParameterService mockSystemParameterService;
    private EmailArgumentsDTO mockArguments;

    @Before
    public void setUp() throws Exception {
        
        mockSystemParameterService = mock(SystemParameterService.class);
        
        service = mock(TransferProductsConfirmationEmailServiceImpl.class);
        service.systemParameterService = mockSystemParameterService;
        mockArguments = mock(EmailArgumentsDTO.class);                
    }
    
    @Test
    public void shouldPrepareModel() {
        
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn("");

        when(mockArguments.getToAddress()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getSalutation()).thenReturn(DUMMY_ARGUMENT);
        when(mockArguments.getBaseUri()).thenReturn(BASE_URI_1);
        when(mockArguments.getCardNumber()).thenReturn(OYSTER_NUMBER_1);
        when(mockArguments.getRangeFrom()).thenReturn(getAug19());
        when(mockArguments.getRangeTo()).thenReturn(getAug22());
        when(mockArguments.getReferenceNumber()).thenReturn(1L);
        when(service.getContent(anyString())).thenReturn(DUMMY_CONTENT);
        when(service.prepareModel(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        Map<String, String> result = service.prepareModel(mockArguments);
        assertTrue(result.containsKey(TO_ADDRESS_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(SALUTATION_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(BASE_IMAGE_URL_MODEL_ATTRIBUTE));
    }

    @Test
    public void shouldSendConfirmationMessage() {
        Map<String, String> mockModel = mock(Map.class);
        MimeMessage mockMimeMessage = mock(MimeMessage.class);
        EmailService mockEmailService = mock(EmailService.class);
        doNothing().when(mockEmailService).sendMessage(any(MimeMessage.class));
        doCallRealMethod().when(service).sendConfirmationMessage(any(EmailArgumentsDTO.class));
        when(service.prepareModel((any(EmailArgumentsDTO.class)))).thenReturn(mockModel);
        when(service.prepareMessage(anyMap(), anyString(), anyList())).thenReturn(mockMimeMessage);
        setField(service, "emailService", mockEmailService);
        service.sendConfirmationMessage(mockArguments);
        verify(service).prepareModel(any(EmailArgumentsDTO.class));
        verify(mockEmailService).sendMessage(any(MimeMessage.class));
    }

}
