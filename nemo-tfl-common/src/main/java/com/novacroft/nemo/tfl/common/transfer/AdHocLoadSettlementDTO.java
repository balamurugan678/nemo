package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

/**
 * Settlement using an ad hoc load: payment or refund against an order.
 */
public class AdHocLoadSettlementDTO extends SettlementDTO {
    protected Long cardId;
    protected Integer requestSequenceNumber;
    protected Integer pickUpNationalLocationCode;
    protected Date expiresOn;
    protected ItemDTO item;

    public AdHocLoadSettlementDTO() {
    }

    public AdHocLoadSettlementDTO(Long orderId, String status, Integer amount, Date settlementDate, Long cardId,
                                  Integer requestSequenceNumber, Integer pickUpNationalLocationCode, Date expiresOn) {
        this.orderId = orderId;
        this.status = status;
        this.amount = amount;
        this.cardId = cardId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.pickUpNationalLocationCode = pickUpNationalLocationCode;
        this.expiresOn = expiresOn;
        this.settlementDate = settlementDate;
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

    public ItemDTO getItem() {
        return item;
    }

    public void setItem(ItemDTO item) {
        this.item = item;
    }
}
