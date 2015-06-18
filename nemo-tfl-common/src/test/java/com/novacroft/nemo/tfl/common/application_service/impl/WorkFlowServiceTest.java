package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.test_support.AdministrationFeeTestUtil.getTestAdministrationFeeDTO1;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.AGENT;
import static com.novacroft.nemo.test_support.ApprovalTestUtil.REFUND_IDENTIFIER;
import static com.novacroft.nemo.test_support.CardTestUtil.CARD_ID;
import static com.novacroft.nemo.test_support.CustomerTestUtil.getTestCustomerDTO1;
import static com.novacroft.nemo.tfl.common.util.PayeePaymentTestUtil.getPayeePaymentDTO1;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyMapOf;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DeploymentBuilder;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.repository.ProcessDefinitionQuery;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.ContentDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.test_support.AdministrationFeeItemTestUtil;
import com.novacroft.nemo.test_support.CardInfoResponseV2TestUtil;
import com.novacroft.nemo.test_support.CartCmdTestUtil;
import com.novacroft.nemo.test_support.CartTestUtil;
import com.novacroft.nemo.test_support.GoodwillPaymentTestUtil;
import com.novacroft.nemo.test_support.PayAsYouGoItemTestUtil;
import com.novacroft.nemo.test_support.PrePayValueTestUtil;
import com.novacroft.nemo.test_support.RefundWorkflowTestUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartAdministrationService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CaseHistoryNoteService;
import com.novacroft.nemo.tfl.common.application_service.EditRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.WorkflowEditService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.converter.impl.RefundToWorkflowConverter;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowSearchService;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.innovator.workflow.CustomUserEntityManager;
import com.novacroft.nemo.tfl.innovator.workflow.WorkFlowGeneratorStrategy;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class WorkFlowServiceTest {

    private static final String TEST_WORKFLOW_ITEM_ID = "1";
    
    private WorkFlowServiceImpl workFlowService;
    private WorkFlowServiceImpl mockService;
    private TaskService mockTaskService;
    private RuntimeService mockRuntimeService;
    private HistoryService mockHistoryService;
    private HistoricTaskInstanceQuery mockHistoricTaskQuery;
    private List<HistoricTaskInstance> mockHistoricTaskList;
    private TaskQuery mockTaskQuery;
    private List<Task> mockTaskList;
    private Iterator<Task> mockTaskIterator;
    private Task mockTask;
    private Map<String, Object> mockVariableList;
    private Set<Entry<String, Object>> mockEntrySet;
    private Iterator<Entry<String, Object>> mockEntryIterator;
    private Entry<String, Object> mockEntry;
    private WorkflowItemDTO mockWorkflowItemDTO;
    private List<IdentityLink> mockIdentityLinklist;
    private IdentityLink mockIdentityLink;
    private RefundDetailDTO mockRefundDetailDTO;
    private CaseHistoryNoteService mockCaseHistoryNoteService;
    private List<CaseHistoryNote> mockCaseNotes;
    private WorkflowDataService mockWorkflowDataService;
    private WorkflowSearchService mockWorkflowSearchService;
    private ArrayList<WorkflowItemDTO> workflowItemList;
    private CartCmdImpl mockCartCmd;
    private RefundToWorkflowConverter mockRefundToWorkflowConverter;
    private Refund mockRefund;
    private CardService mockCardService;
    private CartService mockCartService;
    private CartAdministrationService mockCartAdminService;
    private CustomUserEntityManager mockCustomUserEntityManager;
    private CustomerDataService mockCustomerDataService;
    private AddressDataService mockAddressDataService;
    private RefundCalculationBasisService mockRefundCalculationService;
    private CartDTO mockCartDto;
    private RepositoryService mockRepositoryService;
    private CardDTO card;
    private WorkflowItemConverter mockWorkflowConverter;
    private AdministrationFeeDataService mockAdministrationFeeDataService;
    private CustomerDataService mockCustomerService;
    private CardDataService mockCardDataService;
    private GetCardService mockGetCardService;
    private CardInfoResponseV2DTO mockCardInfoResponseV2DTO;
    private WorkflowCmd mockWorkflowCmd;
    private WorkflowEditCmd mockWorkflowEditCmd;
    private EditRefundPaymentService mockEditRefundPaymentService;
    private WorkFlowGeneratorStrategy mockWorkFlowGeneratorStrategy;
    private WorkflowEditService mockWorkflowEditService;
    private ContentDataService mockContentDataService;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        workFlowService = new WorkFlowServiceImpl();
        
    	mockWorkFlowGeneratorStrategy = mock(WorkFlowGeneratorStrategy.class);
        mockTaskService = mock(TaskService.class);
        mockRuntimeService = mock(RuntimeService.class);
        mockHistoryService = mock(HistoryService.class);
        mockHistoricTaskQuery = mock(HistoricTaskInstanceQuery.class);
        mockTaskQuery = mock(TaskQuery.class);
        mockTask = mock(Task.class);
        mockTaskList = mock(List.class);
        mockTaskIterator = mock(Iterator.class);
        mockVariableList = mock(Map.class);
        mockEntrySet = mock(Set.class);
        mockEntryIterator = mock(Iterator.class);
        mockEntry = mock(Entry.class);
        mockWorkflowItemDTO = mock(WorkflowItemDTO.class);
        mockIdentityLinklist = mock(List.class);
        mockIdentityLink = mock(IdentityLink.class);
        mockRefundDetailDTO = mock(RefundDetailDTO.class);
        mockCaseHistoryNoteService = mock(CaseHistoryNoteService.class);
        mockCaseNotes = mock(List.class);
        mockWorkflowDataService = mock(WorkflowDataService.class);
        mockWorkflowSearchService = mock(WorkflowSearchService.class);
        mockCartCmd = mock(CartCmdImpl.class);
        mockRefundToWorkflowConverter = mock(RefundToWorkflowConverter.class);
        mockRefund = mock(Refund.class);
        mockCardService = mock(CardService.class);
        mockCartService = mock(CartService.class);
        mockCartAdminService = mock(CartAdministrationService.class);
        mockCustomUserEntityManager = mock(CustomUserEntityManager.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockAddressDataService = mock(AddressDataService.class);
        mockCustomUserEntityManager = mock(CustomUserEntityManager.class);
        mockCustomerDataService = mock(CustomerDataService.class);
        mockAddressDataService = mock(AddressDataService.class);
        mockRefundCalculationService = mock(RefundCalculationBasisService.class);
        mockCustomerService = mock(CustomerDataService.class);
        mockCardDataService = mock(CardDataService.class);
        mockCartDto = mock(CartDTO.class);
        mockGetCardService = mock(GetCardService.class);
        mockCardInfoResponseV2DTO = mock(CardInfoResponseV2DTO.class);
        mockWorkflowCmd = mock(WorkflowCmd.class);
        mockWorkflowEditCmd = mock(WorkflowEditCmd.class);
        mockWorkflowConverter = mock(WorkflowItemConverter.class);
        mockRepositoryService = mock(RepositoryServiceImpl.class);
        mockEditRefundPaymentService = mock(EditRefundPaymentService.class); 
        mockAdministrationFeeDataService = mock(AdministrationFeeDataService.class);
        mockWorkflowEditService = mock(WorkflowEditService.class);
        mockContentDataService = mock(ContentDataService.class);

        workFlowService.workflowDataService = mockWorkflowDataService;
        workFlowService.cardService = mockCardService;
        workFlowService.cartService = mockCartService;
        workFlowService.refundCalculationBasisService = mockRefundCalculationService;
        workFlowService.workflowItemConverter = mockWorkflowConverter;
        workFlowService.getCardService = mockGetCardService;
        workFlowService.administrationFeeDataService = mockAdministrationFeeDataService;
        workFlowService.addressService = mockAddressDataService;
        workFlowService.customerDataService = mockCustomerDataService;
        workFlowService.workflowEditService = mockWorkflowEditService;
        workFlowService.editRefundPaymentService = mockEditRefundPaymentService;
        workFlowService.content = mockContentDataService;
        workFlowService.setWorkflowSearchService(mockWorkflowSearchService);
        workFlowService.setRepositoryService(mockRepositoryService);
        workFlowService.setTaskService(mockTaskService);
        workFlowService.setHistoryService(mockHistoryService);
        workFlowService.setRuntimeService(mockRuntimeService);
        workFlowService.setCaseHistoryNoteService(mockCaseHistoryNoteService);
        
        mockService = spy(workFlowService);

        when(mockTaskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskService.getVariables(anyString(), any(Collection.class))).thenReturn(mockVariableList);

        when(mockCartService.findById(anyLong())).thenReturn(mockCartDto);
        doReturn(card).when(mockCardDataService).findByCardNumber(anyString());

        when(
                        mockRefundCalculationService.getRefundCalculationBasis(anyString(), anyString(), anyLong(), anyString(), anyString(),
                                        anyInt(), anyInt(), anyBoolean(), anyBoolean())).thenReturn(RefundCalculationBasis.PRO_RATA.code());
        when(mockAdministrationFeeDataService.findByType(anyString())).thenReturn(getTestAdministrationFeeDTO1());
        when(mockCustomerService.findByCardNumber(anyString())).thenReturn(getTestCustomerDTO1());
        when(mockTaskService.createTaskQuery()).thenReturn(mockTaskQuery);
        when(mockTaskService.getVariables(anyString(), any(Collection.class))).thenReturn(mockVariableList);
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO12());
        when(mockCardInfoResponseV2DTO.getPpvDetails()).thenReturn(PrePayValueTestUtil.getPendingItemsPpvTestWithPositiveBalance());

        when(mockHistoryService.createHistoricTaskInstanceQuery()).thenReturn(mockHistoricTaskQuery);
        when(mockHistoricTaskQuery.processInstanceId(anyString())).thenReturn(mockHistoricTaskQuery);
        when(mockHistoricTaskQuery.includeTaskLocalVariables()).thenReturn(mockHistoricTaskQuery);
        when(mockHistoricTaskQuery.orderByHistoricTaskInstanceDuration()).thenReturn(mockHistoricTaskQuery);
        when(mockHistoricTaskQuery.desc()).thenReturn(mockHistoricTaskQuery);
        when(mockHistoricTaskQuery.list()).thenReturn(mockHistoricTaskList);
        when(mockTaskQuery.processVariableValueEquals(anyString(), anyObject())).thenReturn(mockTaskQuery);
        when(mockTaskQuery.singleResult()).thenReturn(mockTask);
        when(mockTaskQuery.list()).thenReturn(mockTaskList);
        when(mockTaskList.iterator()).thenReturn(mockTaskIterator);
        when(mockTaskIterator.next()).thenReturn(mockTask);
        when(mockVariableList.entrySet()).thenReturn(mockEntrySet);
        when(mockVariableList.get("workflowItem")).thenReturn(mockWorkflowItemDTO);
        when(mockEntrySet.iterator()).thenReturn(mockEntryIterator);
        when(mockEntryIterator.next()).thenReturn(mockEntry);
        when(mockTaskService.getIdentityLinksForTask(anyString())).thenReturn(mockIdentityLinklist);
        when(mockIdentityLinklist.get(anyInt())).thenReturn(mockIdentityLink);
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(mockRefundDetailDTO);
        when(mockRefundDetailDTO.getRefundEntity()).thenReturn(mockRefund);
        when(mockRefundDetailDTO.getCustomer()).thenReturn(getTestCustomerDTO1());
        when(mockRefund.getRefundAmount()).thenReturn(10L);
        when(mockWorkflowDataService.getWorkflowItem(anyString())).thenReturn(mockWorkflowItemDTO);
        workflowItemList = new ArrayList<WorkflowItemDTO>();
        when(mockWorkflowSearchService.findBySearchCriteria(any(ApprovalListCmdImpl.class))).thenReturn(workflowItemList);
        when(mockWorkflowSearchService.findAllByGroup(any(AgentGroup.class))).thenReturn(workflowItemList);
        when(mockWorkflowSearchService.findAllByGroup(anyString())).thenReturn(workflowItemList);
        when(mockWorkflowSearchService.findAllByUser(anyString())).thenReturn(workflowItemList);
        when(mockCartService.findById(any(Long.class))).thenReturn(CartTestUtil.getTestCartDTO1());
        when(mockCartAdminService.addApprovalId(any(CartDTO.class))).thenReturn(new Long(100));
        
        doCallRealMethod().when(mockRefundToWorkflowConverter).convert(any(CartCmdImpl.class));
        doCallRealMethod().when(mockRefundToWorkflowConverter).convert(any(CartCmdImpl.class));

    }

    @Test
    public void shouldCallDataServiceForGetWorkflowItem() {
        WorkflowItemDTO result = workFlowService.getWorkflowItem("0");
        verify(mockWorkflowDataService).getWorkflowItem(anyString());
        assertEquals(mockWorkflowItemDTO, result);
    }
    
    @Test
    public void shouldCallDataServiceForGetHistoricWorkflowItem() {
        when(mockWorkflowDataService.getHistoricWorkflowItem(anyString())).thenReturn(mockWorkflowItemDTO);
        
        assertEquals(mockWorkflowItemDTO, workFlowService.getHistoricWorkflowItem(""));
        verify(mockWorkflowDataService).getHistoricWorkflowItem(anyString());
    }
    
    @Test
    public void shouldCallEditServiceForSaveChanges() {
        when(mockWorkflowEditService.saveChanges(any(WorkflowCmd.class), any(WorkflowEditCmd.class))).thenReturn(mockWorkflowCmd);
        
        assertEquals(mockWorkflowCmd, workFlowService.saveChanges(mockWorkflowCmd, mockWorkflowEditCmd));
        verify(mockWorkflowEditService).saveChanges(mockWorkflowCmd, mockWorkflowEditCmd);
    }
    
    @Test
    public void shouldCallCartServiceForGetCartDtoFromCardId() {
        when(mockCartService.findNotInWorkFlowFlightCartByCardId(anyLong())).thenReturn(mockCartDto);
        
        assertEquals(mockCartDto, workFlowService.getCartDtoFromCardId(CARD_ID));
        verify(mockCartService).findNotInWorkFlowFlightCartByCardId(CARD_ID);
    }
    
    @Test
    public void shouldCallTaskServiceForGetLocalPayeePayment() {
        when(mockTaskService.getVariableLocal(anyString(), anyString())).thenReturn(null);
        
        workFlowService.getLocalPayeePayment(REFUND_IDENTIFIER);
        verify(mockTaskService).getVariableLocal(REFUND_IDENTIFIER, "refundEditPaymentInfo");
    }
    
    @Test
    public void shouldCallContentServiceForInit() {
        ContentDTO testContentDto = mock(ContentDTO.class);
        when(testContentDto.getContent()).thenReturn("");
        when(mockContentDataService.findByLocaleAndCode(anyString(), anyString())).thenReturn(testContentDto);
        
        workFlowService.init();
        
        verify(mockContentDataService).findByLocaleAndCode(anyString(), anyString());
    }

    @Test
    public void shouldRejectCaseWithReasonDuplicate() {
        when(mockIdentityLinklist.get(anyInt())).thenReturn(mockIdentityLink);
        when(mockIdentityLink.getGroupId()).thenReturn(AgentGroup.FIRST_STAGE_APPROVER.code());
        WorkflowRejectCmd cmd = new WorkflowRejectCmd();
        cmd.setRejectReason("Duplicate");
        shouldRejectCase(cmd);
    }

    @Test
    public void shouldRejectCaseWithReasonOther() {
        when(mockIdentityLink.getGroupId()).thenReturn(AgentGroup.SECOND_STAGE_APPROVER.code());
        WorkflowRejectCmd cmd = new WorkflowRejectCmd();
        cmd.setRejectReason("Other");
        cmd.setRejectFreeText("Other");
        shouldRejectCase(cmd);
    }

    public void shouldRejectCase(WorkflowRejectCmd cmd) {
        doCallRealMethod().when(mockService).reject(anyString(), any(WorkflowRejectCmd.class));
        doCallRealMethod().when(mockService).getWorkflowItem(anyString());
        doCallRealMethod().when(mockService).setRejectionBooleans(any(AgentGroup.class), any(WorkflowItemDTO.class));
        when(mockCaseHistoryNoteService.addRejectionCaseNotes(any(WorkflowRejectCmd.class), any(WorkflowItemDTO.class))).thenReturn(mockCaseNotes);
        when(mockWorkflowItemDTO.getStatus()).thenReturn("Rejected");
        mockService.reject("1", cmd);
        verify(mockService).setRejectionBooleans(any(AgentGroup.class), any(WorkflowItemDTO.class));
        verify(mockCaseHistoryNoteService).addRejectionCaseNotes(any(WorkflowRejectCmd.class), any(WorkflowItemDTO.class));
    }

    @Test
    public void shouldCallSearchServiceForFindBySearchCriteriaFromRefundSearch() {
        when(mockWorkflowSearchService.findBySearchCriteria(any(RefundSearchCmd.class))).thenReturn(workflowItemList);
        RefundSearchCmd search = new RefundSearchCmd();
        List<WorkflowItemDTO> result = workFlowService.findBySearchCriteria(search);
        verify(mockWorkflowSearchService).findBySearchCriteria(search);
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldCallSearchServiceForFindBySearchCriteria() {
        ApprovalListCmdImpl search = new ApprovalListCmdImpl();
        List<WorkflowItemDTO> result = workFlowService.findBySearchCriteria(search);
        verify(mockWorkflowSearchService).findBySearchCriteria(search);
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldCallSearchServiceForFindByGroupAgentGroup() {
        AgentGroup group = AgentGroup.FIRST_STAGE_APPROVER;
        List<WorkflowItemDTO> result = workFlowService.findAllByGroup(group);
        verify(mockWorkflowSearchService).findAllByGroup(group);
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldCallSearchServiceForFindByGroupString() {
        String group = "FirstStageApprover";
        List<WorkflowItemDTO> result = workFlowService.findAllByGroup(group);
        verify(mockWorkflowSearchService).findAllByGroup(group);
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldCallSearchServiceForFindByUser() {
        String user = AgentGroup.FIRST_STAGE_APPROVER.code();
        List<WorkflowItemDTO> result = workFlowService.findAllByUser(user);
        verify(mockWorkflowSearchService).findAllByUser(user);
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldCallSearchServiceForFindAll() {
        when(mockWorkflowSearchService.findAll()).thenReturn(workflowItemList);
        List<WorkflowItemDTO> result = workFlowService.findAll();
        verify(mockWorkflowSearchService).findAll();
        assertEquals(ArrayList.class, result.getClass());
    }

    @Test
    public void shouldCallSearchServiceForFindHistoricRefundsByAgent() {
        workFlowService.findHistoricRefundsByAgent("Test", "Test");
        verify(mockWorkflowSearchService).findHistoricRefundsByAgent(anyString(), anyString());
    }

    @Test
    public void shouldCallSearchServiceForFindHistoricRefundsByCardNumber() {
        workFlowService.findHistoricRefundsByCardNumber("0", "y");
        verify(mockWorkflowSearchService).findHistoricRefundsByCardNumber(anyString(), anyString());
    }

    @Test
    public void testApproveForFirstStageApprover() {
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(mockRefundDetailDTO);
        doReturn(mockWorkflowItemDTO).when(mockTaskService).getVariable(anyString(), anyString());
        doReturn(mockIdentityLinklist).when(mockTaskService).getIdentityLinksForTask(anyString());
        doReturn(AgentGroup.FIRST_STAGE_APPROVER.code()).when(mockIdentityLink).getGroupId();
        doCallRealMethod().when(mockService).approve(anyString());
        
        assertEquals(AgentGroup.FIRST_STAGE_APPROVER, mockService.approve(TEST_WORKFLOW_ITEM_ID));
        verify(mockRefundDetailDTO).setFirstStageApprovalGiven(Boolean.TRUE);
    }
    
    @Test
    public void testApproveForSecondStageApprover() {
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(mockRefundDetailDTO);
        doReturn(mockWorkflowItemDTO).when(mockTaskService).getVariable(anyString(), anyString());
        doReturn(mockIdentityLinklist).when(mockTaskService).getIdentityLinksForTask(anyString());
        doReturn(AgentGroup.SECOND_STAGE_APPROVER.code()).when(mockIdentityLink).getGroupId();
        doCallRealMethod().when(mockService).approve(anyString());
        
        assertEquals(AgentGroup.SECOND_STAGE_APPROVER, mockService.approve(TEST_WORKFLOW_ITEM_ID));
        verify(mockRefundDetailDTO).setSecondStageApprovalGiven(Boolean.TRUE);
    }
    
    @Test
    public void testApproveForAgentGroup() {
        doReturn(mockWorkflowItemDTO).when(mockTaskService).getVariable(anyString(), anyString());
        doReturn(mockIdentityLinklist).when(mockTaskService).getIdentityLinksForTask(anyString());
        doReturn(AgentGroup.AGENT.code()).when(mockIdentityLink).getGroupId();
        doCallRealMethod().when(mockService).approve(anyString());
        
        assertEquals(AgentGroup.AGENT, mockService.approve(TEST_WORKFLOW_ITEM_ID));
    }
    
    @Test
    public void testApproveForExceptionGroup() {
        when(mockWorkflowItemDTO.getRefundDetails()).thenReturn(mockRefundDetailDTO);
        doReturn(mockWorkflowItemDTO).when(mockTaskService).getVariable(anyString(), anyString());
        doReturn(mockIdentityLinklist).when(mockTaskService).getIdentityLinksForTask(anyString());
        doReturn(AgentGroup.EXCEPTIONS.code()).when(mockIdentityLink).getGroupId();
        doCallRealMethod().when(mockService).approve(anyString());
        
        assertEquals(AgentGroup.FIRST_STAGE_APPROVER, mockService.approve(TEST_WORKFLOW_ITEM_ID));
        verify(mockRefundDetailDTO).setReassignedFromExceptionQueue(Boolean.TRUE);
    }

    @Test
    public void testGenerateWorkflowItem() {

        doNothing().when(mockService).createWorkflowDeploymentIfNotPresent();
        doCallRealMethod().when(mockService).generateWorkflowEntity(mockCartCmd, mockWorkFlowGeneratorStrategy);
        doReturn(mockWorkflowItemDTO).when(mockRefundToWorkflowConverter).convert(mockCartCmd);
        doReturn(mock(CustomerDTO.class)).when(mockCustomerDataService).findByCardNumber(anyString());
        when(mockCartCmd.getPaymentType()).thenReturn(PaymentType.CHEQUE.code());
        when(mockCartCmd.getPayeeAddress()).thenReturn(mock(AddressDTO.class));
        when(mockCartCmd.getPayeeAddress().getId()).thenReturn(1L);
        when(mockCartCmd.getCustomerId()).thenReturn(1L);
        when(mockCartCmd.getCartDTO()).thenReturn(CartTestUtil.getTestCartDTO1());
        
        mockService.generateWorkflowEntity(mockCartCmd, mockWorkFlowGeneratorStrategy);
        verify(mockRuntimeService, times(1)).startProcessInstanceByKey(anyString(), anyMapOf(String.class, Object.class));
    }

    @Test
    public void shouldReturnTrueIfAdminFeeHasChangedFromDefault() {

        assertTrue(workFlowService.hasAdminFeeChangedFromDefault(CartCmdTestUtil.getTestCartCmdForItemWithoutDefaultItems()));
    }

    @Test
    public void shouldReturnFalseIfAdminFeeHasNotChangedFromDefault() {
        assertFalse(workFlowService.hasAdminFeeChangedFromDefault(CartCmdTestUtil.getTestCartCmdItemWithDefaultItems()));
    }

    @Test
    public void shouldReturnTrueIfRefundCalculationHasChangedFromDefault() {
        when(mockCartService.findById(anyLong())).thenReturn(CartCmdTestUtil.getCartDtoWithoutValuesOfRefundCalculationBasis());
        assertTrue(workFlowService.hasCalculationBasisChangedFromDefault(CartCmdTestUtil.getRefundCalculationBasisChangedDefaultItems()));
    }

    @Test
    public void shouldReturnFalseIfRefundCalculationHasNotChangedFromDefault() {
        when(mockCartService.findById(anyLong())).thenReturn(CartCmdTestUtil.getCartDtoValuesWithDefaultfRefundCalculationBasis());
        assertFalse(workFlowService.hasCalculationBasisChangedFromDefault(CartCmdTestUtil.getRefundCalculationBasisChangedDefaultItems()));
    }
    
    @Test
    public void shouldReturnFalseIfNoTravelCardAndBusPass() {
        when(mockCartService.findById(anyLong())).thenReturn(mockCartDto);
        when(mockCartDto.getCartItems()).thenReturn(new ArrayList<ItemDTO>());
        when(mockCartCmd.getCartDTO()).thenReturn(mockCartDto);
        
        assertFalse(workFlowService.hasCalculationBasisChangedFromDefault(mockCartCmd));
    }

    @Test
    public void shouldReturnTrueIfPayAsYouGoHasChangedFromDefault() {
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO10());
        when(mockCartService.findById(anyLong())).thenReturn(CartCmdTestUtil.getTestCartCmdForItemWithoutDefaultItems().getCartDTO());
        assertTrue(workFlowService.hasPayAsYouGoChangedFromDefault(CartCmdTestUtil.getTestCartCmdForItemWithoutDefaultItems()));
    }

    @Test
    public void shouldReturnFalseIfPayAsYouGoHasNotChangedFromDefault() {
        when(mockGetCardService.getCard(anyString())).thenReturn(CardInfoResponseV2TestUtil.getTestCardInfoResponseV2DTO10());
        when(mockCartService.findById(anyLong())).thenReturn(CartCmdTestUtil.getTestCartCmdItemWithDefaultItems().getCartDTO());
        assertFalse(workFlowService.hasPayAsYouGoChangedFromDefault(CartCmdTestUtil.getTestCartCmdItemWithDefaultItems()));
    }

    @Test
    public void shouldAddCaseHistoryNote() {
        when(mockService.getWorkflowItem(anyString())).thenReturn(mockWorkflowItemDTO);
        when(mockCaseHistoryNoteService.addCaseNote(any(WorkflowItemDTO.class), anyString())).thenReturn(mockCaseNotes);
        doCallRealMethod().when(mockService).addCaseHistoryNote(anyString(), anyString());
        mockService.addCaseHistoryNote(REFUND_IDENTIFIER, StringUtil.EMPTY_STRING);
        verify(mockCaseHistoryNoteService).addCaseNote(any(WorkflowItemDTO.class), anyString());
        verify(mockRuntimeService).setVariable(anyString(), anyString(), anyObject());
    }

    @Test
    public void shouldClaimTask() {
        ArrayList<UserEntity> userEntityList = new ArrayList<UserEntity>();
        UserEntity user = new UserEntity();
        user.setId(AGENT);
        userEntityList.add(user);
        when(mockService.getCustomUserEntityManager()).thenReturn(mockCustomUserEntityManager);
        when(mockCustomUserEntityManager.findUsersByGroupIdHardcoded(anyString())).thenReturn(userEntityList);
        doNothing().when(mockTaskService).claim(anyString(), anyString());
        doNothing().when(mockWorkflowDataService).updateAgentAndAddClaimTime(anyString(), any(UserEntity.class), any(DateTime.class));
        when(mockTaskService.getIdentityLinksForTask(anyString())).thenReturn(mockIdentityLinklist);
        when(mockIdentityLinklist.get(anyInt())).thenReturn(mockIdentityLink);
        when(mockIdentityLink.getGroupId()).thenReturn(AgentGroup.FIRST_STAGE_APPROVER.code());
        doCallRealMethod().when(mockService).claim(anyString(), anyString());
        assertEquals(AgentGroup.FIRST_STAGE_APPROVER, mockService.claim(REFUND_IDENTIFIER, AgentGroup.FIRST_STAGE_APPROVER.code()));
        verify(mockWorkflowDataService).updateAgentAndAddClaimTime(anyString(), any(UserEntity.class), any(DateTime.class));
        verify(mockTaskService).claim(anyString(), anyString());
    }
    
    @Test
    public void createWorkflowBeanTest(){
        doCallRealMethod().when(mockService).createWorkflowBean(anyString(), anyBoolean());
        doReturn(mockWorkflowItemDTO).when(mockService).getWorkflowItem(anyString());
        doReturn(mockWorkflowCmd).when(mockWorkflowConverter).convert(any(WorkflowItemDTO.class));
        WorkflowCmd testBean = mockService.createWorkflowBean("test",false);
        assertNotNull(testBean);
        assertEquals(testBean.getEdit(),false);
    }

    @Test
    public void shouldCheckCartDTOIsTypeOfGoodwillPaymentDTO() {
        doCallRealMethod().when(mockService).isItemDtoTypeOfGoodwillPaymentDTO(any(ItemDTO.class));
        assertTrue(mockService.isItemDtoTypeOfGoodwillPaymentDTO(GoodwillPaymentTestUtil.getGoodwillPaymentItemDTOItem1()));

    }

    @Test
    public void shouldCheckCartDTOisTypeOfTravelCardorBusPass() {
        doCallRealMethod().when(mockService).isTravelCardOrBusPass(any(ItemDTO.class));
        assertTrue(mockService.isTravelCardOrBusPass(RefundWorkflowTestUtil.getProductItemDTO()));
    }

    @Test
    public void shouldCheckCartDTOisTypeOfAdministrationFeeDTO() {
        doCallRealMethod().when(mockService).isItemDtoTypeOfAdministrationFeeItemDto(any(ItemDTO.class));
        assertTrue(mockService.isItemDtoTypeOfAdministrationFeeItemDto(AdministrationFeeItemTestUtil.getTestAdministrationFeeItemDTO1()));
    }

    @Test
    public void shouldCheckCartDTOisTypeOfPayAsyouGoCreditDTO() {
        doCallRealMethod().when(mockService).isItemDtoTypeOfPayAsYouGoDTO(any(ItemDTO.class));
        assertTrue(mockService.isItemDtoTypeOfPayAsYouGoDTO(PayAsYouGoItemTestUtil.getTestPayAsYouGoItemDTO1()));
    }
    
    @Test
    public void testCreateWorkflowDeploymentIfNotPresent(){
        ProcessDefinitionQuery mockProcessdef = mock(ProcessDefinitionQuery.class);
        DeploymentBuilder mockDeploymentBuilder = mock(DeploymentBuilder.class);
        Deployment mockDeployment =  mock(Deployment.class);
        
        
        doReturn(mockProcessdef).when(mockProcessdef).processDefinitionName(anyString());
        doReturn(new ArrayList<ProcessDefinition>()).when(mockProcessdef).list();
        doReturn(mockProcessdef).when(mockRepositoryService).createProcessDefinitionQuery();
        doReturn(mockDeploymentBuilder).when(mockDeploymentBuilder).addClasspathResource(anyString());
        doReturn(mockDeployment).when(mockDeploymentBuilder).deploy();
        doReturn(mockDeploymentBuilder).when(mockRepositoryService).createDeployment();
        doCallRealMethod().when(mockService).createWorkflowDeploymentIfNotPresent();
        
        
        mockService.createWorkflowDeploymentIfNotPresent();
        
        verify(mockDeploymentBuilder, Mockito.atMost(1)).deploy();
        verify(mockProcessdef, Mockito.atMost(1)).list();
      
    }
    
    @Test(expected=ApplicationServiceException.class)
    public void testCreateWorkflowDeploymentIfNotPresentWithException() {
        ProcessDefinitionQuery mockQuery = mock(ProcessDefinitionQuery.class);
        when(mockQuery.processDefinitionName(anyString())).thenReturn(mockQuery);
        when(mockQuery.list()).thenReturn(Collections.<ProcessDefinition>emptyList());
        
        when(mockRepositoryService.createProcessDefinitionQuery()).thenReturn(mockQuery);
        when(mockRepositoryService.createDeployment()).thenReturn(null);
        
        workFlowService.createWorkflowDeploymentIfNotPresent();
    }
    
    @Test
    public void testWorkflowCloseMethod() {

        doReturn(mockWorkflowItemDTO).when(mockTaskService).getVariable(anyString(), anyString());
        doReturn(mockIdentityLinklist).when(mockTaskService).getIdentityLinksForTask(anyString());
        doReturn(AgentGroup.EXCEPTIONS.code()).when(mockIdentityLink).getGroupId();
        doCallRealMethod().when(mockService).close(anyString());
        
        AgentGroup group = mockService.close("1");
        
        assertEquals(group, AgentGroup.EXCEPTIONS);

    }
    
    @Test
    public void getChangesAfterEditRefundsShouldInvokeService(){
    	when(mockWorkflowDataService.getWorkflowItem(anyString())).thenReturn(mockWorkflowItemDTO);
    	when(mockEditRefundPaymentService.getModifiedRefundPaymentFields(any(WorkflowItemDTO.class), any(CartCmdImpl.class))).thenReturn(getPayeePaymentDTO1());
    	doCallRealMethod().when(mockService).getChangesAfterEditRefunds(any(CartCmdImpl.class));
    	
    	mockService.getChangesAfterEditRefunds(mockCartCmd);
    	verify(mockTaskService).setVariableLocal(anyString(), anyString(), any(Object.class));
    }

}
