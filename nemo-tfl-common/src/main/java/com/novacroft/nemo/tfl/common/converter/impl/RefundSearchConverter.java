package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Component(value = "refundSearchConverter")
public class RefundSearchConverter {
    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected CardDataService cardDataService;

    public RefundSearchResultDTO convertFromWorkflowItem(WorkflowItemDTO workflowItem) {
        RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
        CustomerDTO customer = refundDetails.getCustomer();
        return new RefundSearchResultDTO(workflowItem.getCaseNumber(), workflowItem.getCreatedTime(), workflowItem.getAgent(),
                        (customer != null) ? formatName(customer) : StringUtil.EMPTY_STRING, (refundDetails.getCardNumber() != null) ? refundDetails.getCardNumber() : StringUtil.EMPTY_STRING,
                        workflowItem.getPaymentMethod(), workflowItem.getStatus());
    }

    public RefundSearchResultDTO convertFromOrder(OrderDTO order) {
        WorkflowItemDTO workflowItem = workflowService.getHistoricWorkflowItem(order.getApprovalId().toString());
        RefundSearchResultDTO searchResult = convertFromWorkflowItem(workflowItem);
        searchResult.setStatus(order.getStatus());
        return searchResult;
    }
}
