package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.utils.StringUtil.isNotEmpty;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.RefundSearchCmd;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.RefundSearchDataService;
import com.novacroft.nemo.tfl.common.domain.Order;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

@Service(value = "refundSearchDataService")
public class RefundSearchDataServiceImpl implements RefundSearchDataService {
    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected CustomerDataService customerDataService;

    @Override
    public Set<OrderDTO> findBySearchCriteria(RefundSearchCmd search) {
        Order exampleOrder = new Order();
        Set<OrderDTO> potentialOrderSet = new HashSet<OrderDTO>();

        potentialOrderSet = processCaseNumber(search.getCaseNumber(), potentialOrderSet);

        potentialOrderSet = processCardNumber(search, potentialOrderSet);

        potentialOrderSet = processCustomerName(search, exampleOrder, potentialOrderSet);

        potentialOrderSet = processAgentName(search, potentialOrderSet);

        potentialOrderSet = processChequeNumber(search.getChequeNumber(), potentialOrderSet);

        potentialOrderSet = processBacsNumber(search.getBacsNumber(), potentialOrderSet);

        Set<OrderDTO> orderSet = findOrderListByCriteria(potentialOrderSet, exampleOrder);

        return orderSet;
    }

    protected Set<OrderDTO> processAgentName(RefundSearchCmd search, Set<OrderDTO> potentialOrderSet) {
        if (isNotEmpty(search.getAgentFirstName()) || isNotEmpty(search.getAgentLastName())) {
            List<WorkflowItemDTO> resultList = workflowService.findHistoricRefundsByAgent(search.getAgentFirstName(), search.getAgentLastName());
            for (WorkflowItemDTO result : resultList) {
                Order exampleEntity = new Order();
                exampleEntity.setApprovalId(Long.valueOf(result.getCaseNumber()));
                potentialOrderSet.addAll(orderDataService.findByExample(exampleEntity));
            }
        }
        return potentialOrderSet;
    }

    protected Set<OrderDTO> processCaseNumber(String caseNumber, Set<OrderDTO> potentialOrderSet) {
        if (isNotEmpty(caseNumber)) {
            Order exampleEntity = new Order();
            exampleEntity.setApprovalId(Long.valueOf(caseNumber));
            potentialOrderSet.addAll(orderDataService.findByExample(exampleEntity));
        }
        return potentialOrderSet;
    }

    protected Set<OrderDTO> processCardNumber(RefundSearchCmd search, Set<OrderDTO> potentialOrderSet) {
        if (isNotEmpty(search.getCardNumber())) {
            List<WorkflowItemDTO> resultList = workflowService.findHistoricRefundsByCardNumber(search.getCardNumber(), search.getExact());
            for (WorkflowItemDTO result : resultList) {
                Order exampleEntity = new Order();
                exampleEntity.setApprovalId(Long.valueOf(result.getCaseNumber()));
                potentialOrderSet.addAll(orderDataService.findByExample(exampleEntity));
            }
        }
        return potentialOrderSet;
    }

    protected Set<OrderDTO> processCustomerName(RefundSearchCmd search, Order exampleOrder, Set<OrderDTO> potentialOrderSet) {
        if (isNotEmpty(search.getCustomerFirstName()) || isNotEmpty(search.getCustomerLastName())) {
            String firstName = search.getCustomerFirstName();
            String lastName = search.getCustomerLastName();
            Boolean exact = isNotEmpty(search.getExact());

            List<CustomerDTO> customerResults;
            if (isNotEmpty(firstName) && isNotEmpty(lastName)) {
                customerResults = customerDataService.findByFirstNameAndLastName(firstName, lastName, exact);
            } else if (isNotEmpty(firstName)) {
                customerResults = customerDataService.findByFirstName(StringUtils.capitalize(firstName.toLowerCase()), exact);
            } else {
                customerResults = customerDataService.findByLastName(StringUtils.capitalize(lastName.toLowerCase()), exact);
            }

            if (!customerResults.isEmpty()) {
                for (CustomerDTO customer : customerResults) {
                    exampleOrder.setCustomerId(customer.getId());
                    potentialOrderSet.addAll(orderDataService.findRefundsForCustomer(customer.getId()));
                }
                exampleOrder.setCustomerId(null);
            }
        }
        return potentialOrderSet;
    }

    protected Set<OrderDTO> processChequeNumber(String chequeNumber, Set<OrderDTO> potentialOrderSet) {
        if (isNotEmpty(chequeNumber)) {
            potentialOrderSet.addAll(orderDataService.findRefundByChequeSerialNumber(Long.valueOf(chequeNumber)));
        }
        return potentialOrderSet;
    }

    protected Set<OrderDTO> processBacsNumber(String bacsNumber, Set<OrderDTO> potentialOrderSet) {
        if (isNotEmpty(bacsNumber)) {
            potentialOrderSet.addAll(orderDataService.findRefundByBACSReferenceNumber(Long.valueOf(bacsNumber)));
        }
        return potentialOrderSet;
    }

    protected Set<OrderDTO> findOrderListByCriteria(Set<OrderDTO> potentialOrderSet, Order exampleOrder) {
        Set<OrderDTO> orderSet = new HashSet<OrderDTO>();
        if (!potentialOrderSet.isEmpty()) {
            for (OrderDTO potentialOrder : potentialOrderSet) {
                exampleOrder.setApprovalId(potentialOrder.getApprovalId());
                orderSet.addAll(orderDataService.findByExample(exampleOrder));
            }
        }
        return orderSet;
    }
}
