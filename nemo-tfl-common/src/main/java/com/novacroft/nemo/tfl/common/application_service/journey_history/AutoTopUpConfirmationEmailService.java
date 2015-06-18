package com.novacroft.nemo.tfl.common.application_service.journey_history;

import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

/**
 * Auto Top Up Confirmation  email service
 */
public interface AutoTopUpConfirmationEmailService {
    void sendConfirmationMessage(EmailArgumentsDTO arguments);
}
