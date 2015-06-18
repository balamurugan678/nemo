package com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification;

import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;

import java.util.ArrayList;
import java.util.List;

public class IncompleteJourneyNotificationResponseDTO {

    protected List<IncompleteJourneyNotificationDTO> inCompleteJourneyNotificationList = new ArrayList<>();

    public List<IncompleteJourneyNotificationDTO> getInCompleteJourneyNotificationList() {
        return inCompleteJourneyNotificationList;
    }

    public void setInCompleteJourneyNotificationList(List<IncompleteJourneyNotificationDTO> inCompleteJourneyNotificationList) {
        this.inCompleteJourneyNotificationList = inCompleteJourneyNotificationList;
    }
}
