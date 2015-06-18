package com.novacroft.nemo.common.data_access;

import com.novacroft.nemo.common.domain.Event;
import org.springframework.stereotype.Repository;

/**
 * TfL Event data access class implementation.
 */

@Repository("eventDAO")
public class EventDAO extends BaseDAOImpl<Event> {

}
