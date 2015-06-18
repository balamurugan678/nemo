package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnAddressDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnCustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnTitleDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnUserAccountDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnUserDTO;

public class SingleSignOnResponseTestUtil {
    
    public static final Long SSO_CUSTOMER_ID = 1859115L;
    public static final String SSO_FIRST_NAME = "David";
    public static final String SSO_MIDDLE_NAME = "S";
    public static final String SSO_LAST_NAME = "Williams";
    public static final String SSO_HOME_NUMBER = "0999999999";
    public static final String SSO_MOBILE_NUMBER = "";
    public static final String SSO_EMAIL_ADDRESS = "someone@gmail.com";
    
    private static final String SSO_ADDRESS_LINE_1 = "41 High Street";
    private static final String SSO_ADDRESS_LINE_2 = "";
    public static final String SSO_ADDRESS_LINE_3 = "Surrey";
    private static final String SSO_ADDRESS_LINE_4 = null;
    private static final String SSO_ADDRESS_LINE_5 = null;
    public static final String SSO_HOUSE_NO = "41";
    private static final String SSO_HOUSE_NAME = null;
    public static final String SSO_STREET_NAME = "High Street";
    private static final String SSO_CITY = "London";
    public static final String SSO_POST_CODE = "SM6 8FG";
    public static final String SSO_COUNTRY = "United Kingdom";
    
    private static final Integer SSO_TITLE_ID = 1;
    public static final String SSO_TITLE_DESCRIPTION = "Mr.";
    
    public static final String SSO_USER_NAME = "someone@gmail.com";
    
    public static final Boolean SSO_TFL_MARKETING_OPT_IN = Boolean.TRUE;
    public static final Boolean SSO_TOC_MARKETING_OPT_IN = Boolean.TRUE;
    
    public static SingleSignOnResponseDTO createMockResponseDTO() {
        SingleSignOnResponseDTO dto = new SingleSignOnResponseDTO();
        
        SingleSignOnUserDTO userDto = new SingleSignOnUserDTO();
        
        SingleSignOnUserDTO wrappedUserDto = new SingleSignOnUserDTO();
        wrappedUserDto.setCustomer(createMockCustomerDTO());
        wrappedUserDto.setUserAccount(createMockUserAccountDTO());
        
        userDto.setUser(wrappedUserDto);
        dto.setUser(userDto);
        return dto;
    }
    
    private static SingleSignOnCustomerDTO createMockCustomerDTO() {
        SingleSignOnCustomerDTO customerDto = new SingleSignOnCustomerDTO();
        customerDto.setCustomerId(SSO_CUSTOMER_ID);
        customerDto.setFirstName(SSO_FIRST_NAME);
        customerDto.setMiddleName(SSO_MIDDLE_NAME);
        customerDto.setLastName(SSO_LAST_NAME);
        customerDto.setEmailAddress(SSO_EMAIL_ADDRESS);
        customerDto.setHomePhoneNumber(SSO_HOME_NUMBER);
        customerDto.setMobilePhoneNumber(SSO_MOBILE_NUMBER);
        customerDto.setTfLMarketingOptIn(SSO_TFL_MARKETING_OPT_IN);
        customerDto.setTocMarketingOptIn(SSO_TOC_MARKETING_OPT_IN);
        customerDto.setAddress(createMockAddressDTO());
        customerDto.setTitle(createMockTitleDTO());
        return customerDto;
    }
    
    private static SingleSignOnAddressDTO createMockAddressDTO() {
        SingleSignOnAddressDTO addressDto = new SingleSignOnAddressDTO();
        addressDto.setAddressLine1(SSO_ADDRESS_LINE_1);
        addressDto.setAddressLine2(SSO_ADDRESS_LINE_2);
        addressDto.setAddressLine3(SSO_ADDRESS_LINE_3);
        addressDto.setAddressLine4(SSO_ADDRESS_LINE_4);
        addressDto.setAddressLine5(SSO_ADDRESS_LINE_5);
        addressDto.setHouseNo(SSO_HOUSE_NO);
        addressDto.setHouseName(SSO_HOUSE_NAME);
        addressDto.setStreetName(SSO_STREET_NAME);
        addressDto.setCity(SSO_CITY);
        addressDto.setCountry(SSO_COUNTRY);
        addressDto.setPostCode(SSO_POST_CODE);
        return addressDto;
    }
    
    private static SingleSignOnTitleDTO createMockTitleDTO() {
        SingleSignOnTitleDTO titleDto = new SingleSignOnTitleDTO();
        titleDto.setId(SSO_TITLE_ID);
        titleDto.setDescription(SSO_TITLE_DESCRIPTION);
        return titleDto;
    }
    
    private static SingleSignOnUserAccountDTO createMockUserAccountDTO() {
        SingleSignOnUserAccountDTO userAccountDto = new SingleSignOnUserAccountDTO();
        userAccountDto.setUserName(SSO_USER_NAME);
        return userAccountDto;
    }
}
