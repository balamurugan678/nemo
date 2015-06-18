package com.novacroft.nemo.tfl.common.service_access;

import com.novacroft.tfl.web_service.model.incomplete_journey_notification.GetIncompleteJourneyNotifications;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.GetIncompleteJourneyNotificationsResponse;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatus;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponse;


public interface NotificationStatusServiceAccess {

    GetIncompleteJourneyNotificationsResponse getIncompleteJourneyNotifications(GetIncompleteJourneyNotifications request);
    
    NotifyAutoFillOfSSRStatusResponse  notifyAutoFillOfSSRStatus(NotifyAutoFillOfSSRStatus notifyAutoFillOfSSRStatus);
	
}
