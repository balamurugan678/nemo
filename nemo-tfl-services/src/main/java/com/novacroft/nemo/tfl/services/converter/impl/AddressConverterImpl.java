package com.novacroft.nemo.tfl.services.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.services.converter.AddressConverter;
import com.novacroft.nemo.tfl.services.transfer.Address;

@Component("externalAddressConverter")
public class AddressConverterImpl implements AddressConverter{

    @Override
    public Address convert(AddressDTO dto) {
        Address address = null;
        if(dto != null){
            address = new Address();
            address.setHouseNameNumber(dto.getHouseNameNumber());
            address.setStreet(dto.getStreet());
            address.setTown(dto.getTown());
            address.setCounty(dto.getCounty());
            address.setCountry(dto.getCountryName());
            address.setPostcode(dto.getPostcode());
        }
        return address;
    }

}
