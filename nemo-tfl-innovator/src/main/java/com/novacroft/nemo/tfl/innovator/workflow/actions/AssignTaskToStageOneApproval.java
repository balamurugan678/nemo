package com.novacroft.nemo.tfl.innovator.workflow.actions;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.novacroft.nemo.tfl.common.constant.AgentGroup;

public class AssignTaskToStageOneApproval implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(AssignTaskToStageOneApproval.class);

    @Autowired
    TaskService taskService;
    @Autowired
    RepositoryService repositoryService;
    @Autowired
    HistoryService historyService;
    @Autowired
    ManagementService managementService;
    @Autowired
    RuntimeService runtimeService;
    @Autowired
    IdentityService identityService;

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.debug("In the AssignTaskToStageOneApproval Class");
        logger.debug("execution id " + execution.getId());

        logger.debug("Assign the task to the 1st stage approver group");
        try {
            taskService.addCandidateGroup(execution.getId(), AgentGroup.FIRST_STAGE_APPROVER.code());
        } catch (Exception e) {
            logger.debug("This item has already been claimed!");
            //probably want to avoid this error catch somehow.
        }
    }
}
