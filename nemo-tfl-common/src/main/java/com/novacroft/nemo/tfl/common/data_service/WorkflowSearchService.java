package com.novacroft.nemo.tfl.common.data_service;

import java.util.List;

import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.constant.AgentGroup;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public interface WorkflowSearchService {

    List<WorkflowItemDTO> findBySearchCriteria(ApprovalListCmdImpl search);

    List<WorkflowItemDTO> findAllByGroup(String group);

    List<WorkflowItemDTO> findAllByGroup(AgentGroup group);

    List<WorkflowItemDTO> findAllByUser(String user);

    List<WorkflowItemDTO> findAll();

    List<WorkflowItemDTO> findBySearchCriteria(RefundSearchCmd search);

    List<WorkflowItemDTO> findHistoricRefundsByAgent(String firstName, String lastName);

    List<WorkflowItemDTO> findHistoricRefundsByCardNumber(String cardNumber, String exact);

}
