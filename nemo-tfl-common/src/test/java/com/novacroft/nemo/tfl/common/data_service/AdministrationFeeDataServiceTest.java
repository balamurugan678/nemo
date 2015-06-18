package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.converter.impl.AdministrationFeeConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AdministrationFeeDAO;
import com.novacroft.nemo.tfl.common.data_service.impl.AdministrationFeeDataServiceImpl;
import com.novacroft.nemo.tfl.common.domain.AdministrationFee;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.AdministrationFeeTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

/**
 * Administration fee data service unit tests.
 */
public class AdministrationFeeDataServiceTest {
    
    private static final Integer INT_MINUS_ONE = -1;

    private AdministrationFeeDataService dataService;
    private AdministrationFeeDAO mockDao;

    @Before
    public void setUp() {
        dataService = new AdministrationFeeDataServiceImpl();
        mockDao = mock(AdministrationFeeDAO.class);
        dataService.setConverter(new AdministrationFeeConverterImpl());
        dataService.setDao(mockDao);
    }
    
    @Test
    public void findByPriceShouldReturnNull() {
        assertNull(dataService.findByPrice(INT_MINUS_ONE));
    }

    @Test
    public void findByPriceShouldFindAdministrationFeeItem() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyInt())).thenReturn(getTestAdministrationFee1());

        AdministrationFeeDTO resultsDTO = dataService.findByPrice(ADMINISTRATION_FEE_PRICE_1);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), anyInt());
        assertEquals(ADMINISTRATION_FEE_NAME_1, resultsDTO.getType());
    }

    @Test
    public void findByTicketPriceShouldFindOtherPayAsYouGoItem() {
        when(mockDao.findByQueryUniqueResult(anyString(), eq(ADMINISTRATION_FEE_PRICE_1))).thenReturn(null);
        when(mockDao.findByQueryUniqueResult(anyString(), eq(ADMINISTRATION_FEE_PRICE_ZERO))).thenReturn(getTestAdministrationFee1());

        AdministrationFeeDTO resultsDTO = dataService.findByPrice(ADMINISTRATION_FEE_PRICE_1);
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), eq(ADMINISTRATION_FEE_PRICE_1));
        verify(mockDao, atLeastOnce()).findByQueryUniqueResult(anyString(), eq(ADMINISTRATION_FEE_PRICE_ZERO));
        assertEquals(ADMINISTRATION_FEE_NAME_1, resultsDTO.getType());
    }
    
    @Test
    public void findByTypeShouldReturnNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);
        assertNull(dataService.findByType(""));
    }
    
    @Test
    public void findByTypeShouldReturnNotNull() {
        AdministrationFee testFee = new AdministrationFee();
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(testFee);
        assertNotNull(dataService.findByType(""));
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(dataService.getNewEntity());
    }
}
