package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.AddressTestUtil.ADDRESS_ID_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.HOUSE_NAME_NUMBER_1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.CardTestUtil.getTestCardDTO1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_2;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.NAME_1;
import static com.novacroft.nemo.test_support.CommonContactTestUtil.VALUE_1;
import static com.novacroft.nemo.test_support.ContactTestUtil.getTestContactDTOHomePhone1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_DEACTIVE;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EMAIL_ADDRESS_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO3;
import static com.novacroft.nemo.test_support.OrderTestUtil.getTestOrderDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.springframework.validation.BindingResult;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.command.impl.CommonOrderCardCmd;
import com.novacroft.nemo.common.constant.ContactType;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.exception.DataServiceException;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.test_support.AddressTestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.CommonOrderCardCmdTestUtil;
import com.novacroft.nemo.test_support.CustomerPreferencesTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.test_support.SecurityQuestionCmdTestUtil;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.SystemParameterCode;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ContactDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerPreferencesDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.ContactDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchResultDTO;

/**
 * Unit tests for CustomerService
 */
public class CustomerServiceImplTest {
    private static final String GHOST_EMAIL_START_VALUE = "gstart";
    private static final String GHOST_EMAIL_END_VALUE = "gend";
    private static final String NORMAL_EMAIL_ADDRESS_VALUE = "ttest-last-name-1Cirrus Park";

    protected AddressDataService mockAddressDataService;
    protected CardDataService mockCardDataService;
    protected ContactDataService mockContactDataService;
    protected CustomerServiceImpl service;
    protected CustomerDataService mockCustomerDataService;
    protected CartCmdImpl cmd;
    protected SecurityService mockSecurityService;
    protected OrderDataService mockOrderDataService;
    private SystemParameterService mockSystemParameterService;
    private CustomerPreferencesDataService mockCustomerPreferenceService;
    private BindingResult mockBindingResult;
    private CommonOrderCardCmd testCommonOrderCardCmd;

    @Before
    public void setUp() {
        service = new CustomerServiceImpl();
        cmd = new CartCmdImpl();
        testCommonOrderCardCmd = CommonOrderCardCmdTestUtil.getTestCommonOrderCardCmd();

        mockAddressDataService = mock(AddressDataService.class);
        mockCardDataService = mock(CardDataService.class);
        mockContactDataService = mock(ContactDataService.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockSecurityService = mock(SecurityService.class);
        mockOrderDataService = mock(OrderDataService.class);
        mockSystemParameterService = mock(SystemParameterService.class);
        mockCustomerPreferenceService = mock(CustomerPreferencesDataService.class);
        mockBindingResult = mock(BindingResult.class);

        service.addressDataService = mockAddressDataService;
        service.cardDataService = mockCardDataService;
        service.contactDataService = mockContactDataService;
        service.customerDataService = mockCustomerDataService;
        service.securityService = mockSecurityService;
        service.orderDataService = mockOrderDataService;
        service.systemParameterService = mockSystemParameterService;
        service.customerPreferencesDataService = mockCustomerPreferenceService;

        when(mockSystemParameterService.getParameterValue(SystemParameterCode.GHOST_EMAIL_ADDDRESS_START.code()))
                .thenReturn(GHOST_EMAIL_START_VALUE);
        when(mockSystemParameterService.getParameterValue(SystemParameterCode.GHOST_EMAIL_ADDDRESS_END.code()))
                .thenReturn(GHOST_EMAIL_END_VALUE);
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(getTestCustomerDTO1());
    }

    @Test
    public void usernameShouldExist() {
        boolean isUsernameExist = service.isUsernameAlreadyUsed(USERNAME_1);
        verify(mockCustomerDataService, atLeastOnce()).findByUsernameOrEmail(anyString());
        assertTrue(isUsernameExist);
    }

    @Test
    public void usernameShouldNotExistWithNullUsername() {
        boolean isUsernameExist = service.isUsernameAlreadyUsed(null);
        assertFalse(isUsernameExist);
    }

    @Test
    public void usernameShouldNotExistWithUsernameThatDoesNotFindAnyCustomer() {
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(null);
        boolean isUsernameExist = service.isUsernameAlreadyUsed(USERNAME_1);
        assertFalse(isUsernameExist);
    }

    @Test
    public void isUsernameAlreadyUsedExceptionThrown() {
        BDDMockito.willThrow(DataServiceException.class).given(mockCustomerDataService).findByUsernameOrEmail(anyString());
        assertTrue(service.isUsernameAlreadyUsed(""));
    }

    @Test
    public void emailShouldExist() {
        boolean isEmailExist = service.isEmailAddressAlreadyUsed(EMAIL_ADDRESS_1);
        verify(mockCustomerDataService, atLeastOnce()).findByUsernameOrEmail(anyString());
        assertTrue(isEmailExist);
    }

    @Test
    public void emailShouldNotExistWithNullEmail() {
        boolean isEmailExist = service.isEmailAddressAlreadyUsed(null);
        assertFalse(isEmailExist);
    }

    @Test
    public void emailShouldNotExistWithEmailThatDoesNotFindAnyCustomer() {
        when(mockCustomerDataService.findByUsernameOrEmail(anyString())).thenReturn(null);
        boolean isEmailExist = service.isEmailAddressAlreadyUsed(EMAIL_ADDRESS_1);
        assertFalse(isEmailExist);
    }

    @Test
    public void addAddressShouldCreateAddress() {
        when(mockAddressDataService.createOrUpdate((AddressDTO) anyObject())).thenReturn(getTestAddressDTO1());

        AddressDTO resultDto = service.createAddress(cmd);
        verify(mockAddressDataService, atLeastOnce()).createOrUpdate((AddressDTO) anyObject());
        assertNotNull(resultDto);
        assertEquals(HOUSE_NAME_NUMBER_1, resultDto.getHouseNameNumber());
    }

    @Test(expected = AssertionError.class)
    public void addAddressShouldNotCreateAddress() {
        when(mockAddressDataService.createOrUpdate((AddressDTO) anyObject())).thenReturn(null);

        AddressDTO resultDto = service.createAddress(null);
        verify(mockAddressDataService, never()).createOrUpdate((AddressDTO) anyObject());
        assertNull(resultDto.getId());
    }

    @Test
    public void addCustomerShouldCreateCustomer() {
        when(mockCustomerDataService.createOrUpdate((CustomerDTO) anyObject())).thenReturn(getTestCustomerDTO1());

        CustomerDTO resultDto = service.createCustomer(cmd, ADDRESS_ID_1);
        verify(mockCustomerDataService, atLeastOnce()).createOrUpdate((CustomerDTO) anyObject());
        assertNotNull(resultDto);
        assertEquals(FIRST_NAME_1, resultDto.getFirstName());
    }

    @Test(expected = AssertionError.class)
    public void addCustomerShouldNotCreateCustomer() {
        when(mockCustomerDataService.createOrUpdate((CustomerDTO) anyObject())).thenReturn(null);

        CustomerDTO resultDto = service.createCustomer(null, ADDRESS_ID_1);
        verify(mockCustomerDataService, never()).createOrUpdate((CustomerDTO) anyObject());
        assertNull(resultDto.getId());
    }

    @Test
    public void addContactShouldCreateContact() {
        when(mockContactDataService.createOrUpdate((ContactDTO) anyObject())).thenReturn(getTestContactDTOHomePhone1());

        ContactDTO resultDto = service.createContact(NAME_1, VALUE_1, ContactType.HomePhone.toString(), CUSTOMER_ID_1);
        verify(mockContactDataService, atLeastOnce()).createOrUpdate((ContactDTO) anyObject());
        assertNotNull(resultDto);
        assertEquals(VALUE_1, resultDto.getValue());
    }

    @Test(expected = AssertionError.class)
    public void addContactShouldNotCreateContact() {
        when(mockContactDataService.createOrUpdate((ContactDTO) anyObject())).thenReturn(null);

        ContactDTO resultDto = service.createContact(NAME_1, VALUE_1, null, CUSTOMER_ID_1);
        verify(mockContactDataService, never()).createOrUpdate((ContactDTO) anyObject());
        assertNull(resultDto.getId());
    }

    @Test
    public void shouldCreateCardIfNullCardNumber() {
        when(mockCardDataService.createOrUpdate((CardDTO) anyObject())).thenReturn(getTestCardDTO1());
        when(mockSecurityService.isLoggedIn()).thenReturn(true);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());

        CardDTO resultDto = service.createCard(CUSTOMER_ID_1, null);
        verify(mockCardDataService, atLeastOnce()).createOrUpdate((CardDTO) anyObject());
        assertNotNull(resultDto);
        assertEquals(OYSTER_NUMBER_1, resultDto.getCardNumber());
    }

    @Test
    public void shouldCreateCardIfCardNumberNotNull() {
        when(mockCardDataService.createOrUpdate((CardDTO) anyObject())).thenReturn(getTestCardDTO1());

        CardDTO resultDto = service.createCard(CUSTOMER_ID_1, OYSTER_NUMBER_1);
        assertNotNull(resultDto);
        assertEquals(OYSTER_NUMBER_1, resultDto.getCardNumber());
    }

    @Test
    public void findCustomerByOrderNumberShouldFindCustomer() {
        when(mockOrderDataService.findByOrderNumber(anyLong())).thenReturn(getTestOrderDTO1());
        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(getTestCustomerDTO1());
        when(mockAddressDataService.findById(anyLong())).thenReturn(getTestAddressDTO1());

        List<CustomerSearchResultDTO> customers = service.findCustomerByOrderNumber(1L);
        verify(mockOrderDataService).findByOrderNumber(anyLong());
        verify(mockCustomerDataService).findById(CUSTOMER_ID_1);
        assertEquals(customers.get(0).getId(), CUSTOMER_ID_1);
    }

    @Test
    public void deactivateCustomerShouldDeactivateCustomer() {
        CustomerDTO customerDTO = getTestCustomerDTO3();
        when(mockCustomerDataService.findById(CUSTOMER_ID_1)).thenReturn(customerDTO);
        when(mockCustomerDataService.createOrUpdate(customerDTO)).thenReturn(customerDTO);

        CustomerDTO resultDto = service.deactivateCustomer(CUSTOMER_ID_1);

        verify(mockCustomerDataService).findById(CUSTOMER_ID_1);
        assertNotNull(resultDto);
        assertEquals(CUSTOMER_DEACTIVE, resultDto.getDeactivated());
    }

    @Test
    public void checkGhostEmailShouldBeTrueIfGhostEmailAddress() {
        assertTrue(service.checkGhostEmail(GHOST_EMAIL_START_VALUE + NORMAL_EMAIL_ADDRESS_VALUE + GHOST_EMAIL_END_VALUE));
    }

    @Test
    public void checkGhostEmailShouldBeFalseIfGhostEmailEndMissing() {
        assertFalse(service.checkGhostEmail(GHOST_EMAIL_START_VALUE + NORMAL_EMAIL_ADDRESS_VALUE));
    }

    @Test
    public void checkGhostEmailShouldBeFalseIfGhostEmailStartMissing() {
        assertFalse(service.checkGhostEmail(NORMAL_EMAIL_ADDRESS_VALUE + GHOST_EMAIL_END_VALUE));
    }

    @Test
    public void checkGhostEmailShouldBeFalseIfNormalEmailAddress() {
        assertFalse(service.checkGhostEmail(NORMAL_EMAIL_ADDRESS_VALUE));
    }

    @Test
    public void testCreateGhostEmail() {
        assertEquals(GHOST_EMAIL_START_VALUE + NORMAL_EMAIL_ADDRESS_VALUE + GHOST_EMAIL_END_VALUE,
                service.createGhostEmail(testCommonOrderCardCmd));
    }

    @Test
    public void testAddCustomer() {
        when(mockAddressDataService.createOrUpdate((AddressDTO) anyObject())).thenReturn(getTestAddressDTO1());
        when(mockCustomerDataService.createOrUpdate((CustomerDTO) anyObject())).thenReturn(getTestCustomerDTO1());

        CustomerDTO actualCustomerDTO = service.addCustomer(testCommonOrderCardCmd);
        verify(mockContactDataService, times(2)).createOrUpdate(any(ContactDTO.class));
        assertEquals(FIRST_NAME_1, actualCustomerDTO.getFirstName());
        assertEquals(LAST_NAME_1, actualCustomerDTO.getLastName());
    }

    @Test
    public void testCustomerDoesNotOwnCardWhenNoCardsFound() {
        when(mockCardDataService.findByCustomerId(anyLong())).thenReturn(new ArrayList<CardDTO>());
        assertFalse(service.validateCustomerOwnsCard(CUSTOMER_ID_1, CARD_ID_1));
    }

    @Test
    public void testCustomerDoesNotOwnCardWhenCardsFound() {
        when(mockCardDataService.findByCustomerId(anyLong())).thenReturn(CardTestUtil.getTestCardList1());
        assertFalse(service.validateCustomerOwnsCard(CUSTOMER_ID_1, CARD_ID_2));
    }

    @Test
    public void testCustomerOwnsCardWhenCardsFound() {
        when(mockCardDataService.findByCustomerId(anyLong())).thenReturn(CardTestUtil.getTestCardList1());
        assertTrue(service.validateCustomerOwnsCard(CUSTOMER_ID_1, CARD_ID_1));
    }

    @Test
    public void shouldReturnNullPreferredStationId() {
        when(mockCustomerPreferenceService.findByCustomerId(anyLong())).thenReturn(null);
        assertNull(service.getPreferredStationId(null));
    }

    @Test
    public void shouldReturnPreferredStationId() {
        when(mockCustomerPreferenceService.findByCustomerId(anyLong()))
                .thenReturn(CustomerPreferencesTestUtil.getTestCustomerPreferencesDTO1());
        assertEquals(CustomerPreferencesTestUtil.STATION_ID_1, service.getPreferredStationId(CUSTOMER_ID_1));
    }

    @Test
    public void shouldCreateOysterCard() {
        when(mockCardDataService.createOrUpdate((CardDTO) anyObject())).thenReturn(getTestCardDTO1());
        CardDTO resultDto = service.createCard(CUSTOMER_ID_1, OYSTER_NUMBER_1);
        verify(mockCardDataService, atLeastOnce()).createOrUpdate((CardDTO) anyObject());
        assertNotNull(resultDto);

    }

    @Test
    public void createCardWithCustomerIdAndCardNumber() {
        when(mockCardDataService.createOrUpdate(any(CardDTO.class))).thenReturn(getTestCardDTO1());
        CardDTO resultDto = service.createCard(CUSTOMER_ID_1, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(resultDto);
        assertNotNull(resultDto.getId());
        assertEquals(CardTestUtil.OYSTER_NUMBER_1, resultDto.getCardNumber());
    }

    @Test
    public void isEmailAlreadyUsedExceptionThrown() {
        BDDMockito.willThrow(DataServiceException.class).given(mockCustomerDataService).findByUsernameOrEmail(anyString());
        assertTrue(service.isEmailAddressAlreadyUsed(""));
    }

    @Test
    public void findCustomerByOrderNumberThrowError() {
        when(mockOrderDataService.findByOrderNumber(anyLong())).thenReturn(null);
        assertNull(service.findCustomerByOrderNumber(OrderTestUtil.ORDER_NUMBER));
    }

    @Test
    public void customerExistsShouldNotRejectDueToNoNameMatch() {
        when(this.mockCustomerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class)))
                .thenReturn(Collections.EMPTY_LIST);
        doNothing().when(this.mockBindingResult).reject(anyString());
        this.service.customerExists(this.testCommonOrderCardCmd, this.mockBindingResult);
        verify(this.mockCustomerDataService).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        verify(this.mockBindingResult, never()).reject(anyString());
    }

    @Test
    public void customerExistsShouldNotRejectDueToNoAddressMatch() {
        CustomerSearchResultDTO mockCustomerSearchResultDTO = mock(CustomerSearchResultDTO.class);
        when(mockCustomerSearchResultDTO.getHouseNameNumber()).thenReturn(AddressTestUtil.HOUSE_NAME_NUMBER_2);
        when(mockCustomerSearchResultDTO.getTown()).thenReturn(AddressTestUtil.TOWN_2);
        List<CustomerSearchResultDTO> testCustomers = new ArrayList<CustomerSearchResultDTO>();
        testCustomers.add(mockCustomerSearchResultDTO);

        when(this.mockCustomerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class))).thenReturn(testCustomers);
        doNothing().when(this.mockBindingResult).reject(anyString());
        this.service.customerExists(this.testCommonOrderCardCmd, this.mockBindingResult);
        verify(this.mockCustomerDataService).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        verify(this.mockBindingResult, never()).reject(anyString());
    }

    @Test
    public void customerExistsShouldReject() {
        CustomerSearchResultDTO mockCustomerSearchResultDTO = mock(CustomerSearchResultDTO.class);
        when(mockCustomerSearchResultDTO.getHouseNameNumber()).thenReturn(AddressTestUtil.HOUSE_NAME_NUMBER_1);
        when(mockCustomerSearchResultDTO.getTown()).thenReturn(AddressTestUtil.TOWN_1);
        List<CustomerSearchResultDTO> testCustomers = new ArrayList<CustomerSearchResultDTO>();
        testCustomers.add(mockCustomerSearchResultDTO);

        when(this.mockCustomerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class))).thenReturn(testCustomers);
        doNothing().when(this.mockBindingResult).reject(anyString());
        this.service.customerExists(this.testCommonOrderCardCmd, this.mockBindingResult);
        verify(this.mockCustomerDataService).findByCriteria(any(CustomerSearchArgumentsDTO.class));
        verify(this.mockBindingResult).reject(anyString());
    }

    @Test
    public void shouldCreateCardWithSecurityQuestions() {
        when(mockCardDataService.createOrUpdate((CardDTO) anyObject())).thenReturn(getTestCardDTO1());
        CardDTO resultDto = service.createCardWithSecurityQuestion(CUSTOMER_ID_1, SecurityQuestionCmdTestUtil.SECURITY_QUESTION_1,
                        SecurityQuestionCmdTestUtil.SECURITY_ANSWER_1);
        assertNotNull(resultDto);
        assertEquals(OYSTER_NUMBER_1, resultDto.getCardNumber());
    }
}
