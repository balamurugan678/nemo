package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.REASONID1;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.TYPE;
import static com.novacroft.nemo.test_support.GoodwillReasonTestUtil.getTestGoodwillReason1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.GoodwillReasonConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.GoodwillReasonDAO;
import com.novacroft.nemo.tfl.common.domain.GoodwillReason;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;

public class GoodwillReasonDataServiceImplTest {
    private static final Integer ZERO_PRICE = 0;
    private static final Integer MINUS_PRICE = -1;
    private static final Integer TEST_NO_RECORD_PRICE = 333;
    private static final Integer TEST_FOUND_PRICE = 444;
    
    private GoodwillReasonDataServiceImpl service;
    private GoodwillReasonDAO mockDAO;
    
    private GoodwillReason testReturnedReason;
    
    @Before
    public void setUp() {
        service = new GoodwillReasonDataServiceImpl();
        mockDAO = mock(GoodwillReasonDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new GoodwillReasonConverterImpl());
        
        testReturnedReason = getTestGoodwillReason1();
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findByTicketPriceShouldReturnNullIfZero() {
        assertNull(service.findByTicketPrice(ZERO_PRICE));
    }
    
    @Test
    public void findByTicketPriceShouldReturnNullIfMinus() {
        assertNull(service.findByTicketPrice(MINUS_PRICE));
    }
    
    @Test
    public void findByTicketPriceShouldReturnZeroValueDTO() {
        when(mockDAO.findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_PRICE, TEST_NO_RECORD_PRICE))
                .thenReturn(null);
        when(mockDAO.findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_PRICE, 0))
                .thenReturn(testReturnedReason);
        
        GoodwillReasonDTO actualResult = service.findByTicketPrice(TEST_NO_RECORD_PRICE);
        assertNotNull(actualResult);
        assertEquals(REASONID1, actualResult.getReasonId());
        verify(mockDAO).findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_PRICE, TEST_NO_RECORD_PRICE);
        verify(mockDAO).findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_PRICE, 0);
    }
    
    @Test
    public void findByTicketPriceShouldReturnNonZeroDTO() {
        when(mockDAO.findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_PRICE, TEST_FOUND_PRICE))
                .thenReturn(testReturnedReason);
        
        GoodwillReasonDTO actualResult = service.findByTicketPrice(TEST_FOUND_PRICE);
        assertNotNull(actualResult);
        assertEquals(REASONID1, actualResult.getReasonId());
        verify(mockDAO).findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_PRICE, TEST_FOUND_PRICE);
        verify(mockDAO, never()).findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_PRICE, 0);
    }
    
    @Test
    public void shouldFindByReasonId() {
        when(mockDAO.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(testReturnedReason);
        
        GoodwillReasonDTO actualResult = service.findByReasonId(REASONID1);
        assertEquals(REASONID1, actualResult.getReasonId());
        verify(mockDAO).findByQueryUniqueResult(GoodwillReasonDataServiceImpl.HQL_FIND_BY_REASON_ID, REASONID1);
    }
    
    @Test
    public void shouldFindByType() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(testReturnedReason));
        
        List<GoodwillReasonDTO> actualList = service.findByType(TYPE);
        assertEquals(1, actualList.size());
        assertEquals(TYPE, actualList.get(0).getType());
        verify(mockDAO).findByQuery(GoodwillReasonDataServiceImpl.HQL_FIND_BY_TYPE, TYPE);
    }
}
