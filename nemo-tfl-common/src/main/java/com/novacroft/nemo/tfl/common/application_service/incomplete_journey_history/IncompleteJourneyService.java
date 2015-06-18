package com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history;

import java.util.Date;

import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.converter.incomplete_journey_notification.NotifyAutoFillOfSSRStatusResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyHistoryDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

/**
 * Incomplete Journeys application service specification
 */
public interface IncompleteJourneyService {
    IncompleteJourneyHistoryDTO getIncompleteJourneys(Long cardId);
   
	NotifyAutoFillOfSSRStatusResponseDTO notifyAutofill(Long cardId, Date linkedTransactionDateTime, Integer linkedStationKey, AutoFillSSRNotificationStatus autoFillSSRNotificationStatus);

	IncompleteJourneyHistoryDTO getIncompleteJourneysForInnovator(Long cardId);

	IncompleteJourneyNotificationDTO retrieveEligibleIncompleteJourney(	Long cardId, Date linkedTransactionDateTime, Integer linkedStationKey);

	IncompleteJourneyNotificationDTO getIncompleteJourneyNotification(Long cardId, Date linkedTransactionDateTime,	Integer linkedStationKey);
	
	JourneyDTO getSSREligibleJourneyHistoryItem(Long cardId, Date journeyDate, Integer journeyId);
}
