package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.task.Task;
import org.joda.time.DateTime;

import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public interface WorkflowDataService {
    WorkflowItemDTO getWorkflowItem(String caseNumber);

    WorkflowItemDTO getHistoricWorkflowItem(String caseNumber);

    List<WorkflowItemDTO> getWorkflowList(List<Task> tasks);

    List<WorkflowItemDTO> getHistoricWorkflowList(List<HistoricProcessInstance> processList);

    void updateAgentAndAddClaimTime(String caseNumber, UserEntity agent, DateTime time);

    Boolean isApprovalIdBeingUsed(Long approvalId);
}
