package com.novacroft.nemo.common.command.impl;

import com.novacroft.nemo.common.command.AddressCmd;
import com.novacroft.nemo.common.command.CommonPostcodeCmd;
import com.novacroft.nemo.common.command.ContactDetailsCmd;
import com.novacroft.nemo.common.command.CustomerNameCmd;
import com.novacroft.nemo.common.command.OysterCardCmd;
import com.novacroft.nemo.common.transfer.CountryDTO;

/**
 * Order card command (MVC "view") class common definition.
 */
public class CommonOrderCardCmd
        implements AddressCmd, CommonPostcodeCmd, ContactDetailsCmd, CustomerNameCmd, OysterCardCmd {

    protected String cardNumber;

    protected String title;
    protected String firstName;
    protected String initials;
    protected String lastName;
    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected String county;
    protected CountryDTO country;
    protected String postcode;
    protected String homePhone;
    protected String mobilePhone;
    protected String emailAddress;
    protected String confirmEmailAddress;
    protected String username;
    protected String newPassword;
    protected String newPasswordConfirmation;
    protected boolean invalidPostCodeCheckFlag = false;
    
    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
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

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
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

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
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

    public String getConfirmEmailAddress() {
        return confirmEmailAddress;
    }

    public void setConfirmEmailAddress(String confirmEmailAddress) {
        this.confirmEmailAddress = confirmEmailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordConfirmation() {
        return newPasswordConfirmation;
    }

    public void setNewPasswordConfirmation(String newPasswordConfirmation) {
        this.newPasswordConfirmation = newPasswordConfirmation;
    }

	public boolean isInvalidPostCodeCheckFlag() {
		return invalidPostCodeCheckFlag;
	}

	public void setInvalidPostCodeCheckFlag(boolean invalidPostCodeCheckFlag) {
		this.invalidPostCodeCheckFlag = invalidPostCodeCheckFlag;
	}
}