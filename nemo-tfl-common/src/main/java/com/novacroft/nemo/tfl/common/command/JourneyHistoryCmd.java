package com.novacroft.nemo.tfl.common.command;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyHistoryDTO;

import java.util.Date;

/*
 * Journey History Command specifications
 */
public interface JourneyHistoryCmd {
    Long getCardId();

    Integer getWeekNumberFromToday();
    
    Date getStartDate();

    Date getEndDate();

    JourneyHistoryDTO getJourneyHistory();
}
