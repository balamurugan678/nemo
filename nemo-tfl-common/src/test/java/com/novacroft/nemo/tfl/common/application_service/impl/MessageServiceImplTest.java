package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.MessageTestUtil.getEmailMessageList;
import static com.novacroft.nemo.test_support.MessageTestUtil.getMimeMessage;
import static com.novacroft.nemo.test_support.MessageTestUtil.getMimeMessageWithContent;
import static com.novacroft.nemo.test_support.MessageTestUtil.getPhoneTextMessageList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.application_service.impl.EmailServiceImpl;
import com.novacroft.nemo.common.application_service.impl.SystemParameterServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.MessageDataService;
import com.novacroft.nemo.tfl.common.data_service.MessageEventDataService;
import com.novacroft.nemo.tfl.common.data_service.PhoneTextMessageDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.EmailMessageDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.MessageDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.MessageEventDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.PhoneTextMessageDataServiceImpl;
import com.novacroft.nemo.tfl.common.transfer.EmailMessageDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import com.novacroft.nemo.tfl.common.transfer.MessageEventDTO;
import com.novacroft.nemo.tfl.common.transfer.PhoneTextMessageDTO;

public class MessageServiceImplTest {

    private EmailMessageDataServiceImpl mockEmailMessageDataService;
    private PhoneTextMessageDataService mockPhoneTextMessageDataService;
    private MessageEventDataService mockMessageEventDataService;
    private MessageDataService mockMessageDataService;
    private EmailService mockEmailService;
    private MessageServiceImpl service;
    private SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        this.service = new MessageServiceImpl();
        this.mockEmailMessageDataService = mock(EmailMessageDataServiceImpl.class);
        this.mockEmailService = mock(EmailServiceImpl.class);
        this.mockMessageDataService = mock(MessageDataServiceImpl.class);
        this.mockMessageEventDataService = mock(MessageEventDataServiceImpl.class);
        this.mockPhoneTextMessageDataService = mock(PhoneTextMessageDataServiceImpl.class);
        this.mockSystemParameterService = mock(SystemParameterServiceImpl.class);

        service.emailMessageDataService = mockEmailMessageDataService;
        service.emailService = mockEmailService;
        service.messageDataService = mockMessageDataService;
        service.messageEventDataService = mockMessageEventDataService;
        service.phoneTextMessageDataService = mockPhoneTextMessageDataService;
        service.systemParameterService = mockSystemParameterService;

        when(mockSystemParameterService.getIntegerParameterValue(anyString())).thenReturn(3);

    }

    @Test
    public void testAddEmailMessageToQueueToBeSent() throws MessagingException, IOException {
        MimeMessage emailMessage = getMimeMessageWithContent();
        when(mockEmailMessageDataService.createOrUpdate(any(EmailMessageDTO.class))).thenReturn(null);
        service.addEmailMessageToQueueToBeSent(emailMessage, null, null);
        verify(mockEmailMessageDataService).createOrUpdate(any(EmailMessageDTO.class));
    }

    @Test(expected = MessagingException.class)
    public void testAddEmailMessageToQueueToBeSentShouldErrorWithMessagingException() throws MessagingException, IOException {
        MimeMessage emailMessage = mock(MimeMessage.class);
        when(emailMessage.getFrom()).thenThrow(new MessagingException("error"));
        service.addEmailMessageToQueueToBeSent(emailMessage, null, null);
        verify(mockEmailMessageDataService).createOrUpdate(any(EmailMessageDTO.class));
    }

    @Test(expected = IOException.class)
    public void testAddEmailMessageToQueueToBeSentShouldErrorWithIOException() throws MessagingException, IOException {
        MimeMessage emailMessage = mock(MimeMessage.class);
        when(emailMessage.getContent()).thenThrow(new IOException("error"));
        service.addEmailMessageToQueueToBeSent(emailMessage, null, null);
        verify(mockEmailMessageDataService).createOrUpdate(any(EmailMessageDTO.class));
    }

    @Test
    public void testAddPhoneTextMessageToQueueToBeSent() throws MessagingException, IOException {
        String phoneTextMessageContent = "Test message";
        Long phoneNumber = new Long("0123456789");
        when(mockPhoneTextMessageDataService.createOrUpdate(any(PhoneTextMessageDTO.class))).thenReturn(null);
        service.addPhoneTextMessageToQueueToBeSent(phoneNumber, phoneTextMessageContent, null, null);
        verify(mockPhoneTextMessageDataService).createOrUpdate(any(PhoneTextMessageDTO.class));
    }

    @Test
    public void testProcessMessages() {
        JobLogDTO log = new JobLogDTO();
        when(mockEmailMessageDataService.findEmailMessagesToBeSent()).thenReturn(getEmailMessageList());
        when(mockPhoneTextMessageDataService.findPhoneTextMessagesToBeSent()).thenReturn(getPhoneTextMessageList());
        when(mockMessageEventDataService.createOrUpdate(any(MessageEventDTO.class))).thenReturn(null);
        when(mockMessageDataService.createOrUpdate(any(EmailMessageDTO.class))).thenReturn(null);
        when(mockEmailService.getMimeMailMessage()).thenReturn(getMimeMessage());

        service.processMessages(log);

        verify(mockEmailService, atLeastOnce()).sendMessage(any(MimeMessage.class));

    }

    @Test
    public void testProcessMessagesWithNoMessagesToProcess() {
        JobLogDTO log = new JobLogDTO();
        when(mockEmailMessageDataService.findEmailMessagesToBeSent()).thenReturn(null);
        when(mockPhoneTextMessageDataService.findPhoneTextMessagesToBeSent()).thenReturn(null);
        when(mockMessageEventDataService.createOrUpdate(any(MessageEventDTO.class))).thenReturn(null);
        when(mockMessageDataService.createOrUpdate(any(EmailMessageDTO.class))).thenReturn(null);
        when(mockEmailService.getMimeMailMessage()).thenReturn(getMimeMessage());

        service.processMessages(log);

        verify(mockEmailService, never()).sendMessage(any(MimeMessage.class));

    }

}
