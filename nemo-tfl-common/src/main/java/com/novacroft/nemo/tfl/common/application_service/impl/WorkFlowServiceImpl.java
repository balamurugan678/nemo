package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.utils.TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.AGENT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.LOCAL_STATUS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.REFUND_EDIT_PAYMENT_INFO;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.REFUND_IDENTIFIER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.STATUS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.WORKFLOW_ITEM;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.activiti.engine.HistoryService;
import org.activiti.engine.IdentityService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.domain.cubic.PrePayValue;
import com.novacroft.nemo.common.exception.ApplicationServiceException;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.tfl.common.application_service.CardService;
import com.novacroft.nemo.tfl.common.application_service.CartService;
import com.novacroft.nemo.tfl.common.application_service.CaseHistoryNoteService;
import com.novacroft.nemo.tfl.common.application_service.CommonRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.EditRefundPaymentService;
import com.novacroft.nemo.tfl.common.application_service.RefundCalculationBasisService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.application_service.WorkflowEditService;
import com.novacroft.nemo.tfl.common.application_service.cubic.GetCardService;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.constant.TicketType;
import com.novacroft.nemo.tfl.common.constant.WorkFlowStatus;
import com.novacroft.nemo.tfl.common.constant.WorkflowFields;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.data_service.AdministrationFeeDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowSearchService;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeDTO;
import com.novacroft.nemo.tfl.common.transfer.AdministrationFeeItemDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.GoodwillPaymentItemDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayAsYouGoItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.transfer.cubic.CardInfoResponseV2DTO;
import com.novacroft.nemo.tfl.innovator.workflow.CustomUserEntityManager;
import com.novacroft.nemo.tfl.innovator.workflow.WorkFlowGeneratorStrategy;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Service("workFlowService")
public class WorkFlowServiceImpl implements WorkFlowService  {
    protected static final Logger logger = LoggerFactory.getLogger(WorkFlowServiceImpl.class);
    private static final String NEW_WORKFLOW_APPROVAL_ITEM_TEXT_KEY = "workflowItem.display.newItem";
    private static final String WORKFLOW_PROCESS_NAME = "Refundprocess";

    @Autowired(required = false)
    protected TaskService taskService;
    @Autowired(required = false)
    protected HistoryService historyService;
    @Autowired(required = false)
    protected RuntimeService runtimeService;
    @Autowired(required = false)
    protected RepositoryService repositoryService;
    @Autowired(required = false)
    protected IdentityService identityService;
    @Autowired
    protected CustomerDataService customerDataService;
    @Autowired
    protected AddressDataService addressService;
    @Autowired
    protected CardService cardService;
    @Autowired
    protected CaseHistoryNoteService caseHistoryNoteService;
    @Autowired
    protected WorkflowSearchService workflowSearchService;
    @Autowired
    protected WorkflowEditService workflowEditService;
    @Autowired
    protected WorkflowDataService workflowDataService;
    @Autowired
    protected CartService cartService;
    @Autowired
    protected WorkflowItemConverter workflowItemConverter;
    @Autowired
    protected GetCardService getCardService;
    @Autowired
    protected AdministrationFeeDataService administrationFeeDataService;
    @Autowired
    protected RefundCalculationBasisService refundCalculationBasisService;
    @Autowired
    protected CommonRefundPaymentService commonRefundPaymentService;
    @Autowired
    protected EditRefundPaymentService editRefundPaymentService;
    @Autowired
    protected ContentDataService content;
    protected String newWorkflowApprovalItemText;
    
    @Override
    public WorkflowItemDTO getWorkflowItem(String caseNumber) {
        return workflowDataService.getWorkflowItem(caseNumber);
    }

    @Override
    public WorkflowItemDTO getHistoricWorkflowItem(String caseNumber) {
        return workflowDataService.getHistoricWorkflowItem(caseNumber);
    }

    @Override
    public WorkflowCmd saveChanges(WorkflowCmd workflowCmd, WorkflowEditCmd workFlowEditCmd) {
        return workflowEditService.saveChanges(workflowCmd, workFlowEditCmd);
    }
    
    @PostConstruct
    public void init(){
        newWorkflowApprovalItemText = content.findByLocaleAndCode(NEW_WORKFLOW_APPROVAL_ITEM_TEXT_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        
    }
    
    @Override
    public AgentGroup approve(String workflowItemId) {
        Task task = taskService.createTaskQuery().processVariableValueEquals(REFUND_IDENTIFIER, workflowItemId).singleResult();
        String taskId = task.getId();
        List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskId);
        String stage = idList.get(0).getGroupId();

        WorkflowItemDTO workflowItem = (WorkflowItemDTO) taskService.getVariable(taskId, WORKFLOW_ITEM);
        workflowItem.setStatus(WorkFlowStatus.APPROVED.code());

        RefundDetailDTO refundDetail = workflowItem.getRefundDetails();
        AgentGroup agentGroup = AgentGroup.find(stage);

        workflowItem.getRefundDetails().setApprovalRequired(Boolean.TRUE);
        if (agentGroup.equals(AgentGroup.AGENT)) {
            refundDetail.setApprovalRequired(Boolean.TRUE);
        } else if (agentGroup.equals(AgentGroup.FIRST_STAGE_APPROVER)) {
            workflowItem.getRefundDetails().setFirstStageApprovalGiven(Boolean.TRUE);
        } else if (agentGroup.equals(AgentGroup.SECOND_STAGE_APPROVER)) {
            workflowItem.getRefundDetails().setSecondStageApprovalGiven(Boolean.TRUE);
        }else if (agentGroup.equals(AgentGroup.EXCEPTIONS)) {
            workflowItem.getRefundDetails().setReassignedFromExceptionQueue(Boolean.TRUE);
            workflowItem.setStatus(WorkFlowStatus.PENDING_APPROVAL.code());
            workflowItem.setAgent(StringUtils.EMPTY);
            runtimeService.setVariable(workflowItem.getExecutionId(), AGENT, StringUtils.EMPTY);
            agentGroup = AgentGroup.FIRST_STAGE_APPROVER;
        }

        runtimeService.setVariable(task.getExecutionId(), WORKFLOW_ITEM, workflowItem);
        taskService.setVariableLocal(taskId, LOCAL_STATUS, workflowItem.getStatus());
        taskService.complete(taskId);
        return agentGroup;
    }

    @Override
    public AgentGroup reject(String workflowItemId, WorkflowRejectCmd cmd) {
        WorkflowItemDTO workflowItem = getWorkflowItem(workflowItemId);
        List<IdentityLink> idList = taskService.getIdentityLinksForTask(workflowItem.getTaskId());

        AgentGroup agentGroup = AgentGroup.find(idList.get(0).getGroupId());
        workflowItem = setRejectionBooleans(agentGroup, workflowItem);

        workflowItem.setStatus(WorkFlowStatus.REJECTED.code());
        workflowItem.setCaseNotes(caseHistoryNoteService.addRejectionCaseNotes(cmd, workflowItem));

        runtimeService.setVariable(workflowItem.getExecutionId(), WORKFLOW_ITEM, workflowItem);
        runtimeService.setVariable(workflowItem.getExecutionId(), STATUS, workflowItem.getStatus().toUpperCase());
        taskService.setVariableLocal(workflowItem.getTaskId(), LOCAL_STATUS, workflowItem.getStatus());
        taskService.complete(workflowItem.getTaskId());
        return AgentGroup.EXCEPTIONS;
    }

    protected WorkflowItemDTO setRejectionBooleans(AgentGroup agentGroup, WorkflowItemDTO workflowItem) {
        if (agentGroup.equals(AgentGroup.FIRST_STAGE_APPROVER)) {
            workflowItem.getRefundDetails().setFirstStageApprovalGiven(Boolean.FALSE);
        } else if (agentGroup.equals(AgentGroup.SECOND_STAGE_APPROVER)) {
            workflowItem.getRefundDetails().setSecondStageApprovalGiven(Boolean.FALSE);
        }
        workflowItem.getRefundDetails().setRefundRejected(Boolean.TRUE);
        return workflowItem;
    }

    @Override
    public List<WorkflowItemDTO> findBySearchCriteria(RefundSearchCmd search) {
        return workflowSearchService.findBySearchCriteria(search);
    }

    @Override
    public List<WorkflowItemDTO> findBySearchCriteria(ApprovalListCmdImpl search) {
        return workflowSearchService.findBySearchCriteria(search);
    }

    @Override
    public List<WorkflowItemDTO> findAllByGroup(AgentGroup group) {
        return workflowSearchService.findAllByGroup(group);
    }

    @Override
    public List<WorkflowItemDTO> findAllByGroup(String group) {
        return workflowSearchService.findAllByGroup(group);
    }

    @Override
    public List<WorkflowItemDTO> findAllByUser(String user) {
        return workflowSearchService.findAllByUser(user);
    }

    @Override
    public List<WorkflowItemDTO> findAll() {
        return workflowSearchService.findAll();
    }

    public void setWorkflowSearchService(WorkflowSearchService workflowSearchService) {
        this.workflowSearchService = workflowSearchService;
    }

    @Override
    @Transactional(propagation = Propagation.NESTED)
    public String generateWorkflowEntity(CartCmdImpl cartCmdImpl, WorkFlowGeneratorStrategy workFlowGeneratorStrategy) {
    	cartCmdImpl.getCartDTO().setWorkFlowInFlight(Boolean.TRUE);
    	cartService.updateCart(cartCmdImpl.getCartDTO());
    	cartService.postProcessAndSortCartDTOAndRecalculateRefund(cartCmdImpl.getCartDTO());
    	
        createWorkflowDeploymentIfNotPresent();
        Map<String, Object> workflowProcessVariables = new HashMap<String, Object>();
        workFlowGeneratorStrategy.setUpWorkFlowVariables(cartCmdImpl, workflowProcessVariables);
        workFlowGeneratorStrategy.setUpCustomerDetailsForWorkFlow(cartCmdImpl, workflowProcessVariables);
        ArrayList<String> reasons = new ArrayList<String>();
        reasons.add(newWorkflowApprovalItemText);
        String reasonsString = StringUtils.join(reasons, ";");
        workflowProcessVariables.put("approvalReasons", reasonsString);
        runtimeService.startProcessInstanceByKey("RefundProcess", workflowProcessVariables);
        logger.debug("Started Process");
        return cartCmdImpl.getApprovalId().toString();
    }

    @Override
    public CartDTO getCartDtoFromCardId(Long cardId) {
        return cartService.findNotInWorkFlowFlightCartByCardId(cardId);
    }

    @Override
    public boolean isItemDtoTypeOfAdministrationFeeItemDto(ItemDTO itemDto) {
        return itemDto instanceof AdministrationFeeItemDTO;
    }

    @Override
    public boolean isItemDtoTypeOfGoodwillPaymentDTO(ItemDTO cartItemDto) {
        return cartItemDto instanceof GoodwillPaymentItemDTO;
    }

    @Override
    public boolean isItemDtoTypeOfPayAsYouGoDTO(ItemDTO cartItemDto) {
        return cartItemDto instanceof PayAsYouGoItemDTO;
    }

    protected void createWorkflowDeploymentIfNotPresent() {

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().processDefinitionName(WORKFLOW_PROCESS_NAME).list();
        if (list.size() == 0) {
            try {
                repositoryService.createDeployment().addClasspathResource("RefundProcess.bpmn20.xml").deploy();
                logger.debug("Process deployed.");
            } catch (Exception e) {
                throw new ApplicationServiceException(e);
            }
        }
    }

    @Override
    public WorkflowCmd createWorkflowBean(String caseNumber, Boolean edit) {
        WorkflowItemDTO workflowItem = getWorkflowItem(caseNumber);
        WorkflowCmd workflowCommand = workflowItemConverter.convert(workflowItem);
        workflowCommand.setWorkflowItem(workflowItem);
        workflowCommand.setEdit(edit);
        return workflowCommand;
    }

    @Override
    public void addCaseHistoryNote(String workflowItemId, String note) {
        WorkflowItemDTO workflowItem = getWorkflowItem(workflowItemId);

        workflowItem.setCaseNotes(caseHistoryNoteService.addCaseNote(workflowItem, note));
        runtimeService.setVariable(workflowItem.getExecutionId(), WORKFLOW_ITEM, workflowItem);

    }

    @Override
    public List<WorkflowItemDTO> findHistoricRefundsByAgent(String firstName, String lastName) {
        return workflowSearchService.findHistoricRefundsByAgent(firstName, lastName);
    }

    @Override
    public List<WorkflowItemDTO> findHistoricRefundsByCardNumber(String cardNumber, String exact) {
        return workflowSearchService.findHistoricRefundsByCardNumber(cardNumber, exact);
    }

    @Override
    public boolean hasPayAsYouGoChangedFromDefault(CartCmdImpl cmd) {
        CartDTO cartDto = cartService.findById(cmd.getCartDTO().getId());
        PayAsYouGoItemDTO payAsYouGoItemDto = cartDto.getPayAsYouGoItem();
        CardInfoResponseV2DTO cardInfoResponseV2DTO = getCardProductDTOFromCubic(cmd.getCardNumber());
        PrePayValue ppvDetails = cardInfoResponseV2DTO != null ? cardInfoResponseV2DTO.getPpvDetails() : null; 
        final int itemValue = (payAsYouGoItemDto != null && payAsYouGoItemDto.getPrice() != null) ? payAsYouGoItemDto.getPrice() : 0;
        final int prePayValue = (ppvDetails != null && ppvDetails.getBalance() != null) ? ppvDetails.getBalance().intValue() : 0;
        return !(itemValue == prePayValue);
    }

    @Override
    public boolean hasAdminFeeChangedFromDefault(CartCmdImpl cmd) {
        AdministrationFeeDTO defaultAdministrationFee = administrationFeeDataService.findByType(TicketType.ADMINISTRATION_FEE.code()
                        + cmd.getCartType());
        AdministrationFeeItemDTO adminFeeDTO = cmd.getCartDTO().getAdministrationFeeItem();
        return ((adminFeeDTO != null) ? !adminFeeDTO.getPrice().equals(defaultAdministrationFee.getPrice()) : Boolean.FALSE);
    }

    @Override
    public boolean hasCalculationBasisChangedFromDefault(CartCmdImpl cmd) {
        CartDTO cartDto = cartService.findById(cmd.getCartDTO().getId());
        List<ItemDTO> cartItemsDto = cartDto.getCartItems();

        for (ItemDTO cartItemDto : cartItemsDto) {
            if (isTravelCardOrBusPass(cartItemDto)) {

                ProductItemDTO productItemDto = (ProductItemDTO) cartItemDto;
                String startDate = DateUtil.formatDate(productItemDto.getStartDate());
                String endDate = DateUtil.formatDate(productItemDto.getEndDate());
                String defaultRefundBasis = refundCalculationBasisService.getRefundCalculationBasis(endDate, productItemDto.getRefundType(),
                                cartDto.getCardId(), getTravelCardDurationFromStartAndEndDate(startDate, endDate), startDate,
                                productItemDto.getStartZone(), productItemDto.getEndZone(), productItemDto.getDeceasedCustomer(),true);
                return (!productItemDto.getRefundCalculationBasis().equalsIgnoreCase(defaultRefundBasis));
            }
        }

        return false;
    }

    @Override
    public boolean isTravelCardOrBusPass(ItemDTO cartItemDto) {
        return cartItemDto instanceof ProductItemDTO;
    }

    protected CardInfoResponseV2DTO getCardProductDTOFromCubic(String cardNumber) {
        return getCardService.getCard(cardNumber);
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public void setHistoryService(HistoryService historyService) {
        this.historyService = historyService;
    }

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void setCaseHistoryNoteService(CaseHistoryNoteService caseHistoryNoteService) {
        this.caseHistoryNoteService = caseHistoryNoteService;
    }



    @Override
    public AgentGroup claim(String caseNumber, String agent) {
        Task task = taskService.createTaskQuery().processVariableValueEquals(WorkflowFields.REFUND_IDENTIFIER, caseNumber).singleResult();

        CustomUserEntityManager customerUserManager = getCustomUserEntityManager();

        List<UserEntity> users = customerUserManager.findUsersByGroupIdHardcoded(AgentGroup.find(agent).code());
        UserEntity user = users.get(0);
        taskService.claim(task.getId(), user.getId());
        workflowDataService.updateAgentAndAddClaimTime(caseNumber, user, new DateTime());
        List<IdentityLink> idLinks = taskService.getIdentityLinksForTask(task.getId());
        return AgentGroup.find(idLinks.get(0).getGroupId());
    }

    protected CustomUserEntityManager getCustomUserEntityManager() {
        return new CustomUserEntityManager();
    }

    @Override
    public void getChangesAfterEditRefunds(CartCmdImpl cartCmdImpl){
    	WorkflowItemDTO  workflowItem = workflowDataService.getWorkflowItem(cartCmdImpl.getApprovalId().toString());
    	PayeePaymentDTO payeePayment = editRefundPaymentService.getModifiedRefundPaymentFields(workflowItem, cartCmdImpl);
    	taskService.setVariableLocal(workflowItem.getTaskId(), REFUND_EDIT_PAYMENT_INFO , payeePayment);
    }

	@Override
	public PayeePaymentDTO getLocalPayeePayment(String taskId) {
	    Object payeePayment = taskService.getVariableLocal(taskId, REFUND_EDIT_PAYMENT_INFO);
		return (payeePayment != null ? (PayeePaymentDTO) payeePayment : null); 
	}    

    @Override
    public AgentGroup close(String workflowItemId) {
        Task task = taskService.createTaskQuery().processVariableValueEquals(REFUND_IDENTIFIER, workflowItemId).singleResult();
        String taskId = task.getId();
        List<IdentityLink> idList = taskService.getIdentityLinksForTask(taskId);
        String stage = idList.get(0).getGroupId();

        WorkflowItemDTO workflowItem = (WorkflowItemDTO) taskService.getVariable(taskId, WORKFLOW_ITEM);
        workflowItem.setStatus(WorkFlowStatus.COMPLETE.code());

        AgentGroup agentGroup = AgentGroup.find(stage);

        workflowItem.getRefundDetails().setApprovalRequired(Boolean.FALSE);

        if (agentGroup.equals(AgentGroup.EXCEPTIONS)) {
            workflowItem.getRefundDetails().setReassignedFromExceptionQueue(Boolean.FALSE);
            workflowItem.setStatus(WorkFlowStatus.COMPLETE.code());
            workflowItem.setAgent(StringUtils.EMPTY);
            runtimeService.setVariable(workflowItem.getExecutionId(), AGENT, StringUtils.EMPTY);
        }

        runtimeService.setVariable(task.getExecutionId(), WORKFLOW_ITEM, workflowItem);
        taskService.setVariableLocal(taskId, LOCAL_STATUS, workflowItem.getStatus());
        taskService.complete(taskId);
        return agentGroup;

    }
}
