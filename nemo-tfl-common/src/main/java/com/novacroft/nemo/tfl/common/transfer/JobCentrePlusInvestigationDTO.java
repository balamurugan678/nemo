package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * TfL job centre plus investigation transfer implementation
 */

public class JobCentrePlusInvestigationDTO extends AbstractBaseDTO {
    protected Long cardId;
    protected String cardNumber;
    protected Date startDate;
    protected Date expiryDate;

    public JobCentrePlusInvestigationDTO() {
    }

    public JobCentrePlusInvestigationDTO(Long cardId, String cardNumber, Date startDate, Date expiryDate) {
        this.cardId = cardId;
        this.cardNumber = cardNumber;
        this.startDate = startDate;
        this.expiryDate = expiryDate;
    }
    
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

}
