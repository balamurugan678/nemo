package com.novacroft.nemo.common.application_service;

import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.transfer.SelectListDTO;

/**
 * Postcode Address File (PAF) service specification
 */
public interface PAFService {
    String[] getAddressesForPostcode(String postcode);

    SelectListDTO getAddressesForPostcodeSelectList(String postcode);
    
    CommonOrderCardCmd populateAddressFromJson(CommonOrderCardCmd cmd , String addressForPostCode);
}
