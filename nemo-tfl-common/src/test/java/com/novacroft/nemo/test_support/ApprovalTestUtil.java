package com.novacroft.nemo.test_support;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.test_support.CountryTestUtil.getTestCountryDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;

import com.novacroft.nemo.common.transfer.CountryDTO;
import com.novacroft.nemo.tfl.common.command.impl.ApprovalListCmdImpl;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

public class ApprovalTestUtil {

    public static final String REFUND_IDENTIFIER = "0";
    public static final String TIME_ON_QUEUE = "1d 6h";
    public static final String AGENT = "John Smith";
    public static final String STATUS = "Pending";
    public static final List<String> REASONS = new ArrayList<String>();
    public static final String REASONS_AS_STRING = "Refund £1 above limit\\u003cbr /\\u003e";
    public static final String REASON = "Refund £1 above limit";
    public static final String AMOUNT = "5000";
    public static final Long AMOUNT_AS_LONG = 5000L;
    public static final String PAYMENT_METHOD = "Ad-Hoc Load";
    public static final String REASON_LONGER_THAN_CHARACTER_LIMIT = "Refund the customer with £120 due to unforseen circumstances. The customr is extremely unhappy and wishes that all monies on the oyster card to be refunded to bank accoount and to deactrivate the card as the customer will not be using the card again";

    public static final String INVALID_REFUND_IDENTIFIER = "a!!";
    public static final String INVALID_REFUND_IDENTIFIER_CHARACTERS = "a";
    public static final String INVALID_AMOUNT = "!!!";
    public static final String INVALID_AGENT = "!!!";
    public static final String INVALID_STATUS = "!!!";
    public static final String INVALID_REASON = "!!!";
    public static final String INVALID_PAYMENT_METHOD = "!!!";
    public static final String INVALID_TIME_ON_QUEUE = "!!!";

    public static final String INVALID_PAYMENT_METHOD_WHITE_SPACE = " ";
    public static final String INVALID_TIME_ON_QUEUE_WHITE_SPACE = " ";
    public static final String INVALID_AGENT_WHITE_SPACE = " ";
    public static final String INVALID_STATUS_WHITE_SPACE = " ";
    public static final String INVALID_REASON_WHITE_SPACE = " ";

    public static final String EXPECTED_RESULT = "[{\"caseNumber\":\"0\"";

    public static final String EXPECTED_JSON_RESULT = "{\"caseNumber\":\"0\",\"createdTime\":\""
                    + new DateTime().toString(SHORT_DATE_PATTERN)
                    + "\",\"approvalReasons\":\"Refund £1 above limit\\u003cbr /\\u003e\",\"amount\":5,\"timeOnQueue\":\"\", \"timeOnQueueAsLong\":,\"agent\":\"John Smith\",\"paymentMethod\":\"Cheque\",\"status\":\"Pending\"}";

    public static final String TITLE = "Mr";
    public static final String FIRSTNAME = "Person";
    public static final String INITIALS = "L";
    public static final String LASTNAME = "Smith";
    public static final String USERNAME = "Username";
    public static final String HOUSENAMENUMBER = "House 1";
    public static final String STREET = "New Street";
    public static final String TOWN = "Town";
    public static final String POSTCODE = "NN6 9UX";
    public static final String COUNTRY = "UK";
    public static final String REFUND_SCENARIO_TYPE = "OYSTER";
    public static final String REFUND_SCENARIO_SUB_TYPE = "FailedCardRefund";
    public static final String REFUNDABLE_PERIOD_START_DATE = "13/06/2014";
    public static final List<RefundItemCmd> REFUND_ITEMS = new ArrayList<RefundItemCmd>(Arrays.asList(new RefundItemCmd[] { new RefundItemCmd("Test",
                    "1", "2", "Pro Rata", "£15", 120L) }));
    public static final Integer TICKET_AMOUNT = 50000;
    public static final Integer TICKET_DEPOSIT = 0;
    public static final Integer TICKET_ADMIN_FEE = 0;

    public static final String INVALID_TITLE = "!!!";
    public static final String INVALID_FIRSTNAME = "!!!";
    public static final String INVALID_INITIALS = "!!!";
    public static final String INVALID_LASTNAME = "!!!";
    public static final String INVALID_USERNAME = "!!!";
    public static final String INVALID_HOUSENAMENUMBER = "!!!";
    public static final String INVALID_STREET = "!!!";
    public static final String INVALID_TOWN = "!!!";
    public static final String INVALID_COUNTRY = "";
    public static final String INVALID_REFUND_SCENARIO_TYPE = "!!!";
    public static final String INVALID_REFUND_SCENARIO_SUB_TYPE = "!!!";
    public static final String INVALID_REFUNDABLE_PERIOD_START_DATE = "!!!";
    public static final List<RefundItemCmd> INVALID_REFUND_ITEMS = null;
    public static final Integer INVALID_TICKET_AMOUNT = null;
    public static final Integer INVALID_TICKET_DEPOSIT = null;
    public static final Integer INVALID_TICKET_ADMIN_FEE = null;
    public static final String EMPTY_REASON = "";
    public static final String FIRSTNAME_WITH_SPACES = "Person Second Third";
    public static final String LASTNAME_WITH_SPACES = "Smith Second Third";
    public static final String TOWN_WITH_HYPHEN = "Town-Second-Third";
    public static final Long APPROVAL_ID = 1L;
    public static final String EXECUTION_ID = "1";

    public static ApprovalListCmdImpl createApprovalSearch(String caseNumber, String dateCreated, String amount, String paymentMethod,
                    String timeOnQueue, String agent, String status, String reason) {
        ApprovalListCmdImpl approval = new ApprovalListCmdImpl();

        approval.setCaseNumber(caseNumber);
        approval.setDateCreated(dateCreated);
        approval.setAmount(amount);
        approval.setPaymentMethod(paymentMethod);
        approval.setTimeOnQueue(timeOnQueue);
        approval.setAgent(agent);
        approval.setStatus(status);
        approval.setReason(reason);

        return approval;
    }

    public static ApprovalListCmdImpl getSearch() {
        return createApprovalSearch(REFUND_IDENTIFIER, DateTestUtil.REFUND_DATE_1, AMOUNT, PAYMENT_METHOD, TIME_ON_QUEUE, AGENT, STATUS, REASON);
    }

    public static ApprovalListCmdImpl getInvalidSearch() {
        return createApprovalSearch(INVALID_REFUND_IDENTIFIER, DateTestUtil.INVALID_DATE_1, INVALID_AMOUNT, INVALID_PAYMENT_METHOD,
                        INVALID_TIME_ON_QUEUE, INVALID_AGENT, INVALID_STATUS, INVALID_REASON);
    }

    public static ApprovalListCmdImpl getInvalidSearchCharacters() {
        return createApprovalSearch(INVALID_REFUND_IDENTIFIER_CHARACTERS, DateTestUtil.INVALID_DATE_1, INVALID_AMOUNT, INVALID_PAYMENT_METHOD,
                        INVALID_TIME_ON_QUEUE, INVALID_AGENT, INVALID_STATUS, INVALID_REASON);
    }

    public static ApprovalListCmdImpl getInvalidSearchWhiteSpace() {
        return createApprovalSearch(INVALID_REFUND_IDENTIFIER, DateTestUtil.INVALID_DATE_1, INVALID_AMOUNT, INVALID_PAYMENT_METHOD_WHITE_SPACE,
                        INVALID_TIME_ON_QUEUE_WHITE_SPACE, INVALID_AGENT_WHITE_SPACE, INVALID_STATUS_WHITE_SPACE, INVALID_REASON_WHITE_SPACE);
    }

    public static WorkflowItemDTO getResult() {
        REASONS.add(REASON);
        return createApprovalSearchResult(REFUND_IDENTIFIER, PAYMENT_METHOD, AGENT, STATUS, REASONS);
    }

    private static WorkflowItemDTO createApprovalSearchResult(String caseNumber, String paymentMethod, String agent, String status,
                    List<String> reasons) {
        WorkflowItemDTO approval = new WorkflowItemDTO();

        approval.setCaseNumber(caseNumber);
        approval.setCreatedTime(new DateTime());
        approval.setPaymentMethod(paymentMethod);
        approval.setAgent(agent);
        approval.setStatus(status);
        approval.setApprovalReasons(reasons);

        return approval;
    }

    public static WorkflowItemDTO getJSONInput() {
        WorkflowItemDTO workflowItem = getResult();

        workflowItem.setTaskCreatedTime(new DateTime());

        Refund refund = new Refund();
        refund.setRefundAmount(AMOUNT_AS_LONG);

        RefundDetailDTO refundDetail = new RefundDetailDTO();
        refundDetail.setRefundEntity(refund);

        workflowItem.setRefundDetails(refundDetail);

        return workflowItem;
    }

    public static String getCaseNoteMessage(String oldValue, String newValue, String reason) {
        return "Changed  from " + oldValue + " to " + newValue + reason;
    }

    public static String getCaseNoteMessageNull(String oldValue, String newValue, String reason) {
        return "Changed null from " + oldValue + " to " + newValue + reason;
    }

    public static WorkflowEditCmd getValidWorkflowEditCmd() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }

    public static WorkflowEditCmd getInvalidWorkflowEditCmd() {
        return createTestWorkflowEditCmd(INVALID_TITLE, INVALID_FIRSTNAME, INVALID_INITIALS, INVALID_LASTNAME, INVALID_USERNAME, INVALID_HOUSENAMENUMBER,
                        INVALID_STREET, INVALID_TOWN, INVALID_COUNTRY, INVALID_REFUND_SCENARIO_TYPE, INVALID_REFUND_SCENARIO_SUB_TYPE,
                        INVALID_REFUNDABLE_PERIOD_START_DATE, INVALID_REFUND_ITEMS, INVALID_TICKET_AMOUNT, INVALID_TICKET_DEPOSIT,
                        INVALID_TICKET_ADMIN_FEE, INVALID_PAYMENT_METHOD, INVALID_TITLE, INVALID_FIRSTNAME, INVALID_INITIALS, INVALID_LASTNAME,
                        INVALID_HOUSENAMENUMBER, INVALID_STREET, INVALID_TOWN, INVALID_COUNTRY, INVALID_TICKET_AMOUNT, EMPTY_REASON);
    }

    public static WorkflowEditCmd getValidFirstLastNamesAndTownWorkflowEditCmd() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME_WITH_SPACES, INITIALS, LASTNAME_WITH_SPACES, USERNAME, HOUSENAMENUMBER, STREET, TOWN_WITH_HYPHEN,
                        COUNTRY, REFUND_SCENARIO_TYPE, REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT,
                        TICKET_DEPOSIT, TICKET_ADMIN_FEE, PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN,
                        COUNTRY, TICKET_AMOUNT, REASON);
    }

    public static WorkflowEditCmd getWorkflowEditCmdEmptyUsername() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, "", HOUSENAMENUMBER, STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }

    public static WorkflowEditCmd getWorkflowEditCmdEmptyHouseNAmeNumber() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, USERNAME, "", STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }

    public static WorkflowEditCmd getWorkflowEditCmdEmptyStreet() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, "", TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }

    public static WorkflowEditCmd getWorkflowEditCmdemptyTown() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, "", COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }

    public static WorkflowEditCmd getWorkflowEditCmdEmptyCountry() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, TOWN, "", REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }
    
    public static WorkflowEditCmd getWorkflowEditCmdReasonExceedingLimit() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, TOWN, "", REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON_LONGER_THAN_CHARACTER_LIMIT);
    }
    
    public static WorkflowEditCmd getWorkflowEditCmdEmptyRefundPeriodStartDate() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, "", REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }
    
    public static WorkflowEditCmd getWorkflowEditCmdEmptyTitle() {
        return createTestWorkflowEditCmd("", FIRSTNAME, INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }
    
    public static WorkflowEditCmd getWorkflowEditCmdEmptyFirstName() {
        return createTestWorkflowEditCmd(TITLE, "", INITIALS, LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }
    
    public static WorkflowEditCmd getWorkflowEditCmdEmptyInitials() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, "", LASTNAME, USERNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }
    
    public static WorkflowEditCmd getWorkflowEditCmdEmptyLastName() {
        return createTestWorkflowEditCmd(TITLE, FIRSTNAME, INITIALS, "", USERNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, REFUND_SCENARIO_TYPE,
                        REFUND_SCENARIO_SUB_TYPE, REFUNDABLE_PERIOD_START_DATE, REFUND_ITEMS, TICKET_AMOUNT, TICKET_DEPOSIT, TICKET_ADMIN_FEE,
                        PAYMENT_METHOD, TITLE, FIRSTNAME, INITIALS, LASTNAME, HOUSENAMENUMBER, STREET, TOWN, COUNTRY, TICKET_AMOUNT, REASON);
    }
    
    private static WorkflowEditCmd createTestWorkflowEditCmd(String title, String firstName, String initials, String lastName, String username, String houseNameNumber, String street,
                    String town, String countryCode, String refundScenarioType, String refundScenarioSubType, String refundablePeriodStartDate,
                    List<RefundItemCmd> refundItems, Integer ticketAmount, Integer ticketDeposit, Integer ticketAdminFee, String paymentMethod,
                    String paymentTitle, String paymentFirstName, String paymentInitials, String paymentLastName, String paymentHouseNameNumber,
                    String paymentStreet, String paymentTown, String paymentCountryCode, Integer amount, String reason) {
        CountryDTO countryDTO = getTestCountryDTO(countryCode);
        CountryDTO paymentCountryDTO = getTestCountryDTO(paymentCountryCode);
        return new WorkflowEditCmd(title, firstName, initials, lastName, username, 
                        houseNameNumber, street, town, countryDTO, 
                        refundScenarioType, refundScenarioSubType, refundablePeriodStartDate, refundItems, 
                        ticketAmount, ticketDeposit, ticketAdminFee, 
                        paymentMethod, paymentTitle, paymentFirstName, paymentInitials, paymentLastName, 
                        paymentHouseNameNumber, paymentStreet, paymentTown, paymentCountryDTO, amount, reason);
        
    }

    public static WorkflowItemDTO getWorkflowItem() {
        WorkflowItemDTO workflowItem = getResult();
        workflowItem.setRefundDetails(getRefundDetails());
        return workflowItem;
    }

    public static WorkflowItemDTO getWorkflowItemBACS() {
        WorkflowItemDTO workflowItem = getResult();
        workflowItem.setPaymentMethod(PaymentType.BACS.code());
        RefundDetailDTO refundDetails = getRefundDetails();
        refundDetails.setPaymentType(PaymentType.BACS);
        workflowItem.setRefundDetails(refundDetails);
        return workflowItem;
    }

    public static WorkflowItemDTO getWorkflowItemCheque() {
        WorkflowItemDTO workflowItem = getResult();
        workflowItem.setPaymentMethod(PaymentType.CHEQUE.code());
        RefundDetailDTO refundDetails = getRefundDetails();
        refundDetails.setPaymentType(PaymentType.CHEQUE);
        workflowItem.setRefundDetails(refundDetails);
        return workflowItem;
    }

    public static RefundDetailDTO getRefundDetails() {
        RefundDetailDTO refundDetails = new RefundDetailDTO();
        refundDetails.setCustomer(CustomerTestUtil.getTestCustomerDTO1());
        refundDetails.setAddress(AddressTestUtil.getTestAddressDTO1());
        refundDetails.setTotalRefundAmount(1L);
        refundDetails.setRefundDate(new DateTime());
        refundDetails.setCardId(CardTestUtil.CARD_ID);
        refundDetails.setPaymentType(PaymentType.AD_HOC_LOAD);
        return refundDetails;
    }
}
