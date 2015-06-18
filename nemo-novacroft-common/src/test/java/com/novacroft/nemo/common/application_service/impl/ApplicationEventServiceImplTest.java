package com.novacroft.nemo.common.application_service.impl;

import static com.novacroft.nemo.test_support.EventTestUtil.APPLICATIONEVENT_CUSTOMER_ID;
import static com.novacroft.nemo.test_support.EventTestUtil.APPLICATIONEVENT_EVENT_ID;
import static com.novacroft.nemo.test_support.EventTestUtil.getApplicationEvent1;
import static com.novacroft.nemo.test_support.EventTestUtil.getApplicationEventDTO1;
import static com.novacroft.nemo.test_support.EventTestUtil.getEvent1;
import static com.novacroft.nemo.test_support.EventTestUtil.getEventDTO1;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.common.constant.EventName;
import com.novacroft.nemo.common.converter.impl.ApplicationEventConverterImpl;
import com.novacroft.nemo.common.converter.impl.EventConverterImpl;
import com.novacroft.nemo.common.data_access.ApplicationEventDAO;
import com.novacroft.nemo.common.data_access.EventDAO;
import com.novacroft.nemo.common.data_service.ApplicationEventDataService;
import com.novacroft.nemo.common.data_service.EventDataService;
import com.novacroft.nemo.common.domain.ApplicationEvent;
import com.novacroft.nemo.common.domain.Event;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;

public class ApplicationEventServiceImplTest {
    static final Logger logger = LoggerFactory.getLogger(ApplicationEventServiceImplTest.class);
    static final String TEST_DUMMY_HQL = "select 1 from sys.dual";

    ApplicationEventServiceImpl applicationEventService = new ApplicationEventServiceImpl();
    ApplicationEventDataService applicationEventDataServiceMock = mock(ApplicationEventDataService.class);
    ApplicationEventDAO applicationEventDaoMock = mock(ApplicationEventDAO.class);
    ApplicationEventConverterImpl applicationEventConverterMock = mock(ApplicationEventConverterImpl.class);

    EventDataService eventDataServiceMock = mock(EventDataService.class);
    EventDAO eventDAOMock = mock(EventDAO.class);
    EventConverterImpl eventConverterImplMock = mock(EventConverterImpl.class);

    Query query = mock(Query.class);
    Query query1 = mock(Query.class);
    List<ApplicationEvent> applicationEventList = new ArrayList<ApplicationEvent>();
    List<Event> eventList = new ArrayList<Event>();
    List<ApplicationEventDTO> applicationEventListDTO = new ArrayList<ApplicationEventDTO>();

    @Before
    public void setUp() throws Exception {
        applicationEventList.add(getApplicationEvent1());
        applicationEventListDTO.add(getApplicationEventDTO1());
        when(applicationEventDaoMock.findByExample((ApplicationEvent) any())).thenReturn(applicationEventList);
        applicationEventDataServiceMock.setDao(applicationEventDaoMock);
        applicationEventDataServiceMock.setConverter(applicationEventConverterMock);
        when(applicationEventDataServiceMock.findAllCustomerEventsByCustomerId(anyLong()))
                .thenReturn(applicationEventListDTO);

        eventList.add(getEvent1());
        when(eventDAOMock.findByQueryUniqueResult(anyString(), anyObject())).thenReturn(getEvent1());
        when(eventDAOMock.findByQuery(anyString(), anyObject())).thenReturn(eventList);
        eventDataServiceMock.setDao(eventDAOMock);
        eventDataServiceMock.setConverter(eventConverterImplMock);
        when(eventDataServiceMock.findById(anyLong())).thenReturn(getEventDTO1());

        applicationEventService.setApplicationEventDataService(applicationEventDataServiceMock);
        applicationEventService.setEventDataService(eventDataServiceMock);
    }

    @Test
    public void shouldGetApplicationEventsForCustomer() {
        List<ApplicationEventDTO> events = applicationEventService
                .findApplicationEventsForCustomer(APPLICATIONEVENT_CUSTOMER_ID);
        assertEquals(events.get(0).getEventId(), APPLICATIONEVENT_EVENT_ID);
    }

    @Test
    public void shouldCreate() {
        ApplicationEventServiceImpl service = mock(ApplicationEventServiceImpl.class);
        ApplicationEventDataService mockApplicationEventDataService = mock(ApplicationEventDataService.class);
        EventDataService mockEventDataService = mock(EventDataService.class);
        service.applicationEventDataService = mockApplicationEventDataService;
        service.eventDataService = mockEventDataService;
        
        doCallRealMethod().when(service).create((Long)any(), any(EventName.class), anyString());
        when(mockEventDataService.findByEventName(anyString())).thenReturn(getEventDTO1());        
        when(mockApplicationEventDataService.createOrUpdate((ApplicationEventDTO)any())).thenReturn(getApplicationEventDTO1());
        service.create(APPLICATIONEVENT_CUSTOMER_ID, EventName.AD_HOC_LOAD_FAILED, "");
        verify(mockEventDataService, atLeastOnce()).findByEventName(anyString());
        verify(mockApplicationEventDataService, atLeastOnce()).createOrUpdate((ApplicationEventDTO)any());
    }
}
