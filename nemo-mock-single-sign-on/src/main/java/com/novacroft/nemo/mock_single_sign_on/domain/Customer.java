package com.novacroft.nemo.mock_single_sign_on.domain;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private final List<SystemReferences> systemReferences;

    private Boolean tfLMarketingOptIn;

    private String lastName;

    private String workPhoneNumber;

    private Title title;

    private Boolean tocMarketingOptIn;

    private String mobilePhoneNumber;

    private String middleName;

    private String homePhoneNumber;

    private Long customerId;

    private final List<SecurityAnswer> securityAnswers;

    private Address address;

    private String emailAddress;

    private String firstName;

    public Customer() {
        super();
        systemReferences = new ArrayList<SystemReferences>();
        securityAnswers = new ArrayList<SecurityAnswer>();
    }

    public Customer(Long customerId, Title title, String firstName, String middleName, String lastName, Address address, String homePhoneNumber,
                    String mobilePhoneNumber, String workPhoneNumber, String emailAddress, Boolean tfLMarketingOptIn, Boolean tocMarketingOptIn) {
        this.customerId = customerId;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.address = address;
        this.homePhoneNumber = homePhoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.workPhoneNumber = workPhoneNumber;
        this.emailAddress = emailAddress;
        this.tfLMarketingOptIn = tfLMarketingOptIn;
        this.tocMarketingOptIn = tocMarketingOptIn;
        this.systemReferences = new ArrayList<SystemReferences>();
        this.securityAnswers = new ArrayList<SecurityAnswer>();
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

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
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

    public List<SystemReferences> getSystemReferences() {
        return systemReferences;
    }

    public List<SecurityAnswer> getSecurityAnswers() {
        return securityAnswers;
    }

}