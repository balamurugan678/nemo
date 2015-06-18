package com.novacroft.nemo.tfl.common.application_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.SHORT_DATE_PATTERN;
import static com.novacroft.nemo.common.utils.CurrencyUtil.formatPenceWithHtmlCurrencySymbol;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.ADHOC_LOAD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.PAYMENT_CARD;
import static com.novacroft.nemo.tfl.common.constant.RefundConstants.WEB_CREDIT;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ALTERNATIVE_ADDRESS;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.PAYMENT_METHOD;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.WORKFLOW_ITEM;
import static com.novacroft.nemo.tfl.common.util.AddressFormatUtil.formatName;

import java.util.ArrayList;
import java.util.List;

import org.activiti.engine.RuntimeService;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.data_service.CountryDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.application_service.CaseHistoryNoteService;
import com.novacroft.nemo.tfl.common.application_service.WorkflowEditService;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowCmd;
import com.novacroft.nemo.tfl.common.command.impl.WorkflowEditCmd;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.converter.impl.WorkflowItemConverter;
import com.novacroft.nemo.tfl.common.data_service.LocationDataService;
import com.novacroft.nemo.tfl.common.domain.CaseHistoryNote;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.common.util.AddressFormatUtil;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Service("workflowEditService")
public class WorkflowEditServiceImpl implements WorkflowEditService {
    protected final DateTimeFormatter formatter = DateTimeFormat.forPattern(SHORT_DATE_PATTERN);
    protected static final String BLANK = "BLANK";

    @Autowired
    protected CaseHistoryNoteService caseHistoryNoteService;
    @Autowired(required = false)
    protected RuntimeService runtimeService;
    @Autowired
    protected ContentDataService content;
    @Autowired
    protected WorkflowItemConverter workflowItemConverter;
    @Autowired
    protected LocationDataService locationDataService;
    @Autowired
    protected CountryDataService countryDataService;

    private static final String CUSTOMER_NAME_KEY = "customerNameField";
    private static final String CUSTOMER_USERNAME_KEY = "customerUsernameField";
    private static final String CUSTOMER_ADDRESS_KEY = "customerAddressField";
    private static final String REFUND_SCENARIO_TYPE_KEY = "refundScenarioTypeField";
    private static final String REFUND_SCENARIO_SUB_TYPE_KEY = "refundScenarioSubTypeField";
    private static final String START_OF_REFUNDABLE_PERIOD_KEY = "startOfRefundablePeriodField";
    private static final String REFUND_ITEM_AMOUNT_KEY = "ViewWorkflowItem.totalTicketPrice.label";
    private static final String REFUND_ITEM_DEPOSIT_KEY = "refundItemDepositField";
    private static final String REFUND_ITEM_ADMIN_FEE_KEY = "refundItemAdminFeeField";
    private static final String PAYMENT_METHOD_KEY = "paymentMethodField";
    private static final String PAYMENT_NAME_KEY = "paymentNameField";
    private static final String PAYMENT_ADDRESS_KEY = "paymentAddressField";
    private static final String PAYMENT_AMOUNT_KEY = "paymentAmountField";
    private static final String PAYASYOUGOCREDIT_KEY = "payAsYouGoCredit.text";
    private static final String PAYMENT_SORTCODE_KEY = "payeeSortCodeField";
    private static final String PAYMENT_ACCOUNT_NUMBER_KEY = "payeeAccountNumberField";
    private static final String PAYMENT_STATION_KEY = "ViewWorkflowItem.station.label";
    private static final String PAYMENT_TARGET_CARD_NUMBER_KEY = "targetCardNumberField";

    private String refundItemPayAsYouGoText = "";
    private String customerNameText = "";
    private String customerUsernameText = "";
    private String customerAddressText = "";
    private String refundScenarioTypeText = "";
    private String refundScenarioSubTypeText = "";
    private String startOfRefundablePeriodText = "";
    private String refundItemAmountText = "";
    private String refundItemDepositText = "";
    private String refundItemAdminFeeText = "";
    private String paymentMethodFieldText = "";
    private String paymentNameText = "";
    private String paymentAddressText = "";
    private String paymentAmountText = "";
    private String paymentSortCodeText = "";
    private String paymentAccountNumberText = "";
    private String paymentStationText = "";
    private String paymentTargetCardNumberText = "";


    @Override
    public WorkflowCmd saveChanges(WorkflowCmd workflowCmd, WorkflowEditCmd workflowEditCmd) {

        assert (workflowEditCmd.getReason() != null && StringUtils.isNotEmpty(workflowEditCmd.getReason()));
        initialize();

         WorkflowItemDTO workflowItem = workflowCmd.getWorkflowItem();
        List<CaseHistoryNote> caseNotes = caseHistoryNoteService.getCaseNotesExcludingHistoric(workflowItem);

        caseNotes = processCustomerDetails(caseNotes, workflowCmd, workflowItem, workflowEditCmd);

        caseNotes = processRefundScenario(caseNotes, workflowCmd, workflowItem, workflowEditCmd);

        caseNotes = processRefundItemDetails(caseNotes, workflowCmd, workflowItem, workflowEditCmd);

        caseNotes = processPaymentDetails(caseNotes, workflowCmd, workflowItem, workflowEditCmd);


        workflowItem.setCaseNotes(caseNotes);
        runtimeService.setVariable(workflowItem.getExecutionId(), WORKFLOW_ITEM, workflowItem);
        return workflowItemConverter.convert(workflowItem);

    }

    protected void initialize() {
        customerNameText = content.findByLocaleAndCode(CUSTOMER_NAME_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        customerUsernameText = content.findByLocaleAndCode(CUSTOMER_USERNAME_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        customerAddressText = content.findByLocaleAndCode(CUSTOMER_ADDRESS_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        refundScenarioTypeText = content.findByLocaleAndCode(REFUND_SCENARIO_TYPE_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        refundScenarioSubTypeText = content.findByLocaleAndCode(REFUND_SCENARIO_SUB_TYPE_KEY,
                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        startOfRefundablePeriodText = content.findByLocaleAndCode(START_OF_REFUNDABLE_PERIOD_KEY,
                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        refundItemAmountText = content.findByLocaleAndCode(REFUND_ITEM_AMOUNT_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        refundItemDepositText = content.findByLocaleAndCode(REFUND_ITEM_DEPOSIT_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        refundItemAdminFeeText = content.findByLocaleAndCode(REFUND_ITEM_ADMIN_FEE_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        paymentMethodFieldText = content.findByLocaleAndCode(PAYMENT_METHOD_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        paymentNameText = content.findByLocaleAndCode(PAYMENT_NAME_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
        				.getContent();
        paymentAddressText = content.findByLocaleAndCode(PAYMENT_ADDRESS_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
        				.getContent();
        paymentAmountText = content.findByLocaleAndCode(PAYMENT_AMOUNT_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
        				.getContent();
        paymentSortCodeText = content.findByLocaleAndCode(PAYMENT_SORTCODE_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
        				.getContent();
        paymentAccountNumberText = content.findByLocaleAndCode(PAYMENT_ACCOUNT_NUMBER_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
        				.getContent();
        paymentStationText = content.findByLocaleAndCode(PAYMENT_STATION_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
        				.getContent();
        paymentTargetCardNumberText = content.findByLocaleAndCode(PAYMENT_TARGET_CARD_NUMBER_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
        				.getContent();
        refundItemPayAsYouGoText = content
                        .findByLocaleAndCode(PAYASYOUGOCREDIT_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
    }

    protected List<CaseHistoryNote> processCustomerDetails(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, WorkflowEditCmd cmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        caseNotes = processCustomerName(caseNotes, workflowCmd, workflowItem, cmd);

        caseNotes = processCustomerUsername(caseNotes, workflowCmd, workflowItem, workflowCmd.getRefundUserName(), cmd.getUsername(), cmd.getReason());

        caseNotes = processCustomerAddress(caseNotes, workflowCmd, workflowItem, workflowCmd.getRefundAddress(), cmd);

        return caseNotes;
    }

    protected List<CaseHistoryNote> processCustomerName(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, WorkflowEditCmd cmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        String currentCustomerName = workflowCmd.getRefundName();
        CustomerDTO inputCustomer = new CustomerDTO(cmd.getTitle(), cmd.getFirstName(), cmd.getInitials(), cmd.getLastName());
        String inputCustomerName = formatName(inputCustomer);

        if (!inputCustomerName.equalsIgnoreCase(currentCustomerName)) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, customerNameText, currentCustomerName, inputCustomerName,
                            cmd.getReason());
            workflowCmd.setRefundName(inputCustomerName);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setCustomer(inputCustomer);
            runtimeService.setVariable(workflowItem.getExecutionId(), CUSTOMER, inputCustomerName.toUpperCase());
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processCustomerUsername(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, String currentUsername, String inputUsername, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if (!inputUsername.equalsIgnoreCase(currentUsername)) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, customerUsernameText, currentUsername, inputUsername,
                            reason);
            workflowCmd.setRefundUserName(inputUsername);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setName(inputUsername);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processCustomerAddress(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, List<String> refundAddress, WorkflowEditCmd workflowEditCmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        List<String> current = refundAddress;
        List<String> input = AddressFormatUtil.formatAddress(workflowEditCmd.getHouseNameNumber(), workflowEditCmd.getStreet(),
                        workflowEditCmd.getTown(),
                        workflowEditCmd.getPostcode(), workflowEditCmd.getCountry());

        if (isAddressUpdated(current, input)) {
            workflowCmd.setRefundAddress(input);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();

            AddressDTO inputAddress = new AddressDTO(workflowEditCmd.getHouseNameNumber(), workflowEditCmd.getStreet(), workflowEditCmd.getTown(), 
                            countryDataService.findCountryByCode(workflowEditCmd.getCountry().getCode()));

            refundDetails.setAddress(inputAddress);
            runtimeService.setVariable(workflowItem.getExecutionId(), CUSTOMER_ADDRESS, inputAddress);

            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, customerAddressText, current.toString(),
                            input.toString(),
                            workflowEditCmd.getReason());
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundScenario(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, WorkflowEditCmd cmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        caseNotes = processRefundScenarioType(caseNotes, workflowCmd, workflowItem, workflowCmd.getRefundScenarioType(), cmd.getRefundScenarioType(),
                        cmd.getReason());

        caseNotes = processRefundScenarioSubType(caseNotes, workflowCmd, workflowItem, workflowCmd.getRefundScenarioSubType(),
                        cmd.getRefundScenarioSubType(), cmd.getReason());

        caseNotes = processRefundablePeriodStartDate(caseNotes, workflowCmd, workflowItem, workflowCmd.getRefundablePeriodStartDate(),
                        cmd.getRefundablePeriodStartDate(), cmd.getReason());

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundScenarioType(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, String currentRefundScenarioType, String inputRefundScenarioType, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if (!inputRefundScenarioType.equalsIgnoreCase(currentRefundScenarioType)) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, refundScenarioTypeText, currentRefundScenarioType,
                            inputRefundScenarioType, reason);
            workflowCmd.setRefundScenarioType(inputRefundScenarioType);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setRefundDepartment(RefundDepartmentEnum.find(inputRefundScenarioType));
            workflowItem.setRefundDetails(refundDetails);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundScenarioSubType(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, String currentRefundScenarioSubType, String inputRefundScenarioSubType, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if (!inputRefundScenarioSubType.equalsIgnoreCase(currentRefundScenarioSubType)) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, refundScenarioSubTypeText,
                            currentRefundScenarioSubType,
                            inputRefundScenarioSubType, reason);
            workflowCmd.setRefundScenarioSubType(inputRefundScenarioSubType);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setRefundScenario(RefundScenarioEnum.find(inputRefundScenarioSubType));
            workflowItem.setRefundDetails(refundDetails);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundablePeriodStartDate(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, String currentRefundablePeriodStartDate, String inputRefundablePeriodStartDate, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if (!inputRefundablePeriodStartDate.equalsIgnoreCase(currentRefundablePeriodStartDate)) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, startOfRefundablePeriodText,
                            currentRefundablePeriodStartDate, inputRefundablePeriodStartDate, reason);
            workflowCmd.setRefundablePeriodStartDate(inputRefundablePeriodStartDate);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setRefundDate(formatter.parseDateTime(inputRefundablePeriodStartDate));
            workflowItem.setRefundDetails(refundDetails);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundItemDetails(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, WorkflowEditCmd workflowEditCmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        caseNotes = processRefundItems(caseNotes, workflowCmd, workflowItem, (workflowCmd.getRefundItems() != null) ? workflowCmd.getRefundItems()
                        : new ArrayList<RefundItemCmd>(), workflowEditCmd.getRefundItems(), workflowEditCmd.getReason());

        caseNotes = processTotalTicketAmount(caseNotes, workflowCmd, workflowItem, workflowCmd.getTotalTicketAmount(), workflowEditCmd.getTotalTicketAmount(),
                        workflowEditCmd.getReason());

        caseNotes = processRefundItemDeposit(caseNotes, workflowCmd, workflowItem, workflowCmd.getTicketDeposit(),
                        workflowEditCmd.getTicketDeposit(), workflowEditCmd.getReason());

        caseNotes = processRefundItemAdminFee(caseNotes, workflowCmd, workflowItem, workflowCmd.getTicketAdminFee(), workflowEditCmd.getTicketAdminFee(),
                        workflowEditCmd.getReason());

        caseNotes = processRefundItemPayAsyougoCredit(caseNotes, workflowCmd, workflowItem, workflowCmd.getPayAsYouGoCredit(),
                        workflowEditCmd.getPayAsYouGoCredit(), workflowEditCmd.getReason());

        caseNotes = processGoodwillPaymentItems(caseNotes, workflowCmd, workflowItem,
                        (workflowCmd.getGoodwillPaymentItems() != null) ? workflowCmd.getGoodwillPaymentItems()
                                        : new ArrayList<GoodwillPaymentItemCmd>(), workflowEditCmd.getGoodwillPaymentItems(),
                        workflowEditCmd.getReason());

        return caseNotes;
    }


    protected List<CaseHistoryNote> processRefundItems(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, List<RefundItemCmd> currentItems, List<RefundItemCmd> inputItems, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        Boolean changed = Boolean.FALSE;
        for (RefundItemCmd inputRefundItemCmd : inputItems) {
            if (currentItems == null || !currentItems.contains(inputRefundItemCmd)) {
                caseNotes = caseHistoryNoteService.addRefundItemAddedCaseNote(caseNotes, workflowCmd, inputRefundItemCmd.toString(), reason);
                changed = Boolean.TRUE;
            }
        }

        for (RefundItemCmd currentRefundItemCmd : currentItems) {
            if (!inputItems.contains(currentRefundItemCmd)) {
                caseNotes = caseHistoryNoteService.addRefundItemRemovedCaseNote(caseNotes, workflowCmd, currentRefundItemCmd.toString(), reason);
                changed = Boolean.TRUE;
            }
        }

        if (changed) {
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setRefundItems(inputItems);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processGoodwillPaymentItems(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, List<GoodwillPaymentItemCmd> currentItems, List<GoodwillPaymentItemCmd> inputItems, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        Boolean changed = Boolean.FALSE;
        for (GoodwillPaymentItemCmd goodwillPaymentItemCmd : inputItems) {
            if (currentItems == null || !currentItems.contains(goodwillPaymentItemCmd)) {
                caseNotes = caseHistoryNoteService.addRefundItemAddedCaseNote(caseNotes, workflowCmd, goodwillPaymentItemCmd.toString(), reason);
                changed = Boolean.TRUE;
            }
        }

        for (GoodwillPaymentItemCmd currentGoodwillPaymentItemCmd : currentItems) {
            if (!inputItems.contains(currentGoodwillPaymentItemCmd)) {
                caseNotes = caseHistoryNoteService.addRefundItemRemovedCaseNote(caseNotes, workflowCmd, currentGoodwillPaymentItemCmd.toString(),
                                reason);
                changed = Boolean.TRUE;
            }
        }

        if (changed) {
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setGoodwillItems(inputItems);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundItemPayAsyougoCredit(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, Integer currentTicketAmount, Integer inputTicketAmount, String reason) {

        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if (!inputTicketAmount.equals(currentTicketAmount.intValue())) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, refundItemPayAsYouGoText,
                            formatPenceWithHtmlCurrencySymbol(currentTicketAmount), formatPenceWithHtmlCurrencySymbol(inputTicketAmount),
                            reason);
            workflowCmd.setPayAsYouGoCredit(inputTicketAmount);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setPayAsYouGoCredit(inputTicketAmount);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processTotalTicketAmount(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, Long currentTicketAmount, Integer inputTicketAmount, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if (!inputTicketAmount.equals(currentTicketAmount.intValue())) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, refundItemAmountText,
                            formatPenceWithHtmlCurrencySymbol(currentTicketAmount.intValue()), formatPenceWithHtmlCurrencySymbol(inputTicketAmount),
                            reason);
            workflowCmd.setTotalTicketAmount(inputTicketAmount.longValue());
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setTotalTicketPrice(inputTicketAmount.longValue());

        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundItemDeposit(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, Long currentTicketDeposit, Integer inputTicketDeposit, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if ((currentTicketDeposit == null || !inputTicketDeposit.equals(currentTicketDeposit.intValue())) && inputTicketDeposit != null) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, refundItemDepositText,
                            (currentTicketDeposit == null) ? "0"
                            : formatPenceWithHtmlCurrencySymbol(currentTicketDeposit.intValue()),
                            formatPenceWithHtmlCurrencySymbol(inputTicketDeposit), reason);
            workflowCmd.setTicketDeposit(inputTicketDeposit.longValue());
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setDeposit(inputTicketDeposit);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processRefundItemAdminFee(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, Long currentTicketAdminFee, Integer inputTicketAdminFee, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        if (inputTicketAdminFee != null && (currentTicketAdminFee == null || !inputTicketAdminFee.equals(currentTicketAdminFee.intValue()))) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, refundItemAdminFeeText,
                            (currentTicketAdminFee == null) ? "0"
                            : formatPenceWithHtmlCurrencySymbol(currentTicketAdminFee.intValue()),
                            formatPenceWithHtmlCurrencySymbol(inputTicketAdminFee), reason);
            workflowCmd.setTicketAdminFee(inputTicketAdminFee.longValue());
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setAdministrationFee(inputTicketAdminFee.longValue());
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processPaymentDetails(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, WorkflowEditCmd cmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        caseNotes = processPaymentMethod(caseNotes, workflowCmd, workflowItem, workflowCmd.getPaymentMethod(), cmd.getPaymentMethod(),
                        cmd.getReason());

        caseNotes = processPaymentName(caseNotes, workflowCmd, workflowItem, cmd);

        if (PaymentType.BACS.code().equalsIgnoreCase(workflowItem.getPaymentMethod()) 
        		|| PaymentType.CHEQUE.code().equalsIgnoreCase(workflowItem.getPaymentMethod())){
        	caseNotes = processPaymentAddress(caseNotes, workflowCmd, workflowItem, workflowCmd.getPaymentAddress(), cmd);
        }
        
        caseNotes = processPaymentAmount(caseNotes, workflowCmd, workflowItem, workflowCmd.getTotalRefund(), cmd.getAmount(), cmd.getReason());

        caseNotes = processPaymentSortCode(caseNotes, workflowCmd, workflowItem, workflowCmd.getPayeeSortCode(), cmd.getPayeeSortCode(), cmd.getReason());
        
        caseNotes = processPaymentAccountNumber(caseNotes, workflowCmd, workflowItem, workflowCmd.getPayeeAccountNumber(), cmd.getPayeeAccountNumber(), cmd.getReason());
        
        caseNotes = processPaymentStationId(caseNotes, workflowCmd, workflowItem, workflowItem.getRefundDetails().getStationId(), cmd.getStationId(),
                        cmd.getReason());
        
        caseNotes = processTargetCardNumber(caseNotes, workflowCmd, workflowItem, workflowCmd.getTargetCardNumber(), cmd.getTargetCardNumber(), cmd.getReason());
        
        return caseNotes;
    }

    protected List<CaseHistoryNote> processPaymentMethod(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, String currentPaymentMethod, String inputPaymentMethod, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;
        
        if (StringUtils.isNotBlank(inputPaymentMethod) && !inputPaymentMethod.equalsIgnoreCase(currentPaymentMethod)) {
        	caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentMethodFieldText, currentPaymentMethod,
                    inputPaymentMethod,
                    reason);
        	if(StringUtils.isNotBlank(inputPaymentMethod) && inputPaymentMethod.equalsIgnoreCase(WEB_CREDIT)){
            	inputPaymentMethod = PaymentType.WEB_ACCOUNT_CREDIT.code();
            } else if(StringUtils.isNotBlank(inputPaymentMethod) && inputPaymentMethod.equalsIgnoreCase(ADHOC_LOAD)) {
            	inputPaymentMethod = PaymentType.AD_HOC_LOAD.code();
            } else if(StringUtils.isNotBlank(inputPaymentMethod) && inputPaymentMethod.equalsIgnoreCase(PAYMENT_CARD)) {
            	inputPaymentMethod = PaymentType.PAYMENT_CARD.code();
            }
            workflowCmd.setPaymentMethod(inputPaymentMethod);
            workflowItem.setPaymentMethod(inputPaymentMethod);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setPaymentType(PaymentType.lookUpPaymentType(inputPaymentMethod));
            runtimeService.setVariable(workflowItem.getExecutionId(), PAYMENT_METHOD, inputPaymentMethod.toUpperCase());
        }
        return caseNotes;
    }

    protected List<CaseHistoryNote> processPaymentName(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, WorkflowEditCmd workflowEditCmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        String currentPaymentName = workflowCmd.getPaymentName();
        CustomerDTO inputCustomer = new CustomerDTO(workflowEditCmd.getPaymentTitle(), workflowEditCmd.getPaymentFirstName(), workflowEditCmd.getPaymentInitials(),
                        workflowEditCmd.getPaymentLastName());
        String inputPaymentName = formatName(workflowEditCmd.getPaymentTitle(), workflowEditCmd.getPaymentFirstName(), workflowEditCmd.getPaymentInitials(), workflowEditCmd.getPaymentLastName());

        if ((PaymentType.BACS.code().equalsIgnoreCase(workflowItem.getPaymentMethod()) 
        		|| PaymentType.CHEQUE.code().equalsIgnoreCase(workflowItem.getPaymentMethod())) && inputPaymentName != null && !inputPaymentName.equalsIgnoreCase(currentPaymentName)) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentNameText, currentPaymentName, inputPaymentName,
                            workflowEditCmd.getReason());
            workflowCmd.setPaymentName(inputPaymentName);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();

            refundDetails.setAlternativeRefundPayee(inputCustomer);
        }

        return caseNotes;
    }

    protected List<CaseHistoryNote> processPaymentAddress(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, List<String> paymentAddress, WorkflowEditCmd workflowEditCmd) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;
        List<String> current = paymentAddress;
        List<String> input = AddressFormatUtil.formatAddress(workflowEditCmd.getPaymentHouseNameNumber(), workflowEditCmd.getPaymentStreet(),
                workflowEditCmd.getPaymentTown(), workflowEditCmd.getPaymentPostCode(), workflowEditCmd.getPaymentCountry());

        if(paymentAddress != null && input.get(0) != null && isAddressUpdated(current, input)){
        	workflowCmd.setPaymentAddress(input);
        	RefundDetailDTO refundDetails = workflowItem.getRefundDetails();

        	AddressDTO inputAddress = new AddressDTO(workflowEditCmd.getPaymentHouseNameNumber(), workflowEditCmd.getPaymentStreet(), workflowEditCmd.getPaymentTown(),
        			workflowEditCmd.getPaymentPostCode(), countryDataService.findCountryByCode(workflowEditCmd.getPaymentCountry().getCode()));

        	refundDetails.setAlternativeAddress(inputAddress);
        	runtimeService.setVariable(workflowItem.getExecutionId(), CUSTOMER_ALTERNATIVE_ADDRESS, inputAddress);

        	caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentAddressText, current.toString(), input.toString(),
        			workflowEditCmd.getReason());
        }
        return caseNotes;
    }
    

    protected List<CaseHistoryNote> processPaymentAmount(List<CaseHistoryNote> caseHistoryNotes, WorkflowCmd workflowCmd,
                    WorkflowItemDTO workflowItem, Long currentPaymentAmount, Integer input, String reason) {
        List<CaseHistoryNote> caseNotes = caseHistoryNotes;

        Long inputPaymentAmount = input == null ? null : input.longValue();
        if (inputPaymentAmount != null && !currentPaymentAmount.equals(inputPaymentAmount)) {
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentAmountText,
                            formatPenceWithHtmlCurrencySymbol(currentPaymentAmount.intValue()), formatPenceWithHtmlCurrencySymbol(input), reason);
            workflowCmd.setTotalRefund(inputPaymentAmount);
            RefundDetailDTO refundDetails = workflowItem.getRefundDetails();
            refundDetails.setTotalRefundAmount(inputPaymentAmount);
        }

        return caseNotes;
    }

    protected boolean isAddressUpdated(List<String> current, List<String> input) {
        if (current.size() != input.size()) {
            return true;
        }
        
        for (int addressLine = 0; addressLine < current.size(); addressLine++) {
            String inputAddressLine = input.get(addressLine);
            String workflowCmdAddressLine = current.get(addressLine);

            if (!inputAddressLine.equalsIgnoreCase(workflowCmdAddressLine)) {
                return true;
            }
        }

        return false;
    }
    
    protected List<CaseHistoryNote> processPaymentSortCode(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd,
            WorkflowItemDTO workflowItem, String currentSortCode, String inputSortCode, String reason) {
    	if(inputSortCode != null && !inputSortCode.equalsIgnoreCase(currentSortCode)){
    		caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentSortCodeText, currentSortCode, inputSortCode,
                    reason);
    		workflowCmd.setPayeeSortCode(inputSortCode);
    		workflowItem.getRefundDetails().setPayeeSortCode(inputSortCode);
    	}
    	
    	return caseNotes;
    }
    
    protected List<CaseHistoryNote> processPaymentAccountNumber(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd,
            WorkflowItemDTO workflowItem, String currentAccountNumber, String inputAccountNumber, String reason) {
    	if(inputAccountNumber != null && !inputAccountNumber.equalsIgnoreCase(currentAccountNumber)){
    		caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentAccountNumberText, currentAccountNumber, inputAccountNumber,
                    reason);
    		workflowCmd.setPayeeAccountNumber(inputAccountNumber);
    		workflowItem.getRefundDetails().setPayeeAccountNumber(inputAccountNumber);
    	}
    	
    	return caseNotes;
    }
    
    protected List<CaseHistoryNote> processPaymentStationId(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd,
            WorkflowItemDTO workflowItem, Long currentStationId, Long inputStationId, String reason) {
    	if(inputStationId != null && !inputStationId.equals(currentStationId)){
            String inputStation = locationDataService.getActiveLocationById(inputStationId.intValue()).getName();
    		if(currentStationId == null){
                caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentStationText, BLANK, inputStation,
                        reason);
    		} else {
                String currentStation = locationDataService.getActiveLocationById(currentStationId.intValue()).getName();
                caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentStationText, currentStation, inputStation,
                    reason);
    		}
            workflowCmd.setStation(inputStation);
    		workflowItem.getRefundDetails().setStationId(inputStationId);
    	}
    	
    	return caseNotes;
    }
    
    protected List<CaseHistoryNote> processTargetCardNumber(List<CaseHistoryNote> caseNotes, WorkflowCmd workflowCmd,
            WorkflowItemDTO workflowItem, String currentTargetCardNumber, String inputTargetCardNumber, String reason) {
    	if(inputTargetCardNumber != null && !inputTargetCardNumber.equalsIgnoreCase(currentTargetCardNumber)){
            caseNotes = caseHistoryNoteService.addChangesCaseNote(caseNotes, workflowCmd, paymentTargetCardNumberText, currentTargetCardNumber,
                            inputTargetCardNumber,
                    reason);
    		workflowCmd.setTargetCardNumber(inputTargetCardNumber);
    		workflowItem.getRefundDetails().setTargetCardNumber(inputTargetCardNumber);
    	}
    	
    	return caseNotes;
    }
}
