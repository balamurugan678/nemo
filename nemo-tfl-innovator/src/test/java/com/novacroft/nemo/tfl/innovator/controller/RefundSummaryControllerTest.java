package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1;
import static com.novacroft.nemo.tfl.common.constant.PageView.INV_REFUND_SUMMARY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;

public class RefundSummaryControllerTest {
    private RefundSummaryController controller;

    private HotlistCardService mockHotlistCardService;
    private AddUnattachedCardService mockAddUnattachedCardService;

    @Before
    public void setUp() throws Exception {
        controller = new RefundSummaryController();
        mockHotlistCardService = mock(HotlistCardService.class);
        mockAddUnattachedCardService = mock(AddUnattachedCardService.class);

        controller.hotlistCardService = mockHotlistCardService;
        controller.addUnattachedCardService = mockAddUnattachedCardService;
    }

    @Test
    public void viewShouldReturnRefundSummaryView() {
        ModelAndView result = controller.view(CartCmdTestUtil.getTestCartCmd2());
        assertEquals(INV_REFUND_SUMMARY, result.getViewName());
    }

    @Test
    public void refundFailedCardShouldReturnNullView() {
        when(mockAddUnattachedCardService.retrieveOysterDetails(anyString())).thenReturn(getTestPersonalDetailsCmd1());
        assertNull(controller.refund(CartCmdTestUtil.getTestCartCmd2()).getViewName());
    }
}
