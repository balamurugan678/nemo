package com.novacroft.nemo.tfl.common.data_service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.constant.ApprovalLevelRequiredEnum;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowValueApprovalLimitsService;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Service(value = "workflowValueApprovalLimitsService")
public class WorkflowValueApprovalLimitsServiceImpl implements WorkflowValueApprovalLimitsService{
    private static final int SINGLE_PENNY_OFFSET = 1;
    private static final String OYSTER_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT = "oyster_goodwill_no_approvalreqd_lower_limit";
    private static final String OYSTER_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT = "oyster_goodwill_no_approvalreqd_upper_limit";
    private static final String OYSTER_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT = "oyster_goodwill_firststageapproval_upper_limit";
    private static final String OYSTER_OTHERREFUNDS_NO_APPROVALREQD_LOWER_LIMIT = "oyster_otherrefunds_no_approvalreqd_lower_limit";
    private static final String OYSTER_OTHERREFUNDS_NO_APPROVALREQD_UPPER_LIMIT = "oyster_otherrefunds_no_approvalreqd_upper_limit";
    private static final String OYSTER_OTHERREFUNDS_FIRSTSTAGEAPPROVAL_UPPER_LIMIT = "oyster_otherrefunds_firststageapproval_upper_limit";
    private static final String LU_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT = "lu_goodwill_no_approvalreqd_lower_limit";
    private static final String LU_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT = "lu_goodwill_no_approvalreqd_upper_limit";
    private static final String LU_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT = "lu_goodwill_firststageapproval_upper_limit";
    private static final String CCS_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT = "ccs_goodwill_no_approvalreqd_lower_limit";
    private static final String CCS_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT = "ccs_goodwill_no_approvalreqd_upper_limit";
    private static final String CCS_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT = "ccs_goodwill_firststageapproval_upper_limit";

    private static final String RULE_BREACH_DESCRIPTION_MAXIUMUM_LIMIT_KEY = "rule_breach_description_maximum_limit";
    
    private String RULE_BREACH_DESCRIPTION_MAXIMUM_LIMIT_TEXT = "";

    protected Integer oysterGoodwillNoApprovalRequiredLowerLimit = 0;
    protected Integer oysterGoodwillNoApprovalRequiredUpperLimit = 0;
    protected Integer oysterGoodwillFirstStageApprovalLowerLimit = 0;
    protected Integer oysterGoodwillFirstStageApprovalUpperLimit = 0;
    protected Integer oysterGoodwillSecondStageApprovalLowerLimit = 0;

    protected Integer oysterOtherrefundsNoApprovalRequiredLowerLimit = 0;
    protected Integer oysterOtherrefundsNoApprovalRequiredUpperLimit = 0;
    protected Integer oysterOtherrefundsFirstStageApprovalLowerLimit = 0;
    protected Integer oysterOtherrefundsFirstStageApprovalUpperLimit = 0;
    protected Integer oysterOtherrefundsSecondStageApprovalLowerLimit = 0;

    protected Integer luGoodwillNoApprovalRequiredLowerLimit = 0;
    protected Integer luGoodwillNoApprovalRequiredUpperLimit = 0;
    protected Integer luGoodwillFirstStageApprovalLowerLimit = 0;
    protected Integer luGoodwillFirstStageApprovalUpperLimit = 0;
    protected Integer luGoodwillSecondStageApprovalLowerLimit = 0;

    protected Integer ccsGoodwillNoApprovalRequiredLowerLimit = 0;
    protected Integer ccsGoodwillNoApprovalRequiredUpperLimit = 0;
    protected Integer ccsGoodwillFirstStageApprovalLowerLimit = 0;
    protected Integer ccsGoodwillFirstStageApprovalUpperLimit = 0;
    protected Integer ccsGoodwillSecondStageApprovalLowerLimit = 0;

        
    protected Integer workflowRefundsQuantityPerPeriodCeiling;

    
    @Autowired
    protected SystemParameterDataService systemParameterService;
    @Autowired
    protected CardDataService cardService;
    @Autowired
    protected PaymentCardSettlementDataService paymentService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected ContentDataService contentDataService;

    WorkflowValueApprovalLimitsServiceImpl() {

    }

    protected void initialize() {
        //TODO other rules have now been implemented in a separate dedicated table. It may be worthwhile moving these rules to the same table at some point in the future.
        SystemParameterDTO oysterGoodwillNoApprovalRequiredLowerLimitDTO = systemParameterService.findByCode(OYSTER_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT);
        SystemParameterDTO oysterGoodwillNoApprovalRequiredUpperLimitDTO = systemParameterService.findByCode(OYSTER_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT);
        SystemParameterDTO oysterGoodwillFirstStageApprovalUpperLimitDTO = systemParameterService.findByCode(OYSTER_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);
        SystemParameterDTO oysterOtherrefundsNoApprovalRequiredLowerLimitDTO = systemParameterService.findByCode(OYSTER_OTHERREFUNDS_NO_APPROVALREQD_LOWER_LIMIT);
        SystemParameterDTO oysterOtherrefundsNoApprovalRequiredUpperLimitDTO = systemParameterService.findByCode(OYSTER_OTHERREFUNDS_NO_APPROVALREQD_UPPER_LIMIT);
        SystemParameterDTO oysterOtherrefundsFirstStageApprovalUpperLimitDTO = systemParameterService.findByCode(OYSTER_OTHERREFUNDS_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);
        SystemParameterDTO luGoodwillNoApprovalRequiredLowerLimitDTO = systemParameterService.findByCode(LU_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT);
        SystemParameterDTO luGoodwillNoApprovalRequiredUpperLimitDTO = systemParameterService.findByCode(LU_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT);
        SystemParameterDTO luGoodwillFirstStageApprovalUpperLimitDTO = systemParameterService.findByCode(LU_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);
        SystemParameterDTO ccsGoodwillNoApprovalRequiredLowerLimitDTO = systemParameterService.findByCode(CCS_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT);
        SystemParameterDTO ccsGoodwillNoApprovalRequiredUpperLimitDTO = systemParameterService.findByCode(CCS_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT);
        SystemParameterDTO ccsGoodwillFirstStageApprovalUpperLimitDTO = systemParameterService.findByCode(CCS_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);

        oysterGoodwillNoApprovalRequiredLowerLimit = Integer.valueOf(oysterGoodwillNoApprovalRequiredLowerLimitDTO.getValue());
        oysterGoodwillNoApprovalRequiredUpperLimit = Integer.valueOf(oysterGoodwillNoApprovalRequiredUpperLimitDTO.getValue());
        oysterGoodwillFirstStageApprovalLowerLimit = oysterGoodwillNoApprovalRequiredUpperLimit + SINGLE_PENNY_OFFSET;
        oysterGoodwillFirstStageApprovalUpperLimit = Integer.valueOf(oysterGoodwillFirstStageApprovalUpperLimitDTO.getValue());
        oysterGoodwillSecondStageApprovalLowerLimit = oysterGoodwillFirstStageApprovalUpperLimit + SINGLE_PENNY_OFFSET;
        
        oysterOtherrefundsNoApprovalRequiredLowerLimit = Integer.valueOf(oysterOtherrefundsNoApprovalRequiredLowerLimitDTO.getValue());
        oysterOtherrefundsNoApprovalRequiredUpperLimit = Integer.valueOf(oysterOtherrefundsNoApprovalRequiredUpperLimitDTO.getValue());
        oysterOtherrefundsFirstStageApprovalLowerLimit  = oysterOtherrefundsNoApprovalRequiredUpperLimit + SINGLE_PENNY_OFFSET;
        oysterOtherrefundsFirstStageApprovalUpperLimit = Integer.valueOf(oysterOtherrefundsFirstStageApprovalUpperLimitDTO.getValue());
        oysterOtherrefundsSecondStageApprovalLowerLimit = oysterOtherrefundsFirstStageApprovalUpperLimit + SINGLE_PENNY_OFFSET;
        
        luGoodwillNoApprovalRequiredLowerLimit = Integer.valueOf(luGoodwillNoApprovalRequiredLowerLimitDTO.getValue());
        luGoodwillNoApprovalRequiredUpperLimit = Integer.valueOf(luGoodwillNoApprovalRequiredUpperLimitDTO.getValue());
        luGoodwillFirstStageApprovalLowerLimit = luGoodwillNoApprovalRequiredUpperLimit + SINGLE_PENNY_OFFSET;
        luGoodwillFirstStageApprovalUpperLimit = Integer.valueOf(luGoodwillFirstStageApprovalUpperLimitDTO.getValue());
        luGoodwillSecondStageApprovalLowerLimit = luGoodwillFirstStageApprovalUpperLimit + SINGLE_PENNY_OFFSET;
        
        ccsGoodwillNoApprovalRequiredLowerLimit = Integer.valueOf(ccsGoodwillNoApprovalRequiredLowerLimitDTO.getValue());
        ccsGoodwillNoApprovalRequiredUpperLimit = Integer.valueOf(ccsGoodwillNoApprovalRequiredUpperLimitDTO.getValue());
        ccsGoodwillFirstStageApprovalLowerLimit = ccsGoodwillNoApprovalRequiredUpperLimit + SINGLE_PENNY_OFFSET;
        ccsGoodwillFirstStageApprovalUpperLimit = Integer.valueOf(ccsGoodwillFirstStageApprovalUpperLimitDTO.getValue());
        ccsGoodwillSecondStageApprovalLowerLimit= ccsGoodwillFirstStageApprovalUpperLimit + SINGLE_PENNY_OFFSET ;

        RULE_BREACH_DESCRIPTION_MAXIMUM_LIMIT_TEXT = contentDataService.findByLocaleAndCode(RULE_BREACH_DESCRIPTION_MAXIUMUM_LIMIT_KEY,
                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
    }

    @Override
    public ApprovalLevelRequiredEnum checkApprovalThreshold(WorkflowItemDTO workflowItem, RefundDetailDTO refundDetails){
        initialize(); 
    	RefundDepartmentEnum dept = refundDetails.getRefundDepartment();
    	RefundScenarioEnum scenario = refundDetails.getRefundScenario();
    	int totalRefundPrice = refundDetails.getTotalRefundAmount().intValue();
    	int goodwillRefundTotal = 0;
    	ApprovalLevelRequiredEnum approvalType = ApprovalLevelRequiredEnum.NONE;
    	
        for (GoodwillPaymentItemCmd goodwillItem : refundDetails.getGoodwillItems()) {
    	        goodwillRefundTotal += Integer.valueOf(goodwillItem.getPrice());
    	}

        if (dept.equals(RefundDepartmentEnum.LU) && goodwillRefundTotal > 0){
            approvalType = calculateApprovalTypeByThreshold(goodwillRefundTotal,luGoodwillNoApprovalRequiredLowerLimit,luGoodwillNoApprovalRequiredUpperLimit,luGoodwillFirstStageApprovalLowerLimit,luGoodwillFirstStageApprovalUpperLimit,luGoodwillSecondStageApprovalLowerLimit);
            addApprovalReasonIfNeeded(approvalType, workflowItem);
        } else if (dept.equals(RefundDepartmentEnum.CCS) && goodwillRefundTotal > 0){
            approvalType = calculateApprovalTypeByThreshold(goodwillRefundTotal,ccsGoodwillNoApprovalRequiredLowerLimit,ccsGoodwillNoApprovalRequiredUpperLimit,ccsGoodwillFirstStageApprovalLowerLimit,ccsGoodwillFirstStageApprovalUpperLimit,ccsGoodwillSecondStageApprovalLowerLimit);
            addApprovalReasonIfNeeded(approvalType, workflowItem);
        }else if (dept.equals(RefundDepartmentEnum.OYSTER) && goodwillRefundTotal > 0){
                approvalType = calculateApprovalTypeByThreshold(goodwillRefundTotal,oysterGoodwillNoApprovalRequiredLowerLimit,oysterGoodwillNoApprovalRequiredUpperLimit,oysterGoodwillFirstStageApprovalLowerLimit,oysterGoodwillFirstStageApprovalUpperLimit,oysterGoodwillSecondStageApprovalLowerLimit);
                addApprovalReasonIfNeeded(approvalType, workflowItem);
        }
    	    
        if (dept.equals(RefundDepartmentEnum.OYSTER) && !(RefundScenarioEnum.GOODWILL.equals(scenario) || RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND.equals(scenario))) {
    	    ApprovalLevelRequiredEnum approvalTypeForOysterNonGoodwill = calculateApprovalTypeByThreshold(totalRefundPrice,oysterOtherrefundsNoApprovalRequiredLowerLimit,oysterOtherrefundsNoApprovalRequiredUpperLimit,oysterOtherrefundsFirstStageApprovalLowerLimit,oysterOtherrefundsFirstStageApprovalUpperLimit,oysterOtherrefundsSecondStageApprovalLowerLimit);
            addApprovalReasonIfNeeded(approvalType, workflowItem);
            if (approvalTypeForOysterNonGoodwill.code() > approvalType.code()){
                approvalType = approvalTypeForOysterNonGoodwill;
                addApprovalReasonIfNeeded(approvalType, workflowItem);
            }
                
    	}

        return approvalType;
    }
    
    protected void addApprovalReasonIfNeeded(ApprovalLevelRequiredEnum approvalType, WorkflowItemDTO workflowItem) {
    	if(ApprovalLevelRequiredEnum.NONE != approvalType) {
    	    
    		List<String> reasonsList = workflowItem.getApprovalReasonsList();
            if (!reasonsList.contains(RULE_BREACH_DESCRIPTION_MAXIMUM_LIMIT_TEXT.toUpperCase())) {
                reasonsList.add(RULE_BREACH_DESCRIPTION_MAXIMUM_LIMIT_TEXT);
            }
    		workflowItem.setApprovalReasons(reasonsList);
    	}
    }
  
    private ApprovalLevelRequiredEnum calculateApprovalTypeByThreshold(Integer totalRefundPrice , Integer noApprovalLower,Integer noApprovalUpper,Integer firstStageApproverLowerLimit ,Integer firstStageApproverUpperlimit,Integer secondStageApproverLowerLimit){
       
        if (totalRefundPrice >= noApprovalLower && totalRefundPrice <= noApprovalUpper){
            return ApprovalLevelRequiredEnum.NONE;
        }else if (totalRefundPrice >= firstStageApproverLowerLimit && totalRefundPrice <= firstStageApproverUpperlimit){
            return ApprovalLevelRequiredEnum.FIRST_STAGE;
        }else if(totalRefundPrice >= secondStageApproverLowerLimit){
            return ApprovalLevelRequiredEnum.SECOND_STAGE;
        }
        
        return null;
    }

   
}
