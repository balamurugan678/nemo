package com.novacroft.nemo.tfl.common.application_service.journey_history;

import com.novacroft.nemo.tfl.common.transfer.EmailArgumentsDTO;

/**
 * Journey history statement email service
 */
public interface JourneyHistoryStatementEmailService {
    void sendWeeklyMessage(EmailArgumentsDTO arguments);

    void sendMonthlyMessage(EmailArgumentsDTO arguments);
}
