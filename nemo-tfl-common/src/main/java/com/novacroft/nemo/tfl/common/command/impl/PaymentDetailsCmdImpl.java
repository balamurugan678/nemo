package com.novacroft.nemo.tfl.common.command.impl;

import com.novacroft.nemo.common.transfer.CountryDTO;

/**
 * Command (MVC model) class for payment card details
 */
public class PaymentDetailsCmdImpl {
    protected String billToFirstName;
    protected String billToLastName;
    protected String billToEmail;
    protected String billToAddressLine1;
    protected String billToAddressLine2;
    protected String billToAddressCity;
    protected String billToAddressPostalCode;
    protected CountryDTO billToAddressCountry;

    public String getBillToFirstName() {
        return billToFirstName;
    }

    public void setBillToFirstName(String billToFirstName) {
        this.billToFirstName = billToFirstName;
    }

    public String getBillToLastName() {
        return billToLastName;
    }

    public void setBillToLastName(String billToLastName) {
        this.billToLastName = billToLastName;
    }

    public String getBillToEmail() {
        return billToEmail;
    }

    public void setBillToEmail(String billToEmail) {
        this.billToEmail = billToEmail;
    }

    public String getBillToAddressLine1() {
        return billToAddressLine1;
    }

    public void setBillToAddressLine1(String billToAddressLine1) {
        this.billToAddressLine1 = billToAddressLine1;
    }

    public String getBillToAddressLine2() {
        return billToAddressLine2;
    }

    public void setBillToAddressLine2(String billToAddressLine2) {
        this.billToAddressLine2 = billToAddressLine2;
    }

    public String getBillToAddressCity() {
        return billToAddressCity;
    }

    public void setBillToAddressCity(String billToAddressCity) {
        this.billToAddressCity = billToAddressCity;
    }

    public String getBillToAddressPostalCode() {
        return billToAddressPostalCode;
    }

    public void setBillToAddressPostalCode(String billToAddressPostalCode) {
        this.billToAddressPostalCode = billToAddressPostalCode;
    }

    public CountryDTO getBillToAddressCountry() {
        return billToAddressCountry;
    }

    public void setBillToAddressCountry(CountryDTO billToAddressCountry) {
        this.billToAddressCountry = billToAddressCountry;
    }
}
