package com.novacroft.nemo.tfl.innovator.workflow;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.BUSINESS_REASON;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.EXECUTION_ID;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.RANDOM_FLAGGING;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.REFUND_CEILING_RULES;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.generateWorkflowItem;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.getFirstStageApprovalLevelRequired;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.getNoApprovalLevelRequired;
import static com.novacroft.nemo.test_support.WorkflowActionsTestUtil.getSecondStageApprovalLevelRequired;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.novacroft.nemo.common.application_service.SystemParameterService;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.ApprovalTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.tfl.common.application_service.HotlistCardService;
import com.novacroft.nemo.tfl.common.application_service.RefundPaymentService;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.converter.impl.RefundToWorkflowConverter;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CardUpdateRequestDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PayAsYouGoDataServiceTest;
import com.novacroft.nemo.tfl.common.data_service.RefundScenarioHotListReasonTypeDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowBusinessRulesService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowRandomApprovalSampleService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowRefundCeilingLimitsService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowValueApprovalLimitsService;
import com.novacroft.nemo.tfl.common.data_service.impl.CardDataServiceImpl;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.HotlistReasonDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundScenarioHotListReasonTypeDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkflowActionsTest {
	static final Logger logger = LoggerFactory.getLogger(PayAsYouGoDataServiceTest.class);

    private static final String TIME_CYCLE = "1M";

    private static final String EXPECTED_TIME_CYCLE = "R/PT1M";
    private static final String EXPECTED_TIME_CYCLE_WITHOUT_REPEAT = "PT1M";

	private WorkflowItemDTO workflowItem;
	private CartCmdImpl mockCartCmdImpl;
	private WorkflowActions mockWorkflowActions;
	private WorkflowItemDTO mockWorkflowItem;
	private RefundDetailDTO mockRefundDetails;
	private TaskService mockTaskService;
	private RefundScenarioHotListReasonTypeDataService mockRefundScenarioHotListReasonTypeDataService;
	private HotlistCardService mockHotlistCardService;
	private CardDTO mockCard;
	private CardDataService mockCardService;
	private RefundScenarioHotListReasonTypeDTO mockRefundScenarioHotListReasonType;
	private HotlistReasonDTO mockHotListReason;
	private RefundPaymentService mockRefundService;
	private RefundToWorkflowConverter mockConverter;
	private CardUpdateRequestDataService mockCubicCardService;
	private OrderDataService mockOrderService;
	private OrderDTO mockOrder;
	private List<OrderDTO> mockOrderList;
	private List<String> mockBusinessRules;
	private List<String> mockRefundCeilingRules;
	private List<String> mockflaggingRules;
	private RuntimeService mockRuntimeService;
	private WorkflowValueApprovalLimitsService mockValueApprovalLimitsService;
	private WorkflowBusinessRulesService mockBusinessRuleService;
	private WorkflowRefundCeilingLimitsService mockRefundCeilingService;
	private WorkflowRandomApprovalSampleService mockRandomApprovalService;
    private HistoryService mockHistoryService;
    private HistoricTaskInstanceQuery mockHistoricTaskInstanceQuery;
    private List<HistoricTaskInstance> historicTaskInstanceList;
    private SystemParameterService mockSystemParameterService;
	
	@Before
	public void setUp() {
		workflowItem = generateWorkflowItem();
		
		mockWorkflowActions = mock(WorkflowActions.class);
		mockCartCmdImpl = mock(CartCmdImpl.class);
		mockWorkflowItem = mock(WorkflowItemDTO.class);
		mockRefundDetails = mock(RefundDetailDTO.class);
		mockTaskService = mock(TaskService.class);
		mockRefundScenarioHotListReasonTypeDataService = mock(RefundScenarioHotListReasonTypeDataService.class);
		mockHotlistCardService = mock(HotlistCardService.class);
		mockCard = mock(CardDTO.class);
		mockCardService = mock(CardDataServiceImpl.class);
		mockRefundScenarioHotListReasonType = mock(RefundScenarioHotListReasonTypeDTO.class);
		mockHotListReason = mock(HotlistReasonDTO.class);
		mockRefundService = mock(RefundPaymentService.class);
		mockConverter = mock(RefundToWorkflowConverter.class);
		mockOrderService = mock(OrderDataService.class);
		mockOrderList = new ArrayList<OrderDTO>();
		mockOrderList.add(mockOrder);
		mockBusinessRules = new ArrayList<String>();
		
		mockRefundCeilingRules = new ArrayList<String>();
		
		mockflaggingRules = new ArrayList<String>();
		
		mockCubicCardService = mock(CardUpdateRequestDataService.class);
		mockRuntimeService = mock(RuntimeService.class);
		mockValueApprovalLimitsService = mock(WorkflowValueApprovalLimitsService.class);
		mockBusinessRuleService = mock(WorkflowBusinessRulesService.class);
		mockRefundCeilingService = mock(WorkflowRefundCeilingLimitsService.class);
		mockRandomApprovalService = mock(WorkflowRandomApprovalSampleService.class);
        mockHistoryService = mock(HistoryService.class);
        mockSystemParameterService = mock(SystemParameterService.class);

        mockHistoricTaskInstanceQuery = mock(HistoricTaskInstanceQuery.class);
		
		mockWorkflowActions.taskService = mockTaskService;
		mockWorkflowActions.refundScenarioHotListReasonTypeDataService = mockRefundScenarioHotListReasonTypeDataService;
		mockWorkflowActions.hotlistCardService = mockHotlistCardService;
		mockWorkflowActions.cardService = mockCardService;
		mockWorkflowActions.refundPaymentService = mockRefundService;
		mockWorkflowActions.converter = mockConverter;
		mockWorkflowActions.orderService = mockOrderService;
		mockWorkflowActions.cubicCardService = mockCubicCardService;
		mockWorkflowActions.runtimeService = mockRuntimeService;
		mockWorkflowActions.valueApprovalLimitsService = mockValueApprovalLimitsService;
		mockWorkflowActions.businessRuleService = mockBusinessRuleService;
		mockWorkflowActions.refundCeilingService = mockRefundCeilingService;
		mockWorkflowActions.randomApprovalService = mockRandomApprovalService;
        mockWorkflowActions.historyService = mockHistoryService;
        mockWorkflowActions.systemParameterService = mockSystemParameterService;

		doNothing().when(mockRuntimeService).setVariable(anyString(), anyString(), anyObject());
		when(mockRefundScenarioHotListReasonType.getHotlistReasonDTO()).thenReturn(mockHotListReason);
		when(mockOrderService.findByExample(any(Order.class))).thenReturn(mockOrderList);
		when(mockWorkflowItem.getRefundDetails()).thenReturn(mockRefundDetails);

        historicTaskInstanceList = new ArrayList<HistoricTaskInstance>();
        when(mockHistoryService.createHistoricTaskInstanceQuery()).thenReturn(mockHistoricTaskInstanceQuery);
        when(mockHistoricTaskInstanceQuery.processVariableValueEquals(anyString(), anyString())).thenReturn(mockHistoricTaskInstanceQuery);
        when(mockHistoricTaskInstanceQuery.orderByHistoricTaskInstanceEndTime()).thenReturn(mockHistoricTaskInstanceQuery);
        when(mockHistoricTaskInstanceQuery.desc()).thenReturn(mockHistoricTaskInstanceQuery);
        when(mockHistoricTaskInstanceQuery.list()).thenReturn(historicTaskInstanceList);
	}

	@Test
	public void setCardAsHotlistedShouldInvokeServices(){	
		when(mockCardService.findById(anyLong())).thenReturn(mockCard);
		when(mockRefundScenarioHotListReasonTypeDataService.findByRefundScenario(any(RefundScenarioEnum.class))).thenReturn(mockRefundScenarioHotListReasonType);
		doCallRealMethod().when(mockWorkflowActions).setCardAsHotlisted(any(WorkflowItemDTO.class), anyString());

		mockWorkflowActions.setCardAsHotlisted(workflowItem, EXECUTION_ID);
		verify(mockHotlistCardService).toggleCardHotlisted(anyString(), any(Integer.class));
		verify(mockCardService).findById(anyLong());
		verify(mockRefundScenarioHotListReasonTypeDataService).findByRefundScenario(any(RefundScenarioEnum.class));
	}

	@Test
	public void processPaymentShouldInvokeRefundAndOrderServices(){
		when(mockConverter.convert(any(WorkflowItemDTO.class))).thenReturn(mockCartCmdImpl);
		doCallRealMethod().when(mockWorkflowActions).processPayment(any(WorkflowItemDTO.class));
		
		mockWorkflowActions.processPayment(workflowItem);		
		verify(mockRefundService).completeRefund(any(CartCmdImpl.class));
	}
	
	@Test
	public void pushToGateShouldReturnTrueWithEmptyResponse(){
		when(mockCardService.findById(anyLong())).thenReturn(mockCard);
		doCallRealMethod().when(mockWorkflowActions).pushToGate(any(WorkflowItemDTO.class));
		
		assertTrue(mockWorkflowActions.pushToGate(workflowItem));
		verify(mockRefundService).completeRefund(any(CartCmdImpl.class));
	}

	@Test
	public void cardRefundProcessShouldInvokeRefundAndOrderServices(){
		when(mockConverter.convert(any(WorkflowItemDTO.class))).thenReturn(mockCartCmdImpl);
		doCallRealMethod().when(mockWorkflowActions).cardRefundProcess(any(WorkflowItemDTO.class));
		
		mockWorkflowActions.cardRefundProcess(workflowItem);
		verify(mockRefundService).completeRefund(any(CartCmdImpl.class));
		verify(mockConverter).convert(any(WorkflowItemDTO.class));
	}

	@Test
	public void webAccountCreditAddedToAccountShouldInvokeRefundAndOrderServices(){
		when(mockConverter.convert(any(WorkflowItemDTO.class))).thenReturn(mockCartCmdImpl);
		doCallRealMethod().when(mockWorkflowActions).wacAddedToAccount(any(WorkflowItemDTO.class));
		
		mockWorkflowActions.wacAddedToAccount(workflowItem);
		verify(mockRefundService).completeRefund(any(CartCmdImpl.class));
		verify(mockConverter).convert(any(WorkflowItemDTO.class));
	}
	
	@Test
	public void checkApprovalRequiredShouldInvokesServices(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired()); 
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		verify(mockValueApprovalLimitsService,times(1)).checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class));
		verify(mockBusinessRuleService,times(1)).checkBusinessRules(any(WorkflowItemDTO.class));
		verify(mockRefundCeilingService,times(1)).hasViolatedRefundCeilingRules(any(WorkflowItemDTO.class));
		verify(mockRandomApprovalService,times(1)).processForRandomSampleFlagging(any(WorkflowItemDTO.class));
	}
	
	@Test
	public void checkApprovalRequiredShouldReturnFalseForNoApprovalRequired(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired()); 
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		mockWorkflowActions.checkApprovalRequired(mockWorkflowItem, EXECUTION_ID);
		verify(mockValueApprovalLimitsService,times(1)).checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class));
		assertFalse(workflowItem.getRefundDetails().getApprovalRequired());
		assertFalse(workflowItem.getRefundDetails().getSecondStageApprovalRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldReturnTrueForFirstStageApprovalRequired(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getFirstStageApprovalLevelRequired()); 
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		verify(mockValueApprovalLimitsService,times(1)).checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class));
		assertTrue(workflowItem.getRefundDetails().getApprovalRequired());
		assertFalse(workflowItem.getRefundDetails().getSecondStageApprovalRequired());
	}	
	
	@Test
	public void checkApprovalRequiredShouldReturnTrueForSecondStageApprovalRequired(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getSecondStageApprovalLevelRequired()); 
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		verify(mockValueApprovalLimitsService,times(1)).checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class));
		assertTrue(workflowItem.getRefundDetails().getApprovalRequired());
		assertTrue(workflowItem.getRefundDetails().getSecondStageApprovalRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateApprovalRequiredTrueWithBusinessRules(){
		mockBusinessRules.add(BUSINESS_REASON);
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		when(mockBusinessRuleService.checkBusinessRules(any(WorkflowItemDTO.class))).thenReturn(mockBusinessRules);
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setApprovalRequired(Boolean.FALSE);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		verify(mockBusinessRuleService,times(1)).checkBusinessRules(any(WorkflowItemDTO.class));
		assertTrue(workflowItem.getRefundDetails().getApprovalRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateApprovalRequiredFalseWithNoBusinessRules(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		when(mockBusinessRuleService.checkBusinessRules(any(WorkflowItemDTO.class))).thenReturn(mockBusinessRules);
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setApprovalRequired(Boolean.FALSE);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		verify(mockBusinessRuleService,times(1)).checkBusinessRules(any(WorkflowItemDTO.class));
		assertFalse(workflowItem.getRefundDetails().getApprovalRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateApprovalRequiredTrueWithRefundCeilingRules(){
		mockRefundCeilingRules.add(REFUND_CEILING_RULES);
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		when(mockBusinessRuleService.checkBusinessRules(any(WorkflowItemDTO.class))).thenReturn(mockBusinessRules);
		when(mockRefundCeilingService.hasViolatedRefundCeilingRules(any(WorkflowItemDTO.class))).thenReturn(mockRefundCeilingRules);
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setApprovalRequired(Boolean.FALSE);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertTrue(workflowItem.getRefundDetails().getApprovalRequired());
	}

	@Test
	public void checkApprovalRequiredShouldPopulateApprovalRequiredFalseWithNoRefundCeilingRules(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		when(mockBusinessRuleService.checkBusinessRules(any(WorkflowItemDTO.class))).thenReturn(mockBusinessRules);
		when(mockRefundCeilingService.hasViolatedRefundCeilingRules(any(WorkflowItemDTO.class))).thenReturn(mockRefundCeilingRules);
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setApprovalRequired(Boolean.FALSE);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertFalse(workflowItem.getRefundDetails().getApprovalRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateApprovalRequiredTrueWithFlaggingRules(){
		mockflaggingRules.add(RANDOM_FLAGGING);
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		when(mockBusinessRuleService.checkBusinessRules(any(WorkflowItemDTO.class))).thenReturn(mockBusinessRules);
		when(mockRefundCeilingService.hasViolatedRefundCeilingRules(any(WorkflowItemDTO.class))).thenReturn(mockRefundCeilingRules);
		when(mockRandomApprovalService.processForRandomSampleFlagging(any(WorkflowItemDTO.class))).thenReturn(mockflaggingRules);
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setApprovalRequired(Boolean.FALSE);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertTrue(workflowItem.getRefundDetails().getApprovalRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateApprovalRequiredFalseWithNoFlaggingRules(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		when(mockBusinessRuleService.checkBusinessRules(any(WorkflowItemDTO.class))).thenReturn(mockBusinessRules);
		when(mockRefundCeilingService.hasViolatedRefundCeilingRules(any(WorkflowItemDTO.class))).thenReturn(mockRefundCeilingRules);
		when(mockRandomApprovalService.processForRandomSampleFlagging(any(WorkflowItemDTO.class))).thenReturn(mockflaggingRules);
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setApprovalRequired(Boolean.FALSE);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertFalse(workflowItem.getRefundDetails().getApprovalRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateBacsChequePaymentRequiredToTrueForCheque(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertTrue(workflowItem.getRefundDetails().getBacsChequePaymentRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateBacsChequePaymentRequiredToFalseForPaymentCard(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setPaymentType(PaymentType.PAYMENT_CARD);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertFalse(workflowItem.getRefundDetails().getBacsChequePaymentRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateAdhocLoadRequiredTrueForPaymentCard(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setPaymentType(PaymentType.AD_HOC_LOAD);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertTrue(workflowItem.getRefundDetails().getAdhocLoadRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateWebAccountCreditFalseForPaymentCard(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setPaymentType(PaymentType.WEB_ACCOUNT_CREDIT);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertFalse(workflowItem.getRefundDetails().getCardPaymentRequired());
	}
	
	@Test
	public void checkApprovalRequiredShouldPopulateWebAccountCreditTrueForPaymentCard(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		workflowItem.getRefundDetails().setPaymentType(PaymentType.PAYMENT_CARD);
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		assertTrue(workflowItem.getRefundDetails().getCardPaymentRequired());
	}
	
	@Test
	public void checkApprovalShouldInvokeRuntimeService(){
		when(mockValueApprovalLimitsService.checkApprovalThreshold(any(WorkflowItemDTO.class), any(RefundDetailDTO.class))).thenReturn(getNoApprovalLevelRequired());
		doCallRealMethod().when(mockWorkflowActions).checkApprovalRequired(any(WorkflowItemDTO.class), anyString());
		
		mockWorkflowActions.checkApprovalRequired(workflowItem, EXECUTION_ID);
		verify(mockRuntimeService, times(1)).setVariable(anyString(), anyString(), any(Object.class));
	}
	
	@Test
	public void findOriginatingApplicationShouldBeInnovatorAndNotOnline(){
		doCallRealMethod().when(mockWorkflowActions).findOriginatingApplication(any(WorkflowItemDTO.class));
		
		mockWorkflowActions.findOriginatingApplication(workflowItem);
		assertTrue(workflowItem.getRefundDetails().getRefundOriginatingApplicationIsInnovator());
		assertFalse(workflowItem.getRefundDetails().getRefundOriginatingApplicationIsOnline());
	}
	
	@Test
	public void findOriginatingApplicationShouldBeOnlineAndNotInnovator(){
		workflowItem.getRefundDetails().setRefundOriginatingApplication(ApplicationName.NEMO_TFL_ONLINE);
		doCallRealMethod().when(mockWorkflowActions).findOriginatingApplication(any(WorkflowItemDTO.class));
		
		mockWorkflowActions.findOriginatingApplication(workflowItem);
		assertFalse(workflowItem.getRefundDetails().getRefundOriginatingApplicationIsInnovator());
		assertTrue(workflowItem.getRefundDetails().getRefundOriginatingApplicationIsOnline());
	}

    @Test
    public void shouldUnclaimAClaimedTask() {
        historicTaskInstanceList.add(mock(HistoricTaskInstance.class));
        when(mockWorkflowItem.getCaseNumber()).thenReturn(ApprovalTestUtil.REFUND_IDENTIFIER);
        when(mockWorkflowItem.getAgent()).thenReturn(ApprovalTestUtil.AGENT);
        DateTimeFormatter formatter = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
        when(mockWorkflowItem.getClaimedTime()).thenReturn(formatter.parseDateTime(DateTestUtil.DATE_21_12_2013));
        when(mockWorkflowActions.isPeriodPastTimeCycle(any(WorkflowItemDTO.class))).thenReturn(Boolean.TRUE);

        doCallRealMethod().when(mockWorkflowActions).unclaimTask(any(WorkflowItemDTO.class));
        mockWorkflowActions.unclaimTask(mockWorkflowItem);

        verify(mockHistoryService).createHistoricTaskInstanceQuery();
        verify(mockHistoricTaskInstanceQuery).list();
        verify(mockWorkflowItem).setAgent(anyString());
        verify(mockTaskService).unclaim(anyString());
    }

    @Test
    public void shouldNotUnlcaimAClaimedTaskIfItWasClaimedWithinSetTime() {
        historicTaskInstanceList.add(mock(HistoricTaskInstance.class));
        when(mockWorkflowItem.getCaseNumber()).thenReturn(ApprovalTestUtil.REFUND_IDENTIFIER);
        when(mockWorkflowItem.getAgent()).thenReturn(ApprovalTestUtil.AGENT);
        when(mockWorkflowItem.getClaimedTime()).thenReturn(new DateTime());
        when(mockWorkflowActions.isPeriodPastTimeCycle(any(WorkflowItemDTO.class))).thenReturn(Boolean.FALSE);

        doCallRealMethod().when(mockWorkflowActions).unclaimTask(any(WorkflowItemDTO.class));
        mockWorkflowActions.unclaimTask(mockWorkflowItem);

        verify(mockHistoryService).createHistoricTaskInstanceQuery();
        verify(mockHistoricTaskInstanceQuery).list();
        verify(mockWorkflowItem, never()).setAgent(anyString());
        verify(mockTaskService, never()).unclaim(anyString());
    }

    @Test
    public void shouldNotCallUnclaimOnAnUnclaimedTask() {
        historicTaskInstanceList.add(mock(HistoricTaskInstance.class));
        when(mockWorkflowItem.getCaseNumber()).thenReturn(ApprovalTestUtil.REFUND_IDENTIFIER);
        when(mockWorkflowItem.getAgent()).thenReturn(StringUtil.EMPTY_STRING);

        doCallRealMethod().when(mockWorkflowActions).unclaimTask(any(WorkflowItemDTO.class));
        mockWorkflowActions.unclaimTask(mockWorkflowItem);

        verify(mockHistoryService).createHistoricTaskInstanceQuery();
        verify(mockHistoricTaskInstanceQuery).list();
        verify(mockWorkflowItem, never()).setAgent(anyString());
        verify(mockTaskService, never()).unclaim(anyString());
    }

    @Test
    public void shouldGetTimeCycleWithoutRepeat() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(TIME_CYCLE);
        doCallRealMethod().when(mockWorkflowActions).getTimeCycleWithoutRepeat();
        assertEquals(EXPECTED_TIME_CYCLE_WITHOUT_REPEAT, mockWorkflowActions.getTimeCycleWithoutRepeat());
        verify(mockSystemParameterService).getParameterValue(anyString());
    }

    @Test
    public void shouldGetTimeCycle() {
        when(mockSystemParameterService.getParameterValue(anyString())).thenReturn(TIME_CYCLE);
        doCallRealMethod().when(mockWorkflowActions).getTimeCycle();
        assertEquals(EXPECTED_TIME_CYCLE, mockWorkflowActions.getTimeCycle());
        verify(mockSystemParameterService).getParameterValue(anyString());
    }
}
