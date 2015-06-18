package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
import static com.novacroft.nemo.test_support.ItemTestUtil.PRICE_1;
import static com.novacroft.nemo.test_support.CardRefundableDepositItemTestUtil.getTestCardRefundableDepositItem1;
import static com.novacroft.nemo.test_support.OrderTestUtil.ORDER_ID;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.tfl.common.converter.impl.CardRefundableDepositItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.CardRefundableDepositItemDAO;
import com.novacroft.nemo.tfl.common.domain.CardRefundableDepositItem;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;

/**
 * Card refundable deposit item data service unit tests.
 */
public class CardRefundableDepositItemDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(CardRefundableDepositItemDataServiceTest.class);

    private CardRefundableDepositItemDataServiceImpl dataService;
    private CardRefundableDepositItemDAO mockDao;

    @Before
    public void setUp() {
        dataService = new CardRefundableDepositItemDataServiceImpl();
        mockDao = mock(CardRefundableDepositItemDAO.class);

        dataService.setConverter(new CardRefundableDepositItemConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindCardRefundableDepositItem() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDepositItem) anyObject())).thenReturn(getTestCardRefundableDepositItem1());

        ItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((CardRefundableDepositItem) anyObject());
        assertEquals(PRICE_1, resultsDTO.getPrice());
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCardRefundableDepositItem() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDepositItem) anyObject())).thenReturn(null);

        ItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((CardRefundableDepositItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCartId() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDepositItem) anyObject())).thenReturn(getTestCardRefundableDepositItem1());

        ItemDTO resultsDTO = dataService.findByCartIdAndCardId(null, CARD_ID_1);

        verify(mockDao, never()).findByExampleUniqueResult((CardRefundableDepositItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCardId() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDepositItem) anyObject())).thenReturn(getTestCardRefundableDepositItem1());

        ItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, null);

        verify(mockDao, never()).findByExampleUniqueResult((CardRefundableDepositItem) anyObject());
        assertNull(resultsDTO);
    }
    
    @Test
    public void findByOrderNumberShouldFindCardRefundableDepositItem() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDepositItem) anyObject())).thenReturn(getTestCardRefundableDepositItem1());

        ItemDTO resultsDTO = dataService.findByOrderNumber(ORDER_ID);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((CardRefundableDepositItem) anyObject());
        assertEquals(PRICE_1, resultsDTO.getPrice());
    }
    
    @Test
    public void findByOrderNumberShouldFindNullWithNullCardRefundableDepositItem() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDepositItem) anyObject())).thenReturn(null);

        ItemDTO resultsDTO = dataService.findByOrderNumber(ORDER_ID);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((CardRefundableDepositItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test(expected = AssertionError.class)
    public void findByOrderNumberShouldFindNullWithNullOrderId() {
        when(mockDao.findByExampleUniqueResult((CardRefundableDepositItem) anyObject())).thenReturn(getTestCardRefundableDepositItem1());

        ItemDTO resultsDTO = dataService.findByOrderNumber(null);

        verify(mockDao, never()).findByExampleUniqueResult((CardRefundableDepositItem) anyObject());
        assertNull(resultsDTO);
    }

}
