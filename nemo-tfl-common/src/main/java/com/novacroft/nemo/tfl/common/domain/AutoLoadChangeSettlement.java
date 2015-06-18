package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

/**
 * Settlement to track auto load changes
 */
@Entity
@DiscriminatorValue("AutoLoadChange")
public class AutoLoadChangeSettlement extends Settlement {
    private static final long serialVersionUID = -506538275786518910L;
    
    protected Long cardId;
    protected Integer requestSequenceNumber;
    protected Integer pickUpNationalLocationCode;
    protected Integer autoLoadState;
    
    public AutoLoadChangeSettlement() {
    }

    public AutoLoadChangeSettlement(Long orderId, String status, Integer amount, Date settlementDate, Long cardId,
                                    Integer requestSequenceNumber, Integer pickUpNationalLocationCode, Integer autoLoadState) {
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.settlementDate = settlementDate;
        this.cardId = cardId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.pickUpNationalLocationCode = pickUpNationalLocationCode;
        this.autoLoadState = autoLoadState;
    }

    public Long getCardId() {
        return cardId;

    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public Integer getPickUpNationalLocationCode() {
        return pickUpNationalLocationCode;
    }

    public void setPickUpNationalLocationCode(Integer pickUpNationalLocationCode) {
        this.pickUpNationalLocationCode = pickUpNationalLocationCode;
    }

    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }

}
