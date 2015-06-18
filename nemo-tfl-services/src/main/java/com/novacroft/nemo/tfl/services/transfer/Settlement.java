package com.novacroft.nemo.tfl.services.transfer;

import java.util.Date;

import com.novacroft.nemo.common.utils.DateUtil;

public class Settlement {
    //Common Settlement Fields
    private Long id;
    private String status;
    private Integer amount;
    private Date settlementDate;
    private Long settlementNumber;
    private String formattedSettlementDate;
    
    //Web Credit and Payment Card Settlement Fields
    private String transactionType;
    private String transactionUuid;
    private String decision;
    private String message;
    private String reasonCode;
    private Integer authorisedAmount;
    private Date authorisationTime;
    private String authorisationTransactionReferenceNumber;
    
    //AdHocLoad Settlement Fields
    private Integer requestSequenceNumber;
    private Integer pickUpNationalLocationCode;
    private Date expiresOn;
    private String formattedExpiresOn;
    private Long itemId;
    
    //Payee Settlement Fields - Parent to BACS and Cheque Settlements
    private String payeeName;
    private Address address;
    private Long paymentReference;
    
    //BACs Settlement Fields
    private String bankAccount;
    private String sortCode;
    private Date bankPaymentDate;
    private Date paymentFailedDate;
    private String bacsRejectCode;
    private String bacsRejectCodeDescription;
    
    //Cheque Settlement Fields
    private Long chequeSerialNumber;
    private Date printedOn;
    private String formattedPrintedOn;
    private Date clearedOn;
    private String formattedClearedOn;
    private Date outdatedOn;
    private String formattedOudatedOn;
    
    //AutoLoad Settlement Fields
    private Integer autoLoadState;
    
    private String type;
    
    public Settlement() {
    }
    
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(Date settlementDate) {
        this.settlementDate = settlementDate;
        this.formattedSettlementDate = DateUtil.formatDate(settlementDate);
    }

    public Long getSettlementNumber() {
        return settlementNumber;
    }

    public void setSettlementNumber(Long settlementNumber) {
        this.settlementNumber = settlementNumber;
    }

    public String getFormattedSettlementDate() {
        return formattedSettlementDate;
    }

    public void setFormattedSettlementDate(String formattedSettlementDate) {
        this.formattedSettlementDate = formattedSettlementDate;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionUuid() {
        return transactionUuid;
    }

    public void setTransactionUuid(String transactionUuid) {
        this.transactionUuid = transactionUuid;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(String reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Integer getAuthorisedAmount() {
        return authorisedAmount;
    }

    public void setAuthorisedAmount(Integer authorisedAmount) {
        this.authorisedAmount = authorisedAmount;
    }

    public Date getAuthorisationTime() {
        return authorisationTime;
    }

    public void setAuthorisationTime(Date authorisationTime) {
        this.authorisationTime = authorisationTime;
    }

    public String getAuthorisationTransactionReferenceNumber() {
        return authorisationTransactionReferenceNumber;
    }

    public void setAuthorisationTransactionReferenceNumber(String authorisationTransactionReferenceNumber) {
        this.authorisationTransactionReferenceNumber = authorisationTransactionReferenceNumber;
    }

    public Integer getRequestSequenceNumber() {
        return requestSequenceNumber;
    }

    public void setRequestSequenceNumber(Integer requestSequenceNumber) {
        this.requestSequenceNumber = requestSequenceNumber;
    }

    public Integer getPickUpNationalLocationCode() {
        return pickUpNationalLocationCode;
    }

    public void setPickUpNationalLocationCode(Integer pickUpNationalLocationCode) {
        this.pickUpNationalLocationCode = pickUpNationalLocationCode;
    }

    public Date getExpiresOn() {
        return expiresOn;
    }

    public void setExpiresOn(Date expiresOn) {
        this.expiresOn = expiresOn;
        this.formattedExpiresOn = DateUtil.formatDate(expiresOn);
    }
    
    public String getFormattedExpiresOn() {
        return formattedExpiresOn;
    }

    public void setFormattedExpiresOn(String formattedExpiresOn) {
        this.formattedExpiresOn = formattedExpiresOn;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Long getPaymentReference() {
        return paymentReference;
    }

    public void setPaymentReference(Long paymentReference) {
        this.paymentReference = paymentReference;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public Date getBankPaymentDate() {
        return bankPaymentDate;
    }

    public void setBankPaymentDate(Date bankPaymentDate) {
        this.bankPaymentDate = bankPaymentDate;
    }

    public Date getPaymentFailedDate() {
        return paymentFailedDate;
    }

    public void setPaymentFailedDate(Date paymentFailedDate) {
        this.paymentFailedDate = paymentFailedDate;
    }

    public String getBacsRejectCode() {
        return bacsRejectCode;
    }

    public void setBacsRejectCode(String bacsRejectCode) {
        this.bacsRejectCode = bacsRejectCode;
    }

    public String getBacsRejectCodeDescription() {
        return bacsRejectCodeDescription;
    }

    public void setBacsRejectCodeDescription(String bacsRejectCodeDescription) {
        this.bacsRejectCodeDescription = bacsRejectCodeDescription;
    }

    public Long getChequeSerialNumber() {
        return chequeSerialNumber;
    }

    public void setChequeSerialNumber(Long chequeSerialNumber) {
        this.chequeSerialNumber = chequeSerialNumber;
    }

    public Date getPrintedOn() {
        return printedOn;
    }

    public void setPrintedOn(Date printedOn) {
        this.printedOn = printedOn;
        if(printedOn != null){
            this.formattedPrintedOn = DateUtil.formatDate(printedOn);
        }
    }

    public String getFormattedPrintedOn() {
        return formattedPrintedOn;
    }

    public void setFormattedPrintedOn(String formattedPrintedOn) {
        this.formattedPrintedOn = formattedPrintedOn;
    }

    public Date getClearedOn() {
        return clearedOn;
    }

    public void setClearedOn(Date clearedOn) {
        this.clearedOn = clearedOn;
        if(this.clearedOn != null){
            this.formattedClearedOn = DateUtil.formatDate(this.clearedOn);
        }
    }

    public String getFormattedClearedOn() {
        return formattedClearedOn;
    }

    public void setFormattedClearedOn(String formattedClearedOn) {
        this.formattedClearedOn = formattedClearedOn;
    }

    public Date getOutdatedOn() {
        return outdatedOn;
    }

    public void setOutdatedOn(Date outdatedOn) {
        this.outdatedOn = outdatedOn;
        if(outdatedOn != null){
            this.formattedOudatedOn = DateUtil.formatDate(outdatedOn);
        }
    }

    public String getFormattedOudatedOn() {
        return formattedOudatedOn;
    }

    public void setFormattedOudatedOn(String formattedOudatedOn) {
        this.formattedOudatedOn = formattedOudatedOn;
    }

    public Integer getAutoLoadState() {
        return autoLoadState;
    }

    public void setAutoLoadState(Integer autoLoadState) {
        this.autoLoadState = autoLoadState;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    
}
