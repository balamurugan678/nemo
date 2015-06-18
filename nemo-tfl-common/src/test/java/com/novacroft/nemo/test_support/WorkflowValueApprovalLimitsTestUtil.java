package com.novacroft.nemo.test_support;

import java.util.ArrayList;

import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkflowValueApprovalLimitsTestUtil {
    private static final String GOODWILL_LABEL = "Goodwill";
    public static final Long OYSTER_GOODWILL_AMOUNT_NO_APPROVAL = 100L;
    public static final Long OYSTER_GOODWILL_AMOUNT_FIRST_STAGE = 1501L;
    public static final Long OYSTER_GOODWILL_AMOUNT_SECOND_STAGE = 10200L;

    public static final Long OYSTER_OTHER_AMOUNT_NO_APPROVAL = 12400L;
    public static final Long OYSTER_OTHER_AMOUNT_FIRST_STAGE = 12501L;
    public static final Long OYSTER_OTHER_AMOUNT_SECOND_STAGE = 100001L;

    public static final Long LU_GOODWILL_AMOUNT_NO_APPROVAL = 2000L;
    public static final Long LU_GOODWILL_AMOUNT_FIRST_STAGE = 2001L;
    public static final Long LU_GOODWILL_AMOUNT_SECOND_STAGE = 15001L;

    public static final Long CCS_GOODWILL_AMOUNT_NO_APPROVAL = 5000L;
    public static final Long CCS_GOODWILL_AMOUNT_FIRST_STAGE = 5001L;
    public static final Long CCS_GOODWILL_AMOUNT_SECOND_STAGE = 15001L;

    public static RefundDetailDTO getOysterGoodwillNoApproval() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.GOODWILL, OYSTER_GOODWILL_AMOUNT_NO_APPROVAL);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getOysterGoodwillFirstStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.GOODWILL, OYSTER_GOODWILL_AMOUNT_FIRST_STAGE);
        addGoodwillItem(refundDetails, OYSTER_GOODWILL_AMOUNT_FIRST_STAGE);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getOysterGoodwillSecondStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.GOODWILL, OYSTER_GOODWILL_AMOUNT_SECOND_STAGE);
        addRefundItem(refundDetails);
        addGoodwillItem(refundDetails, OYSTER_GOODWILL_AMOUNT_SECOND_STAGE);
        
        return refundDetails;
    }

    public static RefundDetailDTO getOysterOtherNoApproval() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.OTHER, OYSTER_OTHER_AMOUNT_NO_APPROVAL);
        
        return refundDetails;
    }

    public static RefundDetailDTO getOysterOtherFirstStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.OTHER, OYSTER_OTHER_AMOUNT_FIRST_STAGE);
       
        return refundDetails;
    }

    public static RefundDetailDTO getOysterOtherSecondStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.OTHER, OYSTER_OTHER_AMOUNT_SECOND_STAGE);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getOysterOtherSecondStageWithFirstStageGoodwill() {
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundItems(new ArrayList<RefundItemCmd>());
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        refundDetails.setRefundDepartment(RefundDepartmentEnum.OYSTER);
        refundDetails.setRefundScenario(RefundScenarioEnum.FAILEDCARD);
        addGoodwillItem(refundDetails, OYSTER_GOODWILL_AMOUNT_FIRST_STAGE);
        addRefundItem(refundDetails);
        refundDetails.setTotalRefundAmount(OYSTER_OTHER_AMOUNT_SECOND_STAGE);
        return refundDetails;
    }

    public static RefundDetailDTO getLUGoodWillNoApproval() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.LU, RefundScenarioEnum.GOODWILL, LU_GOODWILL_AMOUNT_NO_APPROVAL);
        addGoodwillItem(refundDetails, OYSTER_GOODWILL_AMOUNT_NO_APPROVAL);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getLUGoodWillFirstStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.LU, RefundScenarioEnum.GOODWILL, LU_GOODWILL_AMOUNT_FIRST_STAGE);
        addGoodwillItem(refundDetails, LU_GOODWILL_AMOUNT_FIRST_STAGE);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getLUGoodWillSecondStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.LU, RefundScenarioEnum.GOODWILL, LU_GOODWILL_AMOUNT_SECOND_STAGE);
        addGoodwillItem(refundDetails, LU_GOODWILL_AMOUNT_SECOND_STAGE);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getCCSGoodWillNoApproval() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.CCS, RefundScenarioEnum.GOODWILL, CCS_GOODWILL_AMOUNT_NO_APPROVAL);
        addGoodwillItem(refundDetails, CCS_GOODWILL_AMOUNT_NO_APPROVAL);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getCCSGoodWillFirstStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.CCS, RefundScenarioEnum.GOODWILL, CCS_GOODWILL_AMOUNT_FIRST_STAGE);
        addGoodwillItem(refundDetails, CCS_GOODWILL_AMOUNT_FIRST_STAGE);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getCCSMultipleGoodWillFirstStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.CCS, RefundScenarioEnum.GOODWILL, 100100L);
        addGoodwillItem(refundDetails, 5000L);
        addGoodwillItem(refundDetails, 5000L);
        addGoodwillItem(refundDetails, 100L);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    public static RefundDetailDTO getCCSGoodWillSecondStage() {
        RefundDetailDTO refundDetails = initializeRefundDetail(RefundDepartmentEnum.CCS, RefundScenarioEnum.GOODWILL, CCS_GOODWILL_AMOUNT_SECOND_STAGE);
        addGoodwillItem(refundDetails, CCS_GOODWILL_AMOUNT_SECOND_STAGE);
        addRefundItem(refundDetails);

        return refundDetails;
    }

    private static RefundDetailDTO initializeRefundDetail(RefundDepartmentEnum dept, RefundScenarioEnum scenario, Long amount) {
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundItems(new ArrayList<RefundItemCmd>());
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());

        refundDetails.setRefundDepartment(dept);
        refundDetails.setRefundScenario(scenario);
        refundDetails.setTotalRefundAmount(amount);

        return refundDetails;
    }

    private static void addGoodwillItem(RefundDetailDTO refundDetails, Long goodwillAmount) {
        GoodwillPaymentItemDTO goodwillPayment = new GoodwillPaymentItemDTO();
        goodwillPayment.setPrice(goodwillAmount.intValue());
        goodwillPayment.setGoodwillReasonDTO(GoodwillReasonTestUtil.getGoodwillReasonDTO1());
        goodwillPayment.setGoodwillOtherText(GoodwillReasonTestUtil.GOODWILL_REASON_OTHER);
        GoodwillPaymentItemCmd goodwillPayMentCmd = new GoodwillPaymentItemCmd(goodwillPayment);
        refundDetails.getGoodwillItems().add(goodwillPayMentCmd);
    }

    private static void addRefundItem(RefundDetailDTO refundDetails) {
        refundDetails.setRefundItems(RefundWorkflowTestUtil.getRefundItemCmdList());
    }

}
