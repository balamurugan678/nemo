package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.transfer.incomplete_journey_notification.IncompleteJourneyNotificationDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.InsertSyntheticTapResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryResponseDTO;

import java.util.Date;

/**
 * Journey history data service
 */
public interface OysterJourneyHistoryDataService {
    JourneyHistoryResponseDTO findByCardNumberForDateRangeForOnline(String cardNumber, Date rangeFrom, Date rangeTo);

    JourneyHistoryResponseDTO findByCardNumberForDateRangeForBatch(String cardNumber, Date rangeFrom, Date rangeTo);

    InsertSyntheticTapResponseDTO insertSyntheticTap(IncompleteJourneyNotificationDTO incompleteJourneyNotificationDTO,
                                                     String cardNumber, Integer missingStationId, String reasonForMissing,
                                                     boolean submitedByAgent);
}
