package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;

public interface WorkflowEditService {

    WorkflowCmd saveChanges(WorkflowCmd workflowCmd, WorkflowEditCmd cmd);

}
