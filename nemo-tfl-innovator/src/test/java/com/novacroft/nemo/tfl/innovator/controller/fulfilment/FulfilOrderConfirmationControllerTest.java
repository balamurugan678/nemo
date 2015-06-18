package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentRetrievalService;
import com.novacroft.nemo.tfl.common.application_service.impl.fulfilment.FulfilmentRetrievalServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

public class FulfilOrderConfirmationControllerTest {
    private FulfilOrderConfirmationController controller;
    private HttpSession mockHttpSession;
    private FulfilmentCmd fulfilmentCmd;
    private FulfilmentRetrievalService mockFulfilmentRetrievalService;
    private SessionStatus mockSessionStatus;
    private Model mockModel;
    
    @Before
    public void setUp() throws Exception {
        controller = new FulfilOrderConfirmationController();
        
        mockHttpSession = mock(HttpSession.class);
        mockFulfilmentRetrievalService = mock(FulfilmentRetrievalServiceImpl.class);
        mockSessionStatus = mock(SessionStatus.class);
        mockModel = mock(Model.class);
        controller.fulfilmentRetrievalService = mockFulfilmentRetrievalService;
        
        fulfilmentCmd = new FulfilmentCmd();
        fulfilmentCmd.setCurrentQueue(OrderStatus.FULFILMENT_PENDING.code());
    }

    @Test
    public void shouldLoadConfirmation() {
        when(mockFulfilmentRetrievalService.getNumberOfOrdersInQueue(any(OrderStatus.class))).thenReturn(1L);
        assertViewName(controller.loadConfirmation(fulfilmentCmd, mockHttpSession), PageView.FULFIL_ORDER_CONFIRMATION);
        verify(mockFulfilmentRetrievalService).getNumberOfOrdersInQueue(any(OrderStatus.class));
    }

    @Test
    public void shouldRetrieveNextInQueue() {
        when(mockFulfilmentRetrievalService.getFirstOrderPendingFulfilmentFromQueue(any(OrderStatus.class))).thenReturn(
                        OrderTestUtil.getTestOrderDTO1());
        ModelAndView result = controller.retrieveNextInQeue(fulfilmentCmd, mockModel, mockHttpSession, mockSessionStatus);
        assertEquals(PageUrl.FULFIL_ORDER, ((RedirectView) result.getView()).getUrl());
    }
}

