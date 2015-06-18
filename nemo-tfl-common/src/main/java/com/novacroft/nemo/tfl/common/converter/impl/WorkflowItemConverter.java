package com.novacroft.nemo.tfl.common.converter.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ADHOC_LOAD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.WEB_CREDIT;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.ObjectUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.utils.StringUtil;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.constant.CartType;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Component("workflowItemConverter")
public class WorkflowItemConverter {
    private final DateTimeFormatter formatter = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);

    @Autowired
    protected LocationDataService locationDataService;

    public WorkflowCmd convert(WorkflowItemDTO workflowItem) {

        RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
        CustomerDTO customer = refundDetails.getCustomer();
        CustomerDTO alternativePayee = refundDetails.getAlternativeRefundPayee();
        AddressDTO address = refundDetails.getAddress();

        AddressDTO refundAddress = (refundDetails.getAlternativeAddress() != null ? refundDetails.getAlternativeAddress() : address);

        WorkflowCmd workflowCmd = new WorkflowCmd();
        setRefundOverviewDetails(workflowItem, refundDetails, workflowCmd);
        if (customer != null) {
            setRefundCustomerDetails(refundDetails, customer, address, workflowCmd);
        }
        setRefundScenarioDetails(refundDetails, workflowCmd);
        setRefundItemDetails(refundDetails, workflowCmd);
        setRefundPaymentDetails(workflowItem, customer, alternativePayee, refundAddress, workflowCmd);
        setGoodWillPaymentDetails(refundDetails, workflowCmd);
        workflowCmd.setCaseNotes(refundDetails.getCaseNotes());
        workflowCmd.setWorkflowItem(workflowItem);

        return workflowCmd;
    }

    protected WorkflowCmd setGoodWillPaymentDetails(RefundDetailDTO refundDetails, WorkflowCmd workflowCmd) {
        if (refundDetails.getGoodwillItems() != null) {
            workflowCmd.setGoodwillPaymentItems(refundDetails.getGoodwillItems());
        }
        return workflowCmd;

    }

    protected void setRefundPaymentDetails(WorkflowItemDTO workflowItem, CustomerDTO customer, CustomerDTO alternativePayee,
                    AddressDTO refundAddress, WorkflowCmd workflowCommand) {
        List<String> alternativeRefundAddress = new ArrayList<String>();
        if (refundAddress != null) {
            alternativeRefundAddress = AddressFormatUtil.formatAddress(refundAddress.getHouseNameNumber(), refundAddress.getStreet(),
                            refundAddress.getTown(), refundAddress.getPostcode(), refundAddress.getCountry());
        }
        workflowCommand.setPaymentAddressDTO(refundAddress);
        workflowCommand.setPaymentMethod(convertToWorkflowCommandPaymentMethod(workflowItem.getPaymentMethod()));
        final CustomerDTO payeeAddress = ObjectUtils.firstNonNull(alternativePayee, customer); 
        workflowCommand.setPaymentName(payeeAddress != null ? formatName(payeeAddress) : null);
        if (PaymentType.BACS.code().equalsIgnoreCase(workflowItem.getPaymentMethod())
                        || PaymentType.CHEQUE.code().equalsIgnoreCase(workflowItem.getPaymentMethod())) {
            workflowCommand.setPaymentAddress(alternativeRefundAddress);
        }
        RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
        workflowCommand.setTargetCardNumber(refundDetails.getTargetCardNumber());
        workflowCommand.setStation((refundDetails.getStationId() != null) ? locationDataService.getActiveLocationById(
                        refundDetails.getStationId().intValue()).getName() : StringUtil.EMPTY_STRING);
        workflowCommand.setPayeeSortCode(refundDetails.getPayeeSortCode());
        workflowCommand.setPayeeAccountNumber(refundDetails.getPayeeAccountNumber());
    }

    protected void setRefundItemDetails(RefundDetailDTO refundDetails, WorkflowCmd workflowCommand) {
        workflowCommand.setTicketType(refundDetails.getTicketDescription());
        workflowCommand.setTicketItemName(refundDetails.getTicketDescription());
        workflowCommand.setCalculationBasis(refundDetails.getRefundBasis() != null ? refundDetails.getRefundBasis().code() : null);
        workflowCommand.setTicketAdminFee(refundDetails.getAdministrationFee());
        workflowCommand.setTotalTicketAmount(refundDetails.getTotalTicketPrice());
        workflowCommand.setTicketDeposit((refundDetails.getDeposit() != null ? refundDetails.getDeposit().longValue() : null));
        workflowCommand.setPayAsYouGoCredit(refundDetails.getPayAsYouGoCredit());

        workflowCommand.setRefundItems(refundDetails.getRefundItems());
    }

    protected void setRefundScenarioDetails(RefundDetailDTO refundDetails, WorkflowCmd workflowCommand) {
        workflowCommand.setRefundScenarioSubType(refundDetails.getRefundScenario().code());
        workflowCommand.setRefundScenarioType(refundDetails.getRefundDepartment().code());
        workflowCommand.setRefundablePeriodStartDate(refundDetails.getRefundDate().toString(formatter));
    }

    protected void setRefundCustomerDetails(RefundDetailDTO refundDetails, CustomerDTO customer, AddressDTO address, WorkflowCmd workflowCommand) {
        List<String> customerAddress = new ArrayList<String>();
        if (address != null) {
            customerAddress = AddressFormatUtil.formatAddress(address.getHouseNameNumber(), address.getStreet(), address.getTown(),
                            address.getPostcode(), address.getCountry());
        }
        workflowCommand.setRefundAddress(customerAddress);
        workflowCommand.setCustomerAddressDTO(address);
        workflowCommand.setRefundName(customer != null ? formatName(customer) : null);
        workflowCommand.setRefundUserName(refundDetails.getName() != null ? refundDetails.getName() : customer != null ? customer.getUsername()
                        : null);
    }

    protected void setRefundOverviewDetails(WorkflowItemDTO workflowItem, RefundDetailDTO refundDetails, WorkflowCmd workflowCommand) {
        workflowCommand.setRefundCaseNumber(workflowItem.getCaseNumber());
        workflowCommand.setCreatedDate(workflowItem.getCreatedTime());
        workflowCommand.setTotalRefund(refundDetails.getTotalRefundAmount());
        workflowCommand.setRefundStatus(workflowItem.getStatus());
        workflowCommand.setReasons(workflowItem.getApprovalReasons());
        workflowCommand.setAgentName(workflowItem.getAgent());
        workflowCommand.setTimeOnQueue(workflowItem.getTimeOnQueue());
    }

    protected String formatCustomerName(CustomerDTO customer) {
        assert (customer != null);
        return AddressFormatUtil.formatName(customer);
    }

    protected String getCartTypeCode(RefundScenarioEnum refundScenarioEnum) {
        switch (refundScenarioEnum) {

        case FAILEDCARD:
            return CartType.FAILED_CARD_REFUND.code();
        case LOST:
            return CartType.LOST_REFUND.code();
        case STOLEN:
            return CartType.STOLEN_REFUND.code();
        case CANCEL_AND_SURRENDER:
            return CartType.CANCEL_SURRENDER_REFUND.code();
        case DESTROYED:
            return CartType.DESTROYED_CARD_REFUND.code();
        default:
            return null;
        }
    }

    protected String convertToWorkflowCommandPaymentMethod(String paymentMethod) {
        PaymentType workflowItemPayment = PaymentType.lookUpPaymentType(paymentMethod);
        if (workflowItemPayment != null) {
            switch (workflowItemPayment) {
            case WEB_ACCOUNT_CREDIT:
                return WEB_CREDIT;
            case AD_HOC_LOAD:
                return ADHOC_LOAD;
            case PAYMENT_CARD:
                return PAYMENT_CARD;
            default:
                break;
            }
        }
        return paymentMethod;
    }

}
