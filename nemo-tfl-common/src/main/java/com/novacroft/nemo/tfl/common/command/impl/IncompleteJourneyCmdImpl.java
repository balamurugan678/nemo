package com.novacroft.nemo.tfl.common.command.impl;

import com.novacroft.nemo.tfl.common.command.SelectCardCmd;
import com.novacroft.nemo.tfl.common.transfer.IncompleteJourneyHistoryDTO;

/**
 * Command class for incomplete journeys
 */
public class IncompleteJourneyCmdImpl implements SelectCardCmd {
    protected Long cardId;
    protected IncompleteJourneyHistoryDTO incompleteJourneyHistoryDTO;

    @Override
    public Long getCardId() {
        return this.cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Boolean hasIncompleteJourneys() {
        return this.incompleteJourneyHistoryDTO != null && this.incompleteJourneyHistoryDTO.getIncompleteJourneyMonthDTO() != null && !this.incompleteJourneyHistoryDTO.getIncompleteJourneyMonthDTO().isEmpty() ;
    }

	public IncompleteJourneyHistoryDTO getIncompleteJourneyHistoryDTO() {
		return incompleteJourneyHistoryDTO;
	}

	public void setIncompleteJourneyHistoryDTO(
			IncompleteJourneyHistoryDTO incompleteJourneyHistoryDTO) {
		this.incompleteJourneyHistoryDTO = incompleteJourneyHistoryDTO;
	}

	
    
}
