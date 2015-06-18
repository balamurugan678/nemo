package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.test_support.WebAccountTestUtil.PASSWORD_1;
import static com.novacroft.nemo.test_support.WebAccountTestUtil.USERNAME_1;

import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;

/**
 * Utilities for tests that use LoginCmd
 */
public class RefundScenarioHotListReasonTypeTestUtil {

    public static RefundScenarioHotListReasonTypeDTO getRefundScenarioHotListReasonTypeDTO1() {
        return getRefundScenarioHotListReasonTypeDTO(USERNAME_1, PASSWORD_1);
    }

    public static RefundScenarioHotListReasonTypeDTO getRefundScenarioHotListReasonTypeDTO(String username, String password) {
    	RefundScenarioHotListReasonTypeDTO refundScenarioHotListReasonTypeDTO = new RefundScenarioHotListReasonTypeDTO();
    	refundScenarioHotListReasonTypeDTO.setHotlistReasonDTO(HotlistReasonTestUtil.getHotlistReasonDTO3());
        return refundScenarioHotListReasonTypeDTO;
    }
}
