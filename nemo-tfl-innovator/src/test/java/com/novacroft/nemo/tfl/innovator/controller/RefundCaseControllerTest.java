package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.application_service.RefundCaseService;
import com.novacroft.nemo.tfl.common.command.impl.RefundCaseCmd;
import com.novacroft.nemo.tfl.common.constant.PageView;

public class RefundCaseControllerTest {
    private RefundCaseController controller;
    private HttpServletRequest mockRequest;
    private RefundCaseService mockRefundCaseService;

    @Before
    public void setUp() {
        controller = new RefundCaseController();
        mockRequest = mock(HttpServletRequest.class);
        mockRefundCaseService = mock(RefundCaseService.class);

        controller.refundCaseService = mockRefundCaseService;
    }

    @Test
    public void shouldReturnAgentListView() {
        when(mockRefundCaseService.getRefundCase(anyString())).thenReturn(new RefundCaseCmd());
        ModelAndView modelAndView = controller.view(REFUND_IDENTIFIER, mockRequest);
        verify(mockRefundCaseService).getRefundCase(anyString());
        assertViewName(modelAndView, PageView.INV_REFUND_CASE);
    }
}
