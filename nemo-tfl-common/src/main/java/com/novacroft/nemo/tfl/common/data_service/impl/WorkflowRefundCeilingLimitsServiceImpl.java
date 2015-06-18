package com.novacroft.nemo.tfl.common.data_service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundCeilingLimitRulesDataService;
import com.novacroft.nemo.tfl.common.data_service.SettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowRefundCeilingLimitsService;
import com.novacroft.nemo.tfl.common.transfer.RefundCeilingRuleLimitTally;
import com.novacroft.nemo.tfl.common.transfer.RepeatClaimLimitRuleDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service(value = "workflowRefundCeilingLimitsService")
public class WorkflowRefundCeilingLimitsServiceImpl implements WorkflowRefundCeilingLimitsService{

    protected static final Logger logger = LoggerFactory.getLogger(WorkflowRefundCeilingLimitsServiceImpl.class);


    @Autowired
    protected RefundCeilingLimitRulesDataService rulesService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected SettlementDataService settlementDataService;
    @Autowired
    protected ApplicationContext applicationContext;

    protected String ruleBreachMessage;
    protected Integer oysterGoodwillPercentage;
    protected Integer oysterOtherPercentage;
    protected List<RepeatClaimLimitRuleDTO> refundCeilingLimitRuleList = new ArrayList<RepeatClaimLimitRuleDTO>();

    protected void initialize() {
        refundCeilingLimitRuleList.clear();
        refundCeilingLimitRuleList.addAll(rulesService.findAllRepeatClaimLimitRules());
        ruleBreachMessage = this.applicationContext.getMessage("refundceilinglimits.rulebreach.message",new String[]{}, null, null);
    }
    
    @Override
    public List<String> hasViolatedRefundCeilingRules(WorkflowItemDTO workflow) {
        assert workflow.getRefundDetails() != null;
        assert workflow.getRefundDetails().getRefundDepartment() != null;

        initialize();
        List<String> reason = new ArrayList<String>();
        if (!RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND.equals(workflow.getRefundDetails().getRefundScenario())) {
            findGoodwillCeilingViolations(workflow, reason);
            findRefundCeilingViolations(workflow, reason);
            findTicketOverlapRefundCeilingViolations(workflow, reason);
        }
        return reason;
    }
    
    protected void findRefundCeilingViolations(WorkflowItemDTO workflow, List<String> reason) {
        List<RefundCeilingRuleLimitTally> allRefundsInPast12Months = orderDataService.findAllRefundsForCustomerInPast12Months(workflow.getRefundDetails().getCustomerId(), workflow.getRefundDetails().getRefundScenario().name().toUpperCase());
        if (allRefundsInPast12Months.size() > 0) {
            RefundCeilingRuleLimitTally refundCeilingRuleLimitTally = allRefundsInPast12Months.get(0);
            for (RepeatClaimLimitRuleDTO rule : refundCeilingLimitRuleList) {
                matchRefundItemAndTallyToCeilingRule(workflow, reason, refundCeilingRuleLimitTally, rule);
            }
        }
    }

    protected void findGoodwillCeilingViolations(WorkflowItemDTO workflow, List<String> reason) {
        if (workflow.getRefundDetails().getGoodwillItems().size() > 0){
            List<RefundCeilingRuleLimitTally> allGoodwillsInPast12Months = orderDataService.findAllGoodwillsForCustomerInPast12Months(workflow.getRefundDetails().getCustomerId());
            if (allGoodwillsInPast12Months.size() == 1){
                for(RepeatClaimLimitRuleDTO rule : refundCeilingLimitRuleList){
                    matchGoodwillItemAndTallyToCeilingRule(reason, allGoodwillsInPast12Months, rule);
                }
            }
        }
    }
    
    protected void matchRefundItemAndTallyToCeilingRule(WorkflowItemDTO workflow, List<String> reason, RefundCeilingRuleLimitTally refundCeilingRuleLimitTally, RepeatClaimLimitRuleDTO rule) {
        if (refundCeilingRuleLimitTally.getDepartment().equalsIgnoreCase(rule.getDepartment())
                && refundCeilingRuleLimitTally.getName().toUpperCase().contains(workflow.getRefundDetails().getRefundScenario().name().toUpperCase())
                && refundCeilingRuleLimitTally.getName().equalsIgnoreCase(rule.getScenario()) && rule.getInstanceThreshold() != null
                && refundCeilingRuleLimitTally.getTally() >= rule.getInstanceThreshold()) {
            reason.add(MessageFormat.format(ruleBreachMessage, rule.getScenario(), rule.getInstanceThreshold()));
        }
    }

    protected void matchGoodwillItemAndTallyToCeilingRule(List<String> reason, List<RefundCeilingRuleLimitTally> allGoodwillsInPast12Months, RepeatClaimLimitRuleDTO rule) {
        if (rule.getDepartment().equalsIgnoreCase(RefundScenarioEnum.GOODWILL.name()) && 
                rule.getInstanceThreshold() != null && allGoodwillsInPast12Months.get(0).getTally() >= rule.getInstanceThreshold()) {
                       reason.add(MessageFormat.format(ruleBreachMessage, rule.getScenario(), rule.getInstanceThreshold()));
        }
    }
    
    protected void findTicketOverlapRefundCeilingViolations(WorkflowItemDTO workflow, List<String> reason) {
        int overlappedTicketInCurrentRefund = getNumberOfOverlappedTicketInWorkflowItem(workflow);
        if (overlappedTicketInCurrentRefund > 0) {
            RefundCeilingRuleLimitTally ticketOverlapRefundRuleLimitTally = orderDataService.findAllOverlapRefundForCustomerInPast12Months(workflow.getRefundDetails().getCustomerId());
            ticketOverlapRefundRuleLimitTally.setTally(ticketOverlapRefundRuleLimitTally.getTally().intValue() + overlappedTicketInCurrentRefund);

            for (RepeatClaimLimitRuleDTO rule : refundCeilingLimitRuleList) {
                matchTicketOverlapCeilingRule(reason, ticketOverlapRefundRuleLimitTally, rule);
            }
        }
    }
    
    protected void matchTicketOverlapCeilingRule(List<String> reason, RefundCeilingRuleLimitTally refundCeilingRuleLimitTally, RepeatClaimLimitRuleDTO rule) {
        if (refundCeilingRuleLimitTally.getDepartment().equalsIgnoreCase(rule.getDepartment())
                && rule.getScenario().equalsIgnoreCase(RefundScenarioEnum.OVERLAP.name()) 
                && rule.getInstanceThreshold() != null
                && refundCeilingRuleLimitTally.getTally() >= rule.getInstanceThreshold()) {
            reason.add(MessageFormat.format(ruleBreachMessage, rule.getScenario(), rule.getInstanceThreshold()));
        }
    }
    
    protected int getNumberOfOverlappedTicketInWorkflowItem(WorkflowItemDTO workflow) {
        int numberOfOverlappedTicket = 0;
        List<RefundItemCmd> refundItems = workflow.getRefundDetails().getRefundItems();
        for (RefundItemCmd refundItem : refundItems) {
            if (refundItem.isTicketOverlapped()) {
                numberOfOverlappedTicket++;
            }
        }
        return numberOfOverlappedTicket;
    }
   
}
   
