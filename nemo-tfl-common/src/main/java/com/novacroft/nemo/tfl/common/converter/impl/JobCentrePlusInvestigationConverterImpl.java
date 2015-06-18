package com.novacroft.nemo.tfl.common.converter.impl;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.JobCentrePlusInvestigation;
import com.novacroft.nemo.tfl.common.transfer.JobCentrePlusInvestigationDTO;

/**
 * Transform between job centre plus investigation entity and DTO.
 */
@Component(value = "jobCentrePlusInvestigationConverter")
public class JobCentrePlusInvestigationConverterImpl extends BaseDtoEntityConverterImpl<JobCentrePlusInvestigation, JobCentrePlusInvestigationDTO> {
    @Override
    protected JobCentrePlusInvestigationDTO getNewDto() {
        return new JobCentrePlusInvestigationDTO();
    }
}
