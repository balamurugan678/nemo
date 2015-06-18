package com.novacroft.nemo.common.domain;

import java.util.Date;

import javax.persistence.MappedSuperclass;
import javax.persistence.Version;

import org.hibernate.envers.Audited;

/**
 * Common settlement attributes.  A settlement is a payment or refund against an order.
 */
@Audited
@MappedSuperclass() 
public class CommonSettlement extends AbstractBaseEntity {
    private static final long serialVersionUID = 1L;
    protected Long version;
    protected Long orderId;
    protected String status;
    protected Date settlementDate;
    protected Long settlementNumber;    
        
    /**
     * Value of settlement (in pence): positive for payment; and negative for refund
     */
    protected Integer amount;

    @Version
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
