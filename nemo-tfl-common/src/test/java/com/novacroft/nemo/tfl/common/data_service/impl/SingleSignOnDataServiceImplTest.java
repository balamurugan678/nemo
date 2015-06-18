package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.ADDRESS_ID_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.COUNTRY_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.ContactTestUtil.getContactDTOList;
import static com.novacroft.nemo.test_support.CustomerPreferencesTestUtil.getTestCustomerPreferencesDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getCustomerDTO;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.tfl.common.converter.single_sign_on.SingleSignOnConverter;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerPreferencesDataService;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerPreferencesDTO;
import com.novacroft.nemo.tfl.common.transfer.single_sign_on.SingleSignOnResponseDTO;

@RunWith(MockitoJUnitRunner.class)
public class SingleSignOnDataServiceImplTest {
    private static final Long TEST_NEW_ADDRESS_ID = 101L;
    private static final Long TEST_NEW_CUSTOMER_ID = 201L;
    
    @Mock
    private SingleSignOnDataServiceImpl mockService;
    @Mock
    private CustomerDataService mockCustomerDataService;
    @Mock
    private AddressDataService mockAddressDataService;
    @Mock
    private CountryDataService mockCountryDataService;
    @Mock
    private ContactDataService mockContactDataService;
    @Mock
    private CustomerPreferencesDataService mockPreferencesDataService;
    @Mock
    private SingleSignOnConverter mockSingleSignOnConverter;
    @Mock
    private SingleSignOnResponseDTO mockSsoResponseDTO;
    
    private CustomerDTO testCustomerDto;
    @Mock
    private AddressDTO mockAddressDto;
    @Mock
    private List<ContactDTO> mockContacts;
    @Mock
    private CustomerDTO mockCustomer;
    
    @Before
    public void setUp() {
        mockService.addressDataService = mockAddressDataService;
        mockService.customerDataService = mockCustomerDataService;
        mockService.contactDataService = mockContactDataService;
        mockService.countryDataService = mockCountryDataService;
        mockService.singleSignOnConverter = mockSingleSignOnConverter;
        mockService.customerPreferencesDataService = mockPreferencesDataService;
        
        testCustomerDto = getCustomerDTO(CUSTOMER_ID_1);
        
        when(mockSingleSignOnConverter.convertResponseToCustomerDto(mockSsoResponseDTO))
                    .thenReturn(testCustomerDto);
        when(mockSingleSignOnConverter.convertResponseToAddressDto(mockSsoResponseDTO))
                    .thenReturn(getTestAddressDTO1());
        when(mockSingleSignOnConverter.convertResponseToContactDtoList(mockSsoResponseDTO))
                    .thenReturn(getContactDTOList());
    }
    
    @Test
    public void checkAndUpdateLocalDataShouldCreateNewRecord() {
        when(mockService.checkAndUpdateLocalData(mockSsoResponseDTO)).thenCallRealMethod();
        
        when(mockCustomerDataService.findByTflMasterId(anyLong())).thenReturn(null);
        when(mockAddressDto.getId()).thenReturn(TEST_NEW_ADDRESS_ID);
        when(mockService.checkAndUpdateLocalAddress(any(AddressDTO.class), anyLong())).thenReturn(mockAddressDto);
        when(mockCustomer.getId()).thenReturn(TEST_NEW_CUSTOMER_ID);
        when(mockService.createNewCustomer(any(CustomerDTO.class), anyLong())).thenReturn(mockCustomer);
        
        mockService.checkAndUpdateLocalData(mockSsoResponseDTO);
        
        verify(mockService).createNewCustomer(any(CustomerDTO.class), anyLong());
        verify(mockService).checkAndUpdateLocalAddress(any(AddressDTO.class), anyLong());
        verify(mockService).checkAndUpdateLocalContacts(anyListOf(ContactDTO.class), anyLong());
    }
    
    @Test
    public void checkAndUpdateLocalDataShouldUpdateRecord() {
        when(mockService.checkAndUpdateLocalData(mockSsoResponseDTO)).thenCallRealMethod();

        when(mockService.checkAndUpdateLocalCustomer(any(CustomerDTO.class), any(CustomerDTO.class)))
                .thenReturn(testCustomerDto);
        when(mockService.checkAndUpdateLocalAddress(any(AddressDTO.class), anyLong()))
                .thenReturn(null);
        when(mockService.checkAndUpdateLocalContacts(anyListOf(ContactDTO.class), anyLong()))
                .thenReturn(null);
        when(mockCustomerDataService.findByTflMasterId(anyLong())).thenReturn(testCustomerDto);
        
        mockService.checkAndUpdateLocalData(mockSsoResponseDTO);
        
        verify(mockService).checkAndUpdateLocalCustomer(any(CustomerDTO.class), any(CustomerDTO.class));
        verify(mockService).checkAndUpdateLocalAddress(any(AddressDTO.class), anyLong());
        verify(mockService).checkAndUpdateLocalContacts(anyListOf(ContactDTO.class), anyLong());
    }
    
    @Test
    public void shouldCheckAndUpdateLocalCustomer() {
        when(mockService.checkAndUpdateLocalCustomer(any(CustomerDTO.class), any(CustomerDTO.class))).thenCallRealMethod();
        
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).then(returnsFirstArg());
        
        CustomerDTO actualResult = mockService.checkAndUpdateLocalCustomer(testCustomerDto, new CustomerDTO());
        
        assertNotNull(actualResult);
        verify(mockCustomerDataService).createOrUpdate(any(CustomerDTO.class));
        assertEquals(FIRST_NAME_1, actualResult.getFirstName());       
    }
    
    @Test
    public void shouldCheckAndUpdateLocalAddress() {
        when(mockService.checkAndUpdateLocalAddress(any(AddressDTO.class), anyLong())).thenCallRealMethod();
        
        AddressDTO localAddress = new AddressDTO();
        localAddress.setId(ADDRESS_ID_1);
        localAddress.setCountry(new CountryDTO());
        when(mockAddressDataService.findById(anyLong())).thenReturn(localAddress);
        when(mockCountryDataService.findCountryByName(anyString())).thenReturn(new CountryDTO());
        when(mockAddressDataService.createOrUpdate(any(AddressDTO.class))).then(returnsFirstArg());
        
        AddressDTO actualResult = mockService.checkAndUpdateLocalAddress(getTestAddressDTO1(), ADDRESS_ID_1);
        
        assertNotNull(actualResult);
        verify(mockAddressDataService).findById(ADDRESS_ID_1);
        verify(mockCountryDataService).findCountryByName(COUNTRY_1);
        verify(mockAddressDataService).createOrUpdate(any(AddressDTO.class));
    }
    
    @Test
    public void shouldCheckAndUpdateLocalContacts() {
        when(mockService.checkAndUpdateLocalContacts(anyListOf(ContactDTO.class), anyLong())).thenCallRealMethod();
        
        when(mockContactDataService.findPhoneNumbersByCustomerId(anyLong())).thenReturn(null);
        when(mockContactDataService.createOrUpdateAll(anyListOf(ContactDTO.class))).then(returnsFirstArg());
        
        List<ContactDTO> actualResult = mockService.checkAndUpdateLocalContacts(getContactDTOList(), CUSTOMER_ID_1);
        
        assertNotNull(actualResult);
        verify(mockContactDataService).findPhoneNumbersByCustomerId(CUSTOMER_ID_1);
        verify(mockContactDataService).createOrUpdateAll(anyListOf(ContactDTO.class));
    }
    
    @Test
    public void shouldCreateNewCustomer() {
        when(mockService.createNewCustomer(any(CustomerDTO.class), anyLong())).thenCallRealMethod();
        
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).then(returnsFirstArg());
        
        CustomerDTO actualResult = mockService.createNewCustomer(new CustomerDTO(), ADDRESS_ID_1);
        
        assertNotNull(actualResult);
        assertEquals(ADDRESS_ID_1, actualResult.getAddressId());
    }
    
    @Test
    public void shouldCheckAndUpdateCustomerPreferences() {
        when(mockService.checkAndUpdateCustomerPreferences(any(CustomerPreferencesDTO.class), anyLong())).thenCallRealMethod();
        
        when(mockPreferencesDataService.findByCustomerId(anyLong())).thenReturn(null);
        when(mockPreferencesDataService.createOrUpdate(any(CustomerPreferencesDTO.class))).then(returnsFirstArg());
        
        CustomerPreferencesDTO actualResult = mockService.checkAndUpdateCustomerPreferences(getTestCustomerPreferencesDTO1(), CUSTOMER_ID_1);
        
        assertNotNull(actualResult);
        verify(mockPreferencesDataService).findByCustomerId(CUSTOMER_ID_1);
        verify(mockPreferencesDataService).createOrUpdate(any(CustomerPreferencesDTO.class));
    }
}
