package com.novacroft.nemo.tfl.services.converter;

import java.util.List;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;

public interface CustomerConverter {
    CustomerDTO convertToCustomerDTO(Customer customer);
    CustomerDTO convertToCustomerDTO(Customer customer, CustomerDTO customerDTO);
    CustomerDTO convertToCustomerDTO(DeleteCustomer customer, CustomerDTO customerDTO);
    List<ContactDTO> convertToContactDTOs(Customer customer, Long internalCustomerId);
    List<ContactDTO> updateContactDTOs(Customer customer, List<ContactDTO> originalContactDTOs);
    AddressDTO convertToAddressDTO(Customer customer);
    AddressDTO convertToAddressDTO(Customer customer, AddressDTO addressDTO);
    Customer convertToCustomer(CustomerDTO customerDTO, AddressDTO addressDTO, List<ContactDTO> contactDTOs);
    DeleteCustomer convertToDeleteCustomer(CustomerDTO customerDTO);
}
