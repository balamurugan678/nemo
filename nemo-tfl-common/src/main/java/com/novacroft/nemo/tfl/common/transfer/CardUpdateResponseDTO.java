package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.constant.HashCodeSeed;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class CardUpdateResponseDTO implements BaseResponseDTO {

    protected String prestigeId;
    protected Integer requestSequenceNumber;
    protected Integer removedRequestSequenceNumber;
    protected Long pickUpLocation;
    protected Integer availableSlots;
    protected Integer errorCode;
    protected String errorDescription;

    public CardUpdateResponseDTO(Integer errorCode, String errorDescription) {
        super();
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public CardUpdateResponseDTO(String prestigeId, Integer requestSequenceNumber, Long pickUpLocation,
                                 Integer availableSlots) {
        super();
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.pickUpLocation = pickUpLocation;
        this.availableSlots = availableSlots;
    }

    public CardUpdateResponseDTO(String prestigeId, Integer requestSequenceNumber, Long pickUpLocation, Integer availableSlots,
                                 Integer errorCode, String errorDescription) {
        super();
        this.prestigeId = prestigeId;
        this.requestSequenceNumber = requestSequenceNumber;
        this.pickUpLocation = pickUpLocation;
        this.availableSlots = availableSlots;
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public CardUpdateResponseDTO() {
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

    public Integer getRemovedRequestSequenceNumber() {
        return removedRequestSequenceNumber;
    }

    public void setRemovedRequestSequenceNumber(Integer removedRequestSequenceNumber) {
        this.removedRequestSequenceNumber = removedRequestSequenceNumber;
    }

    @Override
    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    @Override
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

        CardUpdateResponseDTO that = (CardUpdateResponseDTO) object;

        return new EqualsBuilder().append(prestigeId, that.prestigeId).append(requestSequenceNumber, that.requestSequenceNumber)
                .append(pickUpLocation, that.pickUpLocation).append(availableSlots, that.availableSlots)
                .append(errorCode, that.errorCode).append(errorDescription, that.errorDescription).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(HashCodeSeed.CARD_UPDATE_RESPONSE_DTO.initialiser(),
                HashCodeSeed.CARD_UPDATE_RESPONSE_DTO.multiplier()).append(prestigeId).append(requestSequenceNumber)
                .append(pickUpLocation).append(availableSlots).append(errorCode).append(errorDescription).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("prestigeId", prestigeId).append("requestSequenceNumber", requestSequenceNumber)
                .append("pickUpLocation", pickUpLocation).append("availableSlots", availableSlots)
                .append("errorCode", errorCode).append("errorDescription", errorDescription).toString();
    }

}
