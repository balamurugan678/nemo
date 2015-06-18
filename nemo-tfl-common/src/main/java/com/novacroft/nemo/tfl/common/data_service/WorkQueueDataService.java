package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.common.data_service.BaseDataService;
import com.novacroft.nemo.tfl.common.domain.WorkQueue;
import com.novacroft.nemo.tfl.common.transfer.WorkQueueDTO;

import java.util.List;

/**
 * Workqueue   transfer implementation.
 * Automatically created.
 */

public interface WorkQueueDataService extends BaseDataService<WorkQueue, WorkQueueDTO> {
    List<WorkQueueDTO> findAllBy(String test);

    WorkQueueDTO findByTest(String test);
}
