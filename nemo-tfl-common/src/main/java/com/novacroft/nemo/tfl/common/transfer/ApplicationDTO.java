package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.tfl.common.domain.Card;
import com.novacroft.nemo.tfl.common.domain.Event;

import java.util.Date;
import java.util.List;

/**
 * DTO to hold all the information that is shown on the innovator - application page.
 */
public class ApplicationDTO {
    protected Integer customerId;
    protected String securityOption;
    protected String securityPassword;
    protected Date dateOfBirth;
    protected String title;
    protected String firstName;
    protected String initials;
    protected String lastName;
    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected String county;
    protected String country;
    protected String postcode;
    protected String email;
    protected String telephone;
    protected Integer notflserviceinfo;
    protected Integer optouttoc;
    protected List<Card> oysterCards;
    protected List<Event> events;
    protected String notes;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getSecurityOption() {
        return securityOption;
    }

    public void setSecurityOption(String securityOption) {
        this.securityOption = securityOption;
    }

    public String getSecurityPassword() {
        return securityPassword;
    }

    public void setSecurityPassword(String securityPassword) {
        this.securityPassword = securityPassword;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getNotflserviceinfo() {
        return notflserviceinfo;
    }

    public void setNotflserviceinfo(Integer notflserviceinfo) {
        this.notflserviceinfo = notflserviceinfo;
    }

    public Integer getOptouttoc() {
        return optouttoc;
    }

    public void setOptouttoc(Integer optouttoc) {
        this.optouttoc = optouttoc;
    }

    public List<Card> getOysterCards() {
        return oysterCards;
    }

    public void setOysterCards(List<Card> oysterCards) {
        this.oysterCards = oysterCards;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
