package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.AUTO_TOP_UP_AMOUNT_1;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.AUTO_TOP_UP_ID_1;
import static com.novacroft.nemo.test_support.AutoTopUpTestUtil.getTestAutoTopUp1;
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

import com.novacroft.nemo.tfl.common.converter.impl.AutoTopUpConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AutoTopUpDAO;
import com.novacroft.nemo.tfl.common.domain.AutoTopUp;
import com.novacroft.nemo.tfl.common.transfer.AutoTopUpDTO;

/**
 * Auto top-up data service unit tests.
 */
public class AutoTopUpDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(AutoTopUpDataServiceTest.class);

    private AutoTopUpDataServiceImpl dataService;
    private AutoTopUpDAO mockDao;

    @Before
    public void setUp() {
        dataService = new AutoTopUpDataServiceImpl();
        mockDao = mock(AutoTopUpDAO.class);

        dataService.setConverter(new AutoTopUpConverterImpl());
        dataService.setDao(mockDao);
    }
    
    @Test
    public void findByAutoTopUpAmountShouldFindAutoTopUp() {
        when(mockDao.findByExampleUniqueResult((AutoTopUp) anyObject())).thenReturn(getTestAutoTopUp1());

        AutoTopUpDTO resultsDTO = dataService.findByAutoTopUpAmount(AUTO_TOP_UP_AMOUNT_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((AutoTopUp) anyObject());
        assertEquals(AUTO_TOP_UP_ID_1, resultsDTO.getId());
    }

    @Test
    public void findByAutoTopUpAmountShouldFindNullWithNullAutoTopUp() {
        when(mockDao.findByExampleUniqueResult((AutoTopUp) anyObject())).thenReturn(null);

        AutoTopUpDTO resultsDTO = dataService.findByAutoTopUpAmount(AUTO_TOP_UP_AMOUNT_1);

        verify(mockDao, atLeastOnce()).findByExampleUniqueResult((AutoTopUp) anyObject());
        assertNull(resultsDTO);
    }

    @Test
    public void findByAutoTopUpAmountShouldFindNullWithNullAutoTopUpAmount() {
        when(mockDao.findByExampleUniqueResult((AutoTopUp) anyObject())).thenReturn(getTestAutoTopUp1());

        AutoTopUpDTO resultsDTO = dataService.findByAutoTopUpAmount(null);

        verify(mockDao, never()).findByExampleUniqueResult((AutoTopUp) anyObject());
        assertNull(resultsDTO);
    }

}
