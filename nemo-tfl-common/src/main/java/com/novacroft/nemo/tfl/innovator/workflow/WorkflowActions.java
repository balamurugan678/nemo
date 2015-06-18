package com.novacroft.nemo.tfl.innovator.workflow;

import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AGENT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.WORKFLOW_ITEM;

import java.io.Serializable;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.ManagementService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.Period;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.ApprovalLevelRequiredEnum;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.constant.WorkflowFields;
import com.novacroft.nemo.tfl.common.converter.impl.RefundToWorkflowConverter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardUpdateRequestDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundScenarioHotListReasonTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.SettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowBusinessRulesService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowRandomApprovalSampleService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowRefundCeilingLimitsService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowValueApprovalLimitsService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkflowActions implements Serializable {

    static final Logger logger = LoggerFactory.getLogger(WorkflowActions.class);

    private static final long serialVersionUID = 1L;

    @Autowired
    protected TaskService taskService;
    @Autowired
    protected RepositoryService repositoryService;
    @Autowired
    protected HistoryService historyService;
    @Autowired
    protected ManagementService managementService;
    @Autowired
    protected RuntimeService runtimeService;
    @Autowired
    protected IdentityService identityService;
    @Autowired
    protected WorkflowValueApprovalLimitsService valueApprovalLimitsService;
    @Autowired
    protected WorkflowRandomApprovalSampleService randomApprovalService;
    @Autowired
    protected WorkflowBusinessRulesService businessRuleService;
    @Autowired
    protected WorkflowRefundCeilingLimitsService refundCeilingService;
    @Autowired
    protected RefundToWorkflowConverter converter;
    @Autowired
    protected CardUpdateRequestDataService cubicCardService;
    @Autowired
    protected CardDataService cardService;
    @Autowired
    protected SettlementDataService settlementDataService;
    @Autowired
    protected OrderDataService orderService;
    @Autowired
    protected HotlistCardService hotlistCardService;
    @Autowired
    protected RefundScenarioHotListReasonTypeDataService refundScenarioHotListReasonTypeDataService;
    @Autowired
    protected RefundPaymentService refundPaymentService;
    @Autowired
    protected SystemParameterService systemParameterService;

    @Value("cubicUserId")
    protected String cubicUserId;

    @Value("cubicPassword")
    protected String cubicPassword;

    private static final String TIME_CYCLE_DURATION = "workflowTimeCycleDuration";

    public void retrieveDetailsFromCentralSystem(WorkflowItemDTO workflowItem) {
        RefundDetailDTO refundBean = workflowItem.getRefundDetails();

        logger.debug("Searching for username :  " + refundBean.getName());
        logger.debug("with card id :  " + refundBean.getCardId());
        logger.debug("Success!");
    }

    public void checkApprovalRequired(WorkflowItemDTO workflow, String executionId) {
        assert (StringUtil.isNotEmpty(executionId));
        assert (workflow != null);
        assert (workflow.getRefundDetails() != null);
        assert (workflow.getRefundDetails().getTotalRefundAmount() != null);

        RefundDetailDTO refundBean = workflow.getRefundDetails();
        logger.debug("----------CHECK APPROVAL REQUIRED--------------");

        checkRefundValueLimitThresholdRules(workflow, refundBean);
        checkRefundAgainstBusinessRules(workflow, refundBean);
        checkRefundAgainstRefundCeilingRules(workflow, refundBean);
        checkRandomFlaggingRules(workflow, refundBean);

        if (PaymentType.CHEQUE.equals(refundBean.getPaymentType()) || PaymentType.BACS.equals(refundBean.getPaymentType())) {
            refundBean.setBacsChequePaymentRequired(true);
        } else if (PaymentType.AD_HOC_LOAD.equals(refundBean.getPaymentType())) {
            refundBean.setAdhocLoadRequired(true);
        } else if (PaymentType.WEB_ACCOUNT_CREDIT.equals(refundBean.getPaymentType())) {
            refundBean.setCardPaymentRequired(false);
        } else {
            refundBean.setCardPaymentRequired(true);
        }

        workflow.setRefundDetails(refundBean);

        runtimeService.setVariable(executionId, WorkflowFields.APPROVAL_REASONS, workflow.getApprovalReasons());

    }

    private void checkRefundValueLimitThresholdRules(WorkflowItemDTO workflow, RefundDetailDTO refundBean) {
        logger.debug("Checking Approval Value Limits ");
        ApprovalLevelRequiredEnum approvalLevelRequired =
                valueApprovalLimitsService.checkApprovalThreshold(workflow, refundBean);
        if (approvalLevelRequired.equals(ApprovalLevelRequiredEnum.NONE)) {
            workflow.getRefundDetails().setApprovalRequired(Boolean.FALSE);
            workflow.getRefundDetails().setSecondStageApprovalRequired(Boolean.FALSE);
        }
        if (approvalLevelRequired.equals(ApprovalLevelRequiredEnum.FIRST_STAGE)) {
            workflow.getRefundDetails().setApprovalRequired(Boolean.TRUE);
            workflow.getRefundDetails().setSecondStageApprovalRequired(Boolean.FALSE);
        }
        if (approvalLevelRequired.equals(ApprovalLevelRequiredEnum.SECOND_STAGE)) {
            workflow.getRefundDetails().setApprovalRequired(Boolean.TRUE);
            workflow.getRefundDetails().setSecondStageApprovalRequired(Boolean.TRUE);
        }

    }

    private void checkRandomFlaggingRules(WorkflowItemDTO workflow, RefundDetailDTO refundBean) {
        logger.debug("processing for possible random approval..");
        List<String> randomFlagging = randomApprovalService.processForRandomSampleFlagging(workflow);
        if (!refundBean.getApprovalRequired()) {
            workflow.getRefundDetails().setApprovalRequired(randomFlagging.size() > 0);
        }
        List<String> reasonsList = workflow.getApprovalReasonsList();
        reasonsList.addAll(randomFlagging);
        workflow.setApprovalReasons(reasonsList);
    }

    private void checkRefundAgainstRefundCeilingRules(WorkflowItemDTO workflow, RefundDetailDTO refundBean) {
        logger.debug("Checking Refund ceiling amount rules");
        List<String> refundCeilingRules = refundCeilingService.hasViolatedRefundCeilingRules(workflow);
        if (!refundBean.getApprovalRequired()) {
            workflow.getRefundDetails().setApprovalRequired(refundCeilingRules.size() > 0);
        }
        List<String> reasonsList = workflow.getApprovalReasonsList();
        reasonsList.addAll(refundCeilingRules);
        workflow.setApprovalReasons(reasonsList);
    }

    private void checkRefundAgainstBusinessRules(WorkflowItemDTO workflow, RefundDetailDTO refundBean) {
        logger.debug("Checking Business rules");
        List<String> checkBusinessRules = businessRuleService.checkBusinessRules(workflow);
        if (!refundBean.getApprovalRequired()) {
            workflow.getRefundDetails().setApprovalRequired(checkBusinessRules.size() > 0);
        }
        List<String> reasonsList = workflow.getApprovalReasonsList();
        reasonsList.addAll(checkBusinessRules);
        workflow.setApprovalReasons(reasonsList);
    }

    public void setCardAsHotlisted(WorkflowItemDTO workflow, String executionId) {
        logger.info("Hotlisting the card");
        assert (null != workflow.getRefundDetails().getCardId());
        CardDTO card = cardService.findById(workflow.getRefundDetails().getCardId());

        RefundScenarioHotListReasonTypeDTO refundScenarioHotListReasonTypeDTO = refundScenarioHotListReasonTypeDataService
                .findByRefundScenario(workflow.getRefundDetails().getRefundScenario());
        if (card.getHotlistReason() == null) {
            if (refundScenarioHotListReasonTypeDTO != null) {
                hotlistCardService.toggleCardHotlisted(card.getCardNumber(), refundScenarioHotListReasonTypeDTO.getHotlistReasonDTO().getId()
                                .intValue());
            } else {
                logger.warn("Unable to hotlist the card with Id " + workflow.getRefundDetails().getCardId().toString());
            }
        }
    }

    public Boolean processPayment(WorkflowItemDTO workflowItem) {
        refundPaymentService.completeRefund(converter.convert(workflowItem));

        logger.debug("----------PROCESS PAYMENT--------------");

        logger.debug("record added to FSC payment file");

        logger.debug("payment processed");

        logger.debug("response file received");

        return Boolean.TRUE;

    }

    public Boolean pushToGate(WorkflowItemDTO workflowItem) {
        logger.debug("----------PUSH TO GATE--------------");

        refundPaymentService.completeRefund(converter.convert(workflowItem));
        workflowItem.getRefundDetails().setAcceptedByGate(Boolean.TRUE);
        return true;
    }

    public Boolean cardRefundProcess(WorkflowItemDTO workflowItem) {
        refundPaymentService.completeRefund(converter.convert(workflowItem));

        logger.debug("----------CARD REFUND PROCESS--------------");

        return Boolean.TRUE;

    }

    public Boolean wacAddedToAccount(WorkflowItemDTO workflowItem) {
        refundPaymentService.completeRefund(converter.convert(workflowItem));

        logger.debug("----------WAC ADDED TO ACCOUNT--------------");

        return Boolean.TRUE;

    }

    /*
     * This isn't an ideal solution, but there are problems with equality matching in Activiti
     */
    public void findOriginatingApplication(WorkflowItemDTO workflowItem) {
        RefundDetailDTO refundBean = workflowItem.getRefundDetails();
        refundBean.setRefundOriginatingApplicationIsInnovator(
                refundBean.getRefundOriginatingApplication().equals(ApplicationName.NEMO_TFL_INNOVATOR));
        refundBean.setRefundOriginatingApplicationIsOnline(!refundBean.getRefundOriginatingApplicationIsInnovator());

    }

    public Boolean endListener(WorkflowItemDTO workflowItem) {
        RefundDetailDTO refundBean = workflowItem.getRefundDetails();

        logger.debug("----------An End event was detected for the following task details --------------");

        logger.debug(refundBean.toString());
        return Boolean.TRUE;

    }

    public Boolean checkHotListingFeasible(WorkflowItemDTO workflowItem) {
        RefundDetailDTO refundBean = workflowItem.getRefundDetails();
        return !(RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND.equals(refundBean.getRefundScenario()) || RefundScenarioEnum.STANDALONE_GOODWILL_REFUND.equals(refundBean.getRefundScenario()));
    }

    public void unclaimTask(WorkflowItemDTO workflowItem) {
        HistoricTaskInstance lastTask = historyService.createHistoricTaskInstanceQuery()
                        .processVariableValueEquals(WorkflowFields.REFUND_IDENTIFIER, workflowItem.getCaseNumber())
                        .orderByHistoricTaskInstanceEndTime().desc().list().get(0);
        if (StringUtils.isNotEmpty(workflowItem.getAgent()) && isPeriodPastTimeCycle(workflowItem)) {
            logger.debug("Unclaiming case " + workflowItem.getCaseNumber());
            workflowItem.setAgent(StringUtil.EMPTY_STRING);
            workflowItem.setClaimedTime(null);
            runtimeService.setVariable(workflowItem.getExecutionId(), WORKFLOW_ITEM, workflowItem);
            runtimeService.setVariable(workflowItem.getExecutionId(), AGENT, StringUtil.EMPTY_STRING);
            taskService.unclaim(lastTask.getId());
        }
    }

    protected Boolean isPeriodPastTimeCycle(WorkflowItemDTO workflowItem) {
        return new Period(workflowItem.getClaimedTime(), new DateTime()).toStandardSeconds().isGreaterThan(
                        new Period(getTimeCycleWithoutRepeat()).toStandardSeconds());
    }

    protected String getTimeCycleWithoutRepeat() {
        String duration = systemParameterService.getParameterValue(TIME_CYCLE_DURATION);

        StringBuilder timeCycle = new StringBuilder();
        timeCycle.append("PT");
        timeCycle.append(duration);
        return timeCycle.toString();
    }

    public String getTimeCycle() {
        String duration = systemParameterService.getParameterValue(TIME_CYCLE_DURATION);

        StringBuilder timeCycle = new StringBuilder();
        timeCycle.append("R/PT");
        timeCycle.append(duration);
        return timeCycle.toString();
    }
}
