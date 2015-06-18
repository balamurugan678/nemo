package com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification;

import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.IncompleteJourneysNotificationsList;

public interface IncompleteJourneyNotificationsConverter {
	IncompleteJourneyNotificationDTO convertModelToDto(IncompleteJourneysNotificationsList model);

}
