package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;
import com.novacroft.nemo.common.transfer.AddressDTO;

public class PayeePaymentDTO extends AbstractBaseDTO {

    protected AddressDTO payeeAddress;
    protected boolean overwriteAddress;
    protected String payeeSortCode;
    protected String payeeAccountNumber;
    protected String title;
    protected String firstName;
    protected String initials;
    protected String lastName;
    protected String paymentType;
    protected Integer webCreditAvailableAmount;
    protected String targetCardNumber;
    protected Long stationId;
    protected Boolean isEdited = Boolean.FALSE;

    public PayeePaymentDTO() {
    }

    public PayeePaymentDTO(AddressDTO payeeAddress, boolean overwriteAddress, String payeeSortCode, String payeeAccountNumber,
                           String title, String firstName, String initials, String lastName, String paymentType,
                           Integer webCreditAvailableAmount, String targetCardNumber, Long stationId, Boolean isEdited) {
        this.payeeAddress = payeeAddress;
        this.overwriteAddress = overwriteAddress;
        this.payeeSortCode = payeeSortCode;
        this.payeeAccountNumber = payeeAccountNumber;
        this.title = title;
        this.firstName = firstName;
        this.initials = initials;
        this.lastName = lastName;
        this.paymentType = paymentType;
        this.webCreditAvailableAmount = webCreditAvailableAmount;
        this.targetCardNumber = targetCardNumber;
        this.stationId = stationId;
        this.isEdited = isEdited;
    }

    public AddressDTO getPayeeAddress() {
        return payeeAddress;
    }

    public void setPayeeAddress(AddressDTO payeeAddress) {
        this.payeeAddress = payeeAddress;
    }

    public boolean isOverwriteAddress() {
        return overwriteAddress;
    }

    public void setOverwriteAddress(boolean overwriteAddress) {
        this.overwriteAddress = overwriteAddress;
    }

    public String getPayeeSortCode() {
        return payeeSortCode;
    }

    public void setPayeeSortCode(String payeeSortCode) {
        this.payeeSortCode = payeeSortCode;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Integer getWebCreditAvailableAmount() {
        return webCreditAvailableAmount;
    }

    public void setWebCreditAvailableAmount(Integer webCreditAvailableAmount) {
        this.webCreditAvailableAmount = webCreditAvailableAmount;
    }

    public String getTargetCardNumber() {
        return targetCardNumber;
    }

    public void setTargetCardNumber(String targetCardNumber) {
        this.targetCardNumber = targetCardNumber;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public Boolean getIsEdited() {
        return isEdited;
    }

    public void setIsEdited(Boolean isEditedAlready) {
        this.isEdited = isEditedAlready;
    }
}