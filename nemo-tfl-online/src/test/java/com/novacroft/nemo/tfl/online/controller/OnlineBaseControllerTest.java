package com.novacroft.nemo.tfl.online.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getCustomerDTO;
import static com.novacroft.nemo.test_support.SecurityServiceTestUtil.USERNAME;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

public class OnlineBaseControllerTest {
    @Mock
    private OnlineBaseController mockController;
    @Mock
    private CustomerDataService mockCustomerDataService;
    @Mock
    private SystemParameterService mockSystemParameterService;
    @Mock
    private SecurityService mockSecurityService;
    
    private CustomerDTO testCustomerDTO;
    
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        
        mockController.customerDataService = mockCustomerDataService;
        mockController.systemParameterService = mockSystemParameterService;
        setField(mockController, "securityService", mockSecurityService);
        
        testCustomerDTO = getCustomerDTO(CUSTOMER_ID_1);
    }
    
    @Test
    public void testCancel() {
        when(mockController.cancel()).thenCallRealMethod();
        
        ModelAndView actualResult = mockController.cancel();
        
        assertEquals(PageUrl.DASHBOARD, ((RedirectView) actualResult.getView()).getUrl());
    }
    
    @Test
    public void testGetLoggedInUserCustomerId() {
        when(mockController.getLoggedInUserCustomerId()).thenCallRealMethod();
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(testCustomerDTO);
        
        assertEquals(CUSTOMER_ID_1, mockController.getLoggedInUserCustomerId());
        verify(mockSecurityService).getLoggedInCustomer();
    }
    
    @Test
    public void testGetLoggedInUsername() {
        when(mockController.getLoggedInUsername()).thenCallRealMethod();
        when(mockSecurityService.getLoggedInUsername()).thenReturn(USERNAME);
        
        assertEquals(USERNAME, mockController.getLoggedInUsername());
        verify(mockSecurityService).getLoggedInUsername();
    }
    
    @Test
    public void getCustomerShouldCallCustomerDataService() {
        when(mockController.getCustomer()).thenCallRealMethod();
        
        CustomerDTO mockCustomerDTO = mock(CustomerDTO.class);
        when(mockSecurityService.getLoggedInCustomer()).thenReturn(testCustomerDTO);
        when(mockCustomerDataService.findById(testCustomerDTO.getId())).thenReturn(mockCustomerDTO);
        
        assertEquals(mockCustomerDTO, mockController.getCustomer());
        verify(mockSecurityService).getLoggedInCustomer();
        verify(mockCustomerDataService).findById(CUSTOMER_ID_1);
    }
    
    @Test
    public void testIsSingleSignOnAuthenticationOn() {
        when(mockController.isSingleSignOnAuthenticationOn()).thenCallRealMethod();
        
        when(mockSystemParameterService.getBooleanParameterValue(anyString())).thenReturn(Boolean.TRUE);
        
        assertTrue(mockController.isSingleSignOnAuthenticationOn());
        
        verify(mockSystemParameterService).getBooleanParameterValue(anyString());
    }
}
