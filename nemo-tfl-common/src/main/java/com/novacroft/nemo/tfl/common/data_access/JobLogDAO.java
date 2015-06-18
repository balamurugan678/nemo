package com.novacroft.nemo.tfl.common.data_access;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.JobLog;
import org.springframework.stereotype.Repository;

/**
 * Job Log data access class implementation.
 */
@Repository("jobLogDAO")
public class JobLogDAO extends BaseDAOImpl<JobLog> {

}
