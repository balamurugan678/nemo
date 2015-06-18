package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Transfer class for Oyster journey web service journey
 */
public class JourneyDTO {
    protected Integer addedStoredValueBalance;
    protected Integer agentNumber;
    protected Boolean autoCompletionFlag;
    protected Integer bestValueActualFare;
    protected String bestValueCappingScheme;
    protected Integer busOffPeakRunTotal;
    protected Integer busPeakRunTotal;
    protected Integer cardDiscountId;
    protected Integer cardPassengerTypeId;
    protected Integer cardType;
    protected String chargeDescription;
    protected String concessionNarrative;
    protected Boolean dailyCappingFlag;
    protected Integer discountedFare;
    protected String discountNarrative;
    protected Date exitAt;
    protected Integer exitLocation;
    protected Integer journeyId;
    protected Integer innerZone;
    protected Integer modeOfTravel;
    protected String narrative;
    protected String offPeakCap;
    protected String osi;
    protected Integer outerZone;
    protected Boolean payAsYouGoUsed;
    protected Integer paymentMethodCode;
    protected String peakCap;
    protected String peakFare;
    protected Long prestigeId;
    protected Integer productCode;
    protected Date productExpiresOn;
    protected Date productStartsOn;
    protected Integer productTimeValidityCode;
    protected Integer productZonalValidityCode;
    protected Integer pseudoTransactionTypeId;
    protected Integer railOffPeakMaxOuterZone;
    protected Integer railOffPeakMinInnerZone;
    protected Integer railOffPeakRunTotal;
    protected Integer railPeakMaxOuterZone;
    protected Integer railPeakMinInnerZone;
    protected Integer railPeakRunTotal;
    protected String routeId;
    protected Integer runningTotal;
    protected Boolean suppressCode;
    protected Integer storedValueBalance;
    protected List<TapDTO> taps;
    protected Boolean transferDiscountsAppliedFlag;
    protected Date trafficOn;
    protected Boolean travelCardUsed;
    protected String travelType;
    protected Date transactionAt;
    protected Integer transactionLocation;
    protected String validTicketOnCMS;
    protected Integer zonalIndicator;

    protected JourneyDisplayDTO journeyDisplay = new JourneyDisplayDTO();

    public JourneyDTO() {
    }

    public JourneyDTO(Integer addedStoredValueBalance, Integer agentNumber, Boolean autoCompletionFlag,
                      Integer bestValueActualFare, String bestValueCappingScheme, Integer busOffPeakRunTotal,
                      Integer busPeakRunTotal, Integer cardDiscountId, Integer cardPassengerTypeId, Integer cardType,
                      String chargeDescription, String concessionNarrative, Boolean dailyCappingFlag, Integer discountedFare,
                      String discountNarrative, Date exitAt, Integer exitLocation, Integer journeyId, Integer innerZone,
                      Integer modeOfTravel, String narrative, String offPeakCap, String osi, Integer outerZone,
                      Boolean payAsYouGoUsed, Integer paymentMethodCode, String peakCap, String peakFare, Long prestigeId,
                      Integer productCode, Date productExpiresOn, Date productStartsOn, Integer productTimeValidityCode,
                      Integer productZonalValidityCode, Integer pseudoTransactionTypeId, Integer railOffPeakMaxOuterZone,
                      Integer railOffPeakMinInnerZone, Integer railOffPeakRunTotal, Integer railPeakMaxOuterZone,
                      Integer railPeakMinInnerZone, Integer railPeakRunTotal, String routeId, Integer runningTotal,
                      Boolean suppressCode, Integer storedValueBalance, List<TapDTO> taps, Boolean transferDiscountsAppliedFlag,
                      Date trafficOn, Boolean travelCardUsed, String travelType, Date transactionAt,
                      Integer transactionLocation, String validTicketOnCMS, Integer zonalIndicator) {
        this.addedStoredValueBalance = addedStoredValueBalance;
        this.agentNumber = agentNumber;
        this.autoCompletionFlag = autoCompletionFlag;
        this.bestValueActualFare = bestValueActualFare;
        this.bestValueCappingScheme = bestValueCappingScheme;
        this.busOffPeakRunTotal = busOffPeakRunTotal;
        this.busPeakRunTotal = busPeakRunTotal;
        this.cardDiscountId = cardDiscountId;
        this.cardPassengerTypeId = cardPassengerTypeId;
        this.cardType = cardType;
        this.chargeDescription = chargeDescription;
        this.concessionNarrative = concessionNarrative;
        this.dailyCappingFlag = dailyCappingFlag;
        this.discountedFare = discountedFare;
        this.discountNarrative = discountNarrative;
        this.exitAt = exitAt;
        this.exitLocation = exitLocation;
        this.journeyId = journeyId;
        this.innerZone = innerZone;
        this.modeOfTravel = modeOfTravel;
        this.narrative = narrative;
        this.offPeakCap = offPeakCap;
        this.osi = osi;
        this.outerZone = outerZone;
        this.payAsYouGoUsed = payAsYouGoUsed;
        this.paymentMethodCode = paymentMethodCode;
        this.peakCap = peakCap;
        this.peakFare = peakFare;
        this.prestigeId = prestigeId;
        this.productCode = productCode;
        this.productExpiresOn = productExpiresOn;
        this.productStartsOn = productStartsOn;
        this.productTimeValidityCode = productTimeValidityCode;
        this.productZonalValidityCode = productZonalValidityCode;
        this.pseudoTransactionTypeId = pseudoTransactionTypeId;
        this.railOffPeakMaxOuterZone = railOffPeakMaxOuterZone;
        this.railOffPeakMinInnerZone = railOffPeakMinInnerZone;
        this.railOffPeakRunTotal = railOffPeakRunTotal;
        this.railPeakMaxOuterZone = railPeakMaxOuterZone;
        this.railPeakMinInnerZone = railPeakMinInnerZone;
        this.railPeakRunTotal = railPeakRunTotal;
        this.routeId = routeId;
        this.runningTotal = runningTotal;
        this.suppressCode = suppressCode;
        this.storedValueBalance = storedValueBalance;
        this.taps = taps;
        this.transferDiscountsAppliedFlag = transferDiscountsAppliedFlag;
        this.trafficOn = trafficOn;
        this.travelCardUsed = travelCardUsed;
        this.travelType = travelType;
        this.transactionAt = transactionAt;
        this.transactionLocation = transactionLocation;
        this.validTicketOnCMS = validTicketOnCMS;
        this.zonalIndicator = zonalIndicator;
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

    public Boolean getAutoCompletionFlag() {
        return autoCompletionFlag;
    }

    public void setAutoCompletionFlag(Boolean autoCompletionFlag) {
        this.autoCompletionFlag = autoCompletionFlag;
    }

    public Integer getBestValueActualFare() {
        return bestValueActualFare;
    }

    public void setBestValueActualFare(Integer bestValueActualFare) {
        this.bestValueActualFare = bestValueActualFare;
    }

    public String getBestValueCappingScheme() {
        return bestValueCappingScheme;
    }

    public void setBestValueCappingScheme(String bestValueCappingScheme) {
        this.bestValueCappingScheme = bestValueCappingScheme;
    }

    public Integer getBusOffPeakRunTotal() {
        return busOffPeakRunTotal;
    }

    public void setBusOffPeakRunTotal(Integer busOffPeakRunTotal) {
        this.busOffPeakRunTotal = busOffPeakRunTotal;
    }

    public Integer getBusPeakRunTotal() {
        return busPeakRunTotal;
    }

    public void setBusPeakRunTotal(Integer busPeakRunTotal) {
        this.busPeakRunTotal = busPeakRunTotal;
    }

    public Integer getCardDiscountId() {
        return cardDiscountId;
    }

    public void setCardDiscountId(Integer cardDiscountId) {
        this.cardDiscountId = cardDiscountId;
    }

    public Integer getCardPassengerTypeId() {
        return cardPassengerTypeId;
    }

    public void setCardPassengerTypeId(Integer cardPassengerTypeId) {
        this.cardPassengerTypeId = cardPassengerTypeId;
    }

    public Integer getCardType() {
        return cardType;
    }

    public void setCardType(Integer cardType) {
        this.cardType = cardType;
    }

    public String getChargeDescription() {
        return chargeDescription;
    }

    public void setChargeDescription(String chargeDescription) {
        this.chargeDescription = chargeDescription;
    }

    public String getConcessionNarrative() {
        return concessionNarrative;
    }

    public void setConcessionNarrative(String concessionNarrative) {
        this.concessionNarrative = concessionNarrative;
    }

    public Boolean getDailyCappingFlag() {
        return dailyCappingFlag;
    }

    public void setDailyCappingFlag(Boolean dailyCappingFlag) {
        this.dailyCappingFlag = dailyCappingFlag;
    }

    public Integer getDiscountedFare() {
        return discountedFare;
    }

    public void setDiscountedFare(Integer discountedFare) {
        this.discountedFare = discountedFare;
    }

    public String getDiscountNarrative() {
        return discountNarrative;
    }

    public void setDiscountNarrative(String discountNarrative) {
        this.discountNarrative = discountNarrative;
    }

    public Date getExitAt() {
        return exitAt;
    }

    public void setExitAt(Date exitAt) {
        this.exitAt = exitAt;
    }

    public Integer getExitLocation() {
        return exitLocation;
    }

    public void setExitLocation(Integer exitLocation) {
        this.exitLocation = exitLocation;
    }

    public Integer getJourneyId() {
        return journeyId;
    }

    public void setJourneyId(Integer journeyId) {
        this.journeyId = journeyId;
    }

    public Integer getInnerZone() {
        return innerZone;
    }

    public void setInnerZone(Integer innerZone) {
        this.innerZone = innerZone;
    }

    public Integer getModeOfTravel() {
        return modeOfTravel;
    }

    public void setModeOfTravel(Integer modeOfTravel) {
        this.modeOfTravel = modeOfTravel;
    }

    public String getNarrative() {
        return narrative;
    }

    public void setNarrative(String narrative) {
        this.narrative = narrative;
    }

    public String getOffPeakCap() {
        return offPeakCap;
    }

    public void setOffPeakCap(String offPeakCap) {
        this.offPeakCap = offPeakCap;
    }

    public String getOsi() {
        return osi;
    }

    public void setOsi(String osi) {
        this.osi = osi;
    }

    public Integer getOuterZone() {
        return outerZone;
    }

    public void setOuterZone(Integer outerZone) {
        this.outerZone = outerZone;
    }

    public Boolean getPayAsYouGoUsed() {
        return payAsYouGoUsed;
    }

    public void setPayAsYouGoUsed(Boolean payAsYouGoUsed) {
        this.payAsYouGoUsed = payAsYouGoUsed;
    }

    public Integer getPaymentMethodCode() {
        return paymentMethodCode;
    }

    public void setPaymentMethodCode(Integer paymentMethodCode) {
        this.paymentMethodCode = paymentMethodCode;
    }

    public String getPeakCap() {
        return peakCap;
    }

    public void setPeakCap(String peakCap) {
        this.peakCap = peakCap;
    }

    public String getPeakFare() {
        return peakFare;
    }

    public void setPeakFare(String peakFare) {
        this.peakFare = peakFare;
    }

    public Long getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(Long prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Integer getProductCode() {
        return productCode;
    }

    public void setProductCode(Integer productCode) {
        this.productCode = productCode;
    }

    public Date getProductExpiresOn() {
        return productExpiresOn;
    }

    public void setProductExpiresOn(Date productExpiresOn) {
        this.productExpiresOn = productExpiresOn;
    }

    public Date getProductStartsOn() {
        return productStartsOn;
    }

    public void setProductStartsOn(Date productStartsOn) {
        this.productStartsOn = productStartsOn;
    }

    public Integer getProductTimeValidityCode() {
        return productTimeValidityCode;
    }

    public void setProductTimeValidityCode(Integer productTimeValidityCode) {
        this.productTimeValidityCode = productTimeValidityCode;
    }

    public Integer getProductZonalValidityCode() {
        return productZonalValidityCode;
    }

    public void setProductZonalValidityCode(Integer productZonalValidityCode) {
        this.productZonalValidityCode = productZonalValidityCode;
    }

    public Integer getPseudoTransactionTypeId() {
        return pseudoTransactionTypeId;
    }

    public void setPseudoTransactionTypeId(Integer pseudoTransactionTypeId) {
        this.pseudoTransactionTypeId = pseudoTransactionTypeId;
    }

    public Integer getRailOffPeakMaxOuterZone() {
        return railOffPeakMaxOuterZone;
    }

    public void setRailOffPeakMaxOuterZone(Integer railOffPeakMaxOuterZone) {
        this.railOffPeakMaxOuterZone = railOffPeakMaxOuterZone;
    }

    public Integer getRailOffPeakMinInnerZone() {
        return railOffPeakMinInnerZone;
    }

    public void setRailOffPeakMinInnerZone(Integer railOffPeakMinInnerZone) {
        this.railOffPeakMinInnerZone = railOffPeakMinInnerZone;
    }

    public Integer getRailOffPeakRunTotal() {
        return railOffPeakRunTotal;
    }

    public void setRailOffPeakRunTotal(Integer railOffPeakRunTotal) {
        this.railOffPeakRunTotal = railOffPeakRunTotal;
    }

    public Integer getRailPeakMaxOuterZone() {
        return railPeakMaxOuterZone;
    }

    public void setRailPeakMaxOuterZone(Integer railPeakMaxOuterZone) {
        this.railPeakMaxOuterZone = railPeakMaxOuterZone;
    }

    public Integer getRailPeakMinInnerZone() {
        return railPeakMinInnerZone;
    }

    public void setRailPeakMinInnerZone(Integer railPeakMinInnerZone) {
        this.railPeakMinInnerZone = railPeakMinInnerZone;
    }

    public Integer getRailPeakRunTotal() {
        return railPeakRunTotal;
    }

    public void setRailPeakRunTotal(Integer railPeakRunTotal) {
        this.railPeakRunTotal = railPeakRunTotal;
    }

    public String getRouteId() {
        return routeId;
    }

    public void setRouteId(String routeId) {
        this.routeId = routeId;
    }

    public Integer getRunningTotal() {
        return runningTotal;
    }

    public void setRunningTotal(Integer runningTotal) {
        this.runningTotal = runningTotal;
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

    public List<TapDTO> getTaps() {
        return taps;
    }

    public void setTaps(List<TapDTO> taps) {
        this.taps = taps;
    }

    public Boolean getTransferDiscountsAppliedFlag() {
        return transferDiscountsAppliedFlag;
    }

    public void setTransferDiscountsAppliedFlag(Boolean transferDiscountsAppliedFlag) {
        this.transferDiscountsAppliedFlag = transferDiscountsAppliedFlag;
    }

    public Date getTrafficOn() {
        return trafficOn;
    }

    public void setTrafficOn(Date trafficOn) {
        this.trafficOn = trafficOn;
    }

    public Boolean getTravelCardUsed() {
        return travelCardUsed;
    }

    public void setTravelCardUsed(Boolean travelCardUsed) {
        this.travelCardUsed = travelCardUsed;
    }

    public String getTravelType() {
        return travelType;
    }

    public void setTravelType(String travelType) {
        this.travelType = travelType;
    }

    public Date getTransactionAt() {
        return transactionAt;
    }

    public void setTransactionAt(Date transactionAt) {
        this.transactionAt = transactionAt;
    }

    public Integer getTransactionLocation() {
        return transactionLocation;
    }

    public void setTransactionLocation(Integer transactionLocation) {
        this.transactionLocation = transactionLocation;
    }

    public String getValidTicketOnCMS() {
        return validTicketOnCMS;
    }

    public void setValidTicketOnCMS(String validTicketOnCMS) {
        this.validTicketOnCMS = validTicketOnCMS;
    }

    public Integer getZonalIndicator() {
        return zonalIndicator;
    }

    public void setZonalIndicator(Integer zonalIndicator) {
        this.zonalIndicator = zonalIndicator;
    }

    public JourneyDisplayDTO getJourneyDisplay() {
        return journeyDisplay;
    }

    public void setJourneyDisplay(JourneyDisplayDTO journeyDisplay) {
        this.journeyDisplay = journeyDisplay;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        JourneyDTO that = (JourneyDTO) object;

        return new EqualsBuilder().append(addedStoredValueBalance, that.addedStoredValueBalance)
                .append(agentNumber, that.agentNumber).append(autoCompletionFlag, that.autoCompletionFlag)
                .append(bestValueActualFare, that.bestValueActualFare)
                .append(bestValueCappingScheme, that.bestValueCappingScheme).append(busOffPeakRunTotal, that.busOffPeakRunTotal)
                .append(busPeakRunTotal, that.busPeakRunTotal).append(cardDiscountId, that.cardDiscountId)
                .append(cardPassengerTypeId, that.cardPassengerTypeId).append(cardType, that.cardType)
                .append(chargeDescription, that.chargeDescription).append(concessionNarrative, that.concessionNarrative)
                .append(dailyCappingFlag, that.dailyCappingFlag).append(discountedFare, that.discountedFare)
                .append(discountNarrative, that.discountNarrative).append(exitAt, that.exitAt)
                .append(exitLocation, that.exitLocation).append(journeyId, that.journeyId).append(innerZone, that.innerZone)
                .append(modeOfTravel, that.modeOfTravel).append(narrative, that.narrative).append(offPeakCap, that.offPeakCap)
                .append(osi, that.osi).append(outerZone, that.outerZone).append(payAsYouGoUsed, that.payAsYouGoUsed)
                .append(paymentMethodCode, that.paymentMethodCode).append(peakCap, that.peakCap).append(peakFare, that.peakFare)
                .append(prestigeId, that.prestigeId).append(productCode, that.productCode)
                .append(productExpiresOn, that.productExpiresOn).append(productStartsOn, that.productStartsOn)
                .append(productTimeValidityCode, that.productTimeValidityCode)
                .append(productZonalValidityCode, that.productZonalValidityCode)
                .append(pseudoTransactionTypeId, that.pseudoTransactionTypeId)
                .append(railOffPeakMaxOuterZone, that.railOffPeakMaxOuterZone)
                .append(railOffPeakMinInnerZone, that.railOffPeakMinInnerZone)
                .append(railOffPeakRunTotal, that.railOffPeakRunTotal).append(railPeakMaxOuterZone, that.railPeakMaxOuterZone)
                .append(railPeakMinInnerZone, that.railPeakMinInnerZone).append(railPeakRunTotal, that.railPeakRunTotal)
                .append(routeId, that.routeId).append(runningTotal, that.runningTotal).append(suppressCode, that.suppressCode)
                .append(storedValueBalance, that.storedValueBalance)
                .append(new HashSet<TapDTO>(taps), new HashSet<TapDTO>(that.taps))
                .append(transferDiscountsAppliedFlag, that.transferDiscountsAppliedFlag).append(trafficOn, that.trafficOn)
                .append(travelCardUsed, that.travelCardUsed).append(travelType, that.travelType)
                .append(transactionAt, that.transactionAt).append(transactionLocation, that.transactionLocation)
                .append(validTicketOnCMS, that.validTicketOnCMS).append(zonalIndicator, that.zonalIndicator).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.JOURNEY_DTO.initialiser(), HashCodeSeed.JOURNEY_DTO.multiplier())
                .append(addedStoredValueBalance).append(agentNumber).append(autoCompletionFlag).append(bestValueActualFare)
                .append(bestValueCappingScheme).append(busOffPeakRunTotal).append(busPeakRunTotal).append(cardDiscountId)
                .append(cardPassengerTypeId).append(cardType).append(chargeDescription).append(concessionNarrative)
                .append(dailyCappingFlag).append(discountedFare).append(discountNarrative).append(exitAt).append(exitLocation)
                .append(journeyId).append(innerZone).append(modeOfTravel).append(narrative).append(offPeakCap).append(osi)
                .append(outerZone).append(payAsYouGoUsed).append(paymentMethodCode).append(peakCap).append(peakFare)
                .append(prestigeId).append(productCode).append(productExpiresOn).append(productStartsOn)
                .append(productTimeValidityCode).append(productZonalValidityCode).append(pseudoTransactionTypeId)
                .append(railOffPeakMaxOuterZone).append(railOffPeakMinInnerZone).append(railOffPeakRunTotal)
                .append(railPeakMaxOuterZone).append(railPeakMinInnerZone).append(railPeakRunTotal).append(routeId)
                .append(runningTotal).append(suppressCode).append(storedValueBalance).append(new HashSet<TapDTO>(taps))
                .append(transferDiscountsAppliedFlag).append(trafficOn).append(travelCardUsed).append(travelType)
                .append(transactionAt).append(transactionLocation).append(validTicketOnCMS).append(zonalIndicator).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("addedStoredValueBalance", addedStoredValueBalance)
                .append("agentNumber", agentNumber).append("autoCompletionFlag", autoCompletionFlag)
                .append("bestValueActualFare", bestValueActualFare).append("bestValueCappingScheme", bestValueCappingScheme)
                .append("busOffPeakRunTotal", busOffPeakRunTotal).append("busPeakRunTotal", busPeakRunTotal)
                .append("cardDiscountId", cardDiscountId).append("cardPassengerTypeId", cardPassengerTypeId)
                .append("cardType", cardType).append("chargeDescription", chargeDescription)
                .append("concessionNarrative", concessionNarrative).append("dailyCappingFlag", dailyCappingFlag)
                .append("discountedFare", discountedFare).append("discountNarrative", discountNarrative)
                .append("exitAt", exitAt).append("exitLocation", exitLocation).append("journeyId", journeyId)
                .append("innerZone", innerZone).append("modeOfTravel", modeOfTravel).append("narrative", narrative)
                .append("offPeakCap", offPeakCap).append("osi", osi).append("outerZone", outerZone)
                .append("payAsYouGoUsed", payAsYouGoUsed).append("paymentMethodCode", paymentMethodCode)
                .append("peakCap", peakCap).append("peakFare", peakFare).append("prestigeId", prestigeId)
                .append("productCode", productCode).append("productExpiresOn", productExpiresOn)
                .append("productStartsOn", productStartsOn).append("productTimeValidityCode", productTimeValidityCode)
                .append("productZonalValidityCode", productZonalValidityCode)
                .append("pseudoTransactionTypeId", pseudoTransactionTypeId)
                .append("railOffPeakMaxOuterZone", railOffPeakMaxOuterZone)
                .append("railOffPeakMinInnerZone", railOffPeakMinInnerZone).append("railOffPeakRunTotal", railOffPeakRunTotal)
                .append("railPeakMaxOuterZone", railPeakMaxOuterZone).append("railPeakMinInnerZone", railPeakMinInnerZone)
                .append("railPeakRunTotal", railPeakRunTotal).append("routeId", routeId).append("runningTotal", runningTotal)
                .append("suppressCode", suppressCode).append("storedValueBalance", storedValueBalance).append("taps", taps)
                .append("transferDiscountsAppliedFlag", transferDiscountsAppliedFlag).append("trafficOn", trafficOn)
                .append("travelCardUsed", travelCardUsed).append("travelType", travelType)
                .append("transactionAt", transactionAt).append("transactionLocation", transactionLocation)
                .append("validTicketOnCMS", validTicketOnCMS).append("zonalIndicator", zonalIndicator).toString();
    }
}
