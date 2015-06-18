package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyMonthDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;

import java.util.List;

public interface CollateIncompleteJourneyService {

    List<IncompleteJourneyMonthDTO> collateRefundEligibleJourneysByMonth(
            List<IncompleteJourneyNotificationDTO> notificationsList);

}