package com.novacroft.nemo.tfl.common.transfer.financial_services_centre;

import java.util.Date;

public class BACSRequestExportDTO extends BasePayeeExportDTO {
	 
	 protected String customerBnakAccount;
	 
	 protected String customerSortCode;

	 public BACSRequestExportDTO() {
		 
	 }
    public BACSRequestExportDTO(Integer documentId, Date documentDate, Date postingDate, String documentType,
                                  String companyCode, String currency, String referenceNumber, String documentHeaderText,
                                  String accountType, Integer accountNumber, Date baselineDate, Float netAmount, String taxCode,
                                  String customerName, AddressExportDTO addressExportDTO, String paymentMethod) {
        this.documentId = documentId;
        this.documentDate = documentDate;
        this.postingDate = postingDate;
        this.documentType = documentType;
        this.companyCode = companyCode;
        this.currency = currency;
        this.referenceNumber = referenceNumber;
        this.documentHeaderText = documentHeaderText;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.baselineDate = baselineDate;
        this.netAmount = netAmount;
        this.taxCode = taxCode;
        this.customerName = customerName;
        this.addressExportDTO = addressExportDTO;
        this.paymentMethod = paymentMethod;
    }
    public BACSRequestExportDTO(Integer documentId, Date documentDate, Date postingDate, String documentType,
                                  String companyCode, String currency, String referenceNumber, String documentHeaderText,
                                  String accountType, Integer accountNumber, Date baselineDate, Float netAmount, String taxCode,
                                  String paymentMethod) {
        this.documentId = documentId;
        this.documentDate = documentDate;
        this.postingDate = postingDate;
        this.documentType = documentType;
        this.companyCode = companyCode;
        this.currency = currency;
        this.referenceNumber = referenceNumber;
        this.documentHeaderText = documentHeaderText;
        this.accountType = accountType;
        this.accountNumber = accountNumber;
        this.baselineDate = baselineDate;
        this.netAmount = netAmount;
        this.taxCode = taxCode;
        this.paymentMethod = paymentMethod;
    }
	
	public String getCustomerSortCode() {
		return customerSortCode;
	}
	public void setCustomerSortCode(String customerSortCode) {
		this.customerSortCode = customerSortCode;
	}
	public String getCustomerBnakAccount() {
		return customerBnakAccount;
	}
	public void setCustomerBnakAccount(String customerBnakAccount) {
		this.customerBnakAccount = customerBnakAccount;
	}
    
}
