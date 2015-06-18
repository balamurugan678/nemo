package com.novacroft.nemo.tfl.common.command;

import java.util.Date;

import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

public interface JourneyHistoryItemCmd {

    String getCardNumber();

    Long getCardId();

    Date getJourneyDate();

    Integer getJourneyId();

    JourneyDTO getJourney();
}
