package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.application_service.WorkFlowService;
import com.novacroft.nemo.tfl.common.command.impl.RefundCaseCmd;
import com.novacroft.nemo.tfl.common.data_service.BACSSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.ChequeSettlementDataService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.BACSSettlementDTO;
import com.novacroft.nemo.tfl.common.transfer.financial_services_centre.ChequeSettlementDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Component(value = "refundCaseConverter")
public class RefundCaseConverter {
    private final DateTimeFormatter formatter = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
    @Autowired
    protected WorkFlowService workflowService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected BACSSettlementDataService bacsSettlementDataService;
    @Autowired
    protected ChequeSettlementDataService chequeSettlementDataService;

    public RefundCaseCmd convertFromWorkflowItem(WorkflowItemDTO workflowItem) {
        RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
        CustomerDTO customer = refundDetails.getCustomer();
        AddressDTO address = refundDetails.getAddress();
        List<String> customerAddress = (address != null) ? AddressFormatUtil.formatAddress(address.getHouseNameNumber(), address.getStreet(),
                        address.getTown(), address.getPostcode(), address.getCountry()) : new ArrayList<String>();
        CardDTO card = (refundDetails.getCardId() != null) ? cardDataService.findById(refundDetails.getCardId()) : null;
        return new RefundCaseCmd(workflowItem.getCaseNumber(), refundDetails.getTotalRefundAmount(), workflowItem.getCreatedTime(), refundDetails
                        .getRefundDate().toString(formatter), null, workflowItem.getStatus(), Boolean.FALSE, null, workflowItem.getAgent(), null,
                        (customer != null) ? formatName(customer) : StringUtil.EMPTY_STRING, (customer != null) ? customer.getUsername()
                                        : StringUtil.EMPTY_STRING, customerAddress, (card != null) ? card.getCardNumber() : null,
                        workflowItem.getPaymentMethod(), null, null, workflowItem.getCaseNotes());
    }

    public RefundCaseCmd convertFromOrder(OrderDTO order) {
        WorkflowItemDTO workflowItem = workflowService.getHistoricWorkflowItem(order.getApprovalId().toString());
        RefundCaseCmd cmd = convertFromWorkflowItem(workflowItem);
        cmd.setStatus(order.getStatus());

        switch (workflowItem.getRefundDetails().getPaymentType()) {
            case BACS:
                BACSSettlementDTO bacsSettlement = bacsSettlementDataService.findByOrderNumber(order.getOrderNumber()).get(0);
                cmd.setBacsNumber((bacsSettlement.getPaymentReference() != null) ? bacsSettlement.getPaymentReference().toString() : null);
                cmd.setPaidDate(bacsSettlement.getSettlementDate().toString());
                break;
            case CHEQUE:
                ChequeSettlementDTO chequeSettlement = chequeSettlementDataService.findByOrderNumber(order.getOrderNumber());
                cmd.setChequeNumber((chequeSettlement.getChequeSerialNumber() != null) ? chequeSettlement.getChequeSerialNumber().toString() : null);
                cmd.setPaidDate(chequeSettlement.getSettlementDate().toString());
                break;
            default:
                break;
        }

        return cmd;
    }
}
