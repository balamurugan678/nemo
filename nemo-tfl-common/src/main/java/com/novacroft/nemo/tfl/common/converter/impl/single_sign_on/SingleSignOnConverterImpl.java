package com.novacroft.nemo.tfl.common.converter.impl.single_sign_on;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.ContactType;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.common.utils.Converter;
import com.novacroft.nemo.tfl.common.constant.ContactNameType;
import com.novacroft.nemo.tfl.common.converter.single_sign_on.SingleSignOnConverter;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnAddressDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnCustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnUserAccountDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;

@Service(value = "singleSignOnConverter")
public class SingleSignOnConverterImpl implements SingleSignOnConverter {

    @Override
    public CustomerDTO convertResponseToCustomerDto(SingleSignOnResponseDTO ssoResponseDTO) {
        SingleSignOnCustomerDTO ssoCustomerDTO = getSingleSignOnCustomerDTO(ssoResponseDTO);
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO = (CustomerDTO) Converter.convert(ssoCustomerDTO, customerDTO);
        customerDTO.setInitials(ssoCustomerDTO.getMiddleName());
        customerDTO.setTitle(ssoCustomerDTO.getTitle().getDescription());
        customerDTO.setTflMasterId(ssoCustomerDTO.getCustomerId());
        
        SingleSignOnUserAccountDTO ssoUserAccountDTO = getSingleSignOnUserAccountDTO(ssoResponseDTO);
        customerDTO.setUsername(ssoUserAccountDTO.getUserName());
        
        return customerDTO;
    }

    @Override
    public AddressDTO convertResponseToAddressDto(SingleSignOnResponseDTO ssoResponseDTO) {
        SingleSignOnAddressDTO ssoAddressDTO = getSingleSignOnCustomerDTO(ssoResponseDTO).getAddress();
        AddressDTO addressDTO = new AddressDTO();
        Converter.convert(ssoAddressDTO, addressDTO);
        String houseNameNumber = AddressFormatUtil.formatHouseNameNumber(ssoAddressDTO.getHouseNo(), ssoAddressDTO.getHouseName());
        addressDTO.setHouseNameNumber(houseNameNumber);
        addressDTO.setTown(ssoAddressDTO.getAddressLine3());
        addressDTO.setStreet(ssoAddressDTO.getStreetName());
        
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setName(ssoAddressDTO.getCountry());
        addressDTO.setCountry(countryDTO);
        return addressDTO;
    }

    @Override
    public List<ContactDTO> convertResponseToContactDtoList(SingleSignOnResponseDTO ssoResponseDTO) {
        List<ContactDTO> contactDtos = new ArrayList<>();
        SingleSignOnCustomerDTO ssoCustomerDTO = getSingleSignOnCustomerDTO(ssoResponseDTO);
        
        String homeNumber = ssoCustomerDTO.getHomePhoneNumber();
        contactDtos.add(new ContactDTO(ContactNameType.Phone.name(), homeNumber, ContactType.HomePhone.name(), null));
        
        String mobileNumber = ssoCustomerDTO.getMobilePhoneNumber();
        contactDtos.add(new ContactDTO(ContactNameType.Phone.name(), mobileNumber, ContactType.MobilePhone.name(), null));
        
        return contactDtos;
    }
    
    protected SingleSignOnCustomerDTO getSingleSignOnCustomerDTO(SingleSignOnResponseDTO ssoResponseDTO) {
        return ssoResponseDTO.getUser().getUser().getCustomer();
    }
    
    protected SingleSignOnUserAccountDTO getSingleSignOnUserAccountDTO(SingleSignOnResponseDTO ssoResponseDTO) {
        return ssoResponseDTO.getUser().getUser().getUserAccount();
    }

    @Override
    public CustomerPreferencesDTO convertResponseToCustomerPreferencesDto(SingleSignOnResponseDTO ssoResponseDTO) {
        SingleSignOnCustomerDTO ssoCustomerDTO = getSingleSignOnCustomerDTO(ssoResponseDTO);
        CustomerPreferencesDTO preferencesDto = new CustomerPreferencesDTO();
        preferencesDto.setCanTflContact(ssoCustomerDTO.getTfLMarketingOptIn());
        preferencesDto.setCanThirdPartyContact(ssoCustomerDTO.getTocMarketingOptIn());
        return preferencesDto;
    }

}
