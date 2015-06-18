package com.novacroft.nemo.tfl.innovator.workflow.actions;

import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RevertTask implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(RevertTask.class);

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

    private static final String AGENT_GROUP = "Agent";

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.debug("In the RevertTask Class");
        logger.debug("execution id " + execution.getId());

        logger.debug("Assign the task back to the agent pool");
        ExecutionEntity ee = (ExecutionEntity) execution;

        ee.getSuperExecutionId();

        String taskId = ee.getProcessDefinition().getDeploymentId();

        try {
            taskService.claim(execution.getId(), AGENT_GROUP);
        } catch (Exception e) {
            logger.debug("This item has already been claimed!");
            //probably want to avoid this error catch somehow.
        }
    }
}
