package com.novacroft.nemo.mock_single_sign_on.service;

import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.Response;
import com.novacroft.nemo.mock_single_sign_on.domain.User;

public interface LogonService {

    void setUserAccount(User user);

    void setSecurityAnswers(Customer customer);

    String formatDateForJson(String date);

    void setSystemReferences(Customer customer);

    void setCustomerDetails(Customer customer);

    void setCustomerAddress(Customer customer);

    Map<String, String> convert(String str);

    Cookie processCookies(HttpServletRequest httpRequest);
    
    Response createResponse(User user);

}
