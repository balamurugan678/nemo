package com.novacroft.nemo.common.data_access;

import com.novacroft.nemo.common.domain.ServiceCallLog;
import org.springframework.stereotype.Repository;

/**
 * Service call log data access class implementation.
 */
@Repository("serviceCallLogDAO")
public class ServiceCallLogDAO extends BaseDAOImpl<ServiceCallLog> {
}
