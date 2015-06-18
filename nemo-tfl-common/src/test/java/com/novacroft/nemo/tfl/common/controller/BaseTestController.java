package com.novacroft.nemo.tfl.common.controller;

import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.common.application_service.SelectListService;
import com.novacroft.nemo.common.transfer.SelectListDTO;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;

public class BaseTestController {
    
    protected SecurityService mockSecurityService;
    protected CustomerDataService mockCustomerDataService;
    protected SelectListService mockSelectListService;
    
    public void setUp() {
        mockSelectListService = mock(SelectListService.class);
        when(mockSelectListService.getSelectList(anyString())).thenReturn(new SelectListDTO());
        this.mockSecurityService = mock(SecurityService.class);
        this.mockCustomerDataService = mock(CustomerDataService.class);
        when(this.mockSecurityService.getLoggedInCustomer()).thenReturn(getTestCustomerDTO1());
        when(this.mockCustomerDataService.findById(anyLong())).thenReturn(getTestCustomerDTO1());
    }
    
    protected String redirectViewCheck(ModelAndView modelAndView) {
        RedirectView redirectView = (RedirectView) modelAndView.getView();
        return redirectView.getUrl(); 
    }
    
}
