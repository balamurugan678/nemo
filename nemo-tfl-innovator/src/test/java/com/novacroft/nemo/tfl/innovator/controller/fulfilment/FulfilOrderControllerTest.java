package com.novacroft.nemo.tfl.innovator.controller.fulfilment;

import static com.novacroft.nemo.test_support.OrderTestUtil.getOrderDTOWithItems;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.fulfilment.FulfilmentService;
import com.novacroft.nemo.tfl.common.application_service.impl.fulfilment.FulfilmentServiceImpl;
import com.novacroft.nemo.tfl.common.command.impl.fulfilment.FulfilmentCmd;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.impl.CardDataServiceImpl;
import com.novacroft.nemo.tfl.common.data_service.impl.OrderDataServiceImpl;
import com.novacroft.nemo.tfl.common.form_validator.OysterCardValidator;



public class FulfilOrderControllerTest {
    private FulfilOrderController controller;
    private OrderDataService mockOrderDataService;
    private CardDataService mockCardDataService;
    
    private HttpSession mockHttpSession;
    private FulfilmentCmd fulfilmentCmd;
    private BeanPropertyBindingResult mockResult;
    private OysterCardValidator mockOysterCardValidator;
    private FulfilmentService mockFulfilmentService;
    
    @Before
    public void setUp() throws Exception {
        controller = new FulfilOrderController();
        
        mockOrderDataService = mock(OrderDataServiceImpl.class);
        mockCardDataService = mock(CardDataServiceImpl.class);
        mockHttpSession = mock(HttpSession.class);
        mockOysterCardValidator = mock(OysterCardValidator.class);
        mockResult = mock(BeanPropertyBindingResult.class);
        mockFulfilmentService = mock(FulfilmentServiceImpl.class);
        
        controller.orderDataService = mockOrderDataService;
        controller.cardDataService = mockCardDataService;                
        controller.oysterCardValidator = mockOysterCardValidator;
        controller.fulfilmentService = mockFulfilmentService;
        
        fulfilmentCmd=new FulfilmentCmd();
        fulfilmentCmd.setOrder(getOrderDTOWithItems());
        
        when(mockFulfilmentService.getOrderFromOrderNumber(any(Long.class))).thenReturn(getOrderDTOWithItems());
    }

    @Test
    public void loadOrderForFulfilmentShouldReturnFulfilOrderView() {
        ModelAndView result = controller.loadOrderForFulfilment(fulfilmentCmd,mockHttpSession);
        assertViewName(result, PageView.FULFIL_ORDER);
    }
    
    @Test
    public void fulfilOrderShouldRedirectToSummaryPage() {
        doNothing().when(mockOysterCardValidator).validate(any(Object.class), any(Errors.class));
        when(mockResult.hasErrors()).thenReturn(false);
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        ModelAndView result = controller.fulfilOrder( cmd, mockHttpSession,mockResult);
        assertEquals(PageUrl.FULFIL_ORDER_CONFIRMATION, ((RedirectView) result.getView()).getUrl());
    }
    
    @Test
    public void fulfilOrderShouldRedirectToFulfilOrderPage() {
        doNothing().when(mockOysterCardValidator).validate(any(Object.class), any(Errors.class));
        when(mockResult.hasErrors()).thenReturn(true);
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        ModelAndView result = controller.fulfilOrder( cmd, mockHttpSession, mockResult);
        assertViewName(result, PageView.FULFIL_ORDER);
    }
    
    @Test
    public void cancelShouldRedirectToFulfilmentHome() {
        FulfilmentCmd cmd = new FulfilmentCmd();
        cmd.setOrder(getOrderDTOWithItems());
        ModelAndView result = controller.cancel(cmd, mockHttpSession);
        assertEquals(PageUrl.FULFILMENT_HOME, ((RedirectView) result.getView()).getUrl());
    }
}

