package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

/**
 * send email message service specification
 */
public interface EmailMessageService {
    void sendMessage(EmailArgumentsDTO arguments);
}
