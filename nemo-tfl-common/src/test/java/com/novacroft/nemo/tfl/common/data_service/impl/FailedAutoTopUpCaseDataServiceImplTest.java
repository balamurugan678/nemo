package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.CASE_ID;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.CASE_PROGRESSION_STATUS;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.CASE_STATUS;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.TEST_CARD_NUMBER;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.CUSTOMER_ID;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.getTestFailedAutoTopUpCase;
import static com.novacroft.nemo.test_support.FailedAutoTopUpCaseTestUtil.getTestFailedAutoTopUpCaseWithCustomer;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.FailedAutoTopUpCaseConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.FailedAutoTopUpCaseDAO;
import com.novacroft.nemo.tfl.common.domain.FailedAutoTopUpCase;
import com.novacroft.nemo.tfl.common.transfer.FailedAutoTopUpCaseDTO;

public class FailedAutoTopUpCaseDataServiceImplTest {

    private FailedAutoTopUpCaseDataServiceImpl dataService;
    private FailedAutoTopUpCaseDAO mockDao;

    @Before
    public void setUp() throws Exception {
        dataService = new FailedAutoTopUpCaseDataServiceImpl();
        mockDao = mock(FailedAutoTopUpCaseDAO.class);

        dataService.setDao(mockDao);
        dataService.setConverter(new FailedAutoTopUpCaseConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(dataService.getNewEntity());
    }
    
    @Test
    public void shouldCreate() {
    	FailedAutoTopUpCaseDataServiceImpl spyService = spy(dataService);
        doAnswer(returnsFirstArg()).when(spyService).createOrUpdate(any(FailedAutoTopUpCaseDTO.class));
        FailedAutoTopUpCaseDTO testDTO = new FailedAutoTopUpCaseDTO();
        spyService.create(testDTO);
        verify(spyService).createOrUpdate(testDTO);
    }
 
    @Test
    public void getEmptyFailedAutoTopUpShouldReturnFalse() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);
        boolean isOysterCardWithFailedAutoTopUpCase = dataService.isOysterCardWithFailedAutoTopUpCase(TEST_CARD_NUMBER);
        assertFalse(isOysterCardWithFailedAutoTopUpCase);
    }
    
    @Test
    public void getFailedAutoTopUpShouldReturnTrue() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestFailedAutoTopUpCase());
        boolean isOysterCardWithFailedAutoTopUpCase = dataService.isOysterCardWithFailedAutoTopUpCase(TEST_CARD_NUMBER);
        assertTrue(isOysterCardWithFailedAutoTopUpCase);
    }
    
    @Test
    public void findByFATUCaseIdWithEmptyRecordsShouldReturnFalse() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);
        FailedAutoTopUpCaseDTO  failedAutoTopUpCaseDTO = dataService.findByFATUCaseId(CASE_ID);
        assertNull(failedAutoTopUpCaseDTO);
    }
    
    @Test
    public void findByFATUCaseIdWithRecordsShouldReturnTrue() {
        when(mockDao.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestFailedAutoTopUpCase());
        FailedAutoTopUpCaseDTO failedAutoTopUpCaseDTO = dataService.findByFATUCaseId(CASE_ID);
        assertNotNull(failedAutoTopUpCaseDTO);
        assertEquals(CASE_STATUS, failedAutoTopUpCaseDTO.getCaseStatus());
    }
     
    @Test
    public void findByCustomerIdShouldReturnDTOList() {
        when(mockDao.findByExample(any(FailedAutoTopUpCase.class))).thenReturn(Arrays.asList(getTestFailedAutoTopUpCase()));
        List<FailedAutoTopUpCaseDTO> actualList = dataService.findByCustomerId(getTestFailedAutoTopUpCaseWithCustomer());
        assertEquals(1, actualList.size());
        assertEquals(CASE_PROGRESSION_STATUS, actualList.get(0).getCaseProgressionStatus());
    }
    
    @Test
    public void findPendingFailedAutoTopUpItemByCustomerIdShouldReturnDTOList() {
        when(mockDao.findByExample(any(FailedAutoTopUpCase.class))).thenReturn(Arrays.asList(getTestFailedAutoTopUpCase()));
        List<FailedAutoTopUpCaseDTO> actualList = dataService.findPendingItemsByCustomerId(CUSTOMER_ID);
        assertEquals(1, actualList.size());
        assertEquals(CASE_STATUS, actualList.get(0).getCaseStatus());
    }
    
    @Test
    public void shouldFindAmountByCustomerId() {
    	List<FailedAutoTopUpCase> list = new ArrayList<FailedAutoTopUpCase>();
    	FailedAutoTopUpCase failedAutoTopUpCase = new FailedAutoTopUpCase();
    	failedAutoTopUpCase.setFailedAutoTopUpAmount((double)2000);
    	list.add(failedAutoTopUpCase);
    	when(mockDao.findByExample(any(FailedAutoTopUpCase.class))).thenReturn(list);
    	int amountOwed = dataService.findPendingAmountByCustomerId(CUSTOMER_ID);
    	assertEquals(20, amountOwed);
    }
}
