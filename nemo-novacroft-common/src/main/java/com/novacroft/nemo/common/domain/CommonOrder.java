package com.novacroft.nemo.common.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;

import org.hibernate.envers.Audited;

/**
 * Order common attributes
 */
@Audited
@MappedSuperclass()
public abstract class CommonOrder extends AbstractBaseEntity {
    private static final long serialVersionUID = -4823488702876654205L;
    protected Long orderNumber;
    protected Date orderDate;
    protected Integer totalAmount;
    protected String status;
    protected Long webAccountId;
    protected Long customerId;    
    protected Long stationId;
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

    public Long getApprovalId() {
        return approvalId;
    }

    public void setApprovalId(Long approvalId) {
        this.approvalId = approvalId;
    }
    
    public Long getCustomerId() {
    	return customerId;
    }
    
    public void setCustomerId(Long customerId) {
    	this.customerId = customerId;
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
