package com.novacroft.nemo.mock_cubic.rest.controller;

import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.CARD_NUMBER_CCC_LOSt_STOLEN_DATE_TIME;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.CARD_NUMBER_NON_CCC_LOSt_STOLEN_DATE_TIME;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.GET_CARD_PAGE_VIEW;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO1;
import static com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOCCCLostStolenDateTime;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.ModelAndViewAssert.assertViewName;

import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;
import org.springframework.validation.BindingResult;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.w3c.dom.Document;

import com.novacroft.nemo.common.converter.XmlModelConverter;
import com.novacroft.nemo.common.exception.ControllerException;
import com.novacroft.nemo.mock_cubic.command.GetCardCmd;
import com.novacroft.nemo.mock_cubic.constant.CardAction;
import com.novacroft.nemo.mock_cubic.service.card.CardResponseService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.domain.cubic.CardInfoResponseV2;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;

/**
 * Controller for card response.
 */
public class GetCardControllerTest {

    private transient GetCardController controller;
    private transient CardResponseService cardResponseService;
    private transient GetCardService mockGetCardService;
    private static final String CARDDETAILS = "012345678902";
    private transient BindingAwareModelMap mockModel;
    private transient GetCardCmd cmd;
    private transient BindingResult result;
    private transient XmlModelConverter<CardInfoResponseV2> mockcardInfoResponseV2Converter;
    
    @Before
    public void setUp() throws Exception {
        controller = new GetCardController();
        cardResponseService = mock(CardResponseService.class);
        mockGetCardService = mock(GetCardService.class);
        mockcardInfoResponseV2Converter = mock(XmlModelConverter.class);
        controller.setCardResponseService(cardResponseService);
        controller.getCardService = mockGetCardService;
        controller.cardInfoResponseV2Converter = mockcardInfoResponseV2Converter;
        mockModel = new BindingAwareModelMap();
        cmd = new GetCardCmd();
    }

    @Test
    public void testView() {
        final ModelAndView view = controller.load();
        assertEquals("Correct view returned", view.getViewName(), GetCardController.VIEW);
    }

    @Test
    public void testGetCardHttpRequestValidRequest() {
        final String xmlRequest = getCardRequest();
        when(cardResponseService.getCardDetails(any(Document.class))).thenReturn(CARDDETAILS);
        final String card = controller.getCard(xmlRequest);
        assertEquals("Card details match", CARDDETAILS, card);
    }

    @Test
    public void testGetCardHttpRequestInValidRequest() {
        final String xmlRequest = getCardRequest();
        when(cardResponseService.getCardDetails(anyString(), anyString())).thenReturn(CARDDETAILS);
        final String card = controller.getCard(xmlRequest);
        assertEquals("Card details not match", null, card);
    }

    @Test
    public void testGetCardResponseService() {
        final CardResponseService cardResponseService2 = controller.getCardResponseService();
        assertEquals("Service returned", cardResponseService, cardResponseService2);
    }

    private String getCardRequest() {
        final StringBuffer stringBuffer = new StringBuffer(220);
        stringBuffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + 
        "<CardInfoRequestV2>" +
        "<PrestigeID>012345678902</PrestigeID>" +
        "<OriginatorInfo>" +
        "<UserID>N0v4Croft</UserID>" +
        "<Password>t3stconn1</Password>" +
        "</OriginatorInfo>" +
        "</CardInfoRequestV2>");
        return stringBuffer.toString();
    }
    
    @Test
    public void testPopulateActions(){
        controller.populateActions(mockModel);
        assertTrue(mockModel.containsKey("cardActions"));
    }
    
    @Test
    public void testGetCard(){
        final String message = "Found";
        when(cardResponseService.getCardDetails(anyString(), anyString())).thenReturn(message);
        cmd.setPrestigeId("0123456789");
        cmd.setCardAction(CardAction.GET_CARD);
        controller.getCard(cmd, result);
        assertTrue(message.equals(cmd.getResponse()));
    }
    
    @Test(expected = ControllerException.class)
    public void testGetCardStringBody(){
        when(cardResponseService.getCardDetails(any(Document.class))).thenThrow(UnsupportedEncodingException.class);
        controller.getCard("<Test></Test>");
        
    }
    
    @Test
    public void testGetCardXMLWithCCCLostStolenDateTime(){
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTOCCCLostStolenDateTime());
        when(mockGetCardService.checkAndPopulateNodesExcludingLeafNodes(any(CardInfoResponseV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTOCCCLostStolenDateTime());
        cmd.setPrestigeId(CARD_NUMBER_CCC_LOSt_STOLEN_DATE_TIME);
        cmd.setCardAction(CardAction.GET_CARD);
        ModelAndView modelAndViewResult = controller.getCardXml(cmd, result);
        assertViewName(modelAndViewResult,GET_CARD_PAGE_VIEW);
    }
    
    @Test
    public void testGetCardXMLWithoutCCCLostStolenDateTime(){
        when(mockGetCardService.getCard(anyString())).thenReturn(getTestCardInfoResponseV2DTO1());
        when(mockGetCardService.checkAndPopulateNodesExcludingLeafNodes(any(CardInfoResponseV2DTO.class))).thenReturn(getTestCardInfoResponseV2DTOCCCLostStolenDateTime());
        cmd.setPrestigeId(CARD_NUMBER_NON_CCC_LOSt_STOLEN_DATE_TIME);
        cmd.setCardAction(CardAction.GET_CARD);
        ModelAndView modelAndViewResult = controller.getCardXml(cmd, result);
        assertViewName(modelAndViewResult,GET_CARD_PAGE_VIEW);
    }

}
