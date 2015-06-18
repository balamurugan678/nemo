package com.novacroft.nemo.tfl.services.converter;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.services.transfer.Address;

public interface AddressConverter {
    Address convert(AddressDTO dto);
}
