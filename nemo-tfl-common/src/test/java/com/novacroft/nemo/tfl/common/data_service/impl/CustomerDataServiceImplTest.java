package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.CustomerSearchTestUtil.getTestCustomerSearchArgumentsDTO;
import static com.novacroft.nemo.test_support.CustomerSearchTestUtil.getTestCustomerSearchResultDTOs;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EXTERNAL_CUSTOMER_ID;
import static com.novacroft.nemo.test_support.CustomerTestUtil.EXTERNAL_USER_ID;
import static com.novacroft.nemo.test_support.CustomerTestUtil.FIRST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.LAST_NAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.TFL_MASTER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getCustomer1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getCustomer7;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getCustomer8;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getSearchResult;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyList;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMap;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.common.support.NemoUserContext;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.CustomerSearchTestUtil;
import com.novacroft.nemo.tfl.common.converter.CustomerSearchConverter;
import com.novacroft.nemo.tfl.common.converter.impl.CustomerConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CustomerDAO;
import com.novacroft.nemo.tfl.common.domain.Customer;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;

/**
 * Note: no test for <code>createOrUpdate</code> method because Mockito doesn't support mocking super methods
 */
public class CustomerDataServiceImplTest {

    private Query query;
    private Query query1;

    private CustomerDAO mockDao;
    private CustomerDataServiceImpl service;

    private CustomerDataServiceImpl mockCustomerDataService;
    private CustomerSearchConverter mockCustomerSearchConverter;
    private CustomerConverterImpl mockCustomerConverterImpl;
    
    private NemoUserContext mockNemoUserContext;

    @Before
    public void setUp() {
        query = mock(Query.class);
        query1 = mock(Query.class);
        mockDao = mock(CustomerDAO.class);
        service = new CustomerDataServiceImpl();

        List<Customer> customerList = new ArrayList<Customer>();
        customerList.add(getCustomer1());
        Object[] searchResult = getSearchResult();
        List<Object[]> searchResults = new ArrayList<Object[]>();
        searchResults.add(searchResult);
        when(mockDao.findByQuery(anyString(), anyObject())).thenReturn(customerList);
        when(mockDao.findByQuery(anyString(), anyObject(), anyObject())).thenReturn(customerList);
        when(mockDao.findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyObject())).thenReturn(customerList);
        when(mockDao.findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyObject(), anyObject())).thenReturn(customerList);
        when(mockDao.findByQueryUniqueResult(anyString(), anyLong())).thenReturn(getCustomer1());

        when(mockDao.createQuery(anyString())).thenReturn(query);
        when(query.list()).thenReturn(customerList);
        when(mockDao.createSQLQuery(anyString())).thenReturn(query1);
        when(query1.list()).thenReturn(searchResults);

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        this.mockCustomerDataService = mock(CustomerDataServiceImpl.class);
        this.mockCustomerSearchConverter = mock(CustomerSearchConverter.class);
        this.mockCustomerDataService.customerSearchConverter = this.mockCustomerSearchConverter;
        ReflectionTestUtils.setField(this.mockCustomerDataService, "dao", this.mockDao);
        this.mockCustomerConverterImpl = mock(CustomerConverterImpl.class);
        ReflectionTestUtils.setField(this.mockCustomerDataService, "converter", this.mockCustomerConverterImpl);
        this.mockNemoUserContext = mock(NemoUserContext.class);
        ReflectionTestUtils.setField(this.mockCustomerDataService, "nemoUserContext", this.mockNemoUserContext);
        
        when(this.mockNemoUserContext.getUserName()).thenReturn(StringUtil.EMPTY_STRING);
    }

    
    @Test
    public void createOrUpdateShouldReturnCustomer() {
        when(mockDao.createOrUpdate(any(Customer.class))).thenReturn(getCustomer1());
        mockCustomerDataService.createOrUpdate(getTestCustomerDTO1());
        verify(mockCustomerDataService).createOrUpdate(any(CustomerDTO.class));
  }
    
    
    @Test
    public void findByIdShouldFindCustomer() {
        when(mockDao.findById(anyLong())).thenReturn(getCustomer1());

        CustomerDTO customer = service.findById(ID_1);
        verify(mockDao).findById(anyLong());
        assertEquals(FIRST_NAME_1, customer.getFirstName());
    }

    @Test
    public void findByCardIdShouldFindCustomer() {
        service.findByCardId(ID_1);
        verify(mockDao).findByQueryUniqueResult(anyString(), anyLong());
        assertEquals(FIRST_NAME_1, service.findByCardId(ID_1).getFirstName());
    }

    @Test
    public void findByFirstNameShouldFindCustomer() {
        List<CustomerDTO> resultsDTO = service.findByFirstName(FIRST_NAME_1, true);
        verify(mockDao).findByQuery(anyString(), anyObject());
        assertEquals(FIRST_NAME_1, resultsDTO.get(0).getFirstName());

        List<CustomerDTO> run2 = service.findByFirstName(FIRST_NAME_1, false);
        assertEquals(FIRST_NAME_1, run2.get(0).getFirstName());

        List<CustomerDTO> run3 = service.findByFirstName(FIRST_NAME_1, true, 1, 2);
        assertEquals(FIRST_NAME_1, run3.get(0).getFirstName());

        List<CustomerDTO> run4 = service.findByFirstName(FIRST_NAME_1, false, 1, 2);
        assertEquals(FIRST_NAME_1, run4.get(0).getFirstName());
    }

    @Test
    public void findByLastNameShouldFindCustomer() {
        List<CustomerDTO> resultsDTO = service.findByLastName(LAST_NAME_1, true);
        verify(mockDao).findByQuery(anyString(), anyObject());
        assertEquals(LAST_NAME_1, resultsDTO.get(0).getLastName());

        List<CustomerDTO> run2 = service.findByLastName(LAST_NAME_1, false);
        assertEquals(LAST_NAME_1, run2.get(0).getLastName());
    }

    @Test
    public void findByFirstNameAndLastNameShouldFindCustomer() {
        List<CustomerDTO> resultsDTO = service.findByFirstNameAndLastName(FIRST_NAME_1, LAST_NAME_1, true);
        verify(mockDao).findByQuery(anyString(), anyObject(), anyObject());
        assertEquals(LAST_NAME_1, resultsDTO.get(0).getLastName());

        List<CustomerDTO> run2 = service.findByFirstNameAndLastName(FIRST_NAME_1, LAST_NAME_1, false);
        assertEquals(LAST_NAME_1, run2.get(0).getLastName());

        List<CustomerDTO> run3 = service.findByFirstNameAndLastName(FIRST_NAME_1, LAST_NAME_1, false, 1, 2);
        assertEquals(LAST_NAME_1, run3.get(0).getLastName());

        List<CustomerDTO> run4 = service.findByFirstNameAndLastName(FIRST_NAME_1, LAST_NAME_1, false, 1, 2);
        assertEquals(LAST_NAME_1, run4.get(0).getLastName());
    }

    @Test
    public void findByUsernameOrEmailShouldFindUser() {
        when(this.mockCustomerDataService.findByUsernameOrEmail(anyString())).thenCallRealMethod();
        when(this.mockDao.findByQueryUniqueResult(anyString(), anyString(), anyString())).thenReturn(getCustomer1());
        when(this.mockCustomerConverterImpl.convertEntityToDto(any(Customer.class))).thenReturn(getTestCustomerDTO1());
        this.mockCustomerDataService.findByUsernameOrEmail(USERNAME_1);
        verify(this.mockDao).findByQueryUniqueResult(anyString(), anyString(), anyString());
        verify(this.mockCustomerConverterImpl).convertEntityToDto(any(Customer.class));
    }

    @Test
    public void findByUsernameOrEmailShouldFindNull() {
        when(this.mockCustomerDataService.findByUsernameOrEmail(anyString())).thenCallRealMethod();
        when(this.mockDao.findByQueryUniqueResult(anyString(), anyString(), anyString())).thenReturn(null);
        when(this.mockCustomerConverterImpl.convertEntityToDto(any(Customer.class))).thenReturn(getTestCustomerDTO1());
        this.mockCustomerDataService.findByUsernameOrEmail(USERNAME_1);
        verify(this.mockDao).findByQueryUniqueResult(anyString(), anyString(), anyString());
        verify(this.mockCustomerConverterImpl, never()).convertEntityToDto(any(Customer.class));
    }

    @Test
    public void findByCustomerIdShouldFindUser() {
        when(this.mockCustomerDataService.findByCustomerId(anyLong())).thenCallRealMethod();
        when(this.mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getCustomer1());
        when(this.mockCustomerConverterImpl.convertEntityToDto(any(Customer.class))).thenReturn(getTestCustomerDTO1());
        this.mockCustomerDataService.findByCustomerId(CUSTOMER_ID_1);
        verify(this.mockDao).findByQueryUniqueResult(anyString(), anyString());
        verify(this.mockCustomerConverterImpl).convertEntityToDto(any(Customer.class));
    }

    @Test
    public void findByCustomerIdShouldFindNull() {
        when(this.mockCustomerDataService.findByCustomerId(anyLong())).thenCallRealMethod();
        when(this.mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(null);
        when(this.mockCustomerConverterImpl.convertEntityToDto(any(Customer.class))).thenReturn(getTestCustomerDTO1());
        this.mockCustomerDataService.findByCustomerId(CUSTOMER_ID_1);
        verify(this.mockDao).findByQueryUniqueResult(anyString(), anyString());
        verify(this.mockCustomerConverterImpl, never()).convertEntityToDto(any(Customer.class));
    }

    @Test
    public void findByCardNumberShouldFindUser() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getCustomer7());

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        CustomerDTO resultDto = service.findByCardNumber(OYSTER_NUMBER_1);

        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertEquals(USERNAME_1, resultDto.getUsername());
    }

    @Test
    public void findByCardNumberShouldFindNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(null);

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        CustomerDTO resultDto = service.findByCardNumber(OYSTER_NUMBER_1);

        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertNull(resultDto);
    }

    @Test
    public void findByCardIdShouldFindUser() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(getCustomer7());

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        CustomerDTO resultDto = service.findByCardId(CARD_ID_1);

        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertEquals(USERNAME_1, resultDto.getUsername());
    }

    @Test
    public void findByCardIdShouldFindNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyString())).thenReturn(null);

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        CustomerDTO resultDto = service.findByCardId(CARD_ID_1);

        verify(mockDao).findByQueryUniqueResult(anyString(), anyString());
        assertNull(resultDto);
    }

    @Test
    public void findByExternalUserIdNull() {
        when(mockDao.findByQuery(anyString(), anyString())).thenReturn(null);

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        List<CustomerDTO> resultDto = service.findByExternalUserId(EXTERNAL_USER_ID);

        assertNull(resultDto);
    }

    @Test
    public void findByExternalUserId() {
        when(mockDao.findByQuery(anyString(), anyString())).thenReturn(Arrays.asList(getCustomer8()));

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        List<CustomerDTO> resultDto = service.findByExternalUserId(EXTERNAL_USER_ID);

        assertNotNull(resultDto);
        assertEquals(1, resultDto.size());
    }

    @Test
    public void findByExternalUserIdMultipleResults() {
        when(mockDao.findByQuery(anyString(), anyString())).thenReturn(Arrays.asList(getCustomer8(), getCustomer1()));

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        List<CustomerDTO> resultDto = service.findByExternalUserId(EXTERNAL_USER_ID);
        assertNotNull(resultDto);
        assertEquals(2, resultDto.size());
    }

    @Test
    public void findByExternalUserIdZeroResults() {
        when(mockDao.findByQuery(anyString(), anyString())).thenReturn(Arrays.asList());

        service.setConverter(new CustomerConverterImpl());
        service.setDao(mockDao);

        List<CustomerDTO> resultDto = service.findByExternalUserId(EXTERNAL_USER_ID);

        assertNull(resultDto);
    }

    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }

    @Test
    public void shouldFindByExternalIdAndExternalUserId() {
        when(this.mockCustomerDataService.findByExternalIdAndExternalUserId(anyLong(), anyLong())).thenCallRealMethod();
        when(this.mockDao.findByQueryUniqueResult(anyString(), anyLong(), anyLong())).thenReturn(getCustomer1());
        when(this.mockCustomerConverterImpl.convertEntityToDto(any(Customer.class))).thenReturn(getTestCustomerDTO1());
        this.mockCustomerDataService.findByExternalIdAndExternalUserId(EXTERNAL_CUSTOMER_ID, EXTERNAL_USER_ID);
        verify(this.mockDao).findByQueryUniqueResult(anyString(), anyLong(), anyLong());
        verify(this.mockCustomerConverterImpl).convertEntityToDto(any(Customer.class));
    }

    @Test
    public void shouldNotFindByExternalIdAndExternalUserIdIfNoCustomersReturned() {
        when(this.mockCustomerDataService.findByExternalIdAndExternalUserId(anyLong(), anyLong())).thenCallRealMethod();
        when(this.mockDao.findByQueryUniqueResult(anyString(), anyLong(), anyLong())).thenReturn(null);
        when(this.mockCustomerConverterImpl.convertEntityToDto(any(Customer.class))).thenReturn(getTestCustomerDTO1());
        this.mockCustomerDataService.findByExternalIdAndExternalUserId(EXTERNAL_CUSTOMER_ID, EXTERNAL_USER_ID);
        verify(this.mockDao).findByQueryUniqueResult(anyString(), anyLong(), anyLong());
        verify(this.mockCustomerConverterImpl, never()).convertEntityToDto(any(Customer.class));
    }

    @Test
    public void findByIncomingTflIdShouldReturnNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyObject())).thenReturn(null);
        assertNull(service.findByTflMasterId(TFL_MASTER_ID_1));
    }

    @Test
    public void findByIncomingTflIdShouldFindDto() {
        Customer testCustomer = getCustomer1();
        testCustomer.setTflMasterId(TFL_MASTER_ID_1);
        when(mockDao.findByQueryUniqueResult(anyString(), anyObject())).thenReturn(testCustomer);

        CustomerDTO actualResult = service.findByTflMasterId(TFL_MASTER_ID_1);
        assertNotNull(actualResult);
        assertEquals(TFL_MASTER_ID_1, actualResult.getTflMasterId());
    }

    @Test
    public void shouldFindByCriteria() {
        when(this.mockCustomerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class))).thenCallRealMethod();

        when(this.mockDao.findBySqlQueryWithLimitUsingNamedParameters(anyString(), anyInt(), anyInt(), anyMap()))
                .then(new Answer<Object>() {
                    @Override
                    public Object answer(InvocationOnMock invocation) throws Throwable {
                        assertEquals("select cu.id, cu.firstname, cu.lastname, ca.cardnumber, ca.status, a.housenamenumber, " +
                                        "a.street, a.town, a.county, co.name, a.postcode from card ca, customer cu, " +
                                        "address a, country co where a.id (+) = cu.addressid and ca.customerid (+) = cu.id " +
                                        "and a.country_id = co.id and cu.id = :customerId and ca.cardnumber = :cardNumber and" +
                                        " upper(cu.firstname) = :firstName and upper(cu.lastname) = :lastName and replace" +
                                        "(upper(a.postcode), ' ', '') = :postcode and lower(cu.emailAddress) = :email and lower(cu.username) = :userName",
                                invocation.getArguments()[0]);
                        assertEquals("{lastName=TEST-LAST-NAME-1, customerId=2, email=test-1@nowhere.com, userName=testuser1, postcode=NN36UR, " +
                                        "firstName=TEST-FIRST-NAME-1, cardNumber=100000000001}",
                                        
                                invocation.getArguments()[3].toString());
                        return CustomerSearchTestUtil.getTestCustomerSearchRecords();
                    }
                });
        when(this.mockCustomerSearchConverter.convert(anyList())).thenReturn(getTestCustomerSearchResultDTOs());

        this.mockCustomerDataService.findByCriteria(getTestCustomerSearchArgumentsDTO());

        verify(this.mockDao).findBySqlQueryWithLimitUsingNamedParameters(anyString(), anyInt(), anyInt(), anyMap());
        verify(this.mockCustomerSearchConverter).convert(anyList());
    }
}


