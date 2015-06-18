package com.novacroft.nemo.common.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

/**
 * Common card attributes that will be inherited by all implementations.
 */
@Audited
@MappedSuperclass()
public abstract class CommonCard extends AbstractBaseEntity {
    protected static final String ACTIVE = "Active";
    protected static final String HOTLISED = "Hotlised";
    protected static final long serialVersionUID = -254916044214513917L;
    protected String cardNumber;
    @Column(nullable = false)
    protected Long customerId;
    protected String name;
    protected String mifareNumber;
    protected String status;
    public CommonCard() {
        setCreated();
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMifareNumber() {
        return mifareNumber;
    }

    public void setMifareNumber(String mifareNumber) {
        this.mifareNumber = mifareNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }


}
