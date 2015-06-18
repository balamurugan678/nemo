package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.tfl.common.converter.impl.ShippingMethodItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ShippingMethodItemDAO;
import com.novacroft.nemo.tfl.common.domain.ShippingMethodItem;
import com.novacroft.nemo.tfl.common.transfer.ShippingMethodItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
import static com.novacroft.nemo.test_support.ShippingMethodItemTestUtil.getTestShippingMethodItem1;
import static com.novacroft.nemo.test_support.ShippingMethodTestUtil.SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Shipping method item data service unit tests.
 */
public class ShippingMethodItemDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(ShippingMethodItemDataServiceTest.class);

    private ShippingMethodItemDataServiceImpl dataService;
    private ShippingMethodItemDAO mockDao;

    @Before
    public void setUp() {
        dataService = new ShippingMethodItemDataServiceImpl();
        mockDao = mock(ShippingMethodItemDAO.class);

        dataService.setConverter(new ShippingMethodItemConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindShippingMethodItem() {
        when(mockDao.findByExampleUniqueResult((ShippingMethodItem) anyObject())).thenReturn(getTestShippingMethodItem1());

        ShippingMethodItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((ShippingMethodItem) anyObject());
        assertEquals(SHIPPING_METHOD_RECORDED_DELIVERY_PRICE_1, resultsDTO.getPrice());
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullShippingMethodItem() {
        when(mockDao.findByExampleUniqueResult((ShippingMethodItem) anyObject())).thenReturn(null);

        ShippingMethodItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((ShippingMethodItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCartId() {
        when(mockDao.findByExampleUniqueResult((ShippingMethodItem) anyObject())).thenReturn(getTestShippingMethodItem1());

        ShippingMethodItemDTO resultsDTO = dataService.findByCartIdAndCardId(null, CARD_ID_1);

        verify(mockDao, never()).findByExampleUniqueResult((ShippingMethodItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCardId() {
        when(mockDao.findByExampleUniqueResult((ShippingMethodItem) anyObject())).thenReturn(getTestShippingMethodItem1());

        ShippingMethodItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, null);

        verify(mockDao, never()).findByExampleUniqueResult((ShippingMethodItem) anyObject());
        assertNull(resultsDTO);
    }

}
