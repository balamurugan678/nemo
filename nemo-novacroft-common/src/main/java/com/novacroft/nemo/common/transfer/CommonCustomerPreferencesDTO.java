package com.novacroft.nemo.common.transfer;

/**
 * Customer preferences transfer class common definition
 */
public class CommonCustomerPreferencesDTO extends AbstractBaseDTO {
    protected Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
