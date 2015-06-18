package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.DateTestUtil.DATE_20_03_2014;
import static com.novacroft.nemo.test_support.DateTestUtil.DATE_20_07_2014;
import static com.novacroft.nemo.test_support.DateTestUtil.get1Jan;
import static com.novacroft.nemo.test_support.DateTestUtil.get31Jan;
import static com.novacroft.nemo.test_support.JobLogTestUtil.FILE_NAME_3;
import static com.novacroft.nemo.test_support.JobLogTestUtil.JOB_ID3;
import static com.novacroft.nemo.test_support.JobLogTestUtil.getTestJobLog3;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.anyVararg;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.JobLogConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.JobLogDAO;
import com.novacroft.nemo.tfl.common.domain.JobLog;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;

public class JobLogDataServiceImplTest {
    private static final Integer LIMIT_START = 30;
    private static final Integer LIMIT_END = 40;
    private static final String TEST_BLANK_JOB_NAME = "  ";
    
    private JobLogDataServiceImpl service;
    private JobLogDAO mockDAO;
    
    private List<JobLog> mockReturnedList;
    
    @Before
    public void setUp() {
        service = new JobLogDataServiceImpl();
        mockDAO = mock(JobLogDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new JobLogConverterImpl());
        
        mockReturnedList = mock(List.class);
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findByFileNameShouldReturnDTOList() {
        when(mockDAO.findByExample(any(JobLog.class))).thenReturn(Arrays.asList(getTestJobLog3()));
        List<JobLogDTO> actualList = service.findByFileName(FILE_NAME_3);
        assertEquals(1, actualList.size());
        assertEquals(FILE_NAME_3, actualList.get(0).getFileName());
    }
    
    @Test
    public void findJobLogsByDatesAndJobNameIfJobNameIsBlank() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(mockReturnedList);
        assertEquals(mockReturnedList, 
                        service.findJobLogSearchDetailsByExactExecutionDatesAndJobName(
                                        TEST_BLANK_JOB_NAME, DATE_20_03_2014, DATE_20_07_2014));
        verify(mockDAO).findByQuery(anyString(), anyObject(), anyObject());
    }
    
    @Test
    public void findJobLogsByDatesAndJobNameIfJobNameIsNotBlank() {
        when(mockDAO.findByQuery(anyString(), anyVararg())).thenReturn(mockReturnedList);
        assertEquals(mockReturnedList, 
                        service.findJobLogSearchDetailsByExactExecutionDatesAndJobName(
                                        FILE_NAME_3, DATE_20_03_2014, DATE_20_07_2014));
        verify(mockDAO).findByQuery(anyString(), anyObject(), anyObject(), anyObject());
    }
    
    @Test
    public void findJobLogsByDatesAndJobNameWithLimitIfJobNameIsBlank() {
        when(mockDAO.findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyVararg())).thenReturn(mockReturnedList);
        assertEquals(mockReturnedList, 
                        service.findJobLogSearchDetailsByExactExecutionDatesAndJobNameWithLimits(
                                        TEST_BLANK_JOB_NAME, get1Jan(), get31Jan(),
                                        LIMIT_START, LIMIT_END));
        verify(mockDAO).findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyObject(), anyObject());
    }
    
    @Test
    public void findJobLogsByDatesAndJobNameWithLimitIfJobNameIsNotBlank() {
        when(mockDAO.findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyVararg())).thenReturn(mockReturnedList);
        assertEquals(mockReturnedList, 
                        service.findJobLogSearchDetailsByExactExecutionDatesAndJobNameWithLimits(
                                        FILE_NAME_3, get1Jan(), get31Jan(), LIMIT_START, LIMIT_END));
        verify(mockDAO).findByQueryWithLimit(anyString(), anyInt(), anyInt(), anyObject(), anyObject(), anyObject());
    }
    
    @Test
    public void getJobLogDetailsByJobIdShouldReturnNull() {
        when(mockDAO.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(null);
        assertNull(service.getJobLogDetailsByJobId(null));
    }
    
    @Test
    public void getJobLogDetailsByJobIdShouldReturnDTO() {
        when(mockDAO.findByQueryUniqueResult(anyString(), anyVararg())).thenReturn(getTestJobLog3());
        JobLogDTO actualDTO = service.getJobLogDetailsByJobId(JOB_ID3);
        assertNotNull(actualDTO);
        assertEquals(JOB_ID3, actualDTO.getId());
    }
    
    @Test
    public void shouldCreate() {
        JobLogDataServiceImpl spyService = spy(service);
        doAnswer(returnsFirstArg()).when(spyService).createOrUpdate(any(JobLogDTO.class));
        
        JobLogDTO testDTO = new JobLogDTO();
        spyService.create(testDTO);
        verify(spyService).createOrUpdate(testDTO);
    }
}
