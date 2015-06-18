package com.novacroft.nemo.tfl.innovator.controller;

import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseCmdTestUtil.getTestFailedAutoTopUpCmd;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.novacroft.nemo.test_support.PersonalDetailsCmdTestUtil;
import com.novacroft.nemo.tfl.common.application_service.AddUnattachedCardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.command.impl.FailedAutoTopUpCaseCmdImpl;
import com.novacroft.nemo.tfl.common.constant.Page;
import com.novacroft.nemo.tfl.common.constant.PageUrl;

public class ResettleFailedAutoTopUpControllerTest {
	
	private ResettleFailedAutoTopUpController controller;
	private AddUnattachedCardService mockAddUnattachedCardService;
	private CartService mockCartService; 
    private HttpSession mockHttpSession;
    private BindingResult mockBindingResult;
    
	@Before
	public void setUp() {
		controller = mock(ResettleFailedAutoTopUpController.class);
		mockAddUnattachedCardService = mock(AddUnattachedCardService.class);
        mockHttpSession = mock(HttpSession.class);
        mockBindingResult = mock(BindingResult.class);
        
		controller.addUnattachedCardService= mockAddUnattachedCardService;
		controller.cartService = mockCartService;
	}

    @Test
    public void shouldShowPage() {
    	FailedAutoTopUpCaseCmdImpl cmd = getTestFailedAutoTopUpCmd();
    	String cardNumber = cmd.getCardNumber();
    	doNothing().when(controller).addCustomerFailedAutoTopUpHistoryDetailsInModel(any(ModelAndView.class), anyLong()) ;
    	when(controller.loadResettleViewPage(mockHttpSession, cmd, cardNumber)).thenCallRealMethod();
    	ModelAndView result = controller.loadResettleViewPage(mockHttpSession, cmd, cardNumber);
    	assert(result.getViewName().contains(Page.RESETTLE_FAILED_AUTO_TOP_UP));
    }
    
    @Test
    public void cancelResettleShouldReturnToCustomerView() {
        when(mockAddUnattachedCardService.retrieveOysterDetails(anyString())).thenReturn(PersonalDetailsCmdTestUtil.getTestPersonalDetailsCmd1());
        FailedAutoTopUpCaseCmdImpl cmd = getTestFailedAutoTopUpCmd();
        when(controller.cancel(mockHttpSession, cmd, mockBindingResult)).thenCallRealMethod();
        ModelAndView result = controller.cancel(mockHttpSession, cmd, mockBindingResult);
        assertEquals(PageUrl.INV_CUSTOMER, ((RedirectView) result.getView()).getUrl());
    }
  
}
