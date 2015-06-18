package com.novacroft.nemo.tfl.common.command.impl;

import java.util.Date;
import java.util.List;

import org.activiti.engine.task.Task;

import com.novacroft.nemo.tfl.common.command.Approval;
import com.novacroft.nemo.tfl.common.command.Release;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class ApprovalImpl implements Approval, Release {

    protected List<Task> tasks;
    protected List<Task> tasks2;
    protected List<Task> tasks3;
    protected List<Task> tasks4;
    protected List<Task> tasks5;
    protected List<Task> tasks6;
    protected String stage;
    protected String approval;
    protected List<WorkflowItemDTO> approvals;
    protected String taskId = "";
    protected String formLocation = "";
    protected String caseNumber = "";

    protected Date dateCreated;
    protected String reason;
    protected Integer amount;
    protected String timeOnQueue;
    protected String agent;
    protected String paymentMethod;
    protected String status;
    protected String exact;

    @Override
    public String getTaskId() {
        return taskId;
    }

    @Override
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    @Override
    public List<Task> getTasks() {
        return tasks;
    }

    @Override
    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    @Override
    public void setStage(String stage) {
        this.stage = stage;

    }

    @Override
    public String getStage() {

        return this.stage;
    }

    @Override
    public String getApproval() {
        return this.approval;
    }

    @Override
    public void setApproval(String approval) {
        this.approval = approval;
    }

    @Override
    public void setFormLocation(String location) {
        this.formLocation = location;

    }

    @Override
    public String getFormLocation() {
        return this.formLocation;
    }

    @Override
    public List<Task> getTasks2() {
        return tasks2;
    }

    @Override
    public void setTasks2(List<Task> tasks2) {
        this.tasks2 = tasks2;
    }

    @Override
    public List<Task> getTasks3() {
        return tasks3;
    }

    @Override
    public void setTasks3(List<Task> tasks3) {
        this.tasks3 = tasks3;
    }

    @Override
    public List<Task> getTasks4() {
        return tasks4;
    }

    @Override
    public void setTasks4(List<Task> tasks4) {
        this.tasks4 = tasks4;
    }

    @Override
    public List<Task> getTasks5() {
        return tasks5;
    }

    @Override
    public void setTasks5(List<Task> tasks5) {
        this.tasks5 = tasks5;
    }

    @Override
    public List<Task> getTasks6() {
        return tasks6;
    }

    @Override
    public void setTasks6(List<Task> tasks6) {
        this.tasks6 = tasks6;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getTimeOnQueue() {
        return timeOnQueue;
    }

    public void setTimeOnQueue(String timeOnQueue) {
        this.timeOnQueue = timeOnQueue;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExact() {
        return exact;
    }

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
    public String getCaseNumber() {
        return caseNumber;
    }
}
