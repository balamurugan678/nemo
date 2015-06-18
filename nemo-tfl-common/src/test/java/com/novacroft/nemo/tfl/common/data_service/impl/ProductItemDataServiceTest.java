package com.novacroft.nemo.tfl.common.data_service.impl;

import com.novacroft.nemo.tfl.common.converter.impl.ProductItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ProductItemDAO;
import com.novacroft.nemo.tfl.common.domain.ProductItem;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
import static com.novacroft.nemo.test_support.ProductItemTestUtil.getTestTravelCardProductList1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.TRAVEL_PRICE_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.*;

/**
 * Product item data service unit tests.
 */
public class ProductItemDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(ProductItemDataServiceTest.class);

    private ProductItemDataServiceImpl dataService;
    private ProductItemDAO mockDao;

    @Before
    public void setUp() {
        dataService = new ProductItemDataServiceImpl();
        mockDao = mock(ProductItemDAO.class);

        dataService.setConverter(new ProductItemConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindProductItem() {
        when(mockDao.findByExample((ProductItem) anyObject())).thenReturn(getTestTravelCardProductList1());

        List<ProductItemDTO> resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExample((ProductItem) anyObject());
        assertEquals(TRAVEL_PRICE_1, resultsDTO.get(0).getPrice());
    }

    @Test
    public void findByCartIdAndCardIdShouldFindEmptyWithNullProductItem() {
        when(mockDao.findByExample((ProductItem) anyObject())).thenReturn(null);

        List<ProductItemDTO> resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExample((ProductItem) anyObject());
        assertTrue(resultsDTO.size() == 0);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindEmptyWithNullCartId() {
        when(mockDao.findByExample((ProductItem) anyObject())).thenReturn(getTestTravelCardProductList1());

        List<ProductItemDTO> resultsDTO = dataService.findByCartIdAndCardId(null, CARD_ID_1);

        verify(mockDao, never()).findByExample((ProductItem) anyObject());
        assertTrue(resultsDTO.size() == 0);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindEmptyWithNullCardId() {
        when(mockDao.findByExample((ProductItem) anyObject())).thenReturn(getTestTravelCardProductList1());

        List<ProductItemDTO> resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, null);

        verify(mockDao, never()).findByExample((ProductItem) anyObject());
        assertTrue(resultsDTO.size() == 0);
    }

}
