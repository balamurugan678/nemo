package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

import javax.servlet.http.HttpServletRequest;

/**
 * Security service specification.
 */
public interface SecurityService {
    void login(String username, String password, HttpServletRequest request);

    void logout(HttpServletRequest request);

    String getLoggedInUsername();

    boolean isLoggedIn();

    String generateSalt();

    String hashPassword(String salt, String clearPassword);

    CustomerDTO updatePassword(CustomerDTO customerDTO, String newPassword);

    CustomerDTO getLoggedInCustomer();

    CustomerDTO updatePasswordWithoutSavingCustomer(CustomerDTO customerDTO, String newPassword);
    
    String getUsernameFromAuthorizationHeader(HttpServletRequest req);
}
