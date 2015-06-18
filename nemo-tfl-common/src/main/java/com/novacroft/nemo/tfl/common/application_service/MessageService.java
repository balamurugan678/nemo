package com.novacroft.nemo.tfl.common.application_service;

import java.io.IOException;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public interface MessageService {

    void processMessages(JobLogDTO log);

    void addPhoneTextMessageToQueueToBeSent(Long toMobileNumber, String content, Long customerId, Long orderId) throws MessagingException;

    void addEmailMessageToQueueToBeSent(MimeMessage emailMessage, Long customerId, Long orderId) throws MessagingException, IOException;

}
