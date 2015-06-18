package com.novacroft.nemo.common.transfer;

/**
 * Event transfer common definition
 */

public class CommonEventDTO extends AbstractBaseDTO {

	protected String name;
	protected String description;
	protected Integer eventOrder;
	protected Integer displayOrder;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getEventOrder() {
		return eventOrder;
	}
	public void setEventOrder(Integer eventOrder) {
		this.eventOrder = eventOrder;
	}
	public Integer getDisplayOrder() {
		return displayOrder;
	}
	public void setDisplayOrder(Integer displayOrder) {
		this.displayOrder = displayOrder;
	}
	
	
	
}
