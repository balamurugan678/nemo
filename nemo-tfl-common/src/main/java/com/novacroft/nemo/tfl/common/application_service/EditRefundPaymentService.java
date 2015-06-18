package com.novacroft.nemo.tfl.common.application_service;

import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public interface EditRefundPaymentService {
	 
	PayeePaymentDTO getModifiedRefundPaymentFields(WorkflowItemDTO workflowItem, CartCmdImpl cartCmdImpl);
	
	WorkflowEditCmd updateWorkflowEditCmdWithModifiedEditRefundsDetails(WorkflowItemDTO workflowItem, WorkflowEditCmd workflowEditCmd, PayeePaymentDTO payeePayment);
	
	CartCmdImpl populateEditedValuesInCartCmdImplFromPayeePayment(CartCmdImpl cartCmdImpl, PayeePaymentDTO payeePayment);
	
	void clearEditedPayeePaymentInfo(WorkflowItemDTO workflowItem);
}
