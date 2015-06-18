package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * TfL auto top-up transfer implementation
 */

public class AutoTopUpDTO extends AbstractBaseDTO {
	protected Integer autoTopUpAmount;
	
	public AutoTopUpDTO() {
		
	}
	
	public Integer getAutoTopUpAmount() {
		return autoTopUpAmount;
	}

	public void setAutoTopUpAmount(Integer autoTopUpAmount) {
		this.autoTopUpAmount = autoTopUpAmount;
	}

}
