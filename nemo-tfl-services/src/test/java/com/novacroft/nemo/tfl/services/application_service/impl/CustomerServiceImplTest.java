package com.novacroft.nemo.tfl.services.application_service.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.ContactTestUtil.getContactDTOList;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTOWithEmail;
import static com.novacroft.nemo.tfl.services.test_support.CustomerServiceTestUtil.getTestCustomer1;
import static com.novacroft.nemo.tfl.services.test_support.CustomerServiceTestUtil.getTestCustomerEmailIsEmpty;
import static com.novacroft.nemo.tfl.services.test_support.CustomerServiceTestUtil.getTestCustomerNullId;
import static com.novacroft.nemo.tfl.services.test_support.CustomerServiceTestUtil.getTestCustomerWithError;
import static com.novacroft.nemo.tfl.services.test_support.ErrorResultTestUtil.ERROR_DESCRIPTION_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.validator.CommonAddressValidator;
import com.novacroft.nemo.common.validator.CustomerNameValidator;
import com.novacroft.nemo.test_support.AddressTestUtil;
import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.ExternalUserDataService;
import com.novacroft.nemo.tfl.common.form_validator.CommonCustomerRegistrationValidator;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.services.converter.CustomerConverter;
import com.novacroft.nemo.tfl.services.test_support.CustomerServiceTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;
import com.novacroft.nemo.tfl.services.transfer.ErrorResult;

public class CustomerServiceImplTest {

    private CustomerServiceImpl customerServiceImpl;

    private CustomerDataService mockCustomerDataService;
    private AddressDataService mockAddressDataService;
    private ContactDataService mockContactDataService;
    private CustomerConverter mockCustomerConverter;
    private CommonCustomerRegistrationValidator mockCommonCustomerRegistrationValidator;
    private CommonAddressValidator mockCommonAddressValidator;
    private CustomerNameValidator mockCustomerNameValidator;
    private ExternalUserDataService mockExternalUserDataService;

    private CustomerService customerService;
    private Errors errors;
    private DeleteCustomer mockDeleteCustomer;
    private Customer mockCustomer;

    @Before
    public void setUp() throws Exception {
        customerServiceImpl = mock(CustomerServiceImpl.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockAddressDataService = mock(AddressDataService.class);
        mockContactDataService = mock(ContactDataService.class);
        mockCustomerConverter = mock(CustomerConverter.class);
        mockCommonCustomerRegistrationValidator = mock(CommonCustomerRegistrationValidator.class);
        mockCommonAddressValidator = mock(CommonAddressValidator.class);
        mockCustomerNameValidator = mock(CustomerNameValidator.class);
        mockExternalUserDataService = mock(ExternalUserDataService.class);

        customerService = mock(CustomerService.class);
        mockCommonCustomerRegistrationValidator.customerService = customerService;

        customerServiceImpl.commonAddressValidator = mockCommonAddressValidator;
        customerServiceImpl.commonCustomerRegistrationValidator = mockCommonCustomerRegistrationValidator;
        customerServiceImpl.customerNameValidator = mockCustomerNameValidator;
        customerServiceImpl.customerConverter = mockCustomerConverter;
        customerServiceImpl.contactDataService = mockContactDataService;
        customerServiceImpl.addressDataService = mockAddressDataService;
        customerServiceImpl.customerDataService = mockCustomerDataService;
        customerServiceImpl.externalUserDataService = mockExternalUserDataService;

        mockDeleteCustomer = mock(DeleteCustomer.class);
        errors = new BeanPropertyBindingResult(getTestCustomer1(), "customer");
        mockCustomer = mock(Customer.class);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldGetCustomerById() {
        Customer mockReturnCustomer = mock(Customer.class);
        when(customerServiceImpl.getCustomerByExternalId(any(Long.class))).thenCallRealMethod();
        when(mockCustomerDataService.findByExternalId(any(Long.class))).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());
        when(mockAddressDataService.findById(any(Long.class))).thenReturn(AddressTestUtil.getTestAddressDTO1());
        when(mockContactDataService.findPhoneNumbersByCustomerId(any(Long.class))).thenReturn(getContactDTOList());
        when(mockCustomerConverter.convertToCustomer((CustomerDTO) any(), (AddressDTO) any(), (List<ContactDTO>) any())).thenReturn(
                        mockReturnCustomer);
        Customer customer = customerServiceImpl.getCustomerByExternalId(CUSTOMER_ID_1);
        assertEquals(mockReturnCustomer, customer);
    }

    @Test
    public void shouldNotGetCustomerByIdIfExternalIdInvalid() {
        when(customerServiceImpl.getCustomerByExternalId(any(Long.class))).thenCallRealMethod();
        BDDMockito.willThrow(new RuntimeException()).given(mockCustomerDataService).findByExternalId(anyLong());
        Customer customer = customerServiceImpl.getCustomerByExternalId(CUSTOMER_ID_1);
        assertNull(customer);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createCustomerNoErrors() {
        when(customerServiceImpl.createCustomer(any(Customer.class), anyString())).thenCallRealMethod().thenReturn(getTestCustomer1());
        when(customerServiceImpl.checkCustomerForNull(any(Customer.class))).thenReturn(getTestCustomer1());
        when(customerServiceImpl.checkCustomerId(any(Customer.class), (Long) any())).thenReturn(getTestCustomer1());
        when(mockCustomerConverter.convertToAddressDTO((Customer) any())).thenReturn(getTestAddressDTO1());
        when(mockAddressDataService.createOrUpdate((AddressDTO) any())).thenReturn(getTestAddressDTO1());
        when(mockCustomerConverter.convertToCustomerDTO(any(Customer.class))).thenReturn(getTestCustomerDTOWithEmail());
        when(customerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(mockCustomerDataService.createOrUpdate(any(CustomerDTO.class))).thenReturn(getTestCustomerDTOWithEmail());
        when(customerServiceImpl.createOrUpdateCustomer(any(Customer.class), any(AddressDTO.class), any(CustomerDTO.class), (List<ContactDTO>) any()))
                        .thenCallRealMethod();
        when(mockCustomerConverter.convertToCustomerDTO(any(Customer.class))).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToCustomer(any(CustomerDTO.class), any(AddressDTO.class), any(List.class))).thenReturn(getTestCustomer1());
        Customer customer = customerServiceImpl.createCustomer(getTestCustomer1(), USERNAME_1);
        assertNotNull(customer);
        assertNull(customer.getErrors());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void createCustomerNullAddressIdAndCustomerId() {
        when(customerServiceImpl.createCustomer(any(Customer.class), anyString())).thenCallRealMethod().thenReturn(getTestCustomer1());
        when(customerServiceImpl.checkCustomerForNull(any(Customer.class))).thenReturn(getTestCustomer1());
        when(customerServiceImpl.checkCustomerId(any(Customer.class), (Long) any())).thenReturn(getTestCustomer1());
        when(customerServiceImpl.getContent(anyString())).thenReturn(ERROR_DESCRIPTION_1);
        when(mockCustomerConverter.convertToCustomerDTO((Customer) any())).thenReturn(CustomerTestUtil.getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToAddressDTO((Customer) any())).thenReturn(AddressTestUtil.getTestAddressDTO1());
        when(mockAddressDataService.createOrUpdate((AddressDTO) any())).thenReturn(null);
        when(mockCustomerDataService.createOrUpdate((CustomerDTO) any())).thenReturn(null);
        when(customerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        when(customerServiceImpl.createOrUpdateCustomer(any(Customer.class), any(AddressDTO.class), any(CustomerDTO.class), (List<ContactDTO>) any()))
                        .thenReturn(CustomerServiceTestUtil.getTestCustomer1());
        Customer customer = customerServiceImpl.createCustomer(CustomerServiceTestUtil.getTestCustomer1(), USERNAME_1);
        assertNotNull(customer);
        assertNull(customer.getErrors());
    }

    @Test
    public void cannotCreateCustomerIfNull() {
        when(customerServiceImpl.createCustomer(any(Customer.class), anyString())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerForNull(any(Customer.class))).thenReturn(getTestCustomerWithError());
        Customer customer = customerServiceImpl.createCustomer(getTestCustomerWithError(), USERNAME_1);
        assertNotNull(customer);
        assertNotNull(customer.getErrors());
    }

    @Test
    public void cannotCreateCustomerIfCreateOrUpdateFails() {
        when(customerServiceImpl.createCustomer(any(Customer.class), anyString())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerForNull(any(Customer.class))).thenReturn(getTestCustomer1());
        when(customerServiceImpl.checkCustomerId(any(Customer.class), (Long) any())).thenReturn(getTestCustomer1());
        when(mockCustomerConverter.convertToCustomerDTO((Customer) any())).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToAddressDTO((Customer) any())).thenReturn(getTestAddressDTO1());
        when(customerService.isEmailAddressAlreadyUsed(anyString())).thenReturn(false);
        doCallRealMethod().when(customerServiceImpl).validate((Errors) any(), (CustomerDTO) any(), (AddressDTO) any(), anyBoolean());
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                BeanPropertyBindingResult e = (BeanPropertyBindingResult) args[0];
                e.addError(new ObjectError("p", "q"));
                return e;
            }
        }).when(customerServiceImpl).validate((Errors) any(), (CustomerDTO) any(), (AddressDTO) any(), anyBoolean());
        customerServiceImpl.validate(errors, getTestCustomerDTOWithEmail(), getTestAddressDTO1(), true);
        Customer customer = customerServiceImpl.createCustomer(getTestCustomerWithError(), USERNAME_1);
        assertNotNull(customer);
        assertNotNull(customer.getErrors());
    }

    @Test
    public void initialisationTest() {
        assertNotNull(new CustomerServiceImpl());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldUpdateCustomer() {
        when(customerServiceImpl.updateCustomer((Customer) any(), anyLong(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerForNull((Customer) any())).thenReturn(getTestCustomer1());
        when(customerServiceImpl.checkCustomerId(any(Customer.class), (Long) any())).thenReturn(getTestCustomer1());
        when(customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer((Long) any(), anyString())).thenReturn(true);
        when(mockCustomerDataService.findByExternalId(anyLong())).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToCustomerDTO((Customer) any(), (CustomerDTO) any())).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToAddressDTO((Customer) any(), (AddressDTO) any())).thenReturn(getTestAddressDTO1());
        when(mockContactDataService.findPhoneNumbersByCustomerId(anyLong())).thenReturn(getContactDTOList());
        when(mockCustomerConverter.updateContactDTOs((Customer) any(), (List<ContactDTO>) any())).thenReturn(getContactDTOList());
        when(customerServiceImpl.createOrUpdateCustomer((Customer) any(), (AddressDTO) any(), (CustomerDTO) any(), (List<ContactDTO>) any()))
                        .thenReturn(CustomerServiceTestUtil.getTestCustomer1());
        Customer customer = customerServiceImpl.updateCustomer(getTestCustomer1(), CUSTOMER_ID_1, USERNAME_1);
        assertNotNull(customer);
        assertNull(customer.getErrors());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotUpdateCustomerWhenValidationFails() {
        when(customerServiceImpl.updateCustomer((Customer) any(), anyLong(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerForNull((Customer) any())).thenReturn(getTestCustomerEmailIsEmpty());
        when(customerServiceImpl.checkCustomerId((Customer) any(), (Long) any())).thenReturn(getTestCustomerEmailIsEmpty());
        when(customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer((Long) any(), anyString())).thenReturn(true);
        when(mockCustomerDataService.findByExternalId(anyLong())).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToCustomerDTO((Customer) any(), (CustomerDTO) any())).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToAddressDTO((Customer) any(), (AddressDTO) any())).thenReturn(getTestAddressDTO1());
        when(mockContactDataService.findPhoneNumbersByCustomerId(anyLong())).thenReturn(getContactDTOList());
        when(mockCustomerConverter.updateContactDTOs((Customer) any(), (List<ContactDTO>) any())).thenReturn(getContactDTOList());
        doCallRealMethod().when(customerServiceImpl).validate((Errors) any(), (CustomerDTO) any(), (AddressDTO) any(), anyBoolean());
        doAnswer(new Answer<Object>() {
            public Object answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                BeanPropertyBindingResult e = (BeanPropertyBindingResult) args[0];
                e.addError(new ObjectError("s", "k"));
                return e;
            }
        }).when(customerServiceImpl).validate((Errors) any(), (CustomerDTO) any(), (AddressDTO) any(), anyBoolean());
        customerServiceImpl.validate(errors, getTestCustomerDTOWithEmail(), getTestAddressDTO1(), true);
        Customer customer = customerServiceImpl.updateCustomer(getTestCustomerEmailIsEmpty(), CUSTOMER_ID_1, USERNAME_1);
        assertNotNull(customer);
        assertNotNull(customer.getErrors());
    }

    @Test
    public void shouldNotUpdateCustomerIfExternalIdInvalid() {
        when(customerServiceImpl.updateCustomer((Customer) any(), (Long) any(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerForNull((Customer) any())).thenReturn(getTestCustomerEmailIsEmpty());
        when(customerServiceImpl.checkCustomerId((Customer) any(), (Long) any())).thenReturn(getTestCustomerEmailIsEmpty());
        Customer customer = customerServiceImpl.updateCustomer(getTestCustomerEmailIsEmpty(), CUSTOMER_ID_1, USERNAME_1);
        assertNotNull(customer);
        assertNotNull(customer.getErrors());
    }
    
    @Test
    public void shouldNotUpdateCustomerIfCustomerIsNull() {
        when(customerServiceImpl.updateCustomer((Customer) any(), anyLong(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerForNull((Customer) any())).thenCallRealMethod();
        when(customerServiceImpl.createCustomerError()).thenCallRealMethod();
        Customer customer = customerServiceImpl.updateCustomer(null, CUSTOMER_ID_1, USERNAME_1);
        assertNotNull(customer);
        assertNotNull(customer.getErrors());
    }

    @Test
    public void shouldNotUpdateCustomerIfAnExceptionIsThrown() {
        when(customerServiceImpl.updateCustomer((Customer) any(), anyLong(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerForNull((Customer) any())).thenCallRealMethod();
        when(customerServiceImpl.checkCustomerId((Customer) any(), anyLong())).thenCallRealMethod();
        BDDMockito.willThrow(new RuntimeException()).given(mockCustomerDataService).findByExternalId(anyLong());
        Customer customer = customerServiceImpl.updateCustomer(getTestCustomerEmailIsEmpty(), CUSTOMER_ID_1, USERNAME_1);
        assertNotNull(customer);
        assertNotNull(customer.getErrors());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRunCreateForCreateOrUpdateCustomer() {
        when(customerServiceImpl.createOrUpdateCustomer((Customer) any(), (AddressDTO) any(), (CustomerDTO) any(), (List<ContactDTO>) any()))
                        .thenCallRealMethod().thenReturn(getTestCustomer1());
        when(mockAddressDataService.createOrUpdate((AddressDTO) any())).thenReturn(null);
        when(mockCustomerDataService.createOrUpdate((CustomerDTO) any())).thenReturn(null);
        when(mockCustomerConverter.convertToContactDTOs((Customer) any(), anyLong())).thenReturn(getContactDTOList());
        when(mockContactDataService.createOrUpdateAll((List<ContactDTO>) any())).thenReturn(getContactDTOList());
        when(mockCustomerConverter.convertToCustomer((CustomerDTO) any(), (AddressDTO) any(), (List<ContactDTO>) any())).thenReturn(
                        getTestCustomer1());
        Customer customer = customerServiceImpl.createOrUpdateCustomer(getTestCustomer1(), getTestAddressDTO1(), getTestCustomerDTOWithEmail(), null);
        assertNotNull(customer);
        assertNull(customer.getErrors());
        verify(mockCustomerConverter, atLeastOnce()).convertToContactDTOs((Customer) any(), anyLong());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldRunUpdateForCreateOrUpdateCustomer() {
        when(customerServiceImpl.createOrUpdateCustomer((Customer) any(), (AddressDTO) any(), (CustomerDTO) any(), (List<ContactDTO>) any()))
                        .thenCallRealMethod().thenReturn(getTestCustomer1());
        when(mockAddressDataService.createOrUpdate((AddressDTO) any())).thenReturn(null);
        when(mockCustomerDataService.createOrUpdate((CustomerDTO) any())).thenReturn(null);
        when(mockCustomerConverter.convertToContactDTOs((Customer) any(), anyLong())).thenReturn(getContactDTOList());
        when(mockContactDataService.createOrUpdateAll((List<ContactDTO>) any())).thenReturn(getContactDTOList());
        when(mockCustomerConverter.convertToCustomer((CustomerDTO) any(), (AddressDTO) any(), (List<ContactDTO>) any())).thenReturn(
                        getTestCustomer1());
        Customer customer = customerServiceImpl.createOrUpdateCustomer(getTestCustomer1(), getTestAddressDTO1(), getTestCustomerDTOWithEmail(),
                        getContactDTOList());
        assertNotNull(customer);
        assertNull(customer.getErrors());
        verify(mockCustomerConverter, never()).convertToContactDTOs((Customer) any(), anyLong());
    }

    @SuppressWarnings("unchecked")
    @Test
    public void shouldNotCreateOrUpdateCustomerIfExceptionThrown() {
        when(customerServiceImpl.createOrUpdateCustomer((Customer) any(), (AddressDTO) any(), (CustomerDTO) any(), (List<ContactDTO>) any()))
                        .thenCallRealMethod().thenReturn(getTestCustomer1());
        BDDMockito.willThrow(new RuntimeException()).given(mockAddressDataService).createOrUpdate((AddressDTO) any());
        doNothing().when(mockCustomer).setErrors(any(ErrorResult.class));
        Customer customer = customerServiceImpl.createOrUpdateCustomer(getTestCustomer1(), getTestAddressDTO1(), getTestCustomerDTOWithEmail(),
                        getContactDTOList());
        assertNotNull(customer.getErrors());
        verify(mockCustomerConverter, never()).convertToContactDTOs((Customer) any(), anyLong());
    }

    @Test
    public void shouldCheckCustomerForNullAndCreateCustomerError() {
        when(customerServiceImpl.checkCustomerForNull(null)).thenCallRealMethod();
        Customer customer = customerServiceImpl.checkCustomerForNull(null);
        assertNull(customer);
        verify(customerServiceImpl, atLeastOnce()).createCustomerError();
    }

    @Test
    public void shouldCheckCustomerForNullAndNotCreateCustomerError() {
        when(customerServiceImpl.checkCustomerForNull((Customer) any())).thenCallRealMethod();
        Customer customer = customerServiceImpl.checkCustomerForNull(getTestCustomer1());
        assertNotNull(customer);
        verify(customerServiceImpl, never()).createCustomerError();
    }

    @Test
    public void shouldValidateWhenCustomerCreated() {
        doCallRealMethod().when(customerServiceImpl).validate((Errors) any(), (CustomerDTO) any(), (AddressDTO) any(), anyBoolean());
        doNothing().when(mockCommonCustomerRegistrationValidator).validate((CustomerDTO) any(), (Errors) any());
        doNothing().when(mockCommonAddressValidator).validate((AddressDTO) any(), (Errors) any());
        customerServiceImpl.validate(errors, getTestCustomerDTOWithEmail(), getTestAddressDTO1(), true);
        verify(mockCommonCustomerRegistrationValidator, atLeastOnce()).validate((CustomerDTO) any(), (Errors) any());
        verify(mockCommonAddressValidator, atLeastOnce()).validate((AddressDTO) any(), (Errors) any());
        verify(mockCustomerNameValidator, never()).validate((CustomerDTO) any(), (Errors) any());
        verify(mockCommonCustomerRegistrationValidator, never()).validateMandatoryField((Errors) any(), anyString());
    }

    @Test
    public void shouldValidateWhenCustomerUpdated() {
        doCallRealMethod().when(customerServiceImpl).validate((Errors) any(), (CustomerDTO) any(), (AddressDTO) any(), anyBoolean());
        doNothing().when(mockCustomerNameValidator).validate((CustomerDTO) any(), (Errors) any());
        doNothing().when(mockCommonCustomerRegistrationValidator).validateMandatoryField((Errors) any(), anyString());
        doNothing().when(mockCommonAddressValidator).validate((AddressDTO) any(), (Errors) any());
        customerServiceImpl.validate(errors, getTestCustomerDTOWithEmail(), getTestAddressDTO1(), false);
        verify(mockCustomerNameValidator, atLeastOnce()).validate((CustomerDTO) any(), (Errors) any());
        verify(mockCommonCustomerRegistrationValidator, atLeastOnce()).validateMandatoryField((Errors) any(), anyString());
        verify(mockCommonAddressValidator, atLeastOnce()).validate((AddressDTO) any(), (Errors) any());
        verify(mockCommonCustomerRegistrationValidator, never()).validate((CustomerDTO) any(), (Errors) any());
    }

    @Test
    public void shouldCheckCustomerIdForNullAndCreateCustomerError() {
        when(customerServiceImpl.checkCustomerId((Customer) any(), (Long) any())).thenCallRealMethod();
        Customer customer = customerServiceImpl.checkCustomerId(getTestCustomerNullId(), CUSTOMER_ID_1);
        assertNull(customer.getId());
        verify(customerServiceImpl, atLeastOnce()).getContent(anyString());
    }

    @Test
    public void shouldCheckCustomerIdDoesNotMatchExternalIdAndCreateCustomerError() {
        when(customerServiceImpl.checkCustomerId((Customer) any(), (Long) any())).thenCallRealMethod();
        Customer customer = customerServiceImpl.checkCustomerId(getTestCustomer1(), CUSTOMER_ID_1);
        assertNotEquals(customer.getId(), CUSTOMER_ID_1);
        verify(customerServiceImpl, atLeastOnce()).getContent(anyString());
    }

    @Test
    public void shouldCheckCustomerIdForNullAndNotCreateCustomerError() {
        when(customerServiceImpl.checkCustomerId((Customer) any(), (Long) any())).thenCallRealMethod();
        Customer customer = customerServiceImpl.checkCustomerId(getTestCustomer1(), ID_1);
        assertNotNull(customer.getId());
        verify(customerServiceImpl, never()).getContent(anyString());
    }

    @Test
    public void shouldDeleteCustomerIfAuthorized() {
        when(customerServiceImpl.deleteCustomer((DeleteCustomer) any(), anyLong(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer((Long) any(), anyString())).thenReturn(true);
        when(mockCustomerDataService.findByExternalId(any(Long.class))).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerConverter.convertToCustomerDTO(any(DeleteCustomer.class), (CustomerDTO) any())).thenReturn(getTestCustomerDTOWithEmail());
        when(mockCustomerDataService.createOrUpdate((CustomerDTO) any())).thenReturn(getTestCustomerDTOWithEmail());
        customerServiceImpl.deleteCustomer(mockDeleteCustomer, CUSTOMER_ID_1, USERNAME_1);
        verify(mockCustomerDataService, atLeastOnce()).findByExternalId(any(Long.class));
        verify(mockCustomerConverter, atLeastOnce()).convertToCustomerDTO(any(DeleteCustomer.class), (CustomerDTO) any());
        verify(mockCustomerDataService, atLeastOnce()).createOrUpdate((CustomerDTO) any());
    }

    @Test
    public void shouldNotDeleteCustomerIfUnauthorized() {
        when(customerServiceImpl.deleteCustomer((DeleteCustomer) any(), anyLong(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer((Long) any(), anyString())).thenReturn(false);
        customerServiceImpl.deleteCustomer(mockDeleteCustomer, CUSTOMER_ID_1, USERNAME_1);
        verify(mockCustomerDataService, never()).findByExternalId(any(Long.class));
        verify(mockCustomerConverter, never()).convertToCustomerDTO(any(DeleteCustomer.class), (CustomerDTO) any());
        verify(mockCustomerDataService, never()).createOrUpdate((CustomerDTO) any());
    }

    @Test
    public void shouldNotDeleteCustomerIfExternalIdInvalid() {
        when(customerServiceImpl.deleteCustomer((DeleteCustomer) any(), (Long) any(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer((Long) any(), anyString())).thenReturn(true);
        BDDMockito.willThrow(new RuntimeException()).given(mockCustomerDataService).findByExternalId(anyLong());
        customerServiceImpl.deleteCustomer(new DeleteCustomer(), null, USERNAME_1);
        verify(mockCustomerDataService, atLeastOnce()).findByExternalId(any(Long.class));
        verify(mockCustomerConverter, never()).convertToCustomerDTO(any(DeleteCustomer.class), (CustomerDTO) any());
        verify(mockCustomerDataService, never()).createOrUpdate((CustomerDTO) any());
    }

    @Test
    public void shouldGetExternalUserIdForCustomerWhenUsernameIsNotNull() {
        when(customerServiceImpl.getExternalUserIdForCustomer(anyString())).thenCallRealMethod();
        when(mockExternalUserDataService.findExternalUserIdByUsername(anyString())).thenReturn(CUSTOMER_ID_1);
        Long externalUserId = customerServiceImpl.getExternalUserIdForCustomer(USERNAME_1);
        assertNotNull(externalUserId);
        verify(mockExternalUserDataService, atLeastOnce()).findExternalUserIdByUsername(anyString());
    }

    @Test
    public void shouldNotGetExternalUserIdForCustomerWhenUsernameIsNull() {
        when(customerServiceImpl.getExternalUserIdForCustomer(anyString())).thenCallRealMethod();
        Long externalUserId = customerServiceImpl.getExternalUserIdForCustomer(null);
        assertNull(externalUserId);
        verify(mockExternalUserDataService, never()).findExternalUserIdByUsername(anyString());
    }

    @Test
    public void isLoggedInUserAuthorizedToDeleteCustomerShouldReturnTrue() {
        when(customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer((Long) any(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.getExternalUserIdForCustomer(anyString())).thenReturn(CUSTOMER_ID_1);
        when(mockCustomerDataService.findByExternalIdAndExternalUserId((Long) any(), (Long) any())).thenReturn(getTestCustomerDTOWithEmail());
        customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer(CUSTOMER_ID_1, USERNAME_1);
        verify(mockCustomerDataService, atLeastOnce()).findByExternalIdAndExternalUserId((Long) any(), (Long) any());
    }

    @Test
    public void isLoggedInUserAuthorizedToDeleteCustomerShouldReturnFalse() {
        when(customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer((Long) any(), anyString())).thenCallRealMethod();
        when(customerServiceImpl.getExternalUserIdForCustomer(anyString())).thenReturn(CUSTOMER_ID_1);
        when(mockCustomerDataService.findByExternalIdAndExternalUserId((Long) any(), (Long) any())).thenReturn(null);
        customerServiceImpl.isLoggedInUserAuthorizedToUpdateOrDeleteCustomer(CUSTOMER_ID_1, USERNAME_1);
        verify(mockCustomerDataService, atLeastOnce()).findByExternalIdAndExternalUserId((Long) any(), (Long) any());
    }

    @Test
    public void shouldCreateCustomerError() {
        when(customerServiceImpl.createCustomerError()).thenCallRealMethod().thenReturn(getTestCustomer1());
        when(customerServiceImpl.getContent(anyString())).thenReturn(ERROR_DESCRIPTION_1);
        customerServiceImpl.createCustomerError();
        verify(customerServiceImpl, atLeastOnce()).getContent(anyString());
    }
}
