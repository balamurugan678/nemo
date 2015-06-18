package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.JobLog;
import com.novacroft.nemo.tfl.common.transfer.JobLogDTO;
import org.springframework.stereotype.Component;

/**
 * Transform between job log entity and DTO.
 */
@Component(value = "jobLogConverter")
public class JobLogConverterImpl extends BaseDtoEntityConverterImpl<JobLog, JobLogDTO> {
    @Override
    protected JobLogDTO getNewDto() {
        return new JobLogDTO();
    }
}
