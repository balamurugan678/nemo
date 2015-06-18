package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import static com.novacroft.nemo.tfl.common.constant.PageView.FULFILMENT_HOME;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.ui.Model;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.OrderTestUtil;
import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentRetrievalService;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.OrderStatus;
import com.novacroft.nemo.tfl.common.constant.PageUrl;



public class FulfilmentControllerTest {
    private FulfilmentController controller;
    private FulfilmentRetrievalService mockFulfilmentRetrievalService;
    private HttpSession mockHttpSession;
    private Model mockModel;
    private FulfilmentCmd mockFulfilmentCmd;
    private SessionStatus mockSessionStatus;
    
    @Before
    public void setUp() throws Exception {
        mockFulfilmentRetrievalService = mock(FulfilmentRetrievalService.class);
        mockHttpSession = mock(HttpSession.class);
        mockModel = mock(Model.class);
        mockFulfilmentCmd = mock(FulfilmentCmd.class);
        mockSessionStatus = mock(SessionStatus.class);
        
        controller = new FulfilmentController();
        controller.fulfilmentRetrievalService = mockFulfilmentRetrievalService;
    }

    @Test
    public void viewShouldReturnFulfilmentHomeView() {
        when(mockFulfilmentRetrievalService.getNumberOfOrdersInQueue(any(OrderStatus.class))).thenReturn(1L);
        ModelAndView result = controller.showFulfilmentQueues();
        assertEquals(FULFILMENT_HOME, result.getViewName());
        verify(mockFulfilmentRetrievalService, times(6)).getNumberOfOrdersInQueue(any(OrderStatus.class));
    }
    
    @Test
    public void viewShouldReturnfulfilmentPendingOrder() {
        when(mockFulfilmentRetrievalService.getFirstOrderPendingFulfilmentFromQueue(any(OrderStatus.class))).thenReturn(OrderTestUtil.getTestOrderDTO1());
        ModelAndView result = controller.fulfilmentPendingOrder(mockFulfilmentCmd, mockModel, mockHttpSession, mockSessionStatus);
        assertEquals(PageUrl.FULFIL_ORDER, ((RedirectView) result.getView()).getUrl());
    }
    
}
