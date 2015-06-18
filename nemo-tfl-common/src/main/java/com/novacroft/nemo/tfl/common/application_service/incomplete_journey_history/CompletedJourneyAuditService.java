package com.novacroft.nemo.tfl.common.application_service.incomplete_journey_history;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.JourneyCompletedRefundItemDTO;

public interface CompletedJourneyAuditService {

	List<JourneyCompletedRefundItemDTO> getAuditLog(Long cardId);

}