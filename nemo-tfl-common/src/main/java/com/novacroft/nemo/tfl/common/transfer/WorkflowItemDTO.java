package com.novacroft.nemo.tfl.common.transfer;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.tfl.common.util.WorkflowItemUtil.calculateTimeOnQueueAsLong;
import static com.novacroft.nemo.tfl.common.util.WorkflowItemUtil.getApprovalReasonsAsString;
import static com.novacroft.nemo.tfl.common.util.WorkflowItemUtil.getCaseNotesIncludingHistory;
import static com.novacroft.nemo.tfl.common.util.WorkflowItemUtil.getTimeOnQueueAsString;
import static com.novacroft.nemo.tfl.common.util.WorkflowItemUtil.setApprovalReasonsUpperCase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkflowItemDTO implements Serializable{

    private static final long serialVersionUID = 5051493852585119362L;
    protected String taskId;
    protected String executionId;
    protected String processInstanceId;
    protected DateTime taskCreatedTime;
    protected RefundDetailDTO refundDetails;
    protected String caseNumber;
    protected DateTime createdTime;
    protected String agent;
    protected String status;
    protected List<String> approvalReasons;
    protected String paymentMethod;
    protected List<CaseHistoryNote> caseNotes;
    protected DateTime claimedTime;
    protected Long approvalId;

    public WorkflowItemDTO(){
        approvalReasons = new ArrayList<String>();
        caseNotes = new  ArrayList<CaseHistoryNote>();
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getProcessInstanceId() {
        return processInstanceId;
    }

    public void setProcessInstanceId(String processInstanceId) {
        this.processInstanceId = processInstanceId;
    }

    public DateTime getTaskCreatedTime() {
        return taskCreatedTime;
    }

    public void setTaskCreatedTime(DateTime taskCreatedTime) {
        this.taskCreatedTime = taskCreatedTime;
    }

    public void setRefundDetails(RefundDetailDTO refundDetails) {
        this.refundDetails = refundDetails;

    }

    public RefundDetailDTO getRefundDetails() {
        return refundDetails;
    }

    public String getCaseNumber() {
        return caseNumber;
    }

    public void setCaseNumber(String caseNumber) {
        this.caseNumber = caseNumber;
    }

    public String getCreatedTime() {
        return createdTime.toString(SHORT_DATE_PATTERN);
    }

    public DateTime getCreatedTimeAsDateTime() {
        return createdTime;
    }

    public void setCreatedTime(DateTime createdTime) {
        this.createdTime = createdTime;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimeOnQueue() {
        return getTimeOnQueueAsString(this);
    }

    public Long getTimeOnQueueAsLong() {
        return calculateTimeOnQueueAsLong(this);
    }

    public Long getAmount() {
        return refundDetails.getRefundEntity().getRefundAmount();
    }

    public List<String> getApprovalReasonsList() {
        return new ArrayList<String>(approvalReasons);
    }
    public String getApprovalReasons() {
        return getApprovalReasonsAsString(approvalReasons);
    }

    public void setApprovalReasons(List<String> approvalReasons) {
        this.approvalReasons = setApprovalReasonsUpperCase(approvalReasons);
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public List<CaseHistoryNote> getCaseNotes() {
        return getCaseNotesIncludingHistory(caseNotes, processInstanceId);
    }

    public void setCaseNotes(List<CaseHistoryNote> caseNotes) {
        this.caseNotes = caseNotes;
    }

    public DateTime getClaimedTime() {
        return claimedTime;
    }

    public void setClaimedTime(DateTime claimedTime) {
        this.claimedTime = claimedTime;
    }

	public Long getApprovalId() {
		return approvalId;
	}

	public void setApprovalId(Long approvalId) {
		this.approvalId = approvalId;
	}
    
    
}
