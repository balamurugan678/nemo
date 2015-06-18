package com.novacroft.nemo.tfl.common.application_service.impl.fare_aggregation_service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.tfl.common.application_service.fare_aggregation_service.FareAggregationService;
import com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history.IncompleteJourneyService;
import com.novacroft.nemo.tfl.common.application_service.journey_history.JourneyHistoryService;
import com.novacroft.nemo.tfl.common.constant.incomplete_journey.AutoFillSSRNotificationStatus;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.FareAggregationDataService;
import com.novacroft.nemo.tfl.common.data_service.NotificationsStatusDataService;
import com.novacroft.nemo.tfl.common.data_service.OysterJourneyHistoryDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.fare_aggregation_engine.RecalculatedOysterChargeResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.RefundOrchestrationResultDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;

@Service
@Transactional
public class FareAggregationServiceImpl implements FareAggregationService {

	@Autowired
	protected NotificationsStatusDataService notificationStatusDataService;
	
	@Autowired 
	protected CardDataService cardDataService; 
	
	@Autowired
	protected OysterJourneyHistoryDataService oysterJourneyHistoryDataService;
	
	@Autowired
	protected JourneyHistoryService journeyHistoryService;
	
	@Autowired
	protected IncompleteJourneyService incompleteJourneyService;
	
	@Autowired
	protected FareAggregationDataService fareAggregationDataService;
	
	@Override
    public RefundOrchestrationResultDTO orchestrateServicesForRefundSubmission(final Long cardId, final Date linkedTransactionDateTime, final Integer linkedStationKey, Integer missingStationId, String reasonForMissing,  boolean agentSubmitted){
    	AutoFillSSRNotificationStatus notificationStatus = AutoFillSSRNotificationStatus.AUTOFILL_NOTIFIED_COMMENCED;
    	final CardDTO cardDTO = cardDataService.findById(cardId);
		final String cardNumber = cardDTO.getCardNumber();
    	try {
    		final IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO = incompleteJourneyService.retrieveEligibleIncompleteJourney(cardId, linkedTransactionDateTime, linkedStationKey);
    		final Integer refundAmount  =  calculateRefund(cardId, linkedTransactionDateTime, linkedStationKey, missingStationId, reasonForMissing, agentSubmitted);
    		notificationStatusDataService.notifyAutoFillOfJourneySSRStatus( incompleteJourneyNotificationDTO, cardNumber, notificationStatus);
    		return new RefundOrchestrationResultDTO(notificationStatus, refundAmount);
		} catch (Exception e) {
			notificationStatus = AutoFillSSRNotificationStatus.SSR_COMMENCED_BUT_FAILED;
			notificationStatusDataService.notifyAutoFillOfJourneySSRStatus(incompleteJourneyService.getIncompleteJourneyNotification(cardId, linkedTransactionDateTime, linkedStationKey), cardNumber, notificationStatus);
			return new RefundOrchestrationResultDTO(AutoFillSSRNotificationStatus.SSR_COMMENCED_BUT_FAILED, 0);
		}
    	
    }
 
	@Override
	public Integer calculateRefund(final Long cardId, final Date linkedTransactionDateTime, final Integer linkedStationKey, Integer missingStationId, String reasonForMissing,  boolean agentSubmitted) {
    	final CardDTO cardDTO = cardDataService.findById(cardId);
    	final IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO = incompleteJourneyService.retrieveEligibleIncompleteJourney(cardId, linkedTransactionDateTime, linkedStationKey);
		oysterJourneyHistoryDataService.insertSyntheticTap(incompleteJourneyNotificationDTO, cardDTO.getCardNumber(), missingStationId,   reasonForMissing, agentSubmitted);
		final JourneyDayDTO journeyDayDTO = journeyHistoryService.getJourneysForDay(cardId, linkedTransactionDateTime);
		final RecalculatedOysterChargeResponseDTO recalculatedOysterChargeResponseDTO = fareAggregationDataService.getRecalculatedOysterCharge(journeyDayDTO , cardDTO.getCardNumber());
		return journeyDayDTO.getTotalSpent() - recalculatedOysterChargeResponseDTO.getDayPricePence() ;
	}

}
