package com.novacroft.nemo.mock_single_sign_on.data_service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.mock_single_sign_on.data_service.MasterCustomerDataService;
import com.novacroft.nemo.mock_single_sign_on.domain.Customer;
import com.novacroft.nemo.mock_single_sign_on.domain.User;
import com.novacroft.nemo.mock_single_sign_on.service.LogonService;

@Service("masterCustomerDataService")
public class MasterCustomerDataServiceImpl implements MasterCustomerDataService {
    private static final int DEFAULT_USER_COUNT = 2;
    
    private static final Long DEFAULT_USER_ID_1 = 1859115L;
    private static final Long DEFAULT_USER_ID_2 = 1859116L;
    
    private static final String DEFAULT_USERNAME_1 = "someone@example.com";
    private static final String DEFAULT_USERNAME_2 = "nemo1@example.com";
    
    private static final String DEFAULT_PASSWORD_1 = "someone";
    private static final String DEFAULT_PASSWORD_2 = "nemo";

    protected static final Map<Long, User> TEST_MASTER_CUSTOMER_POOL = new HashMap<>();
    protected static final Map<Long, String> ID_USERNAME_MAP = new HashMap<>();
    protected static final Map<Long, String> ID_PASSWORD_MAP = new HashMap<Long, String>();

    @Autowired
    protected LogonService logonService;
    
    @PostConstruct
    public void initialiseDefaultUsers() {
        ID_USERNAME_MAP.put(DEFAULT_USER_ID_1, DEFAULT_USERNAME_1);
        ID_USERNAME_MAP.put(DEFAULT_USER_ID_2, DEFAULT_USERNAME_2);
        
        ID_PASSWORD_MAP.put(DEFAULT_USER_ID_1, DEFAULT_PASSWORD_1);
        ID_PASSWORD_MAP.put(DEFAULT_USER_ID_2, DEFAULT_PASSWORD_2);
        
        TEST_MASTER_CUSTOMER_POOL.put(DEFAULT_USER_ID_1, createDefaultUser(DEFAULT_USER_ID_1, DEFAULT_USERNAME_1));
        TEST_MASTER_CUSTOMER_POOL.put(DEFAULT_USER_ID_2, createDefaultUser(DEFAULT_USER_ID_2, DEFAULT_USERNAME_2));
    }

    @Override
    public User findMasterCustomerByUsername(String username) {
        Long customerId = null;
        for (Map.Entry<Long, String> entry : ID_USERNAME_MAP.entrySet()) {
            if (StringUtils.equalsIgnoreCase(username, entry.getValue())) {
                customerId = entry.getKey();
                break;
            }
        }

        return findMasterCustomerById(customerId);
    }

    protected User createDefaultUser(Long customerId, String username) {
        User user = new User();

        Customer customer = new Customer();
        customer.setCustomerId(customerId);
        user.setCustomer(customer);

        logonService.setUserAccount(user);
        logonService.setCustomerDetails(customer);
        logonService.setCustomerAddress(customer);
        logonService.setSecurityAnswers(customer);
        logonService.setSystemReferences(customer);

        user.getUserAccount().setUserName(username);
        customer.setEmailAddress(username);
        return user;
    }

    @Override
    public Boolean isUsernameExisting(String username) {
        if (StringUtils.isBlank(username)) {
            return false;
        } else {
            return ID_USERNAME_MAP.containsValue(username);
        }
    }

    @Override
    public Boolean updateMasterCustomer(User masterCustomerData) {
        if (masterCustomerData == null) {
            return false;
        }
        
        Long customerID = masterCustomerData.getCustomer().getCustomerId();
        if (customerID == null) {
            return false;
        }
        
        TEST_MASTER_CUSTOMER_POOL.put(customerID, masterCustomerData);
        ID_USERNAME_MAP.put(customerID, masterCustomerData.getUserAccount().getUserName());
        return true;
    }

    @Override
    public User findMasterCustomerById(Long customerId) {
        return (customerId == null ? null : TEST_MASTER_CUSTOMER_POOL.get(customerId));
    }
    
    protected Long getNextAvailableId() {
        return Long.valueOf(ID_USERNAME_MAP.size() - DEFAULT_USER_COUNT);
    }
    
    @Override
    public User createMasterCustomer(User masterCustomerData, String password) {
        Long customerID = masterCustomerData.getCustomer().getCustomerId();
        if (customerID == null) {
            customerID = getNextAvailableId();
            masterCustomerData.getCustomer().setCustomerId(customerID);
        }
        
        TEST_MASTER_CUSTOMER_POOL.put(customerID, masterCustomerData);
        ID_USERNAME_MAP.put(customerID, masterCustomerData.getUserAccount().getUserName());
        ID_PASSWORD_MAP.put(customerID, password);
        return masterCustomerData;
    }

    @Override
    public void updatePassword(Long customerId, String password) {
        ID_PASSWORD_MAP.put(customerId, password);
    }

    @Override
    public String getPassword(Long customerId) {
        return ID_PASSWORD_MAP.get(customerId);
    }
}
