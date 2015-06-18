package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.PageView;



public class FulfilOrderReceiptControllerTest {
    private FulfilOrderReceiptController controller;
    private FulfilmentCmd fulfilmentCmd;
    private HttpSession mockHttpSession;
    
    @Before
    public void setUp() {
        controller = new FulfilOrderReceiptController();
        fulfilmentCmd = new FulfilmentCmd();
        
        mockHttpSession = mock(HttpSession.class);
    }

    @Test
    public void loadPrintableReceiptShouldLoadReceipt() {
        ModelAndView result = controller.loadPrintableReceipt(fulfilmentCmd, mockHttpSession);
        assertViewName(result, PageView.FULFIL_ORDER_RECEIPT);
    }
}

