package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;

/**
 * Oyster journey web service - journey history container
 */
public class JourneyHistoryDTO {
    protected String cardNumber;
    protected Date rangeFrom;
    protected Date rangeTo;
    protected List<JourneyDayDTO> journeyDays;

    public JourneyHistoryDTO() {
    }

    public JourneyHistoryDTO(String cardNumber, Date rangeFrom, Date rangeTo) {
        this.cardNumber = cardNumber;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
    }

    public JourneyHistoryDTO(String cardNumber, Date rangeFrom, Date rangeTo, List<JourneyDayDTO> journeyDays) {
        this.cardNumber = cardNumber;
        this.rangeFrom = rangeFrom;
        this.rangeTo = rangeTo;
        this.journeyDays = journeyDays;
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

    public List<JourneyDayDTO> getJourneyDays() {
        return journeyDays;
    }

    public void setJourneyDays(List<JourneyDayDTO> journeyDays) {
        this.journeyDays = journeyDays;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        JourneyHistoryDTO that = (JourneyHistoryDTO) object;

        return new EqualsBuilder().append(cardNumber, that.cardNumber).append(rangeFrom, that.rangeFrom)
                .append(rangeTo, that.rangeTo).append(journeyDays, that.journeyDays).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.JOURNEY_HISTORY_DTO.initialiser(),
                HashCodeSeed.JOURNEY_HISTORY_DTO.multiplier()).append(cardNumber).append(rangeFrom).append(rangeTo)
                .append(journeyDays).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("cardNumber", cardNumber).append("rangeFrom", rangeFrom)
                .append("rangeTo", rangeTo).append("journeyDays", journeyDays).toString();
    }
}
