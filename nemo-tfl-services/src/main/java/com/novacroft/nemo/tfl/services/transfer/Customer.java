package com.novacroft.nemo.tfl.services.transfer;


public class Customer extends AbstractBase {
    private Long id;
    private String title;
    private String firstName;
    private String initials;
    private String lastName;
    private String houseNameNumber;
    private String street;
    private String town;
    private String county;
    private String country;
    private String postcode;
    private String homePhone;
    private String mobilePhone;
    private String emailAddress;
    private String username;

    public Customer() {
    }

    public Customer(Long id, String title, String firstName, String initials, String lastName, String houseNameNumber, String street, String town,
                    String county, String country, String postcode, String homePhone, String mobilePhone, String emailAddress, String username) {
        this.id = id;
        this.title = title;
        this.firstName = firstName;
        this.initials = initials;
        this.lastName = lastName;
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.country = country;
        this.county = county;
        this.postcode = postcode;
        this.homePhone = homePhone;
        this.mobilePhone = mobilePhone;
        this.emailAddress = emailAddress;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
