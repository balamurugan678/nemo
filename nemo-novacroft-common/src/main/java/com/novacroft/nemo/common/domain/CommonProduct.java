package com.novacroft.nemo.common.domain;

import org.hibernate.envers.Audited;

import javax.persistence.MappedSuperclass;

/**
 * Common shopping basket product attributes that will be inherited by all implementations.
 */
@Audited
@MappedSuperclass()
public abstract class CommonProduct extends AbstractBaseEntity {
    private static final long serialVersionUID = 7001524272435183149L;

    protected String rate;
    protected String productCode;
    protected String productName;
    protected Integer ticketPrice;
    protected String duration;
    protected String type;
    
    public CommonProduct() {
        setCreated();
    }

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getTicketPrice() {
		return ticketPrice;
	}

	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
