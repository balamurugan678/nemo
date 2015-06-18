package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

public class JourneyCompletedRefundItemDTO extends ItemDTO {

    /**
	 * 
	 */
    private static final long serialVersionUID = 6349779010082153317L;

    protected Date processingDate;

    protected Date journeyDate;

    protected Integer startExitStation;

    protected Integer providedStation;

    protected Integer pickUpStation;

    protected Long refundReason;

    protected String errorDescription;

    protected Integer journeyID;

    protected String startExitStationName;

    protected String providedStationName;

    protected String pickUpStationName;

    public JourneyCompletedRefundItemDTO() {
    }

    public JourneyCompletedRefundItemDTO(Long cardId, Integer price, Long orderId) {
        this.cardId = cardId;
        this.price = price;
        this.orderId = orderId;

    }

    public JourneyCompletedRefundItemDTO(Long cardId, Integer price, Long orderId, Date processingDate, Date journeyDate, Integer startExitStation,
                    Integer providedStation, Integer pickUpStation, Long refundReason, String errorDescription, Integer journeyID) {
        super();
        this.cardId = cardId;
        this.price = price;
        this.orderId = orderId;
        this.processingDate = processingDate;
        this.journeyDate = journeyDate;
        this.startExitStation = startExitStation;
        this.providedStation = providedStation;
        this.pickUpStation = pickUpStation;
        this.refundReason = refundReason;
        this.errorDescription = errorDescription;
        this.journeyID = journeyID;
    }

    public Date getProcessingDate() {
        return processingDate;
    }

    public void setProcessingDate(Date processingDate) {
        this.processingDate = processingDate;
    }

    public Date getJourneyDate() {
        return journeyDate;
    }

    public void setJourneyDate(Date journeyDate) {
        this.journeyDate = journeyDate;
    }

    public Integer getStartExitStation() {
        return startExitStation;
    }

    public void setStartExitStation(Integer startExitStation) {
        this.startExitStation = startExitStation;
    }

    public Integer getProvidedStation() {
        return providedStation;
    }

    public void setProvidedStation(Integer providedStation) {
        this.providedStation = providedStation;
    }

    public Integer getPickUpStation() {
        return pickUpStation;
    }

    public void setPickUpStation(Integer pickUpStation) {
        this.pickUpStation = pickUpStation;
    }

    public Long getRefundReason() {
        return refundReason;
    }

    public void setRefundReason(Long refundReason) {
        this.refundReason = refundReason;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    public Integer getJourneyID() {
        return journeyID;
    }

    public void setJourneyID(Integer journeyID) {
        this.journeyID = journeyID;
    }

    public String getStartExitStationName() {
        return startExitStationName;
    }

    public void setStartExitStationName(String startExitStationName) {
        this.startExitStationName = startExitStationName;
    }

    public String getProvidedStationName() {
        return providedStationName;
    }

    public void setProvidedStationName(String providedStationName) {
        this.providedStationName = providedStationName;
    }

    public String getPickUpStationName() {
        return pickUpStationName;
    }

    public void setPickUpStationName(String pickUpStationName) {
        this.pickUpStationName = pickUpStationName;
    }

}
