package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.novacroft.nemo.tfl.common.command.ApprovalListCmd;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class ApprovalListCmdImpl implements ApprovalListCmd {

    protected String caseNumber;
    protected String dateCreated;
    protected String paymentMethod;
    protected String amount;
    protected String timeOnQueue;
    protected String agent;
    protected String status;
    protected String reason;
    protected String exact;

    protected List<WorkflowItemDTO> approvals;

    protected String formLocation;
    protected String targetAction;

	public ApprovalListCmdImpl() {
		this.caseNumber = StringUtils.EMPTY;
		this.dateCreated = StringUtils.EMPTY;
		this.paymentMethod = StringUtils.EMPTY;
		this.amount = StringUtils.EMPTY;
		this.timeOnQueue = StringUtils.EMPTY;
		this.agent = StringUtils.EMPTY;
		this.status = StringUtils.EMPTY;
		this.reason = StringUtils.EMPTY;
        this.targetAction = StringUtils.EMPTY;
	}
    
    @Override
    public void setFormLocation(String formLocation) {
        this.formLocation = formLocation;
    }

    @Override
    public String getFormLocation() {
        return formLocation;
    }

    @Override
    public String getCaseNumber() {
        return caseNumber;
    }

    @Override
    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    @Override
    public String getDateCreated() {
        return dateCreated;
    }

    @Override
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    @Override
    public String getAgent() {
        return agent;
    }

    @Override
    public void setAgent(String agent) {
        this.agent = agent;
    }

    @Override
    public String getStatus() {
        return status;
    }

    @Override
    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String getTimeOnQueue() {
        return timeOnQueue;
    }

    public void setTimeOnQueue(String timeOnQueue) {
        this.timeOnQueue = timeOnQueue;
    }

    @Override
    public String getAmount() {
        return amount;
    }

    @Override
    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String getReason() {
        return reason;
    }

    @Override
    public void setReason(String reason) {
        this.reason = reason;
    }

    @Override
    public String getExact() {
        return exact;
    }

    @Override
    public void setExact(String exact) {
        this.exact = exact;
    }

    @Override
    public List<WorkflowItemDTO> getApprovals() {
        return approvals;
    }

    @Override
    public void setApprovals(List<WorkflowItemDTO> approvals) {
        this.approvals = approvals;
    }

    @Override
    public String getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getTargetAction() {
        return targetAction;
    }

    public void setTargetAction(String targetAction) {
        this.targetAction = targetAction;
    }

    public void toUpperCase() {
        this.caseNumber = caseNumber.toUpperCase();
        this.paymentMethod = paymentMethod.toUpperCase();
        this.agent = agent.toUpperCase();
        this.status = status.toUpperCase();
        this.reason = reason.toUpperCase();
        this.paymentMethod = paymentMethod.toUpperCase();
    }
}
