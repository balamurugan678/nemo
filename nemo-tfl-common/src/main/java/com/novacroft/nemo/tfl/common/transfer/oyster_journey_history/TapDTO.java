package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import java.util.Date;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;

/**
 * Transfer class for Oyster journey web service tap
 */
public class TapDTO {
    protected Integer addedStoredValueBalance;
    protected Integer agentNumber;
    protected Integer innerZone;
    protected Integer journeyId;
    protected String narrative;
    protected Integer nationalLocationCode;
    protected Integer outerZone;
    protected Integer paymentMethodCode;
    protected Integer rollOverNumber;
    protected String routeId;
    protected Integer sequenceNumber;
    protected Boolean suppressCode;
    protected Integer storedValueBalance;
    protected Boolean syntheticTap;
    protected Date transactionAt;
    protected Integer transactionType;
    protected TapDisplayDTO tapDisplay = new TapDisplayDTO();

    public TapDTO() {
    }

    public TapDTO(Integer addedStoredValueBalance, Integer agentNumber, Integer innerZone, Integer journeyId, String narrative,
                  Integer nationalLocationCode, Integer outerZone, Integer paymentMethodCode, Integer rollOverNumber,
                  String routeId, Integer sequenceNumber, Boolean suppressCode, Integer storedValueBalance,
                  Boolean syntheticTap, Date transactionAt, Integer transactionType) {
        this.addedStoredValueBalance = addedStoredValueBalance;
        this.agentNumber = agentNumber;
        this.innerZone = innerZone;
        this.journeyId = journeyId;
        this.narrative = narrative;
        this.nationalLocationCode = nationalLocationCode;
        this.outerZone = outerZone;
        this.paymentMethodCode = paymentMethodCode;
        this.rollOverNumber = rollOverNumber;
        this.routeId = routeId;
        this.sequenceNumber = sequenceNumber;
        this.suppressCode = suppressCode;
        this.storedValueBalance = storedValueBalance;
        this.syntheticTap = syntheticTap;
        this.transactionAt = transactionAt;
        this.transactionType = transactionType;
    }

    public Integer getAddedStoredValueBalance() {
        return addedStoredValueBalance;
    }

    public void setAddedStoredValueBalance(Integer addedStoredValueBalance) {
        this.addedStoredValueBalance = addedStoredValueBalance;
    }

    public Integer getAgentNumber() {
        return agentNumber;
    }

    public void setAgentNumber(Integer agentNumber) {
        this.agentNumber = agentNumber;
    }

    public Integer getInnerZone() {
        return innerZone;
    }

    public void setInnerZone(Integer innerZone) {
        this.innerZone = innerZone;
    }

    public Integer getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public Integer getNationalLocationCode() {
        return nationalLocationCode;
    }

    public void setNationalLocationCode(Integer nationalLocationCode) {
        this.nationalLocationCode = nationalLocationCode;
    }

    public Integer getOuterZone() {
        return outerZone;
    }

    public void setOuterZone(Integer outerZone) {
        this.outerZone = outerZone;
    }

    public Integer getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(Integer paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public Integer getRollOverNumber() {
        return rollOverNumber;
    }

    public void setRollOverNumber(Integer rollOverNumber) {
        this.rollOverNumber = rollOverNumber;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Integer getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(Integer sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public Boolean getSuppressCode() {
        return suppressCode;
    }

    public void setSuppressCode(Boolean suppressCode) {
        this.suppressCode = suppressCode;
    }

    public Integer getStoredValueBalance() {
        return storedValueBalance;
    }

    public void setStoredValueBalance(Integer storedValueBalance) {
        this.storedValueBalance = storedValueBalance;
    }

    public Boolean getSyntheticTap() {
        return syntheticTap;
    }

    public void setSyntheticTap(Boolean syntheticTap) {
        this.syntheticTap = syntheticTap;
    }

    public Date getTransactionAt() {
        return transactionAt;
    }

    public void setTransactionAt(Date transactionAt) {
        this.transactionAt = transactionAt;
    }

    public Integer getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(Integer transactionType) {
        this.transactionType = transactionType;
    }

    public TapDisplayDTO getTapDisplay() {
        return tapDisplay;
    }

    public void setTapDisplay(TapDisplayDTO tapDisplay) {
        this.tapDisplay = tapDisplay;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        TapDTO that = (TapDTO) object;

        return new EqualsBuilder().append(addedStoredValueBalance, that.addedStoredValueBalance)
                .append(agentNumber, that.agentNumber).append(innerZone, that.innerZone).append(journeyId, that.journeyId)
                .append(narrative, that.narrative).append(nationalLocationCode, that.nationalLocationCode)
                .append(outerZone, that.outerZone).append(paymentMethodCode, that.paymentMethodCode)
                .append(rollOverNumber, that.rollOverNumber).append(routeId, that.routeId)
                .append(sequenceNumber, that.sequenceNumber).append(suppressCode, that.suppressCode)
                .append(storedValueBalance, that.storedValueBalance).append(syntheticTap, that.syntheticTap)
                .append(transactionAt, that.transactionAt).append(transactionType, that.transactionType).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.TAP_DTO.initialiser(), HashCodeSeed.TAP_DTO.multiplier())
                .append(addedStoredValueBalance).append(agentNumber).append(innerZone).append(journeyId).append(narrative)
                .append(nationalLocationCode).append(outerZone).append(paymentMethodCode).append(rollOverNumber).append(routeId)
                .append(sequenceNumber).append(suppressCode).append(storedValueBalance).append(syntheticTap)
                .append(transactionAt).append(transactionType).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("addedStoredValueBalance", addedStoredValueBalance)
                .append("agentNumber", agentNumber).append("innerZone", innerZone).append("journeyId", journeyId)
                .append("narrative", narrative).append("nationalLocationCode", nationalLocationCode)
                .append("outerZone", outerZone).append("paymentMethodCode", paymentMethodCode)
                .append("rollOverNumber", rollOverNumber).append("routeId", routeId).append("sequenceNumber", sequenceNumber)
                .append("suppressCode", suppressCode).append("storedValueBalance", storedValueBalance)
                .append("syntheticTap", syntheticTap).append("transactionAt", transactionAt)
                .append("transactionType", transactionType).toString();
    }
}
