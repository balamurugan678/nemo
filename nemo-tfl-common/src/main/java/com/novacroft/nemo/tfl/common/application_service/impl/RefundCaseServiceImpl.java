package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.RefundCaseService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.RefundCaseCmd;
import com.novacroft.nemo.tfl.common.converter.impl.RefundCaseConverter;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service("refundCaseService")
public class RefundCaseServiceImpl implements RefundCaseService {
    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected RefundCaseConverter refundCaseConverter;

    @Override
    public RefundCaseCmd getRefundCase(String caseNumber) {
        WorkflowItemDTO workflowItem = workflowService.getWorkflowItem(caseNumber);
        return workflowItem.getCaseNumber() == null ? getRefundCaseFromOrder(caseNumber) : refundCaseConverter.convertFromWorkflowItem(workflowItem);
    }

    protected RefundCaseCmd getRefundCaseFromOrder(String caseNumber) {
        List<OrderDTO> orders = orderDataService.getOrdersByCaseNumber(caseNumber);
        return refundCaseConverter.convertFromOrder(orders.get(0));
    }

}
