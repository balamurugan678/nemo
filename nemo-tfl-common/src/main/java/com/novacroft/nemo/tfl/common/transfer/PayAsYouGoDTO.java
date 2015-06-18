package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * TfL pay as you go transfer common definition
 */

public class PayAsYouGoDTO extends AbstractBaseDTO {
	private static final long serialVersionUID = -6538534709005893886L;
	protected String payAsYouGoName;
    protected Integer ticketPrice;
    
	public String getPayAsYouGoName() {
		return payAsYouGoName;
	}
	public void setPayAsYouGoName(String payAsYouGoName) {
		this.payAsYouGoName = payAsYouGoName;
	}
	public Integer getTicketPrice() {
		return ticketPrice;
	}
	public void setTicketPrice(Integer ticketPrice) {
		this.ticketPrice = ticketPrice;
	}
    
}
