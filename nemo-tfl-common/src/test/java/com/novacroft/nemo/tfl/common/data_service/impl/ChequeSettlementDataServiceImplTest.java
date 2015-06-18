package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.CHEQUE_SERIAL_NUMBER;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.REFERENCE_NUMBER;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.SETTLEMENT_NUMBER;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.SETTLEMENT_STATUS;
import static com.novacroft.nemo.test_support.ChequeSettlementTestUtil.getTestChequeSettlement1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.ChequeSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.ChequeSettlementDAO;
import com.novacroft.nemo.tfl.common.domain.ChequeSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;

public class ChequeSettlementDataServiceImplTest {
    private ChequeSettlementDataServiceImpl service;
    private ChequeSettlementDAO mockDAO;
    private ChequeSettlementConverterImpl mockConverter;
    
    private ChequeSettlement mockSettlement;
    private ChequeSettlementDTO mockSettlementDTO;
    
    @Before
    public void setUp() {
        service = new ChequeSettlementDataServiceImpl();
        mockDAO = mock(ChequeSettlementDAO.class);
        mockConverter = mock(ChequeSettlementConverterImpl.class);
        service.setDao(mockDAO);
        service.setConverter(mockConverter);
        
        mockSettlement = mock(ChequeSettlement.class);
        mockSettlementDTO = mock(ChequeSettlementDTO.class);
        
        when(mockConverter.convertDtoToEntity(any(ChequeSettlementDTO.class), any(ChequeSettlement.class)))
            .thenReturn(mockSettlement);
        when(mockConverter.convertEntityToDto(any(ChequeSettlement.class))).thenReturn(mockSettlementDTO);
    }
    
    @Test
    public void findByStatusShouldReturnEmptyList() {
        when(mockDAO.findByExample(any(ChequeSettlement.class))).thenReturn(new ArrayList<ChequeSettlement>());
        assertTrue(service.findByStatus(SETTLEMENT_STATUS).isEmpty());
    }
    
    @Test
    public void findByStatusShouldReturnDTOList() {
        when(mockDAO.findByExample(any(ChequeSettlement.class))).thenReturn(Arrays.asList(getTestChequeSettlement1()));
        List<ChequeSettlementDTO> actualList = service.findByStatus(SETTLEMENT_STATUS);
        assertEquals(1, actualList.size());
        assertEquals(mockSettlementDTO, actualList.get(0));
    }
    
    @Test
    public void findAllByOrderNumberShouldReturnDTOList() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(Arrays.asList(getTestChequeSettlement1()));
        List<ChequeSettlementDTO> actualList = service.findAllByOrderNumber(REFERENCE_NUMBER);
        verify(mockDAO).findByQuery(ChequeSettlementDataServiceImpl.FIND_BY_ORDER_NUMBER_HSQL, REFERENCE_NUMBER);
        assertEquals(1, actualList.size());
        assertEquals(mockSettlementDTO, actualList.get(0));
    }
    
    @Test
    public void findByOrderNumberShouldReturnNull() {
        when(mockDAO.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);        
        assertNull(service.findByOrderNumber(REFERENCE_NUMBER));
        verify(mockDAO).findByQueryUniqueResult(ChequeSettlementDataServiceImpl.FIND_BY_ORDER_NUMBER_HSQL, REFERENCE_NUMBER);
    }
    
    @Test
    public void findByOrderNumberShouldReturnDTO() {
        when(mockDAO.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestChequeSettlement1());
        assertEquals(mockSettlementDTO, service.findByOrderNumber(REFERENCE_NUMBER));
        verify(mockDAO).findByQueryUniqueResult(ChequeSettlementDataServiceImpl.FIND_BY_ORDER_NUMBER_HSQL, REFERENCE_NUMBER);
    }
    
    @Test
    public void findByChequeSerialNumberShouldReturnNull() {
        when(mockDAO.findByExampleUniqueResult(any(ChequeSettlement.class))).thenReturn(null);
        assertNull(service.findByChequeSerialNumber(CHEQUE_SERIAL_NUMBER));
    }
    
    @Test
    public void findByChequeSerialNumberShouldReturnDTO() {
        when(mockDAO.findByExampleUniqueResult(any(ChequeSettlement.class))).thenReturn(getTestChequeSettlement1());
        assertEquals(mockSettlementDTO, service.findByChequeSerialNumber(CHEQUE_SERIAL_NUMBER));
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findBySettlementNumberShouldReturnNull() {
        when(mockDAO.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);
        assertNull(service.findBySettlementNumber(SETTLEMENT_NUMBER));
    }
    
    @Test
    public void findBySettlementNumberShouldReturnDTO() {
        when(mockDAO.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestChequeSettlement1());
        assertEquals(mockSettlementDTO, service.findBySettlementNumber(SETTLEMENT_NUMBER));
    }
}
