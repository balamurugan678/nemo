package com.novacroft.nemo.tfl.services.converter.impl;

import static com.novacroft.nemo.test_support.GetCardTestUtil.getTestSuccessCardInfoResponseV2DTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CardTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.ProductTestUtil;
import com.novacroft.nemo.tfl.common.data_service.ProductDataService;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.services.test_support.CardDataTestUtil;
import com.novacroft.nemo.tfl.services.transfer.Card;

public class CardConverterImplTest {

    private CardConverterImpl converter;
    private ProductDataService productDataService;

    @Before
    public void setUp() throws Exception {
        converter = new CardConverterImpl();

        productDataService = mock(ProductDataService.class);

        converter.productDataService = productDataService;
    }

    @Test
    public void testConvertCardInfoResponseV2DTO() {
        Card card = converter.convert(getTestSuccessCardInfoResponseV2DTO1());
        assertEquals(getTestSuccessCardInfoResponseV2DTO1().getPrestigeId(), card.getPrestigeId());
        assertEquals(getTestSuccessCardInfoResponseV2DTO1().getPhotoCardNumber(), card.getPhotoCardNumber());
        assertEquals(getTestSuccessCardInfoResponseV2DTO1().getDiscountEntitlement1(), card.getDiscounts().get(0).getEntitlement());
    }

    @Test
    public void addHotListReasonToCard() {
        Card card = CardDataTestUtil.getTestCard1();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTOWithHotListReasons();
        converter.addHotListReasonToCard(cardInfoResponseV2DTO, card);
        assertNotNull(card.getHotListReason());
        assertEquals(new Integer(1), card.getHotListReason());
    }

    @Test
    public void addPendingItemsToCardWithPrePayValue() {
        Card card = CardDataTestUtil.getTestCard1();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO5();
        converter.addPendingItemsToCard(cardInfoResponseV2DTO, card);
        assertNotNull(card.getPendingItems());
        assertEquals(CardInfoResponseV2TestUtil.BALANCE_1, card.getPendingItems().getPrePayValues().get(0).getBalance());
    }

    @Test
    public void addPendingItemsToCardWithPrePayTicket() {
        Card card = CardDataTestUtil.getTestCard1();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO6();
        List<PrePayValue> ppvs = new ArrayList<PrePayValue>();
        ppvs.add(new PrePayValue());
        cardInfoResponseV2DTO.getPendingItems().setPpvs(ppvs);
        when(productDataService.findByProductCode(anyString(), any(Date.class))).thenReturn(ProductTestUtil.getTestProductDTO1());
        converter.addPendingItemsToCard(cardInfoResponseV2DTO, card);
        assertNotNull(card.getPendingItems());
        assertNotNull(card.getPendingItems().getTickets());
    }

    @Test
    public void addTicketsToCard() {
        Card card = CardDataTestUtil.getTestCard1();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO15();
        converter.addTicketsToCard(cardInfoResponseV2DTO, card);
        assertNotNull(card.getTickets());
        assertEquals(DateTestUtil.START_DATE, card.getTickets().get(0).getStartDate());
    }

    @Test
    public void addPrePayValueToCard() {
        Card card = CardDataTestUtil.getTestCard1();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTONullPendingItems();
        converter.addPrePayValueToCard(cardInfoResponseV2DTO, card);
        assertNotNull(card.getPrePayValue());
        assertEquals(CardInfoResponseV2TestUtil.BALANCE_1, card.getPrePayValue().getBalance());
    }
    
    @Test
    public void convertCardDTO(){
        Card card = converter.convert(CardTestUtil.getTestCardDTO1());
        assertNotNull(card);
        assertNotNull(card.getPrestigeId());
    }

}
