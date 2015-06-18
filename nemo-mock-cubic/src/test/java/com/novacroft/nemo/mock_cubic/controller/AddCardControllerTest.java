package com.novacroft.nemo.mock_cubic.controller;

import static com.novacroft.nemo.common.utils.XMLUtil.createDocument;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.mock_cubic.service.card.CardResponseService;

public class AddCardControllerTest {

    private AddCardResponseController controller;
    private CardResponseService cardResponseService;
    private HttpServletRequest request;
    private ServletRequestDataBinder dataBinder;
    private AddCardResponseCmd cmd;
    private Document finalDoc;
    
    static final String VIEW_NAME = "AddCardResponseView";
    
    @Before
    public void setUp() throws Exception {
        controller = new AddCardResponseController();
        cardResponseService = mock(CardResponseService.class);
        dataBinder = mock(ServletRequestDataBinder.class);
        request = mock(HttpServletRequest.class);
        cmd = new AddCardResponseCmd();
        finalDoc = createDocument();
        controller.cardResponseService = cardResponseService;
    }

    @Test
    public void testShowHomePage() {
        ModelAndView result = controller.showPage();
        assertViewName(result, VIEW_NAME);
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void testAddCard() {
        when(cardResponseService.createGetCardResponseXML(cmd)).thenReturn(finalDoc);
        doNothing().when(cardResponseService).addGetCardResponse("012345678902", finalDoc, CardAction.GET_CARD);
        
        ModelAndView result = controller.addCard(cmd);
        assertViewName(result, VIEW_NAME);
    }

    @Test
    public void testGetCardResponseService() {
        controller.setCardResponseService(cardResponseService);
    }

    @Test
    public void testSetCardResponseService() {
        CardResponseService cardResponseService2 = controller.getCardResponseService();
        assertEquals(cardResponseService, cardResponseService2);
    }

    
}
