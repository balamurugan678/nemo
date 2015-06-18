package com.novacroft.nemo.common.data_service.impl;

import com.novacroft.nemo.common.converter.impl.EventConverterImpl;
import com.novacroft.nemo.common.data_access.EventDAO;
import com.novacroft.nemo.common.data_service.EventDataService;
import com.novacroft.nemo.common.domain.Event;
import com.novacroft.nemo.common.transfer.EventDTO;
import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

import static com.novacroft.nemo.test_support.EventTestUtil.EVENT_NAME_1;
import static com.novacroft.nemo.test_support.EventTestUtil.getEvent1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class EventDataServiceTest {

    static final Logger logger = LoggerFactory.getLogger(EventDataServiceTest.class);
    static final String TEST_DUMMY_HQL = "select 1 from sys.dual";

    Query query = mock(Query.class);
    Query query1 = mock(Query.class);

    EventDAO mockDao = mock(EventDAO.class);
    EventDataService dataService = new EventDataServiceImpl();
    EventConverterImpl mockConverter = new EventConverterImpl();

    @Before
    public void setUp() throws Exception {
        List<Event> eventList = new ArrayList<Event>();
        eventList.add(getEvent1());
        when(mockDao.findByQueryUniqueResult(anyString(), anyObject())).thenReturn(getEvent1());
        when(mockDao.findByQuery(anyString(), anyObject())).thenReturn(eventList);
        dataService.setDao(mockDao);
        dataService.setConverter(mockConverter);
    }

    @Test
    public void testSetDaoEventDAO() {
        EventDataService mockService = mock(EventDataServiceImpl.class);
        mockService.setDao(mockDao);
        verify(mockService).setDao(mockDao);
    }

    @Test
    public void testSetConverterEventConverterImpl() {
        dataService.setConverter(mockConverter);
    }

    @Test
    public void testGetNewEntity() {
        Event newEntity = dataService.getNewEntity();
        assertTrue(newEntity instanceof Event);
    }

    @Test
    public void testFindByEventNameShouldReturnEvent() {
        EventDTO eventDTO = dataService.findByEventName(EVENT_NAME_1);
        verify(mockDao).findByQueryUniqueResult(anyString(), anyObject());
        assertEquals(EVENT_NAME_1, eventDTO.getName());
    }

    @Test
    public void testFindByEventNameShouldReturnNull() {
        when(mockDao.findByQueryUniqueResult(anyString(), eq("EMPTY"))).thenReturn(null);
        EventDTO eventDTO = dataService.findByEventName("EMPTY");
        verify(mockDao).findByQueryUniqueResult(anyString(), eq("EMPTY"));
        assertEquals(eventDTO, null);
    }

    @Test
    public void testFindAllByDisplayOnlineShouldFindRecord() {
        List<EventDTO> events = dataService.findAllByDisplayOnline(1);
        verify(mockDao).findByQuery(anyString(), anyObject());
        assertEquals(EVENT_NAME_1, events.get(0).getName());
    }

    @Test
    public void testFindAllByDisplayOnlineShouldReturnEmptyList() {
        when(mockDao.findByQuery(anyString(), anyLong())).thenReturn(null);
        List<EventDTO> events = dataService.findAllByDisplayOnline(1);
        verify(mockDao).findByQuery(anyString(), anyLong());
        assertEquals(events.size(), 0);
    }

    @Test
    public void testFindAllByDisplayOnlineShouldReturnEmptyList2() {
        List<Event> emptyEventList = new ArrayList<Event>();
        when(mockDao.findByQuery(anyString(), anyLong())).thenReturn(emptyEventList);
        List<EventDTO> events = dataService.findAllByDisplayOnline(1);
        verify(mockDao).findByQuery(anyString(), anyLong());
        assertEquals(events.size(), 0);
    }

}
