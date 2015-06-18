package com.novacroft.nemo.tfl.common.application_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.CartCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowRejectCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.PayeePaymentDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.WorkFlowGeneratorStrategy;

public interface WorkFlowService {

    WorkflowItemDTO getWorkflowItem(String caseNumber);

    WorkflowItemDTO getHistoricWorkflowItem(String caseNumber);

    WorkflowCmd saveChanges(WorkflowCmd workflowCmd, WorkflowEditCmd cmd);

    AgentGroup approve(String workflowItemId);

    AgentGroup close(String workflowItemId);

    AgentGroup reject(String workflowItemId, WorkflowRejectCmd cmd);

    List<WorkflowItemDTO> findBySearchCriteria(RefundSearchCmd search);

    List<WorkflowItemDTO> findBySearchCriteria(ApprovalListCmdImpl search);

    List<WorkflowItemDTO> findAllByGroup(AgentGroup group);

    List<WorkflowItemDTO> findAllByGroup(String group);

    List<WorkflowItemDTO> findAllByUser(String user);

    List<WorkflowItemDTO> findAll();

    List<WorkflowItemDTO> findHistoricRefundsByAgent(String firstName, String lastName);

    List<WorkflowItemDTO> findHistoricRefundsByCardNumber(String cardNumber, String exact);

    String generateWorkflowEntity(CartCmdImpl cmd, WorkFlowGeneratorStrategy workFlowGeneratorStrategy);

    void addCaseHistoryNote(String workflowItemId, String note);

    boolean hasPayAsYouGoChangedFromDefault(CartCmdImpl cmd);

    boolean hasAdminFeeChangedFromDefault(CartCmdImpl cmd);

    boolean hasCalculationBasisChangedFromDefault(CartCmdImpl cmd);

    boolean isTravelCardOrBusPass(ItemDTO cartItemDto);

    boolean isItemDtoTypeOfAdministrationFeeItemDto(ItemDTO cartItemDto);

    WorkflowCmd createWorkflowBean(String caseNumber, Boolean edit);

    CartDTO getCartDtoFromCardId(Long cardId);

    AgentGroup claim(String caseNumber, String agent);

    boolean isItemDtoTypeOfGoodwillPaymentDTO(ItemDTO cartItemDto);

    boolean isItemDtoTypeOfPayAsYouGoDTO(ItemDTO cartItemDto);
    
    void getChangesAfterEditRefunds(CartCmdImpl cartCmdImpl);
    
    PayeePaymentDTO getLocalPayeePayment(String taskId);
}
