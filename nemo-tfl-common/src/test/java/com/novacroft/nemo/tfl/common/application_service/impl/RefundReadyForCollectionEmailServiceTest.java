package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.test_support.EmailTestUtil;
import com.novacroft.nemo.tfl.common.constant.ContentCode;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import javax.mail.internet.MimeMessage;

import java.util.Map;

import static com.novacroft.nemo.common.constant.EmailConstant.*;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.EmailTestUtil.FORMATTED_PICKUP_EXPIRE_DATE_IN_EMAIL;
import static com.novacroft.nemo.test_support.EmailTestUtil.FORMATTED_REFUND_AMOUNT_IN_EMAIL;
import static com.novacroft.nemo.test_support.EmailTestUtil.TEST_EMAIL_MESSAGE_BODY;
import static com.novacroft.nemo.test_support.RefundTestUtil.PICK_UP_LOCATION_NAME_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.*;

/**
 * RefundReadyForCollectionEmailService unit tests
 */
@SuppressWarnings("unchecked")
public class RefundReadyForCollectionEmailServiceTest {
    private RefundReadyForCollectionEmailServiceImpl service;
    private EmailArgumentsDTO testEmailArgumentsDTO;
    
    @Before
    public void setUp() {
        service = mock(RefundReadyForCollectionEmailServiceImpl.class);
        testEmailArgumentsDTO = EmailTestUtil.getTestRefundEmailArgumentDTO();
    }
    
    @Test
    public void shouldPrepareModel() {
        when(service.prepareModel(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(service.getContent(anyString())).thenReturn(TEST_EMAIL_MESSAGE_BODY);
        Map<String, String> result = service.prepareModel(testEmailArgumentsDTO);
        assertTrue(result.containsKey(TO_ADDRESS_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(SUBJECT_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(SALUTATION_MODEL_ATTRIBUTE));
        assertTrue(result.containsKey(BODY_TEXT_MODEL_ATTRIBUTE));
    }

    @Test
    public void shouldSendMessage() {
        Map<String, String> mockModel = mock(Map.class);
        MimeMessage mockMimeMessage = mock(MimeMessage.class);

        EmailService mockEmailService = mock(EmailService.class);
        doNothing().when(mockEmailService).sendMessage(any(MimeMessage.class));

        RefundReadyForCollectionEmailServiceImpl service = mock(RefundReadyForCollectionEmailServiceImpl.class);
        when(service.prepareModel(any(EmailArgumentsDTO.class))).thenReturn(mockModel);
        when(service.prepareMessage(any(Map.class), anyString())).thenReturn(mockMimeMessage);
        doCallRealMethod().when(service).sendMessage(any(EmailArgumentsDTO.class));
        ReflectionTestUtils.setField(service, "emailService", mockEmailService);

        service.sendMessage(testEmailArgumentsDTO);
        verify(service).prepareModel(any(EmailArgumentsDTO.class));
        verify(service).prepareMessage(any(Map.class), anyString());
        verify(mockEmailService).sendMessage(any(MimeMessage.class));
    }
    
    @Test
    public void shouldGetSubject() {
        when(service.getSubject(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(service.getContent(anyString())).then(returnsFirstArg());
        assertEquals(ContentCode.REFUND_READY_FOR_COLLECTION_EMAIL_SUBJECT.textCode(), 
                        service.getSubject(null));
    }
    
    @Test
    public void shouldGetBody() {
        when(service.getBody(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(service.getContent(anyString(), (String) anyVararg()))
            .then(new Answer<String>() {

                @Override
                public String answer(InvocationOnMock invocation) throws Throwable {
                    return StringUtils.join(invocation.getArguments());
                }
            });
        
        String expectedResult = ContentCode.REFUND_READY_FOR_COLLECTION_EMAIL_BODY.textCode() +
                        FORMATTED_REFUND_AMOUNT_IN_EMAIL + 
                        PICK_UP_LOCATION_NAME_1 + 
                        OYSTER_NUMBER_1 + 
                        FORMATTED_PICKUP_EXPIRE_DATE_IN_EMAIL;
                        
        assertEquals(expectedResult, service.getBody(testEmailArgumentsDTO));
    }
}
