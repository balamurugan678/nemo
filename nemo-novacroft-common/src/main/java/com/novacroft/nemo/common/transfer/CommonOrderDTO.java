package com.novacroft.nemo.common.transfer;

import java.util.Date;

/**
 * Order transfer class common attributes
 */
public class CommonOrderDTO extends AbstractBaseDTO {
    protected Long webAccountId;
    protected Long customerId;
    protected Long orderNumber;
    protected Date orderDate;
    protected Integer totalAmount;
    protected String status;
    protected Long stationId;
    protected String formattedTotalAmount;
    protected Long approvalId;
    protected Long cardId;
    protected Date refundDate;

    public Long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getWebAccountId() {
        return webAccountId;
    }

    public void setWebAccountId(Long webAccountId) {
        this.webAccountId = webAccountId;
    }

    public Long getStationId() {
        return stationId;
    }

    public void setStationId(Long stationId) {
        this.stationId = stationId;
    }

    public String getFormattedTotalAmount() {
        return formattedTotalAmount;
    }

    public void setFormattedTotalAmount(String formattedTotalAmount) {
        this.formattedTotalAmount = formattedTotalAmount;
    }
    
    public Long getApprovalId() {
        return approvalId;
    }
    
    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }
    
    public Long getCustomerId() {
    	return customerId;
    }
    
    public void setCustomerId(Long custId) {
    	this.customerId = custId;
    }
    
    public Long getCardId() {
        return cardId;
    }

    public void setCardId(Long cardId) {
        this.cardId = cardId;
    }

	public Date getRefundDate() {
		return refundDate;
	}

	public void setRefundDate(Date refundDate) {
		this.refundDate = refundDate;
	}
}
