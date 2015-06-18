package com.novacroft.nemo.common.transfer;

import java.util.Date;

/**
 * Common settlement attributes.  A settlement is a payment or refund against an order.
 */
public class CommonSettlementDTO extends AbstractBaseDTO {
    protected Long version;
    protected Long orderId;
    protected String status;
    protected Date settlementDate;
    protected Long settlementNumber;

    /**
     * Value of settlement (in pence): positive for payment; and negative for refund
     */
    protected Integer amount;

    public CommonSettlementDTO() {
    }

    public CommonSettlementDTO(Long orderId, String status, Date settlementDate, Integer amount) {
        this.orderId = orderId;
        this.status = status;
        this.settlementDate = settlementDate;
        this.amount = amount;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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
    }

	public Long getSettlementNumber() {
		return settlementNumber;
	}

	public void setSettlementNumber(Long settlementNumber) {
		this.settlementNumber = settlementNumber;
	}

}
