package com.novacroft.nemo.mock_cubic.domain.card;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.novacroft.nemo.common.domain.AbstractBaseEntity;


/**
* OysterCard  domain class to hold the domain data.
*/

@Entity
@Table(name = "MOCK_OYSTERCARD")
public class OysterCard extends AbstractBaseEntity {
    
    private static final long serialVersionUID = 1L;
    
    protected String prestigeId;
    protected String photocardNumber;
    protected Long registered;
    protected String passengerType;
    protected Long autoloadState;
    protected Long cardCapability;
    protected Long cardType;
    protected Date cCCLostStolenDateTime;
    protected Long cardDeposit;
    protected String discountEntitleMent1;
    protected Date discountExpiry1;
    protected String discountEntitleMent2;
    protected Date discountExpiry2;
    protected String discountEntitleMent3;
    protected Date discountExpiry3;
    protected String title;
    protected String firstName;
    protected String middleInitial;
    protected String lastName;
    protected String dayTimeTelephoneNumber;
    protected String houseNumber;
    protected String houseName;
    protected String street;
    protected String town;
    protected String county;
    protected String postcode;
    protected String emailAddress;
    protected String password;    
    
    @SequenceGenerator(name = "OYSTERCARD_SEQ", sequenceName = "OYSTERCARD_SEQ", allocationSize = 1)
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "OYSTERCARD_SEQ")
    @Column(name = "ID")
    @Override
    public Long getId() {
      return id;
    }

    public String getPrestigeId() {
        return prestigeId;
    }

    public void setPrestigeId(String prestigeId) {
        this.prestigeId = prestigeId;
    }

    public String getPhotocardNumber() {
        return photocardNumber;
    }

    public void setPhotocardNumber(String photocardNumber) {
        this.photocardNumber = photocardNumber;
    }

    public Long getRegistered() {
        return registered;
    }

    public void setRegistered(Long registered) {
        this.registered = registered;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public Long getAutoloadState() {
        return autoloadState;
    }

    public void setAutoloadState(Long autoloadState) {
        this.autoloadState = autoloadState;
    }

    public Long getCardCapability() {
        return cardCapability;
    }

    public void setCardCapability(Long cardCapability) {
        this.cardCapability = cardCapability;
    }

    public Long getCardType() {
        return cardType;
    }

    public void setCardType(Long cardType) {
        this.cardType = cardType;
    }

    public Date getCccLostStolenDateTime() {
        return cCCLostStolenDateTime;
    }

    public void setCccLostStolenDateTime(Date cCCLostStolenDateTime) {
        this.cCCLostStolenDateTime = cCCLostStolenDateTime;
    }

    public Long getCardDeposit() {
        return cardDeposit;
    }

    public void setCardDeposit(Long cardDeposit) {
        this.cardDeposit = cardDeposit;
    }

    public String getDiscountEntitlement1() {
        return discountEntitleMent1;
    }

    public void setDiscountEntitlement1(String discountEntitleMent1) {
        this.discountEntitleMent1 = discountEntitleMent1;
    }

    public Date getDiscountExpiry1() {
        return discountExpiry1;
    }

    public void setDiscountExpiry1(Date discountExpiry1) {
        this.discountExpiry1 = discountExpiry1;
    }

    public String getDiscountEntitlement2() {
        return discountEntitleMent2;
    }

    public void setDiscountEntitlement2(String discountEntitleMent2) {
        this.discountEntitleMent2 = discountEntitleMent2;
    }

    public Date getDiscountExpiry2() {
        return discountExpiry2;
    }

    public void setDiscountExpiry2(Date discountExpiry2) {
        this.discountExpiry2 = discountExpiry2;
    }

    public String getDiscountentitlement3() {
        return discountEntitleMent3;
    }

    public void setDiscountentitlement3(String discountEntitleMent3) {
        this.discountEntitleMent3 = discountEntitleMent3;
    }

    public Date getDiscountExpiry3() {
        return discountExpiry3;
    }

    public void setDiscountExpiry3(Date discountExpiry3) {
        this.discountExpiry3 = discountExpiry3;
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

    public String getMiddleInitial() {
        return middleInitial;
    }

    public void setMiddleInitial(String middleInitial) {
        this.middleInitial = middleInitial;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDayTimePhoneNumber() {
        return dayTimeTelephoneNumber;
    }

    public void setDayTimePhoneNumber(String dayTimeTelephoneNumber) {
        this.dayTimeTelephoneNumber = dayTimeTelephoneNumber;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
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
        this.postcode = postcode;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
