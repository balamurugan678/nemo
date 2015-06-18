package com.novacroft.nemo.tfl.common.command;

import java.util.List;

import org.activiti.engine.task.Task;

import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public interface Approval {

    void setStage(String string);

    String getStage();

    List<Task> getTasks();

    void setTasks(List<Task> tasks);

    void setTaskId(String taskId);

    String getTaskId();

    String getApproval();

    void setApproval(String approval);

    void setFormLocation(String approval);

    String getFormLocation();

    List<WorkflowItemDTO> getApprovals();

    void setApprovals(List<WorkflowItemDTO> approvals);

    String getCaseNumber();
}
