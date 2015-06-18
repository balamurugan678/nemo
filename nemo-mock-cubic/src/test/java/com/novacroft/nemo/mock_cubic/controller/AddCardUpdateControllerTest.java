package com.novacroft.nemo.mock_cubic.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertModelAttributeAvailable;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestRequestPrePayTicketXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestRequestPrePayValueXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestRemoveUpdateRequestXml1;
import static com.novacroft.nemo.test_support.CardUpdateRequestTestUtil.getTestCardUpdatePrePayTicketResponse;

import java.net.URI;
import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.mock_cubic.application_service.UpdateCardService;
import com.novacroft.nemo.mock_cubic.command.AddRequestCmd;
import com.novacroft.nemo.mock_cubic.command.RemoveRequestCmd;
import com.novacroft.nemo.tfl.common.domain.cubic.CardRemoveUpdateRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;

public class AddCardUpdateControllerTest {

    private AddCardUpdateController controller;
    private UpdateCardService service;

    private XmlModelConverter<CardUpdatePrePayTicketRequest> pptRequestConverter;
    private XmlModelConverter<CardUpdatePrePayValueRequest> ppvRequestConverter;
    private XmlModelConverter<CardRemoveUpdateRequest> removeRequestConverter;
    private String pptRequestXml;
    private String ppvRequestXml;
    private String removeRequestXml;
    private AddRequestCmd addCmd;
    private RemoveRequestCmd removeCmd;

    private RestTemplate restTemplate;
    private URI url;

    static final String VIEW_NAME = "AddCardUpdateView";

    @Before
    public void setUp() throws Exception {
        controller = new AddCardUpdateController();
        service = mock(UpdateCardService.class);
        pptRequestConverter = mock(XmlModelConverter.class);
        ppvRequestConverter = mock(XmlModelConverter.class);
        removeRequestConverter = mock(XmlModelConverter.class);
        pptRequestXml = getTestRequestPrePayTicketXml1();
        ppvRequestXml = getTestRequestPrePayValueXml1();
        removeRequestXml = getTestRemoveUpdateRequestXml1();
        getTestCardUpdatePrePayTicketResponse();
        restTemplate = mock(RestTemplate.class);
        controller.updateCardService = service;
        controller.cardUpdatePrePayTicketRequestConverter = pptRequestConverter;
        controller.cardUpdatePrePayValueRequestConverter = ppvRequestConverter;
        controller.cardRemoveUpdateRequestConverter = removeRequestConverter;
        controller.restTemplate = restTemplate;
        addCmd = mock(AddRequestCmd.class);
        removeCmd = mock(RemoveRequestCmd.class);
        url = new URI("");
    }

    @Test
    public void testShowHomePage() {
        ModelAndView result = controller.showPage();
        assertViewName(result, VIEW_NAME);
        assertModelAttributeAvailable(result, "Cmd");
    }

    @Test
    public void shouldCallUpdateServiceAdd() {
        when(service.update(addCmd)).thenReturn("Response");
        ModelAndView result = controller.updateCard(addCmd);
        assertViewName(result, VIEW_NAME);
    }

    @Test
    public void shouldSendRequestAddUpdatePpt() {
        when(addCmd.getPrePayValue()).thenReturn(null);
        when(pptRequestConverter.convertModelToXml(any(CardUpdatePrePayTicketRequest.class))).thenReturn(pptRequestXml);
        when(restTemplate.postForEntity(url, pptRequestXml, String.class)).thenReturn(null);
        ModelAndView result = controller.updateXml(addCmd, mock(HttpServletRequest.class));
        assertViewName(result, VIEW_NAME);
    }

    @Test
    public void shouldSendRequestAddUpdatePpv() {
        when(addCmd.getPrePayValue()).thenReturn(200L);
        when(pptRequestConverter.convertModelToXml(any(CardUpdatePrePayTicketRequest.class))).thenReturn(ppvRequestXml);
        when(restTemplate.postForEntity(url, pptRequestXml, String.class)).thenReturn(null);
        ModelAndView result = controller.updateXml(addCmd, mock(HttpServletRequest.class));
        assertViewName(result, VIEW_NAME);
    }

    @Test
    public void shouldCallUpdateServiceRemove() {
        when(service.remove(removeCmd)).thenReturn("Response");
        ModelAndView result = controller.removePending(removeCmd);
        assertViewName(result, VIEW_NAME);
    }

    @Test
    public void shouldSendRequestRemoveUpdate() {
        
        when(removeRequestConverter.convertModelToXml(any(CardRemoveUpdateRequest.class))).thenReturn(removeRequestXml);
        when(restTemplate.postForEntity(url, removeRequestXml, String.class)).thenReturn(null);
        ModelAndView result = controller.removePendingXml(removeCmd, mock(HttpServletRequest.class));
        assertViewName(result, VIEW_NAME);
    }
}
