package com.novacroft.nemo.tfl.innovator.workflow.actions;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Deprecated
public class ActivitiUtilities implements JavaDelegate {
    private static final Logger logger = LoggerFactory.getLogger(ActivitiUtilities.class);

    @Override
    public void execute(DelegateExecution execution) throws Exception {
        logger.debug("In the ActivitiUtilities Class");
        logger.debug("execution id " + execution.getId());
    }
}
