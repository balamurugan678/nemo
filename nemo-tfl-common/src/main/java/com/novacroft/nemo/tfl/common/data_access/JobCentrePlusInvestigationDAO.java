package com.novacroft.nemo.tfl.common.data_access;

import org.springframework.stereotype.Repository;

import com.novacroft.nemo.common.data_access.BaseDAOImpl;
import com.novacroft.nemo.tfl.common.domain.JobCentrePlusInvestigation;

/**
 * TfL job centre plus investigation data access class implementation.
 */
@Repository("jobCentrePlusInvestigationDAO")
public class JobCentrePlusInvestigationDAO extends BaseDAOImpl<JobCentrePlusInvestigation> {

}
