package com.novacroft.nemo.test_support;

import java.util.ArrayList;
import java.util.List;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.novacroft.nemo.tfl.common.transfer.EmailMessageDTO;
import com.novacroft.nemo.tfl.common.transfer.PhoneTextMessageDTO;

public final class MessageTestUtil {

    public static List<PhoneTextMessageDTO> getPhoneTextMessageList() {
        List<PhoneTextMessageDTO> phoneTextMessageList = new ArrayList<PhoneTextMessageDTO>();
        phoneTextMessageList.add(getPhoneTextMessageDTO());
        return phoneTextMessageList;
    }

    public static PhoneTextMessageDTO getPhoneTextMessageDTO() {
        PhoneTextMessageDTO phoneTextMessageDTO = new PhoneTextMessageDTO();
        phoneTextMessageDTO.setMobileNumber(new Long("0123456789"));
        phoneTextMessageDTO.setBody("test");
        return phoneTextMessageDTO;
    }

    public static MimeMessage getMimeMessage() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        return mailSender.createMimeMessage();
    }

    public static MimeMessage getMimeMessageWithContent() throws MessagingException {
        MimeMessage mimeMessage = getMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setText("test text");
        return mimeMessage;
    }

    public static List<EmailMessageDTO> getEmailMessageList() {
        List<EmailMessageDTO> emailMessageList = new ArrayList<EmailMessageDTO>();
        emailMessageList.add(getEmailMessageDTO());
        emailMessageList.add(getEmailMessageDTO2());
        return emailMessageList;
    }

    public static EmailMessageDTO getEmailMessageDTO() {
        EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
        emailMessageDTO.setTo("to@test.com");
        emailMessageDTO.setCc("cc@test.com");
        emailMessageDTO.setBcc("bcc@test.com");
        emailMessageDTO.setSubject("test subject");
        emailMessageDTO.setBody("test body");
        return emailMessageDTO;
    }

    public static EmailMessageDTO getEmailMessageDTO2() {
        EmailMessageDTO emailMessageDTO = new EmailMessageDTO();
        emailMessageDTO.setTo("to@test.com");
        emailMessageDTO.setSubject("test subject");
        emailMessageDTO.setBody("test body");
        return emailMessageDTO;
    }

    private MessageTestUtil() {
    }
}
