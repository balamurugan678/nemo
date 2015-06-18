package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getCCSGoodWillFirstStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getCCSGoodWillNoApproval;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getCCSGoodWillSecondStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getCCSMultipleGoodWillFirstStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getLUGoodWillFirstStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getLUGoodWillNoApproval;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getLUGoodWillSecondStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getOysterGoodwillFirstStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getOysterGoodwillNoApproval;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getOysterGoodwillSecondStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getOysterOtherFirstStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getOysterOtherNoApproval;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getOysterOtherSecondStage;
import static com.novacroft.nemo.test_support.WorkflowValueApprovalLimitsTestUtil.getOysterOtherSecondStageWithFirstStageGoodwill;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyListOf;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;

import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.tfl.common.constant.ApprovalLevelRequiredEnum;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkFlowValueApprovalLimitsServiceImplTest {

    private WorkflowValueApprovalLimitsServiceImpl mockWorkflowValueApprovalLimitsServiceImpl;
    private SystemParameterDataService mockSystemParameterService;
    private ContentDataService mockContentDataService;

    private WorkflowItemDTO mockWorkflowItem;
    private ContentDTO mockContentDTO;

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

    @Before
    public void setUp() {

        mockWorkflowValueApprovalLimitsServiceImpl = mock(WorkflowValueApprovalLimitsServiceImpl.class);
        mockSystemParameterService = mock(SystemParameterDataService.class);
        mockContentDataService = mock(ContentDataService.class);
        mockWorkflowValueApprovalLimitsServiceImpl.systemParameterService = mockSystemParameterService;
        mockWorkflowValueApprovalLimitsServiceImpl.contentDataService = mockContentDataService;

        mockWorkflowValueApprovalLimitsServiceImpl.oysterGoodwillNoApprovalRequiredLowerLimit = 0;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterGoodwillNoApprovalRequiredUpperLimit = 1500;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterGoodwillFirstStageApprovalLowerLimit = 1501;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterGoodwillFirstStageApprovalUpperLimit = 10000;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterGoodwillSecondStageApprovalLowerLimit = 10001;

        mockWorkflowValueApprovalLimitsServiceImpl.oysterOtherrefundsNoApprovalRequiredLowerLimit = 0;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterOtherrefundsNoApprovalRequiredUpperLimit = 12500;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterOtherrefundsFirstStageApprovalLowerLimit = 12501;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterOtherrefundsFirstStageApprovalUpperLimit = 100000;
        mockWorkflowValueApprovalLimitsServiceImpl.oysterOtherrefundsSecondStageApprovalLowerLimit = 100001;

        mockWorkflowValueApprovalLimitsServiceImpl.luGoodwillNoApprovalRequiredLowerLimit = 0;
        mockWorkflowValueApprovalLimitsServiceImpl.luGoodwillNoApprovalRequiredUpperLimit = 2000;
        mockWorkflowValueApprovalLimitsServiceImpl.luGoodwillFirstStageApprovalLowerLimit = 2001;
        mockWorkflowValueApprovalLimitsServiceImpl.luGoodwillFirstStageApprovalUpperLimit = 15000;
        mockWorkflowValueApprovalLimitsServiceImpl.luGoodwillSecondStageApprovalLowerLimit = 15001;

        mockWorkflowValueApprovalLimitsServiceImpl.ccsGoodwillNoApprovalRequiredLowerLimit = 0;
        mockWorkflowValueApprovalLimitsServiceImpl.ccsGoodwillNoApprovalRequiredUpperLimit = 5000;
        mockWorkflowValueApprovalLimitsServiceImpl.ccsGoodwillFirstStageApprovalLowerLimit = 5001;
        mockWorkflowValueApprovalLimitsServiceImpl.ccsGoodwillFirstStageApprovalUpperLimit = 15000;
        mockWorkflowValueApprovalLimitsServiceImpl.ccsGoodwillSecondStageApprovalLowerLimit = 15001;

        doCallRealMethod().when(mockWorkflowValueApprovalLimitsServiceImpl).checkApprovalThreshold(any(WorkflowItemDTO.class),
                        any(RefundDetailDTO.class));
        doCallRealMethod().when(mockWorkflowValueApprovalLimitsServiceImpl).addApprovalReasonIfNeeded(any(ApprovalLevelRequiredEnum.class),
                        any(WorkflowItemDTO.class));
        mockWorkflowItem = mock(WorkflowItemDTO.class);
        mockContentDTO = mock(ContentDTO.class);

        ReflectionTestUtils.setField(mockWorkflowValueApprovalLimitsServiceImpl, "RULE_BREACH_DESCRIPTION_MAXIMUM_LIMIT_TEXT", "");
    }

    @Test
    public void testInitialize() {

        SystemParameterDTO param = new SystemParameterDTO();
        param.setValue("1");

        doReturn(param).when(mockSystemParameterService).findByCode(OYSTER_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(OYSTER_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(OYSTER_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(OYSTER_OTHERREFUNDS_NO_APPROVALREQD_LOWER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(OYSTER_OTHERREFUNDS_NO_APPROVALREQD_UPPER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(OYSTER_OTHERREFUNDS_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(LU_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(LU_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(LU_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(CCS_GOODWILL_NO_APPROVALREQD_LOWER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(CCS_GOODWILL_NO_APPROVALREQD_UPPER_LIMIT);
        doReturn(param).when(mockSystemParameterService).findByCode(CCS_GOODWILL_FIRSTSTAGEAPPROVAL_UPPER_LIMIT);
        doReturn(mockContentDTO).when(mockContentDataService).findByLocaleAndCode(anyString(), anyString());
        doCallRealMethod().when(mockWorkflowValueApprovalLimitsServiceImpl).initialize();

        mockWorkflowValueApprovalLimitsServiceImpl.initialize();
        Mockito.verify(mockSystemParameterService, Mockito.atLeast(12)).findByCode(anyString());
        verify(mockContentDataService).findByLocaleAndCode(anyString(), anyString());
        verify(mockContentDTO).getContent();
    }

    @Test
    public void TestOysterGoodwillNoApprovalrequired() {
        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getOysterGoodwillNoApproval());
        assertEquals(ApprovalLevelRequiredEnum.NONE, checkApprovalThreshold);
        verify(mockWorkflowItem, never()).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestOysterGoodwillFirstStageApprovalrequired() {
        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getOysterGoodwillFirstStage());
        assertEquals(checkApprovalThreshold, ApprovalLevelRequiredEnum.FIRST_STAGE);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestOysterGoodwillSecondStageApprovalrequired() {
        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getOysterGoodwillSecondStage());
        assertEquals(checkApprovalThreshold, ApprovalLevelRequiredEnum.SECOND_STAGE);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestOysterOtherRefundsNoApprovalrequired() {
        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getOysterOtherNoApproval());
        assertEquals( ApprovalLevelRequiredEnum.NONE,checkApprovalThreshold);
        verify(mockWorkflowItem, never()).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestOysterOtherRefundsFirstStageApprovalrequired() {
        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getOysterOtherFirstStage());
        assertEquals(ApprovalLevelRequiredEnum.FIRST_STAGE, checkApprovalThreshold);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestOysterOtherRefundsSecondStageApprovalrequired() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getOysterOtherSecondStage());
        assertEquals(checkApprovalThreshold, ApprovalLevelRequiredEnum.SECOND_STAGE);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestOysterOtherRefundsSecondStageApprovalRequiredWithFirstStageGoodwill() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(mockWorkflowItem,
                        getOysterOtherSecondStageWithFirstStageGoodwill());
        assertEquals(ApprovalLevelRequiredEnum.SECOND_STAGE, checkApprovalThreshold);
        verify(mockWorkflowItem, atLeastOnce()).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestLUGoodwillNoApprovalrequired() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getLUGoodWillNoApproval());
        assertEquals(checkApprovalThreshold, ApprovalLevelRequiredEnum.NONE);
        verify(mockWorkflowItem, never()).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestLUGoodwillFirstStageApprovalrequired() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getLUGoodWillFirstStage());
        assertEquals( ApprovalLevelRequiredEnum.FIRST_STAGE, checkApprovalThreshold);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestLUGoodwillSecondStageApprovalrequired() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getLUGoodWillSecondStage());
        assertEquals(ApprovalLevelRequiredEnum.SECOND_STAGE, checkApprovalThreshold);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestCCSGoodwillNoApprovalrequired() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getCCSGoodWillNoApproval());
        assertEquals(ApprovalLevelRequiredEnum.NONE , checkApprovalThreshold );
        verify(mockWorkflowItem, never()).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestCCSGoodwillFirstStageApprovalrequired() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getCCSGoodWillFirstStage());
        assertEquals(ApprovalLevelRequiredEnum.FIRST_STAGE,checkApprovalThreshold );
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }

    @Test
    public void TestCCSGoodwillSecondStageApprovalrequired() {

        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                        mockWorkflowItem, getCCSGoodWillSecondStage());
        assertEquals( ApprovalLevelRequiredEnum.SECOND_STAGE, checkApprovalThreshold);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }
    
    @Test
    public void TestMultipleCCSGoodwillFirstStageApprovalrequired() {
        
        ApprovalLevelRequiredEnum checkApprovalThreshold = mockWorkflowValueApprovalLimitsServiceImpl.checkApprovalThreshold(
                mockWorkflowItem, getCCSMultipleGoodWillFirstStage());
        assertEquals(ApprovalLevelRequiredEnum.FIRST_STAGE, checkApprovalThreshold);
        verify(mockWorkflowItem).setApprovalReasons(anyListOf(String.class));
    }
}