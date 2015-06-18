package com.novacroft.nemo.tfl.common.domain;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.util.Date;

/**
 * Payment card (eg credit card, debit card, etc) entity
 */
@Audited
@Entity
public class PaymentCard extends AbstractBaseEntity {
    @Transient
    private static final long serialVersionUID = -1L;
    protected Long customerId;
    protected Long addressId;
    protected String obfuscatedPrimaryAccountNumber;
    protected String expiryDate;
    protected String firstName;
    protected String lastName;
    protected String token;
    protected String status;
    protected String nickName;
    protected String referenceCode;

    public PaymentCard() {
    }

    public PaymentCard(Long id, String createdUserId, Date createdDateTime, String modifiedUserId, Date modifiedDateTime,
                       Long customerId, Long addressId, String obfuscatedPrimaryAccountNumber, String token, String status,
                       String nickName) {
        this.id = id;
        this.createdUserId = createdUserId;
        this.createdDateTime = createdDateTime;
        this.modifiedUserId = modifiedUserId;
        this.modifiedDateTime = modifiedDateTime;
        this.customerId = customerId;
        this.addressId = addressId;
        this.obfuscatedPrimaryAccountNumber = obfuscatedPrimaryAccountNumber;
        this.token = token;
        this.status = status;
        this.nickName = nickName;
    }

    public PaymentCard(Long customerId, String obfuscatedPrimaryAccountNumber, String token, String createdUserId,
                       Date createdDateTime) {
        this.customerId = customerId;
        this.obfuscatedPrimaryAccountNumber = obfuscatedPrimaryAccountNumber;
        this.token = token;
        this.createdUserId = createdUserId;
        this.createdDateTime = createdDateTime;
    }

    @SequenceGenerator(name = "PaymentCardIdGenerator", sequenceName = "PAYMENTCARD_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PaymentCardIdGenerator")
    @Column(name = "ID")
    @Override
    public Long getId() {
        return id;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getAddressId() {
        return addressId;
    }

    public void setAddressId(Long addressId) {
        this.addressId = addressId;
    }

    public String getObfuscatedPrimaryAccountNumber() {
        return obfuscatedPrimaryAccountNumber;
    }

    public void setObfuscatedPrimaryAccountNumber(String obfuscatedPrimaryAccountNumber) {
        this.obfuscatedPrimaryAccountNumber = obfuscatedPrimaryAccountNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
}
