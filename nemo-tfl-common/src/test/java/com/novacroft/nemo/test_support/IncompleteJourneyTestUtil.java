package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.DateTestUtil.getAug19;

import java.util.Date;

import com.novacroft.nemo.tfl.common.transfer.InCompleteJourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDisplayDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.NotificationStatus;

public class IncompleteJourneyTestUtil {
    
    public static final Integer TEST_LINKED_SEQUENCE_NO = 111;
    public static final Integer TEST_LINKED_ROLLOVER_SEQUENCE_NO = 222;
    public static final Integer TEST_LINKED_TRANSACTION_TYPE = 61;
    public static final Integer TEST_LINKED_DAY_KEY = 3;
    public static final Integer TEST_LINKED_STATION_KAY = 4;
    public static final NotificationStatus TEST_NOTIFICATION_STATUS = NotificationStatus.AUTOFILL_NOT_NOTIFIED;
    
    public static IncompleteJourneyNotificationDTO createIncompleteJourneyNotificationDTO(
                    Boolean eligibleForSSR, Date linkedDayKeyAsDate) {
        IncompleteJourneyNotificationDTO notificationDTO = new IncompleteJourneyNotificationDTO();
        notificationDTO.setEligibleForSSR(eligibleForSSR);
        notificationDTO.setLinkedDayKeyAsDate(linkedDayKeyAsDate);
        return notificationDTO;
    }
    
    public static IncompleteJourneyNotificationDTO getTestIncompleteJourneyNotificationDTO() {
        IncompleteJourneyNotificationDTO notificationDTO = new IncompleteJourneyNotificationDTO();
        notificationDTO.setEligibleForSSR(Boolean.TRUE);
        notificationDTO.setSsrAutoFillNotificationStatus(TEST_NOTIFICATION_STATUS);
        notificationDTO.setLinkedSequenceNo(TEST_LINKED_SEQUENCE_NO);
        notificationDTO.setLinkedRolloverSequenceNo(TEST_LINKED_ROLLOVER_SEQUENCE_NO);
        notificationDTO.setLinkedTransactionType(TEST_LINKED_TRANSACTION_TYPE);
        notificationDTO.setLinkedDayKey(TEST_LINKED_DAY_KEY);
        notificationDTO.setLinkedStationKey(TEST_LINKED_STATION_KAY);
        notificationDTO.setLinkedTransactionDateTime(getAug19());
        return notificationDTO;
    }
    
    public static InCompleteJourneyDTO getTestIncompleteJourneyDTOWithNotificationDTO() {
        InCompleteJourneyDTO incompleteJourneyDTO = new InCompleteJourneyDTO();
        incompleteJourneyDTO.setJourneyNotificationDTO(getTestIncompleteJourneyNotificationDTO());
        incompleteJourneyDTO.setJourneyDisplayDTO(new JourneyDisplayDTO());
        return incompleteJourneyDTO;
    }
}
