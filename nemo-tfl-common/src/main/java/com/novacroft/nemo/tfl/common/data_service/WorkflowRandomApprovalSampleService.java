package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public interface WorkflowRandomApprovalSampleService {

    List<String> processForRandomSampleFlagging(WorkflowItemDTO workflow);

}
