package com.novacroft.nemo.tfl.common.domain;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.novacroft.nemo.tfl.common.constant.financial_services_centre.BACSRejectCodeEnum;

@Entity
@DiscriminatorValue("BACS")
public class BACSSettlement extends PayeeRequestSettlement {

   private static final long serialVersionUID = 7481884052069086587L;
	
	
    /**
     * Bank Account, Padded 8 digit Bank account.
     */
    protected String bankAccount;
    
    protected String sortCode;
    
    protected Date bankPaymentDate;
    
    protected Date paymentFailedDate;
    
    @Enumerated(EnumType.STRING)
    protected BACSRejectCodeEnum financialServicesRejectCode;
    
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
