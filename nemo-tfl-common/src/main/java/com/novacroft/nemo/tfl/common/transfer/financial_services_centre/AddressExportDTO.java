package com.novacroft.nemo.tfl.common.transfer.financial_services_centre;

/**
 * Transfer class to represent an address for export
 */
public class AddressExportDTO {
    protected String customerAddressLine1;
    protected String customerAddressLine2;
    protected String customerAddressStreet;
    protected String customerAddressTown;
    protected String customerAddressPostCode;

    public AddressExportDTO() {
    }

    public AddressExportDTO(String customerAddressLine1, String customerAddressLine2, String customerAddressStreet,
                            String customerAddressTown, String customerAddressPostCode) {
        this.customerAddressLine1 = customerAddressLine1;
        this.customerAddressLine2 = customerAddressLine2;
        this.customerAddressStreet = customerAddressStreet;
        this.customerAddressTown = customerAddressTown;
        this.customerAddressPostCode = customerAddressPostCode;
    }

    public String getCustomerAddressLine1() {
        return customerAddressLine1;
    }

    public void setCustomerAddressLine1(String customerAddressLine1) {
        this.customerAddressLine1 = customerAddressLine1;
    }

    public String getCustomerAddressLine2() {
        return customerAddressLine2;
    }

    public void setCustomerAddressLine2(String customerAddressLine2) {
        this.customerAddressLine2 = customerAddressLine2;
    }

    public String getCustomerAddressStreet() {
        return customerAddressStreet;
    }

    public void setCustomerAddressStreet(String customerAddressStreet) {
        this.customerAddressStreet = customerAddressStreet;
    }

    public String getCustomerAddressTown() {
        return customerAddressTown;
    }

    public void setCustomerAddressTown(String customerAddressTown) {
        this.customerAddressTown = customerAddressTown;
    }

    public String getCustomerAddressPostCode() {
        return customerAddressPostCode;
    }

    public void setCustomerAddressPostCode(String customerAddressPostCode) {
        this.customerAddressPostCode = customerAddressPostCode;
    }
}
