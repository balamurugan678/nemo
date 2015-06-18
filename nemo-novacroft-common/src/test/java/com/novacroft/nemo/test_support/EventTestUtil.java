package com.novacroft.nemo.test_support;

import com.novacroft.nemo.common.domain.ApplicationEvent;
import com.novacroft.nemo.common.domain.Event;
import com.novacroft.nemo.common.transfer.ApplicationEventDTO;
import com.novacroft.nemo.common.transfer.EventDTO;


/**
 * 
 * Test utility class for Event and Application Event domain objects;
 * 
 */
public final class EventTestUtil {
	
	public static final Long EVENT_ID_1 = 1L; 
	public static final String EVENT_NAME_1 = "EventName";
	public static final String EVENT_DESCRIPTION_1 = "Event Description";
	
	public static final Long APPLICATIONEVENT_ID = 1L;
	public static final Long APPLICATIONEVENT_EVENT_ID = 1L;
	public static final Long APPLICATIONEVENT_CUSTOMER_ID = 1L;
	public static final Long APPLICATIONEVENT_WEBACCOUNT_ID = 1L;
	public static final String APPLICATIONEVENT_NAME = "Event Name";
	public static final String APPLICATIONEVENT_DESCRIPTION = "Event Name";
	
	private EventTestUtil(){}
	
	public static final Event getEvent1(){
		return createEvent(EVENT_ID_1, EVENT_NAME_1, EVENT_DESCRIPTION_1);
	}
	
	public static final EventDTO getEventDTO1(){
        return createEventDTO(EVENT_ID_1, EVENT_NAME_1, EVENT_DESCRIPTION_1);
    }
	
	public static final ApplicationEvent getApplicationEvent1(){
		return createApplicationEvent(APPLICATIONEVENT_ID, APPLICATIONEVENT_EVENT_ID, APPLICATIONEVENT_CUSTOMER_ID, APPLICATIONEVENT_WEBACCOUNT_ID);
	}
	
	public static final ApplicationEventDTO getApplicationEventDTO1(){
        return createApplicationEventDTO(APPLICATIONEVENT_ID, APPLICATIONEVENT_EVENT_ID, APPLICATIONEVENT_CUSTOMER_ID, APPLICATIONEVENT_WEBACCOUNT_ID, APPLICATIONEVENT_NAME, APPLICATIONEVENT_DESCRIPTION );
    }
	
	
	public static final Event createEvent(Long id, String name, String description){
		Event event = new Event();
		event.setId(id);
		event.setName(name);
		event.setDescription(description);
		return event;
	}
	
	public static final EventDTO createEventDTO(Long id, String name, String description){
	    EventDTO event = new EventDTO();
        event.setId(id);
        event.setName(name);
        event.setDescription(description);
        return event;
    }
	
	
	
	public static final ApplicationEvent createApplicationEvent(Long id, Long eventId, Long customerId, Long webaccountId){
		ApplicationEvent applicationEvent = new ApplicationEvent();
		applicationEvent.setId(id);
		applicationEvent.setEventId(eventId);
		applicationEvent.setCustomerId(customerId);
		applicationEvent.setWebaccountId(webaccountId);
		return applicationEvent;
	}
	
	public static final ApplicationEventDTO createApplicationEventDTO(Long id, Long eventId, Long customerId, Long webaccountId, String name, String description){
	    ApplicationEventDTO applicationEventDTO = new ApplicationEventDTO();
	    applicationEventDTO.setId(id);
	    applicationEventDTO.setEventId(eventId);
        applicationEventDTO.setCustomerId(customerId);
        applicationEventDTO.setWebaccountId(webaccountId);
        applicationEventDTO.setEventName(name);
        applicationEventDTO.setEventDescription(description);
        
	    return applicationEventDTO;
	}
	
	

}
