package com.novacroft.nemo.test_support;

import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.ApprovalLevelRequiredEnum;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.transfer.CardUpdateResponseDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

import static com.novacroft.nemo.test_support.RefundTestUtil.REFUND_AMOUNT_IN_PENCE_L;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.OYSTER_NUMBER_1;

public final class WorkflowActionsTestUtil {

	private static Integer ERROR_CODE = 0;
	private static String ERROR_DESC = "Returned Error Description";
	private static String EMPTY_ERROR_DESC = null;
	public static String BUSINESS_REASON = "Business Rules Returned";
	public static String REFUND_CEILING_RULES = "Refund Ceiling Rules";
	public static String RANDOM_FLAGGING = "Random Flagging";
	public static String EXECUTION_ID = "1";
	
	public static CardUpdateResponseDTO getResponseWithErrorDescription(){
		return new CardUpdateResponseDTO(ERROR_CODE,ERROR_DESC);
	}
	
	public static CardUpdateResponseDTO getResponseWithEmptyErrorDescription(){
		return new CardUpdateResponseDTO(ERROR_CODE,EMPTY_ERROR_DESC);
	}
	
	public static ApprovalLevelRequiredEnum getNoApprovalLevelRequired(){
		return ApprovalLevelRequiredEnum.NONE;
	}
	
	public static ApprovalLevelRequiredEnum getFirstStageApprovalLevelRequired(){
		return ApprovalLevelRequiredEnum.FIRST_STAGE;
	}
	
	public static ApprovalLevelRequiredEnum getSecondStageApprovalLevelRequired(){
		return ApprovalLevelRequiredEnum.SECOND_STAGE;
	}
	
	public static WorkflowItemDTO generateWorkflowItem(){
		WorkflowItemDTO workflowItem = new WorkflowItemDTO();
		RefundDetailDTO refundBean = new RefundDetailDTO(); 
		
		refundBean.setCardId(CARD_ID_1);
		refundBean.setRefundDepartment(RefundDepartmentEnum.OYSTER);
		refundBean.setRefundScenario(RefundScenarioEnum.FAILEDCARD);
		refundBean.setTotalRefundAmount(REFUND_AMOUNT_IN_PENCE_L);
		refundBean.setPaymentType(PaymentType.CHEQUE);
		refundBean.setRefundOriginatingApplication(ApplicationName.NEMO_TFL_INNOVATOR);
		
		workflowItem.setRefundDetails(refundBean);
		workflowItem.setCaseNumber(OYSTER_NUMBER_1);
		return workflowItem;
	}
}
