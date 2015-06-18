package com.novacroft.nemo.tfl.innovator.controller;

import com.novacroft.nemo.test_support.CustomerSearchTestUtil;
import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CustomerService;
import com.novacroft.nemo.tfl.common.command.impl.CustomerSearchCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.form_validator.CustomerSearchValidator;
import com.novacroft.nemo.tfl.common.transfer.CustomerSearchArgumentsDTO;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.web.ModelAndViewAssert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class CustomerSearchControllerTest {
    private CustomerSearchController controller;

    private CustomerService mockCustomerService;
    private CustomerDataService mockCustomerDataService;
    private CustomerSearchValidator mockCustomerSearchValidator;

    private CustomerSearchCmdImpl mockCmd;
    private BindingResult mockBindingResult;

    private String testMessages = "test-messages";
    private String testResults = "test-results";

    private FieldError mockObjectError;
    private List<ObjectError> testObjectErrors;

    @Before
    public void setUp() throws Exception {
        this.controller = mock(CustomerSearchController.class);

        this.mockCustomerService = mock(CustomerService.class);
        this.controller.customerService = this.mockCustomerService;

        this.mockCustomerDataService = mock(CustomerDataService.class);
        this.controller.customerDataService = this.mockCustomerDataService;

        this.mockCustomerSearchValidator = mock(CustomerSearchValidator.class);
        this.controller.customerSearchValidator = this.mockCustomerSearchValidator;

        this.mockCmd = mock(CustomerSearchCmdImpl.class);
        this.mockBindingResult = mock(BindingResult.class);

        this.mockObjectError = mock(FieldError.class);
        this.testObjectErrors = new ArrayList<ObjectError>();
        this.testObjectErrors.add(this.mockObjectError);
    }

    @Test
    public void shouldShowPage() {
        when(this.controller.showPage(any(CustomerSearchCmdImpl.class))).thenCallRealMethod();
        ModelAndView result = this.controller.showPage(this.mockCmd);
        ModelAndViewAssert.assertViewName(result, PageView.INV_CUSTOMER_SEARCH);
        ModelAndViewAssert.assertModelAttributeAvailable(result, PageCommand.CUSTOMER_SEARCH);
    }

    @Test
    public void getResultsShouldReturnMessages() {
        when(this.controller.getResults(any(CustomerSearchCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();

        when(this.controller.checkSearchCriteria(any(CustomerSearchCmdImpl.class), any(BindingResult.class)))
                .thenReturn(this.testMessages);
        when(this.controller.search(any(CustomerSearchCmdImpl.class))).thenReturn(this.testResults);

        this.controller.getResults(this.mockCmd, this.mockBindingResult);

        verify(this.controller).checkSearchCriteria(any(CustomerSearchCmdImpl.class), any(BindingResult.class));
        verify(this.controller, never()).search(any(CustomerSearchCmdImpl.class));
    }

    @Test
    public void getResultsShouldReturnResults() {
        when(this.controller.getResults(any(CustomerSearchCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();

        when(this.controller.checkSearchCriteria(any(CustomerSearchCmdImpl.class), any(BindingResult.class)))
                .thenReturn(StringUtils.EMPTY);
        when(this.controller.search(any(CustomerSearchCmdImpl.class))).thenReturn(this.testResults);

        this.controller.getResults(this.mockCmd, this.mockBindingResult);

        verify(this.controller).checkSearchCriteria(any(CustomerSearchCmdImpl.class), any(BindingResult.class));
        verify(this.controller).search(any(CustomerSearchCmdImpl.class));
    }

    @Test
    public void shouldCheckSearch() {
        when(this.controller.checkSearch(any(CustomerSearchCmdImpl.class), any(BindingResult.class))).thenCallRealMethod();
        when(this.controller.checkSearchCriteria(any(CustomerSearchCmdImpl.class), any(BindingResult.class)))
                .thenReturn(StringUtils.EMPTY);
        this.controller.checkSearch(this.mockCmd, this.mockBindingResult);
        verify(this.controller).checkSearchCriteria(any(CustomerSearchCmdImpl.class), any(BindingResult.class));
    }

    @Test
    public void shouldSearchByOrderNumber() {
        when(this.controller.search(any(CustomerSearchCmdImpl.class))).thenCallRealMethod();
        when(this.mockCmd.getOrderNumber()).thenReturn(OrderTestUtil.ORDER_NUMBER);
        when(this.mockCustomerService.findCustomerByOrderNumber(anyLong()))
                .thenReturn(CustomerSearchTestUtil.getTestCustomerSearchResultDTOs());
        when(this.mockCustomerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class)))
                .thenReturn(CustomerSearchTestUtil.getTestCustomerSearchResultDTOs());

        this.controller.search(this.mockCmd);

        verify(this.mockCmd, atLeastOnce()).getOrderNumber();
        verify(this.mockCustomerService).findCustomerByOrderNumber(anyLong());
        verify(this.mockCustomerDataService, never()).findByCriteria(any(CustomerSearchArgumentsDTO.class));
    }

    @Test
    public void shouldSearchByCriteria() {
        when(this.controller.search(any(CustomerSearchCmdImpl.class))).thenCallRealMethod();
        when(this.mockCmd.getOrderNumber()).thenReturn(null);
        when(this.mockCustomerService.findCustomerByOrderNumber(anyLong()))
                .thenReturn(CustomerSearchTestUtil.getTestCustomerSearchResultDTOs());
        when(this.mockCustomerDataService.findByCriteria(any(CustomerSearchArgumentsDTO.class)))
                .thenReturn(CustomerSearchTestUtil.getTestCustomerSearchResultDTOs());

        this.controller.search(this.mockCmd);

        verify(this.mockCmd, atLeastOnce()).getOrderNumber();
        verify(this.mockCustomerService, never()).findCustomerByOrderNumber(anyLong());
        verify(this.mockCustomerDataService).findByCriteria(any(CustomerSearchArgumentsDTO.class));
    }

    @Test
    public void shouldCheckSearchCriteria() {
        when(this.controller.checkSearchCriteria(any(CustomerSearchCmdImpl.class), any(BindingResult.class)))
                .thenCallRealMethod();
        when(this.controller.getContent(anyString())).thenReturn(StringUtils.EMPTY);
        doNothing().when(this.mockCustomerSearchValidator).validate(anyObject(), any(Errors.class));
        when(this.mockBindingResult.getAllErrors()).thenReturn(this.testObjectErrors);
        when(this.mockObjectError.getField()).thenReturn(StringUtils.EMPTY);

        this.controller.checkSearchCriteria(this.mockCmd, this.mockBindingResult);

        verify(this.controller).getContent(anyString());
        verify(this.mockCustomerSearchValidator).validate(anyObject(), any(Errors.class));
        verify(this.mockBindingResult).getAllErrors();
        verify(this.mockObjectError, atLeastOnce()).getField();
    }
}
