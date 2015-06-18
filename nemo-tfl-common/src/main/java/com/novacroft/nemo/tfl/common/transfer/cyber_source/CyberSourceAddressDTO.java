package com.novacroft.nemo.tfl.common.transfer.cyber_source;

/**
 * CyberSource address transfer class
 */
public class CyberSourceAddressDTO {
    protected String line1;
    protected String line2;
    protected String city;
    protected String postalCode;
    protected String state;
    protected String country;

    public CyberSourceAddressDTO() {
    }

    public CyberSourceAddressDTO(String line1, String line2, String city, String postalCode, String country) {
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.postalCode = postalCode;
        this.country = country;
    }

    public String getLine1() {
        return line1;
    }

    public void setLine1(String line1) {
        this.line1 = line1;
    }

    public String getLine2() {
        return line2;
    }

    public void setLine2(String line2) {
        this.line2 = line2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
