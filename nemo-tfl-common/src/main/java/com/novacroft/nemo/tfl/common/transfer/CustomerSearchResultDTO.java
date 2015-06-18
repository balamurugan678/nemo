package com.novacroft.nemo.tfl.common.transfer;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatAddress;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;
import static org.apache.commons.lang3.StringUtils.join;

public class CustomerSearchResultDTO {
    protected Long id;
    protected String firstName;
    protected String lastName;
    protected String oysterNumber;
    protected String status;
    protected String houseNameNumber;
    protected String street;
    protected String town;
    protected String county;
    protected String country;
    protected String postcode;
    protected Integer calls = 0;
    protected String name;
    protected String address;
    // C = Customer, O = Order
    protected String resultType;

    public CustomerSearchResultDTO() {
    }

    public CustomerSearchResultDTO(Long id, String firstName, String lastName, String oysterNumber, String status,
                                   String houseNameNumber, String street, String town, String county, String country,
                                   String postcode) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.oysterNumber = oysterNumber;
        this.status = status;
        this.houseNameNumber = houseNameNumber;
        this.street = street;
        this.town = town;
        this.county = county;
        this.country = country;
        this.postcode = postcode;
        setName();
        setAddress();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
        setName();
    }

    public String getOysterNumber() {
        return oysterNumber;
    }

    public void setOysterNumber(String oysterNumber) {
        this.oysterNumber = oysterNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        setAddress();
    }

    public Integer getCalls() {
        return calls;
    }

    public void setCalls(Integer calls) {
        this.calls = calls;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setName() {
        this.name = formatName(this.firstName, this.lastName);
    }

    public String getResultType() {
        return resultType;
    }

    public void setResultType(String resultType) {
        this.resultType = resultType;
    }

    public void setAddress() {
        this.address =
                join(formatAddress(this.houseNameNumber, this.street, this.town, this.postcode, this.country).toArray(), ", ");
    }
}
