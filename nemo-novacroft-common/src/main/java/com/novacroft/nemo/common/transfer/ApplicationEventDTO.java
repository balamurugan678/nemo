package com.novacroft.nemo.common.transfer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import com.novacroft.nemo.common.constant.CommonHashCodeSeed;

/**
 * TfL application event transfer implementation
 */
public class ApplicationEventDTO extends CommonApplicationEventDTO {

    public ApplicationEventDTO() {
        super();
    }

    public ApplicationEventDTO(Long eventId, Long customerId, String additionalInformation) {
        super();
        this.eventId = eventId;
        this.customerId = customerId;
        this.additionalInformation = additionalInformation;
    }
    
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        ApplicationEventDTO that = (ApplicationEventDTO) object;
        
        return new EqualsBuilder()
            .append(eventId, that.eventId)
            .append(customerId, that.customerId)
            .append(additionalInformation, that.additionalInformation)
            .append(createdDateTime, that.createdDateTime)
            .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(CommonHashCodeSeed.APPLICATION_EVENT.initialiser(), CommonHashCodeSeed.APPLICATION_EVENT.multiplier())
                .append(eventId).append(customerId).append(additionalInformation).append(createdDateTime).toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).append("eventId", eventId).append("customerId", customerId)
                        .append("additionalInformation", additionalInformation).append("createdDateTime", createdDateTime).toString();
    }
}
