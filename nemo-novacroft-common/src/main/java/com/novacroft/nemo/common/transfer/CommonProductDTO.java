package com.novacroft.nemo.common.transfer;

/**
 * product transfer common definition
 */
public class CommonProductDTO extends AbstractBaseDTO {
	
    private static final long serialVersionUID = -6483265491014839517L;
    protected String rate;
    protected String productCode;
    protected String productName;
    protected Integer ticketPrice;
    protected String duration;
    protected String type;
    
    public CommonProductDTO() {
        super();
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
