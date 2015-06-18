package com.novacroft.nemo.tfl.common.converter.impl;

import com.novacroft.nemo.common.converter.impl.BaseDtoEntityConverterImpl;
import com.novacroft.nemo.tfl.common.domain.WorkQueue;
import com.novacroft.nemo.tfl.common.transfer.WorkQueueDTO;
import org.springframework.stereotype.Component;

/**
 * WorkQueue  entity/DTO converter.
 * Automatically created.
 */

@Component(value = "WorkQueueConverter")
public class WorkQueueConverterImpl extends BaseDtoEntityConverterImpl<WorkQueue, WorkQueueDTO> {
    @Override
    public WorkQueueDTO getNewDto() {
        return new WorkQueueDTO();
    }
}
