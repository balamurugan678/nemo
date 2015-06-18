package com.novacroft.nemo.tfl.common.transfer.oyster_journey_history;

public class TapDisplayDTO {

    protected String nationalLocationName = "";
    protected Boolean locationBusFlag = Boolean.FALSE;
    protected Boolean locationUndergroundFlag = Boolean.FALSE;
    protected Boolean locationNationalRailFlag = Boolean.FALSE;
    protected String transactionTime;
    protected String transactionTypeDescription;
    protected Integer chargeAmount;
    protected Integer creditAmount;
    protected Boolean topUpActivated = Boolean.FALSE;

    public TapDisplayDTO() {
        super();
    }

    public String getNationalLocationName() {
        return nationalLocationName;
    }

    public void setNationalLocationName(String nationalLocationName) {
        this.nationalLocationName = nationalLocationName;
    }

    public Boolean getLocationBusFlag() {
        return locationBusFlag;
    }

    public void setLocationBusFlag(Boolean locationBusFlag) {
        this.locationBusFlag = locationBusFlag;
    }

    public Boolean getLocationUndergroundFlag() {
        return locationUndergroundFlag;
    }

    public void setLocationUndergroundFlag(Boolean locationUndergroundFlag) {
        this.locationUndergroundFlag = locationUndergroundFlag;
    }

    public Boolean getLocationNationalRailFlag() {
        return locationNationalRailFlag;
    }

    public void setLocationNationalRailFlag(Boolean locationNationalRailFlag) {
        this.locationNationalRailFlag = locationNationalRailFlag;
    }

    public String getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(String transactionTime) {
        this.transactionTime = transactionTime;
    }

    public String getTransactionTypeDescription() {
        return transactionTypeDescription;
    }

    public void setTransactionTypeDescription(String transactionTypeDescription) {
        this.transactionTypeDescription = transactionTypeDescription;
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

    public Boolean getTopUpActivated() {
        return topUpActivated;
    }

    public void setTopUpActivated(Boolean topUpActivated) {
        this.topUpActivated = topUpActivated;
    }


}
