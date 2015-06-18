package com.novacroft.nemo.tfl.common.data_service;

import com.novacroft.nemo.tfl.common.constant.ApprovalLevelRequiredEnum;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public interface WorkflowValueApprovalLimitsService {

    ApprovalLevelRequiredEnum checkApprovalThreshold(WorkflowItemDTO workflowItem, RefundDetailDTO refundDetails);

}
