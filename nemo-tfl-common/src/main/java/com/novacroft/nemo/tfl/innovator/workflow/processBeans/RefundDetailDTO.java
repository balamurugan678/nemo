package com.novacroft.nemo.tfl.innovator.workflow.processBeans;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.joda.time.DateTime;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.command.impl.GoodwillPaymentItemCmd;
import com.novacroft.nemo.tfl.common.command.impl.RefundItemCmd;
import com.novacroft.nemo.tfl.common.constant.ApplicationName;
import com.novacroft.nemo.tfl.common.constant.PaymentType;
import com.novacroft.nemo.tfl.common.constant.RefundCalculationBasis;
import com.novacroft.nemo.tfl.common.constant.RefundDepartmentEnum;
import com.novacroft.nemo.tfl.common.constant.RefundScenarioEnum;
import com.novacroft.nemo.tfl.common.domain.Refund;
import com.novacroft.nemo.tfl.common.transfer.CustomerDTO;

/*
 * Bean to hold refund workflow task variables
 */
public class RefundDetailDTO implements Serializable {
    /*
     * NOTE When you use a Java bean as a process variable in Activiti, make sure the bean implements the Serializable interface because the process
     * variable will be persisted to the Activiti Engine database.
     */
    static final long serialVersionUID = 3571851127251125011L;

    private String name;
    private Long cardId;
    private Long webAccountId;
    private Long customerId;
    private Refund refundEntity;
    private CustomerDTO customer;
    private AddressDTO address;
    private AddressDTO alternativeAddress;
    private CustomerDTO alternativeRefundPayee;
    private PaymentType paymentType;
    private Long totalRefundAmount;
    private DateTime refundDate;
    private RefundDepartmentEnum refundDepartment;
    private RefundScenarioEnum refundScenario;
    private RefundCalculationBasis refundBasis;
    private Integer deposit;
    private Long administrationFee;
    private String ticketDescription;
    private String caseNotes;
    private Long totalTicketPrice;
    private List<RefundItemCmd> refundItems;
    private List<GoodwillPaymentItemCmd> goodwillPaymentItemList;
    protected String targetCardNumber;
    private Integer payAsYouGoCredit;
    protected String payeeSortCode;
    protected String payeeAccountNumber;
    protected Long stationId;
    protected String cardNumber;
    
    protected Boolean ticketCorrupt = Boolean.FALSE;
    protected Boolean agentInterventionRequired = Boolean.FALSE;
    protected Boolean supervisorResolved = Boolean.FALSE;
    protected Boolean chargeAdminFee = Boolean.FALSE;
    protected Boolean goodwillPaymentRequired = Boolean.FALSE;
    protected Boolean caseOwnerRequiresApproval = Boolean.FALSE;
    protected Boolean abnormalRefundTrends = Boolean.FALSE;
    protected Boolean approvalRequired = Boolean.FALSE;
    protected Boolean adminFeeChanged = Boolean.FALSE;
    protected Boolean calculationBasisChanged = Boolean.FALSE;
    protected Boolean payAsYouGoChanged = Boolean.FALSE;
    protected Boolean refundRejected = Boolean.FALSE;
    protected Boolean bacsChequePaymentRequired = Boolean.FALSE;
    protected Boolean adhocLoadRequired = Boolean.FALSE;
    protected Boolean cardPaymentRequired = Boolean.FALSE;
    protected Boolean firstStageApprovalGiven = Boolean.FALSE;
    protected Boolean secondStageApprovalGiven = Boolean.FALSE;
    protected Boolean secondStageApprovalRequired = Boolean.FALSE;
    protected Boolean acceptedByGate = Boolean.FALSE;
    protected Boolean firstFailure = Boolean.FALSE;
    protected Boolean supervisorAssignsToQueue = Boolean.FALSE;
    protected ApplicationName refundOriginatingApplication;
    protected Boolean cardPaymentSuccessful = Boolean.FALSE;
    protected Boolean refundOriginatingApplicationIsInnovator = Boolean.FALSE;
    protected Boolean refundOriginatingApplicationIsOnline = Boolean.FALSE;
    protected Boolean reassignedFromExceptionQueue = Boolean.FALSE;
    protected Boolean previouslyExchanged= Boolean.FALSE;
    
    public Boolean getRefundOriginatingApplicationIsInnovator() {
        return refundOriginatingApplicationIsInnovator;
    }

    public Boolean getRefundOriginatingApplicationIsOnline() {
        return refundOriginatingApplicationIsOnline;
    }

    public void setRefundOriginatingApplicationIsInnovator(Boolean refundOriginatingApplicationIsInnovator) {
        this.refundOriginatingApplicationIsInnovator = refundOriginatingApplicationIsInnovator;
    }

    public void setRefundOriginatingApplicationIsOnline(Boolean refundOriginatingApplicationIsOnline) {
        this.refundOriginatingApplicationIsOnline = refundOriginatingApplicationIsOnline;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

    @Override
    public String toString() {

        return new ToStringBuilder(this).append("name", this.name).append("cardId", this.cardId).append("ticketCorrupt", this.ticketCorrupt)
                        .append("agentInterventionRequired ", this.agentInterventionRequired).append("supervisorResolved ", this.supervisorResolved)
                        .append("chargeAdminFee ", this.chargeAdminFee).append("goodwillPaymentRequired ", this.goodwillPaymentRequired)
                        .append("caseOwnerRequiresApproval ", this.caseOwnerRequiresApproval)
                        .append("abnormalRefundTrends ", this.abnormalRefundTrends).append("approvalRequired ", this.approvalRequired)
                        .append("adminFeeChanged ", this.adminFeeChanged).append("refundRejected ", this.refundRejected)
                        .append("bacsChequePaymentRequired ", this.bacsChequePaymentRequired).append("adhocLoadRequired ", this.adhocLoadRequired)
                        .append("cardPaymentRequired ", this.cardPaymentRequired).append("firstStageApprovalGiven ", this.firstStageApprovalGiven)
                        .append("secondStageApprovalGiven ", this.secondStageApprovalGiven)
                        .append("secondStageApprovalRequired ", this.secondStageApprovalRequired).append("acceptedByGate ", this.acceptedByGate)
                        .append("firstFailure ", this.firstFailure).append("supervisorAssignsToQueue ", this.supervisorAssignsToQueue)
                        .append("cardPaymentSuccessful", this.cardPaymentSuccessful).toString();

    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Refund getRefundEntity() {
        return refundEntity;
    }

    public void setRefundEntity(Refund refundEntity) {
        this.refundEntity = refundEntity;
    }

    public AddressDTO getAlternativeAddress() {
        return alternativeAddress;
    }

    public void setAlternativeAddress(AddressDTO alternativeAddress) {
        this.alternativeAddress = alternativeAddress;
    }

    public CustomerDTO getCustomer() {
        return customer;
    }

    public AddressDTO getAddress() {
        return address;
    }

    public CustomerDTO getAlternativeRefundPayee() {
        return alternativeRefundPayee;
    }

    public void setCustomer(CustomerDTO customer) {
        this.customer = customer;
    }

    public void setAddress(AddressDTO address) {
        this.address = address;
    }

    public void setAlternativeRefundPayee(CustomerDTO alternativeRefundPayee) {
        this.alternativeRefundPayee = alternativeRefundPayee;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public Long getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Long webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Long getTotalRefundAmount() {
        return totalRefundAmount;
    }

    public void setTotalRefundAmount(Long totalRefundAmount) {
        this.totalRefundAmount = totalRefundAmount;
    }

    public DateTime getRefundDate() {
        return refundDate;
    }

    public void setRefundDate(DateTime refundDate) {
        this.refundDate = refundDate;
    }

    public RefundDepartmentEnum getRefundDepartment() {
        return refundDepartment;
    }

    public void setRefundDepartment(RefundDepartmentEnum refundDepartment) {
        this.refundDepartment = refundDepartment;
    }

    public RefundScenarioEnum getRefundScenario() {
        return refundScenario;
    }

    public void setRefundScenario(RefundScenarioEnum refundScenario) {
        this.refundScenario = refundScenario;
    }

    public RefundCalculationBasis getRefundBasis() {
        return refundBasis;
    }

    public void setRefundBasis(RefundCalculationBasis refundBasis) {
        this.refundBasis = refundBasis;
    }

    public Boolean getTicketCorrupt() {
        return ticketCorrupt;
    }

    public void setTicketCorrupt(Boolean ticketCorrupt) {
        this.ticketCorrupt = ticketCorrupt;
    }

    public Boolean getAgentInterventionRequired() {
        return agentInterventionRequired;
    }

    public void setAgentInterventionRequired(Boolean agentInterventionRequired) {
        this.agentInterventionRequired = agentInterventionRequired;
    }

    public Boolean getSupervisorResolved() {
        return supervisorResolved;
    }

    public void setSupervisorResolved(Boolean supervisorResolved) {
        this.supervisorResolved = supervisorResolved;
    }

    public Boolean getChargeAdminFee() {
        return chargeAdminFee;
    }

    public void setChargeAdminFee(Boolean chargeAdminFee) {
        this.chargeAdminFee = chargeAdminFee;
    }

    public Boolean getGoodwillPaymentRequired() {
        return goodwillPaymentRequired;
    }

    public void setGoodwillPaymentRequired(Boolean goodwillPaymentRequired) {
        this.goodwillPaymentRequired = goodwillPaymentRequired;
    }

    public Boolean getCaseOwnerRequiresApproval() {
        return caseOwnerRequiresApproval;
    }

    public void setCaseOwnerRequiresApproval(Boolean caseOwnerRequiresApproval) {
        this.caseOwnerRequiresApproval = caseOwnerRequiresApproval;
    }

    public Boolean getAbnormalRefundTrends() {
        return abnormalRefundTrends;
    }

    public void setAbnormalRefundTrends(Boolean abnormalRefundTrends) {
        this.abnormalRefundTrends = abnormalRefundTrends;
    }

    public Boolean getApprovalRequired() {
        return approvalRequired;
    }

    public void setApprovalRequired(Boolean approvalRequired) {
        this.approvalRequired = approvalRequired;
    }

    public Boolean getAdminFeeChanged() {
        return adminFeeChanged;
    }

    public void setAdminFeeChanged(Boolean adminFeeChanged) {
        this.adminFeeChanged = adminFeeChanged;
    }

    public Boolean getCalculationBasisChanged() {
        return calculationBasisChanged;
    }

    public void setCalculationBasisChanged(Boolean calculationBasisChanged) {
        this.calculationBasisChanged = calculationBasisChanged;
    }

    public Boolean getPayAsYouGoChanged() {
        return payAsYouGoChanged;
    }

    public void setPayAsYouGoChanged(Boolean payAsYouGoChanged) {
        this.payAsYouGoChanged = payAsYouGoChanged;
    }

    public Boolean getRefundRejected() {
        return refundRejected;
    }

    public void setRefundRejected(Boolean refundRejected) {
        this.refundRejected = refundRejected;
    }

    public Boolean getBacsChequePaymentRequired() {
        return bacsChequePaymentRequired;
    }

    public void setBacsChequePaymentRequired(Boolean bacsChequePaymentRequired) {
        this.bacsChequePaymentRequired = bacsChequePaymentRequired;
    }

    public Boolean getAdhocLoadRequired() {
        return adhocLoadRequired;
    }

    public void setAdhocLoadRequired(Boolean adhocLoadRequired) {
        this.adhocLoadRequired = adhocLoadRequired;
    }

    public Boolean getCardPaymentRequired() {
        return cardPaymentRequired;
    }

    public void setCardPaymentRequired(Boolean cardPaymentRequired) {
        this.cardPaymentRequired = cardPaymentRequired;
    }

    public Boolean getFirstStageApprovalGiven() {
        return firstStageApprovalGiven;
    }

    public void setFirstStageApprovalGiven(Boolean firstStageApprovalGiven) {
        this.firstStageApprovalGiven = firstStageApprovalGiven;
    }

    public Boolean getAcceptedByGate() {
        return acceptedByGate;
    }

    public void setAcceptedByGate(Boolean acceptedByGate) {
        this.acceptedByGate = acceptedByGate;
    }

    public Boolean getFirstFailure() {
        return firstFailure;
    }

    public void setFirstFailure(Boolean firstFailure) {
        this.firstFailure = firstFailure;
    }

    public Boolean getSupervisorAssignsToQueue() {
        return supervisorAssignsToQueue;
    }

    public void setSupervisorAssignsToQueue(Boolean supervisorAssignsToQueue) {
        this.supervisorAssignsToQueue = supervisorAssignsToQueue;
    }

    public Boolean getSecondStageApprovalGiven() {
        return secondStageApprovalGiven;
    }

    public void setSecondStageApprovalGiven(Boolean secondStageApprovalGiven) {
        this.secondStageApprovalGiven = secondStageApprovalGiven;
    }

    public Boolean getSecondStageApprovalRequired() {
        return secondStageApprovalRequired;
    }

    public void setSecondStageApprovalRequired(Boolean secondStageApprovalRequired) {
        this.secondStageApprovalRequired = secondStageApprovalRequired;
    }

    public Integer getDeposit() {
        return deposit;
    }

    public Long getAdministrationFee() {
        return administrationFee;
    }

    public void setDeposit(Integer deposit) {
        this.deposit = deposit;
    }

    public void setAdministrationFee(Long administrationFee) {
        this.administrationFee = administrationFee;
    }

    public String getTicketDescription() {
        return ticketDescription;
    }

    public void setTicketDescription(String ticketDescription) {
        this.ticketDescription = ticketDescription;
    }

    public String getCaseNotes() {
        return caseNotes;
    }

    public void setCaseNotes(String caseNotes) {
        this.caseNotes = caseNotes;
    }

    public Long getTotalTicketPrice() {
        return totalTicketPrice;
    }

    public void setTotalTicketPrice(Long originalTicketPrice) {
        this.totalTicketPrice = originalTicketPrice;
    }

    public List<RefundItemCmd> getRefundItems() {
        return refundItems;
    }

    public void setRefundItems(List<RefundItemCmd> refundItems) {
        this.refundItems = refundItems;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long id) {
        customerId = id;
    }

    public ApplicationName getRefundOriginatingApplication() {
        return refundOriginatingApplication;
    }

    public void setRefundOriginatingApplication(ApplicationName refundOriginatingApplication) {
        this.refundOriginatingApplication = refundOriginatingApplication;
    }

    public Boolean getCardPaymentSuccessful() {
        return cardPaymentSuccessful;
    }

    public void setCardPaymentSuccessful(Boolean cardPaymentSuccessful) {
        this.cardPaymentSuccessful = cardPaymentSuccessful;
    }

    public List<GoodwillPaymentItemCmd> getGoodwillItems() {
        return goodwillPaymentItemList;
    }

    public void setGoodwillItems(List<GoodwillPaymentItemCmd> goodwillItems) {
        this.goodwillPaymentItemList = goodwillItems;
    }

    public String getTargetCardNumber() {
        return targetCardNumber;
    }

    public void setTargetCardNumber(String cardNumber) {
        this.targetCardNumber = cardNumber;
    }

    public Integer getPayAsYouGoCredit() {
        return payAsYouGoCredit;
    }

    public void setPayAsYouGoCredit(Integer payAsYouGoCredit) {
        this.payAsYouGoCredit = payAsYouGoCredit;
    }

	public String getPayeeSortCode() {
		return payeeSortCode;
	}

	public void setPayeeSortCode(String payeeSortCode) {
		this.payeeSortCode = payeeSortCode;
	}

	public String getPayeeAccountNumber() {
		return payeeAccountNumber;
	}

	public void setPayeeAccountNumber(String payeeAccountNumber) {
		this.payeeAccountNumber = payeeAccountNumber;
	}

	public Long getStationId() {
		return stationId;
	}

	public void setStationId(Long stationId) {
		this.stationId = stationId;
	}
	
    public List<GoodwillPaymentItemCmd> getGoodwillPaymentItemList() {
        return goodwillPaymentItemList;
    }

    public Boolean getReassignedFromExceptionQueue() {
        return reassignedFromExceptionQueue;
    }

    public void setGoodwillPaymentItemList(List<GoodwillPaymentItemCmd> goodwillPaymentItemList) {
        this.goodwillPaymentItemList = goodwillPaymentItemList;
    }

    public void setReassignedFromExceptionQueue(Boolean reassignedFromExceptionQueue) {
        this.reassignedFromExceptionQueue = reassignedFromExceptionQueue;
    }

	public Boolean getPreviouslyExchanged() {
		return previouslyExchanged;
	}

	public void setPreviouslyExchanged(Boolean previouslyExchanged) {
		this.previouslyExchanged = previouslyExchanged;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	
}
