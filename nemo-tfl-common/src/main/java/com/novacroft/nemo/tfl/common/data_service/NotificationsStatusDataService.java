package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseDTO;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;

public interface NotificationsStatusDataService {

    IncompleteJourneyNotificationResponseDTO findByCardNumberForDaysRangeOnline(String cardNumber, int days);

    NotifyAutoFillOfSSRStatusResponseDTO notifyAutoFillOfJourneySSRStatus(
            IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO, String cardNumber,
            AutoFillSSRNotificationStatus autoFillSSRNotificationStatus);
}
