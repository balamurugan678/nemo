package com.novacroft.nemo.tfl.common.transfer;

import com.novacroft.nemo.common.transfer.AbstractBaseDTO;

/**
 * TfL zone id description transfer common definition
 */

public class ZoneIdDescriptionDTO extends AbstractBaseDTO {
	protected String description;

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
