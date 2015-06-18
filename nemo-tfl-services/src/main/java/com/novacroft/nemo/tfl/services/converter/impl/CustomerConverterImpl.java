package com.novacroft.nemo.tfl.services.converter.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.constant.ContactType;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.services.converter.CustomerConverter;
import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;

@Component("customerConvert")
public class CustomerConverterImpl implements CustomerConverter {

    @Override
    public CustomerDTO convertToCustomerDTO(Customer customer) {
        return convertToCustomerDTO(customer, new CustomerDTO());
    }

    @Override
    public CustomerDTO convertToCustomerDTO(Customer customer, CustomerDTO customerDTO) {
        customerDTO.setTitle(customer.getTitle());
        customerDTO.setFirstName(customer.getFirstName());
        customerDTO.setInitials(customer.getInitials());
        customerDTO.setLastName(customer.getLastName());
        customerDTO.setUsername(customer.getUsername());
        customerDTO.setEmailAddress(customer.getEmailAddress());
        return customerDTO;
    }
    
    @Override
    public CustomerDTO convertToCustomerDTO(DeleteCustomer deleteCustomer, CustomerDTO customerDTO) {
        customerDTO.setDeletedDateTime(new Date());
        customerDTO.setDeletedReasonCode(deleteCustomer.getDeletedReasonCode());
        customerDTO.setDeletedReferenceNumber(deleteCustomer.getDeletedReferenceNumber());
        customerDTO.setDeletedNote(deleteCustomer.getDeletedNote());
        return customerDTO;
    }
    
    @Override
    public Customer convertToCustomer(CustomerDTO customerDTO, AddressDTO addressDTO, List<ContactDTO> contactDTOs) {
        Customer customer = new Customer(customerDTO.getExternalId(), customerDTO.getTitle(), customerDTO.getFirstName(),
                        customerDTO.getInitials(), customerDTO.getLastName(), addressDTO.getHouseNameNumber(), addressDTO.getStreet(),
                        addressDTO.getTown(), addressDTO.getCounty(), addressDTO.getCountryCode(), addressDTO.getPostcode(), null, null,
                        customerDTO.getEmailAddress(), customerDTO.getUsername());
        if (contactDTOs != null && contactDTOs.size() > 0) {
            for (ContactDTO contactDTO : contactDTOs) {
                if (ContactType.MobilePhone.name().equals(contactDTO.getType())) {
                    customer.setMobilePhone(contactDTO.getValue());
                } else if (ContactType.HomePhone.name().equals(contactDTO.getType())) {
                    customer.setHomePhone(contactDTO.getValue());
                }
            }
        }
        return customer;
    }

    @Override
    public List<ContactDTO> convertToContactDTOs(Customer customer, Long internalCustomerId) {
        List<ContactDTO> contactDTOs = new ArrayList<>();
        if (customer.getMobilePhone() != null) {
            contactDTOs.add(getContactDTO(ContactType.MobilePhone.name(), null, customer.getMobilePhone(), internalCustomerId));
        }
        if (customer.getHomePhone() != null) {
            contactDTOs.add(getContactDTO(ContactType.HomePhone.name(), null, customer.getHomePhone(), internalCustomerId));
        }
        return contactDTOs;
    }

    @Override
    public List<ContactDTO> updateContactDTOs(Customer customer, List<ContactDTO> originalContactDTOs) {
        if (customer.getMobilePhone() != null) {
            for (ContactDTO contactDTO : originalContactDTOs) {
                if (contactDTO.getType().equalsIgnoreCase(ContactType.MobilePhone.name())) {
                    contactDTO.setValue(customer.getMobilePhone());
                }
            }
        }
        if (customer.getHomePhone() != null) {
            for (ContactDTO contactDTO : originalContactDTOs) {
                if (contactDTO.getType().equalsIgnoreCase(ContactType.HomePhone.name())) {
                    contactDTO.setValue(customer.getHomePhone());
                }
            }
        }
        return originalContactDTOs;
    }

    private ContactDTO getContactDTO(String type, String name, String value, Long customerId) {
        return new ContactDTO(name, value, type, customerId);
    }

    @Override
    public AddressDTO convertToAddressDTO(Customer customer) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode(customer.getCountry());
        return new AddressDTO(customer.getHouseNameNumber(),
                              customer.getStreet(),
                              customer.getTown(),
                              customer.getPostcode(),
                              countryDTO,
                              customer.getCounty());
    }

    @Override
    public AddressDTO convertToAddressDTO(Customer customer, AddressDTO addressDTO) {
        CountryDTO countryDTO = new CountryDTO();
        countryDTO.setCode(customer.getCountry());
        addressDTO.setHouseNameNumber(customer.getHouseNameNumber());
        addressDTO.setStreet(customer.getStreet());
        addressDTO.setTown(customer.getTown());
        addressDTO.setPostcode(customer.getPostcode());
        addressDTO.setCountry(countryDTO);
        addressDTO.setCounty(customer.getCounty());        
        return addressDTO;
    }

    @Override
    public DeleteCustomer convertToDeleteCustomer(CustomerDTO customerDTO) {
        return new DeleteCustomer(customerDTO.getExternalId(),
                                  customerDTO.getDeletedDateTime(),
                                  customerDTO.getDeletedReasonCode(),
                                  customerDTO.getDeletedReferenceNumber(),
                                  customerDTO.getDeletedNote());
    }

    
}
