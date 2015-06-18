package com.novacroft.nemo.tfl.common.application_service.journey_history;

import com.novacroft.nemo.tfl.common.transfer.CardPreferencesDTO;

import java.util.Date;
import java.util.List;

/**
 * Journey History scheduled email statement service
 */
public interface JourneyHistoryScheduledStatementService {

    List<CardPreferencesDTO> getCardsForWeeklyStatement();

    List<CardPreferencesDTO> getCardsForMonthlyStatement();

    void sendWeeklyStatement(Long cardId, Date rangeFrom, Date rangeTo);

    void sendMonthlyStatement(Long cardId, Date rangeFrom, Date rangeTo);

    Date getStartOfLastWeek();

    Date getEndOfLastWeek();

    Date getStartOfLastMonth();

    Date getEndOfLastMonth();
}
