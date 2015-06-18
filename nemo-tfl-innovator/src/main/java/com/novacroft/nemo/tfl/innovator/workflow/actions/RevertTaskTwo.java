package com.novacroft.nemo.tfl.innovator.workflow.actions;

import org.activiti.engine.*;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class RevertTaskTwo implements TaskListener {
    private static final Logger logger = LoggerFactory.getLogger(RevertTaskTwo.class);

    private static final long serialVersionUID = 2628099613980932269L;
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

    private static final String FIRST_STAGE_APPROVER_GROUP = "FirstStageApprover";

    @Override
    public void notify(DelegateTask delegateTask) {

        logger.debug("Reverting back the task.......");

        String taskId = delegateTask.getId();

        try {
            taskService.claim(taskId, FIRST_STAGE_APPROVER_GROUP);
        } catch (Exception e) {
            logger.debug("This item has already been claimed!");
            //probably want to avoid this error catch somehow.
        }
    }
}
