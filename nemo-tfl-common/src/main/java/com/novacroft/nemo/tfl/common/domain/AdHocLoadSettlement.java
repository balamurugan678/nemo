package com.novacroft.nemo.tfl.common.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Settlement using payment card: payment or refund against an order.
 */
@Entity
@DiscriminatorValue("AdHocLoad")
public class AdHocLoadSettlement extends Settlement {
    protected Long cardId;
    protected Integer requestSequenceNumber;
    protected Integer pickUpNationalLocationCode;
    protected Date expiresOn;
    protected Item item;

    public AdHocLoadSettlement() {
    }

    public AdHocLoadSettlement(Long orderId, String status, Integer amount, Date settlementDate, Long cardId,
                               Integer requestSequenceNumber, Integer pickUpNationalLocationCode, Date expiresOn) {
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.settlementDate = settlementDate;
        this.cardId = cardId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.pickUpNationalLocationCode = pickUpNationalLocationCode;
        this.expiresOn = expiresOn;
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

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
    }

    @ManyToOne
    @JoinColumn(name = "itemid", nullable = true)
    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

}
