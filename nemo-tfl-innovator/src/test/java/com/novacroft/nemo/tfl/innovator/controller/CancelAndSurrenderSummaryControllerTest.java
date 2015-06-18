package com.novacroft.nemo.tfl.innovator.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.PersonalDetailsCmdImpl;
import com.novacroft.nemo.tfl.common.constant.PageUrl;
import com.novacroft.nemo.tfl.common.constant.PageView;

public class CancelAndSurrenderSummaryControllerTest {
    private CancelAndSurrenderCardRefundCartSummaryController controller;
    private HotlistCardService mockHotlistCardService;
    private AddUnattachedCardService mockAddUnattachedCardService;

    @Before
    public void setUp() throws Exception {
        controller = new CancelAndSurrenderCardRefundCartSummaryController();
        mockHotlistCardService = mock(HotlistCardService.class);
        mockAddUnattachedCardService = mock(AddUnattachedCardService.class);
        controller.hotlistCardService = mockHotlistCardService;
        controller.addUnattachedCardService = mockAddUnattachedCardService;
                
    }

    @Test
     public void getShouldReturnRefundCartSummaryViewTest() {
        CartCmdImpl cmd = mock(CartCmdImpl.class);
        ModelAndView result = controller.view(cmd);
        assertEquals(PageView.INV_CANCEL_AND_SURRENDER_CARD_REFUND_CART_SUMMARY, result.getViewName());
    }

    @Test
    public void refundFailedCardShouldRefundCardAndReturnToCustomerScreen(){
        CartCmdImpl cmd = mock(CartCmdImpl.class);
        PersonalDetailsCmdImpl personalDetails = mock(PersonalDetailsCmdImpl.class);
        Mockito.doNothing().when(mockHotlistCardService).toggleCardHotlisted(Mockito.anyString(), Mockito.anyInt());
        Mockito.when(mockAddUnattachedCardService.retrieveOysterDetails(Mockito.anyString())).thenReturn(personalDetails);
        
        
        ModelAndView result = controller.refundFailedCard(cmd);
        assertNotNull(result.getView());
        final RedirectView view = (RedirectView) result.getView();
        assertEquals(PageUrl.INV_CUSTOMER, view.getUrl());
    }
}
