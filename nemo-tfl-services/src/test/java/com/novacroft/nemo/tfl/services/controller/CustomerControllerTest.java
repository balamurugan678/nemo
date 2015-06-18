package com.novacroft.nemo.tfl.services.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.USERNAME_1;
import static com.novacroft.nemo.tfl.services.test_support.WebServiceResultTestUtil.createSuccessfulResult;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.CALLS_REAL_METHODS;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CustomerTestUtil;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.application_service.impl.CustomerServiceImpl;
import com.novacroft.nemo.tfl.services.test_support.CustomerServiceTestUtil;
import com.novacroft.nemo.tfl.services.test_support.DeleteCustomerServiceTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Customer;
import com.novacroft.nemo.tfl.services.transfer.DeleteCustomer;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;


public class CustomerControllerTest {

  private CustomerController mockController;
  private CustomerServiceImpl mockCustomerService;
  private SecurityService mockSecurityService;

  private com.novacroft.nemo.tfl.services.application_service.CustomerService applicationCustomerService;
  private Long customerId = 1L;
  private HttpServletRequest mockHttpServletRequest;


  @Before
  public void setUp() {
    mockController = mock(CustomerController.class, CALLS_REAL_METHODS);
    mockCustomerService = mock(CustomerServiceImpl.class);
    mockSecurityService = mock(SecurityService.class);
    applicationCustomerService = mock(com.novacroft.nemo.tfl.services.application_service.CustomerService.class);

    mockController.customerService = mockCustomerService;
    mockController.applicationCustomerService = applicationCustomerService;
    mockController.securityService = mockSecurityService;
    mockHttpServletRequest = mock(HttpServletRequest.class);
  }

  
  @Test
  public void createCustomer() {
      when(mockSecurityService.getUsernameFromAuthorizationHeader((HttpServletRequest)any())).thenReturn(USERNAME_1);
      when(applicationCustomerService.createCustomer((Customer)any(), anyString())).thenReturn(CustomerServiceTestUtil.getTestCustomer1());
      Customer customer = mockController.createCustomer(CustomerServiceTestUtil.getTestCustomer1(), mockHttpServletRequest);
      assertNotNull(customer);
      assertEquals(CustomerTestUtil.EXTERNAL_USER_ID, customer.getId());
      assertNull(customer.getErrors());
  }
  
  @Test
  public void updateCustomer(){
      when(applicationCustomerService.updateCustomer((Customer)any(), (Long)any(), anyString())).thenReturn(CustomerServiceTestUtil.getTestCustomer1());
      Customer customer = mockController.updateCustomer(CustomerServiceTestUtil.getTestCustomer1(), CustomerTestUtil.EXTERNAL_USER_ID, mockHttpServletRequest);
      assertNotNull(customer);
      assertEquals(CustomerTestUtil.EXTERNAL_USER_ID, customer.getId());
      assertNull(customer.getErrors());
  }
  
  @Test
  public void shouldGetCustomer() {
      when(applicationCustomerService.getCustomerByExternalId(anyLong())).thenReturn(CustomerServiceTestUtil.getTestCustomer1());
      Customer customer = mockController.getCustomer(CUSTOMER_ID_1);
      assertNotNull(customer);
  }
  
  @Test
  public void shouldSetCustomerAsDeleted() {
      when(mockSecurityService.getUsernameFromAuthorizationHeader((HttpServletRequest)any())).thenReturn(USERNAME_1);
      when(applicationCustomerService.deleteCustomer((DeleteCustomer)any(), anyLong(), anyString())).thenReturn(createSuccessfulResult(CUSTOMER_ID_1));
      WebServiceResult customer = mockController.deleteCustomer(DeleteCustomerServiceTestUtil.getTestDeleteCustomer1(), CUSTOMER_ID_1, mockHttpServletRequest);
      assertNotNull(customer);
  }
  
  @Test
  public void instantiationTest(){
      assertNotNull(new CustomerController());
  }
}
