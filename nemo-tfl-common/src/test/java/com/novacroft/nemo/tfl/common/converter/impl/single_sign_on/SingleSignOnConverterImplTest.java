package com.novacroft.nemo.tfl.common.converter.impl.single_sign_on;

import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_ADDRESS_LINE_3;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_COUNTRY;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_CUSTOMER_ID;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_FIRST_NAME;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_HOUSE_NO;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_LAST_NAME;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_MIDDLE_NAME;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_POST_CODE;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_STREET_NAME;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_TITLE_DESCRIPTION;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_USER_NAME;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.createMockResponseDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

public class SingleSignOnConverterImplTest {
    private SingleSignOnConverterImpl converter;
    private SingleSignOnResponseDTO testSsoResponseDto;
    
    @Before
    public void setUp() {
        converter = new SingleSignOnConverterImpl();
        testSsoResponseDto = createMockResponseDTO();
    }
    
    @Test
    public void testConvertResponseToCustomerDto() {
        CustomerDTO actualResult = converter.convertResponseToCustomerDto(testSsoResponseDto);
        
        assertNotNull(actualResult);
        assertEquals(SSO_CUSTOMER_ID, actualResult.getTflMasterId());
        assertEquals(SSO_FIRST_NAME, actualResult.getFirstName());
        assertEquals(SSO_MIDDLE_NAME, actualResult.getInitials());
        assertEquals(SSO_LAST_NAME, actualResult.getLastName());
        assertEquals(SSO_TITLE_DESCRIPTION, actualResult.getTitle());
        assertEquals(SSO_USER_NAME, actualResult.getUsername());
    }
    
    @Test
    public void testConvertResponseToAddressDto() {
        AddressDTO actualResult = converter.convertResponseToAddressDto(testSsoResponseDto);
        
        assertNotNull(actualResult);
        assertEquals(SSO_HOUSE_NO, actualResult.getHouseNameNumber());
        assertEquals(SSO_STREET_NAME, actualResult.getStreet());
        assertEquals(SSO_ADDRESS_LINE_3, actualResult.getTown());
        assertEquals(SSO_POST_CODE, actualResult.getPostcode());
        assertEquals(SSO_COUNTRY, actualResult.getCountryName());
    }
    
    @Test
    public void testConvertResponseToContactDtoList() {
        List<ContactDTO> actualResult = converter.convertResponseToContactDtoList(testSsoResponseDto);
        
        assertNotNull(actualResult);
        assertEquals(2, actualResult.size());
    }
    
    @Test
    public void testConvertResponseToCustomerPreferencesDto() {
        CustomerPreferencesDTO actualResult = converter.convertResponseToCustomerPreferencesDto(testSsoResponseDto);
        
        assertTrue(actualResult.getCanTflContact());
        assertTrue(actualResult.getCanThirdPartyContact());
    }
}
