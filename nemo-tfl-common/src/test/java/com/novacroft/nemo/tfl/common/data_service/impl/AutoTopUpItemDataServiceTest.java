package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.AUTO_TOP_UP_ITEM_ID_1;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.CART_ID_1;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.getTestAutoTopUpItem1;
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

import com.novacroft.nemo.tfl.common.converter.impl.AutoTopUpConfigurationItemConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AutoTopUpItemDAO;
import com.novacroft.nemo.tfl.common.domain.AutoTopUpConfigurationItem;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpConfigurationItemDTO;

/**
 * Auto top-up data service unit tests.
 */
public class AutoTopUpItemDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(AutoTopUpItemDataServiceTest.class);

    private AutoTopUpItemDataServiceImpl dataService;
    private AutoTopUpItemDAO mockDao;

    @Before
    public void setUp() {
        dataService = new AutoTopUpItemDataServiceImpl();
        mockDao = mock(AutoTopUpItemDAO.class);

        dataService.setConverter(new AutoTopUpConfigurationItemConverterImpl());
        dataService.setDao(mockDao);
    }
    
    @Test
    public void findByCartIdAndCardIdShouldFindAutoTopUpItem() {
        when(mockDao.findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject())).thenReturn(getTestAutoTopUpItem1());

        AutoTopUpConfigurationItemDTO resultsDTO = dataService.findByCartIdAndCardId(CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject());
        assertEquals(AUTO_TOP_UP_ITEM_ID_1, resultsDTO.getId());
    }

    @Test
    public void findByWebAccountIdAndCardIdShouldFindNullWithNullAutoTopUp() {
        when(mockDao.findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject())).thenReturn(null);

        AutoTopUpConfigurationItemDTO resultsDTO = dataService.findByCartIdAndCardId(CART_ID_1, CARD_ID_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject());
        assertNull(resultsDTO);
    }

    @Test(expected = AssertionError.class)
    public void findByCartIdAndCardIdShouldFindNullWithNullCartId() {
        when(mockDao.findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject())).thenReturn(getTestAutoTopUpItem1());

        AutoTopUpConfigurationItemDTO resultsDTO = dataService.findByCartIdAndCardId(null, CARD_ID_1);

        verify(mockDao, never()).findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject());
        assertNull(resultsDTO);
    }
    
    @Test(expected = AssertionError.class)
    public void findByCartIdAndCardIdShouldFindNullWithNullCardId() {
        when(mockDao.findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject())).thenReturn(getTestAutoTopUpItem1());

        AutoTopUpConfigurationItemDTO resultsDTO = dataService.findByCartIdAndCardId(CART_ID_1, null);

        verify(mockDao, never()).findByExampleUniqueResult((AutoTopUpConfigurationItem) anyObject());
        assertNull(resultsDTO);
    }

}
