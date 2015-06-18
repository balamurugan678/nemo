package com.novacroft.nemo.tfl.innovator.workflow;

import java.util.Map;

import org.springframework.stereotype.Component;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;

@Component( value ="AnonymousGoodWillWorkFlowGeneratorStrategy")
public class AnonymousGoodWillWorkFlowGeneratorStrategyImpl extends BaseWorkFlowGeneratorStrategyImpl {

	@Override
	public void setUpCustomerDetailsForWorkFlow(CartCmdImpl cartCmdImpl, Map<String, Object> workflowProcessVariables) {

	}

}
