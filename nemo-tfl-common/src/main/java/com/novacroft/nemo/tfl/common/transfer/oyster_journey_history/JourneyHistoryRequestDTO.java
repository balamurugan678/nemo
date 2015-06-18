package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import java.util.Date;

/**
 * Transfer class for journey history request
 */
public class JourneyHistoryRequestDTO {
    protected String cardNumber;
    protected Date rangeFrom;
    protected Date rangeTo;

    public JourneyHistoryRequestDTO() {
    }

    public JourneyHistoryRequestDTO(String cardNumber, Date rangeFrom, Date rangeTo) {
        this.cardNumber = cardNumber;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getRangeFrom() {
        return rangeFrom;
    }

    public void setRangeFrom(Date rangeFrom) {
        this.rangeFrom = rangeFrom;
    }

    public Date getRangeTo() {
        return rangeTo;
    }

    public void setRangeTo(Date rangeTo) {
        this.rangeTo = rangeTo;
    }
}
