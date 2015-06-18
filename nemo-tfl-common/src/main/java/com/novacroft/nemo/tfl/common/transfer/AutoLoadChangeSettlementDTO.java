package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

/**
 * Settlement to track auto load changes.
 */
public class AutoLoadChangeSettlementDTO extends SettlementDTO {
    private static final long serialVersionUID = 4202328749325043069L;
    protected Long cardId;
    protected Integer requestSequenceNumber;
    protected Integer pickUpNationalLocationCode;
    protected String autoTopUpActivity;
    protected Integer autoLoadState;

    public AutoLoadChangeSettlementDTO() {
    }

    public AutoLoadChangeSettlementDTO(Long orderId, String status, Integer amount, Date settlementDate, Long cardId,
                    Integer requestSequenceNumber, Integer pickUpNationalLocationCode,
                    Integer autoLoadState) {
    this.orderId = orderId;
    this.status = status;
    this.amount = amount;
    this.settlementDate = settlementDate;
    this.cardId = cardId;
    this.requestSequenceNumber = requestSequenceNumber;
    this.pickUpNationalLocationCode = pickUpNationalLocationCode;
    this.autoLoadState = autoLoadState;
    }

    public AutoLoadChangeSettlementDTO(Long orderId, String status, Integer amount, Date settlementDate, Long cardId,
                    Integer requestSequenceNumber, Integer pickUpNationalLocationCode,
                    Integer autoLoadState, String autoTopUpActivity) {
    this.orderId = orderId;
    this.status = status;
    this.amount = amount;
    this.settlementDate = settlementDate;
    this.cardId = cardId;
    this.requestSequenceNumber = requestSequenceNumber;
    this.pickUpNationalLocationCode = pickUpNationalLocationCode;
    this.autoLoadState = autoLoadState;
    this.autoTopUpActivity = autoTopUpActivity;
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

    public String getAutoTopUpActivity() {
        return autoTopUpActivity;
    }

    public void setAutoTopUpActivity(String autoTopUpActivity) {
        this.autoTopUpActivity = autoTopUpActivity;
    }

    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }

}
