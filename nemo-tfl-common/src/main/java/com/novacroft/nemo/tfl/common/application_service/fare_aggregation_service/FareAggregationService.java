package com.novacroft.nemo.tfl.common.application_service.fare_aggregation_service;

import java.util.Date;

import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.RefundOrchestrationResultDTO;

public interface FareAggregationService {

    RefundOrchestrationResultDTO orchestrateServicesForRefundSubmission(Long cardId, Date linkedTransactionDateTime, Integer linkedStationKey, Integer missingStationId, String reasonForMissing, boolean agentSubmitted);

	 Integer calculateRefund(Long cardId, Date linkedTransactionDateTime, Integer linkedStationKey,	Integer missingStationId, String reasonForMissing,	boolean agentSubmitted);

}