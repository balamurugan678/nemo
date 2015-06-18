package com.novacroft.nemo.tfl.common.command.impl;

import java.util.List;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.transfer.WorkflowItemDTO;

public class WorkflowCmd {

    protected String refundCaseNumber;
    protected Long totalRefund;
    protected String createdDate;
    protected String refundStatus;
    protected String refundName;
    protected String refundUserName;
    protected List<String> refundAddress;
    protected String refundScenarioType;
    protected String refundScenarioSubType;
    protected String refundablePeriodStartDate;
    protected String paymentMethod;
    protected String paymentName;
    protected List<String> paymentAddress;
    protected String ticketItemName;
    protected Long originalTicketPrice;

    protected String ticketType;
    protected String calculationBasis;
    protected Long totalTicketAmount;
    protected Long ticketDeposit;
    protected Long ticketAdminFee;

    protected String caseNotes;
    protected WorkflowItemDTO workflowItem;
    
    protected List<RefundItemCmd> refundItems;
    protected List<GoodwillPaymentItemCmd> goodwillPaymentItems;
    protected AddressDTO customerAddressDTO;
    protected AddressDTO paymentAddressDTO;

    protected Boolean edit;
    
    protected String reasons;
    protected String timeOnQueue;
    protected String agentName;
    
    protected Integer payAsYouGoCredit;
    protected String payeeSortCode;
    protected String payeeAccountNumber;
    protected String station;
    protected AddressDTO payeeAddress;
    protected String targetCardNumber;
    
    public WorkflowCmd(){
    }

    public WorkflowCmd(String refundCaseNumber, Long totalRefund, String createdDate, String refundStatus, String refundName, String refundUserName,
                    List<String> refundAddress, String refundScenarioType, String refundScenarioSubType, String refundablePeriodStartDate,
                    String paymentMethod, String paymentName, List<String> paymentAddress, String ticketItemName, Long ticketPrice,
                    String calculationBasis, Long ticketAmount, Long ticketDeposit, Long ticketAdminFee, String caseNotes, String reasons,
                    String timeOnQueue, String agentName, String payeeSortCode, String payeeAccountNumber, String station, String targetCardNumber) {
        this.refundCaseNumber = refundCaseNumber; 
        this.totalRefund = totalRefund;
        this.createdDate = createdDate;
        this.refundStatus = refundStatus;
        this.refundName = refundName;
        this.refundUserName = refundUserName;
        this.refundAddress = refundAddress;
        this.refundScenarioType= refundScenarioType;
        this.refundScenarioSubType = refundScenarioSubType;
        this.refundablePeriodStartDate = refundablePeriodStartDate;
        this.paymentMethod = paymentMethod;
        this.paymentName = paymentName;
        this.paymentAddress = paymentAddress;
        this.ticketItemName = ticketItemName;
        this.originalTicketPrice = ticketPrice;
        this.calculationBasis = calculationBasis;
        this.totalTicketAmount = ticketAmount;
        this.ticketDeposit = ticketDeposit;
        this.ticketAdminFee = ticketAdminFee;
        this.caseNotes = caseNotes;
        this.reasons = reasons;
        this.timeOnQueue = timeOnQueue;
        this.agentName = agentName;
        this.payeeSortCode = payeeSortCode;
        this.payeeAccountNumber = payeeAccountNumber;
        this.station = station;
        this.targetCardNumber = targetCardNumber;
    }
    
    public String getRefundCaseNumber() {
        return refundCaseNumber;
    }
    public Long getTotalRefund() {
        return totalRefund;
    }
    public String getCreatedDate() {
        return createdDate;
    }
    public String getRefundStatus() {
        return refundStatus;
    }
    public String getRefundName() {
        return refundName;
    }
    public String getRefundUserName() {
        return refundUserName;
    }
    public List<String> getRefundAddress() {
        return refundAddress;
    }
    public String getRefundScenarioType() {
        return refundScenarioType;
    }
    public String getRefundScenarioSubType() {
        return refundScenarioSubType;
    }
    public String getRefundablePeriodStartDate() {
        return refundablePeriodStartDate;
    }
    public String getPaymentMethod() {
        return paymentMethod;
    }
    public String getPaymentName() {
        return paymentName;
    }
    public List<String> getPaymentAddress() {
        return paymentAddress;
    }
    public void setRefundCaseNumber(String refundCaseNumber) {
        this.refundCaseNumber = refundCaseNumber;
    }
    public void setTotalRefund(Long long1) {
        this.totalRefund = long1;
    }
    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus;
    }
    public void setRefundName(String refundName) {
        this.refundName = refundName;
    }
    public void setRefundUserName(String refundUserName) {
        this.refundUserName = refundUserName;
    }
    public void setRefundAddress(List<String> list) {
        this.refundAddress = list;
    }
    public void setRefundScenarioType(String refundScenarioType) {
        this.refundScenarioType = refundScenarioType;
    }
    public void setRefundScenarioSubType(String refundScenarioSubType) {
        this.refundScenarioSubType = refundScenarioSubType;
    }
    public void setRefundablePeriodStartDate(String refundablePeriodStartDate) {
        this.refundablePeriodStartDate = refundablePeriodStartDate;
    }
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }
    public void setPaymentAddress(List<String> paymentAddress) {
        this.paymentAddress = paymentAddress;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public String getTicketItemName() {
        return ticketItemName;
    }
   
    public String getCalculationBasis() {
        return calculationBasis;
    }

    public Long getTotalTicketAmount() {
        return totalTicketAmount;
    }
    public Long  getTicketDeposit() {
        return ticketDeposit;
    }
    public Long  getTicketAdminFee() {
        return ticketAdminFee;
    }
    public void setTicketItemName(String ticketItemName) {
        this.ticketItemName = ticketItemName;
    }
    
    public void setCalculationBasis(String calculationBasis) {
        this.calculationBasis = calculationBasis;
    }

    public void setTotalTicketAmount(Long totalTicketAmount) {
        this.totalTicketAmount = totalTicketAmount;
    }
    public void setTicketDeposit(Long ticketDeposit) {
        this.ticketDeposit = ticketDeposit;
    }
    public void setTicketAdminFee(Long ticketAdminFee) {
        this.ticketAdminFee = ticketAdminFee;
    }
    public void setWorkflowItem(WorkflowItemDTO workflowItem) {
       this.workflowItem = workflowItem;
        
    }
    public WorkflowItemDTO getWorkflowItem() {
        return workflowItem;
    }
    
    public Long getOriginalTicketPrice() {
        return originalTicketPrice;
    }

    public void setOriginalTicketPrice(Long originalTicketPrice) {
        this.originalTicketPrice = originalTicketPrice;
    }

    public String getCaseNotes() {
        return caseNotes;
    }

    public void setCaseNotes(String caseNotes) {
        this.caseNotes = caseNotes;
    }

    public Boolean getEdit() {
        return edit;
    }

    public void setEdit(Boolean edit) {
        this.edit = edit;
    }

    public List<RefundItemCmd> getRefundItems() {
        return refundItems;
    }

    public void setRefundItems(List<RefundItemCmd> refundItems) {
        this.refundItems = refundItems;
    }

    public AddressDTO getCustomerAddressDTO() {
        return customerAddressDTO;
    }

    public void setCustomerAddressDTO(AddressDTO customerAddressDTO) {
        this.customerAddressDTO = customerAddressDTO;
    }

    public AddressDTO getPaymentAddressDTO() {
        return paymentAddressDTO;
    }

    public void setPaymentAddressDTO(AddressDTO paymentAddressDTO) {
        this.paymentAddressDTO = paymentAddressDTO;
    }

	public String getReasons() {
		return reasons;
	}

	public void setReasons(String reasons) {
		this.reasons = reasons;
	}

	public String getTimeOnQueue() {
		return timeOnQueue;
	}

	public void setTimeOnQueue(String timeOnQueue) {
		this.timeOnQueue = timeOnQueue;
	}

	public String getAgentName() {
		return agentName;
	}

	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}

    public Integer getPayAsYouGoCredit() {
        return payAsYouGoCredit;
    }

    public void setPayAsYouGoCredit(Integer payAsYouGoCredit) {
        this.payAsYouGoCredit = payAsYouGoCredit;
    }

    public List<GoodwillPaymentItemCmd> getGoodwillPaymentItems() {
        return goodwillPaymentItems;
    }

    public void setGoodwillPaymentItems(List<GoodwillPaymentItemCmd> goodwillPaymentItems) {
        this.goodwillPaymentItems = goodwillPaymentItems;
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

    public String getStation() {
        return station;
	}

    public void setStation(String station) {
        this.station = station;
	}

	public AddressDTO getPayeeAddress() {
		return payeeAddress;
	}

	public void setPayeeAddress(AddressDTO payeeAddress) {
		this.payeeAddress = payeeAddress;
	}
	public String getTargetCardNumber() {
		return targetCardNumber;
	}

	public void setTargetCardNumber(String targetCardNumber) {
		this.targetCardNumber = targetCardNumber;
	}

}
