package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.LocaleConstant.UTF_8_FORMAT;
import static com.novacroft.nemo.tfl.common.constant.SystemParameterCode.MAX_RETRIES_FOR_SENDING_MESSAGES;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.application_service.EmailService;
import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.MessageService;
import com.novacroft.nemo.tfl.common.constant.MessageStatus;
import com.novacroft.nemo.tfl.common.data_service.EmailMessageDataService;
import com.novacroft.nemo.tfl.common.data_service.MessageDataService;
import com.novacroft.nemo.tfl.common.data_service.MessageEventDataService;
import com.novacroft.nemo.tfl.common.data_service.PhoneTextMessageDataService;
import com.novacroft.nemo.tfl.common.transfer.EmailMessageDTO;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import com.novacroft.nemo.tfl.common.transfer.MessageDTO;
import com.novacroft.nemo.tfl.common.transfer.MessageEventDTO;
import com.novacroft.nemo.tfl.common.transfer.PhoneTextMessageDTO;

@Service(value = "messageService")
public class MessageServiceImpl implements MessageService {
    private static final Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
    private static final String SUCCESS_RESPONSE = "Success";

    @Autowired
    protected EmailMessageDataService emailMessageDataService;

    @Autowired
    protected PhoneTextMessageDataService phoneTextMessageDataService;

    @Autowired
    protected MessageEventDataService messageEventDataService;

    @Autowired
    protected MessageDataService messageDataService;

    @Autowired
    protected EmailService emailService;

    @Autowired
    protected SystemParameterService systemParameterService;

    @Transactional
    protected String sendEmailMessage(EmailMessageDTO emailMessageDTO) {
        MimeMessage message = emailService.getMimeMailMessage();
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true, UTF_8_FORMAT);
            helper.addTo(emailMessageDTO.getTo());
            if (null != emailMessageDTO.getCc()) {
                helper.addCc(emailMessageDTO.getCc());
            }
            if (null != emailMessageDTO.getBcc()) {
                helper.addBcc(emailMessageDTO.getBcc());
            }
            helper.setSubject(emailMessageDTO.getSubject());
            helper.setText(emailMessageDTO.getBody(), true);
            emailService.sendMessage(message);
        } catch (Exception e) {
            return e.getLocalizedMessage();
        }
        return SUCCESS_RESPONSE;
    }

    @Transactional
    protected String sendPhoneTextMessage(MessageDTO messageDTO) {

        // TODO Implement sending SMS.

        return SUCCESS_RESPONSE;
    }

    @Transactional
    protected void processEmailMessages(List<EmailMessageDTO> emailMessagesToBeSent) {
        for (EmailMessageDTO emailMessageDTO : emailMessagesToBeSent) {
            String response = sendEmailMessage(emailMessageDTO);
            messageEventDataService.createOrUpdate(new MessageEventDTO(emailMessageDTO.getId(), response, new Date()));
            emailMessageDTO.setAttempts(emailMessageDTO.getAttempts() + 1);
            emailMessageDTO = (EmailMessageDTO) updateStatus(emailMessageDTO, response);
            messageDataService.createOrUpdate(emailMessageDTO);
        }

    }

    protected MessageDTO updateStatus(MessageDTO messageDTO, String response) {
        if (SUCCESS_RESPONSE.equalsIgnoreCase(response)) {
            messageDTO.setStatus(MessageStatus.SENT.code());
        } else if (messageDTO.getAttempts() >= systemParameterService.getIntegerParameterValue(MAX_RETRIES_FOR_SENDING_MESSAGES.code())) {
            messageDTO.setStatus(MessageStatus.ABORTED.code());
        } else {
            messageDTO.setStatus(MessageStatus.RETRY.code());
        }
        return messageDTO;
    }

    @Transactional
    protected void processPhoneTextMessages(List<PhoneTextMessageDTO> textMessagesToBeSent) {
        for (PhoneTextMessageDTO phoneTextMessageDTO : textMessagesToBeSent) {
            String response = sendPhoneTextMessage(phoneTextMessageDTO);
            messageEventDataService.createOrUpdate(new MessageEventDTO(phoneTextMessageDTO.getId(), response, new Date()));
            phoneTextMessageDTO.setAttempts(phoneTextMessageDTO.getAttempts() + 1);
            phoneTextMessageDTO = (PhoneTextMessageDTO) updateStatus(phoneTextMessageDTO, response);
            messageDataService.createOrUpdate(phoneTextMessageDTO);
        }
    }

    @Override
    public void processMessages(JobLogDTO log) {
        List<EmailMessageDTO> emailMessagesToBeSent = emailMessageDataService.findEmailMessagesToBeSent();
        if (null != emailMessagesToBeSent) {
            processEmailMessages(emailMessagesToBeSent);
        }

        List<PhoneTextMessageDTO> textMessagesToBeSent = phoneTextMessageDataService.findPhoneTextMessagesToBeSent();
        if (null != textMessagesToBeSent) {
            processPhoneTextMessages(textMessagesToBeSent);
        }
    }

    @Override
    public void addEmailMessageToQueueToBeSent(MimeMessage emailMessage, Long customerId, Long orderId) throws MessagingException, IOException {
        EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
        try {
            emailMessageDTO.setTo(InternetAddress.toString(emailMessage.getRecipients(Message.RecipientType.TO)));
            emailMessageDTO.setCc(InternetAddress.toString(emailMessage.getRecipients(Message.RecipientType.CC)));
            emailMessageDTO.setBcc(InternetAddress.toString(emailMessage.getRecipients(Message.RecipientType.BCC)));
            emailMessageDTO.setFrom(InternetAddress.toString(emailMessage.getFrom()));
            emailMessageDTO.setAttempts(0);
            emailMessageDTO.setCustomerId(customerId);
            emailMessageDTO.setOrderId(orderId);
            emailMessageDTO.setSubject(emailMessage.getSubject());
            emailMessageDTO.setStatus(MessageStatus.REQUESTED.code());
            emailMessageDTO.setBody(getBody(emailMessage));
            emailMessageDataService.createOrUpdate(emailMessageDTO);
        } catch (MessagingException | IOException e) {
            logger.error(e.getLocalizedMessage());
            throw e;
        }
    }

    protected String getBody(MimeMessage message) throws IOException, MessagingException {
        Object contentObject = message.getContent();
        String result = "";
        if (contentObject instanceof Multipart) {
            MimeMultipart content = (MimeMultipart) contentObject;
            int count = content.getCount();
            if (count == 1) {
                result = getFinalContent(content.getBodyPart(0));
            }
        } else if (contentObject instanceof String) {
            result = (String) contentObject;
        }
        return result;
    }

    protected String getFinalContent(Part part) throws MessagingException, IOException {
        String finalContents = "";
        if (part.getContent() instanceof String) {
            finalContents = (String) part.getContent();
        } else {
            Multipart mp = (Multipart) part.getContent();
            if (mp.getCount() > 0) {
                Part bp = mp.getBodyPart(0);
                try {
                    finalContents = dumpPart(bp);
                } catch (Exception e) {
                    logger.error(e.getLocalizedMessage());
                }
            }
        }
        return finalContents.trim();
    }

    protected String dumpPart(Part part) throws IOException, MessagingException {
        InputStream inputStream = part.getInputStream();
        if (!(inputStream instanceof BufferedInputStream)) {
            inputStream = new BufferedInputStream(inputStream);
        }
        return getStringFromInputStream(inputStream);
    }

    protected String getStringFromInputStream(InputStream is) {
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        String line;
        try {
            reader = new BufferedReader(new InputStreamReader(is));
            while ((line = reader.readLine()) != null) {
                content.append(line);
                content.append("\n");
            }
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage());
        }
        return content.toString();
    }

    @Override
    public void addPhoneTextMessageToQueueToBeSent(Long toMobileNumber, String content, Long customerId, Long orderId) throws MessagingException {
        PhoneTextMessageDTO phoneTextMessageDTO = new PhoneTextMessageDTO();
        phoneTextMessageDTO.setAttempts(0);
        phoneTextMessageDTO.setBody(content);
        phoneTextMessageDTO.setCustomerId(customerId);
        phoneTextMessageDTO.setMobileNumber(toMobileNumber);
        phoneTextMessageDTO.setOrderId(orderId);
        phoneTextMessageDTO.setStatus(MessageStatus.REQUESTED.code());
        phoneTextMessageDataService.createOrUpdate(phoneTextMessageDTO);
    }
}
