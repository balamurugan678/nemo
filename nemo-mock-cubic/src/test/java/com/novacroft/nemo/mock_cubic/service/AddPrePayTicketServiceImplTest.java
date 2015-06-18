package com.novacroft.nemo.mock_cubic.service;


import static com.novacroft.nemo.common.utils.XMLUtil.createDocument;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.novacroft.nemo.common.utils.XMLUtil;
import com.novacroft.nemo.mock_cubic.command.AddCardPrePayTicketCmd;
import com.novacroft.nemo.mock_cubic.service.card.AddPrePayTicketServiceImpl;

public class AddPrePayTicketServiceImplTest {

    protected AddPrePayTicketServiceImpl mockService;
    protected AddPrePayTicketServiceImpl service;
    protected AddCardPrePayTicketCmd cmd;
    protected Document document;
    
    @Before
    public void setUp() throws Exception {
        service = new AddPrePayTicketServiceImpl();
        mockService = mock(AddPrePayTicketServiceImpl.class);
        cmd = new AddCardPrePayTicketCmd(); 
        document = createDocument();
    }

    @Test
    public void testCreatePrePayTicketResponseSuccess() {
        cmd.setErrorDescription("");
        when(mockService.createPrePayTicketResponse(cmd)).thenReturn(document);
        Document response = mockService.createPrePayTicketResponse(cmd);
        assertTrue(response == document);
    }
    
    @Test
    public void testCreatePrePayTicketResponseError() {
        cmd.setErrorDescription("Error");
        when(mockService.createPrePayTicketResponse(cmd)).thenReturn(document);
        Document response = mockService.createPrePayTicketResponse(cmd);
        assertTrue(response == document);
    }

    @Test
    public void testCreateCardUpdateResponseXMLError() {
        String errorDescription = "Error Description";
        cmd.setPrestigeId("0123456789");
        cmd.setErrorCode(123);
        cmd.setErrorDescription(errorDescription);
        Document document = service.createPrePayTicketResponse(cmd);
        final Element root = document.getDocumentElement();
        final NodeList list = root.getChildNodes();
        String value = XMLUtil.getValueFromNodes(list,"ErrorDescription");
        assertEquals(errorDescription, value);
    }
    
    @Test
    public void testCreateCardUpdateResponseXMLSuccess() {
        String prestigeID  = "0123456789";
        cmd.setPrestigeId(prestigeID);
        cmd.setRequestSequenceNumber(123456L);
        cmd.setPickupLocation(1234L);
        cmd.setAvailableSlots(1L);
        Document document = service.createPrePayTicketResponse(cmd);
        final Element root = document.getDocumentElement();
        final NodeList list = root.getChildNodes();
        String value = XMLUtil.getValueFromNodes(list,"PrestigeID");
        assertEquals(prestigeID, value);
    }

}
