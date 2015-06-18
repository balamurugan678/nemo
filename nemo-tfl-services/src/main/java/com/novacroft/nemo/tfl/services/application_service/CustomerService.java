package com.novacroft.nemo.tfl.services.application_service;

import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public interface CustomerService {
    Customer createCustomer(Customer customer, String username);
    Customer updateCustomer(Customer customer, Long customerId, String username);
    Customer getCustomerByExternalId(Long customerId);
    WebServiceResult deleteCustomer(DeleteCustomer deleteCustomer, Long customerId, String username);
}
