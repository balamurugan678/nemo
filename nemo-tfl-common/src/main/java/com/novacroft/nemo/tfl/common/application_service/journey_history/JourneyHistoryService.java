package com.novacroft.nemo.tfl.common.application_service.journey_history;

import java.util.Date;

import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.command.impl.CompleteJourneyCommandImpl;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDayDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

/**
 * Journey History application service specification
 */

public interface JourneyHistoryService {
    JourneyHistoryDTO getJourneyHistory(Long cardId, Date rangeFrom, Date rangeTo);

    JourneyDTO getJourneyHistoryItem(Long cardId, Date journeyDate, Integer journeyId);

    byte[] getJourneyHistoryAsCsv(JourneyHistoryDTO journeyHistory);

    byte[] getJourneyHistoryAsPdf(JourneyHistoryDTO journeyHistory);
    
    SelectListDTO getWeekRangeSelectListDTO();

	JourneyDTO getIncompleteJourney(Long cardId, Date transactionDateTime, Integer linkedStation);

	JourneyDayDTO getJourneysForDay(Long cardId, Date transactionDateTime);
	
	void resolveLinkedStationAndTransaction(CompleteJourneyCommandImpl ompleteJourneyCommandImpl);
	
	
}
