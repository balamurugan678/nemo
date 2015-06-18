package com.novacroft.nemo.common.transfer;


/**
 * Call transfer common definition
 */
public class CommonCallTypeDTO extends AbstractBaseDTO {
	
	protected String description;
	protected Integer active;
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getActive() {
		return active;
	}
	public void setActive(Integer active) {
		this.active = active;
	}

}
