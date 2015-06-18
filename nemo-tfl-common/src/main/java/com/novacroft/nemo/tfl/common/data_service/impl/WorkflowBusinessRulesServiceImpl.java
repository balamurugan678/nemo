package com.novacroft.nemo.tfl.common.data_service.impl;

import static com.novacroft.nemo.common.constant.DateConstant.TIME_TO_MINUTE_PATTERN;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ADDRESS_ID;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ALTERNATIVE_ADDRESS_ID;
import static com.novacroft.nemo.tfl.common.constant.WorkflowFields.CUSTOMER_ID;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.codehaus.plexus.util.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.format.DateTimeFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.novacroft.nemo.common.constant.Durations;
import com.novacroft.nemo.common.constant.LocaleConstant;
import com.novacroft.nemo.common.data_service.AddressDataService;
import com.novacroft.nemo.common.data_service.ContentDataService;
import com.novacroft.nemo.common.data_service.SystemParameterDataService;
import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.common.transfer.SystemParameterDTO;
import com.novacroft.nemo.common.utils.DateUtil;
import com.novacroft.nemo.common.utils.TravelCardDurationUtil;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.data_service.CardDataService;
import com.novacroft.nemo.tfl.common.data_service.CartDataService;
import com.novacroft.nemo.tfl.common.data_service.CustomerDataService;
import com.novacroft.nemo.tfl.common.data_service.OrderDataService;
import com.novacroft.nemo.tfl.common.data_service.PaymentCardSettlementDataService;
import com.novacroft.nemo.tfl.common.data_service.WorkflowBusinessRulesService;
import com.novacroft.nemo.tfl.common.transfer.CardDTO;
import com.novacroft.nemo.tfl.common.transfer.CartDTO;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;
import com.novacroft.nemo.tfl.common.transfer.ItemDTO;
import com.novacroft.nemo.tfl.common.transfer.OrderDTO;
import com.novacroft.nemo.tfl.common.transfer.ProductItemDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;
import com.novacroft.nemo.tfl.innovator.workflow.processBeans.RefundDetailDTO;

@Service(value = "workflowBusinessRulesService")
public class WorkflowBusinessRulesServiceImpl implements WorkflowBusinessRulesService {

   
	private static final String RULE_BREACH_DESCRIPTION_THIS_OYSTER_CARD_HAS_BEEN_HOTLISTED_OR_HIDDEN_KEY = "rule_breach_description_this_oyster_card_has_been_hotlisted_or_hidden";
    private static final String RULE_BREACH_DESCRIPTION_EXISTING_MATCH_FOUND_FOR_ANOTHER_PAYMENT_WITH_THE_SAME_OYSTER_CARD_NUMBER_REFUND_AMOUNT_AND_DATE_KEY = "rule_breach_description_existing_match_found_for_another_payment_with_the_same_oyster_card_number_refund_amount_and_date";
    private static final String RULE_BREACH_DESCRIPTION_REFUND_OCCURRED_OUTSIDE_NORMAL_WORKING_HOURS_KEY = "rule_breach_description_refund_occurred_outside_normal_working_hours";
    private static final String RULE_BREACH_DESCRIPTION_LOW_PAYMENT_THRESHOLD_FOR_CHEQUE_PAYMENT_KEY = "rule_breach_description_low_payment_threshold_for_cheque_payment";
    private static final String RULE_BREACH_DESCRIPTION_ACCOUNT_NUMBER_HAS_EXCEEDED_CLAIM_CEILING_KEY = "rule_breach_description_account_number_has_exceeded_claim_ceiling";
    private static final String RULE_BREACH_DESCRIPTION_ADDRESS_HAS_EXCEEDED_CLAIM_CEILING_KEY = "rule_breach_description_address_has_exceeded_claim_ceiling";
    private static final String RULE_BREACH_DESCRIPTION_CUSTOMER_ACCOUNT_HAS_EXCEEDED_CLAIM_CEILING_KEY = "rule_breach_description_customer_account_has_exceeded_claim_ceiling";
    private static final String RULE_BREACH_DESCRIPTION_EXISTING_MATCH_FOUND_FOR_ANOTHER_PAYMENT_WITH_THE_SAME_ACCOUNT_NUMBER_OYSTER_CARD_NUMBER_AND_DATE_KEY = "rule_breach_description_existing_match_found_for_another_payment_with_the_same_account_number_oyster_card_number_and_date";
    private static final String RULE_BREACH_DESCRIPTION_ALTERNATIVE_ADDRESS_KEY = "rule_breach_description_alternative_address";
    private static final String RULE_BREACH_DESCRIPTION_DEFAULTS_CHANGED_KEY = "rule_breach_description_defaults_changed";
    private static final String RULE_BREACH_DESCRIPTION_DAYS_BETWEEN_CANCEL_SURRENDER_AND_REFUND_DATE_EXCEEDED_KEY = "rule_breach_description_days_between_cancel_surrender_and_refund_date_exceeded";
    private static final String RULE_BREACH_DESCRIPTION_PREVIOUSLY_EXCHANGED_TICKET_KEY = "rule_breach_description_previously_exchanged_ticket";
    
    private static final String WORKFLOW_REFUNDS_BY_SCENARIO_CEILING_HOURS = "workflowRefundsByScenarioCeilingHours";
    private static final String WORKFLOW_REFUNDS_BY_SCENARIO_CEILING = "workflowRefundsByScenarioCeiling";
    private static final String WORKFLOW_REFUND_LOW_CHEQUE_VALUE_THRESHOLD = "workflowRefundLowChequeValueThreshold";
    private static final String WORKFLOW_REFUND_TIME_WINDOW_UPPER_VALUE = "workflowRefundTimeWindowUpperValue";
    private static final String WORKFLOW_REFUND_TIME_WINDOW_LOWER_VALUE = "workflowRefundTimeWindowLowerValue";
    private static final String WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_HOURS = "workflowRefundsPerHourlyPeriodCeilingHours";
    private static final String WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_PERIOD = "workflowRefundsPerHourlyPeriodCeilingPeriod";
    private static final String WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_DAYS = "workflowRefundsQuantityPerPeriodDays";
    private static final String WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_CEILING = "workflowRefundsQuantityPerPeriodCeiling";
    public static final String WORKFLOW_REFUNDS_SEASONAL_BACK_DATED_CANCEL_AND_SURRENDER_DAYS_CEILING = "workflowRefundsSeasonalBackDatedCancelAndSurrenderDaysCeiling";
	

    protected Integer workflowRefundsQuantityPerPeriodCeiling;
    protected Integer workflowRefundsQuantityPerPeriodDays;
    protected Integer workflowRefundsPerHourlyPeriodCeiling;
    protected Integer workflowRefundsPerHourlyPeriodCeilingHours;
    protected Integer workflowRefundLowChequeValueThreshold;
    protected Integer workflowRefundsByScenarioCeiling;
    protected Integer workflowRefundsByScenarioCeilingHours;
    protected DateTime workflowRefundTimeWindowLowerValue;
    protected DateTime workflowRefundTimeWindowUpperValue;
    protected Integer workflowRefundsSeasonalBackDatedCancelAndSurrenderDaysCeiling;

    @Autowired
    protected SystemParameterDataService systemParameterService;
    @Autowired
    protected CardDataService cardService;
    @Autowired
    protected PaymentCardSettlementDataService paymentService;
    @Autowired
    protected OrderDataService orderDataService;
    @Autowired
    protected ContentDataService content;
    @Autowired
    protected AddressDataService addressService;
    @Autowired
    protected CartDataService cartDataService;
    @Autowired
    protected CardDataService cardDataService;
    @Autowired
    protected CustomerDataService customerService;
    @Autowired(required = false)
    protected TaskService taskService;


    private String ruleBreachDescriptionThisOysterCardHasBeenHotlistedOrHiddenText = "";
    private String ruleBreachDescriptionExistingMatchFoundForAnotherPaymentWithSameOysterCardRefundAmountAndRefundDateText = "";
    private String ruleBreachDescriptionRefundOccuredOutsideNormalWorkingHoursText = "";
    private String ruleBreachDescriptionLowPaymentThresholdForChequePaymentText = "";
    private String ruleBreachDescriptionAccountNumberHasExceededClaimCeilingText = "";
    private String ruleBreachDescriptionAddressHasExceededClaimCeilingText = "";
    private String ruleBreachDescriptionCustomerAccountHasExceededClaimCeilingText = "";
    private String ruleBreachDescriptionExistingMatchFoundForAnotherPaymentWithTheSameOysterCardNumberAndDateText = "";
    private String ruleBreachDescriptionAlternativeAddressText = "";
    private String ruleBreachDescriptionDefaultesChangedText = "";
    private String ruleBreachDescriptionDaysBetweenCancelSurrenderAndRefundDateExceeded = "";
    private String ruleBreachDescriptionPreviouslyExchangedTicket = "";
    
    WorkflowBusinessRulesServiceImpl() {
       
    }

    @Override
    public List<String> checkBusinessRules(WorkflowItemDTO workflowItem){
        initialize();
        List<String> ruleBreaches = new ArrayList<String>();
        
        if (!RefundScenarioEnum.ANONYMOUS_GOODWILL_REFUND.equals(workflowItem.getRefundDetails().getRefundScenario())) {
			hasAlternativePayeeRequested(workflowItem, ruleBreaches);
			hasExceededBackDatingPeriodForCancelAndSurrender(workflowItem, ruleBreaches);
			hasAlternativeAddressRequested(workflowItem, ruleBreaches);
			hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(workflowItem, ruleBreaches);
			hasAccountExceededClaimCeiling(workflowItem, ruleBreaches);
			hasAddressExceededClaimCeiling(workflowItem, ruleBreaches);
			hasAccountExceededRefundsPerScenarioCeiling(workflowItem, ruleBreaches);
			hasLowChequeRefundValue(workflowItem, ruleBreaches);
			isInelligibleCardForRefund(workflowItem, ruleBreaches);
			hasChangedFromDefaults(workflowItem, ruleBreaches);
			isCustomerOnBlockedList(workflowItem, ruleBreaches);
			hasOverlapAfterFareRevision(workflowItem, ruleBreaches);
			hasWarrantPaymentMismatch(workflowItem, ruleBreaches);
			hasPreviouslyExchangedTicket(workflowItem, ruleBreaches);
		}
        hasRefundedOusideWorkHours(workflowItem, ruleBreaches);
        hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(workflowItem, ruleBreaches);
		return ruleBreaches;
    }

    protected List<String> hasExceededBackDatingPeriodForCancelAndSurrender(WorkflowItemDTO workflow, List<String> ruleBreaches) {
    	 assert workflow != null;
         assert workflow.getRefundDetails() != null;
         RefundDetailDTO refundDetailDTO = workflow.getRefundDetails();
         if(RefundScenarioEnum.CANCEL_AND_SURRENDER.equals(refundDetailDTO.getRefundScenario())){
             String caseNumberAsApprovalId = workflow.getCaseNumber();
             CartDTO cartDTO =  cartDataService.findByApprovalId(new Long(caseNumberAsApprovalId));
        	 for(ItemDTO itemDTO : cartDTO.getCartItems()){
        		 if(backDateLimitExceededForAnnualTicket(itemDTO, refundDetailDTO.getRefundDate())){
                    ruleBreaches.add(ruleBreachDescriptionDaysBetweenCancelSurrenderAndRefundDateExceeded);
        			return ruleBreaches; 
        		 }
        	 }
         }
         return ruleBreaches;
  }

    
    protected List<String> hasPreviouslyExchangedTicket(WorkflowItemDTO workflow, List<String> ruleBreaches) {
   	 assert workflow != null;
        assert workflow.getRefundDetails() != null;
        if(workflow.getRefundDetails().getPreviouslyExchanged()){
            ruleBreaches.add(ruleBreachDescriptionPreviouslyExchangedTicket);
        }
        return ruleBreaches;
    }
    
    
    protected boolean backDateLimitExceededForAnnualTicket(ItemDTO itemDTO, DateTime refundDate){
        if (itemDTO instanceof ProductItemDTO)
    	{
    		ProductItemDTO productItemDTO = (ProductItemDTO)itemDTO;
    		if (isSeasonalTicket(productItemDTO) && productItemDTO.getDateOfCanceAndSurrender() != null) {
				Date dateOfCanceAndSurrender = productItemDTO.getDateOfCanceAndSurrender();
				Date limitingDate =  DateUtil.addDaysToDate(refundDate.toDate(), workflowRefundsSeasonalBackDatedCancelAndSurrenderDaysCeiling);
				return dateOfCanceAndSurrender.after(limitingDate);
			}
    	}
    	return false;
    }
    
    protected boolean isSeasonalTicket(ProductItemDTO productItemDTO){
    	if(productItemDTO.getStartDate() != null && productItemDTO.getEndDate() != null ){
    		return Durations.ANNUAL.getDurationType().equals(TravelCardDurationUtil.getTravelCardDurationFromStartAndEndDate(DateUtil.formatDate(productItemDTO.getStartDate()).toString(), DateUtil.formatDate(productItemDTO.getEndDate()).toString()));
    	}
    	return false;
    }
	protected void initialize() {
        SystemParameterDTO workflowRefundsQuantityPerPeriodCeilingDTO = systemParameterService
                        .findByCode(WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_CEILING);
        SystemParameterDTO workflowRefundsQuantityPerPeriodDaysDTO = systemParameterService.findByCode(WORKFLOW_REFUNDS_QUANTITY_PER_PERIOD_DAYS);
        SystemParameterDTO workflowRefundsPerHourlyPeriodCeilingPeriodDTO = systemParameterService
                        .findByCode(WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_PERIOD);
        SystemParameterDTO workflowRefundsPerHourlyPeriodCeilingHoursDTO = systemParameterService
                        .findByCode(WORKFLOW_REFUNDS_PER_HOURLY_PERIOD_CEILING_HOURS);
        SystemParameterDTO workflowRefundTimeWindowLowerValueDTO = systemParameterService.findByCode(WORKFLOW_REFUND_TIME_WINDOW_LOWER_VALUE);
        SystemParameterDTO workflowRefundTimeWindowUpperValueDTO = systemParameterService.findByCode(WORKFLOW_REFUND_TIME_WINDOW_UPPER_VALUE);
        SystemParameterDTO workflowRefundLowChequeValueThresholdDTO = systemParameterService.findByCode(WORKFLOW_REFUND_LOW_CHEQUE_VALUE_THRESHOLD);
        SystemParameterDTO workflowRefundsByScenarioCeilingDTO = systemParameterService.findByCode(WORKFLOW_REFUNDS_BY_SCENARIO_CEILING);
        SystemParameterDTO workflowRefundsByScenarioCeilingHoursDTO = systemParameterService.findByCode(WORKFLOW_REFUNDS_BY_SCENARIO_CEILING_HOURS);
        SystemParameterDTO workflowRefundsSeasonalBackDatedCancelAndSurrenderDaysCeilingDTO = systemParameterService.findByCode(WORKFLOW_REFUNDS_SEASONAL_BACK_DATED_CANCEL_AND_SURRENDER_DAYS_CEILING);

        workflowRefundsQuantityPerPeriodCeiling = Integer.valueOf(workflowRefundsQuantityPerPeriodCeilingDTO.getValue());
        workflowRefundsQuantityPerPeriodDays = Integer.valueOf(workflowRefundsQuantityPerPeriodDaysDTO.getValue());
        workflowRefundsPerHourlyPeriodCeiling = Integer.valueOf(workflowRefundsPerHourlyPeriodCeilingPeriodDTO.getValue());
        workflowRefundsPerHourlyPeriodCeilingHours = Integer.valueOf(workflowRefundsPerHourlyPeriodCeilingHoursDTO.getValue());
        workflowRefundLowChequeValueThreshold = Integer.valueOf(workflowRefundLowChequeValueThresholdDTO.getValue());
        workflowRefundTimeWindowLowerValue = DateTime.parse(workflowRefundTimeWindowLowerValueDTO.getValue(),
                        DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN));
        workflowRefundTimeWindowUpperValue = DateTime.parse(workflowRefundTimeWindowUpperValueDTO.getValue(),
                        DateTimeFormat.forPattern(TIME_TO_MINUTE_PATTERN));
        workflowRefundsByScenarioCeiling = Integer.valueOf(workflowRefundsByScenarioCeilingDTO.getValue());
        workflowRefundsByScenarioCeilingHours = Integer.valueOf(workflowRefundsByScenarioCeilingHoursDTO.getValue());
        workflowRefundsSeasonalBackDatedCancelAndSurrenderDaysCeiling = Integer.valueOf(workflowRefundsSeasonalBackDatedCancelAndSurrenderDaysCeilingDTO.getValue());
        
        
        
        ruleBreachDescriptionThisOysterCardHasBeenHotlistedOrHiddenText = content.findByLocaleAndCode(RULE_BREACH_DESCRIPTION_THIS_OYSTER_CARD_HAS_BEEN_HOTLISTED_OR_HIDDEN_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionExistingMatchFoundForAnotherPaymentWithSameOysterCardRefundAmountAndRefundDateText =  content.findByLocaleAndCode(RULE_BREACH_DESCRIPTION_EXISTING_MATCH_FOUND_FOR_ANOTHER_PAYMENT_WITH_THE_SAME_OYSTER_CARD_NUMBER_REFUND_AMOUNT_AND_DATE_KEY,LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionRefundOccuredOutsideNormalWorkingHoursText =  content.findByLocaleAndCode(RULE_BREACH_DESCRIPTION_REFUND_OCCURRED_OUTSIDE_NORMAL_WORKING_HOURS_KEY,LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionLowPaymentThresholdForChequePaymentText = content
                        .findByLocaleAndCode(RULE_BREACH_DESCRIPTION_LOW_PAYMENT_THRESHOLD_FOR_CHEQUE_PAYMENT_KEY,
                                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionAccountNumberHasExceededClaimCeilingText = content.findByLocaleAndCode(
                        RULE_BREACH_DESCRIPTION_ACCOUNT_NUMBER_HAS_EXCEEDED_CLAIM_CEILING_KEY,
                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionAddressHasExceededClaimCeilingText = content.findByLocaleAndCode(
                        RULE_BREACH_DESCRIPTION_ADDRESS_HAS_EXCEEDED_CLAIM_CEILING_KEY, LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString())
                        .getContent();
        ruleBreachDescriptionCustomerAccountHasExceededClaimCeilingText = content.findByLocaleAndCode(
                        RULE_BREACH_DESCRIPTION_CUSTOMER_ACCOUNT_HAS_EXCEEDED_CLAIM_CEILING_KEY,
                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionExistingMatchFoundForAnotherPaymentWithTheSameOysterCardNumberAndDateText = content
                        .findByLocaleAndCode(
                                        RULE_BREACH_DESCRIPTION_EXISTING_MATCH_FOUND_FOR_ANOTHER_PAYMENT_WITH_THE_SAME_ACCOUNT_NUMBER_OYSTER_CARD_NUMBER_AND_DATE_KEY,
                                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionAlternativeAddressText = content.findByLocaleAndCode(RULE_BREACH_DESCRIPTION_ALTERNATIVE_ADDRESS_KEY,
                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionDefaultesChangedText = content.findByLocaleAndCode(RULE_BREACH_DESCRIPTION_DEFAULTS_CHANGED_KEY,
                        LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionDaysBetweenCancelSurrenderAndRefundDateExceeded = content.findByLocaleAndCode(
                        RULE_BREACH_DESCRIPTION_DAYS_BETWEEN_CANCEL_SURRENDER_AND_REFUND_DATE_EXCEEDED_KEY,
                LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
        ruleBreachDescriptionPreviouslyExchangedTicket = content.findByLocaleAndCode(RULE_BREACH_DESCRIPTION_PREVIOUSLY_EXCHANGED_TICKET_KEY,
                LocaleConstant.ENGLISH_UNITED_KINGDOM_LOCALE.toString()).getContent();
    }

    protected List<String> hasAlternativePayeeRequested(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        assert workflow != null;
        assert workflow.getRefundDetails() != null;

        if (workflow.getRefundDetails().getAlternativeRefundPayee() != null) {

            CustomerDTO alternativeRefundPayee = workflow.getRefundDetails().getAlternativeRefundPayee();
            CustomerDTO customer = customerService.findById(workflow.getRefundDetails().getCustomerId());

            if (!customer.getFirstName().equals(alternativeRefundPayee.getFirstName()) || !customer.getLastName().equals(alternativeRefundPayee.getLastName())
                            || ((customer.getInitials() != null) && !customer.getInitials().equals(alternativeRefundPayee.getInitials()))) {
                ruleBreaches.add("nominated payee");
            }
        }

        return ruleBreaches;
    }


    protected List<String> hasAlternativeAddressRequested(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        assert workflow != null;
        assert workflow.getRefundDetails() != null;

        if (workflow.getRefundDetails().getAlternativeAddress() != null && StringUtils.isNotEmpty(workflow.getRefundDetails().getAlternativeAddress().getStreet())
                && StringUtils.isNotEmpty(workflow.getRefundDetails().getAlternativeAddress().getPostcode())) {

            CustomerDTO customer = customerService.findById(workflow.getRefundDetails().getCustomerId());
            AddressDTO address = addressService.findById(customer.getAddressId());

            if (!workflow.getRefundDetails().getAlternativeAddress().equals(address)) {

                ruleBreaches.add(ruleBreachDescriptionAlternativeAddressText);
            }
        }
        return ruleBreaches;

    }

    // if customer account or oystercard number & refund amount and refund date are the same as an outstanding payment
    protected List<String> hasMatchingOutstandingPaymentsForAccountOysterCardRefundAndDate(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        assert(workflow.getRefundDetails().getRefundDate() != null); 
        
        List<OrderDTO> matchingPayments = orderDataService.findByCustomerCardNumberPriceAndDate(workflow.getRefundDetails().getCardNumber(), workflow
                        .getRefundDetails().getCustomerId(), -workflow.getRefundDetails().getTotalRefundAmount(), workflow.getRefundDetails()
                        .getRefundDate());

        if ( matchingPayments.size() > 0){
            ruleBreaches.add(ruleBreachDescriptionExistingMatchFoundForAnotherPaymentWithTheSameOysterCardNumberAndDateText);
        }
        return ruleBreaches;

    }

    // if customer account & refund claims number > X in Y days
    protected List<String> hasAccountExceededClaimCeiling(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        assert workflowRefundsQuantityPerPeriodDays > 0;
        DateTime now = new DateTime();
        DateTime thresholdTime = now.minusDays(workflowRefundsQuantityPerPeriodDays);
        List<OrderDTO> orders = orderDataService.findByCustomerIdAndDuration(workflow.getRefundDetails().getCustomerId(), thresholdTime, now);
        Integer ordersCount = (orders == null? 0 : orders.size());
        
        Integer matchingAccountCount = searchWorkflowItemsForMatchesInTimePeriod(workflow, thresholdTime, CUSTOMER_ID, workflow.getRefundDetails().getCustomerId());
        if(ordersCount + matchingAccountCount >= workflowRefundsQuantityPerPeriodCeiling){
            ruleBreaches.add(ruleBreachDescriptionCustomerAccountHasExceededClaimCeilingText);
        }
        
        return ruleBreaches;
    }

    // if customer address & refund claims number > X in Y days
    protected List<String> hasAddressExceededClaimCeiling(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        assert workflowRefundsQuantityPerPeriodDays > 0;
        DateTime now = new DateTime();
        DateTime thresholdTime = now.minusHours(workflowRefundsPerHourlyPeriodCeilingHours);
        List<OrderDTO> orders = orderDataService.findByAddressIdAndDuration(workflow.getRefundDetails().getCustomerId(), thresholdTime, now);
        Integer ordersCount = (orders == null? 0 : orders.size());
        
        Integer matchingAddressCount = searchWorkflowItemsForMatchesInTimePeriod(workflow, thresholdTime, CUSTOMER_ADDRESS_ID, workflow.getRefundDetails().getAddress().getId());
        Integer matchingAlternativeAddressCount = searchWorkflowItemsForMatchesInTimePeriod(workflow, thresholdTime, CUSTOMER_ALTERNATIVE_ADDRESS_ID, workflow.getRefundDetails().getAlternativeAddress().getId());

        if(ordersCount + matchingAddressCount + matchingAlternativeAddressCount > workflowRefundsPerHourlyPeriodCeiling){
            ruleBreaches.add(ruleBreachDescriptionAddressHasExceededClaimCeilingText);
        }
        
        return ruleBreaches ;

    }

    protected Integer searchWorkflowItemsForMatchesInTimePeriod(WorkflowItemDTO workflow, DateTime thresholdTime, String searchKey, Object searchValue) {
        TaskQuery taskQuery = taskService.createTaskQuery();
        taskQuery.processVariableValueEquals(searchKey, searchValue);
        taskQuery.taskCreatedAfter(thresholdTime.toDate());
        List<Task> taskList = taskQuery.list();
        
        return(taskList == null? 0 : taskList.size());

    }

    // if user account creates > x refunds by scenario in Y hours
    protected List<String> hasAccountExceededRefundsPerScenarioCeiling(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        DateTime now = new DateTime();
        DateTime thresholdTime = now.minusHours(workflowRefundsByScenarioCeilingHours);
        List<OrderDTO> orders = orderDataService.findByCustomerIdAndDuration(workflow.getRefundDetails().getCustomerId(), thresholdTime, now);
        
        if(orders.size() > workflowRefundsByScenarioCeiling){
            ruleBreaches.add(ruleBreachDescriptionAccountNumberHasExceededClaimCeilingText);
        }
        
        return ruleBreaches;

    }

    // refund = < £5.00 and payment type = cheque payment
    protected List<String> hasLowChequeRefundValue(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        if (workflow.getRefundDetails().getTotalRefundAmount() < workflowRefundLowChequeValueThreshold
                        && workflow.getRefundDetails().getPaymentType().equals(PaymentType.CHEQUE)) {
            ruleBreaches.add(ruleBreachDescriptionLowPaymentThresholdForChequePaymentText);
        }
        return ruleBreaches;
    }

    // If user account (for users with certain roles) submits a refund before 07:50 time OR after 20:20 time
    protected List<String> hasRefundedOusideWorkHours(WorkflowItemDTO workflowItem, List<String> ruleBreaches) {
        // TODO: check which roles....
        DateTimeComparator comparator = DateTimeComparator.getTimeOnlyInstance();

        if (comparator.compare(workflowItem.getCreatedTimeAsDateTime(), workflowRefundTimeWindowLowerValue) < 0
                        || comparator.compare(workflowItem.getCreatedTimeAsDateTime(), workflowRefundTimeWindowUpperValue) > 0) {
            ruleBreaches.add(ruleBreachDescriptionRefundOccuredOutsideNormalWorkingHoursText);
        }
        return ruleBreaches;
    }

    // if payment oyster card number & refund amount & refund date are the same as an outstanding payment
    protected List<String> hasMatchingOutstandingPaymentsForOysterCardDateAndPrice(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        List<OrderDTO> matchingPayments = orderDataService.findByRefundedCardNumberPriceAndDate(workflow.getRefundDetails().getTargetCardNumber(),
                        -workflow.getRefundDetails().getTotalRefundAmount(), workflow.getRefundDetails().getRefundDate());

        if(matchingPayments.size() > 0){
            ruleBreaches.add(ruleBreachDescriptionExistingMatchFoundForAnotherPaymentWithSameOysterCardRefundAmountAndRefundDateText);
        }
        
        return ruleBreaches;
    }

    // If oyster card refunded = hotlisted OR hidden card that has been refunded or replaced previously.
    protected List<String> isInelligibleCardForRefund(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        if (workflow.getRefundDetails().getCardId() != null) {
			// TODO : Add tests for hidden cards. Waiting on hidden card definition...
			CardDTO card = cardService.findById(workflow.getRefundDetails().getCardId());
			if (card != null && card.getHotlistReason() != null) {
				ruleBreaches
						.add(ruleBreachDescriptionThisOysterCardHasBeenHotlistedOrHiddenText);

			}
		}
		return ruleBreaches;

    }

    protected List<String> hasChangedFromDefaults(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        assert workflow != null;
        assert workflow.getRefundDetails() != null;

        if (haveDefaultsChanged(workflow.getRefundDetails())) {
            ruleBreaches.add(ruleBreachDescriptionDefaultesChangedText);
        }
        return ruleBreaches;
    }

    protected boolean haveDefaultsChanged(RefundDetailDTO refundDetails) {
        return refundDetails.getCalculationBasisChanged() || refundDetails.getPayAsYouGoChanged() || refundDetails.getAdminFeeChanged();
    }

    /*
     * To be completed during stage two ----------------------------------------------------------
     */

    protected List<String> isCustomerOnBlockedList(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        // TODO this is pending with TFL. There is currently no block list. Expected in the Autumn Drop (Andrew Parker 15/05/2014).

        return ruleBreaches;
    }

    // Overlaps and surrenders where a 2nd ticket is involved - If 1 season ticket expires after a fares revision & second ticket of the same zone
    // starts in the 2nd week period before the same fares revision
    protected List<String> hasOverlapAfterFareRevision(WorkflowItemDTO workflow, List<String> ruleBreaches) {

        return ruleBreaches;
    }

    // if purchase method = warrant & refund payment not = warrant
    protected List<String> hasWarrantPaymentMismatch(WorkflowItemDTO workflow, List<String> ruleBreaches) {
        // TODO: Currently there is no ability to either pay or refund via warrant method.
        // On hold until further clarified

        return ruleBreaches;

    }

}
