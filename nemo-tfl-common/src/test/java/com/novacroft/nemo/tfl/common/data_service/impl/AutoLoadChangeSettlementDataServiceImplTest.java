package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.novacroft.nemo.tfl.common.converter.impl.AutoLoadChangeSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.AutoLoadChangeSettlementDAO;
import com.novacroft.nemo.tfl.common.domain.AutoLoadChangeSettlement;
import com.novacroft.nemo.tfl.common.transfer.AutoLoadChangeSettlementDTO;

import org.junit.Before;
import org.junit.Test;

import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;
import static com.novacroft.nemo.test_support.SettlementTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * AutoLoadChangeSettlementDataService unit tests
 */
public class AutoLoadChangeSettlementDataServiceImplTest {
    private AutoLoadChangeSettlementDataServiceImpl service;
    private AutoLoadChangeSettlementDAO mockDao;
    private AutoLoadChangeSettlementConverterImpl mockConverter;
    
    @Before
    public void setUp() {
        mockDao = mock(AutoLoadChangeSettlementDAO.class);
        mockConverter = mock(AutoLoadChangeSettlementConverterImpl.class);
        
        service = new AutoLoadChangeSettlementDataServiceImpl();
        service.setDao(mockDao);
        service.setConverter(mockConverter);
    }
    
    @Test
    public void findByRequestSequenceNumberAndCardNumberShouldReturnNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyInt(), anyString())).thenReturn(null);
        assertNull(service.findByRequestSequenceNumberAndCardNumber(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1));
    }

    @Test
    public void findByRequestSequenceNumberAndCardNumberShouldReturnSettlement() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyInt(), anyString()))
                .thenReturn(getTestAutoLoadChangeSettlement1());
        when(mockConverter.convertEntityToDto(any(AutoLoadChangeSettlement.class)))
                .thenReturn(getTestAutoLoadChangeSettlementDTO1());
        
        AutoLoadChangeSettlementDTO result =
                service.findByRequestSequenceNumberAndCardNumber(REQUEST_SEQUENCE_NUMBER, OYSTER_NUMBER_1);
        assertEquals(REQUEST_SEQUENCE_NUMBER, result.getRequestSequenceNumber());
        verify(mockDao).findByQueryUniqueResult(anyString(), anyInt(), anyString());
        verify(mockConverter).convertEntityToDto(any(AutoLoadChangeSettlement.class));
    }
    
    @Test
    public void findLatestByCardIdShouldReturnNull() {
        when(mockDao.findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyVararg()))
                .thenReturn(new ArrayList<AutoLoadChangeSettlement>());
        assertNull(service.findLatestByCardId(PAYMENT_CARD_ID_1));
    }
    
    @Test
    public void findLatestByCardIdShouldReturnDTO() {
        AutoLoadChangeSettlement settlement = getTestAutoLoadChangeSettlement1();
        List<AutoLoadChangeSettlement> settlementList = Arrays.asList(settlement);
        AutoLoadChangeSettlementDTO mockDTO = mock(AutoLoadChangeSettlementDTO.class);
        when(mockDao.findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyVararg()))
                .thenReturn(settlementList);
        when(mockConverter.convertEntityToDto(settlementList.get(0))).thenReturn(mockDTO);
        assertTrue(service.findLatestByCardId(PAYMENT_CARD_ID_1) == mockDTO);
    }
    
    @Test
    public void findByOrderIdShouldReturnEmptyList() {
        when(mockDao.findByQuery(anyString(), anyVararg())).thenReturn(null);
        assertTrue(service.findByOrderId(PAYMENT_CARD_ID_1).isEmpty());
    }
    
    @Test
    public void findByOrderIdShouldReturnDTOList() {
        when(mockDao.findByQuery(anyString(), anyVararg()))
                .thenReturn(Arrays.asList(getTestAutoLoadChangeSettlement1()));
        List<AutoLoadChangeSettlementDTO> actualList = service.findByOrderId(PAYMENT_CARD_ID_1);
        assertNotNull(actualList);
        assertTrue(actualList.size() == 1);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
}
