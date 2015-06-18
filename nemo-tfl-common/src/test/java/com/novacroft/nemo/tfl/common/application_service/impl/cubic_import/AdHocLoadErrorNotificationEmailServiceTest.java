package com.novacroft.nemo.tfl.common.application_service.impl.cubic_import;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import java.util.HashMap;
import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.FreemarkerTemplateService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

import freemarker.template.Template;

public class AdHocLoadErrorNotificationEmailServiceTest {

    private AdHocLoadErrorNotificationEmailServiceImpl mockService;
    private SystemParameterService mockSystemParameterService;
    private MimeMessage mockMimeMessage;
    private EmailService mockEmailService;
    private ApplicationContext mockApplicationContext;
    private FreemarkerTemplateService mockFreemarkerTemplateService;
    private Template mockTemplate;
    private String TEST_URL="http://localhost/mailserver";

    @Before
    public void setUp() {
        this.mockSystemParameterService = mock(SystemParameterService.class);
        this.mockService = mock(AdHocLoadErrorNotificationEmailServiceImpl.class);
        this.mockMimeMessage = mock(MimeMessage.class);
        this.mockEmailService = mock(EmailService.class);
        this.mockApplicationContext = mock(ApplicationContext.class);
        this.mockFreemarkerTemplateService = mock(FreemarkerTemplateService.class);
        this.mockTemplate = mock(Template.class);
        this.mockService.systemParameterService = this.mockSystemParameterService;
        setField(this.mockService, "emailService", this.mockEmailService);
        setField(this.mockService, "applicationContext", this.mockApplicationContext);
        setField(this.mockService, "freemarkerTemplateService", this.mockFreemarkerTemplateService);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldSendMessage() {
        doCallRealMethod().when(this.mockService).sendMessage(any(EmailArgumentsDTO.class));
        when(this.mockService.prepareModel(any(EmailArgumentsDTO.class))).thenReturn(new HashMap<String, Object>());
        when(this.mockService.prepareMessage(any(Map.class) , anyString())).thenReturn(this.mockMimeMessage);
        when(this.mockFreemarkerTemplateService.getTemplate(anyString())).thenReturn(this.mockTemplate);
        
        this.mockService.sendMessage(new EmailArgumentsDTO());
        
        verify(this.mockEmailService, atLeastOnce()).sendMessage(any(MimeMessage.class));
    }
    
    @Test
    public void shouldPrepareModel() {
        when(this.mockService.prepareModel(any(EmailArgumentsDTO.class))).thenCallRealMethod();
        when(this.mockSystemParameterService.getParameterValue(anyString())).thenReturn(TEST_URL);   
        Map<String, Object> model = this.mockService.prepareModel(new EmailArgumentsDTO());
        assertNotNull(model);
    }

   }
