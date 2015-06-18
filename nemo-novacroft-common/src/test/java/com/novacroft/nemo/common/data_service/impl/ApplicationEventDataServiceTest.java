package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.ApplicationEventConverterImpl;
import com.novacroft.nemo.common.data_access.ApplicationEventDAO;
import com.novacroft.nemo.common.data_service.ApplicationEventDataService;
import com.novacroft.nemo.common.domain.ApplicationEvent;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.EventTestUtil.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class ApplicationEventDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(ApplicationEventDataServiceTest.class);
    static final String TEST_DUMMY_HQL = "select 1 from sys.dual";

    Query query = mock(Query.class);
    Query query1 = mock(Query.class);

    ApplicationEventDAO mockDao = mock(ApplicationEventDAO.class);
    ApplicationEventDataServiceImpl dataService = new ApplicationEventDataServiceImpl();
    ApplicationEventConverterImpl mockConverter = new ApplicationEventConverterImpl();

    List<ApplicationEvent> eventList = new ArrayList<ApplicationEvent>();

    @Before
    public void setUp() throws Exception {
        eventList.add(getApplicationEvent1());
        when(mockDao.findByExample((ApplicationEvent) any())).thenReturn(eventList);
        dataService.setDao(mockDao);
        dataService.setConverter(mockConverter);
    }

    @Test
    public void testSetDaoApplicationEventDAO() {
        ApplicationEventDataService mockService = mock(ApplicationEventDataServiceImpl.class);
        mockService.setDao(mockDao);
        verify(mockService).setDao(mockDao);
    }

    @Test
    public void testSetConverterApplicationEventConverterImpl() {
        dataService.setConverter(mockConverter);
    }

    @Test
    public void testGetNewEntity() {
        ApplicationEvent newEntity = dataService.getNewEntity();
        assertTrue(newEntity instanceof ApplicationEvent);
    }

    @Test
    public void testfindByCustomerIdShouldFindApplicationEvent() {
        List<ApplicationEventDTO> events = dataService.findByCustomerId(APPLICATIONEVENT_CUSTOMER_ID);
        verify(mockDao).findByExample((ApplicationEvent) any());
        assertEquals(APPLICATIONEVENT_CUSTOMER_ID, events.get(0).getCustomerId());
    }

    @Test
    public void testFindByCustomerShouldFindApplicationEvent() {
        List<ApplicationEventDTO> events = dataService.findByCustomerId(APPLICATIONEVENT_CUSTOMER_ID);
        verify(mockDao).findByExample((ApplicationEvent) any());
        assertEquals(APPLICATIONEVENT_CUSTOMER_ID, events.get(0).getCustomerId());
    }

    @Test
    public void testFindByEventIdShouldFindApplicationEvent() {
        List<ApplicationEventDTO> events = dataService.findByEventId(APPLICATIONEVENT_EVENT_ID);
        verify(mockDao).findByExample((ApplicationEvent) any());
        assertEquals(APPLICATIONEVENT_EVENT_ID, events.get(0).getCustomerId());
    }

}
