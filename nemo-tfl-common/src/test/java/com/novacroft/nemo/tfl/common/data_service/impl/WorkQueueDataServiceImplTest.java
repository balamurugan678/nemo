package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.tfl.common.converter.impl.WorkQueueConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.WorkQueueDAO;
import com.novacroft.nemo.tfl.common.domain.WorkQueue;

public class WorkQueueDataServiceImplTest {
    private static final String TEST_STATUS = "status";
    private static final String TEST_STRING = "test";
    
    private WorkQueueDataServiceImpl service;
    private WorkQueueDAO mockDAO;
    
    @Before
    public void setUp() {
        service = new WorkQueueDataServiceImpl();
        mockDAO = mock(WorkQueueDAO.class);
        service.setDao(mockDAO);
        service.setConverter(new WorkQueueConverterImpl());
    }
    
    @Test
    public void getNewEntityNotNull() {
        assertNotNull(service.getNewEntity());
    }
    
    @Test
    public void findAllByShouldReturnEmptyList() {
        when(mockDAO.findByExample(any(WorkQueue.class))).thenReturn(new ArrayList<WorkQueue>());
        assertTrue(service.findAllBy(TEST_STATUS).isEmpty());
    }
    
    @Test
    public void findAllByShouldReturnDTOList() {
        List<WorkQueue> twoQueuesLst = Arrays.asList(new WorkQueue(), new WorkQueue());
        when(mockDAO.findByExample(any(WorkQueue.class))).thenReturn(twoQueuesLst);
        assertEquals(twoQueuesLst.size(), service.findAllBy(TEST_STATUS).size());
    }
    
    @Test
    public void findByTestShouldReturnNull() {
        assertNull(service.findByTest(TEST_STRING));
    }
}
