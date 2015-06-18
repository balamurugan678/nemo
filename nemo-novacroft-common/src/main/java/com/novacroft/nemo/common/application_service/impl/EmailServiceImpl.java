package com.novacroft.nemo.common.application_service.impl;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.constant.CommonPrivateError;
import com.novacroft.nemo.common.constant.CommonSystemParameterCode;
import com.novacroft.nemo.common.data_service.EmailsDataService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;

/**
 * <p>
 * Email service implementation
 * </p>
 * <p>
 * This service provides facilities for creating and sending email messages.
 * </p>
 * <p>
 * This service is dependent on:
 * </p>
 * <ul>
 * <li><code>mailSender</code> : a mail sender bean should be configured in the application content</li>
 * <li><code>systemParameterService</code> : the "from" email address is system parameter lookup</li>
 * </ul>
 */
@Service(value = "emailService")
public class EmailServiceImpl implements EmailService {
    protected static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    protected JavaMailSender mailSender;
    @Autowired
    protected SystemParameterService systemParameterService;
    @Autowired
    protected EmailsDataService emailsDataService;

    @Override
    public void sendMessage(MimeMessage mimeMessage) throws MailSendException {
        try {
            this.mailSender.send(mimeMessage);
        } catch (MailSendException e) {
            logger.error(e.getLocalizedMessage());
            throw e;
        }
    }

    @Override
    public MimeMessage getMimeMailMessage() {
        try {
            MimeMessage mimeMessage = this.mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(getFromAddress());
            helper.setSentDate(new Date());
            return mimeMessage;
        } catch (MessagingException e) {
            logger.error(e.getMessage(), e);
            throw new ApplicationServiceException(CommonPrivateError.CREATE_MIME_MESSAGE_FAILED.message(), e);
        }
    }

    protected String getFromAddress() {
        String fromAddress = this.systemParameterService.getParameterValue(CommonSystemParameterCode.EMAIL_MESSAGE_FROM_ADDRESS.code());
        assert (fromAddress != null);
        return fromAddress;
    }
}
