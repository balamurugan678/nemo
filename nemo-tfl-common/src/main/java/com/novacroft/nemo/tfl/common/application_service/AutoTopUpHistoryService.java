package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.AutoTopUpHistoryItemDTO;

/**
 * Auto top-up history retrieval service.
 */
public interface AutoTopUpHistoryService {
	List<AutoTopUpHistoryItemDTO> getAutoTopUpHistoryForOysterCard(Long cardId);

}
