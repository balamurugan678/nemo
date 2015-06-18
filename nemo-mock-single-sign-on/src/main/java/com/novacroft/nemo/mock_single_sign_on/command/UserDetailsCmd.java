package com.novacroft.nemo.mock_single_sign_on.command;

import com.novacroft.nemo.common.transfer.CountryDTO;

public class UserDetailsCmd {
    protected String username;
    protected String password;
    protected Long customerId;
    protected String title;
    protected String firstName;
    protected String middleName;
    protected String lastName;
    protected String homePhoneNumber;
    protected String mobilePhoneNumber;
    protected String workPhoneNumber;
    protected String emailAddress;
    protected String securityQuestion;
    protected String securityAnswer;
    protected Boolean tflMarketingOptIn;
    protected Boolean tocMarketingOptIn;

    protected String houseName;
    protected String houseNo;
    protected String streetName;
    protected String city;
    protected String county;
    protected String postCode;
    protected CountryDTO country;
    protected String addressType;

    protected String status;
    protected String returnURL;

    public UserDetailsCmd() {

    }

    public UserDetailsCmd(String username, String password, Long customerId, String title, String firstName, String middleName, String lastName,
                    String homePhoneNumber, String mobilePhoneNumber, String workPhoneNumber, String emailAddress, String securityQuestion,
                    String securityAnswer, Boolean tflMarketingOptIn, Boolean tocMarketingOptIn, String houseName, String houseNo, String streetName,
                    String city, String county, String postCode, CountryDTO countryDTO, String addressType, String status) {
        this.username = username;
        this.password = password;
        this.customerId = customerId;
        this.title = title;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.homePhoneNumber = homePhoneNumber;
        this.mobilePhoneNumber = mobilePhoneNumber;
        this.workPhoneNumber = workPhoneNumber;
        this.emailAddress = emailAddress;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.tflMarketingOptIn = tflMarketingOptIn;
        this.tocMarketingOptIn = tocMarketingOptIn;
        this.houseName = houseName;
        this.houseNo = houseNo;
        this.streetName = streetName;
        this.city = city;
        this.county = county;
        this.postCode = postCode;
        this.country = countryDTO;
        this.addressType = addressType;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getHomePhoneNumber() {
        return homePhoneNumber;
    }

    public void setHomePhoneNumber(String homePhoneNumber) {
        this.homePhoneNumber = homePhoneNumber;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getWorkPhoneNumber() {
        return workPhoneNumber;
    }

    public void setWorkPhoneNumber(String workPhoneNumber) {
        this.workPhoneNumber = workPhoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public Boolean getTflMarketingOptIn() {
        return tflMarketingOptIn;
    }

    public void setTflMarketingOptIn(Boolean tflMarketingOptIn) {
        this.tflMarketingOptIn = tflMarketingOptIn;
    }

    public Boolean getTocMarketingOptIn() {
        return tocMarketingOptIn;
    }

    public void setTocMarketingOptIn(Boolean tocMarketingOptIn) {
        this.tocMarketingOptIn = tocMarketingOptIn;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getAddressType() {
        return addressType;
    }

    public void setAddressType(String addressType) {
        this.addressType = addressType;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getReturnURL() {
        return returnURL;
    }

    public void setReturnURL(String returnURL) {
        this.returnURL = returnURL;
    }

}
