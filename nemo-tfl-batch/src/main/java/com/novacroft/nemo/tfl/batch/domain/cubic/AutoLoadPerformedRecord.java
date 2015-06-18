package com.novacroft.nemo.tfl.batch.domain.cubic;

import com.novacroft.nemo.tfl.batch.domain.ImportRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.Date;

/**
 * Represents a record from the CUBIC Auto Loads Performed batch file
 */
public class AutoLoadPerformedRecord implements ImportRecord, Serializable {
    private String prestigeId;
    private Integer pickUpLocation;
    private String busRouteId;
    private Date pickUpTime;
    private Integer autoLoadConfiguration;
    private Integer topUpAmountAdded;
    private Integer currency = 0;

    public AutoLoadPerformedRecord() {
    }

    public AutoLoadPerformedRecord(String prestigeId, Integer pickUpLocation, String busRouteId, Date pickUpTime,
                                   Integer autoLoadConfiguration, Integer topUpAmountAdded, Integer currency) {
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

    public Integer getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Integer pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public String getBusRouteId() {
        return busRouteId;
    }

    public void setBusRouteId(String busRouteId) {
        this.busRouteId = busRouteId;
    }

    public Date getPickUpTime() {
        return pickUpTime;
    }

    public void setPickUpTime(Date pickUpTime) {
        this.pickUpTime = pickUpTime;
    }

    public Integer getAutoLoadConfiguration() {
        return autoLoadConfiguration;
    }

    public void setAutoLoadConfiguration(Integer autoLoadConfiguration) {
        this.autoLoadConfiguration = autoLoadConfiguration;
    }

    public Integer getTopUpAmountAdded() {
        return topUpAmountAdded;
    }

    public void setTopUpAmountAdded(Integer topUpAmountAdded) {
        this.topUpAmountAdded = topUpAmountAdded;
    }

    public Integer getCurrency() {
        return currency;
    }

    public void setCurrency(Integer currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AutoLoadPerformedRecord that = (AutoLoadPerformedRecord) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(pickUpLocation, that.pickUpLocation)
                .append(busRouteId, that.busRouteId).append(pickUpTime, that.pickUpTime)
                .append(autoLoadConfiguration, that.autoLoadConfiguration).append(topUpAmountAdded, that.topUpAmountAdded)
                .append(currency, that.currency).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(55, 77).append(prestigeId).append(pickUpLocation).append(busRouteId).append(pickUpTime)
                .append(autoLoadConfiguration).append(topUpAmountAdded).append(currency).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("pickUpLocation", pickUpLocation)
                .append("busRouteId", busRouteId).append("pickUpTime", pickUpTime)
                .append("autoLoadConfiguration", autoLoadConfiguration).append("topUpAmountAdded", topUpAmountAdded)
                .append("currency", currency).toString();
    }
}
