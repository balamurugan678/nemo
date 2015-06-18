package com.novacroft.nemo.tfl.common.domain.cubic;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Change Auto Load Configuration CUBIC service success response
 */
public class AutoLoadResponse {
    protected String prestigeId;
    protected Integer requestSequenceNumber;
    protected Integer autoLoadState;
    protected Integer pickupLocation;
    protected Integer availableSlots;

    public AutoLoadResponse() {
    }

    public AutoLoadResponse(String prestigeId, Integer requestSequenceNumber, Integer autoLoadState, Integer pickupLocation,
                            Integer availableSlots) {
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.autoLoadState = autoLoadState;
        this.pickupLocation = pickupLocation;
        this.availableSlots = availableSlots;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }

    public Integer getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Integer pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AutoLoadResponse that = (AutoLoadResponse) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(requestSequenceNumber, that.requestSequenceNumber)
                .append(autoLoadState, that.autoLoadState).append(pickupLocation, that.pickupLocation)
                .append(availableSlots, that.availableSlots).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.AUTO_LOAD_RESPONSE.initialiser(), HashCodeSeed.AUTO_LOAD_RESPONSE.multiplier())
                .append(prestigeId).append(requestSequenceNumber).append(autoLoadState).append(pickupLocation)
                .append(availableSlots).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("requestSequenceNumber", requestSequenceNumber)
                .append("autoLoadState", autoLoadState).append("pickupLocation", pickupLocation)
                .append("availableSlots", availableSlots).toString();
    }
}
