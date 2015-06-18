package com.novacroft.nemo.mock_single_sign_on.data_service;

import com.novacroft.nemo.mock_single_sign_on.domain.User;


public interface MasterCustomerDataService {
    
    User findMasterCustomerById(Long customerId);
    
    User findMasterCustomerByUsername(String username);
    
    Boolean isUsernameExisting(String username);
    
    Boolean updateMasterCustomer(User masterCustomerData);
    
    User createMasterCustomer(User masterCustomerData, String password);
    
    void updatePassword(Long customerId, String password);
    
    String getPassword(Long customerId);
    
}
