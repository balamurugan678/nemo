package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Common customer preferences domain definition
 */
@Audited
@MappedSuperclass()
public class CommonCustomerPreferences extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = 1L;
    protected Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
}
