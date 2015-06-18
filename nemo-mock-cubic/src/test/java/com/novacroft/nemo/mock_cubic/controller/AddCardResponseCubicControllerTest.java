package com.novacroft.nemo.mock_cubic.controller;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.mock_cubic.application_service.OysterCardDetailsService;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.data_service.OysterCardDataService;

public class AddCardResponseCubicControllerTest {

    private AddCardResponseCubicController controller;
    private OysterCardDetailsService mockOysterCardDetailsService;
    private OysterCardDataService mockCardResponseService;
    private AddCardResponseCmd cmd;
    
    static final String VIEW_NAME = "AddCardResponseNewView";
    
    @Before
    public void setUp() throws Exception {
        controller = new AddCardResponseCubicController();
        mockOysterCardDetailsService = mock(OysterCardDetailsService.class);
        mockCardResponseService = mock(OysterCardDataService.class);
        controller.cardResponseService = mockCardResponseService;
        controller.oysterCardDetailsService = mockOysterCardDetailsService;
        cmd = new AddCardResponseCmd();
    }

    @Test
    public void testShowHomePage() {
        ModelAndView result = controller.showPage();
        assertViewName(result, VIEW_NAME);
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void testAddCard() {
        doNothing().when(mockOysterCardDetailsService).createOrUpdateOysterCard(cmd); 
        ModelAndView result = controller.addCard(cmd);
        verify(controller.oysterCardDetailsService).createOrUpdateOysterCard(cmd);
        assertViewName(result, VIEW_NAME);
    }
    

    @Test
    public void testfreePrePayTicketSlots() {
        doNothing().when(mockOysterCardDetailsService).freePrePayTicketSlots(cmd); 
        ModelAndView result = controller.freePrePayTicketSlots(cmd);
        verify(controller.oysterCardDetailsService).freePrePayTicketSlots(cmd);
        assertViewName(result, VIEW_NAME);
    }
   
}
