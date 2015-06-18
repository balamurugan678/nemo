package com.novacroft.nemo.tfl.common.converter.single_sign_on;

import java.util.List;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

public interface SingleSignOnConverter {
    
     CustomerDTO convertResponseToCustomerDto(SingleSignOnResponseDTO ssoResponseDTO);
     
     AddressDTO convertResponseToAddressDto(SingleSignOnResponseDTO ssoResponseDTO);
     
     List<ContactDTO> convertResponseToContactDtoList(SingleSignOnResponseDTO ssoResponseDTO);
     
     CustomerPreferencesDTO convertResponseToCustomerPreferencesDto(SingleSignOnResponseDTO ssoResponseDTO);
}
