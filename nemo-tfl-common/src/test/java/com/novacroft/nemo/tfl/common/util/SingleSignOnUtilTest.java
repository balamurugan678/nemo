package com.novacroft.nemo.tfl.common.util;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO2;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.ContactTestUtil.getContactDTOList;
import static com.novacroft.nemo.test_support.ContactTestUtil.getTestContactDTOHomePhone1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO1;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO2;
import static com.novacroft.nemo.test_support.CustomerPreferencesTestUtil.getTestCustomerPreferencesDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_9;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO2;
import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1;
import static com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil.SSO_CUSTOMER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.test_support.SingleSignOnResponseTestUtil;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;

public class SingleSignOnUtilTest {
    
    @Test
    public void isCustomerEqualShouldReturnTrue() {
        assertTrue(SingleSignOnUtil.isCustomerEqual(getTestCustomerDTO1(), getTestCustomerDTO1()));
    }
    
    @Test
    public void isCustomerEqualShouldReturnFalse() {
        assertFalse(SingleSignOnUtil.isCustomerEqual(getTestCustomerDTO1(), getTestCustomerDTO2()));
    }
    
    @Test
    public void isAddressEqualShouldReturnTrue() {
        assertTrue(SingleSignOnUtil.isAddressEqual(getTestAddressDTO1(), getTestAddressDTO1()));
    }
    
    @Test
    public void isAddressEqualShouldReturnFalse() {
        assertFalse(SingleSignOnUtil.isAddressEqual(getTestAddressDTO1(), getTestAddressDTO2()));
    }
    
    @Test
    public void isCountryEqualShouldReturnTrue() {
        assertTrue(SingleSignOnUtil.isCountryEqual(getTestCountryDTO1(), getTestCountryDTO1()));
    }
    
    @Test
    public void isContryEqualShouldReturnFalse() {
        assertFalse(SingleSignOnUtil.isCountryEqual(getTestCountryDTO1(), getTestCountryDTO2()));
    }
    
    @Test
    public void isContactEqualShouldReturnTrue() {
        assertTrue(SingleSignOnUtil.isContactEqual(getContactDTOList(), getContactDTOList()));
    }
    
    @Test
    public void isContactEqualShouldReturnFalse() {
        assertFalse(SingleSignOnUtil.isContactEqual(getContactDTOList(), new ArrayList<ContactDTO>()));
    }
    
    @Test
    public void isCustomerPreferencesEqualShouldReturnTrue() {
        assertTrue(SingleSignOnUtil.isCustomerPreferencesEqual(getTestCustomerPreferencesDTO1(), getTestCustomerPreferencesDTO1()));
    }
    
    @Test
    public void isCustomerPreferencesEqualShouldReturnFalse() {
        assertFalse(SingleSignOnUtil.isCustomerPreferencesEqual(getTestCustomerPreferencesDTO1(), new CustomerPreferencesDTO()));
    }
    
    @Test
    public void shouldUpdateLocalAddress() {
        AddressDTO ssoAddress = getTestAddressDTO1();
        AddressDTO localAddress = new AddressDTO();
        SingleSignOnUtil.updateLocalAddress(ssoAddress, localAddress);
        
        assertEquals(ssoAddress.getPostcode(), localAddress.getPostcode());
        assertEquals(ssoAddress.getHouseNameNumber(), localAddress.getHouseNameNumber());
        assertEquals(ssoAddress.getTown(), localAddress.getTown());
        assertEquals(ssoAddress.getStreet(), localAddress.getStreet());
    }
    
    @Test
    public void shouldUpdateLocalCustomer() {
        CustomerDTO ssoCustomer = getTestCustomerDTO1();
        CustomerDTO localCustomer = new CustomerDTO();
        SingleSignOnUtil.updateLocalCustomer(ssoCustomer, localCustomer);
        
        assertEquals(ssoCustomer.getTitle(), localCustomer.getTitle());
        assertEquals(ssoCustomer.getFirstName(), localCustomer.getFirstName());
        assertEquals(ssoCustomer.getInitials(), localCustomer.getInitials());
        assertEquals(ssoCustomer.getLastName(), localCustomer.getLastName());
        assertEquals(ssoCustomer.getEmailAddress(), localCustomer.getEmailAddress());
    }
    
    @Test
    public void shouldUpdateLocalContacts() {
        List<ContactDTO> ssoContacts = getContactDTOList();
        List<ContactDTO> localContacts = new ArrayList<>();
        ContactDTO localHomeContact = getTestContactDTOHomePhone1();
        localHomeContact.setValue(null);
        localContacts.add(localHomeContact);
        SingleSignOnUtil.updateLocalContacts(ssoContacts, localContacts);
        
        assertEquals(ssoContacts.size(), localContacts.size());
        assertEquals(VALUE_1, localHomeContact.getValue());
    }
    
    @Test
    public void shouldPopulateCustomerIdToContacts() {
        List<ContactDTO> contactList = getContactDTOList();
        SingleSignOnUtil.populateCustomerIdToContacts(contactList, CUSTOMER_ID_9);
        for (ContactDTO contact : contactList) {
            assertEquals(CUSTOMER_ID_9, contact.getCustomerId());
        }
    }
    
    @Test
    public void shouldUpdateLocalCustomerPreferences() {
        CustomerPreferencesDTO ssoPreference = getTestCustomerPreferencesDTO1();
        CustomerPreferencesDTO localPreference = new CustomerPreferencesDTO();
        SingleSignOnUtil.updateLocalCustomerPreferences(ssoPreference, localPreference);
        
        assertEquals(ssoPreference.getCanTflContact(), localPreference.getCanTflContact());
        assertEquals(ssoPreference.getCanThirdPartyContact(), localPreference.getCanThirdPartyContact());
    }
    
    @Test
    public void createSingleSignOnResponseDTOShouldReturnNull() {
        assertNull(SingleSignOnUtil.createSingleSignOnResponseDTO(null));
    }
    
    @Test
    public void shouldCreateSingleSignOnResponseDTO() {
        Object jsonResponse = SingleSignOnResponseTestUtil.createMockResponseDTO();
        assertNotNull(SingleSignOnUtil.createSingleSignOnResponseDTO(jsonResponse));
    }
    
    @Test
    public void shouldGenerateSingleSignOnChangeSet() {
        PersonalDetailsCmdImpl cmd = getTestPersonalDetailsCmd1();
        cmd.setTflMasterId(SSO_CUSTOMER_ID);
        cmd.setCanTflContact(Boolean.TRUE);
        cmd.setCanThirdPartyContact(Boolean.FALSE);
        
        Map<String, String> actualResult = SingleSignOnUtil.generateSingleSignOnChangeSet(cmd);
        
        assertTrue(actualResult.containsValue(SSO_CUSTOMER_ID.toString()));
        assertTrue(actualResult.containsValue(cmd.getFirstName()));
        assertTrue(actualResult.containsValue(cmd.getInitials()));
        assertTrue(actualResult.containsValue(cmd.getLastName()));
        assertTrue(actualResult.containsValue(cmd.getTitle()));
        assertTrue(actualResult.containsValue(cmd.getHouseNameNumber()));
        assertTrue(actualResult.containsValue(cmd.getStreet()));
        assertTrue(actualResult.containsValue(cmd.getTown()));
        assertTrue(actualResult.containsValue(cmd.getPostcode()));
        assertTrue(actualResult.containsValue(cmd.getEmailAddress()));
        assertEquals("1", actualResult.get("TflmarketingOptInFlag"));
        assertEquals("0", actualResult.get("TOCmarketingOptInFlag"));
    }
}
