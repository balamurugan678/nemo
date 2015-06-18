package com.novacroft.nemo.tfl.innovator.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.RefundSearchService;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.form_validator.RefundSearchValidator;
import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;

public class RefundSearchControllerTest {
    private RefundSearchController controller;
    private RefundSearchController mockController;
    private RefundSearchService mockRefundSearchService;
    private RefundSearchValidator mockValidator;
    private ArrayList<RefundSearchResultDTO> mockResultList;
    private BindingResult mockBindingResult;
    private HttpServletRequest mockRequest;

    @Before
    public void setUp() {
        controller = new RefundSearchController();
        mockController = mock(RefundSearchController.class);
        mockRefundSearchService = mock(RefundSearchService.class);
        mockValidator = mock(RefundSearchValidator.class);
        mockBindingResult = mock(BindingResult.class);
        mockRequest = mock(HttpServletRequest.class);

        controller.refundSearchService = mockRefundSearchService;
        controller.refundSearchValidator = mockValidator;

        mockResultList = new ArrayList<RefundSearchResultDTO>();
    }

    @Test
    public void shouldReturnAgentListView() {
        when(mockRefundSearchService.getAllRefunds()).thenReturn(mockResultList);
        ModelAndView modelAndView = controller.view(new RefundSearchCmd());
        verify(mockRefundSearchService).getAllRefunds();
        assertViewName(modelAndView, PageView.INV_REFUND_SEARCH);
    }

    @Test
    public void shouldGetResults() {
        doCallRealMethod().when(mockController).getResults(any(RefundSearchCmd.class), any(BindingResult.class), any(HttpServletRequest.class));
        when(mockController.checkSearch(any(RefundSearchCmd.class), any(BindingResult.class), any(HttpServletRequest.class))).thenReturn(
                        StringUtils.EMPTY);
        mockController.getResults(new RefundSearchCmd(), mockBindingResult, mockRequest);
        verify(mockController).search(any(RefundSearchCmd.class));
    }

    @Test
    public void shouldNotSearchIfValidationFails() {
        doCallRealMethod().when(mockController).getResults(any(RefundSearchCmd.class), any(BindingResult.class), any(HttpServletRequest.class));
        when(mockController.checkSearch(any(RefundSearchCmd.class), any(BindingResult.class), any(HttpServletRequest.class))).thenReturn("errors");
        mockController.getResults(new RefundSearchCmd(), mockBindingResult, mockRequest);
        verify(mockController, never()).search(any(RefundSearchCmd.class));
    }

    @Test
    public void shouldSearch() {
        when(mockRefundSearchService.findBySearchCriteria(any(RefundSearchCmd.class))).thenReturn(mockResultList);
        controller.search(new RefundSearchCmd());
        verify(mockRefundSearchService).findBySearchCriteria(any(RefundSearchCmd.class));
    }

    @Test
    public void shouldCheckSearchAndPlaceErrorMessagesIfValidationFailed() {
        doNothing().when(mockValidator).validate(anyObject(), any(Errors.class));
        controller.checkSearch(new RefundSearchCmd(), mockBindingResult, mockRequest);
        verify(mockValidator).validate(anyObject(), any(Errors.class));
    }

    @Test
    public void shouldGetAll() {
        when(mockRefundSearchService.getAllRefunds()).thenReturn(mockResultList);
        controller.getAll();
        verify(mockRefundSearchService).getAllRefunds();
    }
}
