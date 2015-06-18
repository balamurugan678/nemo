package com.novacroft.nemo.common.converter.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.data_access.CountryDAO;
import com.novacroft.nemo.common.domain.Address;
import com.novacroft.nemo.common.domain.Country;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.Converter;

@Component(value = "addressConverter")
public class AddressConverterImpl extends BaseDtoEntityConverterImpl<Address, AddressDTO> {

    @Autowired
    protected CountryDAO countryDAO;

    @Autowired
    protected CountryConverterImpl countryConverter;

    @Override
    protected AddressDTO getNewDto() {
        return new AddressDTO();
    }

    @Override
    public AddressDTO convertEntityToDto(Address address) {
        AddressDTO addressDTO = (AddressDTO) Converter.convert(address, getNewDto());
        if (address.getCountry() != null) {
            CountryDTO countryDTO = countryConverter.convertEntityToDto(address.getCountry());
            addressDTO.setCountry(countryDTO);
        }
        return addressDTO;
    }

    @Override
    public Address convertDtoToEntity(AddressDTO addressDTO, Address entity) {
        Address address = (Address) Converter.convert(addressDTO, entity);
        final String countryCode = addressDTO.getCountryCode();
        if (countryCode != null) {
            Country country = new Country();
            country.setCode(countryCode);
            address.setCountry(countryDAO.findByExampleUniqueResult(country));
        }
        return address;
    }
}
