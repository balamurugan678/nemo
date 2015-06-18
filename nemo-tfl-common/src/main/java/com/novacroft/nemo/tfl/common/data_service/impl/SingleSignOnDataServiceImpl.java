package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.tfl.common.converter.single_sign_on.SingleSignOnConverter;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerPreferencesDataService;
import com.novacroft.nemo.tfl.common.data_service.SingleSignOnDataService;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;
import com.novacroft.nemo.tfl.common.util.SingleSignOnUtil;

@Service(value = "SingleSignOnDataService")
public class SingleSignOnDataServiceImpl implements SingleSignOnDataService {

    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AddressDataService addressDataService;
    @Autowired
    protected CountryDataService countryDataService;
    @Autowired
    protected ContactDataService contactDataService;
    @Autowired
    protected SingleSignOnConverter singleSignOnConverter;
    @Autowired
    protected CustomerPreferencesDataService customerPreferencesDataService;
    
    @Override
    public boolean checkAndUpdateLocalData(SingleSignOnResponseDTO ssoResponseDTO) {
        CustomerDTO ssoCustomerDTO = singleSignOnConverter.convertResponseToCustomerDto(ssoResponseDTO);
        AddressDTO ssoAddressDTO = singleSignOnConverter.convertResponseToAddressDto(ssoResponseDTO);
        List<ContactDTO> ssoContacts = singleSignOnConverter.convertResponseToContactDtoList(ssoResponseDTO);
        CustomerPreferencesDTO ssoPreferencesDto = singleSignOnConverter.convertResponseToCustomerPreferencesDto(ssoResponseDTO);
        
        CustomerDTO localCustomerDTO = customerDataService.findByTflMasterId(ssoCustomerDTO.getTflMasterId());
        CustomerDTO updatedCustomer = null;
        if (localCustomerDTO == null) {
            AddressDTO newAddressDto = checkAndUpdateLocalAddress(ssoAddressDTO, null);
            updatedCustomer = createNewCustomer(ssoCustomerDTO, newAddressDto.getId());
        }
        else {
            updatedCustomer = checkAndUpdateLocalCustomer(ssoCustomerDTO, localCustomerDTO);
            checkAndUpdateLocalAddress(ssoAddressDTO, updatedCustomer.getAddressId());
        }
        
        checkAndUpdateLocalContacts(ssoContacts, updatedCustomer.getId());
        checkAndUpdateCustomerPreferences(ssoPreferencesDto, updatedCustomer.getId());
        return true;
    }
    
    protected CustomerDTO createNewCustomer(CustomerDTO ssoCustomerDTO, Long addressId) {
        CustomerDTO customerDto = new CustomerDTO();
        SingleSignOnUtil.updateLocalCustomer(ssoCustomerDTO, customerDto);
        customerDto.setAddressId(addressId);
        customerDto.setTflMasterId(ssoCustomerDTO.getTflMasterId());
        return customerDataService.createOrUpdate(customerDto);
    }
    
    protected CustomerDTO checkAndUpdateLocalCustomer(CustomerDTO ssoCustomerDTO, CustomerDTO localCustomerDTO) {
        
        if (!SingleSignOnUtil.isCustomerEqual(ssoCustomerDTO, localCustomerDTO)) {
            SingleSignOnUtil.updateLocalCustomer(ssoCustomerDTO, localCustomerDTO);
            localCustomerDTO = customerDataService.createOrUpdate(localCustomerDTO);
        }
        
        return localCustomerDTO;
    }
    
    protected AddressDTO checkAndUpdateLocalAddress(AddressDTO ssoAddressDTO, Long localAddressId) {
        AddressDTO localAddressDTO = new AddressDTO(); 
        CountryDTO localCountryDTO = new CountryDTO();
        if (localAddressId != null) {
            localAddressDTO = addressDataService.findById(localAddressId);
            localCountryDTO = localAddressDTO.getCountry();
        }
    
        CountryDTO ssoCountryDTO = ssoAddressDTO.getCountry();
        if (!SingleSignOnUtil.isCountryEqual(ssoCountryDTO, localCountryDTO)) {
            CountryDTO existingCountry = countryDataService.findCountryByName(ssoCountryDTO.getName());
            localAddressDTO.setCountry(existingCountry);
        }
        
        if (!SingleSignOnUtil.isAddressEqual(ssoAddressDTO, localAddressDTO)) {
            SingleSignOnUtil.updateLocalAddress(ssoAddressDTO, localAddressDTO);
            localAddressDTO = addressDataService.createOrUpdate(localAddressDTO);
        }
        return localAddressDTO;
    }
    
    protected List<ContactDTO> checkAndUpdateLocalContacts(List<ContactDTO> ssoContacts, Long customerId) {
        List<ContactDTO> localContacts = contactDataService.findPhoneNumbersByCustomerId(customerId);
        if (localContacts == null) {
            localContacts = new ArrayList<ContactDTO>();
        }
        SingleSignOnUtil.populateCustomerIdToContacts(ssoContacts, customerId);
        if (!SingleSignOnUtil.isContactEqual(ssoContacts, localContacts)) {
            SingleSignOnUtil.updateLocalContacts(ssoContacts, localContacts);
            localContacts = contactDataService.createOrUpdateAll(localContacts);
        }
        return localContacts;
    }
    
    protected CustomerPreferencesDTO checkAndUpdateCustomerPreferences(CustomerPreferencesDTO ssoPreferencesDto, Long customerId) {
        CustomerPreferencesDTO localPreference = customerPreferencesDataService.findByCustomerId(customerId);
        if (localPreference == null) {
            localPreference = new CustomerPreferencesDTO();
            localPreference.setCustomerId(customerId);
        }
        
        if (!SingleSignOnUtil.isCustomerPreferencesEqual(ssoPreferencesDto, localPreference)) {
            SingleSignOnUtil.updateLocalCustomerPreferences(ssoPreferencesDto, localPreference);
            localPreference = customerPreferencesDataService.createOrUpdate(localPreference);
        }
        
        return localPreference;
    }
}
