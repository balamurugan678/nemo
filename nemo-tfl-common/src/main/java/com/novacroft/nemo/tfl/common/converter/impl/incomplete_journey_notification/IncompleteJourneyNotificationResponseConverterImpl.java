package com.novacroft.nemo.tfl.common.converter.impl.incomplete_journey_notification;

import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseConverter;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationResponseDTO;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.IncompleteJourneyNotificationsConverter;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.GetIncompleteJourneyNotificationsResponse;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.ArrayOfIncompleteJourneysNotificationsList;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.IncompleteJourneysNotificationsList;
import com.novacroft.tfl.web_service.model.incomplete_journey_notification.data.JourneysNotificationsResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component(value = "incompleteJourneyNotificationResponseConverter")
public class IncompleteJourneyNotificationResponseConverterImpl implements IncompleteJourneyNotificationResponseConverter {

    @Autowired
    protected IncompleteJourneyNotificationsConverter incompleteJourneyNotificationsConverter;

    @Override
    public IncompleteJourneyNotificationResponseDTO convertModelToDto(GetIncompleteJourneyNotificationsResponse response) {
        IncompleteJourneyNotificationResponseDTO responseDTO = new IncompleteJourneyNotificationResponseDTO();
        for (IncompleteJourneysNotificationsList incompleteJourney : getIncompleteJourneyList(
                getArrayOfJourney(getResponse(response)))) {
            responseDTO.getInCompleteJourneyNotificationList().add(convertIncompleteJourney(incompleteJourney));
        }
        return responseDTO;
    }

    protected JourneysNotificationsResponse getResponse(GetIncompleteJourneyNotificationsResponse response) {
        return response.getGetIncompleteJourneyNotificationsResult().getValue();
    }

    protected ArrayOfIncompleteJourneysNotificationsList getArrayOfJourney(JourneysNotificationsResponse response) {
        return response.getIncompleteJourneysNotifications().getValue();
    }

    protected List<IncompleteJourneysNotificationsList> getIncompleteJourneyList(
            ArrayOfIncompleteJourneysNotificationsList arrayOfNotificationsList) {
        return arrayOfNotificationsList.getIncompleteJourneysNotificationsList();
    }

    protected IncompleteJourneyNotificationDTO convertIncompleteJourney(IncompleteJourneysNotificationsList journey) {
        return this.incompleteJourneyNotificationsConverter.convertModelToDto(journey);
    }

}
