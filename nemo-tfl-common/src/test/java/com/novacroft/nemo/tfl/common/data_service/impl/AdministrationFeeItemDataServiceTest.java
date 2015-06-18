package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil.getTestAdministrationFeeItem1;
import static com.novacroft.nemo.test_support.CartItemTestUtil.ADMINISTRATION_FEE_PRICE_1;
import static com.novacroft.nemo.test_support.CartTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CartTestUtil.PURCHASE_CART_ID_1;
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

import com.novacroft.nemo.tfl.common.converter.impl.AdministrationFeeItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AdministrationFeeItemDAO;
import com.novacroft.nemo.tfl.common.domain.AdministrationFeeItem;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;

/**
 * Administration Fee item data service unit tests.
 */
public class AdministrationFeeItemDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(AdministrationFeeItemDataServiceTest.class);

    private AdministrationFeeItemDataServiceImpl dataService;
    private AdministrationFeeItemDAO mockDao;

    @Before
    public void setUp() {
        dataService = new AdministrationFeeItemDataServiceImpl();
        mockDao = mock(AdministrationFeeItemDAO.class);

        dataService.setConverter(new AdministrationFeeItemConverterImpl());
        dataService.setDao(mockDao);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindAdministrationFeeItem() {
        when(mockDao.findByExampleUniqueResult((AdministrationFeeItem) anyObject())).thenReturn(getTestAdministrationFeeItem1());

        AdministrationFeeItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((AdministrationFeeItem) anyObject());
        assertEquals(ADMINISTRATION_FEE_PRICE_1, resultsDTO.getPrice());
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullPayAsYouGoItem() {
        when(mockDao.findByExampleUniqueResult((AdministrationFeeItem) anyObject())).thenReturn(null);

        AdministrationFeeItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((AdministrationFeeItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCartId() {
        when(mockDao.findByExampleUniqueResult((AdministrationFeeItem) anyObject())).thenReturn(getTestAdministrationFeeItem1());

        AdministrationFeeItemDTO resultsDTO = dataService.findByCartIdAndCardId(null, CARD_ID_1);

        verify(mockDao, never()).findByExampleUniqueResult((AdministrationFeeItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByCartIdAndCardIdShouldFindNullWithNullCardId() {
        when(mockDao.findByExampleUniqueResult((AdministrationFeeItem) anyObject())).thenReturn(getTestAdministrationFeeItem1());

        AdministrationFeeItemDTO resultsDTO = dataService.findByCartIdAndCardId(PURCHASE_CART_ID_1, null);

        verify(mockDao, never()).findByExampleUniqueResult((AdministrationFeeItem) anyObject());
        assertNull(resultsDTO);
    }

}
