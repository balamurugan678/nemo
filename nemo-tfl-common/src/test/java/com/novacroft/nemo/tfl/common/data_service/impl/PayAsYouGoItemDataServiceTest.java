package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.tfl.common.converter.impl.PayAsYouGoItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.PayAsYouGoItemDAO;
import com.novacroft.nemo.tfl.common.domain.PayAsYouGoItem;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
import static com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil.getTestPayAsYouGoItem1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.PAY_AS_YOU_GO_TICKET_PRICE_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Pay as you go item data service unit tests.
 */
public class PayAsYouGoItemDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(PayAsYouGoItemDataServiceTest.class);

    private PayAsYouGoItemDataServiceImpl dataService;
    private PayAsYouGoItemDAO mockDao;

    @Before
    public void setUp() {
        dataService = new PayAsYouGoItemDataServiceImpl();
        mockDao = mock(PayAsYouGoItemDAO.class);

        dataService.setConverter(new PayAsYouGoItemConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindPayAsYouGoItem() {
        when(mockDao.findByExampleUniqueResult((PayAsYouGoItem) anyObject())).thenReturn(getTestPayAsYouGoItem1());

        PayAsYouGoItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((PayAsYouGoItem) anyObject());
        assertEquals(PAY_AS_YOU_GO_TICKET_PRICE_1, resultsDTO.getPrice());
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullPayAsYouGoItem() {
        when(mockDao.findByExampleUniqueResult((PayAsYouGoItem) anyObject())).thenReturn(null);

        PayAsYouGoItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((PayAsYouGoItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCartId() {
        when(mockDao.findByExampleUniqueResult((PayAsYouGoItem) anyObject())).thenReturn(getTestPayAsYouGoItem1());

        PayAsYouGoItemDTO resultsDTO = dataService.findByCartIdAndCardId(null, CARD_ID_1);

        verify(mockDao, never()).findByExampleUniqueResult((PayAsYouGoItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCardId() {
        when(mockDao.findByExampleUniqueResult((PayAsYouGoItem) anyObject())).thenReturn(getTestPayAsYouGoItem1());

        PayAsYouGoItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, null);

        verify(mockDao, never()).findByExampleUniqueResult((PayAsYouGoItem) anyObject());
        assertNull(resultsDTO);
    }

}
