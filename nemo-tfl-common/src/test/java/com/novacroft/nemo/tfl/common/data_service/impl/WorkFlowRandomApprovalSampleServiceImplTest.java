package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.test_support.RefundWorkflowTestUtil.getCardGoodwillPaymentItem;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import com.novacroft.nemo.common.application_service.UserService;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.domain.User;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.test_support.AddressTestUtil;
import com.novacroft.nemo.tfl.common.application_service.SecurityService;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.RandomApprovalRulesDataService;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.RandomApprovalSampleThresholdRuleDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkFlowRandomApprovalSampleServiceImplTest {

    private WorkflowRandomApprovalSampleServiceImpl mockWorkFlowRandomApprovalLimitsServiceImpl;
    private SystemParameterDataService mockSystemParameterDataService;
    private RandomApprovalRulesDataService mockRulesService;
    private List<RandomApprovalSampleThresholdRuleDTO> mockExampleTeamAndOrganisationList;
    private RandomApprovalSampleThresholdRuleDTO mockedRule;
    private WorkflowItemDTO mockWorkflowItemDTO;
    private ContentDataService mockContentService;
    private ContentDTO mockContent;
    private SecurityService mockSecurityService;
    private UserService mockUserService;
    private User mockUser;
    private List<RandomApprovalSampleThresholdRuleDTO> randomSampleRulesList;
    private List<RandomApprovalSampleThresholdRuleDTO> exampleTeamAndOrganisationList;
    private ArrayList<RandomApprovalSampleThresholdRuleDTO> randomApprovalSampleThresholdsList;

    private static Integer DEFAULT_BASE_VALUE = 0;
    private static Integer DEFAULT_CEILING_VALUE = 100000000;
    private static Integer FIXED_RANDOM_VALUE = 2;

    @Before
    public void setUp() {

        mockWorkFlowRandomApprovalLimitsServiceImpl = mock(WorkflowRandomApprovalSampleServiceImpl.class);
        mockSystemParameterDataService = mock(SystemParameterDataService.class);
        mockRulesService = mock(RandomApprovalRulesDataService.class);
        mockedRule = mock(RandomApprovalSampleThresholdRuleDTO.class);
        mockExampleTeamAndOrganisationList = Arrays.asList(mockedRule, mockedRule, mockedRule);
        mockWorkflowItemDTO = mock(WorkflowItemDTO.class);
        mockContentService = mock(ContentDataService.class);
        mockContent = mock(ContentDTO.class);
        mockSecurityService = mock(SecurityService.class);
        mockUserService = mock(UserService.class);
        mockUser = mock(User.class);

        randomSampleRulesList = new ArrayList<RandomApprovalSampleThresholdRuleDTO>();
        exampleTeamAndOrganisationList = new ArrayList<RandomApprovalSampleThresholdRuleDTO>();

        exampleTeamAndOrganisationList.add(new RandomApprovalSampleThresholdRuleDTO("TEAM", "ORG"));

        randomSampleRulesList.add(new RandomApprovalSampleThresholdRuleDTO(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.GOODWILL, null,
                        DEFAULT_BASE_VALUE, 1500, 5));
        randomSampleRulesList.add(new RandomApprovalSampleThresholdRuleDTO(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.OTHER, null, 1, 5000, 5));
        randomSampleRulesList.add(new RandomApprovalSampleThresholdRuleDTO(RefundDepartmentEnum.OYSTER, RefundScenarioEnum.OTHER, null, 5001, 12500,
                        10));
        randomSampleRulesList.add(new RandomApprovalSampleThresholdRuleDTO(RefundDepartmentEnum.LU, RefundScenarioEnum.GOODWILL, null,
                        DEFAULT_BASE_VALUE, DEFAULT_CEILING_VALUE, 5));
        randomSampleRulesList.add(new RandomApprovalSampleThresholdRuleDTO(RefundDepartmentEnum.OYSTER, null, RefundCalculationBasis.SPECIAL,
                        DEFAULT_BASE_VALUE, DEFAULT_CEILING_VALUE, 100));
        randomSampleRulesList.add(new RandomApprovalSampleThresholdRuleDTO(RefundDepartmentEnum.CCS, RefundScenarioEnum.GOODWILL, null,
                        DEFAULT_BASE_VALUE, DEFAULT_CEILING_VALUE, 5));

        mockWorkFlowRandomApprovalLimitsServiceImpl.randomSampleRulesList = randomSampleRulesList;
        mockWorkFlowRandomApprovalLimitsServiceImpl.rand = new Random(FIXED_RANDOM_VALUE);
        mockWorkFlowRandomApprovalLimitsServiceImpl.rulesService = mockRulesService;
        mockWorkFlowRandomApprovalLimitsServiceImpl.content = mockContentService;
        mockWorkFlowRandomApprovalLimitsServiceImpl.securityService = mockSecurityService;
        mockWorkFlowRandomApprovalLimitsServiceImpl.userService = mockUserService;
        mockWorkFlowRandomApprovalLimitsServiceImpl.randomApprovalSampleThresholdsList = new ArrayList<RandomApprovalSampleThresholdRuleDTO>();
        mockWorkFlowRandomApprovalLimitsServiceImpl.exampleTeamAndOrganisationList = exampleTeamAndOrganisationList;
        mockWorkFlowRandomApprovalLimitsServiceImpl.rulePercentageThreshold = 0;

    }

    @Test
    public void testForOysterGoodwill() {
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.OYSTER);
        refundDetails.setRefundScenario(RefundScenarioEnum.GOODWILL);
        refundDetails.setTotalRefundAmount(500L);
        refundDetails.setGoodwillItems(getCardGoodwillPaymentItem());
        workflow.setRefundDetails(refundDetails);

        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).processForRandomSampleFlagging(any(WorkflowItemDTO.class));

        List<String> result = mockWorkFlowRandomApprovalLimitsServiceImpl.processForRandomSampleFlagging(workflow);
        assert (result.size() == 1);

    }

    @Test
    public void testForOysterOtherLowValue() {
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.OYSTER);
        refundDetails.setRefundScenario(RefundScenarioEnum.OTHER);
        refundDetails.setTotalRefundAmount(500L);
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        workflow.setRefundDetails(refundDetails);

        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).processForRandomSampleFlagging(any(WorkflowItemDTO.class));

        List<String> result = mockWorkFlowRandomApprovalLimitsServiceImpl.processForRandomSampleFlagging(workflow);
        assert (result.size() == 1);

    }

    @Test
    public void testForOysterOtherHighValue() {
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.OYSTER);
        refundDetails.setRefundScenario(RefundScenarioEnum.OTHER);
        refundDetails.setTotalRefundAmount(5001L);
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        workflow.setRefundDetails(refundDetails);

        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).processForRandomSampleFlagging(any(WorkflowItemDTO.class));

        List<String> result = mockWorkFlowRandomApprovalLimitsServiceImpl.processForRandomSampleFlagging(workflow);
        assert (result.size() == 1);

    }

    @Test
    public void testForLUGoodwill() {
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.LU);
        refundDetails.setRefundScenario(RefundScenarioEnum.GOODWILL);
        refundDetails.setTotalRefundAmount(5001L);
        refundDetails.setGoodwillItems(getCardGoodwillPaymentItem());
        workflow.setRefundDetails(refundDetails);

        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).processForRandomSampleFlagging(any(WorkflowItemDTO.class));

        List<String> result = mockWorkFlowRandomApprovalLimitsServiceImpl.processForRandomSampleFlagging(workflow);
        assert (result.size() == 1);

    }

    @Test
    public void testForOysterSpecial() {
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.OYSTER);
        refundDetails.setRefundScenario(null);
        refundDetails.setRefundBasis(RefundCalculationBasis.SPECIAL);
        refundDetails.setTotalRefundAmount(5001L);
        refundDetails.setGoodwillItems(new ArrayList<GoodwillPaymentItemCmd>());
        workflow.setRefundDetails(refundDetails);

        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).processForRandomSampleFlagging(any(WorkflowItemDTO.class));

        List<String> result = mockWorkFlowRandomApprovalLimitsServiceImpl.processForRandomSampleFlagging(workflow);
        assert (result.size() == 1);

    }

    @Test
    public void testForCCSGoodwill() {
        WorkflowItemDTO workflow = new WorkflowItemDTO();
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setRefundDepartment(RefundDepartmentEnum.CCS);
        refundDetails.setRefundScenario(RefundScenarioEnum.GOODWILL);
        refundDetails.setTotalRefundAmount(5001L);
        refundDetails.setGoodwillItems(getCardGoodwillPaymentItem());
        workflow.setRefundDetails(refundDetails);

        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).processForRandomSampleFlagging(any(WorkflowItemDTO.class));

        List<String> result = mockWorkFlowRandomApprovalLimitsServiceImpl.processForRandomSampleFlagging(workflow);
        assert (result.size() == 1);

    }

    @Test
    public void realtest1() {

        AddressDTO address = AddressTestUtil.getTestAddressDTO1();
        CustomerDTO customer = new CustomerDTO();
        customer.setFirstName("Leo");
        customer.setLastName("Horn");
        customer.setInitials("D");
        customer.setTitle("Capt");
        CustomerDTO alternativeRefundPayee = new CustomerDTO();
        alternativeRefundPayee.setFirstName("Horatio");
        alternativeRefundPayee.setLastName("Hornblower");
        alternativeRefundPayee.setInitials("");
        alternativeRefundPayee.setTitle("Capt");

        RefundDetailDTO refundDetail = new RefundDetailDTO();
        refundDetail.setName("TestUser");
        refundDetail.setCardId(4999L);
        refundDetail.setTicketCorrupt(Boolean.TRUE);
        refundDetail.setAgentInterventionRequired(Boolean.TRUE);
        refundDetail.setSupervisorResolved(Boolean.TRUE);
        refundDetail.setChargeAdminFee(Boolean.TRUE);
        refundDetail.setGoodwillPaymentRequired(Boolean.TRUE);
        refundDetail.setCaseOwnerRequiresApproval(Boolean.TRUE);
        refundDetail.setAbnormalRefundTrends(Boolean.TRUE);
        refundDetail.setApprovalRequired(Boolean.TRUE);
        refundDetail.setRefundRejected(Boolean.TRUE);
        refundDetail.setBacsChequePaymentRequired(Boolean.FALSE);
        refundDetail.setAdhocLoadRequired(Boolean.TRUE);
        refundDetail.setCardPaymentRequired(Boolean.FALSE);

        refundDetail.setTotalTicketPrice(13725L);
        refundDetail.setTotalRefundAmount(5501L);
        refundDetail.setRefundDepartment(RefundDepartmentEnum.OYSTER);
        refundDetail.setRefundScenario(RefundScenarioEnum.LOST);
        refundDetail.setRefundBasis(RefundCalculationBasis.TRADE_UP);
        refundDetail.setRefundDate(new DateTime());
        refundDetail.setWebAccountId(15001L);
        refundDetail.setAddress(address);
        refundDetail.setCustomer(customer);
        refundDetail.setAlternativeRefundPayee(alternativeRefundPayee);
        refundDetail.setTicketDescription("Zones 1 to 3 Three month travel card");

        refundDetail.setFirstStageApprovalGiven(Boolean.TRUE);
        refundDetail.setSecondStageApprovalRequired(Boolean.TRUE);
        refundDetail.setSecondStageApprovalGiven(Boolean.FALSE);
        refundDetail.setAcceptedByGate(Boolean.FALSE);
        refundDetail.setFirstFailure(Boolean.FALSE);
        refundDetail.setSupervisorAssignsToQueue(Boolean.FALSE);

        refundDetail.setGoodwillItems(getCardGoodwillPaymentItem());

        WorkflowItemDTO workflowItem = new WorkflowItemDTO();
        workflowItem.setRefundDetails(refundDetail);

        Refund refund = new Refund();
        refund.setRefundableDays(1);
        refund.setRefundAmount(8045L);
        refundDetail.setRefundEntity(refund);

        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).processForRandomSampleFlagging(any(WorkflowItemDTO.class));

        List<String> result = mockWorkFlowRandomApprovalLimitsServiceImpl.processForRandomSampleFlagging(workflowItem);
        assert (result.size() == 1);

    }

    @Test
    public void testGetRulesForLoggedInUser() {

        doReturn(mockExampleTeamAndOrganisationList).when(mockRulesService).findOrganisationAndTeamById(anyString());
        doReturn(mockContent).when(mockContentService).findByLocaleAndCode(anyString(), anyString());
        doReturn("MESSAGE").when(mockContent).getContent();
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).getRulesForLoggedInUser(any(WorkflowItemDTO.class));
        doReturn("anyId").when(mockSecurityService).getLoggedInUsername();
        doReturn(mockUser).when(mockUserService).findUserById(anyString());
        doReturn(randomSampleRulesList).when(mockRulesService).findAllRandomApprovalSampleThresholdsByOrganisationAndTeam(anyString(), anyString());
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).findDefaultRules();
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).findRulesByOrganisation();
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).findRulesByOrganisationAndTeam();

        mockWorkFlowRandomApprovalLimitsServiceImpl.getRulesForLoggedInUser(mockWorkflowItemDTO);
        assert (mockWorkFlowRandomApprovalLimitsServiceImpl.FLAGGED_FOR_RANDOM_APPROVAL_MESSAGE.equals("MESSAGE"));

    }

    @Test
    public void testGetRulesForAnonymousUser() {

        doReturn(mockExampleTeamAndOrganisationList).when(mockRulesService).findOrganisationAndTeamById(anyString());
        doReturn(mockContent).when(mockContentService).findByLocaleAndCode(anyString(), anyString());
        doReturn("MESSAGE").when(mockContent).getContent();
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).getRulesForLoggedInUser(any(WorkflowItemDTO.class));
        doReturn("").when(mockSecurityService).getLoggedInUsername();
        doReturn(mockUser).when(mockUserService).findUserById(anyString());
        doReturn(new ArrayList<RandomApprovalSampleThresholdRuleDTO>()).when(mockRulesService)
                        .findAllRandomApprovalSampleThresholdsByOrganisationAndTeam("", "DEFAULT");
        doReturn(randomSampleRulesList).when(mockRulesService).findAllRandomApprovalSampleThresholdsByOrganisationAndTeam("DEFAULT", "DEFAULT");
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).findDefaultRules();
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).findRulesByOrganisation();
        doCallRealMethod().when(mockWorkFlowRandomApprovalLimitsServiceImpl).findRulesByOrganisationAndTeam();

        mockWorkFlowRandomApprovalLimitsServiceImpl.getRulesForLoggedInUser(mockWorkflowItemDTO);
        assert (mockWorkFlowRandomApprovalLimitsServiceImpl.FLAGGED_FOR_RANDOM_APPROVAL_MESSAGE.equals("MESSAGE"));

    }
}