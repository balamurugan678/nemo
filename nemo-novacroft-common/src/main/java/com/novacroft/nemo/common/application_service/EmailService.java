package com.novacroft.nemo.common.application_service;

import javax.mail.internet.MimeMessage;

/**
 * Email service specification
 */
public interface EmailService {
    void sendMessage(MimeMessage mimeMessage);

    MimeMessage getMimeMailMessage();
}
