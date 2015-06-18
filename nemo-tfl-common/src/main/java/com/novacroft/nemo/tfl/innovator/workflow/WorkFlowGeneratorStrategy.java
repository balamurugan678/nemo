package com.novacroft.nemo.tfl.innovator.workflow;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

import java.util.Map;

public interface WorkFlowGeneratorStrategy {
    void setUpWorkFlowVariables(CartCmdImpl cartCmdImpl, Map<String, Object> workflowProcessVariables);

    void setUpCustomerDetailsForWorkFlow(CartCmdImpl cartCmdImpl, Map<String, Object> workflowProcessVariables);
}