package com.novacroft.nemo.tfl.online.controller;

import com.novacroft.nemo.tfl.common.application_service.WebCreditStatementService;
import com.novacroft.nemo.tfl.common.command.impl.WebCreditStatementCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageCommand;
import com.novacroft.nemo.tfl.common.constant.PageView;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

/**
 * WebAccountCreditStatementController unit tests
 */
public class WebCreditStatementControllerTest {
    @Test
    public void shouldShowAdHocPage() {
        WebCreditStatementService mockWebCreditStatementService = mock(WebCreditStatementService.class);
        when(mockWebCreditStatementService.getStatement(anyLong())).thenReturn(new WebCreditStatementCmdImpl());
        WebCreditStatementController controller = mock(WebCreditStatementController.class);
        when(controller.showPage()).thenCallRealMethod();
        when(controller.getLoggedInUserCustomerId()).thenReturn(1L);
        controller.webCreditStatementService = mockWebCreditStatementService;
        ModelAndView result = controller.showPage();
        assertViewName(result, PageView.WEB_ACCOUNT_CREDIT_STATEMENT);
        assertModelAttributeAvailable(result, PageCommand.WEB_ACCOUNT_CREDIT_STATEMENT);
    }
}
