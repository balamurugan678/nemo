package com.novacroft.nemo.tfl.common.transfer.single_sign_on;

import java.util.List;

public class SingleSignOnCustomerDTO {
    private List<SingleSignOnSystemReferencesDTO> systemReferences;
    private Boolean tfLMarketingOptIn;
    private String lastName;
    private String workPhoneNumber;
    private SingleSignOnTitleDTO title;
    private Boolean tocMarketingOptIn;
    private String mobilePhoneNumber;
    private String middleName;
    private String homePhoneNumber;
    private Long customerId;
    private List<SingleSignOnSecurityAnswerDTO> securityAnswers;
    private SingleSignOnAddressDTO address;
    private String emailAddress;
    private String firstName;
    
    public List<SingleSignOnSystemReferencesDTO> getSystemReferences() {
        return systemReferences;
    }
    
    public void setSystemReferences(List<SingleSignOnSystemReferencesDTO> systemReferences) {
        this.systemReferences = systemReferences;
    }

    public Boolean getTfLMarketingOptIn() {
        return tfLMarketingOptIn;
    }

    public void setTfLMarketingOptIn(Boolean tfLMarketingOptIn) {
        this.tfLMarketingOptIn = tfLMarketingOptIn;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public SingleSignOnTitleDTO getTitle() {
        return title;
    }

    public void setTitle(SingleSignOnTitleDTO title) {
        this.title = title;
    }

    public Boolean getTocMarketingOptIn() {
        return tocMarketingOptIn;
    }

    public void setTocMarketingOptIn(Boolean tocMarketingOptIn) {
        this.tocMarketingOptIn = tocMarketingOptIn;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<SingleSignOnSecurityAnswerDTO> getSecurityAnswers() {
        return securityAnswers;
    }

    public void setSecurityAnswers(List<SingleSignOnSecurityAnswerDTO> securityAnswers) {
        this.securityAnswers = securityAnswers;
    }

    public SingleSignOnAddressDTO getAddress() {
        return address;
    }

    public void setAddress(SingleSignOnAddressDTO address) {
        this.address = address;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    
}
