package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.CustomerTestUtil.CUSTOMER_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.ID_1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.test_support.WebCreditStatementTestUtil;
import com.novacroft.nemo.tfl.common.converter.WebCreditStatementLineConverter;
import com.novacroft.nemo.tfl.common.converter.impl.WebCreditStatementLineConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.WebAccountCreditSettlementDAO;
import com.novacroft.nemo.tfl.common.transfer.WebCreditStatementLineDTO;

/**
 * WebAccountCreditSettlementDataService unit tests
 */
public class WebCreditSettlementDataServiceImplTest {
    private static final Long EXPECTED_LONG_BALANCE = 99l;
    private static final Integer EXPECTED_INT_BALANCE = 99;
    
    private WebCreditSettlementDataServiceImpl service;
    private WebAccountCreditSettlementDAO mockDAO;
    private WebCreditStatementLineConverter mockWebAccountCreditStatementLineConverter;
    
    @Before
    public void setUp() {
        mockDAO = mock(WebAccountCreditSettlementDAO.class);
        mockWebAccountCreditStatementLineConverter = mock(WebCreditStatementLineConverterImpl.class);
        
        service = new WebCreditSettlementDataServiceImpl();
        service.setDao(mockDAO);
        service.webAccountCreditStatementLineConverter = mockWebAccountCreditStatementLineConverter;
    }
    
    @Test
    public void findByCustomerIdShouldReturnEmptyList() {
        when(mockDAO.findByQuery(anyString(), anyLong())).thenReturn(null);
        
        assertTrue(service.findByCustomerId(CUSTOMER_ID_1).isEmpty());
    }

    @Test
    public void findByCustomerIdShouldReturnDTOList() {
        List<WebCreditStatementLineDTO> mockDTOList = mock(List.class);
        when(mockDAO.findByQuery(anyString(), anyLong()))
                .thenReturn(WebCreditStatementTestUtil.getTestRowList2());
        when(mockWebAccountCreditStatementLineConverter.convertEntityListToDtoList(any(List.class)))
                .thenReturn(mockDTOList);
        
        assertEquals(mockDTOList, service.findByCustomerId(CUSTOMER_ID_1));
        verify(mockWebAccountCreditStatementLineConverter).convertEntityListToDtoList(any(List.class));
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void getBalanceShouldReturnZero() {
        Query mockQuery = mock(Query.class);
        when(mockQuery.setLong(anyString(), anyLong())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), anyString())).thenReturn(mockQuery);
        when(mockQuery.uniqueResult()).thenReturn(null);
        
        when(mockDAO.createQuery(anyString())).thenReturn(mockQuery);
        assertEquals(Integer.valueOf(0), service.getBalance(CUSTOMER_ID_1));
    }
    
    @Test
    public void getBalanceShouldReturnExpectedBalance() {
        Query mockQuery = mock(Query.class);
        when(mockQuery.setLong(anyString(), anyLong())).thenReturn(mockQuery);
        when(mockQuery.setParameter(anyString(), anyString())).thenReturn(mockQuery);
        when(mockQuery.uniqueResult()).thenReturn((Object) EXPECTED_LONG_BALANCE);
        
        when(mockDAO.createQuery(anyString())).thenReturn(mockQuery);
        assertEquals(EXPECTED_INT_BALANCE, service.getBalance(CUSTOMER_ID_1));
    }
    
    @Test
    public void findByOrderIdShouldReturnDTOList() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(null);
        assertNotNull(service.findByOrderId(ID_1));
    }
}
