package com.novacroft.nemo.tfl.innovator.controller.workflow;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.RefundConstants;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@RunWith(Parameterized.class)
public class ViewWorkflowItemControllerRefundScenarioTest {
    private RefundScenarioEnum testRefundScenario;
    private String expectedDisplayString;
    private String expectedCartType;
    
    
    private ViewWorkflowItemController controller;
    
    @Before
    public void setUp() {
        controller = new ViewWorkflowItemController();
    }
    
    public ViewWorkflowItemControllerRefundScenarioTest(RefundScenarioEnum testRefundScenario, 
                    String expectedDisplayString, String expectedCartType) {
        this.testRefundScenario = testRefundScenario;
        this.expectedDisplayString = expectedDisplayString;
        this.expectedCartType = expectedCartType;
    }
    
    
    @Parameters
    public static Collection<?> parameterizedTestData() {
        return Arrays.asList(new Object[][] {
                {RefundScenarioEnum.FAILEDCARD, RefundConstants.FAILED_CARD_REFUND_DISPLAY, CartType.FAILED_CARD_REFUND.code()},
                {RefundScenarioEnum.DESTROYED, RefundConstants.DESTROYED_CARD_REFUND_DISPLAY, CartType.DESTROYED_CARD_REFUND.code()},
                {RefundScenarioEnum.LOST, RefundConstants.LOST_REFUND_DISPLAY, CartType.LOST_REFUND.code()},
                {RefundScenarioEnum.STOLEN, RefundConstants.STOLEN_REFUND_DISPLAY, CartType.STOLEN_REFUND.code()},
                {RefundScenarioEnum.CANCEL_AND_SURRENDER, RefundConstants.CANCEL_AND_SURRENDER_REFUND_DISPLAY, CartType.CANCEL_SURRENDER_REFUND.code()},
                {RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND, RefundConstants.ANONYMOUS_GOODWILL_REFUND_DISPLAY, CartType.ANONYMOUS_GOODWILL_REFUND.code()},
                {RefundScenarioEnum.STANDALONE_GOODWILL_REFUND, RefundConstants.STANDALONE_GOODWILL_REFUND_DISPLAY, CartType.STANDALONE_GOODWILL_REFUND.code()},
                {RefundScenarioEnum.OTHER, StringUtil.EMPTY_STRING, null},
        });
    }
    
    @Test
    public void testGetCartTypeCode() {
        assertTrue(StringUtils.equals(expectedCartType, controller.getCartTypeCode(testRefundScenario)));
    }
    
    @Test
    public void testValidateRefundScenarioSubTypeForWorkFlowDisplay() {
        WorkflowCmd testCmd = createTestWorkflowCmd();
        controller.validateRefundScenarioSubTypeForWorkFlowDisplay(testCmd);
        assertEquals(expectedDisplayString, testCmd.getRefundScenarioSubType());
    }
    
    private WorkflowCmd createTestWorkflowCmd() {
        RefundDetailDTO testRefundDetailDTO = new RefundDetailDTO();
        testRefundDetailDTO.setRefundScenario(testRefundScenario);
        
        WorkflowItemDTO testItemDTO = new WorkflowItemDTO();
        testItemDTO.setRefundDetails(testRefundDetailDTO);
        
        WorkflowCmd testCmd = new WorkflowCmd();
        testCmd.setWorkflowItem(testItemDTO);
        return testCmd;
    }
    
}
