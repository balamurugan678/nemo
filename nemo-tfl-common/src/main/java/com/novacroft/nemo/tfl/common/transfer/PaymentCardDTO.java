package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.common.transfer.AddressDTO;

/**
 * TfL payment card transfer class implementation
 */
public class PaymentCardDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = 3542047213093873025L;
	protected Long customerId;
    protected Long addressId;
    protected String obfuscatedPrimaryAccountNumber;
    protected String token;
    protected String expiryDate;
    protected String firstName;
    protected String lastName;
    protected String status;
    protected String nickName;
    protected AddressDTO addressDTO;
    protected String referenceCode;

    public PaymentCardDTO() {
    }

    public PaymentCardDTO(Long customerId, Long addressId, String obfuscatedPrimaryAccountNumber, String expiryDate,
                          String firstName, String lastName, String token, String referenceCode) {
        this.customerId = customerId;
        this.addressId = addressId;
        this.obfuscatedPrimaryAccountNumber = obfuscatedPrimaryAccountNumber;
        this.token = token;
        this.expiryDate = expiryDate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.referenceCode = referenceCode;
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
    
    public String getExpiryMonth() {
    	return expiryDate.split("-")[0];
    }
    
    public String getExpiryYear() {
    	return expiryDate.split("-")[1];
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

    public AddressDTO getAddressDTO() {
        return addressDTO;
    }

    public void setAddressDTO(AddressDTO addressDTO) {
        this.addressDTO = addressDTO;
    }

    public String getReferenceCode() {
        return referenceCode;
    }

    public void setReferenceCode(String referenceCode) {
        this.referenceCode = referenceCode;
    }
}
