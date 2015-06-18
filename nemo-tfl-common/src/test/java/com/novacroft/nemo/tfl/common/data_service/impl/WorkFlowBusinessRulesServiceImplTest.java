package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.TIME_TO_MINUTE_PATTERN;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO1;
import static com.novacroft.nemo.test_support.AddressTestUtil.getTestAddressDTO2;
import static com.novacroft.nemo.test_support.CommonCardTestUtil.CARD_ID_1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO2;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mockito;

import com.novacroft.nemo.common.constant.DateConstant;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.test_support.ApprovalTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.DateTestUtil;
import com.novacroft.nemo.test_support.ItemTestUtil;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.ItemType;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.domain.HotlistReason;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkFlowBusinessRulesServiceImplTest {

    private static final String WORK_HOURS_HIGH_THRESHOLD = "20:40";
    private static final String WORK_HOURS_LOW_THRESHOLD = "07:50";
    private static final int REFUND_BY_SCENARIO_CEILING_HOURS = 3;
    private static final int REFUND_BY_SCENARIO_CEILING = 3;
    private static final long OVER_VALUE_CHEQUE_THRESHOLD = 2500L;
    private static final long UNDER_VALUE_CHEQUE_THRESHOLD = 250L;
    private static final int CHEQUE_VALUE_THRESHOLD = 500;
    private static final int QUANTITY_PER_PERIOD_DAYS = 4;
    private static final int QUANTITY_PER_PERIOD_CEILING = 2;
    private static final int CANCEL_AND_SURRENDER_DATE_CARD_RECEIVED_LIMIT = 48;
    private static final int HOURLY_PERIOD_CEILING = 4;
    private static final int HOURLY_PERIOD_CEILING_HOURS_CONSTANT = 4;
    private static final String WORKFLOW_REFUNDS_BY_SCENARIO_CEILING_HOURS = "workflowRefundsByScenarioCeilingHours";
    private static final String WORKFLOW_REFUNDS_BY_SCENARIO_CEILING = "workflowRefundsByScenarioCeiling";
    private static final String WORKFLOW_REFUND_LOW_CHEQUE_VALUE_THRESHOLD = "workflowRefundLowChequeValueThreshold";
    private static final String WORKFLOW_REFUND_TIME_WINDOW_UPPER_VALUE = "workflowRefundTimeWindowUpperValue";
    private static final String WORKFLOW_REFUND_TIME_WINDOW_LOWER_VALUE = "workflowRefundTimeWindowLowerValue";
    private static final String WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_HOURS = "workflowRefundsPerHourlyPeriodCeilingHours";
    private static final String WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_PERIOD = "workflowRefundsPerHourlyPeriodCeilingPeriod";
    private static final String WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_DAYS = "workflowRefundsQuantityPerPeriodDays";
    private static final String WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_CEILING = "workflowRefundsQuantityPerPeriodCeiling";
    
    private static final String DATE_OF_SURRENDER = "03/03/2014";
    private static final String DATE_OF_REFUND = "01/01/2014";
    
    
    private WorkflowBusinessRulesServiceImpl mockWorkflowBusinessRulesService;
    private SystemParameterDataService mockSystemParameterService;
    private PaymentCardSettlementDataService mockPaymentService;
    private CardDataService mockCardService;
    private OrderDataService mockOrderDataService;
    private ContentDataService mockContentService;
    private CustomerDataService mockCustomerService;
    private CustomerDTO mockCustomerDTO;
    
    private WorkflowItemDTO workflowItemDTO;
    private RefundDetailDTO details;
    private List<String> ruleBreachList;
    private AddressDataService mockAddressService;
    private TaskService mockTaskService;
    private List<Task> taskList;
    private TaskQuery mockTaskQuery;
    private CartDataService mockCartDataService;
    
    @Before
    public void setUp() {
        mockWorkflowBusinessRulesService = mock(WorkflowBusinessRulesServiceImpl.class);
        mockSystemParameterService = mock(SystemParameterDataService.class);
        mockCardService = mock(CardDataService.class);
        mockPaymentService = mock(PaymentCardSettlementDataService.class);
        mockContentService = mock(ContentDataService.class);
        mockOrderDataService= mock(OrderDataService.class);
        mockCustomerService = mock(CustomerDataService.class);
        ruleBreachList = new ArrayList<String>();
        mockCustomerDTO = mock(CustomerDTO.class);
        mockAddressService = mock(AddressDataService.class);
        mockTaskService = mock(TaskService.class);
        taskList = new ArrayList<Task>();
        mockTaskQuery = mock(TaskQuery.class);
        mockCartDataService=mock(CartDataService.class);
        
        details = new RefundDetailDTO();
        
        mockWorkflowBusinessRulesService.systemParameterService = mockSystemParameterService;
        mockWorkflowBusinessRulesService.cardService = mockCardService;
        mockWorkflowBusinessRulesService.paymentService = mockPaymentService;
        mockWorkflowBusinessRulesService.orderDataService = mockOrderDataService;
        mockWorkflowBusinessRulesService.content = mockContentService;
        mockWorkflowBusinessRulesService.customerService = mockCustomerService;
        mockWorkflowBusinessRulesService.addressService = mockAddressService;
        mockWorkflowBusinessRulesService.taskService = mockTaskService;
        mockWorkflowBusinessRulesService.cartDataService = mockCartDataService;
        
        workflowItemDTO = new WorkflowItemDTO(); 
        workflowItemDTO.setCaseNumber(ApprovalTestUtil.REFUND_IDENTIFIER);
        AddressDTO address = new AddressDTO();
        AddressDTO altAddress = new AddressDTO();
        address.setId(1L);
        altAddress.setId(1L);
        details.setRefundEntity(new Refund());
        details.setCardId(4L);
        details.setAddress(address);
        details.setPaymentType(PaymentType.CHEQUE);
        details.setAlternativeAddress(altAddress);
        details.setRefundDate(new DateTime());
        workflowItemDTO.setRefundDetails(details );
       
        
        mockWorkflowBusinessRulesService.workflowRefundsPerHourlyPeriodCeilingHours = HOURLY_PERIOD_CEILING_HOURS_CONSTANT;
        mockWorkflowBusinessRulesService.workflowRefundsPerHourlyPeriodCeiling = HOURLY_PERIOD_CEILING;
        mockWorkflowBusinessRulesService.workflowRefundsQuantityPerPeriodCeiling =  QUANTITY_PER_PERIOD_CEILING;
        mockWorkflowBusinessRulesService.workflowRefundsQuantityPerPeriodDays =  QUANTITY_PER_PERIOD_DAYS;
        mockWorkflowBusinessRulesService.workflowRefundsSeasonalBackDatedCancelAndSurrenderDaysCeiling = CANCEL_AND_SURRENDER_DATE_CARD_RECEIVED_LIMIT;
    }
    
    @Test
    public void testInitialize(){
        
        ContentDTO content = new ContentDTO();
        content.setContent("ANY");
        SystemParameterDTO param = new SystemParameterDTO();
        SystemParameterDTO time = new SystemParameterDTO();
        param.setValue("1");
        time.setValue(WORK_HOURS_LOW_THRESHOLD);
        
       doReturn(time).when(mockSystemParameterService).findByCode(WORKFLOW_REFUND_TIME_WINDOW_UPPER_VALUE);
       doReturn(time).when(mockSystemParameterService).findByCode(WORKFLOW_REFUND_TIME_WINDOW_LOWER_VALUE);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUNDS_BY_SCENARIO_CEILING_HOURS);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUNDS_BY_SCENARIO_CEILING);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUND_LOW_CHEQUE_VALUE_THRESHOLD);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_HOURS);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_PERIOD);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_DAYS);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_CEILING);
       doReturn(param).when(mockSystemParameterService).findByCode(WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_CEILING);
       doReturn(param).when(mockSystemParameterService).findByCode(WorkflowBusinessRulesServiceImpl.WORKFLOW_REFUNDS_SEASONAL_BACK_DATED_CANCEL_AND_SURRENDER_DAYS_CEILING);
       doReturn(content).when(mockContentService).findByLocaleAndCode(anyString(), anyString());
       
       doCallRealMethod().when(mockWorkflowBusinessRulesService).initialize();

       mockWorkflowBusinessRulesService.initialize();
        
       Mockito.verify(mockSystemParameterService, Mockito.atLeast(6)).findByCode(anyString());
       Mockito.verify(mockContentService, Mockito.atLeast(9)).findByLocaleAndCode(anyString(), anyString());
       
    }
    
    
    @Test
    public void alternativePayeeRequested(){
        details.setAlternativeRefundPayee(getTestCustomerDTO1());
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAlternativePayeeRequested(any(WorkflowItemDTO.class),any(List.class));
        doReturn(getTestCustomerDTO2()).when(mockCustomerService).findById(workflowItemDTO.getRefundDetails().getCustomerId());
        doReturn(getTestAddressDTO1()).when(mockAddressService).findById(anyLong());
        List<String> result = mockWorkflowBusinessRulesService.hasAlternativePayeeRequested(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    
    @Test
    public void alternativePayeeNotRequested(){
        details.setAlternativeRefundPayee(null);
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAlternativePayeeRequested(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasAlternativePayeeRequested(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    @Test
    public void alternativeAddressRequested(){
        details.setAlternativeAddress(getTestAddressDTO1());
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAlternativeAddressRequested(any(WorkflowItemDTO.class),any(List.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAlternativeAddressRequested(workflowItemDTO,ruleBreachList);
        doReturn(mockCustomerDTO).when(mockCustomerService).findById(workflowItemDTO.getRefundDetails().getCustomerId());
        doReturn(getTestAddressDTO2()).when(mockAddressService).findById(anyLong());
        List<String> result = mockWorkflowBusinessRulesService.hasAlternativeAddressRequested(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    @Test
    public void alternativeAddressNotRequested(){
        details.setAlternativeAddress(getTestAddressDTO1());
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAlternativeAddressRequested(workflowItemDTO,ruleBreachList);
        doReturn(mockCustomerDTO).when(mockCustomerService).findById(workflowItemDTO.getRefundDetails().getCustomerId());
        doReturn(getTestAddressDTO1()).when(mockAddressService).findById(anyLong());
        List<String> result = mockWorkflowBusinessRulesService.hasAlternativeAddressRequested(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    @Test
    public void hasTicketPreviouslyExchanged(){
        details.setPreviouslyExchanged(Boolean.TRUE);
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasPreviouslyExchangedTicket(workflowItemDTO, ruleBreachList);
        List<String> result = mockWorkflowBusinessRulesService.hasPreviouslyExchangedTicket(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    
    
    @Test
    public void hasTicketPreviouslyNotExchanged(){
        details.setPreviouslyExchanged(Boolean.FALSE);
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasPreviouslyExchangedTicket(workflowItemDTO, ruleBreachList);
        List<String> result = mockWorkflowBusinessRulesService.hasPreviouslyExchangedTicket(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    
    @Test
    public void hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(){
        details.setTotalRefundAmount(1L);
        details.setRefundDate(new DateTime());
        List<OrderDTO> matchingPayments = new ArrayList<OrderDTO>();
        matchingPayments.add(new OrderDTO());
            
        doReturn(matchingPayments).when(mockOrderDataService).findByRefundedCardNumberPriceAndDate(anyString(), anyLong(), any(DateTime.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(workflowItemDTO, ruleBreachList);
        
       assert(result.size() > 0);
    }
    @Test
    public void doesNotHaveMatchingOutstandingPaymentsForOysterCardDateAndPrice(){
        details.setTotalRefundAmount(1L);
        details.setRefundDate(new DateTime());
        List<OrderDTO> matchingPayments = new ArrayList<OrderDTO>();

        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(any(WorkflowItemDTO.class),any(List.class));
       doReturn(matchingPayments).when(mockOrderDataService).findByCustomerCardNumberPriceAndDate(anyString(),anyLong(),anyLong(),any(DateTime.class));
        List<String> result = mockWorkflowBusinessRulesService.hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }

    @Test
    public void AccountHasExceededClaimCeiling(){
        
        List<OrderDTO> ordersInPeriod = new ArrayList<OrderDTO>();
        for (int i = 0; i < HOURLY_PERIOD_CEILING_HOURS_CONSTANT + 1; i++) {
            ordersInPeriod.add(new OrderDTO());         
        }
        
       doReturn(ordersInPeriod).when(mockOrderDataService).findByCustomerIdAndDuration(anyLong(),any(DateTime.class),any(DateTime.class));
        doReturn(mockTaskQuery).when(mockTaskService).createTaskQuery();
        doReturn(taskList).when(mockTaskQuery).list();
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAccountExceededClaimCeiling(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasAccountExceededClaimCeiling(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
       assert(result.size() > 0);
    }
    
    @Test
    public void AccountHasNotExceededClaimCeiling(){

        
        List<OrderDTO> ordersInPeriod = new ArrayList<OrderDTO>();
        ordersInPeriod.add(new OrderDTO());         

       doReturn(ordersInPeriod).when(mockOrderDataService).findByCustomerIdAndDuration(anyLong(),any(DateTime.class),any(DateTime.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAccountExceededClaimCeiling(any(WorkflowItemDTO.class),any(List.class));
        doReturn(mockTaskQuery).when(mockTaskService).createTaskQuery();
        doReturn(taskList).when(mockTaskQuery).list();
        
        List<String> result = mockWorkflowBusinessRulesService.hasAccountExceededClaimCeiling(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    @Test
    public void AddressHasExceededClaimCeiling(){

        List<OrderDTO> ordersInPeriod = new ArrayList<OrderDTO>();
        for (int i = 0; i <= HOURLY_PERIOD_CEILING; i++) {
            ordersInPeriod.add(new OrderDTO());         
        }
        
       doReturn(ordersInPeriod).when(mockOrderDataService).findByAddressIdAndDuration(anyLong(),any(DateTime.class),any(DateTime.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAddressExceededClaimCeiling(any(WorkflowItemDTO.class),any(List.class));
        doReturn(mockTaskQuery).when(mockTaskService).createTaskQuery();
        doCallRealMethod().when(mockWorkflowBusinessRulesService).searchWorkflowItemsForMatchesInTimePeriod(any(WorkflowItemDTO.class), any(DateTime.class), anyString(), any(Object.class));
        doReturn(taskList).when(mockTaskQuery).list();
        List<String> result = mockWorkflowBusinessRulesService.hasAddressExceededClaimCeiling(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
       Mockito.verify(mockTaskQuery, Mockito.times(2)).list();
       assert(result.size() + taskList.size() > 0);
    }
    @Test
    public void AddressHasNotExceededClaimCeiling(){
        
        doReturn(null).when(mockOrderDataService).findByAddressIdAndDuration(anyLong(),any(DateTime.class),any(DateTime.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAddressExceededClaimCeiling(any(WorkflowItemDTO.class),any(List.class));
        doReturn(mockTaskQuery).when(mockTaskService).createTaskQuery();
        doCallRealMethod().when(mockWorkflowBusinessRulesService).searchWorkflowItemsForMatchesInTimePeriod(any(WorkflowItemDTO.class), any(DateTime.class), anyString(), any(Object.class));
        doReturn(taskList).when(mockTaskQuery).list();
        List<String> result = mockWorkflowBusinessRulesService.hasAddressExceededClaimCeiling(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
       Mockito.verify(mockTaskQuery, Mockito.times(2)).list();
       assert(result.size() + taskList.size() == 0);
    }
    
    
    @Test
    @Ignore
    public void AccountHasExceededRefundsPerScenarioCeiling(){
        //TODO: waiting for cart refactor, unable to data mine refund by scenario currently
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAccountExceededRefundsPerScenarioCeiling(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasAccountExceededRefundsPerScenarioCeiling(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    @Test
    public void AccountHasNotExceededRefundsPerScenarioCeiling(){
        mockWorkflowBusinessRulesService.workflowRefundsByScenarioCeiling = REFUND_BY_SCENARIO_CEILING;
        mockWorkflowBusinessRulesService.workflowRefundsByScenarioCeilingHours = REFUND_BY_SCENARIO_CEILING_HOURS;
        
        
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasAccountExceededRefundsPerScenarioCeiling(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasAccountExceededRefundsPerScenarioCeiling(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    @Test
    public void hasLowChequeRefundValue(){
        mockWorkflowBusinessRulesService.workflowRefundLowChequeValueThreshold =  CHEQUE_VALUE_THRESHOLD;       
        details.setTotalRefundAmount(UNDER_VALUE_CHEQUE_THRESHOLD);
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasLowChequeRefundValue(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasLowChequeRefundValue(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
        
    }
    @Test
    public void doesNotHaveLowChequeRefundValue(){
        mockWorkflowBusinessRulesService.workflowRefundLowChequeValueThreshold =  CHEQUE_VALUE_THRESHOLD;       
        details.setTotalRefundAmount(OVER_VALUE_CHEQUE_THRESHOLD);
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasLowChequeRefundValue(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasLowChequeRefundValue(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
 
    
    @Test
    public void hasBeenRefundedOusideWorkHours(){
        
        SystemParameterDTO systemParam = new SystemParameterDTO();
        when(mockSystemParameterService.findByCode(anyString())).thenReturn(systemParam );
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasRefundedOusideWorkHours(any(WorkflowItemDTO.class),any(List.class));
        
        workflowItemDTO.setCreatedTime(DateTime.parse("04:14", DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN)));
        mockWorkflowBusinessRulesService.workflowRefundTimeWindowLowerValue = DateTime.parse(WORK_HOURS_LOW_THRESHOLD, DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN));
        mockWorkflowBusinessRulesService.workflowRefundTimeWindowUpperValue = DateTime.parse(WORK_HOURS_HIGH_THRESHOLD, DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN));
       
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasRefundedOusideWorkHours(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasRefundedOusideWorkHours(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    @Test
    public void hasNotBeenRefundedOusideWorkHours(){

        SystemParameterDTO lowerParam = new SystemParameterDTO();
        lowerParam.setValue(WORK_HOURS_LOW_THRESHOLD);
        SystemParameterDTO upperParam = new SystemParameterDTO();
        upperParam.setValue("20:20");
        workflowItemDTO.setCreatedTime(DateTime.parse("13:13", DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN)));
        
        mockWorkflowBusinessRulesService.workflowRefundTimeWindowLowerValue = DateTime.parse(WORK_HOURS_LOW_THRESHOLD, DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN));
        mockWorkflowBusinessRulesService.workflowRefundTimeWindowUpperValue = DateTime.parse(WORK_HOURS_HIGH_THRESHOLD, DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN));

        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasRefundedOusideWorkHours(any(WorkflowItemDTO.class),any(List.class));
        
        
       
        List<String> result = mockWorkflowBusinessRulesService.hasRefundedOusideWorkHours(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    @Test
    public void noMatchingOutstandingPayments(){
        details.setTotalRefundAmount(1L);
        details.setRefundDate(new DateTime());
        List<OrderDTO> matchingPayments = new ArrayList<OrderDTO>();
            
       doReturn(matchingPayments).when(mockOrderDataService).findByCustomerCardNumberPriceAndDate(anyString(),anyLong(),anyLong(),any(DateTime.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    @Test
    public void MatchingOutstandingPayments(){
        details.setTotalRefundAmount(1L);
        details.setRefundDate(new DateTime());
        List<OrderDTO> matchingPayments = new ArrayList<OrderDTO>();
        matchingPayments.add(new OrderDTO());
            
       doReturn(matchingPayments).when(mockOrderDataService).findByCustomerCardNumberPriceAndDate(anyString(),anyLong(),anyLong(),any(DateTime.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(workflowItemDTO, ruleBreachList);
        
       assert(result.size() > 0);
    }
    @Test
    public void isInelligibleCardForRefund(){
        CardDTO card = new CardDTO();
        card.setHotlistReason(new HotlistReason());
        doReturn(card).when(mockCardService).findById(anyLong());
        doCallRealMethod().when(mockWorkflowBusinessRulesService).isInelligibleCardForRefund(any(WorkflowItemDTO.class),any(List.class));
        
        List<String> result = mockWorkflowBusinessRulesService.isInelligibleCardForRefund(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    @Test
    public void isNotInelligibleCardForRefund(){
        CardDTO card = null;
       doReturn(card).when(mockCardService).findById(anyLong());
        doCallRealMethod().when(mockWorkflowBusinessRulesService).isInelligibleCardForRefund(any(WorkflowItemDTO.class),any(List.class));
        
        List<String> result = mockWorkflowBusinessRulesService.isInelligibleCardForRefund(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    
    @SuppressWarnings("unchecked")
    @Test
    public void hasExceededBackDatingPeriodForCancelAndSurrender() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasExceededBackDatingPeriodForCancelAndSurrender(any(WorkflowItemDTO.class), any(List.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).backDateLimitExceededForAnnualTicket(any(ItemDTO.class), any(DateTime.class));
        doCallRealMethod().when(mockWorkflowBusinessRulesService).isSeasonalTicket(any(ProductItemDTO.class));

        CartDTO cartDto = new CartDTO();
        ProductItemDTO productItemDto = new ProductItemDTO();
        productItemDto.setStartDate(DateUtil.parse(DateTestUtil.ANNUAL_START_DATE));
        productItemDto.setEndDate(DateUtil.parse(DateTestUtil.ANNUAL_END_DATE));
        productItemDto.setStartZone(1);
        productItemDto.setEndZone(2);
        productItemDto.setProductType(ItemType.TRAVEL_CARD.code());
        productItemDto.setDeceasedCustomer(false);
        productItemDto.setBackdatedRefundReasonId(1l);
        productItemDto.setDateOfCanceAndSurrender(DateUtil.parse(DATE_OF_SURRENDER));
        productItemDto.setDateOfRefund(DateUtil.parse(DATE_OF_REFUND));
        productItemDto.setCardId(CARD_ID_1);
        productItemDto.setRefundCalculationBasis(RefundCalculationBasis.PRO_RATA.code());
        cartDto.setCartType(CartType.CANCEL_SURRENDER_REFUND.code());
        
        details.setRefundDate(DateTime.parse(DATE_OF_REFUND, DateTimeFormat.forPattern(DateConstant.SHORT_DATE_PATTERN)));
        workflowItemDTO.setCaseNumber("3");
        details.setRefundScenario(RefundScenarioEnum.CANCEL_AND_SURRENDER);

        List<ItemDTO> itemDtolist = new ArrayList<ItemDTO>();
        itemDtolist.add(productItemDto);
        cartDto.setCartItems(itemDtolist);
        when(mockCartDataService.findByApprovalId(anyLong())).thenReturn(cartDto);

        List<String> result = mockWorkflowBusinessRulesService.hasExceededBackDatingPeriodForCancelAndSurrender(workflowItemDTO, ruleBreachList);
        assert (result.size() > 0);
    }
    
    @Test
    public void shouldNotAddRuleBreachForExceedingBackDatingPeriodForCancelAndSurrenderIfLimitNotExceed() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasExceededBackDatingPeriodForCancelAndSurrender(any(WorkflowItemDTO.class),
                        any(List.class));
        workflowItemDTO.getRefundDetails().setRefundScenario(RefundScenarioEnum.CANCEL_AND_SURRENDER);
        when(mockCartDataService.findByApprovalId(anyLong())).thenReturn(CartTestUtil.getNewCartDTOWithProductItemWithRefund());
        when(mockWorkflowBusinessRulesService.backDateLimitExceededForAnnualTicket(any(ItemDTO.class), any(DateTime.class)))
                        .thenReturn(Boolean.FALSE);
        List<String> result = mockWorkflowBusinessRulesService.hasExceededBackDatingPeriodForCancelAndSurrender(workflowItemDTO, ruleBreachList);
        assertEquals(ruleBreachList, result);
    }

    @Test
    public void shouldNotcheckHasExceededBackDatingPeriodIfScenarioIsNotCancelAndSurrender() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasExceededBackDatingPeriodForCancelAndSurrender(any(WorkflowItemDTO.class),
                        any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasExceededBackDatingPeriodForCancelAndSurrender(workflowItemDTO, ruleBreachList);
        assertEquals(ruleBreachList, result);
        verify(mockWorkflowBusinessRulesService, never()).backDateLimitExceededForAnnualTicket(any(ItemDTO.class), any(DateTime.class));
    }
    
    @SuppressWarnings("unchecked")
    @Test
    public void hasChangedFromDefaults() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasChangedFromDefaults(any(WorkflowItemDTO.class), any(List.class));
        when(mockWorkflowBusinessRulesService.haveDefaultsChanged(any(RefundDetailDTO.class))).thenReturn(Boolean.TRUE);

        List<String> result = mockWorkflowBusinessRulesService.hasChangedFromDefaults(workflowItemDTO, ruleBreachList);
        assert (result.size() > 0);
    }

    @SuppressWarnings("unchecked")
    @Test
    public void hasNotChangedFromDefaults() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasChangedFromDefaults(any(WorkflowItemDTO.class), any(List.class));
        when(mockWorkflowBusinessRulesService.haveDefaultsChanged(any(RefundDetailDTO.class))).thenReturn(Boolean.FALSE);

        List<String> result = mockWorkflowBusinessRulesService.hasChangedFromDefaults(workflowItemDTO, ruleBreachList);
        assert (result.size() == 0);
    }

    @Test
    public void shouldReturnTrueIfDefaultsChanged() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).haveDefaultsChanged(details);
        details.setAdminFeeChanged(Boolean.TRUE);
        assertTrue(mockWorkflowBusinessRulesService.haveDefaultsChanged(details));
    }

    @Test
    public void shouldReturnFalseIfDefaultsNotChanged() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).haveDefaultsChanged(details);
        assertFalse(mockWorkflowBusinessRulesService.haveDefaultsChanged(details));
    }

    /**RELEASE TWO RULES -----------------------------------------------------------------------------------------------------
     * Not due for implementation until release two
     */
    @Test
    @Ignore
    public void customerIsNotOnBlockedList(){
        //TODO  this is pending with TFL. There is currently no block list. Expected in the Autumn Drop (Andrew Parker 15/05/2014).
         List<String> result = mockWorkflowBusinessRulesService.isCustomerOnBlockedList(workflowItemDTO, ruleBreachList);
        
       assert(result.size() == 0);
    }
    
    @Test
    @Ignore
    public void customerIsOnBlockedList(){
      //TODO  this is pending with TFL. There is currently no block list. Expected in the Autumn Drop (Andrew Parker 15/05/2014).
        List<String> result = mockWorkflowBusinessRulesService.isCustomerOnBlockedList(workflowItemDTO, ruleBreachList);
        
       assert(result.size() > 0);
        
    }

    @Test
    @Ignore
    public void warrantPaymentMismatch(){
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasWarrantPaymentMismatch(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasWarrantPaymentMismatch(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    @Test
    @Ignore
    public void noWarrantPaymentMismatch(){
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasWarrantPaymentMismatch(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasWarrantPaymentMismatch(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    @Test
    @Ignore
    public void hasOverlapAfterFareRevision(){
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasOverlapAfterFareRevision(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasOverlapAfterFareRevision(workflowItemDTO, ruleBreachList);
       assert(result.size() > 0);
    }
    
    @Test
    @Ignore
    public void doesNotHaveOverlapAfterFareRevision(){
        doCallRealMethod().when(mockWorkflowBusinessRulesService).hasOverlapAfterFareRevision(any(WorkflowItemDTO.class),any(List.class));
        List<String> result = mockWorkflowBusinessRulesService.hasOverlapAfterFareRevision(workflowItemDTO, ruleBreachList);
       assert(result.size() == 0);
    }
    
    @Test
    public void testcheckBusinessRulesMethod(){
    doCallRealMethod().when(mockWorkflowBusinessRulesService).checkBusinessRules(any(WorkflowItemDTO.class));
    
    mockWorkflowBusinessRulesService.checkBusinessRules(workflowItemDTO);
    
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasAlternativePayeeRequested(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasAlternativeAddressRequested(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasAccountExceededClaimCeiling(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasAddressExceededClaimCeiling(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasAccountExceededRefundsPerScenarioCeiling(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasLowChequeRefundValue(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasRefundedOusideWorkHours(any(WorkflowItemDTO.class), any(List.class)) ;
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).isInelligibleCardForRefund(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasChangedFromDefaults(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).isCustomerOnBlockedList(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasOverlapAfterFareRevision(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasWarrantPaymentMismatch(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasExceededBackDatingPeriodForCancelAndSurrender(any(WorkflowItemDTO.class), any(List.class));
    Mockito.verify(mockWorkflowBusinessRulesService, Mockito.times(1)).hasPreviouslyExchangedTicket(any(WorkflowItemDTO.class), any(List.class));
    }

    @Test
    public void shouldNotTestBusinessRulesIfAnonymousGoodwill() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).checkBusinessRules(any(WorkflowItemDTO.class));
        workflowItemDTO.getRefundDetails().setRefundScenario(RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND);
        mockWorkflowBusinessRulesService.checkBusinessRules(workflowItemDTO);

        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasAlternativePayeeRequested(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasAlternativeAddressRequested(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(
                        any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasAccountExceededClaimCeiling(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasAddressExceededClaimCeiling(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasAccountExceededRefundsPerScenarioCeiling(any(WorkflowItemDTO.class),
                        any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasLowChequeRefundValue(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).isInelligibleCardForRefund(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasChangedFromDefaults(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).isCustomerOnBlockedList(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasOverlapAfterFareRevision(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasWarrantPaymentMismatch(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasExceededBackDatingPeriodForCancelAndSurrender(any(WorkflowItemDTO.class),
                        any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService, never()).hasPreviouslyExchangedTicket(any(WorkflowItemDTO.class), any(List.class));
    }

    @Test
    public void shouldReturnFalseIfNotASeasonTicketForBackDateLimitIsNotExceededForAnnualTicket() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).backDateLimitExceededForAnnualTicket(any(ItemDTO.class), any(DateTime.class));
        when(mockWorkflowBusinessRulesService.isSeasonalTicket(any(ProductItemDTO.class))).thenReturn(Boolean.FALSE);
        assertFalse(mockWorkflowBusinessRulesService.backDateLimitExceededForAnnualTicket(ItemTestUtil.getTestProductItemDTO1(), new DateTime()));
    }

    @Test
    public void shouldReturnFalseIfItemDTOisNullForBackDateLimitIsNotExceededForAnnualTicket() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).backDateLimitExceededForAnnualTicket(any(ItemDTO.class), any(DateTime.class));
        assertFalse(mockWorkflowBusinessRulesService.backDateLimitExceededForAnnualTicket(null, new DateTime()));
    }

    @Test
    public void shouldReturnFalseIfItemDTOisNotAProductItemDTOForBackDateLimitIsNotExceededForAnnualTicket() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).backDateLimitExceededForAnnualTicket(any(ItemDTO.class), any(DateTime.class));
        assertFalse(mockWorkflowBusinessRulesService.backDateLimitExceededForAnnualTicket(ItemTestUtil.getTestPayAsYouGoItemDTO1(), new DateTime()));
    }
    
    @Test
    public void shouldTestBusinessRulesIfAnonymousGoodwill() {
        doCallRealMethod().when(mockWorkflowBusinessRulesService).checkBusinessRules(any(WorkflowItemDTO.class));
        workflowItemDTO.getRefundDetails().setRefundScenario(RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND);
        mockWorkflowBusinessRulesService.checkBusinessRules(workflowItemDTO);
        
        Mockito.verify(mockWorkflowBusinessRulesService).hasRefundedOusideWorkHours(any(WorkflowItemDTO.class), any(List.class));
        Mockito.verify(mockWorkflowBusinessRulesService).hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(any(WorkflowItemDTO.class), any(List.class));
        
    }
}
