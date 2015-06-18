package com.novacroft.nemo.mock_single_sign_on.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.util.WebUtils;

import com.novacroft.nemo.mock_single_sign_on.domain.AccountStatus;
import com.novacroft.nemo.mock_single_sign_on.domain.Address;
import com.novacroft.nemo.mock_single_sign_on.domain.AddressType;
import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.ProductService;
import com.novacroft.nemo.mock_single_sign_on.domain.RegisteredProdServiceStatus;
import com.novacroft.nemo.mock_single_sign_on.domain.RegisteredProductServices;
import com.novacroft.nemo.mock_single_sign_on.domain.Response;
import com.novacroft.nemo.mock_single_sign_on.domain.Role;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityAnswer;
import com.novacroft.nemo.mock_single_sign_on.domain.SecurityQuestion;
import com.novacroft.nemo.mock_single_sign_on.domain.SystemName;
import com.novacroft.nemo.mock_single_sign_on.domain.SystemReferences;
import com.novacroft.nemo.mock_single_sign_on.domain.Title;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.domain.UserAccount;
import com.novacroft.nemo.mock_single_sign_on.service.LogonService;


@Service("logonService")
public class LogonServiceImpl implements LogonService{
    
    private static final String ERROR_MESSAGE = "Test error message";
    
    @Override
    public void setUserAccount(User user) {
        Role role = new Role(1L,"Customer","");
        String active = "Active";
        UserAccount userAccount = new UserAccount("someone@example.com",new AccountStatus(active),null);
        user.setUserAccount(userAccount );
        RegisteredProductServices registeredProdServices1 =  new RegisteredProductServices(formatDateForJson("1383765820152"),new ProductService(3L,"8ead5cf4-4624-4389-b90c-b1fd1937bf1f","Oyster"),new RegisteredProdServiceStatus(1L,active));
        registeredProdServices1.getRoles().add(role);
        userAccount.getRegisteredProductServices().add(registeredProdServices1);
        
        RegisteredProductServices registeredProdServices2 =  new RegisteredProductServices(formatDateForJson("1405955634743"), new ProductService(1L,"88b73293-96a1-4131-97f5-20026b7fb2d9","SSO"),new RegisteredProdServiceStatus(1L,active));
        registeredProdServices2.getRoles().add(role);
        userAccount.getRegisteredProductServices().add(registeredProdServices2);
        
        RegisteredProductServices registeredProdServices3 =  new RegisteredProductServices(formatDateForJson("-62135596800000"), new ProductService(4L,"a3ac81d4-80e8-4427-b348-a3d028dfdbe7","Customer Self Care"),new RegisteredProdServiceStatus(1L,active));
        registeredProdServices3.getRoles().add(role);
        userAccount.getRegisteredProductServices().add(registeredProdServices3);
    }

    @Override
    public String formatDateForJson(String date) {
        StringBuilder sb = new StringBuilder();
        sb.append('/');
        sb.append("Date(");
        sb.append(date);
        sb.append(')');
        sb.append('/');
        return sb.toString();
    }

    @Override
    public void setSystemReferences(Customer customer) {
        String customerId = customer.getCustomerId() == null ? "" : customer.getCustomerId().toString();
        SystemName systemName1 = new SystemName(1L, "Oyster");
        SystemReferences systemReferences1 = new SystemReferences(systemName1, customerId, "USER1");
        
        SystemName systemName2 = new SystemName(2L, "CASC");
        SystemReferences systemReferences2 = new SystemReferences(systemName2, customerId, null);
        
        SystemName systemName3 = new SystemName(3L,"FTPNotification");
        SystemReferences systemReferences3 = new SystemReferences(systemName3, customerId, null);
        
        customer.getSystemReferences().add(systemReferences1);
        customer.getSystemReferences().add(systemReferences2);
        customer.getSystemReferences().add(systemReferences3);
    }

    @Override
    public void setCustomerDetails(Customer customer) {
        customer.setFirstName("David");
        customer.setMiddleName("S");
        customer.setLastName("Williams");
        customer.setHomePhoneNumber("0999999999");
        customer.setWorkPhoneNumber("");
        customer.setMobilePhoneNumber("");
        customer.setEmailAddress("someone@example.com");
        customer.setTfLMarketingOptIn(true);
        customer.setTocMarketingOptIn(true);
        
        Title title = new Title();
        customer.setTitle(title );
        
        title.setId(1);
        title.setDescription("Mr");
    }

    @Override
    public void setCustomerAddress(Customer customer) {
        Address address = new Address();
        AddressType addType =  new AddressType();
        address.setAddressType(addType);
        
        address.setAddressLine1("41 High Street");
        address.setAddressLine2(null);
        address.setAddressLine3("Surrey");
        address.setAddressLine4(null);
        address.setAddressLine5("null");
        address.setHouseNo("41");
        address.setHouseName(null);
        address.setStreetName("High Street");
        address.setCity("London");
        address.setPostCode("SM6 8FG");
        address.setCountry("United Kingdom");
        address.setCountryCode(826);
        
        addType.setId(1L);
        addType.setDescription("Home");
        
        customer.setAddress(address);
    }
  
    @Override
    public void setSecurityAnswers(Customer customer) {
        SecurityQuestion question = new SecurityQuestion(2L, "In what town was your first job");
        SecurityAnswer securityAnswer = new SecurityAnswer(customer.getCustomerId(), "London", question);
        customer.getSecurityAnswers().add(securityAnswer);
       

    }

    @Override
    public Map<String, String> convert(String str) {
        String[] tokens = str.split(",");
        Map<String, String> map = new HashMap<>();
        for (int i=0; i<=tokens.length-1; i++){
            String[] tokenitem = tokens[i].split(":");
            map.put(tokenitem[0], tokenitem[1]);
        }
        return map;
    }
    
    @Override
    public Cookie processCookies(HttpServletRequest httpRequest) {
        return WebUtils.getCookie(httpRequest, "token");
    }

    @Override
    public Response createResponse(User user) {
        Response jsonResponse = new Response();
        
        User userWrapper = new User();
        jsonResponse.setUser(userWrapper);
        
        if (user == null) {
            userWrapper.setUser(new User());
            jsonResponse.setIsValid(false);
            jsonResponse.setErrorMessage(ERROR_MESSAGE);
        }
        else {
            userWrapper.setUser(user);
            jsonResponse.setIsValid(true);
            jsonResponse.setErrorMessage(null);
        }
        
        return jsonResponse;
    }
}
