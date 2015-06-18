package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.HotlistReasonTestUtil.getTestHotlistReason1;

import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.domain.RefundScenarioHotListReasonType;

public class RefundScenarioHotListReasonTypeTestUtil {
    
    public static final Long REFUND_SCENARIO_TYPE_ID_1 = 14L; 

    public static RefundScenarioHotListReasonType getTestRefundScenarioHotListReasonType1() {
        RefundScenarioHotListReasonType hotlistReasonType = new RefundScenarioHotListReasonType();
        hotlistReasonType.setId(REFUND_SCENARIO_TYPE_ID_1);
        hotlistReasonType.setRefundScenarioEnum(RefundScenarioEnum.OTHER);
        hotlistReasonType.setHotListReaon(getTestHotlistReason1());
        return hotlistReasonType;
    }
}
