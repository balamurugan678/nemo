package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.BACSSettlementTestUtil.PAYMENT_REFERENCE;
import static org.apache.commons.collections.CollectionUtils.isEmpty;
import static org.apache.commons.collections.CollectionUtils.isNotEmpty;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.BACSSettlementConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.BACSSettlementDAO;
import com.novacroft.nemo.tfl.common.domain.BACSSettlement;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;


public class BACSSettlementDataServiceImplTest {

    private BACSSettlementDataServiceImpl service;
	private BACSSettlementDAO mockBacsSettlementDao; 
	private BACSSettlementConverterImpl mockBacsSettlementConverter;
	
	@Before
	public void setUp(){
	    mockBacsSettlementDao = mock(BACSSettlementDAO.class);
	    mockBacsSettlementConverter = mock(BACSSettlementConverterImpl.class);
	    
	    service = new BACSSettlementDataServiceImpl();
	    service.setDao(mockBacsSettlementDao);
	    service.setConverter(mockBacsSettlementConverter);
	}
	
	
	@Test
	public void findByStatusNullShouldReturnEmptyList() {
        when(mockBacsSettlementDao.findByExample(any(BACSSettlement.class))).thenReturn(null);
        assertTrue(isEmpty(service.findByStatus(null)));
		
	}
	
	@Test
	public void findByFinancialServicesReferenceShouldReturnNull() {
		when(mockBacsSettlementDao.findByExampleUniqueResult(any(BACSSettlement.class))).thenReturn(null);
        assertNull(service.findByFinancialServicesReference(PAYMENT_REFERENCE));
	}

	
	@Test
	public void findByFinancialServicesReferenceShouldReturnDTO() {
		BACSSettlementDTO bacsSettlementDto  = new BACSSettlementDTO();
	    BACSSettlement bacsSettlement = new BACSSettlement();
	    bacsSettlement.setPaymentReference(PAYMENT_REFERENCE);
        
	    when(mockBacsSettlementDao.findByExampleUniqueResult(any(BACSSettlement.class))).thenReturn(bacsSettlement);
        when(mockBacsSettlementConverter.convertEntityToDto(bacsSettlement)).thenReturn(bacsSettlementDto);
	    
        assertTrue(service.findByFinancialServicesReference(PAYMENT_REFERENCE) == bacsSettlementDto);
	}
	
	@Test
	public void findByOrderNumberShouldReturnEmptyList() {
	    when(mockBacsSettlementDao.findByQuery(anyString(), anyVararg())).thenReturn(null);
	    assertTrue(isEmpty(service.findByOrderNumber(PAYMENT_REFERENCE)));
	}

	@Test
    public void findByOrderNumberShouldReturnDTOList() {
        when(mockBacsSettlementDao.findByQuery(anyString(), anyVararg()))
            .thenReturn(Arrays.asList(new BACSSettlement()));
        List<BACSSettlementDTO> actualDTOList = service.findByOrderNumber(PAYMENT_REFERENCE); 
        assertTrue(isNotEmpty(actualDTOList));
    }
	
	@Test
	public void shouldFindBySettlementNumber() {
	    BACSSettlementDTO mockSettlementDTO = mock(BACSSettlementDTO.class);
	    BACSSettlement mockSettlement = mock(BACSSettlement.class);
	    when(mockBacsSettlementDao.findByQueryUniqueResult(anyString(), anyVararg()))
	        .thenReturn(mockSettlement);
	    when(mockBacsSettlementConverter.convertEntityToDto(mockSettlement))
	        .thenReturn(mockSettlementDTO);
	    assertTrue(service.findBySettlementNumber(PAYMENT_REFERENCE) == mockSettlementDTO);
	}
	
	@Test
	public void getNewEntityNotNull() {
	    assertNotNull(service.getNewEntity());
	}
}
