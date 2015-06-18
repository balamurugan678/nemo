package com.novacroft.nemo.tfl.common.transfer.financial_services_centre;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AddressDTO;
import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;



public class BACSSettlementDTO extends PayeeSettlementDTO {
	
    
    private static final long serialVersionUID = 8892164765132454940L;
    protected String bankAccount;
    protected String sortCode;
    protected Date bankPaymentDate;
    protected Date paymentFailedDate;
    protected BACSRejectCodeEnum financialServicesRejectCode;
    
    public BACSSettlementDTO(){
    }
    
    public BACSSettlementDTO(AddressDTO addressDTO, Integer amount, Date settlementDate, Long orderId, String payeeName, String status, String bankAccount, String sortCode) {
	this.addressDTO = addressDTO;
	this.amount = amount;
	this.settlementDate = settlementDate;
	this.orderId = orderId;
	this.payeeName = payeeName;
	this.status = status;
	this.bankAccount = bankAccount;
	this.sortCode = sortCode;
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
	public BACSRejectCodeEnum getFinancialServicesRejectCode() {
		return financialServicesRejectCode;
	}
	public void setFinancialServicesRejectCode(
			BACSRejectCodeEnum financialServicesRejectCode) {
		this.financialServicesRejectCode = financialServicesRejectCode;
	}
	
	
    
}
