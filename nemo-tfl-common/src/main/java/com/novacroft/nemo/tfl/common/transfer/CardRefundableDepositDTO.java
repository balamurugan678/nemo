package com.novacroft.nemo.tfl.common.transfer;

import java.util.Date;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * TfL card refundable deposit amount transfer implementation
 */

public class CardRefundableDepositDTO extends AbstractBaseDTO {
    protected Integer price;
    protected Date startDate;
    protected Date endDate;
	
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
	
}
