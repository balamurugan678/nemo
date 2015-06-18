package com.novacroft.nemo.common.application_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

public class EmailServiceImplTest {

    EmailServiceImpl service;
    MimeMessage mockMimeMessage;
    JavaMailSender mockMailSender;
    SystemParameterService mockSystemParameterService;

    @Before
    public void setUp() {
        service = mock(EmailServiceImpl.class);
        mockMimeMessage = mock(MimeMessage.class);
        mockMailSender = mock(JavaMailSender.class);
        mockSystemParameterService = mock(SystemParameterService.class);

        service.mailSender = mockMailSender;
        service.systemParameterService = mockSystemParameterService;
    }

    @Test
    public void shouldSendMessage() {
        doCallRealMethod().when(service).sendMessage(any(MimeMessage.class));
        service.sendMessage(mockMimeMessage);
        verify(mockMailSender, atLeastOnce()).send(mockMimeMessage);
    }

    @Test(expected = MailSendException.class)
    public void sendMessageShouldError() {
        doThrow(new MailSendException("Error")).when(service).sendMessage(any(MimeMessage.class));
        service.sendMessage(mockMimeMessage);
    }

    @Test
    public void shouldGetFromAddress() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn("test");
        doCallRealMethod().when(service).getFromAddress();
        String result = service.getFromAddress();
        assert (result.equals("test"));
        verify(mockSystemParameterService, atLeastOnce()).getParameterValue(anyString());
    }

    @Test
    public void shouldGetMimeMailMessage() {
        when(service.getMimeMailMessage()).thenCallRealMethod();
        doReturn("testFrom").when(service).getFromAddress();
        when(mockMailSender.createMimeMessage()).thenReturn(new JavaMailSenderImpl().createMimeMessage());
        MimeMessage message = service.getMimeMailMessage();
        assertNotNull(message);
    }

    @Test(expected = ApplicationServiceException.class)
    public void getMimeMailMessageShouldError() {
        when(service.getMimeMailMessage()).thenCallRealMethod();
        when(mockMailSender.createMimeMessage()).thenThrow(new ApplicationServiceException());
        service.getMimeMailMessage();
    }

}
