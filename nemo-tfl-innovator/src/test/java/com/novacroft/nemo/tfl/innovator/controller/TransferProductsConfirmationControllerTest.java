package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import com.novacroft.nemo.test_support.CartSessionDataTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.constant.PageView;

public class TransferProductsConfirmationControllerTest {

    private TransferProductsConfirmationController controller;
    private AddUnattachedCardService mockAddUnattachedCardService;
    private HttpSession mockHttpSession; 
    
    
    @Before
    public void setUp() throws Exception {
        controller = new TransferProductsConfirmationController();
        mockHttpSession = mock(HttpSession.class);
        mockAddUnattachedCardService = mock(AddUnattachedCardService.class);
        controller.addUnattachedCardService = mockAddUnattachedCardService;
    }
    
    @Test
    public void viewShouldReturnRefundSummaryView() {
        ModelAndView result = controller.showConfirmation(new RedirectAttributesModelMap());
        assertEquals(PageView.TRANSFER_CONFIRMATION, result.getViewName());
    }

    @Test
    public void backToCustomerPageShouldNotReturnNullView() {
        when(mockHttpSession.getAttribute(anyString())).thenReturn(CartSessionDataTestUtil.getTestCartSessionDataDTO1());
        when(mockAddUnattachedCardService.retrieveOysterDetails(anyString())).thenReturn(getTestPersonalDetailsCmd1());
        assertNotNull(controller.backToCustomerPage(mockHttpSession).getView());
    }

}
