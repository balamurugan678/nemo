package com.novacroft.nemo.tfl.common.command.impl;

import java.util.Date;

import com.novacroft.nemo.tfl.common.command.JourneyHistoryItemCmd;
import com.novacroft.nemo.tfl.common.transfer.oyster_journey_history.JourneyDTO;

public class JourneyHistoryItemCmdImpl implements JourneyHistoryItemCmd {
    protected String cardNumber;
    protected Long cardId;
    protected Date journeyDate;
    protected Integer journeyId;
    protected JourneyDTO journey;


    @Override
    public String getCardNumber() {
        return cardNumber;
    }

    @Override
    public Long getCardId() {
        return cardId;
    }

    @Override
    public Date getJourneyDate() {
        return journeyDate;
    }

    @Override
    public Integer getJourneyId() {
        return journeyId;
    }

    @Override
    public JourneyDTO getJourney() {
        return journey;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public void setJourneyDate(Date journeyDate) {
        this.journeyDate = journeyDate;
    }

    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    public void setJourney(JourneyDTO journey) {
        this.journey = journey;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

}
