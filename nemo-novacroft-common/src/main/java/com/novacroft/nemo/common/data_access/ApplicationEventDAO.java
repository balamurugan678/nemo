package com.novacroft.nemo.common.data_access;

import com.novacroft.nemo.common.domain.ApplicationEvent;
import org.springframework.stereotype.Repository;

/**
 * TfL Application Event data access class implementation.
 */

@Repository("applicationEventDAO")
public class ApplicationEventDAO extends BaseDAOImpl<ApplicationEvent> {

}
