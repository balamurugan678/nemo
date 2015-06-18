package com.novacroft.nemo.common.command;

import com.novacroft.nemo.common.transfer.CountryDTO;

/**
 * Address Details command interface TfL definition
 */
public interface AddressCmd extends CommonPostcodeCmd {

    String getHouseNameNumber();

    String getStreet();

    String getTown();

    String getPostcode();
    
    void setCounty(String county) ;

    CountryDTO getCountry();
}
