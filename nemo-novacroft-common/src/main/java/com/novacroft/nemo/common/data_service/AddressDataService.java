package com.novacroft.nemo.common.data_service;

import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.transfer.AddressDTO;

public interface AddressDataService extends BaseDataService<Address, AddressDTO> {
    AddressDTO findByEmailAddressAndPostcode(String username, String postcode);
}
