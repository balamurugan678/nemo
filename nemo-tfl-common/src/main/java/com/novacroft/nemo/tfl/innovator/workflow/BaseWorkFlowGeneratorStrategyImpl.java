package com.novacroft.nemo.tfl.innovator.workflow;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AGENT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ADDRESS_ID;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ALTERNATIVE_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ALTERNATIVE_ADDRESS_ID;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ID;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.DATE_CREATED;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.REFUND_IDENTIFIER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.STATUS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.WORKFLOW_ITEM;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;

import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.converter.impl.RefundToWorkflowConverter;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Component(value = "BaseWorkFlowGeneratorStrategy")
public class BaseWorkFlowGeneratorStrategyImpl implements WorkFlowGeneratorStrategy {

	@Autowired
	protected GetCardService getCardService;
    @Autowired
    protected RefundToWorkflowConverter refundToWorkflowConverter;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AddressDataService addressService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected CartAdministrationService cartAdministrationService;
    
	@Override
	public void setUpWorkFlowVariables(CartCmdImpl cartCmdImpl, Map<String, Object> workflowProcessVariables) {
		cartCmdImpl.setApprovalId(cartAdministrationService.addApprovalId(cartCmdImpl.getCartDTO()));
		WorkflowItemDTO workflowItem = refundToWorkflowConverter.convert(cartCmdImpl);
		workflowItem.setApprovalId(cartCmdImpl.getApprovalId());
        workflowProcessVariables.put(WORKFLOW_ITEM, workflowItem);
        workflowProcessVariables.put(REFUND_IDENTIFIER, cartCmdImpl.getApprovalId().toString());
        workflowProcessVariables.put(CARD_NUMBER, cartCmdImpl.getCardNumber());
        workflowProcessVariables.put(AGENT, StringUtils.EMPTY);
        workflowProcessVariables.put(AMOUNT, workflowItem.getRefundDetails().getRefundEntity().getRefundAmount().toString());
        workflowProcessVariables.put(STATUS, "PENDING");
        workflowProcessVariables.put(PAYMENT_METHOD, cartCmdImpl.getPaymentType().toUpperCase());
        workflowProcessVariables.put(DATE_CREATED, new DateTime().toString(SHORT_DATE_PATTERN));
        
	}

	@Override
	public void setUpCustomerDetailsForWorkFlow(CartCmdImpl cartCmdImpl, Map<String, Object> workflowProcessVariables) {
		CustomerDTO customer = cartService.findCustomerForCart(cartCmdImpl.getCartDTO().getId());
        workflowProcessVariables.put(CUSTOMER, formatName(customer).toUpperCase());
        workflowProcessVariables.put(CUSTOMER_ID, customer.getId());
        workflowProcessVariables.put(CUSTOMER_ADDRESS, addressService.findById(customer.getAddressId()));
        workflowProcessVariables.put(CUSTOMER_ADDRESS_ID, customer.getAddressId());
        workflowProcessVariables.put(CUSTOMER_ALTERNATIVE_ADDRESS, cartCmdImpl.getPayeeAddress());
        workflowProcessVariables.put(CUSTOMER_ALTERNATIVE_ADDRESS_ID, cartCmdImpl.getPayeeAddress().getId());
	}

}
