package com.novacroft.nemo.tfl.common.data_service.impl;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.context.ApplicationContext;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundCeilingLimitRulesDataService;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundCeilingRuleLimitTally;
import com.novacroft.nemo.tfl.common.transfer.RepeatClaimLimitRuleDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkFlowRefundCeilingLimitServiceImplTest {

    private WorkflowRefundCeilingLimitsServiceImpl mockRefundCeilingLimitsService;
    private OrderDataService mockOrderDataService;
    private RefundCeilingLimitRulesDataService mockRulesService;
    private ApplicationContext mockApplicationContext;

    @Before
    public void setUp() {
        mockRulesService = mock(RefundCeilingLimitRulesDataService.class);
        mockRefundCeilingLimitsService = mock(WorkflowRefundCeilingLimitsServiceImpl.class);
        mockOrderDataService = mock(OrderDataService.class);
        mockApplicationContext = mock(ApplicationContext.class);
       
        List<RepeatClaimLimitRuleDTO> refundCeilingLimitRuleList = new ArrayList<RepeatClaimLimitRuleDTO>();

        refundCeilingLimitRuleList.add(new RepeatClaimLimitRuleDTO(RefundDepartmentEnum.RCL.code(),RefundScenarioEnum.FAILEDCARD.code(),null,null,3));
        refundCeilingLimitRuleList.add(new RepeatClaimLimitRuleDTO(RefundDepartmentEnum.RCL.code(),RefundScenarioEnum.LOST.code(),null,null,3));
        refundCeilingLimitRuleList.add(new RepeatClaimLimitRuleDTO(RefundDepartmentEnum.RCL.code(),RefundScenarioEnum.STOLEN.code(),null,null,3));
        refundCeilingLimitRuleList.add(new RepeatClaimLimitRuleDTO(RefundDepartmentEnum.RCL.code(),RefundScenarioEnum.DESTROYED.code(),null,null,3));
        refundCeilingLimitRuleList.add(new RepeatClaimLimitRuleDTO(RefundDepartmentEnum.RCL.code(),RefundScenarioEnum.OVERLAP.code(),null,null,2));

        mockRefundCeilingLimitsService.orderDataService = mockOrderDataService;
        mockRefundCeilingLimitsService.rulesService = mockRulesService;
        mockRefundCeilingLimitsService.refundCeilingLimitRuleList = refundCeilingLimitRuleList;
        mockRefundCeilingLimitsService.applicationContext = mockApplicationContext;
        mockRefundCeilingLimitsService.ruleBreachMessage = "{0} refund exceeded {1} per 12 month period";
        
    }

    @Test
    public void testInitialize(){
        List<RepeatClaimLimitRuleDTO> refundCeilingLimitRuleList = new ArrayList<RepeatClaimLimitRuleDTO>();
        doCallRealMethod().when(mockRefundCeilingLimitsService).initialize();
        doReturn(refundCeilingLimitRuleList).when(mockRulesService).findAllRepeatClaimLimitRules();
        doReturn("TEST").when(mockApplicationContext).getMessage("refundceilinglimits.rulebreach.message",new String[]{}, null, null);
        mockRefundCeilingLimitsService.initialize();
        assertNotNull(refundCeilingLimitRuleList);
        assert(StringUtil.isNotEmpty(mockRefundCeilingLimitsService.ruleBreachMessage));
    }
    
    @Test
    public void failedCardViolationTest(){
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO();
        goodwillPaymentItemDTO.setPrice(100);
        GoodwillReasonDTO goodwillReasonDTO = new GoodwillReasonDTO();
        goodwillReasonDTO.setReasonId(1L);
        goodwillPaymentItemDTO.setGoodwillReasonDTO(goodwillReasonDTO);
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.RCL);
        refundDetails.setRefundScenario(RefundScenarioEnum.FAILEDCARD);
        workflow.setRefundDetails(refundDetails);
        refundDetails.setCustomerId(4999L);
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        refundDetails.getGoodwillPaymentItemList().add(new GoodwillPaymentItemCmd(goodwillPaymentItemDTO));
        List<RepeatClaimLimitRuleDTO> repeatClaimCeilingRules = new ArrayList<RepeatClaimLimitRuleDTO>();
        RepeatClaimLimitRuleDTO rule = new RepeatClaimLimitRuleDTO("RCL","FAILEDCARDREFUND",null,null,1);
        RepeatClaimLimitRuleDTO goodwillRule = new RepeatClaimLimitRuleDTO(RefundScenarioEnum.GOODWILL.name(),"GOODWILLSEASONTKT",null,null,1);
        goodwillRule.setInstanceThreshold(1);
        rule.setInstanceThreshold(1);
        repeatClaimCeilingRules.add(rule);
        repeatClaimCeilingRules.add(goodwillRule);
        
        List<RefundCeilingRuleLimitTally> goodwillTally = new ArrayList<RefundCeilingRuleLimitTally>();
        RefundCeilingRuleLimitTally goodwillTallyItem = new RefundCeilingRuleLimitTally(RefundScenarioEnum.GOODWILL.name(), 3);
        goodwillTallyItem.setName("GOODWILLSEASONTKT");
        goodwillTallyItem.setDepartment(RefundScenarioEnum.GOODWILL.name());
        goodwillTally.add(goodwillTallyItem);
        
        List<RefundCeilingRuleLimitTally> refundTally = new ArrayList<RefundCeilingRuleLimitTally>();
        RefundCeilingRuleLimitTally refundTallyItem = new RefundCeilingRuleLimitTally("FAILEDCARDREFUND", 3);
        refundTallyItem.setDepartment("RCL");
        refundTally.add(refundTallyItem);
        
        doCallRealMethod().when(mockRefundCeilingLimitsService).matchGoodwillItemAndTallyToCeilingRule(Mockito.anyList(), Mockito.anyList(), any(RepeatClaimLimitRuleDTO.class));
        doCallRealMethod().when(mockRefundCeilingLimitsService).matchRefundItemAndTallyToCeilingRule(any(WorkflowItemDTO.class), Mockito.anyList(), any(RefundCeilingRuleLimitTally.class), any(RepeatClaimLimitRuleDTO.class));
        doCallRealMethod().when(mockRefundCeilingLimitsService).hasViolatedRefundCeilingRules(any(WorkflowItemDTO.class));
        doCallRealMethod().when(mockRefundCeilingLimitsService).findGoodwillCeilingViolations(any(WorkflowItemDTO.class), Mockito.anyList());
        doCallRealMethod().when(mockRefundCeilingLimitsService).findRefundCeilingViolations(any(WorkflowItemDTO.class), Mockito.anyList());
        doReturn(refundTally).when(mockOrderDataService).findAllRefundsForCustomerInPast12Months(Mockito.anyLong(), Mockito.anyString());
        doReturn(goodwillTally).when(mockOrderDataService).findAllGoodwillsForCustomerInPast12Months(Mockito.anyLong());
        doReturn(repeatClaimCeilingRules).when(mockRulesService).findAllRepeatClaimLimitRules();
        mockRefundCeilingLimitsService.refundCeilingLimitRuleList=repeatClaimCeilingRules;
        

        List<String> result = mockRefundCeilingLimitsService.hasViolatedRefundCeilingRules(workflow);
        assertNotNull(result);
        assert(result.size() ==2);
    }

    @Test
    public void testMatchRefundItemAndTallyToCeilingRuleDoesNotMatch(){
/*        if (refundCeilingRuleLimitTally.getDepartment().equalsIgnoreCase(rule.getDepartment())
                && refundCeilingRuleLimitTally.getName().toUpperCase().contains(workflow.getRefundDetails().getRefundScenario().name().toUpperCase())
                && refundCeilingRuleLimitTally.getName().equalsIgnoreCase(rule.getScenario()) && rule.getInstanceThreshold() != null
                && refundCeilingRuleLimitTally.getTally() >= rule.getInstanceThreshold()) {*/
        
        String department="RCL";
            
        List<String> reason = new ArrayList<String>();
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO();
        goodwillPaymentItemDTO.setPrice(100);
        GoodwillReasonDTO goodwillReasonDTO = new GoodwillReasonDTO();
        goodwillReasonDTO.setReasonId(1L);
        goodwillPaymentItemDTO.setGoodwillReasonDTO(goodwillReasonDTO);
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.RCL);
        refundDetails.setRefundScenario(RefundScenarioEnum.FAILEDCARD);
        workflow.setRefundDetails(refundDetails);
        refundDetails.setCustomerId(4999L);
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        refundDetails.getGoodwillPaymentItemList().add(new GoodwillPaymentItemCmd(goodwillPaymentItemDTO));
        
        RepeatClaimLimitRuleDTO rule = new RepeatClaimLimitRuleDTO(department,RefundScenarioEnum.FAILEDCARD.name(),null,null,100);
        RefundCeilingRuleLimitTally tally = new RefundCeilingRuleLimitTally(RefundScenarioEnum.FAILEDCARD.name(), 0);
        tally.setDepartment(department);

        
        doCallRealMethod().when(mockRefundCeilingLimitsService).matchRefundItemAndTallyToCeilingRule(any(WorkflowItemDTO.class), Mockito.anyList(), any(RefundCeilingRuleLimitTally.class), any(RepeatClaimLimitRuleDTO.class));
        
        mockRefundCeilingLimitsService.matchRefundItemAndTallyToCeilingRule(workflow,reason,tally,rule);
        assert(reason.size() == 0);
        
        rule.setInstanceThreshold(null);
        mockRefundCeilingLimitsService.matchRefundItemAndTallyToCeilingRule(workflow,reason,tally,rule);
        assert(reason.size() == 0);
        
        tally.setName(RefundScenarioEnum.CANCEL_AND_SURRENDER.name());
        mockRefundCeilingLimitsService.matchRefundItemAndTallyToCeilingRule(workflow,reason,tally,rule);
        assert(reason.size() == 0);
        
        
    }
 

    @Test
    public void testMatchGoodwillItemAndTallyToCeilingRuleDoesNotMatch(){
        List<String> reason = new ArrayList<String>();
        GoodwillPaymentItemDTO goodwillPaymentItemDTO = new GoodwillPaymentItemDTO();
        goodwillPaymentItemDTO.setPrice(100);
        GoodwillReasonDTO goodwillReasonDTO = new GoodwillReasonDTO();
        goodwillReasonDTO.setReasonId(1L);
        goodwillPaymentItemDTO.setGoodwillReasonDTO(goodwillReasonDTO);
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.RCL);
        refundDetails.setRefundScenario(RefundScenarioEnum.FAILEDCARD);
        workflow.setRefundDetails(refundDetails);
        refundDetails.setCustomerId(4999L);
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        refundDetails.getGoodwillPaymentItemList().add(new GoodwillPaymentItemCmd(goodwillPaymentItemDTO));
        RepeatClaimLimitRuleDTO rule = new RepeatClaimLimitRuleDTO("GOODWILL",RefundDepartmentEnum.RCL.code(),null,null,20);
        RefundCeilingRuleLimitTally tally = new RefundCeilingRuleLimitTally(RefundScenarioEnum.FAILEDCARD.code(), 1);
        tally.setDepartment("RCL");
        List<RefundCeilingRuleLimitTally> goodwillTally = new ArrayList<RefundCeilingRuleLimitTally>();
        goodwillTally.add(tally);
        
        doCallRealMethod().when(mockRefundCeilingLimitsService).matchGoodwillItemAndTallyToCeilingRule(Mockito.anyList(), Mockito.anyList(), any(RepeatClaimLimitRuleDTO.class));
        
        mockRefundCeilingLimitsService.matchGoodwillItemAndTallyToCeilingRule(reason,goodwillTally,rule);
        assert(reason.size() == 0);
        
        rule.setInstanceThreshold(null);
        mockRefundCeilingLimitsService.matchGoodwillItemAndTallyToCeilingRule(reason,goodwillTally,rule);
        assert(reason.size() == 0);
        
        
    }
    
    @Test
    public void hasViolatedRefundCeilingRulesShouldNotRunViolationsRulesForAnonymousGoodwillRefundTest(){
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.RCL);
        refundDetails.setRefundScenario(RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND);
        workflow.setRefundDetails(refundDetails);
        refundDetails.setCustomerId(4999L);
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        mockRefundCeilingLimitsService.hasViolatedRefundCeilingRules(workflow);
        verify(mockRefundCeilingLimitsService, never()).findGoodwillCeilingViolations(any(WorkflowItemDTO.class), Mockito.anyList());
        verify(mockRefundCeilingLimitsService, never()).findRefundCeilingViolations(any(WorkflowItemDTO.class), Mockito.anyList());
    }
}
