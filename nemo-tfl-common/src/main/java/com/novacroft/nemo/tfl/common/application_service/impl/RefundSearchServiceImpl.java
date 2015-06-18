package com.novacroft.nemo.tfl.common.application_service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.RefundSearchService;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.converter.impl.RefundSearchConverter;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundSearchDataService;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.RefundSearchResultDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service("refundSearchService")
public class RefundSearchServiceImpl implements RefundSearchService {
    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected RefundSearchConverter refundSearchConverter;
    @Autowired
    protected RefundSearchDataService refundSearchDataService;

    @Override
    public List<RefundSearchResultDTO> getAllRefunds() {
        List<WorkflowItemDTO> workflowList = workflowService.findAll();
        Set<OrderDTO> orderList = new HashSet<OrderDTO>(orderDataService.findAllRefunds());

        return convertToRefundSearchResults(workflowList, orderList);
    }

    @Override
    public List<RefundSearchResultDTO> findBySearchCriteria(RefundSearchCmd search) {
        return convertToRefundSearchResults(workflowService.findBySearchCriteria(search), refundSearchDataService.findBySearchCriteria(search));
    }

    protected List<RefundSearchResultDTO> convertToRefundSearchResults(List<WorkflowItemDTO> workflowList, Set<OrderDTO> orderSet) {
        List<RefundSearchResultDTO> resultList = new ArrayList<RefundSearchResultDTO>();

        for (WorkflowItemDTO workflowItem : workflowList) {
            resultList.add(refundSearchConverter.convertFromWorkflowItem(workflowItem));
        }

        for (OrderDTO order : orderSet) {
            if (!alreadyInResultList(resultList, order)) {
                resultList.add(refundSearchConverter.convertFromOrder(order));
            }
        }

        return resultList;
    }

    protected boolean alreadyInResultList(List<RefundSearchResultDTO> resultList, OrderDTO order) {
        for (RefundSearchResultDTO result : resultList) {
            if (order.getApprovalId().equals(new Long(result.getCaseNumber()))) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

}
