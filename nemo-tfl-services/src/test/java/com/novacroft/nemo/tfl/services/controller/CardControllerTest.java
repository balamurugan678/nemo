package com.novacroft.nemo.tfl.services.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;
import static org.mockito.Matchers.any;
import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.tfl.services.test_support.WebServiceResultTestUtil.createSuccessfulResult;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.tfl.services.application_service.OysterCardService;
import com.novacroft.nemo.tfl.services.test_support.CardDataTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Card;
import com.novacroft.nemo.tfl.services.transfer.WebServiceResult;

public class CardControllerTest {

    private CardController controller;
    private OysterCardService mockCardService;

    @Before
    public void setUp() throws Exception {
        controller = new CardController();
        mockCardService = mock(OysterCardService.class);
        controller.oysterCardService = mockCardService;
    }

    @Test
    public void testGetCardDetails() {
        when(mockCardService.getCard(CardDataTestUtil.OYSTER_NUMBER_1)).thenReturn(CardDataTestUtil.getTestCard1());
        Card response = controller.getCardDetails(CardDataTestUtil.OYSTER_NUMBER_1);
        assertNotNull(response);
        assertNotNull(response.getPrePayValue());
        assertEquals(CardInfoResponseV2TestUtil.BALANCE_1, response.getPrePayValue().getBalance());
    }
    
    @Test
    public void testGetCardFromExternalId() {
        when(mockCardService.getCard(anyLong())).thenReturn(CardDataTestUtil.getTestCard1());
        Card response = controller.getCardFromExternalId(CardTestUtil.EXTERNAL_CARD_ID);
        assertNotNull(response);
        assertNotNull(response.getPrePayValue());
        assertEquals(CardInfoResponseV2TestUtil.BALANCE_1, response.getPrePayValue().getBalance());
    }

    @Test
    public void shouldCreateOysterCardForCustomer() {
        when(mockCardService.createOysterCard((Long)any())).thenReturn(createSuccessfulResult(CUSTOMER_ID_1));
        WebServiceResult response = controller.createOysterCardForCustomer(CUSTOMER_ID_1);
        assertNotNull(response);
    }

    @Test
    public void shouldCreateOysterCard(){
        when(mockCardService.createOysterCard(anyLong(), anyString())).thenReturn(createSuccessfulResult(CardTestUtil.EXTERNAL_CARD_ID));
        WebServiceResult response = controller.createOysterCard(CUSTOMER_ID_1, CardTestUtil.OYSTER_NUMBER_1);
        assertNotNull(response);
        verify(mockCardService).createOysterCard(anyLong(), anyString());
    }
    
    @Test
    public void shouldUpdateOysterCard(){
        when(mockCardService.updateCard(anyLong(), any(Card.class))).thenReturn(CardDataTestUtil.getTestCard1());
        Card updatedCard = controller.updateCardFromExternald(CardTestUtil.EXTERNAL_CARD_ID, CardDataTestUtil.getTestCard1());
        assertNotNull(updatedCard);
        
        
    }

}
