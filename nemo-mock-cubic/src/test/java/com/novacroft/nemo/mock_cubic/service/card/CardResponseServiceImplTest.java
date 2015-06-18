package com.novacroft.nemo.mock_cubic.service.card;

import static com.novacroft.nemo.common.utils.XMLUtil.convertStringToXmlDocument;
import static com.novacroft.nemo.common.utils.XMLUtil.createDocument;
import static com.novacroft.nemo.mock_cubic.test_support.AddCardTestUtil.createAddCardCmd;
import static com.novacroft.nemo.mock_cubic.test_support.AddCardTestUtil.createPPTSlot;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.novacroft.nemo.common.domain.cubic.PrePayTicketDetails;
import com.novacroft.nemo.common.domain.cubic.PrePayTicketSlot;
import com.novacroft.nemo.mock_cubic.application_service.OysterCardDetailsService;
import com.novacroft.nemo.mock_cubic.command.AddCardResponseCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.mock_cubic.data_access.CubicCardResponseDAO;
import com.novacroft.nemo.mock_cubic.domain.card.CubicCardResponse;
import com.novacroft.nemo.mock_cubic.domain.card.OysterCardDetails;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoRequestV2;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayTicketRequest;
import com.novacroft.nemo.tfl.common.domain.cubic.CardUpdatePrePayValueRequest;

public class CardResponseServiceImplTest {

    private CardResponseServiceImpl service;
    private CubicCardResponseDAO dao;
    private OysterCardDetailsService oysterCardDetailsService;
    
    @Before
    public void setUp() throws Exception {
        service = new CardResponseServiceImpl();
        dao = mock(CubicCardResponseDAO.class);
        oysterCardDetailsService = mock(OysterCardDetailsService.class);
        service.cubicCardResponseDAO = dao;
        service.oysterCardDetailsService = oysterCardDetailsService;
    }

    @Test
    public void testAddGetCardResponse() {
        CubicCardResponse cardresponse = new CubicCardResponse();
        when(dao.createOrUpdate(any(CubicCardResponse.class))).thenReturn(cardresponse);
        service.addGetCardResponse("012345678902", createDocument(), CardAction.GET_CARD);
        verify(dao).createOrUpdate(any(CubicCardResponse.class));
    }

    @Test
    public void testCreateGetCardResponseXML() {
        Document xml = service.createGetCardResponseXML(createAddCardCmd(true));
        assertNotEquals(xml, null);
    }

    @Test
    public void testSetCubicCardResponseDAO() {
        service.setCubicCardResponseDAO(dao);
        assertEquals(dao, service.getCubicCardResponseDAO());
    }

    @Test
    public void testGetCubicCardResponseDAO() {
        assertEquals(dao, service.getCubicCardResponseDAO());
    }

    @Test
    public void testGetCardDetails() {
        final String response = "<Test></Test>"; 
        List<CubicCardResponse> cardList = new ArrayList<CubicCardResponse>();
        CubicCardResponse card = new CubicCardResponse();
        card.setId(123456789L);
        card.setResponse(response);
        cardList.add(card);
        when(dao.findByExample(any(CubicCardResponse.class))).thenReturn(cardList);
        final String cardDetails = service.getCardDetails("1236456789", anyString());
        verify(dao).findByExample(any(CubicCardResponse.class));
        assertEquals(response, cardDetails);
    }
    
    
    @Test
    public void testGetCardDetailsNoResults() {
        final String response = "<Test></Test>"; 
        final List<CubicCardResponse> cardList = new ArrayList<CubicCardResponse>();
        final CubicCardResponse card = new CubicCardResponse();
        card.setId(123456789L);
        card.setResponse(response);
        cardList.add(card);
        when(dao.findByExample(card)).thenReturn(cardList);
        final String cardDetails = service.getCardDetails("1236456789", anyString());
        verify(dao).findByExample(any(CubicCardResponse.class));
        assertEquals("", cardDetails);
    }
    
    @Test
    public void testCreateHotlistReasons() {
        AddCardResponseCmd addCardCmd = createAddCardCmd(true);
        OysterCardDetails cardDetails = service.createCardDetails(addCardCmd);
        cardDetails.setHotlistReasonsCodeNumbers("");
        Document xmlDoc = createDocument();
        Element element = xmlDoc.createElement("Test");
        service.createHotlistReasons(cardDetails, xmlDoc, element);
    }
    
    
    @Test
    public void createPrePayTicketDetails() {
        final PrePayTicketDetails pptDetails = new PrePayTicketDetails();
        final PrePayTicketSlot pptSlot = createPPTSlot();
        final List<PrePayTicketSlot> pptSlots = new ArrayList<PrePayTicketSlot>();
        pptSlots.add(pptSlot);
        pptDetails.setPptSlots(pptSlots);
        assertEquals(pptSlot, pptDetails.getPptSlots().get(0));
    }

    @Test
    public void shouldAddGetCardResponse(){
        final String response = "<Test></Test>"; 
        Document xmlDoc = createDocument();
        final List<CubicCardResponse> cardList = new ArrayList<CubicCardResponse>();
        final CubicCardResponse card = new CubicCardResponse();
        card.setId(123456789L);
        card.setResponse(response);
        cardList.add(card);
        cardList.add(card);
        cardList.add(card);
        when(dao.findByExample(any(CubicCardResponse.class))).thenReturn(cardList);
        when(dao.createOrUpdate(card)).thenReturn(card);
        service.addGetCardResponse(CardTestUtil.OYSTER_NUMBER_1, xmlDoc, CardAction.GET_CARD);
        
    }
    
    @Test
    public void testSetPendingItemsPickupLocation1() {
        AddCardResponseCmd addCardCmd = createAddCardCmd(false);
        addCardCmd.setPendingPptPickupLocation1(1);
        OysterCardDetails cardDetails = service.createCardDetails(addCardCmd);
        service.setPendingItems(addCardCmd, cardDetails);
        assertNotNull(cardDetails.getPendingItems());
        assertNotNull(cardDetails.getPendingItems().getPpts());
    }
    
    @Test
    public void testSetPendingItemsPickupLocation2() {
        AddCardResponseCmd addCardCmd = createAddCardCmd(false);
        addCardCmd.setPendingPptPickupLocation2(1);
        OysterCardDetails cardDetails = service.createCardDetails(addCardCmd);
        service.setPendingItems(addCardCmd, cardDetails);
        assertNotNull(cardDetails.getPendingItems());
        assertNotNull(cardDetails.getPendingItems().getPpts());
    }
    
    @Test
    public void testSetPendingItemsPickupLocation3() {
        AddCardResponseCmd addCardCmd = createAddCardCmd(false);
        addCardCmd.setPendingPptPickupLocation3(1);
        OysterCardDetails cardDetails = service.createCardDetails(addCardCmd);
        service.setPendingItems(addCardCmd, cardDetails);
        assertNotNull(cardDetails.getPendingItems());
        assertNotNull(cardDetails.getPendingItems().getPpts());
    }
    
    @Test
    public void testGetCardDetailsFromDocument(){
        final String response = "<Test></Test>"; 
        final List<CubicCardResponse> cardList = new ArrayList<CubicCardResponse>();
        final CubicCardResponse card = new CubicCardResponse();
        card.setId(123456789L);
        card.setResponse(response);
        cardList.add(card);
        cardList.add(card);
        cardList.add(card);
        when(dao.findByExample(any(CubicCardResponse.class))).thenReturn(cardList);
        Document xmlDoc = convertStringToXmlDocument("<CardInfo><PrestigeID>012345678902</PrestigeID></CardInfo>");
        String cardDetails = service.getCardDetails(xmlDoc);
        assertNotNull(cardDetails);
    }
    
    @Test
    public void testGetCubicCardResponseByPrestigeIdShouldNotReturnNull() {
        CubicCardResponse cubicCardResponse = new CubicCardResponse();
        cubicCardResponse.setPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        List <CubicCardResponse> ccResponses = new ArrayList<>();
        ccResponses.add(cubicCardResponse);
        when(dao.findByExample(any(CubicCardResponse.class))).thenReturn(ccResponses);
        CubicCardResponse response = service.getCubicCardResponseByPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(response);
        assertEquals(cubicCardResponse.getPrestigeId(), response.getPrestigeId());
        
    }
    
    @Test
    public void testGetCubicCardResponseByPrestigeIdShouldReturnNull() {
        List <CubicCardResponse> ccResponses = new ArrayList<>();
        when(dao.findByExample(any(CubicCardResponse.class))).thenReturn(ccResponses);
        CubicCardResponse response = service.getCubicCardResponseByPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        assertNull(response);
    }
    
    @Test
    public void testGetCardDetailsFromDetailService(){
        when(oysterCardDetailsService.getCardDetails(any(CardInfoRequestV2.class))).thenReturn("");
        assertNotNull(service.getCardDetails(new CardInfoRequestV2()));
    }
    
    @Test
    public void testGetCardUpdatePrePayTicketShouldReturnBlank(){
        when(dao.findByExample(any(CubicCardResponse.class))).thenReturn(new ArrayList<CubicCardResponse>());
        CardUpdatePrePayTicketRequest request = new CardUpdatePrePayTicketRequest();
        request.setPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        String result = service.getCardUpdatePrePayTicket(request);
        assertNotNull(result);
        assertTrue(StringUtils.isBlank(result));
    }
    
    @Test
    public void testGetCardUpdatePrePayValue(){
        ArrayList<CubicCardResponse> cubicCardResponses = new ArrayList<CubicCardResponse>();
        CubicCardResponse ccResponse = new CubicCardResponse();
        ccResponse.setResponse("SUCCESS");
        cubicCardResponses.add(ccResponse);
        when(dao.findByExample(any(CubicCardResponse.class))).thenReturn(cubicCardResponses);
        CardUpdatePrePayValueRequest request = new CardUpdatePrePayValueRequest();
        request.setPrestigeId(CardTestUtil.OYSTER_NUMBER_1);
        String result = service.getCardUpdatePrePayValue(request);
        assertNotNull(result);
        assertFalse(StringUtils.isBlank(result));
    }

}
