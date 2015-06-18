package com.novacroft.nemo.mock_payment_gateway.cyber_source.controller;

import com.novacroft.nemo.mock_payment_gateway.cyber_source.application_service.PostTransactionService;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.command.PostCmd;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.CommandName;
import com.novacroft.nemo.mock_payment_gateway.cyber_source.constant.ViewName;
import com.novacroft.nemo.test_support.CyberSourceTestUtil;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * StandardTransactionRequestController unit tests
 */
public class PostControllerTest {

    @Test
    public void shouldHandleTransactionRequest() {
        PostTransactionService mockPostTransactionService = mock(PostTransactionService.class);
        when(mockPostTransactionService.getRequestAndConfiguration(any(Map.class))).thenReturn(new PostCmd());
        PostController controller = new PostController();
        controller.postTransactionService = mockPostTransactionService;
        ModelAndView result = controller.transactionRequest(CyberSourceTestUtil.getTestRequestMap());
        assertViewName(result, ViewName.TRANSACTION_REQUEST);
        assertModelAttributeAvailable(result, CommandName.COMMAND);
    }
}
