package com.novacroft.nemo.tfl.common.application_service.impl;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerPreferencesDataService;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AddressTestUtil.ADDRESS_ID_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.CONTACT_ID_1;
import static com.novacroft.nemo.test_support.ContactTestUtil.getTestContactDTOHomePhone1;
import static com.novacroft.nemo.test_support.ContactTestUtil.getTestContactDTOMobilePhone1;
import static com.novacroft.nemo.test_support.CustomerPreferencesTestUtil.getTestCustomerPreferencesDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.*;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.getTestWebAccountDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Unit tests for PersonalDetailsService
 */
public class PersonalDetailsServiceTest {

    @Test
    public void shouldGetWebAccountWithUsername() {
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerDataService = mockCustomerDataService;
        CustomerDTO result = service.getCustomer(USERNAME_1, null);
        assertEquals(getTestWebAccountDTO1().getId(), result.getId());
        verify(mockCustomerDataService).findByUsernameOrEmail(anyString());
    }

    @Test
    public void shouldGetWebAccountWithCustomerId() {
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findByCustomerId(anyLong())).thenReturn(getTestCustomerDTO1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerDataService = mockCustomerDataService;
        CustomerDTO result = service.getCustomer(null, CUSTOMER_ID_1);
        assertEquals(getTestWebAccountDTO1().getId(), result.getId());
        verify(mockCustomerDataService).findByCustomerId(anyLong());
    }

    @Test(expected = AssertionError.class)
    public void addWebAccountToCommandShouldFailWithNullWebAccount() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = mock(PersonalDetailsServiceImpl.class);
        when(service.getCustomer(anyString(), anyLong())).thenReturn(null);
        doCallRealMethod().when(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
        service.addCustomerToCommand(cmd, USERNAME_1, null);
        verify(service).getCustomer(anyString(), anyLong());
        verify(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
    }

    @Test(expected = AssertionError.class)
    public void addWebAccountToCommandShouldFailWithNullCustomerId() {
        CustomerDTO testCustomerDTO = getTestCustomerDTO1();
        testCustomerDTO.setId(null);
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = mock(PersonalDetailsServiceImpl.class);
        when(service.getCustomer(anyString(), anyLong())).thenReturn(testCustomerDTO);
        doCallRealMethod().when(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
        service.addCustomerToCommand(cmd, USERNAME_1, null);
        verify(service).getCustomer(anyString(), anyLong());
        verify(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
    }

    @Test
    public void shouldAddCustomerToCommandTest() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = mock(PersonalDetailsServiceImpl.class);
        when(service.getCustomer(anyString(), anyLong())).thenReturn(getTestCustomerDTO1());
        doCallRealMethod().when(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
        service.addCustomerToCommand(cmd, USERNAME_1, null);
        assertEquals(getTestWebAccountDTO1().getId(), cmd.getCustomerId());
        verify(service).getCustomer(anyString(), anyLong());
        verify(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
    }

    @Test(expected = AssertionError.class)
    public void addCustomerToCommandShouldFailWithNullCustomerId() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCustomerId(null);
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.addCustomerToCommand(cmd);
    }

    @Test(expected = AssertionError.class)
    public void addCustomerToCommandShouldFailWithNullCustomer() {
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findByCustomerId(anyLong())).thenReturn(null);
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCustomerId(CUSTOMER_ID_1);
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerDataService = mockCustomerDataService;
        service.addCustomerToCommand(cmd);
    }

    @Test
    public void shouldAddCustomerToCommand() {
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCustomerDTO1());
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCustomerId(CUSTOMER_ID_1);
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerDataService = mockCustomerDataService;
        service.addCustomerToCommand(cmd);
        assertEquals(getTestCustomerDTO1().getLastName(), cmd.getLastName());
    }

    @Test(expected = AssertionError.class)
    public void addAddressToCommandShouldFailWithNullAddressId() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setAddressId(null);
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.addAddressToCommand(cmd);
    }

    @Test(expected = AssertionError.class)
    public void addAddressToCommandShouldFailWithNullAddress() {
        AddressDataService mockAddressDataService = mock(AddressDataService.class);
        when(mockAddressDataService.findById(anyLong())).thenReturn(null);
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setAddressId(ADDRESS_ID_1);
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.addressDataService = mockAddressDataService;
        service.addAddressToCommand(cmd);
        verify(mockAddressDataService.findById(anyLong()));
    }

    @Test
    public void shouldAddAddressToCommand() {
        AddressDataService mockAddressDataService = mock(AddressDataService.class);
        when(mockAddressDataService.findById(anyLong())).thenReturn(getTestAddressDTO1());
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setAddressId(ADDRESS_ID_1);
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.addressDataService = mockAddressDataService;
        service.addAddressToCommand(cmd);
        assertEquals(getTestAddressDTO1().getStreet(), cmd.getStreet());
    }

    @Test
    public void addHomePhoneContactToCommandShouldNotAddNullContact() {
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findHomePhoneByCustomerId(anyLong())).thenReturn(null);
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.addHomePhoneContactToCommand(cmd);
        verify(mockContactDataService).findHomePhoneByCustomerId(anyLong());
    }

    @Test
    public void shouldAddHomePhoneContactToCommand() {
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findHomePhoneByCustomerId(anyLong())).thenReturn(getTestContactDTOHomePhone1());
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.addHomePhoneContactToCommand(cmd);
        assertEquals(getTestContactDTOHomePhone1().getValue(), cmd.getHomePhone());
    }

    @Test
    public void addMobilePhoneContactToCommandShouldNotAddNullContact() {
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findMobilePhoneByCustomerId(anyLong())).thenReturn(null);
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.addMobilePhoneContactToCommand(cmd);
        verify(mockContactDataService).findMobilePhoneByCustomerId(anyLong());
    }

    @Test
    public void shouldAddMobilePhoneContactToCommand() {
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findMobilePhoneByCustomerId(anyLong())).thenReturn(getTestContactDTOMobilePhone1());
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.addMobilePhoneContactToCommand(cmd);
        assertEquals(getTestContactDTOMobilePhone1().getValue(), cmd.getMobilePhone());
    }

    @Test
    public void shouldAddCustomerPreferencesToCommand() {
        CustomerPreferencesDataService mockCustomerPreferencesDataService = mock(CustomerPreferencesDataService.class);
        when(mockCustomerPreferencesDataService.findByCustomerId(anyLong())).thenReturn(getTestCustomerPreferencesDTO1());
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerPreferencesDataService = mockCustomerPreferencesDataService;
        service.addPreferencesToCommand(cmd);
        assertEquals(getTestCustomerPreferencesDTO1().getCanTflContact(), cmd.getCanTflContact());
        verify(mockCustomerPreferencesDataService).findByCustomerId(anyLong());
    }

    @Test
    public void addCustomerPreferencesToCommandShouldNotAddNullCustomerPreferences() {
        CustomerPreferencesDataService mockCustomerPreferencesDataService = mock(CustomerPreferencesDataService.class);
        when(mockCustomerPreferencesDataService.findByCustomerId(anyLong())).thenReturn(null);
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerPreferencesDataService = mockCustomerPreferencesDataService;
        service.addPreferencesToCommand(cmd);
        verify(mockCustomerPreferencesDataService).findByCustomerId(anyLong());
    }

    @Test
    public void shouldGetPersonalDetails() {
        PersonalDetailsServiceImpl service = mock(PersonalDetailsServiceImpl.class);
        when(service.getPersonalDetails(anyString(), anyLong())).thenCallRealMethod();
        doNothing().when(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
        doNothing().when(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).addAddressToCommand(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).addHomePhoneContactToCommand(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).addMobilePhoneContactToCommand(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).addPreferencesToCommand(any(PersonalDetailsCmdImpl.class));
        service.getPersonalDetails(USERNAME_1, null);
        verify(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class), anyString(), anyLong());
        verify(service).addCustomerToCommand(any(PersonalDetailsCmdImpl.class));
        verify(service).addAddressToCommand(any(PersonalDetailsCmdImpl.class));
        verify(service).addHomePhoneContactToCommand(any(PersonalDetailsCmdImpl.class));
        verify(service).addMobilePhoneContactToCommand(any(PersonalDetailsCmdImpl.class));
        verify(service).addPreferencesToCommand(any(PersonalDetailsCmdImpl.class));
    }

    @Test
    public void shouldGetPersonalDetailsWithUsername() {
        PersonalDetailsServiceImpl service = mock(PersonalDetailsServiceImpl.class);
        when(service.getPersonalDetails(anyString())).thenCallRealMethod();
        when(service.getPersonalDetails(anyString(), anyLong())).thenReturn(null);
        service.getPersonalDetails(USERNAME_1);
        verify(service).getPersonalDetails(anyString(), anyLong());
    }

    @Test
    public void shouldGetPersonalDetailsByCustomerId() {
        PersonalDetailsServiceImpl service = mock(PersonalDetailsServiceImpl.class);
        when(service.getPersonalDetailsByCustomerId(anyLong())).thenCallRealMethod();
        when(service.getPersonalDetails(anyString(), anyLong())).thenReturn(null);
        service.getPersonalDetailsByCustomerId(CUSTOMER_ID_1);
        verify(service).getPersonalDetails(anyString(), anyLong());
    }

    @Test
    public void shouldUpdatePersonalDetails() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = mock(PersonalDetailsServiceImpl.class);
        when(service.updatePersonalDetails(any(PersonalDetailsCmdImpl.class))).thenCallRealMethod();
        doNothing().when(service).updateCustomer(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).updateAddress(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).updateHomePhoneContact(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).updateMobilePhoneContact(any(PersonalDetailsCmdImpl.class));
        doNothing().when(service).updatePreferences(any(PersonalDetailsCmdImpl.class));
        service.updatePersonalDetails(cmd);
        verify(service).updateCustomer(any(PersonalDetailsCmdImpl.class));
        verify(service).updateAddress(any(PersonalDetailsCmdImpl.class));
        verify(service).updateHomePhoneContact(any(PersonalDetailsCmdImpl.class));
        verify(service).updateMobilePhoneContact(any(PersonalDetailsCmdImpl.class));
        verify(service).updatePreferences(any(PersonalDetailsCmdImpl.class));
    }

    @Test(expected = AssertionError.class)
    public void updateCustomerShouldFailWithNullCustomer() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        service.customerDataService = mockCustomerDataService;
        when(mockCustomerDataService.findById(anyLong())).thenReturn(null);
        service.updateCustomer(cmd);
        verify(mockCustomerDataService).findById(anyLong());
    }

    @Test
    public void shouldUpdateCustomer() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCustomerId(CUSTOMER_ID_1);
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCustomerDTO1());
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).thenReturn(getTestCustomerDTO1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerDataService = mockCustomerDataService;
        service.updateCustomer(cmd);
        assertEquals(getTestCustomerDTO1().getLastName(), cmd.getLastName());
        verify(mockCustomerDataService).findByCustomerId(CUSTOMER_ID_1);
        verify(mockCustomerDataService).createOrUpdate(any(CustomerDTO.class));
    }

    @Test(expected = AssertionError.class)
    public void updateAddressShouldFailWithNullAddress() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        AddressDataService mockAddressDataService = mock(AddressDataService.class);
        service.addressDataService = mockAddressDataService;
        when(mockAddressDataService.findById(anyLong())).thenReturn(null);
        service.updateAddress(cmd);
        verify(mockAddressDataService).findById(anyLong());
    }

    @Test
    public void shouldUpdateAddress() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setAddressId(ADDRESS_ID_1);
        AddressDataService mockAddressDataService = mock(AddressDataService.class);
        when(mockAddressDataService.findById(ADDRESS_ID_1)).thenReturn(getTestAddressDTO1());
        when(mockAddressDataService.createOrUpdate(any(AddressDTO.class))).thenReturn(getTestAddressDTO1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.addressDataService = mockAddressDataService;
        service.updateAddress(cmd);
        assertEquals(getTestAddressDTO1().getStreet(), cmd.getStreet());
        verify(mockAddressDataService).findById(ADDRESS_ID_1);
        verify(mockAddressDataService).createOrUpdate(any(AddressDTO.class));

    }

    @Test(expected = AssertionError.class)
    public void updateWebAccountShouldFailWithNullWebAccount() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        service.customerDataService = mockCustomerDataService;
        when(mockCustomerDataService.findById(anyLong())).thenReturn(null);
        service.updateCustomer(cmd);
        verify(mockCustomerDataService).findById(anyLong());
    }

    @Test
    public void updateHomePhoneContactShouldCreate() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findById(anyLong())).thenReturn(getTestContactDTOHomePhone1());
        when(mockContactDataService.createOrUpdateHomePhone(any(ContactDTO.class))).thenReturn(getTestContactDTOHomePhone1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.updateHomePhoneContact(cmd);
        verify(mockContactDataService, never()).findById(anyLong());
        verify(mockContactDataService).createOrUpdateHomePhone(any(ContactDTO.class));
    }

    @Test
    public void updateHomePhoneContactShouldUpdate() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setHomePhoneContactId(CONTACT_ID_1);
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findById(anyLong())).thenReturn(getTestContactDTOHomePhone1());
        when(mockContactDataService.createOrUpdateHomePhone(any(ContactDTO.class))).thenReturn(getTestContactDTOHomePhone1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.updateHomePhoneContact(cmd);
        verify(mockContactDataService).findById(anyLong());
        verify(mockContactDataService).createOrUpdateHomePhone(any(ContactDTO.class));
    }

    @Test
    public void updateMobilePhoneContactShouldCreate() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findById(anyLong())).thenReturn(getTestContactDTOMobilePhone1());
        when(mockContactDataService.createOrUpdateMobilePhone(any(ContactDTO.class)))
                .thenReturn(getTestContactDTOMobilePhone1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.updateMobilePhoneContact(cmd);
        verify(mockContactDataService, never()).findById(anyLong());
        verify(mockContactDataService).createOrUpdateMobilePhone(any(ContactDTO.class));
    }

    @Test
    public void updateMobilePhoneContactShouldUpdate() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setMobilePhoneContactId(CONTACT_ID_1);
        ContactDataService mockContactDataService = mock(ContactDataService.class);
        when(mockContactDataService.findById(anyLong())).thenReturn(getTestContactDTOMobilePhone1());
        when(mockContactDataService.createOrUpdateMobilePhone(any(ContactDTO.class)))
                .thenReturn(getTestContactDTOMobilePhone1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.contactDataService = mockContactDataService;
        service.updateMobilePhoneContact(cmd);
        verify(mockContactDataService).findById(anyLong());
        verify(mockContactDataService).createOrUpdateMobilePhone(any(ContactDTO.class));
    }

    @Test
    public void updateCustomerPreferencesShouldCreate() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        CustomerPreferencesDataService mockCustomerPreferencesDataService = mock(CustomerPreferencesDataService.class);
        when(mockCustomerPreferencesDataService.findById(anyLong())).thenReturn(getTestCustomerPreferencesDTO1());
        when(mockCustomerPreferencesDataService.createOrUpdate(any(CustomerPreferencesDTO.class)))
                .thenReturn(getTestCustomerPreferencesDTO1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerPreferencesDataService = mockCustomerPreferencesDataService;
        service.updatePreferences(cmd);
        verify(mockCustomerPreferencesDataService, never()).findById(anyLong());
        verify(mockCustomerPreferencesDataService).createOrUpdate(any(CustomerPreferencesDTO.class));
    }

    @Test
    public void updateCustomerPreferencesShouldUpdate() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCustomerId(CS_CUSTOMERID_1);
        CustomerPreferencesDataService mockCustomerPreferencesDataService = mock(CustomerPreferencesDataService.class);
        when(mockCustomerPreferencesDataService.findByCustomerId(anyLong())).thenReturn(getTestCustomerPreferencesDTO1());
        when(mockCustomerPreferencesDataService.createOrUpdate(any(CustomerPreferencesDTO.class)))
                .thenReturn(getTestCustomerPreferencesDTO1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerPreferencesDataService = mockCustomerPreferencesDataService;
        service.updatePreferences(cmd);
        verify(mockCustomerPreferencesDataService).findByCustomerId(anyLong());
        verify(mockCustomerPreferencesDataService).createOrUpdate(any(CustomerPreferencesDTO.class));
    }

    @Test
    public void shouldUpdateCustomerWithDeactivatedAccount() {
        PersonalDetailsCmdImpl cmd = new PersonalDetailsCmdImpl();
        cmd.setCustomerId(CUSTOMER_ID_1);
        CustomerDataService mockCustomerDataService = mock(CustomerDataService.class);
        when(mockCustomerDataService.findByCustomerId(CUSTOMER_ID_1)).thenReturn(getTestCustomerDTO1());
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).thenReturn(getTestCustomerDTO1());
        PersonalDetailsServiceImpl service = new PersonalDetailsServiceImpl();
        service.customerDataService = mockCustomerDataService;
        service.updateCustomer(cmd);
        assertEquals(getTestCustomerDTO1().getLastName(), cmd.getLastName());
        assertTrue(cmd.isShowWebAccountDeactivationEnableFlag());
        verify(mockCustomerDataService).findByCustomerId(CUSTOMER_ID_1);
        verify(mockCustomerDataService).createOrUpdate(any(CustomerDTO.class));
    }
}
