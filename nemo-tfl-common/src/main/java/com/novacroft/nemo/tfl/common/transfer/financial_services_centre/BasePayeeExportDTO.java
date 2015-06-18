package com.novacroft.nemo.tfl.common.transfer.financial_services_centre;

import java.util.Date;

public class BasePayeeExportDTO {

	protected Integer documentId;
	protected Date documentDate;
	protected Date postingDate;
	protected String documentType;
	protected String companyCode;
	protected String currency;
	protected String referenceNumber;
	protected String documentHeaderText;
	protected String accountType;
	protected Integer accountNumber;
	protected Date baselineDate;
	protected Float netAmount;
	protected String taxCode;
	protected String customerName;
	protected AddressExportDTO addressExportDTO;
	protected String customerReference;
	protected String customerVatRegistrationNumber;
	protected String paymentMethod;
	protected String otvReference;

	public BasePayeeExportDTO() {
		super();
	}

	public Integer getDocumentId() {
	    return documentId;
	}

	public void setDocumentId(Integer documentId) {
	    this.documentId = documentId;
	}

	public Date getDocumentDate() {
	    return documentDate;
	}

	public void setDocumentDate(Date documentDate) {
	    this.documentDate = documentDate;
	}

	public Date getPostingDate() {
	    return postingDate;
	}

	public void setPostingDate(Date postingDate) {
	    this.postingDate = postingDate;
	}

	public String getDocumentType() {
	    return documentType;
	}

	public void setDocumentType(String documentType) {
	    this.documentType = documentType;
	}

	public String getCompanyCode() {
	    return companyCode;
	}

	public void setCompanyCode(String companyCode) {
	    this.companyCode = companyCode;
	}

	public String getCurrency() {
	    return currency;
	}

	public void setCurrency(String currency) {
	    this.currency = currency;
	}

	public String getReferenceNumber() {
	    return referenceNumber;
	}

	public void setReferenceNumber(String referenceNumber) {
	    this.referenceNumber = referenceNumber;
	}

	public String getDocumentHeaderText() {
	    return documentHeaderText;
	}

	public void setDocumentHeaderText(String documentHeaderText) {
	    this.documentHeaderText = documentHeaderText;
	}

	public String getAccountType() {
	    return accountType;
	}

	public void setAccountType(String accountType) {
	    this.accountType = accountType;
	}

	public Integer getAccountNumber() {
	    return accountNumber;
	}

	public void setAccountNumber(Integer accountNumber) {
	    this.accountNumber = accountNumber;
	}

	public Date getBaselineDate() {
	    return baselineDate;
	}

	public void setBaselineDate(Date baselineDate) {
	    this.baselineDate = baselineDate;
	}

	public Float getNetAmount() {
	    return netAmount;
	}

	public void setNetAmount(Float netAmount) {
	    this.netAmount = netAmount;
	}

	public String getTaxCode() {
	    return taxCode;
	}

	public void setTaxCode(String taxCode) {
	    this.taxCode = taxCode;
	}

	public String getCustomerName() {
	    return customerName;
	}

	public void setCustomerName(String customerName) {
	    this.customerName = customerName;
	}

	public String getCustomerReference() {
	    return customerReference;
	}

	public void setCustomerReference(String customerReference) {
	    this.customerReference = customerReference;
	}

	public String getCustomerVatRegistrationNumber() {
	    return customerVatRegistrationNumber;
	}

	public void setCustomerVatRegistrationNumber(String customerVatRegistrationNumber) {
	    this.customerVatRegistrationNumber = customerVatRegistrationNumber;
	}

	public AddressExportDTO getAddressExportDTO() {
	    return addressExportDTO;
	}

	public void setAddressExportDTO(AddressExportDTO addressExportDTO) {
	    this.addressExportDTO = addressExportDTO;
	}

	public String getPaymentMethod() {
	    return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
	    this.paymentMethod = paymentMethod;
	}

	public String getOtvReference() {
		return otvReference;
	}

	public void setOtvReference(String otvReference) {
		this.otvReference = otvReference;
	}

	
}