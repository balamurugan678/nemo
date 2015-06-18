package com.novacroft.nemo.tfl.common.data_service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.data_service.BaseDataServiceImpl;
import com.novacroft.nemo.tfl.common.converter.impl.JobCentrePlusInvestigationConverterImpl;
import com.novacroft.nemo.tfl.common.data_access.JobCentrePlusInvestigationDAO;
import com.novacroft.nemo.tfl.common.data_service.JobCentrePlusInvestigationDataService;
import com.novacroft.nemo.tfl.common.domain.JobCentrePlusInvestigation;
import com.novacroft.nemo.tfl.common.transfer.JobCentrePlusInvestigationDTO;

/**
 * Job centre plus investigation data service implementation
 */
@Service(value = "jobCentrePlusInvestigationDataService")
@Transactional(readOnly = true)
public class JobCentrePlusInvestigationDataServiceImpl extends BaseDataServiceImpl<JobCentrePlusInvestigation, JobCentrePlusInvestigationDTO>
                implements JobCentrePlusInvestigationDataService {
    static final Logger logger = LoggerFactory.getLogger(JobCentrePlusInvestigationDataServiceImpl.class);

    public JobCentrePlusInvestigationDataServiceImpl() {
        super();
    }

    @Autowired
    public void setDao(JobCentrePlusInvestigationDAO dao) {
        this.dao = dao;
    }

    @Autowired
    public void setConverter(JobCentrePlusInvestigationConverterImpl converter) {
        this.converter = converter;
    }

    @Override
    public JobCentrePlusInvestigation getNewEntity() {
        return new JobCentrePlusInvestigation();
    }

}
