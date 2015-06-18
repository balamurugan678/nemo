package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import java.util.Date;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Companion class to JourneyDTO to encapsulate human readable journey information
 */
public class JourneyDisplayDTO {
    protected String transactionLocationName = "";
    protected String exitLocationName = "";
    protected Date effectiveTrafficOn;
    protected String pseudoTransactionTypeDisplayDescription;
    protected String journeyStartTime;
    protected String journeyEndTime;
    protected String journeyTime;
    protected String journeyDescription;
    protected Integer chargeAmount;
    protected Integer creditAmount;
    protected Boolean warning;
    protected Boolean manuallyCorrected;
    protected Boolean topUpActivated;
    protected String linkedStation;

    public JourneyDisplayDTO() {
    }

    /**
     * @deprecated
     */
    public JourneyDisplayDTO(String transactionLocationName, String exitLocationName, Date effectiveTrafficOn,
                             String pseudoTransactionTypeDisplayDescription, String journeyStartTime, String journeyEndTime,
                             String journeyTime, String journeyDescription, Integer chargeAmount, Integer creditAmount,
                             Boolean warning, Boolean autoCompletedJourney, Boolean fareCapped) {
        this.transactionLocationName = transactionLocationName;
        this.exitLocationName = exitLocationName;
        this.effectiveTrafficOn = effectiveTrafficOn;
        this.pseudoTransactionTypeDisplayDescription = pseudoTransactionTypeDisplayDescription;
        this.journeyStartTime = journeyStartTime;
        this.journeyEndTime = journeyEndTime;
        this.journeyTime = journeyTime;
        this.journeyDescription = journeyDescription;
        this.chargeAmount = chargeAmount;
        this.creditAmount = creditAmount;
        this.warning = warning;
    }

    public JourneyDisplayDTO(Boolean topUpActivated, String transactionLocationName, String exitLocationName, Date effectiveTrafficOn,
                             String pseudoTransactionTypeDisplayDescription, String journeyStartTime, String journeyEndTime,
                             String journeyTime, String journeyDescription, Integer chargeAmount, Integer creditAmount,
                             Boolean warning, Boolean manuallyCorrected) {
        this.transactionLocationName = transactionLocationName;
        this.exitLocationName = exitLocationName;
        this.effectiveTrafficOn = effectiveTrafficOn;
        this.pseudoTransactionTypeDisplayDescription = pseudoTransactionTypeDisplayDescription;
        this.journeyStartTime = journeyStartTime;
        this.journeyEndTime = journeyEndTime;
        this.journeyTime = journeyTime;
        this.journeyDescription = journeyDescription;
        this.chargeAmount = chargeAmount;
        this.creditAmount = creditAmount;
        this.warning = warning;
        this.manuallyCorrected = manuallyCorrected;
        this.topUpActivated = topUpActivated;
    }

    public String getTransactionLocationName() {
        return transactionLocationName;
    }

    public void setTransactionLocationName(String transactionLocationName) {
        this.transactionLocationName = transactionLocationName;
    }

    public String getExitLocationName() {
        return exitLocationName;
    }

    public void setExitLocationName(String exitLocationName) {
        this.exitLocationName = exitLocationName;
    }

    public Date getEffectiveTrafficOn() {
        return effectiveTrafficOn;
    }

    public void setEffectiveTrafficOn(Date effectiveTrafficOn) {
        this.effectiveTrafficOn = effectiveTrafficOn;
    }

    public String getPseudoTransactionTypeDisplayDescription() {
        return pseudoTransactionTypeDisplayDescription;
    }

    public void setPseudoTransactionTypeDisplayDescription(String pseudoTransactionTypeDisplayDescription) {
        this.pseudoTransactionTypeDisplayDescription = pseudoTransactionTypeDisplayDescription;
    }

    public String getJourneyStartTime() {
        return journeyStartTime;
    }

    public void setJourneyStartTime(String journeyStartTime) {
        this.journeyStartTime = journeyStartTime;
    }

    public String getJourneyEndTime() {
        return journeyEndTime;
    }

    public void setJourneyEndTime(String journeyEndTime) {
        this.journeyEndTime = journeyEndTime;
    }

    public String getJourneyTime() {
        return journeyTime;
    }

    public void setJourneyTime(String journeyTime) {
        this.journeyTime = journeyTime;
    }

    public String getJourneyDescription() {
        return journeyDescription;
    }

    public void setJourneyDescription(String journeyDescription) {
        this.journeyDescription = journeyDescription;
    }

    public Integer getChargeAmount() {
        return chargeAmount;
    }

    public void setChargeAmount(Integer chargeAmount) {
        this.chargeAmount = chargeAmount;
    }

    public Integer getCreditAmount() {
        return creditAmount;
    }

    public void setCreditAmount(Integer creditAmount) {
        this.creditAmount = creditAmount;
    }

    public Boolean getWarning() {
        return warning;
    }

    public void setWarning(Boolean warning) {
        this.warning = warning;
    }

    public Boolean getManuallyCorrected() {
        return manuallyCorrected;
    }

    public void setManuallyCorrected(Boolean manuallyCorrected) {
        this.manuallyCorrected = manuallyCorrected;
    }

    public Boolean getTopUpActivated() {
        return topUpActivated;
    }

    public void setTopUpActivated(Boolean topUpActivated) {
        this.topUpActivated = topUpActivated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("transactionLocationName", transactionLocationName)
                .append("exitLocationName", exitLocationName).append("effectiveTrafficOn", effectiveTrafficOn)
                .append("pseudoTransactionTypeDisplayDescription", pseudoTransactionTypeDisplayDescription)
                .append("journeyTime", journeyTime).append("journeyDescription", journeyDescription)
                .append("chargeAmount", chargeAmount).append("creditAmount", creditAmount).append("warning", warning)
                .append("manuallyCorrected", manuallyCorrected)
                .append("topUpActivated", topUpActivated).toString();
        
    }

	public void setLinkedStation(String stationName) {
		linkedStation = stationName;
	}

	public String getLinkedStation() {
		return linkedStation;
	}
	
}
