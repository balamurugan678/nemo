package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.utils.CurrencyUtil.convertPoundsAndPenceAsStringToPenceAsLong;
import static com.novacroft.nemo.common.utils.StringUtil.isNotEmpty;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AGENT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AMOUNT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.APPROVAL_REASONS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CARD_NUMBER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.DATE_CREATED;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.REFUND_IDENTIFIER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.STATUS;

import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowSearchService;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service(value = "workflowSearchService")
public class WorkflowSearchServiceImpl implements WorkflowSearchService {
    protected static final String PERCENT = "%";
    protected static final String HYPHEN = "-";

    @Autowired(required = false)
    protected TaskService taskService;
    @Autowired(required = false)
    protected HistoryService taskHistoryService;

    @Autowired
    protected WorkflowDataService workflowDataService;

    @Override
    public List<WorkflowItemDTO> findBySearchCriteria(RefundSearchCmd search) {
        search.toUpperCase();

        TaskQuery taskQuery = taskService.createTaskQuery();

        taskQuery = processRefundIdentifierVariable(taskQuery, search.getCaseNumber(), search.getExact());
        taskQuery = processCustomerVariable(taskQuery, search);
        taskQuery = processCardNumberVariable(taskQuery, search.getCardNumber(), search.getExact());
        taskQuery = processAgentVariable(taskQuery, search);
        taskQuery = processStatusVariable(taskQuery, search.getStatus(), search.getExact());

        List<Task> taskList = taskQuery.list();

        return workflowDataService.getWorkflowList(taskList);
    }

    @Override
    public List<WorkflowItemDTO> findBySearchCriteria(ApprovalListCmdImpl search) {
        search.toUpperCase();

        TaskQuery taskQuery = taskService.createTaskQuery();

        taskQuery = processRefundIdentifierVariable(taskQuery, search.getCaseNumber(), search.getExact());
        taskQuery = processAgentVariable(taskQuery, search);
        taskQuery = processAmountVariable(taskQuery, search);
        taskQuery = processStatusVariable(taskQuery, search.getStatus(), search.getExact());
        taskQuery = processPaymentMethodVariable(taskQuery, search.getPaymentMethod(), search.getExact());

        if (isNotEmpty(search.getDateCreated())) {
            taskQuery.processVariableValueEquals(DATE_CREATED, search.getDateCreated());
        }

        taskQuery = processApprovalReasonsVariable(taskQuery, search);

        if (isNotEmpty(search.getFormLocation())) {
            taskQuery.taskCandidateGroup(AgentGroup.find(search.getFormLocation()).code());
        }

        List<Task> taskList = taskQuery.list();

        return workflowDataService.getWorkflowList(taskList);
    }

    private TaskQuery processRefundIdentifierVariable(TaskQuery taskQuery, String caseNumber, String exact) {
        if (isNotEmpty(caseNumber)) {
            if (isNotEmpty(exact)) {
                return taskQuery.processVariableValueEquals(REFUND_IDENTIFIER, caseNumber);
            } else {
                return taskQuery.processVariableValueLike(REFUND_IDENTIFIER, PERCENT + caseNumber + PERCENT);
            }
        } else {
            return taskQuery;
        }
    }

    private TaskQuery processAgentVariable(TaskQuery taskQuery, ApprovalListCmdImpl search) {
        if (isNotEmpty(search.getAgent())) {
            if (isNotEmpty(search.getExact())) {
                return taskQuery.processVariableValueEquals(AGENT, search.getAgent());
            } else {
                return taskQuery.processVariableValueLike(AGENT, PERCENT + search.getAgent() + PERCENT);
            }
        } else {
            return taskQuery;
        }
    }

    private TaskQuery processCustomerVariable(TaskQuery taskQuery, RefundSearchCmd search) {
        return processNameVariables(CUSTOMER, taskQuery, search.getCustomerFirstName(), search.getCustomerLastName());
    }

    private TaskQuery processCardNumberVariable(TaskQuery taskQuery, String cardNumber, String exact) {
        if (isNotEmpty(cardNumber)) {
            if (isNotEmpty(exact)) {
                return taskQuery.processVariableValueEquals(CARD_NUMBER, cardNumber);
            } else {
                return taskQuery.processVariableValueLike(CARD_NUMBER, PERCENT + cardNumber + PERCENT);
            }
        } else {
            return taskQuery;
        }
    }

    private TaskQuery processAgentVariable(TaskQuery taskQuery, RefundSearchCmd search) {
        return processNameVariables(AGENT, taskQuery, search.getAgentFirstName(), search.getAgentLastName());
    }

    private TaskQuery processAmountVariable(TaskQuery taskQuery, ApprovalListCmdImpl search) {
        if (isNotEmpty(search.getAmount())) {
            Long amount = convertPoundsAndPenceAsStringToPenceAsLong(search.getAmount());
            String amountString = amount.toString();
            if (isNotEmpty(search.getExact())) {
                taskQuery.processVariableValueEquals(AMOUNT, amountString);
            } else {
                amountString = amountString.substring(0, amountString.length() - 2);
                taskQuery.processVariableValueLike(AMOUNT, amountString + PERCENT);
            }
        }
        return taskQuery;
    }

    private TaskQuery processStatusVariable(TaskQuery taskQuery, String status, String exact) {
        if (isNotEmpty(status)) {
            if (isNotEmpty(exact)) {
                return taskQuery.processVariableValueEquals(STATUS, status);
            } else {
                return taskQuery.processVariableValueLike(STATUS, PERCENT + status + PERCENT);
            }
        } else {
            return taskQuery;
        }
    }

    private TaskQuery processPaymentMethodVariable(TaskQuery taskQuery, String paymentMethod, String exact) {
        if (isNotEmpty(paymentMethod)) {
            String searchablePaymentMethod = StringUtils.remove(paymentMethod, StringUtil.SPACE);
            searchablePaymentMethod = StringUtils.remove(searchablePaymentMethod, HYPHEN);
            if (isNotEmpty(exact)) {
                return taskQuery.processVariableValueEquals(PAYMENT_METHOD, searchablePaymentMethod);
            } else {
                return taskQuery.processVariableValueLike(PAYMENT_METHOD, PERCENT + searchablePaymentMethod + PERCENT);
            }
        } else {
            return taskQuery;
        }
    }

    private TaskQuery processApprovalReasonsVariable(TaskQuery taskQuery, ApprovalListCmdImpl search) {
        if (isNotEmpty(search.getReason())) {
            if (isNotEmpty(search.getExact())) {
                return taskQuery.processVariableValueEquals(APPROVAL_REASONS, search.getReason());
            } else {
                return taskQuery.processVariableValueLike(APPROVAL_REASONS, PERCENT + search.getReason() + PERCENT);
            }
        } else {
            return taskQuery;
        }
    }

    @Override
    public List<WorkflowItemDTO> findAllByGroup(String group) {
        return workflowDataService
                .getWorkflowList(taskService.createTaskQuery().taskCandidateGroup(AgentGroup.find(group).code()).list());
    }

    @Override
    public List<WorkflowItemDTO> findAllByGroup(AgentGroup group) {
        return workflowDataService.getWorkflowList(taskService.createTaskQuery().taskCandidateGroup(group.code()).list());
    }

    @Override
    public List<WorkflowItemDTO> findAllByUser(String user) {
        return workflowDataService.getWorkflowList(taskService.createTaskQuery().taskCandidateOrAssigned(user).list());
    }

    @Override
    public List<WorkflowItemDTO> findAll() {
        return workflowDataService.getWorkflowList(taskService.createTaskQuery().list());
    }

    @Override
    public List<WorkflowItemDTO> findHistoricRefundsByAgent(String firstName, String lastName) {
        HistoricProcessInstanceQuery processQuery = taskHistoryService.createHistoricProcessInstanceQuery().includeProcessVariables();

        if (isNotEmpty(firstName) && isNotEmpty(lastName)) {
            processQuery.variableValueLike(AGENT,
                    PERCENT + firstName.toUpperCase() + StringUtil.SPACE + lastName.toUpperCase() + PERCENT);
        } else if (isNotEmpty(lastName)) {
            processQuery.variableValueLike(AGENT, PERCENT + lastName.toUpperCase() + PERCENT);
        } else if (isNotEmpty(firstName)){
            processQuery.variableValueLike(AGENT, PERCENT + firstName.toUpperCase() + PERCENT);
        }

        List<HistoricProcessInstance> processList = processQuery.list();

        return workflowDataService.getHistoricWorkflowList(processList);
    }

    @Override
    public List<WorkflowItemDTO> findHistoricRefundsByCardNumber(String cardNumber, String exact) {
        HistoricProcessInstanceQuery processQuery = taskHistoryService.createHistoricProcessInstanceQuery().includeProcessVariables();

        if (isNotEmpty(exact)) {
            processQuery.variableValueEquals(CARD_NUMBER, cardNumber);
        } else {
            processQuery.variableValueLike(CARD_NUMBER, PERCENT + cardNumber + PERCENT);
        }

        List<HistoricProcessInstance> processList = processQuery.list();

        return workflowDataService.getHistoricWorkflowList(processList);
    }

    protected TaskQuery processNameVariables(String field, TaskQuery taskQuery, String firstName, String lastName) {
        if (isNotEmpty(firstName) && isNotEmpty(lastName)) {
            return taskQuery.processVariableValueLike(field,
                    PERCENT + firstName.toUpperCase() + StringUtil.SPACE + lastName.toUpperCase() + PERCENT);
        } else if (isNotEmpty(lastName)) {
            return taskQuery.processVariableValueLike(field, PERCENT + lastName.toUpperCase() + PERCENT);
        } else if (isNotEmpty(firstName)) {
            return taskQuery.processVariableValueLike(field, PERCENT + firstName.toUpperCase() + PERCENT);
        } else {
            return taskQuery;
        }
    }

    // For junit testing ApprovalListController
    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    // For junit testing ApprovalListController
    public void setWorkflowDataService(WorkflowDataService workflowDataService) {
        this.workflowDataService = workflowDataService;
    }
}
