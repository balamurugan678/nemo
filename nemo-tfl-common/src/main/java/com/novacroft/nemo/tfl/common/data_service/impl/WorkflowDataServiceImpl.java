package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AGENT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.APPROVAL_REASONS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ALTERNATIVE_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.DATE_CREATED;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.LOCAL_STATUS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.REFUND_IDENTIFIER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.STATUS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.WORKFLOW_ITEM;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.constant.ContentCodeSuffix;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service(value = "workflowDataService")
public class WorkflowDataServiceImpl implements WorkflowDataService {
    private final DateTimeFormatter formatter = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
    protected static final String SEPARATOR = ".";

    @Autowired(required = false)
    protected TaskService taskService;

    @Autowired(required = false)
    protected HistoryService historyService;
    
    @Autowired(required = false)
    protected RuntimeService runtimeService;
    
    @Autowired
    protected ContentDataService contentDataService;

    @Override
    public WorkflowItemDTO getWorkflowItem(String caseNumber) {
        Task task = taskService.createTaskQuery().processVariableValueEquals(REFUND_IDENTIFIER, caseNumber).singleResult();
 
        WorkflowItemDTO workflow = new WorkflowItemDTO();

        if (task != null) {
            Map<String, Object> variableList = runtimeService.getVariables(task.getExecutionId());
            workflow = setWorkflowVariables(workflow, variableList, task);
            
            taskService.setVariableLocal(task.getId(), LOCAL_STATUS, workflow.getStatus());
        }

        return workflow;

    }

    private WorkflowItemDTO setWorkflowVariables(WorkflowItemDTO workflow, Map<String, Object> variableList, Task task) {
        workflow = (WorkflowItemDTO) variableList.get(WORKFLOW_ITEM);
        variableList = variableValuesToMixCase(variableList);
        workflow.setTaskId(task.getId());
        workflow.setExecutionId(task.getExecutionId());
        workflow.setProcessInstanceId(task.getProcessInstanceId());
        workflow.setTaskCreatedTime(new DateTime(task.getCreateTime()));
        workflow.setCaseNumber((String) variableList.get(REFUND_IDENTIFIER));
        workflow.setAgent((String) variableList.get(AGENT));
        workflow.setStatus((String) variableList.get(STATUS));
        workflow.setPaymentMethod(getPaymentMethod((String) variableList.get(PAYMENT_METHOD)));
        workflow.getRefundDetails().setAddress((AddressDTO) variableList.get(CUSTOMER_ADDRESS));
        workflow.getRefundDetails().setAlternativeAddress((AddressDTO) variableList.get(CUSTOMER_ALTERNATIVE_ADDRESS));

        DateTime parsedDate = getParsedDate(variableList);
        workflow.setCreatedTime(parsedDate == null ? null : parsedDate);

        List<String> reasons = getApprovalReasons(variableList);
        workflow.setApprovalReasons(reasons == null ? null : reasons);

        return workflow;
    }

    protected String getPaymentMethod(String paymentMethod) {
    	StringBuilder paymentType = new StringBuilder();
    	paymentType.append(paymentMethod);
    	paymentType.append(SEPARATOR);
    	paymentType.append(ContentCodeSuffix.TEXT.code());
		return contentDataService.findByLocaleAndCode(paymentType.toString(), LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
	}

	private WorkflowItemDTO setWorkflowVariables(WorkflowItemDTO workflow, Map<String, Object> variableList, String processInstanceId) {
        workflow = (WorkflowItemDTO) variableList.get(WORKFLOW_ITEM);
        variableList = variableValuesToMixCase(variableList);
        workflow.setProcessInstanceId(processInstanceId);
        workflow.setCaseNumber((String) variableList.get(REFUND_IDENTIFIER));
        workflow.setAgent((String) variableList.get(AGENT));
        workflow.setStatus((String) variableList.get(STATUS));
        workflow.setPaymentMethod(getPaymentMethod((String) variableList.get(PAYMENT_METHOD)));
        workflow.getRefundDetails().setAddress((AddressDTO) variableList.get(CUSTOMER_ADDRESS));
        workflow.getRefundDetails().setAlternativeAddress((AddressDTO) variableList.get(CUSTOMER_ALTERNATIVE_ADDRESS));

        DateTime parsedDate = getParsedDate(variableList);
        workflow.setCreatedTime(parsedDate == null ? null : parsedDate);

        List<String> reasons = getApprovalReasons(variableList);
        workflow.setApprovalReasons(reasons == null ? null : reasons);

        return workflow;
    }

    @Override
    public WorkflowItemDTO getHistoricWorkflowItem(String caseNumber) {
        List<HistoricProcessInstance> historicList = historyService.createHistoricProcessInstanceQuery().includeProcessVariables()
                        .variableValueEquals(REFUND_IDENTIFIER, caseNumber).orderByProcessInstanceEndTime().desc().list();
        HistoricProcessInstance historicProcess = historicList.get(0);
        Map<String, Object> variableList = historicProcess.getProcessVariables();
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        workflow = setWorkflowVariables(workflow, variableList, historicProcess.getId());

        return workflow;
    }

    private List<String> getApprovalReasons(Map<String, Object> variableList) {
        if (variableList.get(APPROVAL_REASONS) != null) {
            String reasonString = (String) variableList.get(APPROVAL_REASONS);
            return Arrays.asList(reasonString.split(";"));
        } else {
            return null;
        }
    }

    private DateTime getParsedDate(Map<String, Object> variableList) {
        if (variableList.get(DATE_CREATED) != null) {
            String unparsedDate = (String) variableList.get(DATE_CREATED);
            return formatter.parseDateTime(unparsedDate);
        } else {
            return null;
        }
    }

    private Map<String, Object> variableValuesToMixCase(Map<String, Object> variableList) {
        for (Entry<String, Object> entry : variableList.entrySet()) {
            if (entryValueIsStringAndNotBACS(entry)) {
                entry.setValue(StringUtil.mixCase((String) entry.getValue()));
            }
        }

        return variableList;
    }

    private boolean entryValueIsStringAndNotBACS(Entry<String, Object> entry) {
        return entry.getValue() != null && entry.getValue().getClass() == String.class
                        && !entry.getValue().toString().equalsIgnoreCase(PaymentType.BACS.code());
    }

    @Override
    public List<WorkflowItemDTO> getWorkflowList(List<Task> tasks) {
        List<WorkflowItemDTO> approvals = new ArrayList<WorkflowItemDTO>();

        for (Task task : tasks) {
            String caseNumber = (String) taskService.getVariable(task.getId(), REFUND_IDENTIFIER);
            approvals.add(getWorkflowItem(caseNumber));
        }

        return approvals;
    }

    @Override
    public List<WorkflowItemDTO> getHistoricWorkflowList(List<HistoricProcessInstance> processList) {
        List<WorkflowItemDTO> approvals = new ArrayList<WorkflowItemDTO>();

        for (HistoricProcessInstance process : processList) {
            Map<String, Object> processVariables = process.getProcessVariables();
            String caseNumber = (String) processVariables.get(REFUND_IDENTIFIER);
            approvals.add(getHistoricWorkflowItem(caseNumber));
        }

        return approvals;
    }

    @Override
    public void updateAgentAndAddClaimTime(String caseNumber, UserEntity agent, DateTime time) {
        String agentName = getAgentName(agent);
        WorkflowItemDTO workflowItem = getWorkflowItem(caseNumber);
        workflowItem.setAgent(agentName);
        workflowItem.setClaimedTime(time);
        runtimeService.setVariable(workflowItem.getExecutionId(), AGENT, agentName.toUpperCase());
        runtimeService.setVariable(workflowItem.getExecutionId(), WORKFLOW_ITEM, workflowItem);
    }

    protected String getAgentName(UserEntity agent) {
        return agent.getFirstName() + StringUtil.SPACE + agent.getLastName();
    }

    @Override
    public Boolean isApprovalIdBeingUsed(Long approvalId) {
        List<ProcessInstance> resultList = runtimeService.createProcessInstanceQuery().variableValueEquals(REFUND_IDENTIFIER, approvalId.toString())
                        .list();
        return !resultList.isEmpty();
    }
}
