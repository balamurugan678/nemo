package com.novacroft.nemo.common.application_service.impl;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.FreemarkerTemplateService;
import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.transfer.EmailAttachmentDTO;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.novacroft.nemo.common.constant.EmailConstant.SUBJECT_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.EmailConstant.TO_ADDRESS_MODEL_ATTRIBUTE;
import static com.novacroft.nemo.common.constant.LocaleConstant.UTF_8_FORMAT;

/**
 * Base email service
 */
public abstract class BaseEmailService extends BaseService {
    protected static final Logger logger = LoggerFactory.getLogger(BaseEmailService.class);

    @Autowired
    protected EmailService emailService;
    @Autowired
    protected FreemarkerTemplateService freemarkerTemplateService;

    public MimeMessage prepareMessage(Map<String, Object> model, String template) {
        return prepareMessage(model, template, Collections.<EmailAttachmentDTO>emptyList());
    }

    public MimeMessage prepareMessage(Map<String, Object> model, String template, List<EmailAttachmentDTO> attachments) {
        try {
            MimeMessage message = this.emailService.getMimeMailMessage();

            Template resetPasswordEmailTemplate = this.freemarkerTemplateService.getTemplate(template);
            StringWriter writer = new StringWriter();
            resetPasswordEmailTemplate.process(model, writer);

            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_FORMAT);
            helper.addTo((String) model.get(TO_ADDRESS_MODEL_ATTRIBUTE));
            helper.setSubject((String) model.get(SUBJECT_MODEL_ATTRIBUTE));
            helper.setText(writer.toString(), true);

            for (EmailAttachmentDTO attachment : attachments) {
                helper.addAttachment(attachment.getFileName(), new ByteArrayResource(attachment.getContent()));
            }

            return message;
        } catch (MessagingException | IOException | TemplateException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(CommonPrivateError.CREATE_MIME_MESSAGE_FAILED.message(), e);
        }
    }
}
