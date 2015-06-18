package com.novacroft.nemo.tfl.common.transfer;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import com.novacroft.nemo.common.constant.CommonHashCodeSeed;
import com.novacroft.nemo.common.transfer.CommonContactDTO;

/**
 * TfL contact transfer implementation
 */

public class ContactDTO extends CommonContactDTO {

    private static final long serialVersionUID = -855438001733872596L;

    public ContactDTO() {
        super();
    }
	
    public ContactDTO(String name, String value, String type, Long customerId) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.customerId = customerId;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        
        if (obj == this) {
            return true;
        }
        
        if (obj.getClass() != getClass()) {
            return false;
        }
    
        ContactDTO other = (ContactDTO) obj;
        return new EqualsBuilder()
                    .append(customerId, other.getCustomerId())
                    .append(name, other.getName())
                    .append(type, other.getType())
                    .append(value, other.getValue())
                    .isEquals();
    }
    
    @Override
    public int hashCode() {
        return new HashCodeBuilder(CommonHashCodeSeed.CONTACT_DTO.initialiser(), CommonHashCodeSeed.CONTACT_DTO.multiplier())
                    .append(customerId)
                    .append(name)
                    .append(type)
                    .append(value)
                    .toHashCode();
    }
    
}
