package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.FreemarkerTemplateService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.junit.Test;

import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

import static org.mockito.Mockito.*;

/**
 * BaseEmailService unit tests
 */
public class BaseEmailServiceTest {
    protected static final String TEST_ERROR_MESSAGE = "Test Error";
    protected static final String TEST_TEMPLATE = "test-template";
    protected static final String TEST_MODEL_PROPERTY_VALUE = "test-property-value";

    @Test
    public void shouldPrepareMessage() throws IOException, TemplateException {

        Map<String, Object> mockModel = mock(Map.class);
        when(mockModel.get(anyString())).thenReturn(TEST_MODEL_PROPERTY_VALUE);

        MimeMessage mockMimeMessage = mock(MimeMessage.class);

        EmailService mockEmailService = mock(EmailService.class);
        when(mockEmailService.getMimeMailMessage()).thenReturn(mockMimeMessage);

        Template mockTemplate = mock(Template.class);
        doNothing().when(mockTemplate).process(any(Object.class), any(Writer.class));

        FreemarkerTemplateService mockFreemarkerTemplateService = mock(FreemarkerTemplateService.class);
        when(mockFreemarkerTemplateService.getTemplate(anyString())).thenReturn(mockTemplate);

        BaseEmailService service = mock(BaseEmailService.class);
        when(service.prepareMessage(anyMap(), anyString())).thenCallRealMethod();
        when(service.prepareMessage(anyMap(), anyString(), anyList())).thenCallRealMethod();
        service.emailService = mockEmailService;
        service.freemarkerTemplateService = mockFreemarkerTemplateService;

        service.prepareMessage(mockModel, TEST_TEMPLATE);

        verify(mockEmailService).getMimeMailMessage();
        verify(mockFreemarkerTemplateService).getTemplate(anyString());
        verify(mockTemplate).process(any(Object.class), any(Writer.class));
        verify(mockModel, atLeastOnce()).get(anyString());
    }

    @Test(expected = ApplicationServiceException.class)
    public void prepareMessageShouldError() throws IOException, TemplateException {

        Map<String, Object> mockModel = mock(Map.class);
        when(mockModel.get(anyString())).thenReturn(TEST_MODEL_PROPERTY_VALUE);

        MimeMessage mockMimeMessage = mock(MimeMessage.class);

        EmailService mockEmailService = mock(EmailService.class);
        when(mockEmailService.getMimeMailMessage()).thenReturn(mockMimeMessage);

        TemplateException templateException = new TemplateException(TEST_ERROR_MESSAGE, null);
        Template mockTemplate = mock(Template.class);
        doThrow(templateException).when(mockTemplate).process(any(Object.class), any(Writer.class));

        FreemarkerTemplateService mockFreemarkerTemplateService = mock(FreemarkerTemplateService.class);
        when(mockFreemarkerTemplateService.getTemplate(anyString())).thenReturn(mockTemplate);

        BaseEmailService service = mock(BaseEmailService.class);
        when(service.prepareMessage(anyMap(), anyString())).thenCallRealMethod();
        when(service.prepareMessage(anyMap(), anyString(), anyList())).thenCallRealMethod();
        service.emailService = mockEmailService;
        service.freemarkerTemplateService = mockFreemarkerTemplateService;

        service.prepareMessage(mockModel, TEST_TEMPLATE);
    }

    @Test
    public void noop() {
    }
}
