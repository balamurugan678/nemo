package com.novacroft.nemo.tfl.common.command;

import java.util.List;

import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public interface ApprovalListCmd {

    void setFormLocation(String formLocation);

    String getFormLocation();

    List<WorkflowItemDTO> getApprovals();

    void setApprovals(List<WorkflowItemDTO> approvals);

    String getCaseNumber();

    void setCaseNumber(String caseNumber);

    String getDateCreated();

    void setDateCreated(String dateCreated);

    String getPaymentMethod();

    void setPaymentMethod(String paymentMethod);

    String getAmount();

    void setAmount(String amount);

    String getTimeOnQueue();

    String getAgent();

    void setAgent(String agent);

    String getStatus();

    void setStatus(String status);

    String getReason();

    void setReason(String reason);

    String getExact();

    void setExact(String exact);

    String getTargetAction();

    void setTargetAction(String targetAction);
}
