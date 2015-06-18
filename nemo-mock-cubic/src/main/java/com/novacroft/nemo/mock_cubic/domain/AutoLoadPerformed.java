package com.novacroft.nemo.mock_cubic.domain;

/**
 * Represents a record from the CUBIC Auto Loads Performed batch file
 */
public class AutoLoadPerformed {
    private String prestigeId;
    private String pickUpLocation;
    private String busRouteId = "0";
    private String pickUpTime;
    private String autoLoadConfiguration;
    private String topUpAmountAdded;
    private String currency = "0";

    public AutoLoadPerformed() {

    }

    public AutoLoadPerformed(String prestigeId, String pickUpLocation, String busRouteId, String pickUpTime,
                             String autoLoadConfiguration, String topUpAmountAdded, String currency) {
        this.prestigeId = prestigeId;
        this.pickUpLocation = pickUpLocation;
        this.busRouteId = busRouteId;
        this.pickUpTime = pickUpTime;
        this.autoLoadConfiguration = autoLoadConfiguration;
        this.topUpAmountAdded = topUpAmountAdded;
        this.currency = currency;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public String getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(String pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public String getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(String pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public String getAutoLoadConfiguration() {
        return autoLoadConfiguration;
    }

    public void setAutoLoadConfiguration(String autoLoadConfiguration) {
        this.autoLoadConfiguration = autoLoadConfiguration;
    }

    public String getTopUpAmountAdded() {
        return topUpAmountAdded;
    }

    public void setTopUpAmountAdded(String topUpAmountAdded) {
        this.topUpAmountAdded = topUpAmountAdded;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
