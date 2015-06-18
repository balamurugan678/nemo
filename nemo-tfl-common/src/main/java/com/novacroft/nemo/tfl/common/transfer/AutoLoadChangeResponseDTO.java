package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Transfer class that represents a CUBIC Change Autoload Configuration Response - caters for successful and failed requests
 */
public class AutoLoadChangeResponseDTO {
    protected String prestigeId;
    protected Integer requestSequenceNumber;
    protected Integer autoLoadState;
    protected Long pickUpLocation;
    protected Integer availableSlots;
    protected Integer errorCode;
    protected String errorDescription;

    public AutoLoadChangeResponseDTO() {
    }

    public AutoLoadChangeResponseDTO(String prestigeId, Integer requestSequenceNumber, Integer autoLoadState,
                                     Long pickUpLocation, Integer availableSlots) {
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.autoLoadState = autoLoadState;
        this.pickUpLocation = pickUpLocation;
        this.availableSlots = availableSlots;
    }

    public AutoLoadChangeResponseDTO(Integer errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
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

    public Long getPickUpLocation() {
        return pickUpLocation;
    }

    public void setPickUpLocation(Long pickUpLocation) {
        this.pickUpLocation = pickUpLocation;
    }

    public Integer getAvailableSlots() {
        return availableSlots;
    }

    public void setAvailableSlots(Integer availableSlots) {
        this.availableSlots = availableSlots;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorDescription() {
        return errorDescription;
    }

    public void setErrorDescription(String errorDescription) {
        this.errorDescription = errorDescription;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        AutoLoadChangeResponseDTO that = (AutoLoadChangeResponseDTO) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(autoLoadState, that.autoLoadState)
                .append(requestSequenceNumber, that.requestSequenceNumber).append(pickUpLocation, that.pickUpLocation)
                .append(autoLoadState, that.autoLoadState).append(availableSlots, that.availableSlots)
                .append(errorCode, that.errorCode).append(errorDescription, that.errorDescription).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.AUTO_LOAD_CHANGE_RESPONSE_DTO.initialiser(),
                HashCodeSeed.AUTO_LOAD_CHANGE_RESPONSE_DTO.multiplier()).append(prestigeId).append(autoLoadState)
                .append(requestSequenceNumber).append(pickUpLocation).append(autoLoadState).append(availableSlots)
                .append(errorCode).append(errorDescription).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("autoLoadState", autoLoadState)
                .append("requestSequenceNumber", requestSequenceNumber).append("pickUpLocation", pickUpLocation)
                .append("autoLoadState", autoLoadState).append("availableSlots", availableSlots).append("errorCode", errorCode)
                .append("errorDescription", errorDescription).toString();
    }
}
