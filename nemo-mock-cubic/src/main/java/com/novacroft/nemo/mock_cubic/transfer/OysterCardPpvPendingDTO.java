package com.novacroft.nemo.mock_cubic.transfer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * OysterCardPpvPendingDTO transfer implementation.
 * 
 */

public class OysterCardPpvPendingDTO extends AbstractBaseDTO {

    private static final int HASH_INITIAL = 179;
    private static final int HASH_MULTIPLIER = 181;

    protected String prestigeId;
    protected Long requestSequenceNumber;
    protected String realtimeFlag;
    protected Long prePayValue;
    protected Long currency;
    protected Long pickupLocation;

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public Long getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Long requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public String getRealtimeFlag() {
        return realtimeFlag;
    }

    public void setRealtimeFlag(String realtimeFlag) {
        this.realtimeFlag = realtimeFlag;
    }

    public Long getPrePayValue() {
        return prePayValue;
    }

    public void setPrePayValue(Long prePayValue) {
        this.prePayValue = prePayValue;
    }

    public Long getCurrency() {
        return currency;
    }

    public void setCurrency(Long currency) {
        this.currency = currency;
    }

    public Long getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(Long pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        OysterCardPpvPendingDTO that = (OysterCardPpvPendingDTO) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(requestSequenceNumber, that.requestSequenceNumber)
                        .append(realtimeFlag, that.realtimeFlag).append(prePayValue, that.prePayValue).append(currency, that.currency)
                        .append(pickupLocation, that.pickupLocation).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HASH_INITIAL, HASH_MULTIPLIER).append(prestigeId).append(requestSequenceNumber).append(realtimeFlag)
                        .append(prePayValue).append(currency).append(pickupLocation).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append(prestigeId).append(requestSequenceNumber).append(realtimeFlag).append(prePayValue).append(currency)
                        .append(pickupLocation).toString();
    }
}
