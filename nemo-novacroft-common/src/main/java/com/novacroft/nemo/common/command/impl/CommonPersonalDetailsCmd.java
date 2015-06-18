package com.novacroft.nemo.common.command.impl;

import com.novacroft.nemo.common.transfer.CountryDTO;

/**
 * Personal details command (MVC "view") class common definition.
 */
public class CommonPersonalDetailsCmd {
    

    public static final String FIELD_HOMEPHONE = "homePhone";
    public static final String FIELD_MOBILEPHONE = "mobilePhone";
    public static final String FIELD_EMAILADDRESS = "emailAddress";
    
    protected Long webAccountId;
    protected Long customerId;
    protected Long addressId;
    protected String title;
    protected String firstName;
    protected String initials;
    protected String lastName;
    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected String county;
    protected String postcode;
    protected CountryDTO country;
    protected String homePhone;
    protected String mobilePhone;
    protected String emailAddress;
    protected Long homePhoneContactId;
    protected Long mobilePhoneContactId;
    protected Long customerPreferencesId;

    public Long getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Long webAccountId) {
        this.webAccountId = webAccountId;
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

    public String getHouseNameNumber() {
        return houseNameNumber;
    }

    public void setHouseNameNumber(String houseNameNumber) {
        this.houseNameNumber = houseNameNumber;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode.toUpperCase();
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public Long getHomePhoneContactId() {
        return homePhoneContactId;
    }

    public void setHomePhoneContactId(Long homePhoneContactId) {
        this.homePhoneContactId = homePhoneContactId;
    }

    public Long getMobilePhoneContactId() {
        return mobilePhoneContactId;
    }

    public void setMobilePhoneContactId(Long mobilePhoneContactId) {
        this.mobilePhoneContactId = mobilePhoneContactId;
    }

    public Long getCustomerPreferencesId() {
        return customerPreferencesId;
    }

    public void setCustomerPreferencesId(Long customerPreferencesId) {
        this.customerPreferencesId = customerPreferencesId;
    }
}